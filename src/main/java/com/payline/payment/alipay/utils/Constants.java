package com.payline.payment.alipay.utils;

/**
 * Support for constants used everywhere in the plugin sources.
 */
public class Constants {

    /**
     * Keys for the entries in ContractConfiguration map.
     */
    public static class ContractConfigurationKeys {
        public static final String PARTNER_ID = "PARTNER_ID";
        public static final String SUPPLIER = "SUPPLIER";
        public static final String SECONDARY_MERCHANT_ID = "SECONDARY_MERCHANT_ID";
        public static final String NOTIFY_URL = "NOTIFY_URL";

        /* Static utility class : no need to instantiate it (Sonar bug fix) */
        private ContractConfigurationKeys(){}
    }

    /**
     * Keys for the entries in PartnerConfiguration maps.
     */
    public static class PartnerConfigurationKeys {


        public static final String ALIPAY_URL = "ALIPAY_URL";
        public static final String READ_TIMEOUT = "readTimeOut";
        public static final String CONNECT_TIMEOUT = "connectTimeOut";
        /* Static utility class : no need to instantiate it (Sonar bug fix) */
        private PartnerConfigurationKeys(){}
    }

    /**
     * Keys for the entries in RequestContext data.
     */
    public static class RequestContextKeys {

        public static final String PAYMENT_ID = "paymentId";

        /* Static utility class : no need to instantiate it (Sonar bug fix) */
        private RequestContextKeys(){}
    }

    /* Static utility class : no need to instantiate it (Sonar bug fix) */
    private Constants(){}

}
