package com.payline.payment.alipay.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.payment.alipay.utils.Constants;
import com.payline.payment.alipay.utils.PluginUtils;
import com.payline.payment.alipay.utils.http.HttpClient;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.common.TransactionCorrelationId;
import com.payline.pmapi.bean.notification.request.NotificationRequest;
import com.payline.pmapi.bean.notification.response.NotificationResponse;
import com.payline.pmapi.bean.notification.response.impl.PaymentResponseByNotificationResponse;
import com.payline.pmapi.bean.payment.request.NotifyTransactionStatusRequest;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;
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
            paymentResponse = client.handleNotification(configuration, PluginUtils.inputStreamToString(request.getContent()));
        }
        catch (Exception e)
        {
            paymentResponse = PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                    .withErrorCode("Unable to read body")
                    .withFailureCause(FailureCause.INVALID_DATA)
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
