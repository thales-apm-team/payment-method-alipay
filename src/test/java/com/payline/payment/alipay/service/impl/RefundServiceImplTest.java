package com.payline.payment.alipay.service.impl;

import com.payline.payment.alipay.MockUtils;
import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.payment.alipay.utils.http.HttpClient;
import com.payline.pmapi.bean.refund.request.RefundRequest;
import com.payline.pmapi.bean.refund.response.impl.RefundResponseFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RefundServiceImplTest {
    @InjectMocks
    private RefundServiceImpl service = new RefundServiceImpl();
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
    void refundRequestOK() {
        //RefundResponseSuccess response = (RefundResponseSuccess) service.refundRequest(MockUtils.aPaylineRefundRequest());
        ///Assertions.assertEquals(RefundResponseSuccess.class, response.getClass());
    }

    @Test
    void refundRequestNotOK() {
        RefundResponseFailure response = (RefundResponseFailure) service.refundRequest(MockUtils.anInvalidPaylineRefundRequest());
        Assertions.assertEquals(RefundResponseFailure.class, response.getClass());
    }

    @Test
    void refundRequestMalformedURLException() {
        RefundRequest.RefundRequestBuilder aPaylineRefundRequest = MockUtils.aPaylineRefundRequestBuilder().withPartnerConfiguration(MockUtils.aPartnerConfigurationMalformedURLException());
        RefundResponseFailure response = (RefundResponseFailure)service.refundRequest(aPaylineRefundRequest.build());
        Assertions.assertEquals(RefundResponseFailure.class, response.getClass());
    }
}