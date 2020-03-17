package com.payline.payment.alipay.utils;

import com.payline.payment.alipay.exception.InvalidDataException;
import com.payline.payment.alipay.exception.PluginException;
import com.payline.pmapi.logger.LogManager;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.List;

public class SignatureUtils {
    private static final Logger LOGGER = LogManager.getLogger(SignatureUtils.class);
    private static final String KEY_STORE_PATH = "/home/ubuntu/Documents/Monext/workspace/AlipaySandbox20200115.p12";
    private static final String SIGNATURE_ENCODING = "Signature Exception";
    /**------------------------------------------------------------------------------------------------------------------*/
    /**
     * Get params with sign
     *
     * @param params
     * @return
     */
    public static List<NameValuePair> getSignedParameters(List<NameValuePair> params) {
        try {
            // Create the pre-signing string
            String preSigning = preSigningString(params);

            // Create the RSA2 signature
            String sha256withRsa = signSHA256withRSA(preSigning);

            // Add the signature to the parameters
            params.add(new BasicNameValuePair("sign", sha256withRsa));
            params.add(new BasicNameValuePair("sign_type", "RSA2"));
            return params;
        } catch (Exception e) {
            throw new InvalidDataException(SIGNATURE_ENCODING, e);
        }
    }
    /**------------------------------------------------------------------------------------------------------------------*/
    /**
     * Format parameters to generate a signature
     *
     * @param params
     * @return Parameters string to generate a signature
     */
    public static String preSigningString(List<NameValuePair> params) {
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
    public static String signSHA256withRSA(String preSigningString) {  ///NoSuchAlgorithmException, InvalidKeyException, SignatureException
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(getPk());
            signature.update(preSigningString.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (PluginException | NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
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
        } catch (PluginException | GeneralSecurityException | IOException e) {
            LOGGER.error("getPk", e);
        }
        return null;
    }
    /**------------------------------------------------------------------------------------------------------------------*/
}
