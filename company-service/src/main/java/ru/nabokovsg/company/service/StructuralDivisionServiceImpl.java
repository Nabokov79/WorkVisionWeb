package ru.nabokovsg.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.company.dto.structuralDivision.ResponseStructuralDivisionDto;
import ru.nabokovsg.company.exceptions.BadRequestException;
import ru.nabokovsg.company.exceptions.NotFoundException;
import ru.nabokovsg.company.mapper.StructuralDivisionMapper;
import ru.nabokovsg.company.model.Building;
import ru.nabokovsg.company.model.ExploitationRegion;
import ru.nabokovsg.company.repository.ExploitationRegionRepository;
import ru.nabokovsg.company.repository.HeatSupplyAreaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StructuralDivisionServiceImpl implements StructuralDivisionService {

    private final StructuralDivisionMapper mapper;
    private final HeatSupplyAreaRepository heatSupplyAreaRepository;
    private final ExploitationRegionRepository exploitationRegionRepository;
    private final AddressService addressService;
    @Override
    public ResponseStructuralDivisionDto get(Long heatSupplyAreaId, Long exploitationRegionId, Long addressId) {
        if (heatSupplyAreaId != null && heatSupplyAreaId > 0) {
            return getByHeatSupplyArea(heatSupplyAreaId, addressId);
        }
        if (exploitationRegionId != null && exploitationRegionId > 0) {
            return getByExploitationRegion(exploitationRegionId, addressId);
        }
        throw new BadRequestException(
                String.format("HeatSupplyAreaId=%s and exploitationRegionId=%s " +
                        "should not be null and can only be positive", heatSupplyAreaId, exploitationRegionId));
    }

    private ResponseStructuralDivisionDto getByHeatSupplyArea(Long heatSupplyAreaId, Long addressId) {
        return mapper.mapHeatSupplyArea(
                heatSupplyAreaRepository.findById(heatSupplyAreaId)
                                        .orElseThrow(() -> new NotFoundException
                                              (String.format("HeatSupplyArea with id=%s not found.", heatSupplyAreaId)))
              , addressService.getAddressString(addressId));
    }

    private ResponseStructuralDivisionDto getByExploitationRegion(Long exploitationRegionId, Long addressId) {
        ExploitationRegion exploitationRegion
                = exploitationRegionRepository.findById(exploitationRegionId)
                .orElseThrow(() -> new NotFoundException(String.format("ExploitationRegion with id=%s not found."
                                                                                              , exploitationRegionId)));

        return mapper.mapExploitationRegion(exploitationRegion
                                         , getBuilding(exploitationRegion.getBuildings(), addressId));
    }

    private Building getBuilding(List<Building> buildings, Long addressId) {
        String address = addressService.getAddressString(addressId);
        Building buildingDb = buildings.stream()
                .filter(building -> building.getAddress().equals(address))
                                            .collect(Collectors.toMap(Building::getAddress, building -> building))
                                            .get(address);
        if (buildingDb == null) {
            throw new NotFoundException(String.format("Building with address id=%s not found", addressId));
        }
        return buildingDb;
    }
}