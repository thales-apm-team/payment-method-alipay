package com.payline.payment.alipay.bean.request;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.nio.charset.StandardCharsets;

abstract class Request {
    protected String _input_charset = StandardCharsets.UTF_8.toString();
    protected String out_trade_no;
    protected String partner;
    protected String service;
    protected String sign_type = "RSA2";
    protected String sign;

    public String get_input_charset() {
        return _input_charset;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public String getPartner() {
        return partner;
    }

    public String getService() {
        return service;
    }

    public String getSign_type() {
        return sign_type;
    }

    public String getSign() {
        return sign;
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
