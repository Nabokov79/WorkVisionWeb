package ru.nabokovsg.measurementqc.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import ru.nabokovsg.measurementqc.dto.integration.EquipmentDto;

@Component
public class EquipmentClient {

    private final WebClient client;

    @Autowired
    public EquipmentClient(@Qualifier(value = "webEquipment") WebClient client) {
        this.client = client;
    }

    public EquipmentDto getEquipmentData(String path, MultiValueMap<String, String> params) {
        return client.get()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .queryParams(params)
                        .build())
                .retrieve()
                .bodyToMono(EquipmentDto.class)
                .block();
    }
}