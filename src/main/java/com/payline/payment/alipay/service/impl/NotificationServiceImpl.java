package com.payline.payment.alipay.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.payment.alipay.bean.object.Notification;
import com.payline.payment.alipay.bean.object.Trade;
import com.payline.payment.alipay.bean.request.CreateForexTrade;
import com.payline.payment.alipay.bean.request.NotifyVerify;
import com.payline.payment.alipay.bean.request.SingleTradeQuery;
import com.payline.payment.alipay.bean.response.AlipayAPIResponse;
import com.payline.payment.alipay.exception.PluginException;
import com.payline.payment.alipay.utils.Constants;
import com.payline.payment.alipay.utils.PluginUtils;
import com.payline.payment.alipay.utils.http.HttpClient;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.common.OnHoldCause;
import com.payline.pmapi.bean.common.TransactionCorrelationId;
import com.payline.pmapi.bean.notification.request.NotificationRequest;
import com.payline.pmapi.bean.notification.response.NotificationResponse;
import com.payline.pmapi.bean.notification.response.impl.PaymentResponseByNotificationResponse;
import com.payline.pmapi.bean.payment.request.NotifyTransactionStatusRequest;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseOnHold;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseSuccess;
import com.payline.pmapi.service.NotificationService;

public class NotificationServiceImpl implements NotificationService {
    public static final HttpClient client = HttpClient.getInstance();
    private Gson parser;

    public NotificationServiceImpl() {
        this.parser = new GsonBuilder().create();
    }

    @Override
    public NotificationResponse parse(NotificationRequest request) {
        RequestConfiguration configuration;
        PaymentResponse paymentResponse = null;
        try {
            configuration = new RequestConfiguration(request.getContractConfiguration(), request.getEnvironment(), request.getPartnerConfiguration());
            String content = PluginUtils.inputStreamToString(request.getContent());
            Notification notification = parser.fromJson(content, Notification.class);
            //On crée un object NotifyVerify pour mapper les données de la requête
            NotifyVerify notifyVerify = NotifyVerify.NotifyVerifyBuilder
                    .aNotifyVerify()
                    .withNotifyId(notification.getNotify_id())
                    .withPartner(request.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.PARTNER_ID).getValue())
                    .withService("notify_verify")
                    .build();
            //Check notification is coming from Alipay
            if (client.notificationIsVerified(configuration, notifyVerify.getParametersList())) {
                //Notification is verified, so we can check transaction status
                SingleTradeQuery singleTradeQuery = SingleTradeQuery.SingleTradeQueryBuilder
                        .aSingleTradeQuery()
                        .withOutTradeNo(notification.getOut_trade_no())
                        .withPartner(request.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.PARTNER_ID).getValue())
                        .withService("single_trade_query")
                        .build();
                AlipayAPIResponse response = client.getTransactionStatus(configuration, singleTradeQuery.getParametersList());
                if (response.getIs_success().equalsIgnoreCase("t")) {
                    Trade transaction = response.getResponse().getTrade();
                    if (transaction.getTrade_status().equalsIgnoreCase("TRADE_FINISHED")) {
                        //Return a paymentResponseSuccess
                        paymentResponse = PaymentResponseSuccess.PaymentResponseSuccessBuilder.aPaymentResponseSuccess()
                                .withPartnerTransactionId(transaction.getTrade_no())
                                .withStatusCode(transaction.getTrade_status())
                                .withTransactionAdditionalData(transaction.getBuyer_id())
                                .build();
                    } else if (transaction.getTrade_status().equalsIgnoreCase("TRADE_CLOSED")) {
                        //Return a paymentResponseFailure
                        paymentResponse = PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                                .withErrorCode(transaction.getTrade_status())
                                .withFailureCause(FailureCause.REFUSED)
                                .build();
                    } else if (transaction.getTrade_status().equalsIgnoreCase("WAIT_BUYER_PAY")) {
                        //Return a paymentResponseOnHold
                        paymentResponse = PaymentResponseOnHold.PaymentResponseOnHoldBuilder.aPaymentResponseOnHold()
                                .withPartnerTransactionId(transaction.getTrade_no())
                                .withOnHoldCause(OnHoldCause.ASYNC_RETRY)
                                .build();
                    }
                } else {
                    //Response success is "F"
                    //Return a paymentResponseFailure
                    paymentResponse = PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                            .withErrorCode(response.getError())
                            .withFailureCause(FailureCause.INVALID_DATA)
                            .build();
                }
            } else {
                //Notification verification failed
                //Return a paymentResponseFailure
                paymentResponse = PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                        .withFailureCause(FailureCause.INVALID_DATA)
                        .build();
            }
        } catch (PluginException e) {
            paymentResponse = PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                    .withErrorCode(e.getErrorCode())
                    .withFailureCause(PluginUtils.getFailureCause(e.getFailureCause().toString()))
                    .build();
        } catch (RuntimeException e) {
            paymentResponse = PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                    .withFailureCause(FailureCause.INTERNAL_ERROR)
                    .build();
        }
        TransactionCorrelationId correlationId = TransactionCorrelationId.TransactionCorrelationIdBuilder
                .aCorrelationIdBuilder()
                .withType(TransactionCorrelationId.CorrelationIdType.PARTNER_TRANSACTION_ID)
                .withValue(request.getTransactionId())
                .build();

        return PaymentResponseByNotificationResponse.PaymentResponseByNotificationResponseBuilder
                .aPaymentResponseByNotificationResponseBuilder()
                .withPaymentResponse(paymentResponse)
                .withTransactionCorrelationId(correlationId)
                .build();
    }

    @Override
    public void notifyTransactionStatus(NotifyTransactionStatusRequest notifyTransactionStatusRequest) {
        // does nothing
    }
}
