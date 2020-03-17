package com.payline.payment.alipay.bean.request;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Date;

public class ForexRefund extends Request {
    String currency;
    String gmt_return;
    String is_sync;
    String out_return_no;
    String product_code;
    String return_amount;
    public static class ForexRefundBuilder
    {
        static ForexRefund forexRefund = new ForexRefund();
        public static ForexRefund.ForexRefundBuilder aForexRefund() {
            return new ForexRefund.ForexRefundBuilder();
        }
        public ForexRefund.ForexRefundBuilder withOutTradeNo(String out_trade_no) {
            this.build().out_trade_no = out_trade_no;
            return this;
        }
        public ForexRefund.ForexRefundBuilder withPartner(String partner) {
            this.build().partner = partner;
            return this;
        }
        public ForexRefund.ForexRefundBuilder withService(String service) {
            this.build().service = service;
            return this;
        }
        public ForexRefund.ForexRefundBuilder withCurrency(String currency) {
            this.build().currency = currency;
            return this;
        }
        public ForexRefund.ForexRefundBuilder withGmtReturn(String gmt_return) {
            this.build().gmt_return = gmt_return;
            return this;
        }
        public ForexRefund.ForexRefundBuilder withIsSync(String is_sync) {
            this.build().is_sync = is_sync;
            return this;
        }
        public ForexRefund.ForexRefundBuilder withOutReturnNo(String out_return_no) {
            this.build().out_return_no = out_return_no;
            return this;
        }
        public ForexRefund.ForexRefundBuilder withProductCode(String product_code) {
            this.build().product_code = product_code;
            return this;
        }
        public ForexRefund.ForexRefundBuilder withReturnAmount(String return_amount) {
            this.build().return_amount = return_amount;
            return this;
        }
        public ForexRefund build() {
            return forexRefund;
        }
    }

    public ArrayList<NameValuePair> getParametersList() {
        //Create parameters list
        ArrayList<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("_input_charset", this._input_charset));
        list.add(new BasicNameValuePair("currency", this.currency));
        list.add(new BasicNameValuePair("gmt_return", this.gmt_return));
        list.add(new BasicNameValuePair("is_sync", this.is_sync));
        list.add(new BasicNameValuePair("out_return_no", this.out_return_no));
        list.add(new BasicNameValuePair("out_trade_no", this.out_trade_no));
        list.add(new BasicNameValuePair("partner", this.partner));
        list.add(new BasicNameValuePair("product_code", this.product_code));
        list.add(new BasicNameValuePair("return_amount", this.return_amount));
        list.add(new BasicNameValuePair("service", this.service));
        return list;
    }
}
