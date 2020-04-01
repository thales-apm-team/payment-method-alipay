package com.payline.payment.alipay.utils;

import com.google.gson.Gson;
import com.payline.payment.alipay.bean.request.EndTransactionNotificationRequest;
import com.payline.payment.alipay.bean.response.NotificationMessage;
import com.payline.payment.alipay.utils.constant.ContractConfigurationKeys;
import com.payline.payment.alipay.utils.constant.PartnerConfigurationKeys;
import com.payline.pmapi.bean.notification.request.NotificationRequest;
import com.payline.pmapi.bean.payment.ContractConfiguration;
import com.payline.pmapi.bean.refund.request.RefundRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EndTransactionNotificationUtils {
    private static final DateFormat timeFormatter = new SimpleDateFormat("hhmmss");
    private static final DateFormat dateFormatter = new SimpleDateFormat("MMDD");
    private static final DateFormat yearFormatter = new SimpleDateFormat("YY");
    private static final DateFormat fullFormatter = new SimpleDateFormat("YYYYMMDDhhmmss");

    private static final String trsPmtCtxCrdhldrPres = "Y";
    private static final String ert = "24";


    public static String toJson(EndTransactionNotificationRequest request) {
        Gson gson = new Gson();
        return gson.toJson(request);
    }

    public static EndTransactionNotificationRequest createFromNotificationService(NotificationMessage notificationMessage, NotificationRequest request, boolean success) {
        String message = success ? "0236" : "0256";
        String tagTransactionType = "20";
        String tagAmountAuthorized = notificationMessage.getTotalFee().replace(".", "");
        String partnerCurrency = notificationMessage.getCurrency();
        String tagTransactionTime = timeFormatter.format(notificationMessage.getNotifyTime());
        String tagTransactionDate = dateFormatter.format(notificationMessage.getNotifyTime());
        String tagTransactionYear = yearFormatter.format(notificationMessage.getNotifyTime());
        String schemePaymentTime = fullFormatter.format(notificationMessage.getNotifyTime());
        String tagAcqIdentifier = createTagAcqIdentifier(request.getContractConfiguration());
        String scheme = request.getPartnerConfiguration().getProperty(PartnerConfigurationKeys.SCHEME);
        String schemeTransId = notificationMessage.getOutTradeNo();
        String partnerTransId = createPartnerTransId();
        String partnerTransName = request.getContractConfiguration().getPaymentMethodIdentifier();
        String buyerId = "";// todo
        String partnerId = ""; //todo
        String merchantId = ""; // todo secondaryMerchantID
        String mccCode = request.getContractConfiguration().getProperty(ContractConfigurationKeys.SECONDARY_MERCHANT_INDUSTRY).getValue();
        String storeId = ""; //todo secondaryMerchantName
        String messageSenderId = request.getPartnerConfiguration().getProperty(PartnerConfigurationKeys.MESSAGE_SENDER_ID);

        return EndTransactionNotificationRequest.Builder
                .anEndTransactionNotificationRequest()
                .withMessage(message)
                .withTagTransactionType(tagTransactionType)
                .withTagAmountAuthorized(tagAmountAuthorized)
                .withPartnerCurrency(partnerCurrency)
                .withTagTransactionTime(tagTransactionTime)
                .withTagTransactionDate(tagTransactionDate)
                .withTagTransactionYear(tagTransactionYear)
                .withSchemePaymentTime(schemePaymentTime)
                .withTrsPmtCtxCrdhldrPres(trsPmtCtxCrdhldrPres)
                .withTagAcqIdentifier(tagAcqIdentifier)
                .withErt(ert)
                .withScheme(scheme)
                .withSchemeTransId(schemeTransId)
                .withPartnerTransId(partnerTransId)
                .withPartnerTransName(partnerTransName)
                .withBuyerId(buyerId)
                .withPartnerId(partnerId)
                .withMerchantId(merchantId)
                .withMccCode(mccCode)
                .withStoreId(storeId)
                .withMessageSenderId(messageSenderId)
                .build();
    }


    public static EndTransactionNotificationRequest createFromRefundService(RefundRequest request) {
        String message = "0456";
        String tagTransactionType = "20";
        String tagAmountAuthorized = request.getAmount().getAmountInSmallestUnit().toString();
        String partnerCurrency = request.getAmount().getCurrency().getCurrencyCode();


        String tagTransactionTime = timeFormatter.format(request.getOrder().getDate());
        String tagTransactionDate = dateFormatter.format(request.getOrder().getDate());
        String tagTransactionYear = yearFormatter.format(request.getOrder().getDate());
        String schemePaymentTime = fullFormatter.format(request.getOrder().getDate());
        String tagAcqIdentifier = createTagAcqIdentifier(request.getContractConfiguration());
        String scheme = request.getPartnerConfiguration().getProperty(PartnerConfigurationKeys.SCHEME);
        String schemeTransId = request.getTransactionId();
        String partnerTransId = createPartnerTransId();
        String partnerTransName = request.getContractConfiguration().getPaymentMethodIdentifier();
        String buyerId = request.getContractConfiguration().getProperty(ContractConfigurationKeys.SUPPLIER).getValue();
        String partnerId = ""; //todo
        String merchantId = ""; // todo
        String mccCode = request.getContractConfiguration().getProperty(ContractConfigurationKeys.SECONDARY_MERCHANT_INDUSTRY).getValue();
        String storeId = ""; //todo
        String messageSenderId = request.getPartnerConfiguration().getProperty(PartnerConfigurationKeys.MESSAGE_SENDER_ID);

        return EndTransactionNotificationRequest.Builder
                .anEndTransactionNotificationRequest()
                .withMessage(message)
                .withTagTransactionType(tagTransactionType)
                .withTagAmountAuthorized(tagAmountAuthorized)
                .withPartnerCurrency(partnerCurrency)
                .withTagTransactionTime(tagTransactionTime)
                .withTagTransactionDate(tagTransactionDate)
                .withTagTransactionYear(tagTransactionYear)
                .withSchemePaymentTime(schemePaymentTime)
                .withTrsPmtCtxCrdhldrPres(trsPmtCtxCrdhldrPres)
                .withTagAcqIdentifier(tagAcqIdentifier)
                .withErt(ert)
                .withScheme(scheme)
                .withSchemeTransId(schemeTransId)
                .withPartnerTransId(partnerTransId)
                .withPartnerTransName(partnerTransName)
                .withBuyerId(buyerId)
                .withPartnerId(partnerId)
                .withMerchantId(merchantId)
                .withMccCode(mccCode)
                .withStoreId(storeId)
                .withMessageSenderId(messageSenderId)
                .build();
    }


    private static String createTagAcqIdentifier(ContractConfiguration configuration){
        return configuration.getProperty(ContractConfigurationKeys.MERCHANT_BANK).getValue()
                + configuration.getProperty(ContractConfigurationKeys.MERCHANT_BANK_CODE).getValue();
    }


    private static String createPartnerTransId(){
        return "StoreIDc" + fullFormatter.format(new Date());    // todo storeId + timestamp

    }


}
