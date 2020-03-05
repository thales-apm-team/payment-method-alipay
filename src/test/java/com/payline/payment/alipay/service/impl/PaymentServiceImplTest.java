package com.payline.payment.alipay.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.payment.alipay.exception.PluginException;
import com.payline.payment.alipay.MockUtils;
import com.payline.payment.alipay.utils.PluginUtils;
import com.payline.payment.alipay.utils.http.HttpClient;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseRedirect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;

class PaymentServiceImplTest {
    @InjectMocks
    private PaymentServiceImpl service = new PaymentServiceImpl();
    @Mock
    private HttpClient client = HttpClient.getInstance();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        RequestConfiguration configuration = new RequestConfiguration(
                MockUtils.aPaylinePaymentRequest().getContractConfiguration()
                , MockUtils.aPaylinePaymentRequest().getEnvironment()
                , MockUtils.aPaylinePaymentRequest().getPartnerConfiguration());
    }

    @Test
    void paymentRequestPCOK() {
        PluginUtils.SwitchDevice(service, true);
        PaymentResponseRedirect response = (PaymentResponseRedirect) service.paymentRequest(MockUtils.aPaylinePaymentRequest());
        System.out.println(response.getRedirectionRequest().getParameters());
        System.out.println(response.getRedirectionRequest().getUrl() + "?" + client.preSigningString(response.getRedirectionRequest().getParameters()));
        Assertions.assertEquals(PaymentResponseRedirect.class, response.getClass());
    }

    @Test
    void paymentRequestMobileOK() {
        PluginUtils.SwitchDevice(service, false);
        PaymentResponseRedirect response = (PaymentResponseRedirect) service.paymentRequest(MockUtils.aPaylinePaymentRequest());
        System.out.println(response.getRedirectionRequest().getParameters());
        System.out.println(response.getRedirectionRequest().getUrl() + "?" + client.preSigningString(response.getRedirectionRequest().getParameters()));
        Assertions.assertEquals(PaymentResponseRedirect.class, response.getClass());
    }
}