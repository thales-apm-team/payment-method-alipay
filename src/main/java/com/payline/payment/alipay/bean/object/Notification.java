package com.payline.payment.alipay.bean.object;

import com.google.gson.Gson;

public class Notification {
    private String notify_id;
    private String notify_type;
    private String sign;
    private String trade_no;
    private String buyer_id;
    private String total_fee;
    private String forex_rate;
    private String out_trade_no;
    private String rmb_fee;
    private String seller_id;
    private String currency;
    private String notify_time;
    private String trade_status;
    private String sign_type;

    public String getNotify_id() {
        return notify_id;
    }

    public void setNotify_id(String notify_id) {
        this.notify_id = notify_id;
    }

    public String getNotify_type() {
        return notify_type;
    }

    public void setNotify_type(String notify_type) {
        this.notify_type = notify_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getForex_rate() {
        return forex_rate;
    }

    public void setForex_rate(String forex_rate) {
        this.forex_rate = forex_rate;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getRmb_fee() {
        return rmb_fee;
    }

    public void setRmb_fee(String rmb_fee) {
        this.rmb_fee = rmb_fee;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNotify_time() {
        return notify_time;
    }

    public void setNotify_time(String notify_time) {
        this.notify_time = notify_time;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }
    public static Notification fromJson(String json){
        return new Gson().fromJson(json, Notification.class);
    }
}
