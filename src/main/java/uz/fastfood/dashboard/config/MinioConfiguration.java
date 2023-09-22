package uz.fastfood.dashboard.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;

public class MinioConfiguration {
    private final ApplicationProperties applicationProperties;

    public MinioConfiguration(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(applicationProperties.getMinio().getHost())
                .credentials(
                        applicationProperties.getMinio().getUsername(),
                        applicationProperties.getMinio().getPassword()
                ).build();
    }
}