package com.payline.payment.alipay.bean.request;

import com.payline.payment.alipay.bean.object.ForexService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.payline.payment.alipay.bean.object.ForexService.single_trade_query;

public class SingleTradeQueryTest {
    private SingleTradeQuery singleTradeQuery = new SingleTradeQuery();
    @Test
    void get_input_charset() {
        String inputCharset = "UTF-8";
        singleTradeQuery._input_charset = inputCharset;
        Assertions.assertEquals(inputCharset, singleTradeQuery.get_input_charset());
    }

    @Test
    void getOut_trade_no() {
        String outTradeNo = "PAYLINE123456789";
        singleTradeQuery.out_trade_no = outTradeNo;
        Assertions.assertEquals(outTradeNo, singleTradeQuery.getOut_trade_no());
    }

    @Test
    void getPartner() {
        String partnerId = "9874563210";
        singleTradeQuery.partner = partnerId;
        Assertions.assertEquals(partnerId, singleTradeQuery.getPartner());
    }

    @Test
    void getService() {
        ForexService service = single_trade_query;
        singleTradeQuery.service = service;
        Assertions.assertEquals(service, singleTradeQuery.getService());
    }

    @Test
    void getSign_type() {
        String signType = "RSA2";
        singleTradeQuery.sign_type = signType;
        Assertions.assertEquals(signType, singleTradeQuery.getSign_type());
    }

    @Test
    void getSign() {
        String sign = "AAAAAAABBBBBCCCCCEEEEEEDDDDDDFFFFFFF";
        singleTradeQuery.sign = sign;
        Assertions.assertEquals(sign, singleTradeQuery.getSign());
    }
}
