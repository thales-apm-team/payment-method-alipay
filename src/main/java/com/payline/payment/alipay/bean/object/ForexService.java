package com.payline.payment.alipay.bean.object;

import com.google.gson.annotations.SerializedName;

public enum ForexService {
        @SerializedName("create_forex_trade")
        CREATE_FOREX_TRADE,
        @SerializedName("create_forex_trade_wap")
        CREATE_FOREX_TRADE_WAP,
        @SerializedName("single_trade_query")
        SINGLE_TRADE_QUERY,
        @SerializedName("forex_refund")
        FOREX_REFUND,
        @SerializedName("notify_verify")
        NOTIFY_VERIFY
}
