package com.payline.payment.alipay.bean.request;

import com.google.gson.Gson;
import com.payline.payment.alipay.bean.object.ForexService;

import java.nio.charset.StandardCharsets;

abstract class Request {
    private final String inputCharset = StandardCharsets.UTF_8.toString();
    private final String outTradeNo;
    private final String partner;
    private final ForexService service;

    public String getInputCharset() {
        return inputCharset;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public String getPartner() {
        return partner;
    }

    public ForexService getService() {
        return service;
    }


    Request(RequestBuilder<?> builder) {
        this.outTradeNo = builder.outTradeNo;
        this.partner = builder.partner;
        this.service = builder.service;
    }

    public static class RequestBuilder<T extends RequestBuilder> {
        private String outTradeNo;
        private String partner;
        private ForexService service;

        public T withOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
            return (T) this;
        }

        public T withPartner(String partner) {
            this.partner = partner;
            return (T) this;
        }

        public T withService(ForexService service) {
            this.service = service;
            return (T) this;
        }
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
