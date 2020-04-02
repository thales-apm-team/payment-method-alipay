package com.payline.payment.alipay.bean.request;

public class EndTransactionNotificationRequest {
    private final String message;
    private final String tagTransactionType;
    private final String tagAmountAuthorized;
    private final String partnerCurrency;
    private final String tagTransactionTime;
    private final String tagTransactionDate;
    private final String tagTransactionYear;
    private final String schemePaymentTime;
    private final String trsPmtCtxCrdhldrPres;
    private final String tagAcqIdentifier;
    private final String ert;
    private final String scheme;
    private final String schemeTransId;
    private final String partnerTransId;
    private final String partnerTransName;
    private final String buyerId;
    private final String partnerId;
    private final String merchantId;
    private final String mccCode;
    private final String storeId;
    private final String messageSenderId;

    private EndTransactionNotificationRequest(Builder builder) {
        this.message = builder.message;
        this.tagTransactionType = builder.tagTransactionType;
        this.tagAmountAuthorized = builder.tagAmountAuthorized;
        this.partnerCurrency = builder.partnerCurrency;
        this.tagTransactionTime = builder.tagTransactionTime;
        this.tagTransactionDate = builder.tagTransactionDate;
        this.tagTransactionYear = builder.tagTransactionYear;
        this.schemePaymentTime = builder.schemePaymentTime;
        this.trsPmtCtxCrdhldrPres = builder.trsPmtCtxCrdhldrPres;
        this.tagAcqIdentifier = builder.tagAcqIdentifier;
        this.ert = builder.ert;
        this.scheme = builder.scheme;
        this.schemeTransId = builder.schemeTransId;
        this.partnerTransId = builder.partnerTransId;
        this.partnerTransName = builder.partnerTransName;
        this.buyerId = builder.buyerId;
        this.partnerId = builder.partnerId;
        this.merchantId = builder.merchantId;
        this.mccCode = builder.mccCode;
        this.storeId = builder.storeId;
        this.messageSenderId = builder.messageSenderId;
    }

    public static class Builder {
        String message;
        String tagTransactionType;
        String tagAmountAuthorized;
        String partnerCurrency;
        String tagTransactionTime;
        String tagTransactionDate;
        String tagTransactionYear;
        String schemePaymentTime;
        String trsPmtCtxCrdhldrPres;
        String tagAcqIdentifier;
        String ert;
        String scheme;
        String schemeTransId;
        String partnerTransId;
        String partnerTransName;
        String buyerId;
        String partnerId;
        String merchantId;
        String mccCode;
        String storeId;
        String messageSenderId;

        private Builder() {

        }

        public static Builder anEndTransactionNotificationRequest() {
            return new Builder();
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withTagTransactionType(String tagTransactionType) {
            this.tagTransactionType = tagTransactionType;
            return this;
        }

        public Builder withTagAmountAuthorized(String tagAmountAuthorized) {
            this.tagAmountAuthorized = tagAmountAuthorized;
            return this;
        }

        public Builder withPartnerCurrency(String partnerCurrency) {
            this.partnerCurrency = partnerCurrency;
            return this;
        }

        public Builder withTagTransactionTime(String tagTransactionTime) {
            this.tagTransactionTime = tagTransactionTime;
            return this;
        }

        public Builder withTagTransactionDate(String tagTransactionDate) {
            this.tagTransactionDate = tagTransactionDate;
            return this;
        }

        public Builder withTagTransactionYear(String tagTransactionYear) {
            this.tagTransactionYear = tagTransactionYear;
            return this;
        }

        public Builder withSchemePaymentTime(String schemePaymentTime) {
            this.schemePaymentTime = schemePaymentTime;
            return this;
        }

        public Builder withTrsPmtCtxCrdhldrPres(String trsPmtCtxCrdhldrPres) {
            this.trsPmtCtxCrdhldrPres = trsPmtCtxCrdhldrPres;
            return this;
        }

        public Builder withTagAcqIdentifier(String tagAcqIdentifier) {
            this.tagAcqIdentifier = tagAcqIdentifier;
            return this;
        }

        public Builder withErt(String ert) {
            this.ert = ert;
            return this;
        }

        public Builder withScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        public Builder withSchemeTransId(String schemeTransId) {
            this.schemeTransId = schemeTransId;
            return this;
        }

        public Builder withPartnerTransId(String partnerTransId) {
            this.partnerTransId = partnerTransId;
            return this;
        }

        public Builder withPartnerTransName(String partnerTransName) {
            this.partnerTransName = partnerTransName;
            return this;
        }

        public Builder withBuyerId(String buyerId) {
            this.buyerId = buyerId;
            return this;
        }

        public Builder withPartnerId(String partnerId) {
            this.partnerId = partnerId;
            return this;
        }

        public Builder withMerchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        public Builder withMccCode(String mccCode) {
            this.mccCode = mccCode;
            return this;
        }

        public Builder withStoreId(String storeId) {
            this.storeId = storeId;
            return this;
        }

        public Builder withMessageSenderId(String messageSenderId) {
            this.messageSenderId = messageSenderId;
            return this;
        }

        public EndTransactionNotificationRequest build() {
            return new EndTransactionNotificationRequest(this);
        }
    }
}
