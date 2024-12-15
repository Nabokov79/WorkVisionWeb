package ru.nabokovsg.measurementqc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MeasurementQCApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeasurementQCApplication.class, args);
    }
}