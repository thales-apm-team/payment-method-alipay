import com.payline.payment.alipay.MockUtils;
import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.payment.alipay.bean.response.APIResponse;
import com.payline.payment.alipay.utils.http.HttpClient;
import com.payline.pmapi.logger.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


class MainTester {
    private static final Logger LOGGER = LogManager.getLogger(MainTester.class);
    private static final HttpClient HTTP_CLIENT = HttpClient.getInstance();
    /**------------------------------------------------------------------------------------------------------------------*/
    public static void main(String[] args) throws IOException {
        Boolean connectionStatus;
        APIResponse APIResponse;

        try {

            RequestConfiguration requestConfiguration = new RequestConfiguration(MockUtils.aContractConfigurationToVerifyConnection(), MockUtils.anEnvironment(), MockUtils.aPartnerConfiguration());

            // Test : VerifyPrivateKey
//            connectionStatus = HTTP_CLIENT.verifyConnection(requestConfiguration);
//            LOGGER.info("Private Key Status : " + connectionStatus);


            // Test : Single trade query
            requestConfiguration = new RequestConfiguration(MockUtils.aContractConfiguration(), MockUtils.anEnvironment(), MockUtils.aPartnerConfiguration());
            //alipayAPIResponse = HTTP_CLIENT.getTransactionStatus(requestConfiguration);
            //LOGGER.info("Api response : " + alipayAPIResponse.getIs_success());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**------------------------------------------------------------------------------------------------------------------*/
}
