package ru.nabokovsg.measurementqc.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.nabokovsg.measurementqc.dto.integration.AcceptableMetalHardnessDto;
import ru.nabokovsg.measurementqc.dto.integration.AcceptableResidualThicknessDto;
import ru.nabokovsg.measurementqc.dto.integration.EquipmentDto;
import ru.nabokovsg.measurementqc.dto.integration.LibraryDto;
import ru.nabokovsg.measurementqc.model.measurement.LibraryDataType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeasurementQCClient {

    private final LibraryClient libraryClient;
    private final EquipmentClient equipmentClient;
    private static final String DELIMITER = "/";

    private static final String API_PREFIX_LIBRARY = "/library";

    private static final String API_PREFIX_EQUIPMENT = "/equipment";

    public LibraryDto getLibraryData(Long id, LibraryDataType type) {
        String prefix = "";
        switch (type) {
            case IDENTIFIED_DEFECT, DEFECT -> prefix = "defect";
            case REPAIR -> prefix = "repair";
        }
        return libraryClient.getLibraryData(String.join(DELIMITER, API_PREFIX_LIBRARY, prefix, String.valueOf(id)));
    }

    public EquipmentDto getEquipmentData(Long elementId, Long partElementId) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("elementId", List.of(String.valueOf(elementId)));
        if (partElementId != null) {
            params.put("partElementId", List.of(String.valueOf(partElementId)));
        }
        return equipmentClient.getEquipmentData(String.join(DELIMITER, API_PREFIX_EQUIPMENT, "element"), params);
    }

    public AcceptableResidualThicknessDto getAcceptableResidualThickness(EquipmentDto equipment) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("equipmentLibraryId", List.of(String.valueOf(equipment.getEquipmentLibraryId())));
        params.put("elementLibraryId", List.of(String.valueOf(equipment.getElementLibraryId())));
        params.put("standardSize", List.of(equipment.getStandardSize()));
        if (equipment.getPartElementLibraryId() != null) {
            params.put("partElementLibraryId", List.of(String.valueOf(equipment.getPartElementLibraryId())));
        }
        return libraryClient.getAcceptableResidualThickness(String.join(DELIMITER, API_PREFIX_LIBRARY, "/acceptable/thickness"), params);
    }

    public AcceptableMetalHardnessDto getAcceptableMetalHardness(EquipmentDto equipment) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("equipmentLibraryId", List.of(String.valueOf(equipment.getEquipmentLibraryId())));
        params.put("elementLibraryId", List.of(String.valueOf(equipment.getElementLibraryId())));
        params.put("standardSize", List.of(equipment.getStandardSize()));
        if (equipment.getPartElementLibraryId() != null) {
            params.put("partElementLibraryId", List.of(String.valueOf(equipment.getPartElementLibraryId())));
        }
        return libraryClient.getAcceptableMetalHardness(String.join(DELIMITER, API_PREFIX_LIBRARY, "/acceptable/hardness"), params);
    }
}