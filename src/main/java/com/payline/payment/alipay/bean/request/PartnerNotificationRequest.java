package com.payline.payment.alipay.bean.request;

public class PartnerNotificationRequest {
    private String message;
    private String tagTransactionType;
    private String tagAmountAuthorized;
    private String partnerCurrency;
    private String exchangeRate;
    private String schemeCurrency;
    private String schemeCurrencyAmount;
    private String tagTransactionTime;
    private String tagTransactionDate;
    private String tagTransactionYear;
    private String schemePaymentTime;
    private String trsPmtCtxCrdhldrPres;
    private String trsPmtCtxAttnd;
    private String tagAcqIdentifier;
    private String Idac;
    private String itp;
    private String posLogNum;
    private String posArchitecture;
    private String merCompanionRef;
    private String ert;
    private String scheme;
    private String schemetransId;
    private String partnerTransId;
    private String partnerTransName;
    private String buyerId;
    private String partnerId;
    private String merchantId;
    private String merchantName;
    private String mccCode;
    private String storeId;
    private String storeName;
    private String messageSenderId;
    private String libelle;

    private PartnerNotificationRequest(Builder builder)
    {
        message = builder.message;
        tagTransactionType = builder.tagTransactionType;
        tagAmountAuthorized = builder.tagAmountAuthorized;
        partnerCurrency = builder.partnerCurrency;
        exchangeRate = builder.exchangeRate;
        schemeCurrency = builder.schemeCurrency;
        schemeCurrencyAmount = builder.schemeCurrencyAmount;
        tagTransactionTime = builder.tagTransactionTime;
        tagTransactionDate = builder.tagTransactionDate;
        tagTransactionYear = builder.tagTransactionYear;
        schemePaymentTime = builder.schemePaymentTime;
        trsPmtCtxCrdhldrPres = builder.trsPmtCtxCrdhldrPres;
        trsPmtCtxAttnd = builder.trsPmtCtxAttnd;
        tagAcqIdentifier = builder.tagAcqIdentifier;
        Idac = builder.Idac;
        itp = builder.itp;
        posLogNum = builder.posLogNum;
        posArchitecture = builder.posArchitecture;
        merCompanionRef = builder.merCompanionRef;
        ert = builder.ert;
        scheme = builder.scheme;
        schemetransId = builder.schemetransId;
        partnerTransId = builder.partnerTransId;
        partnerTransName = builder.partnerTransName;
        buyerId = builder.buyerId;
        partnerId = builder.partnerId;
        merchantId = builder.merchantId;
        merchantName = builder.merchantName;
        mccCode = builder.mccCode;
        storeId = builder.storeId;
        storeName = builder.storeName;
        messageSenderId = builder.messageSenderId;
        libelle = builder.libelle;
    }
    public static class Builder
    {
        private String message;
        private String tagTransactionType;
        private String tagAmountAuthorized;
        private String partnerCurrency;
        private String exchangeRate;
        private String schemeCurrency;
        private String schemeCurrencyAmount;
        private String tagTransactionTime;
        private String tagTransactionDate;
        private String tagTransactionYear;
        private String schemePaymentTime;
        private String trsPmtCtxCrdhldrPres;
        private String trsPmtCtxAttnd;
        private String tagAcqIdentifier;
        private String Idac;
        private String itp;
        private String posLogNum;
        private String posArchitecture;
        private String merCompanionRef;
        private String ert;
        private String scheme;
        private String schemetransId;
        private String partnerTransId;
        private String partnerTransName;
        private String buyerId;
        private String partnerId;
        private String merchantId;
        private String merchantName;
        private String mccCode;
        private String storeId;
        private String storeName;
        private String messageSenderId;
        private String libelle;

        public static PartnerNotificationRequest.Builder aPartnerNotificationRequest() {
            return new PartnerNotificationRequest.Builder();
        }
        public Builder withMessage(String message)
        {
            this.message = message;
            return this;
        }
        public Builder withTagTransactionType(String tagTransactionType)
        {
            this.tagTransactionType = tagTransactionType;
            return this;
        }
        public Builder withTagAmountAuthorized(String tagAmountAuthorized)
        {
            this.tagAmountAuthorized = tagAmountAuthorized;
            return this;
        }
        public Builder withPartnerCurrency(String partnerCurrency)
        {
            this.partnerCurrency = partnerCurrency;
            return this;
        }
        public Builder withExchangeRate(String exchangeRate)
        {
            this.exchangeRate = exchangeRate;
            return this;
        }
        public Builder withSchemeCurrency(String schemeCurrency)
        {
            this.schemeCurrency = schemeCurrency;
            return this;
        }
        public Builder withSchemeCurrencyAmount(String schemeCurrencyAmount)
        {
            this.schemeCurrencyAmount = schemeCurrencyAmount;
            return this;
        }
        public Builder withTagTransactionTime(String tagTransactionTime)
        {
            this.tagTransactionTime = tagTransactionTime;
            return this;
        }
        public Builder withTagTransactionDate(String tagTransactionDate)
        {
            this.tagTransactionDate = tagTransactionDate;
            return this;
        }
        public Builder withTagTransactionYear(String tagTransactionYear)
        {
            this.tagTransactionYear = tagTransactionYear;
            return this;
        }
        public Builder withSchemePaymentTime(String schemePaymentTime)
        {
            this.schemePaymentTime = schemePaymentTime;
            return this;
        }
        public Builder withTrsPmtCtxCrdhldrPres(String trsPmtCtxCrdhldrPres)
        {
            this.trsPmtCtxCrdhldrPres = trsPmtCtxCrdhldrPres;
            return this;
        }
        public Builder withTrsPmtCtxAttnd(String trsPmtCtxAttnd)
        {
            this.trsPmtCtxAttnd = trsPmtCtxAttnd;
            return this;
        }
        public Builder withTagAcqIdentifier(String tagAcqIdentifier)
        {
            this.tagAcqIdentifier = tagAcqIdentifier;
            return this;
        }
        public Builder withIdac(String Idac)
        {
            this.Idac = Idac;
            return this;
        }
        public Builder withItp(String itp)
        {
            this.itp = itp;
            return this;
        }
        public Builder withPosLogNum(String posLogNum)
        {
            this.posLogNum = posLogNum;
            return this;
        }
        public Builder withPosArchitecture(String posArchitecture)
        {
            this.posArchitecture = posArchitecture;
            return this;
        }
        public Builder withMerCompanionRef(String merCompanionRef)
        {
            this.merCompanionRef = merCompanionRef;
            return this;
        }
        public Builder withErt(String ert)
        {
            this.ert = ert;
            return this;
        }
        public Builder withScheme(String scheme)
        {
            this.scheme = scheme;
            return this;
        }
        public Builder withSchemeTransId(String schemetransId)
        {
            this.schemetransId = schemetransId;
            return this;
        }
        public Builder withPartnerTransId(String partnerTransId)
        {
            this.partnerTransId = partnerTransId;
            return this;
        }
        public Builder withPartnerTransName(String partnerTransName)
        {
            this.partnerTransName = partnerTransName;
            return this;
        }
        public Builder withBuyerId(String buyerId)
        {
            this.buyerId = buyerId;
            return this;
        }
        public Builder withPartnerId(String partnerId)
        {
            this.partnerId = partnerId;
            return this;
        }
        public Builder withMerchantId(String merchantId)
        {
            this.merchantId = merchantId;
            return this;
        }
        public Builder withMerchantName(String merchantName)
        {
            this.merchantName = merchantName;
            return this;
        }
        public Builder withMccCode(String mccCode)
        {
            this.mccCode = mccCode;
            return this;
        }
        public Builder withStoreId(String storeId)
        {
            this.storeId = storeId;
            return this;
        }
        public Builder withStoreName(String storeName)
        {
            this.storeName = storeName;
            return this;
        }
        public Builder withMessageSenderId(String messageSenderId)
        {
            this.messageSenderId = messageSenderId;
            return this;
        }
        public Builder withLibelle(String libelle)
        {
            this.libelle = libelle;
            return this;
        }
    }
}
