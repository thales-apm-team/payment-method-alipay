package com.payline.payment.alipay.bean.object;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NotificationTest {
    private Notification notification = new Notification();

    @Test
    void setNotify_idTest()
    {
        String notificationId = "12345";
        notification.setNotify_id(notificationId);
        Assertions.assertEquals(notificationId, notification.getNotify_id());
    }

    @Test
    void setNotify_typeTest()
    {
        String notificationType = "test";
        notification.setNotify_type(notificationType);
        Assertions.assertEquals(notificationType, notification.getNotify_type());
    }

    @Test
    void setSignTest()
    {
        String notificationSign = "testSign";
        notification.setSign(notificationSign);
        Assertions.assertEquals(notificationSign, notification.getSign());
    }

    @Test
    void setTradeNo()
    {
        String notificationTradeNo = "123456789";
        notification.setTrade_no(notificationTradeNo);
        Assertions.assertEquals(notificationTradeNo, notification.getTrade_no());
    }

    @Test
    void setBuyer_Id()
    {
        String notificationBuyerId = "987456123";
        notification.setBuyer_id(notificationBuyerId);
        Assertions.assertEquals(notificationBuyerId, notification.getBuyer_id());
    }

    @Test
    void setTotal_Fee()
    {
        String notificationTotalFee = "10.00";
        notification.setTotal_fee(notificationTotalFee);
        Assertions.assertEquals(notificationTotalFee, notification.getTotal_fee());
    }

    @Test
    void setForex_rate()
    {
        String notificationForexRate = "0.01";
        notification.setForex_rate(notificationForexRate);
        Assertions.assertEquals(notificationForexRate, notification.getForex_rate());
    }

    @Test
    void setOut_trade_no()
    {
        String notificationOutTradeNo = "1956842371";
        notification.setOut_trade_no(notificationOutTradeNo);
        Assertions.assertEquals(notificationOutTradeNo, notification.getOut_trade_no());
    }

    @Test
    void setRmb_fee()
    {
        String notificationRmbFee = "0.14";
        notification.setRmb_fee(notificationRmbFee);
        Assertions.assertEquals(notificationRmbFee, notification.getRmb_fee());
    }

    @Test
    void setSeller_id()
    {
        String notificationSellerId = "PAYLINE14571";
        notification.setSeller_id(notificationSellerId);
        Assertions.assertEquals(notificationSellerId, notification.getSeller_id());
    }

    @Test
    void setCurrency()
    {
        String notificationCurrency = "EUR";
        notification.setCurrency(notificationCurrency);
        Assertions.assertEquals(notificationCurrency, notification.getCurrency());
    }

    @Test
    void setNotify_time()
    {
        String notificationNotifyTime = "11:12:13";
        notification.setNotify_time(notificationNotifyTime);
        Assertions.assertEquals(notificationNotifyTime, notification.getNotify_time());
    }
    @Test
    void setTrade_status()
    {
        String notificationTradeStatus = "TRADE_FINISHED";
        notification.setTrade_status(notificationTradeStatus);
        Assertions.assertEquals(notificationTradeStatus, notification.getTrade_status());
    }
}
