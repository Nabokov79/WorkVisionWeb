package ru.nabokovsg.library.model;

public class TypeMeasuredParameterBuilder {

    private final LibraryDataType libraryDataType;
    private final ParameterCalculationType calculationType;
    private final DefectLibrary defect;
    private final RepairLibrary repair;

    public TypeMeasuredParameterBuilder(Builder builder) {
        this.libraryDataType = builder.libraryDataType;
        this.calculationType = builder.calculationType;
        this.defect = builder.defect;
        this.repair = builder.repair;
    }

    public LibraryDataType getLibraryDataType() {
        return libraryDataType;
    }

    public ParameterCalculationType getCalculationType() {
        return calculationType;
    }

    public DefectLibrary getDefect() {
        return defect;
    }

    public RepairLibrary getRepair() {
        return repair;
    }

    public static class Builder {
        private LibraryDataType libraryDataType;
        private ParameterCalculationType calculationType;
        private DefectLibrary defect;
        private RepairLibrary repair;

        public Builder libraryDataType(LibraryDataType libraryDataType) {
            this.libraryDataType = libraryDataType;
            return this;
        }

        public Builder calculationType(ParameterCalculationType calculationType) {
            this.calculationType = calculationType;
            return this;
        }

        public Builder defect(DefectLibrary defect) {
            this.defect = defect;
            return this;
        }

        public Builder repair(RepairLibrary repair) {
            this.repair = repair;
            return this;
        }

        public TypeMeasuredParameterBuilder build() {
            return new TypeMeasuredParameterBuilder(this);
        }
    }
}
