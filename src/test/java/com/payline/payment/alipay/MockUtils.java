package com.payline.payment.alipay;

import com.payline.payment.alipay.utils.Constants;
import com.payline.payment.alipay.utils.http.StringResponse;
import com.payline.pmapi.bean.common.Buyer;
import com.payline.pmapi.bean.configuration.PartnerConfiguration;
import com.payline.pmapi.bean.configuration.request.ContractParametersCheckRequest;
import com.payline.pmapi.bean.notification.request.NotificationRequest;
import com.payline.pmapi.bean.payment.*;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.paymentform.request.PaymentFormConfigurationRequest;
import com.payline.pmapi.bean.paymentform.request.PaymentFormLogoRequest;
import com.payline.pmapi.bean.refund.request.RefundRequest;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.mockito.internal.util.reflection.FieldSetter;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.util.*;

/**
 * Utility class that generates mocks of frequently used objects.
 */
public class MockUtils {
    private static String TRANSACTIONID = "PAYLINE20200116103352";
    private static String PARTNER_TRANSACTIONID = "098765432109876543210";
    public static final StringResponse call()
    {
        StringResponse stringResponse = MockUtils.mockStringResponse(200
                , "OK"
                , "true"
                , null);
        return stringResponse;
    }
    public static String validNotificationBodyTemplate = "{" +
            "\"currency\": \"USD\"," +
            "\"notify_id\": \"1\","+
            "\"out_trade_no\": \"PAYLINE20200116105303\"," +
            "\"total_fee\": \"0.01\"," +
            "\"trade_no\": \"PAYLINE20200116105303\"," +
            "\"trade_status\": \"TRADE_STATUS\"," +
            "\"sign\": \"signaturelulz\"," +
            "\"sign_type\": \"RSA2\"}";
    public static String invalidNotificationBodyTemplate = "{{" +
            "\"currency\" \"USD\"," +
            "\"notify_id\" \"1\","+
            "\"out_trade_no\"\"PAYLINE20200116105303\"," +
            "\"total_fee\" \"0.01\"," +
            "\"trade_no\" \"PAYLINE20200116105303\"," +
            "\"trade_status\"\"TRADE_STATUS\"," +
            "\"sign\" \"signaturelulz\"," +
            "\"sign_type\" \"RSA2\"}";
    public static String invalidIdNotificationBodyTemplate = "{" +
            "\"currency\": \"USD\"," +
            "\"notify_id\": \"-1\","+
            "\"out_trade_no\": \"PAYLINE20200116105303\"," +
            "\"total_fee\": \"0.01\"," +
            "\"trade_no\": \"PAYLINE20200116105303\"," +
            "\"trade_status\": \"TRADE_STATUS\"," +
            "\"sign\": \"signaturelulz\"," +
            "\"sign_type\": \"RSA2\"}";
    public static String transactionBodyPaid = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<alipay>\n" +
            "  <is_success>T</is_success>\n" +
            "  <request>\n" +
            "    <param name=\"out_trade_no\">PAYLINE20200116103332</param>\n" +
            "    <param name=\"partner\">2088621926786355</param>\n" +
            "    <param name=\"_input_charset\">utf-8</param>\n" +
            "    <param name=\"service\">single_trade_query</param>\n" +
            "  </request>\n" +
            "  <response>\n" +
            "    <trade>\n" +
            "      <buyer_email>for***@alitest.com</buyer_email>\n" +
            "      <buyer_id>2088622942481446</buyer_id>\n" +
            "      <discount>0.00</discount>\n" +
            "      <flag_trade_locked>0</flag_trade_locked>\n" +
            "      <gmt_create>2020-02-27 21:46:41</gmt_create>\n" +
            "      <gmt_last_modified_time>2020-02-27 21:46:47</gmt_last_modified_time>\n" +
            "      <gmt_payment>2020-02-27 21:46:47</gmt_payment>\n" +
            "      <is_total_fee_adjust>F</is_total_fee_adjust>\n" +
            "      <operator_role>B</operator_role>\n" +
            "      <out_trade_no>PAYLINE20200116103332</out_trade_no>\n" +
            "      <payment_type>100</payment_type>\n" +
            "      <price>73.82</price>\n" +
            "      <quantity>1</quantity>\n" +
            "      <seller_email>for***@alitest.com</seller_email>\n" +
            "      <seller_id>2088621926786355</seller_id>\n" +
            "      <subject>Test</subject>\n" +
            "      <to_buyer_fee>0.00</to_buyer_fee>\n" +
            "      <to_seller_fee>73.82</to_seller_fee>\n" +
            "      <total_fee>73.82</total_fee>\n" +
            "      <trade_no>2020022722001381441000086488</trade_no>\n" +
            "      <trade_status>TRADE_FINISHED</trade_status>\n" +
            "      <use_coupon>F</use_coupon>\n" +
            "    </trade>\n" +
            "  </response>\n" +
            "  <sign>MiQ/9EHsh6qD1d9+jTpZKiJjCeYU1QlxTLBDlYLvfqfQFnr+uH/4gkfwkHrWgGCYxX+HEPXIJDQRhiQDvOS2jgq3xvxYBK/ZppJu3U79FwlJx1a1oQTqICnEalU1RBQPKdMXLj7qRb07stDBunmZEwcU1d1WQLZeogzx1f76dpu3ofjIPBoUxRiclnYCV7Jkrq4zo0yRD+hxW7VJ98iE8zII6AGVv4z+3v/m2hx71oDnJrj1w6aBuwcxotSba3azOdbzzZSHo3XBW0Gr1/yooM4zc73lcfdXgK75NYUVAgUeXo8YY3PnBHDBe4TRQkNmbKpNElOwOycVgp6FM2oj1A==</sign>\n" +
            "  <sign_type>RSA2</sign_type>\n" +
            "</alipay>";
    public static String notificationBodyPaid = validNotificationBodyTemplate.replace("TRADE_STATUS", "TRADE_FINISHED");
    public static String notificationBodyRefused = validNotificationBodyTemplate.replace("TRADE_STATUS", "TRADE_CLOSED");
    public static String notificationBodyPaymentHold = validNotificationBodyTemplate.replace("TRADE_STATUS", "WAIT_BUYER_PAY");
    /**
     * Generate a valid {@link Environment}.
     */
    public static Environment anEnvironment() {
        return new Environment("http://notificationURL.com",
                "http://redirectionURL.com",
                "http://redirectionCancelURL.com",
                true);
    }

    /**
     * Generate a valid {@link PartnerConfiguration}.
     */
    public static PartnerConfiguration aPartnerConfiguration() {
        Map<String, String> partnerConfigurationMap = new HashMap<>();

        partnerConfigurationMap.put(Constants.PartnerConfigurationKeys.ALIPAY_URL, "https://mapi.alipaydev.com/gateway.do");
        partnerConfigurationMap.put(Constants.PartnerConfigurationKeys.READ_TIMEOUT, "3000");
        partnerConfigurationMap.put(Constants.PartnerConfigurationKeys.CONNECT_TIMEOUT, "3000");

        Map<String, String> sensitiveConfigurationMap = new HashMap<>();

        return new PartnerConfiguration(partnerConfigurationMap, sensitiveConfigurationMap);
    }

    /**
     * Generate a MalformedURLException url {@link PartnerConfiguration}.
     */
    public static PartnerConfiguration aPartnerConfigurationMalformedURLException() {
        Map<String, String> partnerConfigurationMap = new HashMap<>();

        partnerConfigurationMap.put(Constants.PartnerConfigurationKeys.ALIPAY_URL, "htp:/");

        Map<String, String> sensitiveConfigurationMap = new HashMap<>();

        return new PartnerConfiguration(partnerConfigurationMap, sensitiveConfigurationMap);
    }
    /**
     * Generate a valid {@link ContractConfiguration} to verify the connection to the API.
     */
    public static ContractConfiguration aContractConfigurationToVerifyConnection() {

        Map<String, ContractProperty> contractProperties = new HashMap<>();
        contractProperties.put(Constants.ContractConfigurationKeys.PARTNER_ID, new ContractProperty("2088621926786355"));

        return new ContractConfiguration("Alipay", contractProperties);
    }
    /**
     * Generate a valid {@link ContractConfiguration} to verify the connection to the API.
     */
    public static ContractConfiguration aContractConfiguration() {

        Map<String, ContractProperty> contractProperties = new HashMap<>();
        contractProperties.put(Constants.ContractConfigurationKeys.PARTNER_ID, new ContractProperty("2088621926786355"));
        contractProperties.put(Constants.ContractConfigurationKeys.NOTIFY_URL, new ContractProperty("http://notification.com"));
        return new ContractConfiguration("Alipay", contractProperties);
    }
    /**
     * Generate an invalid {@link ContractConfiguration} with invalid charset to generate an Encoding exception
     */
    public static ContractConfiguration aContractConfigurationUnsupportedEncodingException() {

        Map<String, ContractProperty> contractProperties = new HashMap<>();
        contractProperties.put(Constants.ContractConfigurationKeys.PARTNER_ID, new ContractProperty("2088621926786355"));
        return new ContractConfiguration("Alipay", contractProperties);
    }
    /**
     * Generate a valid parameters to verify the connection to the API.
     */
    public static List aValidParametersList() {
        // Create parameters
        ArrayList<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("_input_charset", "UTF-8"));
        params.add(new BasicNameValuePair("out_trade_no", "0"));
        params.add(new BasicNameValuePair("partner", "2088621926786355"));
        params.add(new BasicNameValuePair("service", "single_trade_query"));
        params.add(new BasicNameValuePair("sign_type", "RSA2"));

        return params;
    }

    /**
     * Generate a valid {@link PaymentFormLogoRequest}.
     */
    public static PaymentFormLogoRequest aPaymentFormLogoRequest() {
        return PaymentFormLogoRequest.PaymentFormLogoRequestBuilder.aPaymentFormLogoRequest()
                .withContractConfiguration(aContractConfiguration())
                .withEnvironment(anEnvironment())
                .withPartnerConfiguration(aPartnerConfiguration())
                .withLocale(Locale.getDefault())
                .build();
    }

    /**
     * Generate a valid {@link com.payline.pmapi.bean.notification.request.NotificationRequest}.
     */
    public static NotificationRequest aPaylineNotificationRequest(String body) {
        return aPaylineNotificationRequestBuilder(body).build();
    }

    /**
     * Generate a builder for a valid {@link NotificationRequest}.
     * This way, some attributes may be overridden to match specific test needs.
     */
    public static NotificationRequest.NotificationRequestBuilder aPaylineNotificationRequestBuilder(String body) {
        return NotificationRequest.NotificationRequestBuilder
                .aNotificationRequest()
                .withContent(new ByteArrayInputStream(body.getBytes()))
                .withHttpMethod("GET")
                .withPathInfo("foo")
                .withContractConfiguration(aContractConfiguration())
                .withEnvironment(anEnvironment())
                .withPartnerConfiguration(aPartnerConfiguration())
                .withTransactionId(TRANSACTIONID)
                .withHeaderInfos(new HashMap<>());
    }

    /**
     * Generate a valid {@link PaymentRequest}.
     */
    public static PaymentRequest aPaylinePaymentRequest() {
        return aPaylinePaymentRequestBuilder().build();
    }

    /**
     * Generate a builder for a valid {@link PaymentRequest}.
     * This way, some attributes may be overridden to match specific test needs.
     */
    public static PaymentRequest.Builder aPaylinePaymentRequestBuilder() {
        long randomTransactionId = (long) (Math.random() * 100000000000000L);
        return PaymentRequest.builder()
                .withAmount(aPaylineAmount())
                .withBrowser(aBrowser())
                .withBuyer(aBuyer())
                .withCaptureNow(true)
                .withContractConfiguration(aContractConfiguration())
                .withEnvironment(anEnvironment())
                .withLocale(Locale.getDefault())
                .withOrder(aPaylineOrder())
                .withPartnerConfiguration(aPartnerConfiguration())
                .withPaymentFormContext(aPaymentFormContext())
                .withSoftDescriptor("Test")
                .withTransactionId("PAYLINE"+randomTransactionId);
    }

    /**
     * Generate a valid {@link RefundRequest}.
     */
    public static RefundRequest aPaylineRefundRequest() {
        return aPaylineRefundRequestBuilder().build();
    }

    /**
     * Generate a builder for a valid {@link RefundRequest}.
     * This way, some attributes may be overridden to match specific test needs.
     */
    public static RefundRequest.RefundRequestBuilder aPaylineRefundRequestBuilder() {
        return RefundRequest.RefundRequestBuilder.aRefundRequest()
                .withAmount(aPaylineAmount())
                .withBuyer(aBuyer())
                .withContractConfiguration(aContractConfiguration())
                .withEnvironment(anEnvironment())
                .withPartnerTransactionId(TRANSACTIONID)
                .withOrder(aPaylineOrder())
                .withPartnerConfiguration(aPartnerConfiguration())
                .withSoftDescriptor("Test")
                .withTransactionId(TRANSACTIONID);
    }

    /**
     * Generate a valid {@link PaymentRequest}.
     */
    public static RefundRequest anInvalidPaylineRefundRequest() {
        return anInvalidPaylineRefundRequestBuilder().build();
    }

    /**
     * Generate a builder for a valid {@link RefundRequest}.
     * This way, some attributes may be overridden to match specific test needs.
     */
    public static RefundRequest.RefundRequestBuilder anInvalidPaylineRefundRequestBuilder() {
        return RefundRequest.RefundRequestBuilder.aRefundRequest()
                .withAmount(aPaylineAmount())
                .withBuyer(aBuyer())
                .withContractConfiguration(aContractConfiguration())
                .withEnvironment(anEnvironment())
                .withPartnerTransactionId(TRANSACTIONID)
                .withOrder(aPaylineOrder())
                .withPartnerConfiguration(aPartnerConfiguration())
                .withSoftDescriptor("Test")
                .withTransactionId("null");
    }

    /**
     * Generate a valid {@link PaymentFormContext}.
     */
    public static PaymentFormContext aPaymentFormContext() {
        Map<String, String> paymentFormParameter = new HashMap<>();

        return PaymentFormContext.PaymentFormContextBuilder.aPaymentFormContext()
                .withPaymentFormParameter(paymentFormParameter)
                .withSensitivePaymentFormParameter(new HashMap<>())
                .build();
    }

    /**
     * @return a valid user agent.
     */
    public static String aUserAgent() {
        return "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:67.0) Gecko/20100101 Firefox/67.0";
    }

    /**
     * Generate a valid {@link Browser}.
     */
    public static Browser aBrowser() {
        return Browser.BrowserBuilder.aBrowser()
                .withLocale(Locale.getDefault())
                .withIp("192.168.0.1")
                .withUserAgent(aUserAgent())
                .build();
    }
    /**
     * Generate a valid {@link ContractParametersCheckRequest}.
     */
    public static ContractParametersCheckRequest aContractParametersCheckRequest() {
        return aContractParametersCheckRequestBuilder().build();
    }
    /**
     * Generate a builder for a valid {@link ContractParametersCheckRequest}.
     * This way, some attributes may be overridden to match specific test needs.
     */
    public static ContractParametersCheckRequest.CheckRequestBuilder aContractParametersCheckRequestBuilder() {
        return ContractParametersCheckRequest.CheckRequestBuilder.aCheckRequest()
                .withAccountInfo(anAccountInfo())
                .withContractConfiguration(aContractConfiguration())
                .withEnvironment(anEnvironment())
                .withLocale(Locale.getDefault())
                .withPartnerConfiguration(aPartnerConfiguration());
    }
    /**
     * Generate a valid accountInfo, an attribute of a {@link ContractParametersCheckRequest} instance.
     */
    public static Map<String, String> anAccountInfo() {
        return anAccountInfo(aContractConfiguration());
    }
    /**------------------------------------------------------------------------------------------------------------------*/

    /**
     * Generate a valid accountInfo, an attribute of a {@link ContractParametersCheckRequest} instance,
     * from the given {@link ContractConfiguration}.
     *
     * @param contractConfiguration The model object from which the properties will be copied
     */
    public static Map<String, String> anAccountInfo(ContractConfiguration contractConfiguration) {
        Map<String, String> accountInfo = new HashMap<>();
        for (Map.Entry<String, ContractProperty> entry : contractConfiguration.getContractProperties().entrySet()) {
            accountInfo.put(entry.getKey(), entry.getValue().getValue());
        }
        return accountInfo;
    }

    /**
     * Generate a valid {@link PaymentFormConfigurationRequest}.
     */
    public static PaymentFormConfigurationRequest aPaymentFormConfigurationRequest() {
        return aPaymentFormConfigurationRequestBuilder().build();
    }
    /**
     * Generate a builder for a valid {@link PaymentFormConfigurationRequest}.
     * This way, some attributes may be overridden to match specific test needs.
     */
    public static PaymentFormConfigurationRequest.PaymentFormConfigurationRequestBuilder aPaymentFormConfigurationRequestBuilder() {
        return PaymentFormConfigurationRequest.PaymentFormConfigurationRequestBuilder.aPaymentFormConfigurationRequest()
                .withAmount(aPaylineAmount())
                .withBuyer(aBuyer())
                .withContractConfiguration(aContractConfiguration())
                .withEnvironment(anEnvironment())
                .withLocale(Locale.FRANCE)
                .withOrder(aPaylineOrder())
                .withPartnerConfiguration(aPartnerConfiguration());
    }
    /**
     * Generate a valid Payline Amount.
     */
    public static com.payline.pmapi.bean.common.Amount aPaylineAmount() {
        return new com.payline.pmapi.bean.common.Amount(BigInteger.valueOf(10), Currency.getInstance("EUR"));
    }
    /**
     * Generate a valid {@link Buyer}.
     */
    public static Buyer aBuyer() {
        return Buyer.BuyerBuilder.aBuyer()
                .withFullName(new Buyer.FullName("Marie", "Durand", "1"))
                .withEmail("foo@bar.baz")
                .build();
    }
    /**
     * Generate a valid, but not complete, {@link com.payline.pmapi.bean.payment.Order}
     */
    public static com.payline.pmapi.bean.payment.Order aPaylineOrder() {
        List<Order.OrderItem> items = new ArrayList<>();

        items.add(com.payline.pmapi.bean.payment.Order.OrderItem.OrderItemBuilder
                .anOrderItem()
                .withReference("foo")
                .withAmount(aPaylineAmount())
                .withQuantity((long) 1)
                .build());

        return com.payline.pmapi.bean.payment.Order.OrderBuilder.anOrder()
                .withDate(new Date())
                .withAmount(aPaylineAmount())
                .withItems(items)
                .withReference("ORDER-REF-123456")
                .build();
    }

    /**
     * Generate parameters string for get request (tests only)
     * @param params
     * @return get request parameters string
     */
    public static String generateParametersString(Map<String, String> params) {
        StringBuilder preSign = new StringBuilder();
        boolean first = true;

        // Build a string from parameters
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!first) {
                preSign.append("&");
            }
            // Add name and value
            preSign.append(entry.getKey() + "=" + entry.getValue());
            first = false;
        }

        return preSign.toString();
    }
    /**
     * Moch a StringResponse with the given elements.
     *
     * @param statusCode    The HTTP status code (ex: 200, 403)
     * @param statusMessage The HTTP status message (ex: "OK", "Forbidden")
     * @param content       The response content as a string
     * @param headers       The response headers
     * @return A mocked StringResponse
     */
    public static StringResponse mockStringResponse(int statusCode, String statusMessage, String content, Map<String, String> headers) {
        StringResponse response = new StringResponse();

        try {
            if (content != null && !content.isEmpty()) {
                FieldSetter.setField(response, StringResponse.class.getDeclaredField("content"), content);
            }
            if (headers != null && headers.size() > 0) {
                FieldSetter.setField(response, StringResponse.class.getDeclaredField("headers"), headers);
            }
            if (statusCode >= 100 && statusCode < 600) {
                FieldSetter.setField(response, StringResponse.class.getDeclaredField("statusCode"), statusCode);
            }
            if (statusMessage != null && !statusMessage.isEmpty()) {
                FieldSetter.setField(response, StringResponse.class.getDeclaredField("statusMessage"), statusMessage);
            }
        } catch (NoSuchFieldException e) {
            // This would happen in a testing context: spare the exception throw, the test case will probably fail anyway
            return null;
        }

        return response;
    }

}
