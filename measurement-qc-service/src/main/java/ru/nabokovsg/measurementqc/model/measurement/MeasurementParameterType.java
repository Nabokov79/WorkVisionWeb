package ru.nabokovsg.measurementqc.model.measurement;

public enum MeasurementParameterType {

    LENGTH("длина"),
    WIDTH("ширина"),
    HEIGHT("высота"),
    DEPTH("глубина"),
    DIAMETER("диаметр"),
    AREA("площадь"),
    QUANTITY("количество"),
    DIRECTION("направление");

    public final String label;

    MeasurementParameterType(String label) {
        this.label = label;
    }
}