package com.payline.payment.alipay.bean.request;

import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class CreateForexTrade extends Request {
    String currency;
    String notify_url;
    String product_code;
    String refer_url;
    String return_url;
    String subject;
    String total_fee;
    public static class CreateForexTradeBuilder
    {
        static CreateForexTrade createForexTrade = new CreateForexTrade();
        public static CreateForexTradeBuilder aCreateForexTrade() {
            return new CreateForexTradeBuilder();
        }
        public CreateForexTrade.CreateForexTradeBuilder withOutTradeNo(String out_trade_no) {
            this.build().out_trade_no = out_trade_no;
            return this;
        }
        public CreateForexTrade.CreateForexTradeBuilder withPartner(String partner) {
            this.build().partner = partner;
            return this;
        }
        public CreateForexTrade.CreateForexTradeBuilder withService(String service) {
            this.build().service = service;
            return this;
        }
        public CreateForexTrade.CreateForexTradeBuilder withCurrency(String currency) {
            this.build().currency = currency;
            return this;
        }
        public CreateForexTrade.CreateForexTradeBuilder withNotifyUrl(String notify_url) {
            this.build().notify_url = notify_url;
            return this;
        }
        public CreateForexTrade.CreateForexTradeBuilder withProductCode(String product_code) {
            this.build().product_code = product_code;
            return this;
        }
        public CreateForexTrade.CreateForexTradeBuilder withReferUrl(String refer_url) {
            this.build().refer_url = refer_url;
            return this;
        }
        public CreateForexTrade.CreateForexTradeBuilder withReturnUrl(String return_url) {
            this.build().return_url = return_url;
            return this;
        }
        public CreateForexTrade.CreateForexTradeBuilder withSubject(String subject) {
            this.build().subject = subject;
            return this;
        }
        public CreateForexTrade.CreateForexTradeBuilder withTotalFee(String total_fee) {
            this.build().total_fee = total_fee;
            return this;
        }
        public CreateForexTrade build() {
            return createForexTrade;
        }
    }

    public ArrayList<NameValuePair> getParametersList() {
        ArrayList<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("_input_charset", this._input_charset));
        list.add(new BasicNameValuePair("currency", this.currency));
        list.add(new BasicNameValuePair("notify_url", this.notify_url));
        list.add(new BasicNameValuePair("out_trade_no", this.out_trade_no));
        list.add(new BasicNameValuePair("partner", this.partner));
        list.add(new BasicNameValuePair("product_code", this.product_code));
        list.add(new BasicNameValuePair("refer_url", "https://www.google.fr"));
        list.add(new BasicNameValuePair("return_url", this.return_url));
        list.add(new BasicNameValuePair("service", this.service));
        list.add(new BasicNameValuePair("subject", this.subject));
        list.add(new BasicNameValuePair("total_fee", this.total_fee));
        return list;
    }
}
