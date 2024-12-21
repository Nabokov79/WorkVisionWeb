package ru.nabokovsg.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.nabokovsg.library.dto.acceptableDeviationsGeodesy.NewAcceptableDeviationsGeodesyDto;
import ru.nabokovsg.library.dto.acceptableDeviationsGeodesy.ResponseAcceptableDeviationsGeodesyDto;
import ru.nabokovsg.library.dto.acceptableDeviationsGeodesy.UpdateAcceptableDeviationsGeodesyDto;
import ru.nabokovsg.library.exceptions.BadRequestException;
import ru.nabokovsg.library.exceptions.NotFoundException;
import ru.nabokovsg.library.mapper.AcceptableDeviationsGeodesyMapper;
import ru.nabokovsg.library.repository.AcceptableDeviationsGeodesyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcceptableDeviationsGeodesyServiceImpl implements AcceptableDeviationsGeodesyService {

    private final AcceptableDeviationsGeodesyRepository repository;
    private final AcceptableDeviationsGeodesyMapper mapper;

    @Override
    public ResponseAcceptableDeviationsGeodesyDto save(NewAcceptableDeviationsGeodesyDto geodesyDto) {
        if (repository.existsByEquipmentLibraryIdAndFullAndOld(geodesyDto.getEquipmentLibraryId()
                                                             , geodesyDto.getFull()
                                                             , geodesyDto.getOld())) {
            throw new BadRequestException(String.format("Acceptable deviations geodesy=%s is found", geodesyDto));
        }
        return mapper.mapToResponseAcceptableDeviationsGeodesyDto(
                repository.save(mapper.mapToAcceptableDeviationsGeodesy(geodesyDto)));
    }

    @Override
    public ResponseAcceptableDeviationsGeodesyDto update(UpdateAcceptableDeviationsGeodesyDto geodesyDto) {
        if (repository.existsById(geodesyDto.getId())) {
            return mapper.mapToResponseAcceptableDeviationsGeodesyDto(
                    repository.save(mapper.mapToUpdateAcceptableDeviationsGeodesy(geodesyDto))
            );
        }
        throw new NotFoundException(String.format("Acceptable deviations geodesy=%s not found for update", geodesyDto)
        );
    }

    @Override
    public List<ResponseAcceptableDeviationsGeodesyDto> getAll(Long equipmentLibraryId) {
        return repository.findAllByEquipmentLibraryId(equipmentLibraryId)
                         .stream()
                         .map(mapper::mapToResponseAcceptableDeviationsGeodesyDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Acceptable deviations geodesy with id=%s not found for delete", id));
    }
}