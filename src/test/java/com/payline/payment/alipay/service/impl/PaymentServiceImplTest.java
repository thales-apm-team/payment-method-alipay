package com.payline.payment.alipay.service.impl;

import com.payline.payment.alipay.MockUtils;
import com.payline.payment.alipay.utils.PluginUtils;
import com.payline.payment.alipay.utils.http.HttpClient;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseRedirect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class PaymentServiceImplTest {
    @InjectMocks
    private PaymentServiceImpl service = new PaymentServiceImpl();
    @Mock
    private HttpClient client = HttpClient.getInstance();

    @Test
    void paymentRequestPCOK() {
        PluginUtils.SwitchDevice(true);
        PaymentResponseRedirect response = (PaymentResponseRedirect) service.paymentRequest(MockUtils.aPaylinePaymentRequest());
        System.out.println(response.getRedirectionRequest().getParameters());
        System.out.println(response.getRedirectionRequest().getUrl() + "?" + MockUtils.generateParametersString(response.getRedirectionRequest().getParameters()));
        Assertions.assertEquals(PaymentResponseRedirect.class, response.getClass());
    }

    @Test
    void paymentRequestMobileOK() {
        PluginUtils.SwitchDevice(false);
        PaymentResponseRedirect response = (PaymentResponseRedirect) service.paymentRequest(MockUtils.aPaylinePaymentRequest());
        System.out.println(response.getRedirectionRequest().getParameters());
        System.out.println(response.getRedirectionRequest().getUrl() + "?" + MockUtils.generateParametersString(response.getRedirectionRequest().getParameters()));
        Assertions.assertEquals(PaymentResponseRedirect.class, response.getClass());
    }

    @Test
    void paymentRequestMalformedURLException() {
            PaymentRequest.Builder aPaylinePaymentRequest = MockUtils.aPaylinePaymentRequestBuilder().withPartnerConfiguration(MockUtils.aPartnerConfigurationMalformedURLException());
            PaymentResponseFailure response = (PaymentResponseFailure)service.paymentRequest(aPaylinePaymentRequest.build());
            Assertions.assertEquals(PaymentResponseFailure.class, response.getClass());
    }

    @Test
    void paymentRequestUnsupportedEncodingException() {
        PaymentRequest.Builder aPaylinePaymentRequest = MockUtils.aPaylinePaymentRequestBuilder().withContractConfiguration(MockUtils.aContractConfigurationUnsupportedEncodingException());
        PaymentResponseFailure response = (PaymentResponseFailure)service.paymentRequest(aPaylinePaymentRequest.build());
        Assertions.assertEquals(PaymentResponseFailure.class, response.getClass());
    }
}