package com.app.quantitymeasurement.entity;

public class QuantityMeasurementEntity {

    private final String operation;
    private final String input;
    private final String result;
    private final boolean error;

    public QuantityMeasurementEntity(String operation,
                                     String input,
                                     String result,
                                     boolean error) {
        this.operation = operation;
        this.input = input;
        this.result = result;
        this.error = error;
    }

    public String getOperation() {
        return operation;
    }

    public String getInput() {
        return input;
    }

    public String getResult() {
        return result;
    }

    public boolean isError() {
        return error;
    }
}