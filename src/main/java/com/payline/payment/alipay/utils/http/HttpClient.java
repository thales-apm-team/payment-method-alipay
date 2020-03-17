package com.payline.payment.alipay.utils.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.payment.alipay.bean.response.AlipayAPIResponse;
import com.payline.payment.alipay.exception.InvalidDataException;
import com.payline.payment.alipay.exception.PluginException;
import com.payline.payment.alipay.utils.Constants;
import com.payline.payment.alipay.utils.PluginUtils;
import com.payline.payment.alipay.utils.SignatureUtils;
import com.payline.pmapi.bean.common.FailureCause;
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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class HttpClient {

    private static final Logger LOGGER = LogManager.getLogger(HttpClient.class);
    private Gson parser;
    // Exceptions messages
    private static final String SERVICE_URL_ERROR = "Service URL is invalid";
    private static final String SYNTAX_ENCODING = "Syntax Exception";
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

        // instantiate Apache HTTP client
        this.client = HttpClientBuilder.create()
                .useSystemProperties()
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

        int retries = 3;
        while (strResponse == null && attempts <= retries) {
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
     *
     * @param requestConfiguration
     * @param params
     */
    public boolean notificationIsVerified(RequestConfiguration requestConfiguration, ArrayList<NameValuePair> params) {
        boolean result = false;
        // Get the result of the request
        StringResponse response = getWithSignature(requestConfiguration, params);
        if (response.getStatusCode() == 200) {
            if (Boolean.valueOf(response.getContent())) {
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
    public AlipayAPIResponse getTransactionStatus(RequestConfiguration requestConfiguration, ArrayList<NameValuePair> params) {
        // Get the result of the request
        StringResponse response = getWithSignature(requestConfiguration, params);

        AlipayAPIResponse alipayAPIResponse = AlipayAPIResponse.fromXml(response.getContent());

        return alipayAPIResponse;
    }

    /**
     * Build and send a request to get a refund
     *
     * @param requestConfiguration
     * @param params
     * @return The response converted as a {@link AlipayAPIResponse}.
     */
    public AlipayAPIResponse getRefund(RequestConfiguration requestConfiguration, ArrayList<NameValuePair> params) {

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
     * @param parametersList
     * @return Parameters string for redirecting post request
     */
    public LinkedHashMap<String, String> getParametersPayment(ArrayList<NameValuePair> parametersList) throws UnsupportedEncodingException {
        //Create parameters list
        List<NameValuePair> parametersListWithSign = SignatureUtils.getSignedParameters(parametersList);

        LinkedHashMap<String, String> parametersListForResponse = new LinkedHashMap<>();
        for (NameValuePair parameter : parametersListWithSign) {
            parametersListForResponse.put(parameter.getName(), URLEncoder.encode(parameter.getValue(), StandardCharsets.UTF_8.toString()));
        }
        return parametersListForResponse;
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
        String preSigning = SignatureUtils.preSigningString(params);

        // Create the RSA2 signature
        String sha256withRsa = SignatureUtils.signSHA256withRSA(preSigning);

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

        // Timeout
        final String readTimeout = requestConfiguration.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.READ_TIMEOUT);
        final String connectTimeout = requestConfiguration.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.CONNECT_TIMEOUT);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Integer.valueOf(connectTimeout))
                .setSocketTimeout(Integer.valueOf(readTimeout))
                .build();

        httpGet.setConfig(requestConfig);

        // Execute request
        return this.execute(httpGet);
    }

}
