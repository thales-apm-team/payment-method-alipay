package com.payline.payment.alipay.utils.business;

import com.payline.pmapi.bean.common.FailureCause;

public class ErrorUtils {

    /* Static utility class : no need to instantiate it (to please Sonar) */
    private ErrorUtils() {
    }

    /**
     * Convert an errorCode into an FailureCause
     * @param errorCode the error code to convert
     * @return a Payline FailureCause
     */
    public static FailureCause getFailureCause(String errorCode) {
        switch (errorCode) {
            case "PURCHASE_TRADE_NOT_EXIST":
            case "ILLEGAL_SIGN":
            case "ILLEGAL_DYN_MD5_KEY":
            case "ILLEGAL_ENCRYPT":
            case "ILLEGAL_ARGUMENT":
            case "ILLEGAL_SERVICE":
            case "ILLEGAL_USER":
            case "ILLEGAL_PARTNER":
            case "ILLEGAL_AGENT":
            case "ILLEGAL_SIGN_TYPE":
            case "ILLEGAL_CLIENT_IP":
            case "ILLEGAL_CHARSET":
            case "ILLEGAL_DIGEST_TYPE":
            case "ILLEGAL_DIGEST":
            case "ILLEGAL_FILE_FORMAT":
            case "ILLEGAL_ENCODING":
            case "ILLEGAL_TARGET_SERVICE":
                return FailureCause.INVALID_DATA;
            case "ILLEGAL_EXTERFACE":
            case "ILLEGAL_PARTNER_EXTERFACE":
            case "ILLEGAL_SECURITY_PROFILE":
            case "HAS_NO_PRIVILEGE":
            case "EXTERFACE_IS_CLOSED":
            case "ILLEGAL_REQUEST_REFERER":
            case "ILLEGAL_ANTI_PHISHING_KEY":
            case "ANTI_PHISHING_KEY_TIMEOUT":
            case "ILLEGAL_EXTER_INVOKE_IP":
            case "SESSION_TIMEOUT":
            case "ILLEGAL_ACCESS_SWITCH_SYSTEM":
                return FailureCause.COMMUNICATION_ERROR;
            case "SYSTEM_ERROR":
            case "404 Not Found":
            default:
                return FailureCause.PARTNER_UNKNOWN_ERROR;
        }
    }
}
