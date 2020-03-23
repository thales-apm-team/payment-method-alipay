package com.payline.payment.alipay.utils;

/**
 * Support for constants used everywhere in the plugin sources.
 */
public class Constants {



    /**
     * Keys for the entries in PartnerConfiguration maps.
     */
    public static class PartnerConfigurationKeys {


        public static final String ALIPAY_URL = "ALIPAY_URL";
        public static final String READ_TIMEOUT = "READ_TIMEOUT";
        public static final String CONNECT_TIMEOUT = "CONNECT_TIMEOUT";
        public static final String PAYLINE_PRIVATE_KEY = "PAYLINE_PRIVATE_KEY";
        public static final String PUBLIC_KEY = "PUBLIC_KEY";
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
