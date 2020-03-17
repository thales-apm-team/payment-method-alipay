package com.payline.payment.alipay.service.impl;

import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.payment.alipay.bean.request.CreateForexTrade;
import com.payline.payment.alipay.bean.request.ForexRefund;
import com.payline.payment.alipay.bean.response.AlipayAPIResponse;
import com.payline.payment.alipay.exception.PluginException;
import com.payline.payment.alipay.utils.Constants;
import com.payline.payment.alipay.utils.PluginUtils;
import com.payline.payment.alipay.utils.http.HttpClient;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.refund.request.RefundRequest;
import com.payline.pmapi.bean.refund.response.RefundResponse;
import com.payline.pmapi.bean.refund.response.impl.RefundResponseFailure;
import com.payline.pmapi.bean.refund.response.impl.RefundResponseSuccess;
import com.payline.pmapi.logger.LogManager;
import com.payline.pmapi.service.RefundService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import java.util.Date;

public class RefundServiceImpl implements RefundService {
    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);
    private static final HttpClient client = HttpClient.getInstance();

    @Override
    public RefundResponse refundRequest(RefundRequest refundRequest) {
        try {
            RequestConfiguration configuration = new RequestConfiguration(refundRequest.getContractConfiguration(), refundRequest.getEnvironment(), refundRequest.getPartnerConfiguration());
            String service, product_code;
            if (PluginUtils.userIsOnPC) {
                service = "forex_refund";
                product_code = "NEW_OVERSEAS_SELLER";
            } else {
                service = "forex_refund";
                product_code = "NEW_WAP_OVERSEAS_SELLER";
            }
            //On crée un object ForexRefund pour mapper les données de la requête
            ForexRefund forexRefund = ForexRefund.ForexRefundBuilder
                    .aForexRefund()
                    .withCurrency(refundRequest.getOrder().getAmount().getCurrency().getCurrencyCode())
                    .withGmtReturn(new Date().toString())
                    .withIsSync("y")
                    .withOutReturnNo(refundRequest.getTransactionId())
                    .withOutTradeNo(refundRequest.getTransactionId())
                    .withPartner(refundRequest.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.PARTNER_ID).getValue())
                    .withProductCode(product_code)
                    .withReturnAmount(refundRequest.getAmount().getAmountInSmallestUnit().toString())
                    .withService(service)
                    .build();

            AlipayAPIResponse refundAlipayAPIResponse = client.getRefund(configuration, forexRefund.getParametersList());

            if (refundAlipayAPIResponse.getIs_success().equals("T")) {
                return RefundResponseSuccess.RefundResponseSuccessBuilder.aRefundResponseSuccess()
                        .withPartnerTransactionId(refundRequest.getPartnerTransactionId())
                        .withStatusCode("200")
                        .build();
            }
            return RefundResponseFailure.RefundResponseFailureBuilder.aRefundResponseFailure()
                    .withErrorCode(refundAlipayAPIResponse.getError())
                    .withFailureCause(PluginUtils.getFailureCause(refundAlipayAPIResponse.getError()))
                    .build();
        } catch (PluginException e) {
            return RefundResponseFailure.RefundResponseFailureBuilder.aRefundResponseFailure()
                    .withFailureCause(PluginUtils.getFailureCause(e.getFailureCause().toString()))
                    .build();
        }
        catch (RuntimeException e) {
            return RefundResponseFailure.RefundResponseFailureBuilder.aRefundResponseFailure()
                    .withFailureCause(FailureCause.INTERNAL_ERROR)
                    .build();
        }
    }

    @Override
    public boolean canMultiple() {
        return false;
    }

    @Override
    public boolean canPartial() {
        return true;
    }

}
