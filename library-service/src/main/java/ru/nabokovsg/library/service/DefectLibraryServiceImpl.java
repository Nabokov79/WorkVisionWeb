package ru.nabokovsg.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.library.dto.defectLibrary.NewDefectLibraryDto;
import ru.nabokovsg.library.dto.defectLibrary.ResponseDefectLibraryDto;
import ru.nabokovsg.library.dto.defectLibrary.ResponseShortDefectLibraryDto;
import ru.nabokovsg.library.dto.defectLibrary.UpdateDefectLibraryDto;
import ru.nabokovsg.library.exceptions.BadRequestException;
import ru.nabokovsg.library.exceptions.NotFoundException;
import ru.nabokovsg.library.mapper.DefectLibraryMapper;
import ru.nabokovsg.library.model.DefectLibrary;
import ru.nabokovsg.library.model.LibraryDataType;
import ru.nabokovsg.library.model.ParameterCalculationType;
import ru.nabokovsg.library.model.TypeMeasuredParameterBuilder;
import ru.nabokovsg.library.repository.DefectLibraryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefectLibraryServiceImpl implements DefectLibraryService {

    private final DefectLibraryRepository repository;
    private final DefectLibraryMapper mapper;
    private final MeasuredParameterLibraryService parameterService;

    @Override
    public ResponseDefectLibraryDto save(NewDefectLibraryDto defectDto) {
        ParameterCalculationType calculationType = getTypeCalculation(defectDto.getCalculation());
        DefectLibrary defect = repository.findByDefectName(defectDto.getDefectName());
        if (defect == null) {
            defect = repository.save(mapper.mapToTypeDefectLibrary(defectDto, calculationType));
            defect.setMeasuredParameters(parameterService.save(new TypeMeasuredParameterBuilder.Builder()
                                                                               .libraryDataType(LibraryDataType.DEFECT)
                                                                               .calculationType(defect.getCalculation())
                                                                               .defect(defect)
                                                                               .build()
                                                                            , defectDto.getMeasuredParameters()));
        }
        return mapper.mapToResponseTypeDefectLibraryDto(defect);
    }

    @Override
    public ResponseDefectLibraryDto update(UpdateDefectLibraryDto defectDto) {
        DefectLibrary defect = getById(defectDto.getId());
        if (defect != null) {
            ParameterCalculationType calculationType = getTypeCalculation(defectDto.getCalculation());
            defect = repository.save(mapper.mapToUpdateTypeDefectLibrary(defectDto, calculationType));
            defect.setMeasuredParameters(parameterService.update(new TypeMeasuredParameterBuilder.Builder()
                                                                            .libraryDataType(LibraryDataType.DEFECT)
                                                                            .calculationType(calculationType)
                                                                            .defect(defect)
                                                                            .build()
                                                                    , defectDto.getMeasuredParameters()));
            return mapper.mapToResponseTypeDefectLibraryDto(defect);
        }
        throw new NotFoundException(String.format("Defect with id=%s not found for update", defectDto.getId()));
    }

    @Override
    public ResponseDefectLibraryDto get(Long id) {
        return mapper.mapToResponseTypeDefectLibraryDto(getById(id));
    }

    @Override
    public List<ResponseShortDefectLibraryDto> getAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::mapToResponseShortTypeDefectLibraryDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        DefectLibrary defect = getById(id);
        repository.deleteById(defect.getId());
        parameterService.delete(defect.getMeasuredParameters());
        throw new NotFoundException(String.format("Defect with id=%s not found for delete", id));
    }

    @Override
    public DefectLibrary getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("TypeDefectLibrary with id=%s not found", id)));
    }

    private ParameterCalculationType getTypeCalculation(String calculation) {
        return ParameterCalculationType.from(calculation).orElseThrow(
                () -> new BadRequestException(String.format("Unsupported calculation type=%s", calculation)));
    }
}