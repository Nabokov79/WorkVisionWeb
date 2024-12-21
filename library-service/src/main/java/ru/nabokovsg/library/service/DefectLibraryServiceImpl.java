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
        if (repository.existsByDefectName(defectDto.getDefectName())) {
            throw new BadRequestException(String.format("DefectLibrary with defectDto=%s is found", defectDto));
        }
        DefectLibrary defect = mapper.mapToTypeDefectLibrary(defectDto);
        addTypeCalculation(defect, defectDto.getCalculation());
        defect = repository.save(defect);
        defect.setMeasuredParameters(
                parameterService.save(new TypeMeasuredParameterBuilder.Builder()
                                                                      .libraryDataType(LibraryDataType.DEFECT)
                                                                      .calculation(defect.getCalculation())
                                                                      .defect(defect)
                                                                      .build()
              , defectDto.getMeasuredParameters()));
        return mapper.mapToResponseTypeDefectLibraryDto(defect);
    }

    @Override
    public ResponseDefectLibraryDto update(UpdateDefectLibraryDto defectDto) {
        DefectLibrary defect = getById(defectDto.getId());
        mapper.mapToUpdateTypeDefectLibrary(defect, defectDto);
        addTypeCalculation(defect, defectDto.getCalculation());
        defect = repository.save(defect);
        defect.setMeasuredParameters(
                parameterService.update(defect.getMeasuredParameters()
                        , defectDto.getMeasuredParameters()));
        return mapper.mapToResponseTypeDefectLibraryDto(defect);
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
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("DefectLibrary with id=%s not found for delete", id));
    }

    private void addTypeCalculation(DefectLibrary defect, String calculation) {
        ParameterCalculationType calculationType = ParameterCalculationType.from(calculation).orElseThrow(
                () -> new BadRequestException(String.format("Unsupported calculation type=%s", calculation)));
        mapper.mapWithParameterCalculationType(defect, calculationType);
    }

    private DefectLibrary getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("DefectLibrary with id=%s not found", id)));
    }
}