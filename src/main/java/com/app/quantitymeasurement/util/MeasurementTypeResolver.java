package com.app.quantitymeasurement.util;

import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.LengthUnit;
import com.app.quantitymeasurement.unit.TemperatureUnit;
import com.app.quantitymeasurement.unit.VolumeUnit;
import com.app.quantitymeasurement.unit.WeightUnit;
import java.util.Locale;

public final class MeasurementTypeResolver {

    private MeasurementTypeResolver() {
    }

    public static String normalizeMeasurementType(String measurementType) {
        if (measurementType == null || measurementType.isBlank()) {
            throw new QuantityMeasurementException("Measurement type is required");
        }

        return switch (measurementType.trim().toLowerCase(Locale.ROOT)) {
            case "length", "lengthunit" -> "LengthUnit";
            case "weight", "weightunit" -> "WeightUnit";
            case "volume", "volumeunit" -> "VolumeUnit";
            case "temperature", "temperatureunit" -> "TemperatureUnit";
            default -> throw new QuantityMeasurementException("Unsupported measurement type: " + measurementType);
        };
    }

    public static boolean isValidUnitForMeasurementType(String measurementType, String unit) {
        try {
            Class<? extends Enum<?>> unitClass = resolveUnitClass(normalizeMeasurementType(measurementType));
            resolveUnitUnchecked(unit, unitClass);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static <U extends Enum<U> & IMeasurable> Class<U> resolveUnitClass(String measurementType) {
        return (Class<U>) switch (normalizeMeasurementType(measurementType)) {
            case "LengthUnit" -> LengthUnit.class;
            case "WeightUnit" -> WeightUnit.class;
            case "VolumeUnit" -> VolumeUnit.class;
            case "TemperatureUnit" -> TemperatureUnit.class;
            default -> throw new QuantityMeasurementException("Unsupported measurement type: " + measurementType);
        };
    }

    public static <U extends Enum<U> & IMeasurable> U resolveUnit(
            String measurementType,
            String unit,
            Class<U> unitClass
    ) {
        if (unit == null || unit.isBlank()) {
            throw new QuantityMeasurementException("Unit is required");
        }

        try {
            return Enum.valueOf(unitClass, normalizeUnit(unitClass, unit));
        } catch (IllegalArgumentException exception) {
            throw new QuantityMeasurementException("Invalid unit name: " + unit + ".", exception);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Enum<?> resolveUnitUnchecked(String unit, Class<? extends Enum<?>> unitClass) {
        try {
            return Enum.valueOf((Class) unitClass, normalizeUnit(unitClass, unit));
        } catch (IllegalArgumentException exception) {
            throw new QuantityMeasurementException("Invalid unit name: " + unit + ".", exception);
        }
    }

    private static String normalizeUnit(Class<?> unitClass, String unit) {
        String normalized = unit.trim().toUpperCase(Locale.ROOT).replace(" ", "_");

        if (LengthUnit.class.equals(unitClass)) {
            return switch (normalized) {
                case "FEET", "FT" -> LengthUnit.FOOT.name();
                case "INCHES" -> LengthUnit.INCH.name();
                default -> normalized;
            };
        }

        if (VolumeUnit.class.equals(unitClass)) {
            return switch (normalized) {
                case "LITER", "LITERS", "LITRE", "LITRES" -> VolumeUnit.LITRE.name();
                case "MILLILITER", "MILLILITERS", "MILLILITRE", "MILLILITRES" -> VolumeUnit.MILLILITRE.name();
                case "GALLONS" -> VolumeUnit.GALLON.name();
                default -> normalized;
            };
        }

        return normalized;
    }
}
