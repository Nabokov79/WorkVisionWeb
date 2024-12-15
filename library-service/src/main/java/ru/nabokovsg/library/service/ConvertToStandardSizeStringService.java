package ru.nabokovsg.library.service;
import ru.nabokovsg.library.model.AcceptableMetalHardness;
import ru.nabokovsg.library.model.AcceptableResidualThickness;

public interface ConvertToStandardSizeStringService {

    String convertAcceptableResidualThickness(AcceptableResidualThickness acceptableResidualThickness);

    String convertAcceptableMetalHardness(AcceptableMetalHardness acceptableMetalHardness);
}