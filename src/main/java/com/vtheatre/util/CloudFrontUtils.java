package com.vtheatre.util;

import java.io.File;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;

import com.amazonaws.services.cloudfront.util.SignerUtils;
import com.amazonaws.util.DateUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CloudFrontUtils {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${cloudfront.domain}")
    private String distributionDomain;

    @Value("${cloudfront.private.key.path}")
    private String privateKeyFilePath;

    @Value("${cloudfront.private.key.name}")
    private String privateKeyFileName;

    @Value("${cloudfront.private.key.extension}")
    private String privateKeyFileExtension;

    @Value("${cloudfront.keypair.id}")
    private String keyPairId;

    /**
     * Signed URLS are generated to access private S3 objects through Cloudfront
     * 
     * @param s3ObjectKey the key for the object in S3
     * @return the signedUrl
     */
    public String generateSignedUrl(String s3ObjectKey) {
        // Urls expire in 60 seconds
        StringBuilder time = new StringBuilder(Instant.now().plusMillis(1000 * 60).toString());

        try {

            File privateKeyFile = ResourceUtils.getFileFromResources(privateKeyFilePath, privateKeyFileName,
                    privateKeyFileExtension);
            return CloudFrontUrlSigner.getSignedURLWithCannedPolicy(SignerUtils.Protocol.https, distributionDomain,
                    privateKeyFile, s3ObjectKey, keyPairId, DateUtils.parseISO8601Date(time.toString()));
        } catch (IOException e) {
            logger.error("Error {} for path {}, name {}, and extension {}", e.getMessage(), privateKeyFilePath,
                    privateKeyFileName, privateKeyFileExtension);
        } catch (InvalidKeySpecException e) {
            logger.error("Error {} for signingUrl", e.getMessage());
        }

        return "";

    }
}
