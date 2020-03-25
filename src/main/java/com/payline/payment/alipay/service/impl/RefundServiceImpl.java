package com.payline.payment.alipay.service.impl;

import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.payment.alipay.bean.request.ForexRefund;
import com.payline.payment.alipay.bean.response.AlipayAPIResponse;
import com.payline.payment.alipay.exception.PluginException;
import com.payline.payment.alipay.utils.PluginUtils;
import com.payline.payment.alipay.utils.business.ErrorUtils;
import com.payline.payment.alipay.utils.constant.ContractConfigurationKeys;
import com.payline.payment.alipay.utils.http.HttpClient;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.refund.request.RefundRequest;
import com.payline.pmapi.bean.refund.response.RefundResponse;
import com.payline.pmapi.bean.refund.response.impl.RefundResponseFailure;
import com.payline.pmapi.bean.refund.response.impl.RefundResponseSuccess;
import com.payline.pmapi.logger.LogManager;
import com.payline.pmapi.service.RefundService;
import org.apache.logging.log4j.Logger;

import java.util.Date;

import static com.payline.payment.alipay.bean.object.ForexService.forex_refund;

public class RefundServiceImpl implements RefundService {
    private static final Logger LOGGER = LogManager.getLogger(RefundServiceImpl.class);
    private HttpClient client = HttpClient.getInstance();

    @Override
    public RefundResponse refundRequest(RefundRequest refundRequest) {
        RefundResponse refundResponse;
        try {
            RequestConfiguration configuration = new RequestConfiguration(refundRequest.getContractConfiguration(), refundRequest.getEnvironment(), refundRequest.getPartnerConfiguration());
            String product_code = PluginUtils.userIsOnPC ? "NEW_OVERSEAS_SELLER" : "NEW_WAP_OVERSEAS_SELLER";

            // create ForexRefund request object
            ForexRefund forexRefund = ForexRefund.ForexRefundBuilder
                    .aForexRefund()
                    .withCurrency(refundRequest.getAmount().getCurrency().getCurrencyCode())
                    .withGmtReturn(PluginUtils.formatDate(new Date()))
                    .withIsSync("Y")
                    .withOutReturnNo(refundRequest.getTransactionId())  // refund Id
                    .withOutTradeNo(refundRequest.getTransactionId())   // transaction Id
                    .withPartner(refundRequest.getContractConfiguration().getProperty(ContractConfigurationKeys.MERCHANT_PID).getValue())
                    .withProductCode(product_code)
                    .withReturnAmount(refundRequest.getAmount().getAmountInSmallestUnit().toString()) // todo convertir?
                    .withService(forex_refund)
                    .build();

            // call refund API
            AlipayAPIResponse refundAlipayAPIResponse = client.getRefund(configuration, forexRefund.getParametersList());

            // check the response and return a RefundResponse
            if (refundAlipayAPIResponse.isSuccess()) {
                refundResponse =  RefundResponseSuccess.RefundResponseSuccessBuilder.aRefundResponseSuccess()
                        .withPartnerTransactionId(refundRequest.getPartnerTransactionId())
                        .withStatusCode("200")
                        .build();
            }else {
                refundResponse = RefundResponseFailure.RefundResponseFailureBuilder.aRefundResponseFailure()
                        .withErrorCode(refundAlipayAPIResponse.getError())
                        .withFailureCause(ErrorUtils.getFailureCause(refundAlipayAPIResponse.getError()))
                        .build();
            }

        } catch (PluginException e) {
            LOGGER.error(e.getErrorCode());
            refundResponse = e.toRefundResponseFailureBuilder().build();

        } catch (RuntimeException e) {
            LOGGER.error("Unexpected plugin error", e);
            refundResponse = RefundResponseFailure.RefundResponseFailureBuilder.aRefundResponseFailure()
                    .withErrorCode(PluginException.runtimeErrorCode(e))
                    .withFailureCause(FailureCause.INTERNAL_ERROR)
                    .build();

        }
        return refundResponse;
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
