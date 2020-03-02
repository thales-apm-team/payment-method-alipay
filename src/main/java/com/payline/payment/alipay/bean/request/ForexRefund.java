package com.payline.payment.alipay.bean.request;

import com.google.gson.Gson;

public class ForexRefund extends Request{
    String currency;
    String gmt_return;
    String is_sync;
    String out_return_no;
    String product_code;
    String return_amount;

    public static class ForexRefundBuilder {
        String currency;
        String gmt_return;
        String is_sync;
        String out_return_no;
        String product_code;
        String return_amount;
        static ForexRefund forexRefund = new ForexRefund();
        public ForexRefund.ForexRefundBuilder withInputCharset(String _input_charset) {
            this.build()._input_charset = _input_charset;
            return this;
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

        public ForexRefund.ForexRefundBuilder withSignType(String sign_type) {
            this.build().sign_type = sign_type;
            return this;
        }

        public ForexRefund.ForexRefundBuilder withSign(String sign) {
            this.build().sign = sign;
            return this;
        }

        public ForexRefund.ForexRefundBuilder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public ForexRefund.ForexRefundBuilder withGmtReturn(String gmt_return) {
            this.gmt_return = gmt_return;
            return this;
        }

        public ForexRefund.ForexRefundBuilder withIsSync(String is_sync) {
            this.is_sync = is_sync;
            return this;
        }

        public ForexRefund.ForexRefundBuilder withOutReturnNo(String out_return_no) {
            this.out_return_no = out_return_no;
            return this;
        }

        public ForexRefund.ForexRefundBuilder withProductCode(String product_code) {
            this.product_code = product_code;
            return this;
        }

        public ForexRefund.ForexRefundBuilder withReturnAmount(String return_amount) {
            this.return_amount = return_amount;
            return this;
        }

        public ForexRefund build() {
            forexRefund.currency = currency;
            forexRefund.gmt_return = gmt_return;
            forexRefund.is_sync = is_sync;
            forexRefund.out_return_no = out_return_no;
            forexRefund.product_code = product_code;
            forexRefund.return_amount = return_amount;
            return forexRefund;
        }
    }
}
