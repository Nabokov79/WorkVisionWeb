package ru.nabokovsg.measurementqc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value(value = "${http://localhost:8084}")
    private String libraryUrl;

    @Value(value = "${http://localhost:8083}")
    private String equipmentUrl;

    @Bean
    public WebClient webLibrary() {
        return WebClient.builder()
                .baseUrl(libraryUrl)
                .build();
    }

    @Bean
    public WebClient webEquipment() {
        return WebClient.builder()
                .baseUrl(equipmentUrl)
                .build();
    }
}
