package com.sky.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//  binding external configuration values
//  (like those from application.yml or application.properties)
//  to Java objects.
@Component
@ConfigurationProperties(prefix = "sky.s3")
@Data
public class S3Properties {
    private String region;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
