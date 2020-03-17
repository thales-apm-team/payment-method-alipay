package com.payline.payment.alipay.service.impl;

import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.payment.alipay.exception.PluginException;
import com.payline.payment.alipay.utils.Constants;
import com.payline.payment.alipay.utils.PluginUtils;
import com.payline.payment.alipay.utils.http.HttpClient;
import com.payline.payment.alipay.utils.i18n.I18nService;
import com.payline.payment.alipay.utils.properties.ReleaseProperties;
import com.payline.pmapi.bean.configuration.ReleaseInformation;
import com.payline.pmapi.bean.configuration.parameter.AbstractParameter;
import com.payline.pmapi.bean.configuration.parameter.impl.InputParameter;
import com.payline.pmapi.bean.configuration.request.ContractParametersCheckRequest;
import com.payline.pmapi.service.ConfigurationService;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConfigurationServiceImpl implements ConfigurationService {

    // TODO: remove if not used !
    private static final String I18N_CONTRACT_PREFIX = "contract.";
    private I18nService i18n = I18nService.getInstance();
    private ReleaseProperties releaseProperties = ReleaseProperties.getInstance();
    private HttpClient httpClient = HttpClient.getInstance();
    /**------------------------------------------------------------------------------------------------------------------*/
    @Override
    public List<AbstractParameter> getParameters(Locale locale) {
        List<AbstractParameter> parameters = new ArrayList<>();

        // PARTNER ID
        InputParameter partnerId = new InputParameter();
        partnerId.setKey( Constants.ContractConfigurationKeys.PARTNER_ID );
        partnerId.setLabel( i18n.getMessage("contract.PARTNER_ID.label", locale) );
        partnerId.setDescription( i18n.getMessage("contract.PARTNER_ID.description", locale) );
        partnerId.setRequired( true );
        parameters.add( partnerId );

        // SUPPLIER
        InputParameter supplier = new InputParameter();
        supplier.setKey( Constants.ContractConfigurationKeys.SUPPLIER );
        supplier.setLabel( i18n.getMessage("contract.SUPPLIER.label", locale) );
        supplier.setDescription( i18n.getMessage("contract.SUPPLIER.description", locale) );
        supplier.setRequired( true );
        parameters.add( supplier );

        // SECONDARY_MERCHANT_ID
        InputParameter secondaryMerchantId = new InputParameter();
        secondaryMerchantId.setKey( Constants.ContractConfigurationKeys.SECONDARY_MERCHANT_ID );
        secondaryMerchantId.setLabel( i18n.getMessage("contract.SECONDARY_MERCHANT_ID.label", locale) );
        secondaryMerchantId.setDescription( i18n.getMessage("contract.SECONDARY_MERCHANT_ID.description", locale) );
        secondaryMerchantId.setRequired( true );
        parameters.add( secondaryMerchantId );

        // NOTIFY_URL
        InputParameter notifyUrl = new InputParameter();
        notifyUrl.setKey( Constants.ContractConfigurationKeys.NOTIFY_URL );
        notifyUrl.setLabel( i18n.getMessage("contract.NOTIFY_URL.label", locale) );
        notifyUrl.setDescription( i18n.getMessage("contract.NOTIFY_URL.description", locale) );
        notifyUrl.setRequired( true );
        parameters.add( notifyUrl );

        return parameters;
    }
    /**------------------------------------------------------------------------------------------------------------------*/
    @Override
    public Map<String, String> check(ContractParametersCheckRequest contractParametersCheckRequest) {
        final Map<String, String> errors = new HashMap<>();

        Map<String, String> accountInfo = contractParametersCheckRequest.getAccountInfo();
        Locale locale = contractParametersCheckRequest.getLocale();

        // check required fields
        for( AbstractParameter param : this.getParameters( locale ) ){
            if( param.isRequired() && PluginUtils.isEmpty(accountInfo.get( param.getKey()))){
                String message = i18n.getMessage(I18N_CONTRACT_PREFIX + param.getKey() + ".requiredError", locale);
                errors.put( param.getKey(), message );
            }
        }

        // Check the connection to the API by executing the verifyConnection function
        RequestConfiguration requestConfiguration = RequestConfiguration.build( contractParametersCheckRequest );


        // If partner id is missing, no need to go further, as it is required
        String partnerId = Constants.ContractConfigurationKeys.PARTNER_ID;

        if( !errors.isEmpty()){
            return errors;
        }

        try {
            // Try to retrieve an access token
            httpClient.verifyConnection(requestConfiguration);
        }
        catch( PluginException e ){
            // If an exception is thrown, it means that the client private key is wrong
            errors.put( partnerId, e.getErrorCode() );
        }
        catch(RuntimeException e)
        {
            errors.put( "RuntimeException", e.getMessage() );
        }
        return errors;
    }
    /**------------------------------------------------------------------------------------------------------------------*/
    @Override
    public ReleaseInformation getReleaseInformation() {
        return ReleaseInformation.ReleaseBuilder.aRelease()
                .withDate(LocalDate.parse(releaseProperties.get("release.date"), DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .withVersion(releaseProperties.get("release.version"))
                .build();
    }
    /**------------------------------------------------------------------------------------------------------------------*/
    @Override
    public String getName(Locale locale) {
        return i18n.getMessage("paymentMethod.name", locale);
    }
    /**------------------------------------------------------------------------------------------------------------------*/

}


