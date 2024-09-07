package com.sky.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Data
@AllArgsConstructor
@Slf4j
public class S3Util {
    private String region;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;


    /**
     * File upload to AWS S3
     *
     * @param bytes      The byte array of the file content
     * @param objectName The name of the file (object key in S3)
     * @return The public URL of the uploaded file
     */
    public String upload(byte[] bytes, String objectName) {

        // Create S3Client instance
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, accessKeySecret);
        S3Client s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

        try {
            // Create PutObject request
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build();

            // Upload the file
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(bytes));

        } catch (S3Exception e) {
            log.error("Failed to upload file to S3: {}", e.awsErrorDetails().errorMessage());
            throw new RuntimeException(e);
        } finally {
            if (s3Client != null) {
                s3Client.close();
            }
        }

        // Public URL format: https://bucket-name.s3.region.amazonaws.com/objectName
        String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, objectName);

        log.info("File uploaded to: {}", fileUrl);
        return fileUrl;
    }
}
