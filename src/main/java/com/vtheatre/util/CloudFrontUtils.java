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

@Component
public class CloudFrontUtils {

    @Value("${cloudfoundry.domain}")
    private String distributionDomain;

    @Value("${cloudfoundry.private.key}")
    private String privateKeyFilePath;

    @Value("${cloudfoundry.keypair.id}")
    private String keyPairId;

    /**
     * Signed URLS are generated to access private S3 objects through Cloudfront
     * 
     * @param s3ObjectKey the key for the object in S3
     * @return the signedUrl
     */
    public String generateSignedUrl(String s3ObjectKey) {
        // Urls expire in 30 seconds
        String time = Instant.now().plusMillis(1000 * 30).toString();

        try {
            return CloudFrontUrlSigner.getSignedURLWithCannedPolicy(SignerUtils.Protocol.https, distributionDomain,
                    new File(privateKeyFilePath), s3ObjectKey, keyPairId, DateUtils.parseISO8601Date(time));
        } catch (InvalidKeySpecException | IOException e) {
            e.printStackTrace();
        }

        return "";

    }

}
