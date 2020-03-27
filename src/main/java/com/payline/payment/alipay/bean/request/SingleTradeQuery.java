package com.payline.payment.alipay.bean.request;

import com.payline.payment.alipay.bean.object.ForexService;

import java.util.HashMap;
import java.util.Map;

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

        public SingleTradeQuery.SingleTradeQueryBuilder withService(ForexService service) {
            this.build().service = service;
            return this;
        }

        public SingleTradeQuery build() {
            return singleTradeQuery;
        }
    }

    public Map<String, String> getParametersList() {
        //Create parameters list
        Map<String, String> params = new HashMap<>();
        params.put("_input_charset", this._input_charset);
        params.put("out_trade_no", this.out_trade_no);
        params.put("partner", this.partner);
        params.put("service", this.service.name());
        return params;
    }
}
