package com.payline.payment.alipay.bean.request;

public class CreateForexTrade extends Request{
    String currency;
    String product_code;
    String refer_url;
    String subject;
    String total_fee;
    public static class CreateForexTradeBuilder {
        String currency;
        String product_code;
        String refer_url;
        String subject;
        String total_fee;
        static CreateForexTrade createForexTrade = new CreateForexTrade();
        public CreateForexTrade.CreateForexTradeBuilder withInputCharset(String _input_charset) {
            this.build()._input_charset = _input_charset;
            return this;
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

        public CreateForexTrade.CreateForexTradeBuilder withSignType(String sign_type) {
            this.build().sign_type = sign_type;
            return this;
        }

        public CreateForexTrade.CreateForexTradeBuilder withSign(String sign) {
            this.build().sign = sign;
            return this;
        }

        public CreateForexTrade.CreateForexTradeBuilder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public CreateForexTrade.CreateForexTradeBuilder withProductCode(String product_code) {
            this.product_code = product_code;
            return this;
        }

        public CreateForexTrade.CreateForexTradeBuilder withReferUrl(String refer_url) {
            this.refer_url = refer_url;
            return this;
        }

        public CreateForexTrade.CreateForexTradeBuilder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public CreateForexTrade.CreateForexTradeBuilder withTotalFee(String total_fee) {
            this.total_fee = total_fee;
            return this;
        }

        public CreateForexTrade build() {
            createForexTrade.currency = currency;
            createForexTrade.product_code = product_code;
            createForexTrade.refer_url = refer_url;
            createForexTrade.subject = subject;
            createForexTrade.total_fee = total_fee;
            return createForexTrade;
        }
    }
}
