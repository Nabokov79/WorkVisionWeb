package ru.nabokovsg.company.service;

import ru.nabokovsg.company.dto.structuralDivision.ResponseStructuralDivisionDto;

public interface StructuralDivisionService {

    ResponseStructuralDivisionDto get(Long heatSupplyAreaId, Long exploitationRegionId, Long addressId);
}