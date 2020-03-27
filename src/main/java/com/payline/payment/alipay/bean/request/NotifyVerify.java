package com.payline.payment.alipay.bean.request;

import com.payline.payment.alipay.bean.object.ForexService;
import java.util.HashMap;
import java.util.Map;

public class NotifyVerify {
    private String notify_id;
    private String partner;
    private ForexService service;

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
        public NotifyVerify.NotifyVerifyBuilder withService(ForexService service) {
            this.build().service = service;
            return this;
        }
        public NotifyVerify build() {
            return notifyVerify;
        }
    }

    public Map<String, String> getParametersList() {
        //Create parameters list
        Map<String, String>  params = new HashMap<>();
        params.put("notify_id", this.notify_id);
        params.put("partner", this.partner);
        params.put("service", this.service.name());
        return params;
    }
}
