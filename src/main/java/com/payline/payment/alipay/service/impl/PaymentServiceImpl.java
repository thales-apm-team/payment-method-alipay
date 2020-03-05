package com.payline.payment.alipay.service.impl;
import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.payment.alipay.exception.PluginException;
import com.payline.payment.alipay.utils.PluginUtils;
import com.payline.payment.alipay.utils.http.HttpClient;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseRedirect;
import com.payline.pmapi.logger.LogManager;
import com.payline.pmapi.service.PaymentService;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);
    private static final HttpClient client = HttpClient.getInstance();
    @Override
    public PaymentResponse paymentRequest(PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = null;
        RequestConfiguration configuration = new RequestConfiguration(
                paymentRequest.getContractConfiguration()
                , paymentRequest.getEnvironment()
                , paymentRequest.getPartnerConfiguration()
        );
        String service;
        String product_code;
        if(PluginUtils.userIsOnPC)
        {
            service = "create_forex_trade";
            product_code = "NEW_OVERSEAS_SELLER";
        }
        else
        {
            service = "create_forex_trade_wap";
            product_code = "NEW_WAP_OVERSEAS_SELLER";
        }

        ArrayList<NameValuePair> postFormData = new ArrayList<>();
        try {
            postFormData.add(new BasicNameValuePair("_input_charset", "utf-8"));
            postFormData.add(new BasicNameValuePair("currency", paymentRequest.getOrder().getAmount().getCurrency().getCurrencyCode()));
            postFormData.add(new BasicNameValuePair("out_trade_no", paymentRequest.getTransactionId()));
            postFormData.add(new BasicNameValuePair("partner", paymentRequest.getContractConfiguration().getProperty("PARTNER_ID").getValue()));
            postFormData.add(new BasicNameValuePair("product_code", product_code));
            postFormData.add(new BasicNameValuePair("refer_url", "https://www.google.fr"));
            postFormData.add(new BasicNameValuePair("service", service));
            postFormData.add(new BasicNameValuePair("subject", "Test"));
            postFormData.add(new BasicNameValuePair("total_fee", paymentRequest.getAmount().getAmountInSmallestUnit().toString()));
            List<NameValuePair> parametersListWithSign = client.getParamsWithSign(postFormData, StandardCharsets.UTF_8.toString());
            parametersListWithSign.add(new BasicNameValuePair("sign_type", "RSA2"));
            LinkedHashMap<String, String> parametersListForResponse = new LinkedHashMap<>();
            for (NameValuePair parameter : parametersListWithSign) {
                parametersListForResponse.put(parameter.getName(), parameter.getValue());
            }
            PaymentResponseRedirect.RedirectionRequest redirectionRequest;
            redirectionRequest = PaymentResponseRedirect.RedirectionRequest.RedirectionRequestBuilder.aRedirectionRequest()
                    .withRequestType(PaymentResponseRedirect.RedirectionRequest.RequestType.POST)
                    .withUrl(new URL(paymentRequest.getPartnerConfiguration().getProperty("ALIPAY_URL")))
                    .withPostFormData(parametersListForResponse).build();

            paymentResponse = PaymentResponseRedirect.PaymentResponseRedirectBuilder.aPaymentResponseRedirect()
                    .withPartnerTransactionId(paymentRequest.getTransactionId())
                    .withRedirectionRequest(redirectionRequest)
                    .build();
        } catch (PluginException e) {
            paymentResponse = e.toPaymentResponseFailureBuilder().build();
        } catch (RuntimeException e) {
            LOGGER.error("Unexpected plugin error", e);
            paymentResponse = PaymentResponseFailure.PaymentResponseFailureBuilder
                    .aPaymentResponseFailure()
                    .withErrorCode(PluginException.runtimeErrorCode(e))
                    .withFailureCause(FailureCause.INTERNAL_ERROR)
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return paymentResponse;
    }
}
