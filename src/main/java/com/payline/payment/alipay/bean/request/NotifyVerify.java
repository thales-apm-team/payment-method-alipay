package com.payline.payment.alipay.bean.request;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class NotifyVerify {
    String notify_id;
    String partner;
    String service;
    public static class NotifyVerifyBuilder
    {
        static NotifyVerify notifyVerify = new NotifyVerify();
        public static NotifyVerify.NotifyVerifyBuilder aNotifyVerify() {
            return new NotifyVerify.NotifyVerifyBuilder();
        }
        public NotifyVerify.NotifyVerifyBuilder withNotifyId(String notify_id) {
            this.build().notify_id = notify_id;
            return this;
        }
        public NotifyVerify.NotifyVerifyBuilder withPartner(String partner) {
            this.build().partner = partner;
            return this;
        }
        public NotifyVerify.NotifyVerifyBuilder withService(String service) {
            this.build().service = service;
            return this;
        }
        public NotifyVerify build() {
            return notifyVerify;
        }
    }

    public ArrayList<NameValuePair> getParametersList() {
        //Create parameters list
        ArrayList<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("notify_id", this.notify_id));
        list.add(new BasicNameValuePair("partner", this.partner));
        list.add(new BasicNameValuePair("service", this.service));
        return list;
    }
}
