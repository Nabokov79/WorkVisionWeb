package ru.nabokovsg.library.service;

import org.springframework.stereotype.Component;
import ru.nabokovsg.library.model.AcceptableMetalHardness;
import ru.nabokovsg.library.model.AcceptableResidualThickness;

@Component
public class ConvertToStandardSizeStringServiceImpl implements ConvertToStandardSizeStringService {

    @Override
    public String convertAcceptableResidualThickness(AcceptableResidualThickness acceptableResidualThickness) {
        String standardSizeString = String.valueOf(acceptableResidualThickness.getMinDiameter());
        standardSizeString = joinMinThickness(standardSizeString, acceptableResidualThickness.getMinThickness());
        standardSizeString = joinMaxDiameter(standardSizeString, acceptableResidualThickness.getMaxDiameter());
        standardSizeString = joinMaxThickness(standardSizeString, acceptableResidualThickness.getMaxThickness());
        return standardSizeString;
    }

    @Override
    public String convertAcceptableMetalHardness(AcceptableMetalHardness acceptableMetalHardness) {
        String standardSizeString = String.valueOf(acceptableMetalHardness.getMinAcceptableDiameter());
        standardSizeString = joinMinThickness(standardSizeString, acceptableMetalHardness.getMinAcceptableThickness());
        return standardSizeString;
    }

    private String joinMinThickness(String standardSizeString, Double minThickness) {
        if (minThickness != null) {
            standardSizeString = String.join("x", standardSizeString, String.valueOf(minThickness));
        }
        return standardSizeString;
    }

    private String joinMaxDiameter(String standardSizeString, Integer maxDiameter) {
        if (maxDiameter != null) {
            standardSizeString = String.join("/", standardSizeString, String.valueOf(maxDiameter));
        }
        return standardSizeString;
    }

    private String joinMaxThickness(String standardSizeString, Double maxThickness) {
        if (maxThickness != null) {
            standardSizeString = String.join("x", standardSizeString, String.valueOf(maxThickness));
        }
        return standardSizeString;
    }
}