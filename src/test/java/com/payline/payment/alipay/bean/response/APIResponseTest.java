package com.payline.payment.alipay.bean.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class APIResponseTest {
    private APIResponse APIResponse = new APIResponse();

    @Test
    void setIs_success() {
        String isSuccess = "T";
        APIResponse.setIs_success(isSuccess);
        Assertions.assertEquals(isSuccess, APIResponse.getIs_success());
    }
}
