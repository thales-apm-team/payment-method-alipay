package com.payline.payment.alipay.bean.object;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResponseTest {
    private Response response = new Response();
    @Test
    void setTrade() {
        Trade trade = new Trade();
        response.setTrade(trade);
        Assertions.assertEquals(trade, response.getTrade());
    }
}
