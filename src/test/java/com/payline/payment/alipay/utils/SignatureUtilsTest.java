package com.payline.payment.alipay.utils;

import com.payline.payment.alipay.MockUtils;
import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SignatureUtilsTest {
    private SignatureUtils utils = SignatureUtils.getInstance();

    private RequestConfiguration config = new RequestConfiguration(
            MockUtils.aContractConfiguration()
            ,MockUtils.anEnvironment()
            ,MockUtils.aPartnerConfiguration()
    );

    @Test
    void getSignedParameters() {

        Map<String, String> map = new HashMap<>();
        map.put("foo","FOO");
        map.put("bar","BAR");
        map.put("baz","BAZ");
        String expected = "Nqr1u8l4YYFlbM7VMGrhJ7+tEz0WGQzznc8uBAohvfbed54bA5m4rHJwkUUE0GtqEkEB/3uY4zkeBLHbxDZpyxCkaFCErtxf60UBugegGojTwWe2Af6cmLkeAVQ/oPjA+9yYcXdO3bFkFsIhp+re4Ldyl4zHtnXUj0YBWukE5ul18qBZi4ExcFHeg40eR+AzckvxVWCs1+iaUKOrabVB8AI+q4dy0SQMycoBVUFs6Km6WxGGMyRDW+CuLfzfG8G+4ynjnxnWP1QHBM8++A9WgRWYjW0ZiYlsx61Wl+vhAZXaVDI5FpqHb5L9ZOUU8VvdJsZ6r8Z7eAqTop26HgQIdg==";

        Assertions.assertEquals(expected, utils.getSignedParameters(config,map).get("sign"));
    }

    @Test
    void getVerification() {
        String received = "currency=USD&notify_id=5ac236e4cf7822d205cedcc252b54ebwg1&notify_time=2020-01-14 15:23:12&notify_type=trade_status_sync&out_trade_no=FALCN32YWXN2CL4KFT8&sign=NN2trlV3PKBjZN7KS4oE8PG8WkHFqXIvvQl32fJ2FO9J+HniSuvv36VYPWbARVmodnTvYVkFmR2FB9ioDX0iRTRRSCkz8+ox3ytrlRdRfaeGMSGBuHN6WP/tAHscBbNvjkzyshjTCoXO6MFFg92CR2K50DvtNNUerZa/mx4lA5I=&sign_type=RSA2&total_fee=108.00&trade_no=2020011421001003050502834160&trade_status=TRADE_FINISHED";

        Assertions.assertTrue(utils.getVerification(config, PluginUtils.createMapFromString(received)));
    }

    @Test
    void mapToString() {
        Map<String, String> map = new HashMap<>();
        map.put("foo","FOO");
        map.put("bar","BAR");
        map.put("baz","BAZ");

        Assertions.assertEquals("bar=BAR&baz=BAZ&foo=FOO", utils.mapToString(map));
    }
}