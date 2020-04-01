package com.payline.payment.alipay.service.impl;

import com.payline.payment.alipay.MockUtils;
import com.payline.payment.alipay.bean.response.APIResponse;
import com.payline.payment.alipay.exception.PluginException;
import com.payline.payment.alipay.utils.http.HttpClient;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.refund.response.RefundResponse;
import com.payline.pmapi.bean.refund.response.impl.RefundResponseFailure;
import com.payline.pmapi.bean.refund.response.impl.RefundResponseSuccess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;

class RefundServiceImplTest {
    @InjectMocks
    private RefundServiceImpl service = new RefundServiceImpl();
    @Mock
    private HttpClient client = HttpClient.getInstance();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void refundRequestOK() {
        String xmlOk = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n" +
                "<alipay>\n" +
                "    <is_success>T</is_success>\n" +
                "</alipay>";
        APIResponse apiResponse = APIResponse.fromXml(xmlOk);
        Mockito.doReturn(apiResponse).when(client).getRefund(any(), any());

        RefundResponse response = service.refundRequest(MockUtils.aPaylineRefundRequest());
        Assertions.assertEquals(RefundResponseSuccess.class, response.getClass());

        RefundResponseSuccess responseSuccess = (RefundResponseSuccess) response;
        Assertions.assertEquals("PAYLINE20200116103352", responseSuccess.getPartnerTransactionId());
        Assertions.assertEquals("200", responseSuccess.getStatusCode());
    }

    @Test
    void refundRequestNotOK() {
        String xmlKo = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n" +
                "<alipay>\n" +
                "    <is_success>F</is_success>\n" +
                "    <error>ILLEGAL_ARGUMENT</error>\n" +
                "    <sign>bXZsM4/TKsISBTHbiqgyQ6q8M30MNI+evD7JFg==</sign>\n" +
                "    <sign_type>RSA2</sign_type>\n" +
                "</alipay>";
        APIResponse apiResponse = APIResponse.fromXml(xmlKo);
        Mockito.doReturn(apiResponse).when(client).getRefund(any(), any());

        RefundResponse response = service.refundRequest(MockUtils.anInvalidPaylineRefundRequest());
        Assertions.assertEquals(RefundResponseFailure.class, response.getClass());

        RefundResponseFailure responseFailure = (RefundResponseFailure) response;
        Assertions.assertEquals("ILLEGAL_ARGUMENT", responseFailure.getErrorCode());
        Assertions.assertEquals(FailureCause.INVALID_DATA, responseFailure.getFailureCause());
    }

    @Test
    void refundRequestPluginException() {
        PluginException e = new PluginException("foo", FailureCause.REFUSED);
        Mockito.doThrow(e).when(client).getRefund(any(), any());

        RefundResponse response = service.refundRequest(MockUtils.anInvalidPaylineRefundRequest());
        Assertions.assertEquals(RefundResponseFailure.class, response.getClass());

        RefundResponseFailure responseFailure = (RefundResponseFailure) response;
        Assertions.assertEquals("foo", responseFailure.getErrorCode());
        Assertions.assertEquals(FailureCause.REFUSED, responseFailure.getFailureCause());
    }

    @Test
    void refundRequestRuntimeException() {
        NullPointerException e = new NullPointerException("foo");
        Mockito.doThrow(e).when(client).getRefund(any(), any());

        RefundResponse response = service.refundRequest(MockUtils.anInvalidPaylineRefundRequest());
        Assertions.assertEquals(RefundResponseFailure.class, response.getClass());

        RefundResponseFailure responseFailure = (RefundResponseFailure) response;
        Assertions.assertEquals("plugin error: NullPointerException: foo", responseFailure.getErrorCode());
        Assertions.assertEquals(FailureCause.INTERNAL_ERROR, responseFailure.getFailureCause());
    }
}