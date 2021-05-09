package com.fxsh.file;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty({"minio.server.endpoint","minio.server.accessKey","minio.server.secretKey"})
public class MinioConfig {
    @Value("${minio.server.endpoint}")
    private String endpoint;
    @Value("${minio.server.accessKey}")
    private String accessKey;
    @Value("${minio.server.secretKey}")
    private String secretKey;
    @Bean
    public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(endpoint,accessKey,secretKey);
    }
}
