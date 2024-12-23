package ru.nabokovsg.measurementqc.service.qualityControl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.measurementqc.dto.visualMeasurementControl.NewVisualMeasurementControlDto;
import ru.nabokovsg.measurementqc.dto.visualMeasurementControl.ResponseVisualMeasurementControlDto;
import ru.nabokovsg.measurementqc.dto.visualMeasurementControl.UpdateVisualMeasurementControlDto;
import ru.nabokovsg.measurementqc.mapper.qualityControl.VisualMeasurementControlMapper;
import ru.nabokovsg.measurementqc.model.qualityControl.VisualMeasurementControl;
import ru.nabokovsg.measurementqc.repository.qualityControl.VisualMeasurementControlRepository;
import ru.nabokovsg.measurementqc.service.measurement.MeasuredParameterService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisualMeasurementControlServiceImpl implements VisualMeasurementControlService {

    private final VisualMeasurementControlRepository repository;
    private final VisualMeasurementControlMapper mapper;
    private final DefectLibraryService defectLibraryService;
    private final MeasuredParameterService measuredParameterService;

    @Override
    public ResponseVisualMeasurementControlDto save(NewVisualMeasurementControlDto defectDto) {
        VisualMeasurementControl defect = mapper.mapToVisualMeasurementControl(defectDto);
        DefectLibrary typeDefect = defectLibraryService.getById(defectDto.getDefectId());
        if (defectDto.getDefectId() == null) {
            defect = saveWithPositiveQualityAssessment(defect);
        } else {
            if (compareParameters(typeDefect)) {
                defect = saveWithOneMeasuredParameter(defect
                                             , mapper.mapToUpdateVisualMeasurementControlDto(defectDto)
                                             , typeDefect.getMeasuredParameters());
            } else {
                defect = saveWithArbitraryNumberMeasuredParameter(defect
                                                         , mapper.mapToUpdateVisualMeasurementControlDto(defectDto)
                                                         , typeDefect.getMeasuredParameters());
                                            }
        }
        return mapper.mapToResponseVisualMeasurementControlDto(defect);
    }

    @Override
    public ResponseVisualMeasurementControlDto update(UpdateVisualMeasurementControlDto defectDto) {
        VisualMeasurementControl defect = getById(defectDto.getId());
        if (defectDto.getDefectId() == null) {
            defect = saveWithPositiveQualityAssessment(defect);
        } else {
            DefectLibrary typeDefect = defectLibraryService.getById(defectDto.getDefectId());
            if (compareParameters(typeDefect)) {
                defect = saveWithOneMeasuredParameter(defect, defectDto, typeDefect.getMeasuredParameters());
            } else {
                defect.setMeasuredParameters(measuredParameterService.update(defect.getMeasuredParameters()
                                                                           , defectDto.getMeasuredParameters()));
            }
        }
        if (defect.getMeasuredParameters() != null) {
            measuredParameterService.deleteAll(LibraryDataType.DEFECT, defect.getId());
        }
        return mapper.mapToResponseVisualMeasurementControlDto(defect);
    }

    @Override
    public ResponseVisualMeasurementControlDto get(Long id) {
        return mapper.mapToResponseVisualMeasurementControlDto(getById(id));
    }

    @Override
    public List<ResponseVisualMeasurementControlDto> getAll(Long workJournalId) {
        return repository.findAllByWorkJournalId(workJournalId)
                         .stream()
                         .map(mapper::mapToResponseVisualMeasurementControlDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        measuredParameterService.deleteAll(LibraryDataType.DEFECT, id);
        repository.deleteById(id);
    }

    private VisualMeasurementControl getById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(
                                    String.format("Visual measurement control defect with id=%s not found", id)));
    }

    private boolean compareParameters(DefectLibrary typeDefect) {
        for (MeasurementParameterLibrary parameter : typeDefect.getMeasuredParameters()) {
            if (parameter.getParameterName().equals(typeDefect.getDefectName())) {
                return true;
            }
        }
        return false;
    }

    private MeasurementParameterLibrary getTypeMeasurementParameterLibrary(
                                                              Set<MeasurementParameterLibrary> parametersLibrary) {
        if (parametersLibrary.size() > 1) {
            throw new BadRequestException(
                    String.format("The number of defect type parameters is more than one, size=%s"
                            , parametersLibrary.size()));
        }
        return parametersLibrary.stream().toList().get(0);
    }
    private String getDefectName(Double value, MeasurementParameterLibrary parameterLibrary) {
        return String.join("", parameterLibrary.getParameterName()
                                     , String.valueOf(value)
                                     , parameterLibrary.getUnitMeasurement());
    }

    private VisualMeasurementControl saveWithPositiveQualityAssessment(VisualMeasurementControl defect) {
        mapper.mapToPositiveQualityAssessment(defect
                                                        , "Дефекты не обнаружены"
                                                        , "-"
                                                        , "удовл.");
        return repository.save(defect);
    }

    private VisualMeasurementControl saveWithOneMeasuredParameter(VisualMeasurementControl defect
                                                            , UpdateVisualMeasurementControlDto defectDto
                                                            , Set<MeasurementParameterLibrary> measuredParameters) {
        mapper.mapToUpdateDefectName(defect, getDefectName(defectDto.getMeasuredParameters().get(0).getValue()
                                   , getTypeMeasurementParameterLibrary(measuredParameters)));
        return repository.save(defect);
    }

    private VisualMeasurementControl saveWithArbitraryNumberMeasuredParameter(VisualMeasurementControl defect
                                                        , UpdateVisualMeasurementControlDto defectDto
                                                        , Set<MeasurementParameterLibrary> measuredParameters) {
        defect = repository.save(defect);
        defect.setMeasuredParameters(measuredParameterService.save(
                new ParameterMeasurementBuilder.Builder()
                        .libraryDataType(LibraryDataType.DEFECT)
                        .defect(defect)
                        .measurementParameterLibraries(measuredParameters)
                        .updateMeasuredParameters(defectDto.getMeasuredParameters())
                        .build()));
        return defect;
    }
}