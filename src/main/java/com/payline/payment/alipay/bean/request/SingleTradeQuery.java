package com.payline.payment.alipay.bean.request;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class SingleTradeQuery extends Request {
    public static class SingleTradeQueryBuilder {
        static SingleTradeQuery singleTradeQuery = new SingleTradeQuery();

        public static SingleTradeQuery.SingleTradeQueryBuilder aSingleTradeQuery() {
            return new SingleTradeQuery.SingleTradeQueryBuilder();
        }

        public SingleTradeQuery.SingleTradeQueryBuilder withOutTradeNo(String out_trade_no) {
            this.build().out_trade_no = out_trade_no;
            return this;
        }

        public SingleTradeQuery.SingleTradeQueryBuilder withPartner(String partner) {
            this.build().partner = partner;
            return this;
        }

        public SingleTradeQuery.SingleTradeQueryBuilder withService(String service) {
            this.build().service = service;
            return this;
        }

        public SingleTradeQuery build() {
            return singleTradeQuery;
        }
    }

    public ArrayList<NameValuePair> getParametersList() {
        //Create parameters list
        ArrayList<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("_input_charset", this._input_charset));
        list.add(new BasicNameValuePair("out_trade_no", this.out_trade_no));
        list.add(new BasicNameValuePair("partner", this.partner));
        list.add(new BasicNameValuePair("service", this.service));
        return list;
    }
}
