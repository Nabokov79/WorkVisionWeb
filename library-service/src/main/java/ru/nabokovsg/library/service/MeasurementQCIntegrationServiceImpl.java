package ru.nabokovsg.library.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.library.dto.acceptableMetalHardness.AcceptableMetalHardnessDto;
import ru.nabokovsg.library.dto.acceptableResidualThickness.ResponseAcceptableResidualThicknessDto;
import ru.nabokovsg.library.dto.integration.LibraryDto;
import ru.nabokovsg.library.exceptions.NotFoundException;
import ru.nabokovsg.library.mapper.MeasurementQCIntegrationMapper;
import ru.nabokovsg.library.model.QAcceptableMetalHardness;
import ru.nabokovsg.library.model.QAcceptableResidualThickness;
import ru.nabokovsg.library.repository.DefectLibraryRepository;
import ru.nabokovsg.library.repository.RepairLibraryRepository;

@Service
@RequiredArgsConstructor
public class MeasurementQCIntegrationServiceImpl implements  MeasurementQCIntegrationService {

    private final MeasurementQCIntegrationMapper mapper;
    private final RepairLibraryRepository repairLibraryRepository;
    private final DefectLibraryRepository defectLibraryRepository;
    private final EntityManager em;

    @Override
    public LibraryDto getRepairLibrary(Long id) {
        return mapper.mapRepairLibrary(repairLibraryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("RepairLibrary by id=%s not found", id))));
    }

    @Override
    public LibraryDto getDefectLibrary(Long id) {
        return mapper.mapDefectLibrary(defectLibraryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("DefectLibrary by id=%s not found", id))));
    }

    @Override
    public ResponseAcceptableResidualThicknessDto getAcceptableResidualThickness(Long equipmentLibraryId
                                                                               , Long elementLibraryId
                                                                               , Long partElementLibraryId
                                                                               , String standardSize) {
        QAcceptableResidualThickness thickness = QAcceptableResidualThickness.acceptableResidualThickness;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(thickness.equipmentLibraryId.eq(equipmentLibraryId));
        booleanBuilder.and(thickness.elementLibraryId.eq(elementLibraryId));
        if (partElementLibraryId != null) {
            booleanBuilder.and(thickness.partElementLibraryId.eq(partElementLibraryId));
        }
        booleanBuilder.and(thickness.standardSize.eq(standardSize));
        return mapper.mapToAcceptableResidualThicknessDto(new JPAQueryFactory(em).from(thickness)
                .select(thickness)
                .where(booleanBuilder)
                .fetchOne());
    }

    @Override
    public AcceptableMetalHardnessDto getAcceptableMetalHardness(Long equipmentLibraryId
            , Long elementLibraryId
            , Long partElementLibraryId
            , String standardSize) {
        QAcceptableMetalHardness acceptableHardness = QAcceptableMetalHardness.acceptableMetalHardness;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(acceptableHardness.equipmentLibraryId.eq(equipmentLibraryId));
        builder.and(acceptableHardness.elementLibraryId.eq(elementLibraryId));
        if (partElementLibraryId != null) {
            builder.and(acceptableHardness.partElementLibraryId.eq(partElementLibraryId));
        }
       builder.and(acceptableHardness.standardSize.eq(standardSize));
        return mapper.mapToAcceptableMetalHardnessDto(new JPAQueryFactory(em).from(acceptableHardness)
                .select(acceptableHardness)
                .where(builder)
                .fetchOne());
    }
}