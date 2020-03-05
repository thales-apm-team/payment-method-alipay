package com.payline.payment.alipay.service.impl;
import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.payment.alipay.MockUtils;
import com.payline.payment.alipay.utils.PluginUtils;
import com.payline.payment.alipay.utils.http.HttpClient;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseRedirect;
import com.payline.pmapi.bean.refund.response.RefundResponse;
import com.payline.pmapi.bean.refund.response.impl.RefundResponseFailure;
import com.payline.pmapi.bean.refund.response.impl.RefundResponseSuccess;
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
        //PluginUtils.SwitchDevice(service, true);
        RefundResponseSuccess response = (RefundResponseSuccess) service.refundRequest(MockUtils.aPaylineRefundRequest());
        Assertions.assertEquals(RefundResponseSuccess.class, response.getClass());
    }

    @Test
    void refundRequestNotOK() {
        //PluginUtils.SwitchDevice(service, true);
        RefundResponseFailure response = (RefundResponseFailure) service.refundRequest(MockUtils.anInvalidPaylineRefundRequest());
        Assertions.assertEquals(RefundResponseFailure.class, response.getClass());
    }
}