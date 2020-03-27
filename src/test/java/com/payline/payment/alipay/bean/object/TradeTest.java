package com.payline.payment.alipay.bean.object;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.payline.payment.alipay.bean.object.Trade.TradeStatus.TRADE_CLOSED;

class TradeTest {
    private Trade trade = new Trade();
//    @Test
//    void setXmlMapper() {
//        XmlMapper xmlMapper = new XmlMapper();
//        trade.setXmlMapper(xmlMapper);
//        Assertions.assertEquals(xmlMapper, trade.getXmlMapper());
//    }
//
//    @Test
//    void setBuyer_email() {
//        String email = "test@test.com";
//        trade.setBuyer_email(email);
//        Assertions.assertEquals(email, trade.getBuyer_email());
//    }
//
//    @Test
//    void setBuyer_id() {
//        String buyerId = "197382465";
//        trade.setBuyer_id(buyerId);
//        Assertions.assertEquals(buyerId, trade.getBuyer_id());
//    }
//
//    @Test
//    void setDiscount() {
//        String discount = "0.5";
//        trade.setDiscount(discount);
//        Assertions.assertEquals(discount, trade.getDiscount());
//    }
//
//    @Test
//    void setFlag_trade_locked() {
//        String flagTradeLocked = "FLAG";
//        trade.setFlag_trade_locked(flagTradeLocked);
//        Assertions.assertEquals(flagTradeLocked, trade.getFlag_trade_locked());
//    }
//
//    @Test
//    void setGmt_create() throws Exception{
//        Date gmtCreate = new SimpleDateFormat().parse("11:12:13");
//        trade.setGmt_create(gmtCreate);
//        Assertions.assertEquals(gmtCreate, trade.getGmt_create());
//    }
//
//    @Test
//    void setGmt_last_modified_time() throws Exception{
//        Date gmtCreate = new SimpleDateFormat().parse("11:12:13");
//        trade.setGmt_last_modified_time(gmtCreate);
//        Assertions.assertEquals(gmtCreate, trade.getGmt_last_modified_time());
//    }
//
//
//    @Test
//    void setIs_total_fee_adjust() {
//        String totalFeeAdjust = "0.02";
//        trade.setIs_total_fee_adjust(totalFeeAdjust);
//        Assertions.assertEquals(totalFeeAdjust, trade.getIs_total_fee_adjust());
//    }
//
//    @Test
//    void setOperator_role() {
//        String operatorRole = "SELLER";
//        trade.setOperator_role(operatorRole);
//        Assertions.assertEquals(operatorRole, trade.getOperator_role());
//    }
//
//    @Test
//    void setOut_trade_no() {
//        String tradeNo = "PAYLINE123466789";
//        trade.setOut_trade_no(tradeNo);
//        Assertions.assertEquals(tradeNo, trade.getOut_trade_no());
//    }
//
//    @Test
//    void setPayment_type() {
//        String paymentType = "CREDIT";
//        trade.setPayment_type(paymentType);
//        Assertions.assertEquals(paymentType, trade.getPayment_type());
//    }
//
//    @Test
//    void setPrice() {
//        String price = "10.00";
//        trade.setPrice(price);
//        Assertions.assertEquals(price, trade.getPrice());
//    }
//
//    @Test
//    void setQuantity() {
//        String quantity = "2";
//        trade.setQuantity(quantity);
//        Assertions.assertEquals(quantity, trade.getQuantity());
//    }
//
//    @Test
//    void setSeller_email() {
//        String sellerEmail = "seller@test.com";
//        trade.setSeller_email(sellerEmail);
//        Assertions.assertEquals(sellerEmail, trade.getSeller_email());
//    }
//
//    @Test
//    void setSeller_id() {
//        String sellerId = "123";
//        trade.setSeller_id(sellerId);
//        Assertions.assertEquals(sellerId, trade.getSeller_id());
//    }
//
//    @Test
//    void setSubject() {
//        String subject = "subject";
//        trade.setSubject(subject);
//        Assertions.assertEquals(subject, trade.getSubject());
//    }
//
//    @Test
//    void setTo_buyer_fee() {
//        String toBuyerFee = "2.0";
//        trade.setTo_buyer_fee(toBuyerFee);
//        Assertions.assertEquals(toBuyerFee, trade.getTo_buyer_fee());
//    }
//
//    @Test
//    void setTo_seller_fee() {
//        String toSellerFee = "2.0";
//        trade.setTo_seller_fee(toSellerFee);
//        Assertions.assertEquals(toSellerFee, trade.getTo_seller_fee());
//    }
//
//    @Test
//    void setTotal_fee() {
//        String totalFee = "15.00";
//        trade.setTotal_fee(totalFee);
//        Assertions.assertEquals(totalFee, trade.getTotal_fee());
//    }
//
//    @Test
//    void setTrade_no() {
//        String tradeNo = "PAYLINE123456";
//        trade.setTrade_no(tradeNo);
//        Assertions.assertEquals(tradeNo, trade.getTrade_no());
//    }
//
//    @Test
//    void setTrade_status() {
//        Trade.TradeStatus tradeStatus = TRADE_CLOSED;
//        trade.setTrade_status(tradeStatus);
//        Assertions.assertEquals(tradeStatus, trade.getTrade_status());
//    }
//
//    @Test
//    void setUse_coupon() {
//        String useCoupon = "COUP2020";
//        trade.setUse_coupon(useCoupon);
//        Assertions.assertEquals(useCoupon, trade.getUse_coupon());
//    }
}