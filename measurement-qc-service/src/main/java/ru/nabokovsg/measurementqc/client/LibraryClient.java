package ru.nabokovsg.measurementqc.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import ru.nabokovsg.measurementqc.dto.integration.AcceptableMetalHardnessDto;
import ru.nabokovsg.measurementqc.dto.integration.AcceptableResidualThicknessDto;
import ru.nabokovsg.measurementqc.dto.integration.LibraryDto;

import java.util.Objects;

@Component
@Slf4j
public class LibraryClient {

    private final WebClient client;

    @Autowired
    public LibraryClient(@Qualifier(value = "webLibrary") WebClient client) {
        this.client = client;
    }

    public LibraryDto getLibraryData(String path)  {
        log.info("GET LibraryData URI= {}", path);
        return Objects.requireNonNull(client.get()
                .uri(path)
                .retrieve()
                .bodyToFlux(LibraryDto.class)
                .buffer()
                .blockFirst()).get(0);
    }

    public AcceptableResidualThicknessDto getAcceptableResidualThickness(String path, MultiValueMap<String, String> params) {
        log.info("GET LibraryData URI= {}", path);
        return client.get()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .queryParams(params)
                        .build())
                .retrieve()
                .bodyToMono(AcceptableResidualThicknessDto.class)
                .block();
    }

    public AcceptableMetalHardnessDto getAcceptableMetalHardness(String path, MultiValueMap<String, String> params) {
        log.info("GET LibraryData URI= {}", path);
        return client.get()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .queryParams(params)
                        .build())
                .retrieve()
                .bodyToMono(AcceptableMetalHardnessDto.class)
                .block();
    }
}