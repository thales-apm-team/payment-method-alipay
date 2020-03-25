package com.payline.payment.alipay.utils;

import com.payline.payment.alipay.bean.configuration.RequestConfiguration;
import com.payline.payment.alipay.exception.InvalidDataException;
import com.payline.payment.alipay.exception.PluginException;
import com.payline.payment.alipay.utils.constant.PartnerConfigurationKeys;
import com.payline.pmapi.logger.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;

public class SignatureUtils {
    private static final Logger LOGGER = LogManager.getLogger(SignatureUtils.class); // todo utiliser le logger

    private static class Holder {
        private static final SignatureUtils instance = new SignatureUtils();
    }

    /**
     * ------------------------------------------------------------------------------------------------------------------
     */
    public static SignatureUtils getInstance() {
        return SignatureUtils.Holder.instance;
    }


    public Map<String, String> getSignedParameters(RequestConfiguration configuration, Map<String, String> params) {
        // Create the RSA2 signature
        PrivateKey privateKey = getPrivateKey(configuration);
        String preSigning = mapToString(params);
        String sha256withRsa = signSHA256withRSA(privateKey, preSigning);

        // Add the signature to the parameters
        params.put("sign", sha256withRsa);
        params.put("sign_type", "RSA2");
        return params;
    }

    public boolean getVerification(RequestConfiguration configuration, Map<String, String> map) {
        // extract data
        PublicKey publicKey = getPublicKey(configuration);
        String messageToCompare = mapToString(map);
        String signature = map.get("sign");

        // execute the verification
        return verifySignature(publicKey, messageToCompare, signature);
    }


    public String mapToString(Map<String, String> map) {
        return map.entrySet()
                .stream()
                .filter(e -> !e.getKey().equals("sign_type"))
                .filter(e -> !e.getKey().equals("sign"))
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));
    }


    /**
     * Generate a RSA signature based on the query parameters
     *
     * @param message
     * @return RSA signature
     */
    private String signSHA256withRSA(PrivateKey privateKey, String message) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(message.getBytes(StandardCharsets.UTF_8));

            byte[] signatureBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signatureBytes);

        } catch (NoSuchAlgorithmException e) {
            // should never append, "SHA256withRSA" is a valid algorithm
            throw new PluginException("Invalid algorithm", e);
        } catch (SignatureException e) {
            throw new PluginException("Unable to sign the message", e);
        } catch (InvalidKeyException e) {
            throw new PluginException("Invalid Key", e);
        }
    }

    /**
     * verify if the signature is valid
     *
     * @param publicKey the Key to use to verify the signature
     * @param message   the message to compare
     * @param signature the signature of the message
     * @return
     */
    private boolean verifySignature(PublicKey publicKey, String message, String signature) {
        try {
            Signature publicSignature = Signature.getInstance("SHA256withRSA");
            publicSignature.initVerify(publicKey);
            publicSignature.update(message.getBytes(StandardCharsets.UTF_8));

            byte[] signatureBytes = Base64.getDecoder().decode(signature);
            return publicSignature.verify(signatureBytes);

        } catch (NoSuchAlgorithmException e) {
            // should never append, "SHA256withRSA" is a valid algorithm
            throw new PluginException("Invalid algorithm", e);
        } catch (SignatureException e) {
            throw new PluginException("Unable to verify the signature", e);
        } catch (InvalidKeyException e) {
            throw new PluginException("Invalid Key", e);
        }
    }


    private PublicKey getPublicKey(RequestConfiguration configuration) {
        try {
            // check if privateKey exists in partnerConfiguration
            String sKey = configuration.getPartnerConfiguration().getProperty(PartnerConfigurationKeys.PUBLIC_KEY);
            if (PluginUtils.isEmpty(sKey)) {
                throw new InvalidDataException("Missing private key from partner configuration");
            }

            // extract byte key
            String privKeyPEM = sKey.replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replace("\n", "");
            byte[] decoded = Base64.getDecoder().decode(privKeyPEM);

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);

        } catch (NoSuchAlgorithmException e) {
            // should never append, "RSA" is a valid algorithm
            throw new PluginException("Invalid algorithm", e);
        } catch (InvalidKeySpecException e) {
            throw new PluginException("Invalid KeySpec", e);
        }
    }

    private PrivateKey getPrivateKey(RequestConfiguration configuration) {
        try {
            // check if privateKey exists in partnerConfiguration
            String sKey = configuration.getPartnerConfiguration().getProperty(PartnerConfigurationKeys.PAYLINE_PRIVATE_KEY);
            if (PluginUtils.isEmpty(sKey)) {
                throw new InvalidDataException("Missing private key from partner configuration");
            }

            // extract byte key
            String privKeyPEM = sKey.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replace("\n", "");
            byte[] decoded = Base64.getDecoder().decode(privKeyPEM);

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);

        } catch (NoSuchAlgorithmException e) {
            // should never append, "RSA" is a valid algorithm
            throw new PluginException("Invalid algorithm", e);
        } catch (InvalidKeySpecException e) {
            throw new PluginException("Invalid KeySpec", e);
        }
    }
}
