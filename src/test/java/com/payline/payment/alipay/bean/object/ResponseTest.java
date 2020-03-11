package com.payline.payment.alipay.bean.object;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResponseTest {
    private Response response = new Response();
    @Test
    void setXmlMapper() {
        XmlMapper xmlMapper = new XmlMapper();
        response.setXmlMapper(xmlMapper);
        Assertions.assertEquals(xmlMapper, response.getXmlMapper());
    }

    @Test
    void setTrade() {
        Trade trade = new Trade();
        response.setTrade(trade);
        Assertions.assertEquals(trade, response.getTrade());
    }
}
