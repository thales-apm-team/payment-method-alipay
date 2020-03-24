package com.payline.payment.alipay.utils.http;

import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.payment.alipay.bean.response.AlipayAPIResponse;
import com.payline.payment.alipay.bean.response.NotifyResponse;
import com.payline.payment.alipay.exception.InvalidDataException;
import com.payline.payment.alipay.exception.PluginException;
import com.payline.payment.alipay.utils.PluginUtils;
import com.payline.payment.alipay.utils.SignatureUtils;
import com.payline.payment.alipay.utils.constant.PartnerConfigurationKeys;
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
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class HttpClient {
    private static final Logger LOGGER = LogManager.getLogger(HttpClient.class);

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
     * Build and send request to verify a notification by its id
     * Verification is done by calling an Alipay's service
     *
     * @param requestConfiguration
     * @param params
     */
    public NotifyResponse notificationIsVerified(RequestConfiguration requestConfiguration, ArrayList<NameValuePair> params) {
        // Get the result of the request
        StringResponse response = getWithSignature(requestConfiguration, params);
        return NotifyResponse.valueOf(response.getContent().toUpperCase());
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
        return AlipayAPIResponse.fromXml(response.getContent());
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
        if (response.getStatusCode() == 200) {  // todo pk on fait ca ici et pas pour les autres appels?
            alipayAPIResponse = AlipayAPIResponse.fromXml(response.getContent());
        } else {
            alipayAPIResponse = new AlipayAPIResponse();
            alipayAPIResponse.setIs_success("F");
            alipayAPIResponse.setError("404 Not Found");

        }

        return alipayAPIResponse;
    }

    /**------------------------------------------------------------------------------------------------------------------*/

    /**
     * Manage get API call with signature
     *
     * @param requestConfiguration
     * @return
     */
    public StringResponse getWithSignature(RequestConfiguration requestConfiguration, List<NameValuePair> params) {
        try {
            // Create the HttpGet url with parameters
            URI baseUrl = constructURL(requestConfiguration);
            URI uri = new URIBuilder(baseUrl)
                    .setParameters(SignatureUtils.getSignedParameters(params))
                    .build();

            // Create the HttpGet request
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setConfig(createRequestConfig(requestConfiguration));

            // Execute request
            return this.execute(httpGet);

        } catch (URISyntaxException e) {
            throw new InvalidDataException("Syntax Exception", e);
        }
    }

    /**
     * Construct an URI url from a String given in PartnerConfiguration
     *
     * @param requestConfiguration Contain the String to convert
     * @return
     */
    private URI constructURL(RequestConfiguration requestConfiguration) {
        // check if URL exists in partnerConfiguration
        if (requestConfiguration.getPartnerConfiguration().getProperty(PartnerConfigurationKeys.ALIPAY_URL) == null) {
            throw new InvalidDataException("Missing API url from partner configuration");
        }

        try {
            return new URI(requestConfiguration.getPartnerConfiguration().getProperty(PartnerConfigurationKeys.ALIPAY_URL));
        } catch (URISyntaxException e) {
            throw new InvalidDataException("Service URL is invalid", e);
        }
    }

    private RequestConfig createRequestConfig(RequestConfiguration requestConfiguration) {
        // Timeout
        final String readTimeout = requestConfiguration.getPartnerConfiguration().getProperty(PartnerConfigurationKeys.READ_TIMEOUT);
        final String connectTimeout = requestConfiguration.getPartnerConfiguration().getProperty(PartnerConfigurationKeys.CONNECT_TIMEOUT);

        if (PluginUtils.isEmpty(readTimeout) | PluginUtils.isEmpty(connectTimeout)) {
            throw new InvalidDataException("Missing timeout from partner configuration");
        }

        return RequestConfig.custom()
                .setConnectTimeout(Integer.parseInt(connectTimeout))
                .setSocketTimeout(Integer.parseInt(readTimeout))
                .build();
    }

}
