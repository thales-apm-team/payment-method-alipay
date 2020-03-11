package com.payline.payment.alipay.bean.response;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.payline.payment.alipay.bean.object.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AlipayAPIResponseTest {
    private AlipayAPIResponse alipayAPIResponse = new AlipayAPIResponse();
    @Test
    void setXmlMapper() {
        XmlMapper xmlMapper = new XmlMapper();
        alipayAPIResponse.setXmlMapper(xmlMapper);
        Assertions.assertEquals(xmlMapper, alipayAPIResponse.getXmlMapper());
    }

    @Test
    void setIs_success() {
        String isSuccess = "T";
        alipayAPIResponse.setIs_success(isSuccess);
        Assertions.assertEquals(isSuccess, alipayAPIResponse.getIs_success());
    }

    @Test
    void setResponse() {
        Response response = new Response();
        alipayAPIResponse.setResponse(response);
        Assertions.assertEquals(response, alipayAPIResponse.getResponse());
    }

    @Test
    void setSign() {
        String sign = "AAAAAAABBBBBCCCCCEEEEEEDDDDDDFFFFFFF";
        alipayAPIResponse.setSign(sign);
        Assertions.assertEquals(sign, alipayAPIResponse.getSign());
    }

    @Test
    void setSign_type() {
        String signType = "RSA2";
        alipayAPIResponse.setSign_type(signType);
        Assertions.assertEquals(signType, alipayAPIResponse.getSign_type());
    }
}
