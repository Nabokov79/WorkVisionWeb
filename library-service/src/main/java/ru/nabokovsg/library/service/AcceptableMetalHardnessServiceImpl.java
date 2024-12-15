package ru.nabokovsg.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.library.dto.acceptableMetalHardness.NewAcceptableMetalHardnessDto;
import ru.nabokovsg.library.dto.acceptableMetalHardness.ResponseAcceptableMetalHardnessDto;
import ru.nabokovsg.library.dto.acceptableMetalHardness.UpdateAcceptableMetalHardnessDto;
import ru.nabokovsg.library.exceptions.BadRequestException;
import ru.nabokovsg.library.exceptions.NotFoundException;
import ru.nabokovsg.library.mapper.AcceptableMetalHardnessMapper;
import ru.nabokovsg.library.model.AcceptableMetalHardness;
import ru.nabokovsg.library.repository.AcceptableMetalHardnessRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcceptableMetalHardnessServiceImpl implements AcceptableMetalHardnessService {

    private final AcceptableMetalHardnessRepository repository;
    private final AcceptableMetalHardnessMapper mapper;
    private final ConvertToStandardSizeStringService convertToString;

    @Override
    public ResponseAcceptableMetalHardnessDto save(NewAcceptableMetalHardnessDto hardnessDto) {
        AcceptableMetalHardness acceptableMetalHardness
                                                = addStandardSizeString(mapper.mapToAcceptableHardness(hardnessDto));
        if (getDuplicate(acceptableMetalHardness)) {
            throw new BadRequestException(
                    String.format("AcceptableHardness for hardness=%s is found", hardnessDto));
        }
        return mapper.mapToResponseAcceptableMetalHardnessDto(repository.save(acceptableMetalHardness));
    }

    @Override
    public ResponseAcceptableMetalHardnessDto update(UpdateAcceptableMetalHardnessDto hardnessDto) {
        if (repository.existsById(hardnessDto.getId())) {
            return mapper.mapToResponseAcceptableMetalHardnessDto(
                    repository.save(addStandardSizeString(mapper.mapToUpdateAcceptableHardness(hardnessDto))));
        }
        throw new NotFoundException(
                String.format("AcceptableHardness with id=%s not found for update", hardnessDto.getId())
        );
    }

    @Override
    public List<ResponseAcceptableMetalHardnessDto> getAll(Long equipmentLibraryId) {
        return repository.findAllByEquipmentLibraryId(equipmentLibraryId)
                .stream()
                .map(mapper::mapToResponseAcceptableMetalHardnessDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("AcceptableHardness with id=%s not found for delete", id));
    }


    private boolean getDuplicate(AcceptableMetalHardness acceptableMetalHardness) {
        if (acceptableMetalHardness.getPartElementLibraryId() != null) {
            return repository.existsByEquipmentLibraryIdAndElementLibraryIdAndPartElementLibraryIdAndStandardSizeString(
                                                                      acceptableMetalHardness.getEquipmentLibraryId()
                                                                    , acceptableMetalHardness.getElementLibraryId()
                                                                    , acceptableMetalHardness.getPartElementLibraryId()
                                                                    , acceptableMetalHardness.getStandardSizeString());
        }
        return repository.existsByEquipmentLibraryIdAndElementLibraryIdAndStandardSizeString(
                                                                  acceptableMetalHardness.getEquipmentLibraryId()
                                                                , acceptableMetalHardness.getElementLibraryId()
                                                                , acceptableMetalHardness.getStandardSizeString());
    }


    private AcceptableMetalHardness addStandardSizeString(AcceptableMetalHardness acceptableMetalHardness) {
        mapper.mapToStandardSizeString(acceptableMetalHardness
                , convertToString.convertAcceptableMetalHardness(acceptableMetalHardness));
        return acceptableMetalHardness;
    }
}