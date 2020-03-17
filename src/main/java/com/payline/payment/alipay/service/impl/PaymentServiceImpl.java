package com.payline.payment.alipay.service.impl;

import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.payment.alipay.bean.request.CreateForexTrade;
import com.payline.payment.alipay.exception.PluginException;
import com.payline.payment.alipay.utils.Constants;
import com.payline.payment.alipay.utils.PluginUtils;
import com.payline.payment.alipay.utils.http.HttpClient;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseRedirect;
import com.payline.pmapi.logger.LogManager;
import com.payline.pmapi.service.PaymentService;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class PaymentServiceImpl implements PaymentService {
    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);
    private static final HttpClient client = HttpClient.getInstance();

    @Override
    public PaymentResponse paymentRequest(PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse;
        try {
            RequestConfiguration configuration = new RequestConfiguration(paymentRequest.getContractConfiguration(), paymentRequest.getEnvironment(), paymentRequest.getPartnerConfiguration());
            String service, product_code;
            if (PluginUtils.userIsOnPC) {
                service = "create_forex_trade";
                product_code = "NEW_OVERSEAS_SELLER";
            } else {
                service = "create_forex_trade_wap";
                product_code = "NEW_WAP_OVERSEAS_SELLER";
            }
            //On crée un object createForexTrade pour mapper les données de la requête
            CreateForexTrade createForexTrade = CreateForexTrade.CreateForexTradeBuilder
                    .aCreateForexTrade()
                    .withCurrency(paymentRequest.getOrder().getAmount().getCurrency().getCurrencyCode())
                    .withNotifyUrl(paymentRequest.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.NOTIFY_URL).getValue())
                    .withOutTradeNo(paymentRequest.getTransactionId())
                    .withPartner(paymentRequest.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.PARTNER_ID).getValue())
                    .withProductCode(product_code)
                    .withReferUrl("http://google.fr")
                    .withReturnUrl(paymentRequest.getEnvironment().getRedirectionReturnURL())
                    .withService(service)
                    .withSubject(paymentRequest.getSoftDescriptor())
                    .withTotalFee(paymentRequest.getAmount().getAmountInSmallestUnit().toString())
                    .build();
            //On crée un object redirectionRequest avec l'url et les paramètres de la requête POST
            //On récupère les paramètres de la requête avec la signature à partir des champs de l'objet CreateForexTrade
            PaymentResponseRedirect.RedirectionRequest redirectionRequest = PaymentResponseRedirect.RedirectionRequest.RedirectionRequestBuilder.aRedirectionRequest()
                    .withRequestType(PaymentResponseRedirect.RedirectionRequest.RequestType.POST)
                    .withUrl(new URL(paymentRequest.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.ALIPAY_URL)))
                    .withPostFormData(client.getParametersPayment(createForexTrade.getParametersList())).build();

            paymentResponse = PaymentResponseRedirect.PaymentResponseRedirectBuilder.aPaymentResponseRedirect()
                    .withPartnerTransactionId(paymentRequest.getTransactionId())
                    .withRedirectionRequest(redirectionRequest)
                    .build();
        } catch (PluginException e) {
            LOGGER.error("Unexpected plugin error", e);
            paymentResponse = e.toPaymentResponseFailureBuilder().build();
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            LOGGER.error("Unexpected plugin error", e);
            paymentResponse = PaymentResponseFailure.PaymentResponseFailureBuilder
                    .aPaymentResponseFailure()
                    .withFailureCause(FailureCause.INVALID_DATA)
                    .build();
        } catch (RuntimeException e) {
            LOGGER.error("Unexpected plugin error", e);
            paymentResponse = PaymentResponseFailure.PaymentResponseFailureBuilder
                    .aPaymentResponseFailure()
                    .withErrorCode(PluginException.runtimeErrorCode(e))
                    .withFailureCause(FailureCause.INVALID_DATA)
                    .build();
        }
        return paymentResponse;
    }
}
