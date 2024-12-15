package ru.nabokovsg.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.library.dto.repairLibrary.NewRepairLibraryDto;
import ru.nabokovsg.library.dto.repairLibrary.ResponseRepairLibraryDto;
import ru.nabokovsg.library.dto.repairLibrary.ResponseShortRepairLibraryDto;
import ru.nabokovsg.library.dto.repairLibrary.UpdateRepairLibraryDto;
import ru.nabokovsg.library.exceptions.BadRequestException;
import ru.nabokovsg.library.exceptions.NotFoundException;
import ru.nabokovsg.library.mapper.RepairLibraryMapper;
import ru.nabokovsg.library.model.LibraryDataType;
import ru.nabokovsg.library.model.ParameterCalculationType;
import ru.nabokovsg.library.model.RepairLibrary;
import ru.nabokovsg.library.model.TypeMeasuredParameterBuilder;
import ru.nabokovsg.library.repository.RepairLibraryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepairLibraryServiceImpl implements RepairLibraryService {

    private final RepairLibraryRepository repository;
    private final RepairLibraryMapper mapper;
    private final MeasuredParameterLibraryService parameterService;

    @Override
    public ResponseRepairLibraryDto save(NewRepairLibraryDto repairDto) {
        RepairLibrary repair = repository.findByRepairName(repairDto.getRepairName());
        if (repair == null) {
            ParameterCalculationType calculationType = getTypeCalculation(repairDto.getCalculation());
            repair = repository.save(mapper.mapToTypeRepairLibrary(repairDto, calculationType));
            repair.setMeasuredParameters(parameterService.save(new TypeMeasuredParameterBuilder.Builder()
                                                                            .libraryDataType(LibraryDataType.REPAIR)
                                                                            .calculationType(calculationType)
                                                                            .repair(repair)
                                                                            .build()
                                                                        , repairDto.getMeasuredParameters()));
        }
        return mapper.mapToResponseTypeRepairLibraryDto(repair);
    }

    @Override
    public ResponseRepairLibraryDto update(UpdateRepairLibraryDto repairDto) {
        RepairLibrary repair = getById(repairDto.getId());
        if (repair != null) {
            ParameterCalculationType calculationType = getTypeCalculation(repairDto.getCalculation());
            repair = repository.save(mapper.mapToUpdateTypeRepairLibrary(repairDto, calculationType));
            repair.setMeasuredParameters(parameterService.update(new TypeMeasuredParameterBuilder.Builder()
                                                                            .libraryDataType(LibraryDataType.REPAIR)
                                                                            .calculationType(calculationType)
                                                                            .repair(repair)
                                                                            .build()
                                                                    , repairDto.getMeasuredParameters()));
            return mapper.mapToResponseTypeRepairLibraryDto(repair);
        }
        throw new NotFoundException(String.format("Repair method with id=%s not found for update", repairDto.getId()));
    }

    @Override
    public ResponseRepairLibraryDto get(Long id) {
        return mapper.mapToResponseTypeRepairLibraryDto(getById(id));
    }

    @Override
    public List<ResponseShortRepairLibraryDto> getAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::mapToResponseShortTypeRepairLibraryDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        RepairLibrary repair = getById(id);
        repository.deleteById(repair.getId());
        parameterService.delete(repair.getMeasuredParameters());
        throw new NotFoundException(String.format("Repair method with id=%s not found for delete", id));
    }

    private RepairLibrary getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Repair method with id=%s not found", id)));
    }

    private ParameterCalculationType getTypeCalculation(String calculation) {
        return ParameterCalculationType.from(calculation).orElseThrow(
                () -> new BadRequestException(String.format("Unsupported calculation type=%s", calculation)));
    }
}