package ru.nabokovsg.measurementqc.model.measurement;

import ru.nabokovsg.equipmentDiagnosedQCL.dto.measuredParameter.NewMeasuredParameterDto;
import ru.nabokovsg.equipmentDiagnosedQCL.dto.measuredParameter.UpdateMeasuredParameterDto;
import ru.nabokovsg.equipmentDiagnosedQCL.model.library.LibraryDataType;
import ru.nabokovsg.equipmentDiagnosedQCL.model.library.MeasurementParameterLibrary;
import ru.nabokovsg.equipmentDiagnosedQCL.model.qualityControl.VisualMeasurementControl;
import ru.nabokovsg.measurementqc.dto.measuredParameter.NewMeasuredParameterDto;
import ru.nabokovsg.measurementqc.dto.measuredParameter.UpdateMeasuredParameterDto;
import ru.nabokovsg.measurementqc.model.qualityControl.VisualMeasurementControl;

import java.util.List;
import java.util.Set;

public class ParameterMeasurementBuilder {

    private final LibraryDataType libraryDataType;
    private final IdentifiedDefectMeasurement identifiedDefect;
    private final CompletedRepairMeasurement completedRepair;
    private final VisualMeasurementControl defect;
    private final List<NewMeasuredParameterDto> newMeasuredParameters;
    private final List<UpdateMeasuredParameterDto> updateMeasuredParameters;
    private final Set<MeasurementParameterLibrary> measurementParameterLibraries;

    public ParameterMeasurementBuilder(Builder builder) {
        this.libraryDataType = builder.libraryDataType;
        this.identifiedDefect = builder.identifiedDefect;
        this.completedRepair = builder.completedRepair;
        this.defect = builder.defect;
        this.newMeasuredParameters = builder.newMeasuredParameters;
        this.updateMeasuredParameters = builder.updateMeasuredParameters;
        this.measurementParameterLibraries = builder.measurementParameterLibraries;
    }

    public LibraryDataType getLibraryDataType() {
        return libraryDataType;
    }

    public IdentifiedDefectMeasurement getIdentifiedDefect() {
        return identifiedDefect;
    }

    public CompletedRepairMeasurement getCompletedRepair() {
        return completedRepair;
    }

    public VisualMeasurementControl getDefect() {
        return defect;
    }

    public List<NewMeasuredParameterDto> getNewMeasuredParameters() {
        return newMeasuredParameters;
    }

    public List<UpdateMeasuredParameterDto> getUpdateMeasuredParameters() {
        return updateMeasuredParameters;
    }

    public Set<MeasurementParameterLibrary> getMeasurementParameterLibraries() {
        return measurementParameterLibraries;
    }

    public static class Builder {

        private LibraryDataType libraryDataType;
        private IdentifiedDefectMeasurement identifiedDefect;
        private CompletedRepairMeasurement completedRepair;
        private VisualMeasurementControl defect;
        private List<NewMeasuredParameterDto> newMeasuredParameters;
        private List<UpdateMeasuredParameterDto> updateMeasuredParameters;
        private Set<MeasurementParameterLibrary> measurementParameterLibraries;

        public Builder libraryDataType(LibraryDataType libraryDataType) {
            this.libraryDataType = libraryDataType;
            return this;
        }

        public Builder identifiedDefect(IdentifiedDefectMeasurement identifiedDefect) {
            this.identifiedDefect = identifiedDefect;
            return this;
        }

        public Builder completedRepair(CompletedRepairMeasurement completedRepair) {
            this.completedRepair = completedRepair;
            return this;
        }

        public Builder defect(VisualMeasurementControl defect) {
            this.defect = defect;
            return this;
        }

        public Builder newMeasuredParameters(List<NewMeasuredParameterDto> newMeasuredParameters) {
            this.newMeasuredParameters = newMeasuredParameters;
            return this;
        }

        public Builder updateMeasuredParameters(List<UpdateMeasuredParameterDto> updateMeasuredParameters) {
            this.updateMeasuredParameters = updateMeasuredParameters;
            return this;
        }

        public Builder measurementParameterLibraries(Set<MeasurementParameterLibrary> measurementParameterLibraries) {
            this.measurementParameterLibraries = measurementParameterLibraries;
            return this;
        }

        public ParameterMeasurementBuilder build() {
            return new ParameterMeasurementBuilder(this);
        }
    }
}