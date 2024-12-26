package ru.nabokovsg.measurementqc.service.qualityControl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.measurementqc.client.MeasurementQCClient;
import ru.nabokovsg.measurementqc.dto.integration.LibraryDto;
import ru.nabokovsg.measurementqc.dto.integration.MeasurementParameterLibraryDto;
import ru.nabokovsg.measurementqc.dto.visualMeasurementControl.NewVisualMeasurementControlDto;
import ru.nabokovsg.measurementqc.dto.visualMeasurementControl.ResponseVisualMeasurementControlDto;
import ru.nabokovsg.measurementqc.dto.visualMeasurementControl.UpdateVisualMeasurementControlDto;
import ru.nabokovsg.measurementqc.exceptions.BadRequestException;
import ru.nabokovsg.measurementqc.exceptions.NotFoundException;
import ru.nabokovsg.measurementqc.mapper.qualityControl.VisualMeasurementControlMapper;
import ru.nabokovsg.measurementqc.model.measurement.LibraryDataType;
import ru.nabokovsg.measurementqc.model.measurement.ParameterMeasurementBuilder;
import ru.nabokovsg.measurementqc.model.qualityControl.VisualMeasurementControl;
import ru.nabokovsg.measurementqc.repository.qualityControl.VisualMeasurementControlRepository;
import ru.nabokovsg.measurementqc.service.measurement.MeasuredParameterService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisualMeasurementControlServiceImpl implements VisualMeasurementControlService {

    private final VisualMeasurementControlRepository repository;
    private final VisualMeasurementControlMapper mapper;
    private final MeasuredParameterService measuredParameterService;
    private final MeasurementQCClient client;

    @Override
    public ResponseVisualMeasurementControlDto save(NewVisualMeasurementControlDto defectDto) {
        VisualMeasurementControl defect = mapper.mapToVisualMeasurementControl(defectDto);
        if (defectDto.getDefectLibraryId() == null) {
            defect = saveWithPositiveQualityAssessment(defect);
        } else {
            LibraryDto defectLibrary = client.getLibraryData(defectDto.getDefectLibraryId(), LibraryDataType.DEFECT);
            if (compareParameters(defectLibrary)) {
                defect = saveWithOneMeasuredParameter(defect
                                             , mapper.mapToUpdateVisualMeasurementControlDto(defectDto)
                                             , defectLibrary.getMeasuredParameters());
            } else {
                defect = saveWithArbitraryNumberMeasuredParameter(defect
                                                         , mapper.mapToUpdateVisualMeasurementControlDto(defectDto)
                                                         , defectLibrary.getMeasuredParameters());
                                            }
        }
        return mapper.mapToResponseVisualMeasurementControlDto(defect);
    }

    @Override
    public ResponseVisualMeasurementControlDto update(UpdateVisualMeasurementControlDto defectDto) {
        VisualMeasurementControl defect = getById(defectDto.getId());
        if (defectDto.getDefectLibraryId() == null) {
            defect = saveWithPositiveQualityAssessment(defect);
        } else {
            LibraryDto defectLibrary = client.getLibraryData(defectDto.getDefectLibraryId(), LibraryDataType.DEFECT);
            if (compareParameters(defectLibrary)) {
                defect = saveWithOneMeasuredParameter(defect, defectDto, defectLibrary.getMeasuredParameters());
            } else {
                defect.setMeasuredParameters(measuredParameterService.update(defect.getMeasuredParameters()
                                                                           , defectDto.getMeasuredParameters()));
            }
        }
        if (defectDto.getDefectLibraryId() == null) {
            defect = saveWithPositiveQualityAssessment(defect);
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
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Identified defect with id=%s not found for delete", id));
    }

    private VisualMeasurementControl getById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(
                                    String.format("Visual measurement control defect with id=%s not found", id)));
    }

    private boolean compareParameters(LibraryDto defectLibrary) {
        for (MeasurementParameterLibraryDto parameter : defectLibrary.getMeasuredParameters()) {
            if (parameter.getParameterName().equals(defectLibrary.getDefectName())) {
                return true;
            }
        }
        return false;
    }

    private MeasurementParameterLibraryDto getTypeMeasurementParameterLibrary(
                                                             List<MeasurementParameterLibraryDto> parametersLibrary) {
        if (parametersLibrary.size() > 1) {
            throw new BadRequestException(
                    String.format("The number of defect type parameters is more than one, size=%s"
                            , parametersLibrary.size()));
        }
        return parametersLibrary.stream().toList().get(0);
    }
    private String getDefectName(Double value,MeasurementParameterLibraryDto parameterLibrary) {
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
                                                            , List<MeasurementParameterLibraryDto> measuredParameters) {
        mapper.mapToUpdateDefectName(defect, getDefectName(defectDto.getMeasuredParameters().get(0).getValue()
                                   , getTypeMeasurementParameterLibrary(measuredParameters)));
        return repository.save(defect);
    }

    private VisualMeasurementControl saveWithArbitraryNumberMeasuredParameter(VisualMeasurementControl defect
                                                        , UpdateVisualMeasurementControlDto defectDto
                                                        , List<MeasurementParameterLibraryDto> measuredParameters) {
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