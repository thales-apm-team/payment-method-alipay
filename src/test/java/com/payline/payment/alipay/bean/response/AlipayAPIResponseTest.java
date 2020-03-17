package com.payline.payment.alipay.bean.response;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.payline.payment.alipay.bean.object.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AlipayAPIResponseTest {
    private AlipayAPIResponse alipayAPIResponse = new AlipayAPIResponse();

    @Test
    void setIs_success() {
        String isSuccess = "T";
        alipayAPIResponse.setIs_success(isSuccess);
        Assertions.assertEquals(isSuccess, alipayAPIResponse.getIs_success());
    }
}
