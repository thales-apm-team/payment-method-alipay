package com.payline.payment.alipay.bean.request;

import com.payline.payment.alipay.bean.object.ForexService;
import com.payline.payment.alipay.utils.PluginUtils;

import java.util.HashMap;
import java.util.Map;

public class CreateForexTrade extends Request {
    String currency;
    String notify_url;
    String product_code;
    String refer_url;
    String return_url;
    String subject;
    String total_fee;

    public static class CreateForexTradeBuilder {
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

        public CreateForexTrade.CreateForexTradeBuilder withService(ForexService service) {
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

    public Map<String, String> getParametersList() {
        Map<String, String> params = new HashMap<>();
        params.put("_input_charset", this._input_charset);
        params.put("currency", this.currency);
        params.put("notify_url", this.notify_url);
        params.put("out_trade_no", this.out_trade_no);
        params.put("partner", this.partner);
        params.put("product_code", this.product_code);
        // only non mandatory field
        if (!PluginUtils.isEmpty(this.refer_url)) {
            params.put("refer_url", this.refer_url);
        }
        params.put("return_url", this.return_url);
        params.put("service", this.service.name());
        params.put("subject", this.subject);
        params.put("total_fee", this.total_fee);
        return params;
    }
}
