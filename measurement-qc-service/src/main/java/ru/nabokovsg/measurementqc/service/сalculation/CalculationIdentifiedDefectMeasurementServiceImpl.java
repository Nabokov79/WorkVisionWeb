package ru.nabokovsg.measurementqc.service.сalculation;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.measurementqc.dto.integration.AcceptableResidualThicknessDto;
import ru.nabokovsg.measurementqc.dto.integration.LibraryDto;
import ru.nabokovsg.measurementqc.dto.integration.MeasurementParameterLibraryDto;
import ru.nabokovsg.measurementqc.mapper.measurement.MeasurementResultsUpdateMapper;
import ru.nabokovsg.measurementqc.model.measurement.*;
import ru.nabokovsg.measurementqc.repository.measurement.IdentifiedDefectMeasurementRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculationIdentifiedDefectMeasurementServiceImpl implements CalculationIdentifiedDefectMeasurementService {

    private final IdentifiedDefectMeasurementRepository repository;
    private final MeasurementResultsUpdateMapper mapper;
    private final EntityManager em;


    @Override
    public void calculateByResidualThickness(IdentifiedDefectMeasurement identifiedDefect, AcceptableResidualThicknessDto acceptableThickness) {
        Double minResidualThickness = getMinResidualThickness(identifiedDefect.getElementId(), identifiedDefect.getPartElementId());
        if (minResidualThickness == null) {
            mapper.mapWithQualityAssessment(identifiedDefect, null, MeasurementStatus.valueOf("NO_RESIDUAL_THICKNESS").label);
            return;
        }
        setUnacceptableDefectByResidualThickness(identifiedDefect, minResidualThickness, acceptableThickness);
    }

    @Override
    public void updateUnacceptableByResidualThickness(UltrasonicResidualThicknessMeasurement measurement
            , AcceptableResidualThicknessDto acceptableThickness) {
        Double minMeasurementValue = getMinResidualThickness(measurement.getElementId(), measurement.getPartElementId());
        List<IdentifiedDefectMeasurement> identifiedDefects = getAllIdentifiedDefectMeasurement(measurement);
        identifiedDefects.forEach(defect -> setUnacceptableDefectByResidualThickness(defect
                , minMeasurementValue
                , acceptableThickness));
        repository.saveAll(identifiedDefects);
    }

    @Override
    public void calculateByMaxAllowedValue(LibraryDto defectLibrary
            , IdentifiedDefectMeasurement identifiedDefect
            , Map<Long, Double> measurementValues) {
        Map<Long, Double> maxAllowedValues = defectLibrary.getMeasuredParameters()
                .stream()
                .collect(Collectors.toMap(MeasurementParameterLibraryDto::getId
                        , MeasurementParameterLibraryDto::getMaxAllowedValue));
        measurementValues.forEach((k, v) -> setQualityAssessment(identifiedDefect, maxAllowedValues.get(k), v));
    }

    private Double getMinResidualThickness(Long elementId, Long partElementId) {
        QUltrasonicResidualThicknessMeasurement measurement =
                QUltrasonicResidualThicknessMeasurement.ultrasonicResidualThicknessMeasurement;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(measurement.elementId.eq(elementId));
        if (partElementId != null) {
            builder.and(measurement.partElementId.eq(partElementId));
        }
        return new JPAQueryFactory(em).select(measurement.minMeasurementValue.min())
                .from(measurement)
                .where(builder)
                .fetchOne();
    }

    private void setQualityAssessment(IdentifiedDefectMeasurement identifiedDefect, Double maxAllowedValue, Double value) {
        Boolean unacceptable = null;
        String qualityAssessment = null;
        if (maxAllowedValue != null) {
             unacceptable = maxAllowedValue < value;
             qualityAssessment = getQualityAssessment(unacceptable);
        }
        mapper.mapWithQualityAssessment(identifiedDefect, unacceptable, qualityAssessment);
    }

    private void setUnacceptableDefectByResidualThickness(IdentifiedDefectMeasurement identifiedDefect
            , double minResidualThickness
            , AcceptableResidualThicknessDto acceptableThickness) {
        String depth = MeasurementParameterType.valueOf("DEPTH").label;
        MeasuredParameter parameter = identifiedDefect.getMeasuredParameters().stream()
                .collect(Collectors.toMap(MeasuredParameter::getParameterName, p -> p))
                .get(depth);
        if (parameter == null) {
            String qualityAssessment = String.join(" ", MeasurementStatus.valueOf("NO_MEASUREMENT").label
                    , "параметра :", depth);
            mapper.mapWithQualityAssessment(identifiedDefect, null, qualityAssessment);
            return;
        }
        boolean unacceptable = parameter.getValue() >= (minResidualThickness - acceptableThickness.getAcceptableThickness());
        String qualityAssessment = getQualityAssessment(unacceptable);
        mapper.mapWithQualityAssessment(identifiedDefect, unacceptable, qualityAssessment);
    }

    private String getQualityAssessment(boolean unacceptable) {
        if (unacceptable) {
            return "удовл.";
        }
        return "не удовл.";
    }

    private List<IdentifiedDefectMeasurement> getAllIdentifiedDefectMeasurement(
            UltrasonicResidualThicknessMeasurement measurement) {
        QIdentifiedDefectMeasurement identifiedDefect = QIdentifiedDefectMeasurement.identifiedDefectMeasurement;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(identifiedDefect.elementId.eq(measurement.getElementId()));
        if (measurement.getPartElementId() != null) {
            builder.and(identifiedDefect.partElementId.eq(measurement.getPartElementId()));
        }
        builder.and(identifiedDefect.useCalculateThickness.eq(true));
        return new JPAQueryFactory(em).select(identifiedDefect)
                .from(identifiedDefect)
                .where(builder)
                .fetch();
    }
}