package com.payline.payment.alipay.utils.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.payline.payment.alipay.bean.object.Notification;
import com.payline.payment.alipay.bean.object.Trade;
import com.payline.payment.alipay.bean.response.AlipayAPIResponse;
import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.payment.alipay.exception.InvalidDataException;
import com.payline.payment.alipay.exception.PluginException;
import com.payline.payment.alipay.utils.Constants;
import com.payline.payment.alipay.utils.PluginUtils;
import com.payline.payment.alipay.utils.properties.ConfigProperties;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.common.OnHoldCause;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseOnHold;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseSuccess;
import com.payline.pmapi.bean.refund.request.RefundRequest;
import com.payline.pmapi.logger.LogManager;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.*;


public class HttpClient {

    private static final Logger LOGGER = LogManager.getLogger(HttpClient.class);

    private static final String KEY_STORE_PATH = "/home/ubuntu/Documents/Monext/workspace/AlipaySandbox20200115.p12";
    private Gson parser;
    // Exceptions messages
    private static final String SERVICE_URL_ERROR = "Service URL is invalid";
    private static final String SYNTAX_ENCODING = "Syntax Exception";
    private static final String SIGNATURE_ENCODING = "Signature Exception";
    /**
     * The number of time the client must retry to send the request if it doesn't obtain a response.
     */
    private int retries;

    private org.apache.http.client.HttpClient client;

    // --- Singleton Holder pattern + initialization BEGIN
    /**
     * ------------------------------------------------------------------------------------------------------------------
     */
    HttpClient() {
        int connectionRequestTimeout;
        int connectTimeout;
        int socketTimeout;
        try {
            // request config timeouts (in seconds)
            ConfigProperties config = ConfigProperties.getInstance();
            connectionRequestTimeout = Integer.parseInt(config.get("http.connectionRequestTimeout"));
            connectTimeout = Integer.parseInt(config.get("http.connectTimeout"));
            socketTimeout = Integer.parseInt(config.get("http.socketTimeout"));

            // retries
            this.retries = Integer.parseInt(config.get("http.retries"));
        } catch (NumberFormatException e) {
            throw new PluginException("plugin error: http.* properties must be integers", e);
        }

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout * 1000)
                .setConnectTimeout(connectTimeout * 1000)
                .setSocketTimeout(socketTimeout * 1000)
                .build();

        // instantiate Apache HTTP client
        this.client = HttpClientBuilder.create()
                .useSystemProperties()
                .setDefaultRequestConfig(requestConfig)
                .setSSLSocketFactory(new SSLConnectionSocketFactory(HttpsURLConnection.getDefaultSSLSocketFactory(), SSLConnectionSocketFactory.getDefaultHostnameVerifier()))
                .build();
        this.parser = new GsonBuilder().create();

    }

    /**
     * ------------------------------------------------------------------------------------------------------------------
     */
    private static class Holder {
        private static final HttpClient instance = new HttpClient();
    }

    /**
     * Return HttpClient instance
     *
     * @return HttpClient instance
     */
    public static HttpClient getInstance() {
        return Holder.instance;
    }
    // --- Singleton Holder pattern + initialization END
    /**------------------------------------------------------------------------------------------------------------------*/
    /**
     * Send the request, with a retry system in case the client does not obtain a proper response from the server.
     *
     * @param httpRequest The request to send.
     * @return The response converted as a {@link StringResponse}.
     * @throws PluginException If an error repeatedly occurs and no proper response is obtained.
     */
    StringResponse execute(HttpRequestBase httpRequest) {
        StringResponse strResponse = null;
        int attempts = 1;

        while (strResponse == null && attempts <= this.retries) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Start call to partner API (attempt {}) :" + System.lineSeparator() + PluginUtils.requestToString(httpRequest), attempts);
            } else {
                LOGGER.info("Start call to partner API [{} {}] (attempt {})", httpRequest.getMethod(), httpRequest.getURI(), attempts);
            }
            try (CloseableHttpResponse httpResponse = (CloseableHttpResponse) this.client.execute(httpRequest)) {
                strResponse = StringResponse.fromHttpResponse(httpResponse);
            } catch (IOException e) {
                LOGGER.error("An error occurred during the HTTP call :", e);
                strResponse = null;
            } finally {
                attempts++;
            }
        }

        if (strResponse == null) {
            throw new PluginException("Failed to contact the partner API", FailureCause.COMMUNICATION_ERROR);
        }
        LOGGER.info("Response obtained from partner API [{} {}]", strResponse.getStatusCode(), strResponse.getStatusMessage());
        return strResponse;
    }
    /**------------------------------------------------------------------------------------------------------------------*/
    /**
     * Verify if API url are present
     *
     * @param requestConfiguration
     */
    private void verifyPartnerConfigurationURL(RequestConfiguration requestConfiguration) {
        if (requestConfiguration.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.ALIPAY_URL) == null) {
            throw new InvalidDataException("Missing API url from partner configuration (sensitive properties)");
        }

    }

    /**
     * Build and send request to verify a notification by its id
     * Verification is done by calling an Alipay's service
     * @param requestConfiguration
     * @param notificationId
     */
    public boolean getNotificationVerified(RequestConfiguration requestConfiguration, String notificationId)
    {
        boolean result = false;
        ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("service", "notify_verify"));
        params.add(new BasicNameValuePair("partner", requestConfiguration.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.PARTNER_ID).getValue()));
        params.add(new BasicNameValuePair("notify_id", notificationId));
        // Get the result of the request
        StringResponse response = getWithSignature(requestConfiguration, params);
        if (response.getStatusCode() == 200) {
            if(response.getContent().equalsIgnoreCase("true"))
            {
                result = true;
            }
        }
        return result;
    }

    /**
     * Build and send a request to get transaction status
     *
     * @param requestConfiguration
     * @return The response converted as a {@link AlipayAPIResponse}.
     */
    public AlipayAPIResponse getTransactionStatus(RequestConfiguration requestConfiguration, String transactionId) {
        // Create parameters
        ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("_input_charset", requestConfiguration.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.INPUT_CHARSET).getValue()));
        params.add(new BasicNameValuePair("out_trade_no", transactionId));
        params.add(new BasicNameValuePair("partner", requestConfiguration.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.PARTNER_ID).getValue()));
        params.add(new BasicNameValuePair("service", "single_trade_query"));
        params.add(new BasicNameValuePair("trade_no", transactionId));
        // Get the result of the request
        StringResponse response = getWithSignature(requestConfiguration, params);

        AlipayAPIResponse alipayAPIResponse = AlipayAPIResponse.fromXml(response.getContent());

        return alipayAPIResponse;
    }

    /**
     * Build and send a request to get a refund
     *
     * @param requestConfiguration
     * @param refundRequest
     * @return The response converted as a {@link AlipayAPIResponse}.
     */
    public AlipayAPIResponse getRefund(RequestConfiguration requestConfiguration, RefundRequest refundRequest) {
        //Check if user is on Desktop or Mobile
        String product_code;
        if (PluginUtils.userIsOnPC) {
            product_code = "NEW_OVERSEAS_SELLER";
        } else {
            product_code = "NEW_WAP_OVERSEAS_SELLER";
        }
        // Create parameters
        ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("_input_charset", refundRequest.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.INPUT_CHARSET).getValue()));
        params.add(new BasicNameValuePair("currency", refundRequest.getAmount().getCurrency().toString()));
        params.add(new BasicNameValuePair("gmt_return", new Date().toString()));
        params.add(new BasicNameValuePair("is_sync", "Y"));
        params.add(new BasicNameValuePair("out_return_no", refundRequest.getTransactionId()));
        params.add(new BasicNameValuePair("out_trade_no", refundRequest.getTransactionId()));
        params.add(new BasicNameValuePair("partner", refundRequest.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.PARTNER_ID).getValue()));
        params.add(new BasicNameValuePair("product_code", product_code));
        params.add(new BasicNameValuePair("return_amount", refundRequest.getAmount().getAmountInSmallestUnit().toString()));
        params.add(new BasicNameValuePair("service", "forex_refund"));

        // Get the result of the request
        StringResponse response = getWithSignature(requestConfiguration, params);
        AlipayAPIResponse alipayAPIResponse;
        if (response.getStatusCode() == 200) {
            alipayAPIResponse = AlipayAPIResponse.fromXml(response.getContent());
        } else {
            alipayAPIResponse = new AlipayAPIResponse();
            alipayAPIResponse.setIs_success("F");
            alipayAPIResponse.setError("404 Not Found");

        }

        return alipayAPIResponse;
    }

    /**
     * Build parameters string for create_forex_trade request
     *
     * @param requestConfiguration
     * @param paymentRequest
     * @return Parameters string for redirecting post request
     */
    public LinkedHashMap<String, String> getParametersPayment(RequestConfiguration requestConfiguration, PaymentRequest paymentRequest) throws UnsupportedEncodingException {
        String service;
        String product_code;
        LinkedHashMap<String, String> parametersListForResponse = new LinkedHashMap<>();
        //Check if user is on Desktop or Mobile
        if (PluginUtils.userIsOnPC) {
            service = "create_forex_trade";
            product_code = "NEW_OVERSEAS_SELLER";
        } else {
            service = "create_forex_trade_wap";
            product_code = "NEW_WAP_OVERSEAS_SELLER";
        }
        //Create parameters list
        ArrayList<NameValuePair> postFormData = new ArrayList<>();
        postFormData.add(new BasicNameValuePair("_input_charset", paymentRequest.getContractConfiguration().getProperty("INPUT_CHARSET").getValue()));
        postFormData.add(new BasicNameValuePair("currency", paymentRequest.getOrder().getAmount().getCurrency().getCurrencyCode()));
        postFormData.add(new BasicNameValuePair("notify_url", paymentRequest.getEnvironment().getNotificationURL()));
        postFormData.add(new BasicNameValuePair("out_trade_no", paymentRequest.getTransactionId()));
        postFormData.add(new BasicNameValuePair("partner", paymentRequest.getContractConfiguration().getProperty("PARTNER_ID").getValue()));
        postFormData.add(new BasicNameValuePair("product_code", product_code));
        postFormData.add(new BasicNameValuePair("refer_url", "https://www.google.fr"));
        postFormData.add(new BasicNameValuePair("return_url", paymentRequest.getEnvironment().getRedirectionReturnURL()));
        postFormData.add(new BasicNameValuePair("service", service));
        postFormData.add(new BasicNameValuePair("subject", paymentRequest.getSoftDescriptor()));
        postFormData.add(new BasicNameValuePair("total_fee", paymentRequest.getAmount().getAmountInSmallestUnit().toString()));
        List<NameValuePair> parametersListWithSign = this.getParamsWithSign(postFormData);
        parametersListWithSign.add(new BasicNameValuePair("sign_type", "RSA2"));
        for (NameValuePair parameter : parametersListWithSign) {
            parametersListForResponse.put(parameter.getName(), URLEncoder.encode(parameter.getValue(),paymentRequest.getContractConfiguration().getProperty("INPUT_CHARSET").getValue()));
        }
        return parametersListForResponse;
    }

    /**
     * Handle notification received in NotificationServiceImpl.parse()
     * @param configuration
     * @param content
     * @return The response converted as a {@link PaymentResponse}.
     */
    public PaymentResponse handleNotification(RequestConfiguration configuration, String content)
    {
        Notification notification = parser.fromJson(content, Notification.class);
        PaymentResponse paymentResponse = null;
        //Check notification is coming from Alipay
        if(getNotificationVerified(configuration, notification.getNotify_id()))
        {
            //Notification is verified, so we can check transaction status
            AlipayAPIResponse response = getTransactionStatus(configuration, notification.getOut_trade_no());
            if(response.getIs_success().equalsIgnoreCase("t"))
            {
                Trade transaction = response.getResponse().getTrade();
                if(transaction.getTrade_status().equalsIgnoreCase("TRADE_FINISHED"))
                {
                    //Return a paymentResponseSuccess
                    return PaymentResponseSuccess.PaymentResponseSuccessBuilder.aPaymentResponseSuccess()
                            .withPartnerTransactionId(transaction.getTrade_no())
                            .withStatusCode(transaction.getTrade_status())
                            .withTransactionAdditionalData(transaction.getBuyer_id())
                            .build();
                }
                else if(transaction.getTrade_status().equalsIgnoreCase("TRADE_CLOSED"))
                {
                    //Return a paymentResponseFailure
                    paymentResponse = PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                            .withErrorCode(transaction.getTrade_status())
                            .withFailureCause(FailureCause.REFUSED)
                            .build();
                }
                else if(transaction.getTrade_status().equalsIgnoreCase("WAIT_BUYER_PAY"))
                {
                    //Return a paymentResponseOnHold
                    paymentResponse = PaymentResponseOnHold.PaymentResponseOnHoldBuilder.aPaymentResponseOnHold()
                            .withPartnerTransactionId(transaction.getTrade_no())
                            .withOnHoldCause(OnHoldCause.ASYNC_RETRY)
                            .build();
                }
            }
            else
            {
                //Response success is "F"
                //Return a paymentResponseFailure
                paymentResponse = PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                        .withErrorCode(response.getError())
                        .withFailureCause(FailureCause.INVALID_DATA)
                        .build();
            }
        }
        else
        {
            //Notification verification failed
            //Return a paymentResponseFailure
            paymentResponse = PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                    .withFailureCause(FailureCause.INVALID_DATA)
                    .build();
        }
        return paymentResponse;
    }
    /**------------------------------------------------------------------------------------------------------------------*/
    /**
     * Verify API connection
     *
     * @param requestConfiguration
     * @return True if API connection work, otherwise false
     */
    public Boolean verifyConnection(RequestConfiguration requestConfiguration) {

        // Create parameters
        ArrayList<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("_input_charset", "utf-8"));
        params.add(new BasicNameValuePair("out_trade_no", "0"));
        params.add(new BasicNameValuePair("partner", requestConfiguration.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.PARTNER_ID).getValue()));
        params.add(new BasicNameValuePair("service", "single_trade_query"));

        // Get the result of the request
        StringResponse response = getWithSignature(requestConfiguration, params);

        // Check the response content
        if (response.getContent() == null) {
            LOGGER.error("No response body");
            return false;
        }

        return response.getContent().contains("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<alipay><is_success>F</is_success><error>TRADE_NOT_EXIST</error></alipay>");
    }
    /**------------------------------------------------------------------------------------------------------------------*/
    /**
     * Get params with sign
     *
     * @param params
     * @return
     */
    public List<NameValuePair> getParamsWithSign(List<NameValuePair> params) {
        try {
            // Create the pre-signing string
            String preSigning = preSigningString(params);

            // Create the RSA2 signature
            String sha256withRsa = signSHA256withRSA(preSigning);

            // Add the signature to the parameters
            params.add(new BasicNameValuePair("sign", sha256withRsa));
            return params;
        } catch (Exception e) {
            throw new InvalidDataException(SIGNATURE_ENCODING, e);
        }
    }
    /**------------------------------------------------------------------------------------------------------------------*/
    /**
     * Manage get API call with signature
     *
     * @param requestConfiguration
     * @return
     */
    public StringResponse getWithSignature(RequestConfiguration requestConfiguration, List<NameValuePair> params) {
        URI uri;
        URI baseUrl;

        // Check if API url is present
        verifyPartnerConfigurationURL(requestConfiguration);

        // Get the API URL
        try {
            baseUrl = new URI(requestConfiguration.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.ALIPAY_URL));
        } catch (URISyntaxException e) {
            throw new InvalidDataException(SERVICE_URL_ERROR, e);
        }

        // Create the pre-signing string
        String preSigning = preSigningString(params);

        // Create the RSA2 signature
        String sha256withRsa = signSHA256withRSA(preSigning);

        // Add the signature to the parameters
        params.add(new BasicNameValuePair("sign", sha256withRsa));
        params.add(new BasicNameValuePair("sign_type", "RSA2"));

        // Create the HttpGet url with parameters
        try {
            uri = new URIBuilder(baseUrl).setParameters(params).build();
        } catch (URISyntaxException e) {
            throw new InvalidDataException(SYNTAX_ENCODING, e);
        }

        // Create the HttpGet request
        HttpGet httpGet = new HttpGet(uri);

        // Execute request
        return this.execute(httpGet);
    }
    /**------------------------------------------------------------------------------------------------------------------*/
    /**
     * Manage get API call
     *
     * @param requestConfiguration
     * @return
     */
    public StringResponse get(RequestConfiguration requestConfiguration, List<NameValuePair> params) {
        URI uri;
        URI baseUrl;

        // Check if API url is present
        verifyPartnerConfigurationURL(requestConfiguration);

        // Get the API URL
        try {
            baseUrl = new URI(requestConfiguration.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.ALIPAY_URL));
        } catch (URISyntaxException e) {
            throw new InvalidDataException(SERVICE_URL_ERROR, e);
        }

        // Create the HttpGet url with parameters
        try {
            uri = new URIBuilder(baseUrl).setParameters(params).build();
        } catch (URISyntaxException e) {
            throw new InvalidDataException(SYNTAX_ENCODING, e);
        }

        // Create the HttpGet request
        HttpGet httpGet = new HttpGet(uri);

        // Execute request
        return this.execute(httpGet);
    }
    /**------------------------------------------------------------------------------------------------------------------*/
    /**
     * Format parameters to generate a signature
     *
     * @param params
     * @return Parameters string to generate a signature
     */
    private static String preSigningString(List<NameValuePair> params) {
        StringBuilder preSign = new StringBuilder();
        boolean first = true;

        // Build a string from parameters
        for (NameValuePair nameValuePair : params) {

            // The "sign_type" parameter is not used to generate the request signature
            if (!nameValuePair.getName().equals("sign_type") && !nameValuePair.getName().equals("sign")) {
                // Add the separator
                if (!first) {
                    preSign.append("&");
                }
                if (nameValuePair.getValue().length() > 0 && !nameValuePair.getValue().equals(null) && !nameValuePair.getValue().equals("null")) {
                    // Add name and value
                    preSign.append(nameValuePair.getName() + "=" + nameValuePair.getValue());
                }
            }
            first = false;
        }

        return preSign.toString();
    }
    /**------------------------------------------------------------------------------------------------------------------*/
    /**
     * Generate a RSA signature based on the query parameters
     *
     * @param preSigningString
     * @return RSA signature
     */
    private static String signSHA256withRSA(String preSigningString) {  ///NoSuchAlgorithmException, InvalidKeyException, SignatureException
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(getPk());
            signature.update(preSigningString.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (Exception e) {
            LOGGER.error("signSHA256withRSA", e);

        }
        return null;
    }
    /**------------------------------------------------------------------------------------------------------------------*/
    /**
     * Return the private key in the keyStore
     *
     * @return Private key
     */
    private static PrivateKey getPk() {

        // TODO: récupérer la clé privée dans les partners configuration

        String keyStoreType = "pkcs12";
        char[] passwd = "AlipayCert2020".toCharArray();
        String alias = "selfsigned";

        // Load the keystore and recover the private key
        // @see https://www.baeldung.com/java-keystore
        KeyStore ks = null;
        try {
            ks = KeyStore.getInstance(keyStoreType);
            ks.load(new FileInputStream(KEY_STORE_PATH), passwd);
            return (PrivateKey) ks.getKey(alias, passwd);
        } catch (GeneralSecurityException | IOException e) {
            LOGGER.error("getPk", e);
        }
        return null;
    }
    /**------------------------------------------------------------------------------------------------------------------*/

}
