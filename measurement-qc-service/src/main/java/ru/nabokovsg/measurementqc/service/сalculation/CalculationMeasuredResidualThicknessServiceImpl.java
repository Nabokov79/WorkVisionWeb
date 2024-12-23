package ru.nabokovsg.measurementqc.service.сalculation;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.measurementqc.dto.equipment.EquipmentDto;
import ru.nabokovsg.measurementqc.mapper.measurement.CalculationMeasuredResidualThicknessMapper;
import ru.nabokovsg.measurementqc.model.measurement.*;
import ru.nabokovsg.measurementqc.repository.measurement.UltrasonicResidualThicknessMeasurementRepository;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CalculationMeasuredResidualThicknessServiceImpl implements CalculationMeasuredResidualThicknessService {

    private final AcceptableResidualThicknessService acceptableThicknessService;
    private final EquipmentElementService equipmentElementService;
    private final UltrasonicResidualThicknessMeasurementRepository repository;
    private final CalculationMeasuredResidualThicknessMapper mapper;
    private final EntityManager em;

    @Override
    public void calculation(UltrasonicResidualThicknessMeasurement measurement
                          , EquipmentDto equipmentData
                          , AcceptableResidualThickness acceptableThickness) {
        Double maxCorrosion = getMaxCorrosionValue(measurement.getElementId(), measurement.getPartElementId());
        double residualThickness = countResidualThickness(measurement.getMinMeasurementValue()
                , maxCorrosion
                , acceptableThickness);
        double minAcceptableValue = countMinAcceptableValue(acceptableThickness, equipmentData);
        mapper.mapWithDataCalculation(measurement, maxCorrosion, residualThickness, minAcceptableValue);
        setMeasurementStatus(measurement, acceptableThickness);
    }

    @Override
    public void updateResidualThicknessMeasurementsEquipmentElements(IdentifiedDefectMeasurement identifiedDefect) {
        EquipmentDto equipmentData =
                equipmentElementService.getEquipmentDiagnosedData(identifiedDefect.getElementId());
        AcceptableResidualThickness acceptableThickness = getAcceptableResidualThickness(identifiedDefect.getElementId());
        Set<UltrasonicResidualThicknessMeasurement> measurements;
        if (equipmentData.getPartElementId() != null) {
            measurements = repository.findAllByElementIdAndPartElementId(identifiedDefect.getElementId()
                                                                       , identifiedDefect.getPartElementId());
        } else {
            measurements = repository.findAllByElementId(identifiedDefect.getElementId());
        }
        if (measurements.isEmpty()) {
            return;
        }
        measurements.forEach(measurement -> calculation(measurement
                , equipmentData
                , acceptableThickness));
        repository.saveAll(measurements.stream().toList());
    }

    private AcceptableResidualThickness getAcceptableResidualThickness(Long elementId) {
        return acceptableThicknessService.getByEquipmentDiagnosedData(
                equipmentElementService.getEquipmentDiagnosedData(elementId));
    }

    private Double getMaxCorrosionValue(Long elementId, Long partElementId) {
        QIdentifiedDefectMeasurement defect = QIdentifiedDefectMeasurement.identifiedDefectMeasurement;
        QMeasuredParameter parameter = QMeasuredParameter.measuredParameter;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(defect.elementId.eq(elementId));
        builder.and(defect.useCalculateThickness.eq(true));
        builder.and(parameter.identifiedDefect.id.eq(defect.id));
        if (partElementId != null) {
            builder.and(defect.partElementId.eq(partElementId));
        }
        return new JPAQueryFactory(em).from(parameter)
                .select(parameter.value.max())
                .innerJoin(parameter.identifiedDefect, defect)
                .where(builder)
                .fetchOne();
    }

    private double countResidualThickness(Double minMeasurementValue
                                        , Double maxCorrosion
                                        , AcceptableResidualThickness acceptableThickness) {
        double residualThickness = minMeasurementValue;
        if (maxCorrosion != null) {
            residualThickness = minMeasurementValue - maxCorrosion;
        }
        if (acceptableThickness != null && residualThickness
                        == (acceptableThickness.getAcceptableThickness() - acceptableThickness.getMeasurementError())) {
            return acceptableThickness.getAcceptableThickness();
        }
        if (residualThickness < 0) {
            return 0;
        }
        return residualThickness;
    }

    private double countMinAcceptableValue(AcceptableResidualThickness acceptableThickness
                                         , EquipmentDto equipmentData) {
        if (acceptableThickness.getAcceptablePercent() != null) {
            return equipmentData.getThickness()
                    - (equipmentData.getThickness() * (double) (acceptableThickness.getAcceptablePercent() / 100));
        } else {
            return acceptableThickness.getAcceptableThickness();
        }
    }

    private void setMeasurementStatus(UltrasonicResidualThicknessMeasurement measurement
                                    , AcceptableResidualThickness acceptableThickness) {
        if (getNoStandard(acceptableThickness)) {
            mapper.mapWithMeasurementStatus(measurement
                                          , MeasurementStatus.valueOf("NO_STANDARD").label
                                          , "NO_STANDARD");
            return;
        }
        if (getAcceptable(measurement, acceptableThickness)) {
            mapper.mapWithMeasurementStatus(measurement
                                          , MeasurementStatus.valueOf("ACCEPTABLE").label
                                          , "ACCEPTABLE");
            return;
        }
        if (getInvalid(measurement, acceptableThickness)) {
            mapper.mapWithMeasurementStatus(measurement
                                          , MeasurementStatus.valueOf("INVALID").label
                                          , "INVALID");
            return;
        }
        if (getApproachingInvalid(measurement, acceptableThickness)) {
            mapper.mapWithMeasurementStatus(measurement
                                          , MeasurementStatus.valueOf("APPROACHING_INVALID").label
                                          , "APPROACHING_INVALID");
            return;
        }
        if (getReachedInvalid(measurement, acceptableThickness)) {
            mapper.mapWithMeasurementStatus(measurement
                                          , MeasurementStatus.valueOf("REACHED_INVALID").label
                                          , "REACHED_INVALID");
        }
    }

   private boolean getNoStandard(AcceptableResidualThickness acceptableThickness) {
       return acceptableThickness == null;
   }

    private boolean getAcceptable(UltrasonicResidualThicknessMeasurement measurement
                                , AcceptableResidualThickness acceptableThickness) {
        return measurement.getResidualThickness()
                >= (acceptableThickness.getAcceptableThickness() + acceptableThickness.getMeasurementError());
    }

   private boolean getInvalid(UltrasonicResidualThicknessMeasurement measurement
                            , AcceptableResidualThickness acceptableThickness) {
        return (measurement.getResidualThickness() + acceptableThickness.getMeasurementError())
                                                                        < acceptableThickness.getAcceptableThickness();
    }

    private boolean getApproachingInvalid(UltrasonicResidualThicknessMeasurement measurement
                                        , AcceptableResidualThickness acceptableThickness) {
        return (measurement.getResidualThickness() > acceptableThickness.getAcceptableThickness())
                && (acceptableThickness.getAcceptableThickness() + acceptableThickness.getMeasurementError())
                                                                                > measurement.getResidualThickness();
    }

    private boolean getReachedInvalid(UltrasonicResidualThicknessMeasurement measurement
                                    , AcceptableResidualThickness acceptableThickness) {
        boolean reachedInvalid = Objects.equals(measurement.getResidualThickness()
                                              , acceptableThickness.getAcceptableThickness());
        if (!reachedInvalid) {
            reachedInvalid = (measurement.getResidualThickness() + acceptableThickness.getMeasurementError())
                                                                        == acceptableThickness.getAcceptableThickness();
        }
        return reachedInvalid;
    }
}