package com.payline.payment.alipay.bean.request;

public class SingleTradeQuery extends Request {
    public static class SingleTradeQueryBuilder
    {
        static SingleTradeQuery singleTradeQuery = new SingleTradeQuery();
        public SingleTradeQuery.SingleTradeQueryBuilder withInputCharset(String _input_charset) {
            this.build()._input_charset = _input_charset;
            return this;
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
        public SingleTradeQuery.SingleTradeQueryBuilder withSignType(String sign_type) {
            this.build().sign_type = sign_type;
            return this;
        }
        public SingleTradeQuery.SingleTradeQueryBuilder withSign(String sign) {
            this.build().sign = sign;
            return this;
        }
        public SingleTradeQuery build() {
            return singleTradeQuery;
        }
    }
}
