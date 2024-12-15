package ru.nabokovsg.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DocumentTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentTemplateApplication.class, args);
    }
}