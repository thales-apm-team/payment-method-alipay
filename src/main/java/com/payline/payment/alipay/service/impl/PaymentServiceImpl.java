package com.payline.payment.alipay.service.impl;

import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.payment.alipay.exception.PluginException;
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
import java.util.*;

public class PaymentServiceImpl implements PaymentService {
    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);
    private static final HttpClient client = HttpClient.getInstance();

    @Override
    public PaymentResponse paymentRequest(PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = null;
        try {
            RequestConfiguration configuration = new RequestConfiguration(paymentRequest.getContractConfiguration(), paymentRequest.getEnvironment(), paymentRequest.getPartnerConfiguration());
            LinkedHashMap<String, String> parametersData = client.getParametersPayment(configuration, paymentRequest);
            PaymentResponseRedirect.RedirectionRequest redirectionRequest = PaymentResponseRedirect.RedirectionRequest.RedirectionRequestBuilder.aRedirectionRequest()
                    .withRequestType(PaymentResponseRedirect.RedirectionRequest.RequestType.POST)
                    .withUrl(new URL(paymentRequest.getPartnerConfiguration().getProperty("ALIPAY_URL")))
                    .withPostFormData(parametersData).build();
            paymentResponse = PaymentResponseRedirect.PaymentResponseRedirectBuilder.aPaymentResponseRedirect()
                    .withPartnerTransactionId(paymentRequest.getTransactionId())
                    .withRedirectionRequest(redirectionRequest)
                    .build();
        } catch (PluginException e) {
            paymentResponse = e.toPaymentResponseFailureBuilder().build();
        } catch (MalformedURLException e) {
            paymentResponse = PaymentResponseFailure.PaymentResponseFailureBuilder
                    .aPaymentResponseFailure()
                    .withFailureCause(FailureCause.INTERNAL_ERROR)
                    .build();
        } catch (RuntimeException e) {
            LOGGER.error("Unexpected plugin error", e);
            paymentResponse = PaymentResponseFailure.PaymentResponseFailureBuilder
                    .aPaymentResponseFailure()
                    .withErrorCode(PluginException.runtimeErrorCode(e))
                    .withFailureCause(FailureCause.INTERNAL_ERROR)
                    .build();
        } catch (UnsupportedEncodingException e) {
            paymentResponse = PaymentResponseFailure.PaymentResponseFailureBuilder
                    .aPaymentResponseFailure()
                    .withFailureCause(FailureCause.INTERNAL_ERROR)
                    .build();
        }
        return paymentResponse;
    }
}
