package com.payline.payment.alipay.bean.request;

import com.payline.payment.alipay.bean.object.ForexService;

import java.util.HashMap;
import java.util.Map;

public class ForexRefund extends Request {
    private String currency;
    private String gmt_return;
    private String is_sync;
    private String out_return_no;
    private String product_code;
    private String return_amount;
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
        public ForexRefund.ForexRefundBuilder withService(ForexService service) {
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

    public Map<String, String> getParametersList() {
        //Create parameters list
        Map<String, String>  params = new HashMap<>();
        params.put("_input_charset", this._input_charset);
        params.put("currency", this.currency);
        params.put("gmt_return", this.gmt_return);
        params.put("is_sync", this.is_sync);
        params.put("out_return_no", this.out_return_no);
        params.put("out_trade_no", this.out_trade_no);
        params.put("partner", this.partner);
        params.put("product_code", this.product_code);
        params.put("return_amount", this.return_amount);
        params.put("service", this.service.name());
        return params;
    }
}
