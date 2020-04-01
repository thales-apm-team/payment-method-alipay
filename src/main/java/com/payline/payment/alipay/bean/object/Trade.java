package com.payline.payment.alipay.bean.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.Date;

public class Trade {
    public static XmlMapper xmlMapper = new XmlMapper();

    private String buyer_email;
    private String buyer_id;
    private String discount;
    private String flag_trade_locked;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmt_create;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmt_last_modified_time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmt_payment;
    private String is_total_fee_adjust;
    private String operator_role;
    private String out_trade_no;
    private String payment_type;
    private String price;
    private String quantity;
    private String seller_email;
    private String seller_id;
    private String subject;
    private String to_buyer_fee;
    private String to_seller_fee;
    private String total_fee;
    private String trade_no;

    public String getBuyer_email() {
        return buyer_email;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public String getDiscount() {
        return discount;
    }

    public String getFlag_trade_locked() {
        return flag_trade_locked;
    }

    public Date getGmt_create() {
        return gmt_create;
    }

    public Date getGmt_last_modified_time() {
        return gmt_last_modified_time;
    }

    public Date getGmt_payment() {
        return gmt_payment;
    }

    public String getIs_total_fee_adjust() {
        return is_total_fee_adjust;
    }

    public String getOperator_role() {
        return operator_role;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public String getSubject() {
        return subject;
    }

    public String getTo_buyer_fee() {
        return to_buyer_fee;
    }

    public String getTo_seller_fee() {
        return to_seller_fee;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public TradeStatus getTrade_status() {
        return trade_status;
    }

    public String getUse_coupon() {
        return use_coupon;
    }

    private TradeStatus trade_status;
    private String use_coupon;

    public enum TradeStatus {
        TRADE_FINISHED, WAIT_BUYER_PAY, TRADE_CLOSED
    }
}
