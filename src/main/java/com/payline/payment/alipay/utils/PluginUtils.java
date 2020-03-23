package com.payline.payment.alipay.utils;

import com.payline.pmapi.bean.common.FailureCause;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PluginUtils {
    static public boolean userIsOnPC = false;

    /* Static utility class : no need to instantiate it (to please Sonar) */
    private PluginUtils() {
    }

    /**
     * Convert the path and headers of a {@link HttpRequestBase} to a readable {@link String}.
     * Mainly, for debugging purpose.
     *
     * @param httpRequest the request to convert
     * @return request method, path and headers as a string
     */
    public static String requestToString(HttpRequestBase httpRequest) {
        String ln = System.lineSeparator();
        String str = httpRequest.getMethod() + " " + httpRequest.getURI() + ln;

        List<String> strHeaders = new ArrayList<>();
        for (Header h : httpRequest.getAllHeaders()) {
            // For obvious security reason, the value of Authorization header is never printed in the logs
            if (HttpHeaders.AUTHORIZATION.equals(h.getName())) {
                String[] value = h.getValue().split(" ");
                strHeaders.add(h.getName() + ": " + (value.length > 1 ? value[0] : "") + " *****");
            } else {
                strHeaders.add(h.getName() + ": " + h.getValue());
            }
        }
        str += String.join(ln, strHeaders);

        if (httpRequest instanceof HttpPost) {
            try {
                str += ln + new BufferedReader(new InputStreamReader(((HttpPost) httpRequest).getEntity().getContent()))
                        .lines()
                        .collect(Collectors.joining(ln));
            } catch (IOException e) {
                str += ln + "<< Error retrieving request body >>";
            }
        }

        return str;
    }

    /**
     * Truncate the given string with the given length, if necessary.
     *
     * @param value  The string to truncate
     * @param length The maximum allowed length
     * @return The truncated string
     */
    public static String truncate(String value, int length) {
        if (value != null && value.length() > length) {
            value = value.substring(0, length);
        }
        return value;
    }

    /**
     * Switch user device
     * This method allow to simulate a computer or a mobile (for testing services on mobile)
     */
    public static void SwitchDevice(boolean isOnPC) {
        PluginUtils.userIsOnPC = isOnPC;
    }

    /**
     * Handle error code
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

    /**
     * Convert an InputStream into a String
     *
     * @param stream the InputStream to convert
     * @return the converted String encoded in UTF-8
     */
    public static String inputStreamToString(InputStream stream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        return br.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Check if a String is null or empty
     *
     * @param value the String to check
     * @return
     */
    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    /**
     *
     */
    public static enum VerificationResponse {
        TRUE,
        FALSE,
        INVALID;
    }

    public static String getStringUsingRegex(String regex, String data) {
        Pattern pattern = Pattern.compile("'(.*?)'");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }


    /**
     * create a date in the format yyyy-MM-dd HH:mm:ss
     *
     * @param date the date to convert
     * @return
     */
    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        return format.format(date);
    }
}