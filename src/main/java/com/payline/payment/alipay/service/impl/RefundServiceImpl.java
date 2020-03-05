package com.payline.payment.alipay.service.impl;

import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.payment.alipay.bean.response.Response;
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

public class RefundServiceImpl implements RefundService {
    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);
    private static final HttpClient client = HttpClient.getInstance();
    public boolean userIsOnPC = false;
    @Override
    public RefundResponse refundRequest(RefundRequest refundRequest) {
        try {
            RequestConfiguration configuration = new RequestConfiguration(refundRequest.getContractConfiguration(), refundRequest.getEnvironment(), refundRequest.getPartnerConfiguration());
            Response refundResponse = client.refund(configuration, refundRequest);
            try {
                if (refundResponse.getIs_success().equals("T")) {
                    return RefundResponseSuccess.RefundResponseSuccessBuilder.aRefundResponseSuccess()
                            .withPartnerTransactionId(refundRequest.getPartnerTransactionId())
                            .withStatusCode("200")
                            .build();
                }
                return RefundResponseFailure.RefundResponseFailureBuilder.aRefundResponseFailure()
                        .withErrorCode(refundResponse.getError())
                        .withFailureCause(PluginUtils.getFailureCause(refundResponse.getError()))
                        .build();
            } catch (Exception e) {
                return RefundResponseFailure.RefundResponseFailureBuilder.aRefundResponseFailure()
                        .withFailureCause(FailureCause.INVALID_DATA)
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RefundResponseFailure.RefundResponseFailureBuilder.aRefundResponseFailure()
                    .withFailureCause(FailureCause.INVALID_DATA)
                    .build();
        }
    }

    @Override
    public boolean canMultiple() {
        return false;
    }

    @Override
    public boolean canPartial() {
        return false;
    }

}
