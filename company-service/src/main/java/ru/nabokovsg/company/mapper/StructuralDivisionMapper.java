package ru.nabokovsg.company.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.company.dto.structuralDivision.ResponseStructuralDivisionDto;
import ru.nabokovsg.company.model.Building;
import ru.nabokovsg.company.model.ExploitationRegion;
import ru.nabokovsg.company.model.HeatSupplyArea;

@Mapper(componentModel = "spring")
public interface StructuralDivisionMapper {

    @Mapping(source = "exploitationRegion.branch.fullName", target = "branchFullName")
    @Mapping(source = "exploitationRegion.branch.shortName", target = "branchShortName")
    @Mapping(source = "building", target = "building")
    @Mapping(source = "exploitationRegion", target = "exploitationRegion")
    @Mapping(target = "heatSupplyArea", ignore = true)
    @Mapping(target = "address", ignore = true)
    ResponseStructuralDivisionDto mapExploitationRegion(ExploitationRegion exploitationRegion, Building building);

    @Mapping(source = "heatSupplyArea.branch.fullName", target = "branchFullName")
    @Mapping(source = "heatSupplyArea.branch.shortName", target = "branchShortName")
    @Mapping(source = "heatSupplyArea", target = "heatSupplyArea")
    @Mapping(source = "address", target = "address")
    @Mapping(target = "exploitationRegion", ignore = true)
    @Mapping(target = "building", ignore = true)
    ResponseStructuralDivisionDto mapHeatSupplyArea(HeatSupplyArea heatSupplyArea, String address);
}