package com.sky.config;

import com.sky.properties.S3Properties;
import com.sky.utils.S3Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class S3Configuration {
    @Bean
    @ConditionalOnMissingBean
    public S3Util s3Util(S3Properties s3Properties) {
        log.info("Creating S3Util for file uploading:" +  s3Properties);
        S3Util s3Utiln= new S3Util(s3Properties.getRegion(),
                s3Properties.getAccessKeyId(),
                s3Properties.getAccessKeySecret(),
                s3Properties.getBucketName());
        return s3Utiln;
    }
}
