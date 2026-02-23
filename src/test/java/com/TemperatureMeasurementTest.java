package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.lengthmeasurement.LengthUnit;
import com.measurement.Quantity;
import com.temperaturemeasurement.TemperatureUnit;
import com.volumemeasurement.VolumeUnit;
import com.weightmeasurement.WeightUnit;

public class TemperatureMeasurementTest {
	
	private static final double EPSILON = 0.0001;

    // EQUALITY TESTS

    @Test
    void testTemperatureEquality_CelsiusToCelsius_SameValue() {
        assertEquals(
                new Quantity<>(0.0, TemperatureUnit.CELSIUS),
                new Quantity<>(0.0, TemperatureUnit.CELSIUS)
        );
    }

    @Test
    void testTemperatureEquality_FahrenheitToFahrenheit_SameValue() {
        assertEquals(
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT),
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)
        );
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_0Celsius32Fahrenheit() {
        assertEquals(
                new Quantity<>(0.0, TemperatureUnit.CELSIUS),
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)
        );
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_100Celsius212Fahrenheit() {
        assertEquals(
                new Quantity<>(100.0, TemperatureUnit.CELSIUS),
                new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT)
        );
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_Negative40Equal() {
        assertEquals(
                new Quantity<>(-40.0, TemperatureUnit.CELSIUS),
                new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT)
        );
    }

    @Test
    void testTemperatureEquality_SymmetricProperty() {
        Quantity<TemperatureUnit> a = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> b = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(a.equals(b) && b.equals(a));
    }

    @Test
    void testTemperatureEquality_ReflexiveProperty() {
        Quantity<TemperatureUnit> a = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertEquals(a, a);
    }

    @Test
    void testTemperatureDifferentValuesInequality() {
        assertNotEquals(
                new Quantity<>(50.0, TemperatureUnit.CELSIUS),
                new Quantity<>(100.0, TemperatureUnit.CELSIUS)
        );
    }

    // CONVERSION TESTS

    @Test
    void testTemperatureConversion_CelsiusToFahrenheit_VariousValues() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(50.0, TemperatureUnit.CELSIUS)
                        .convertTo(TemperatureUnit.FAHRENHEIT);

        assertEquals(122.0, result.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_FahrenheitToCelsius_VariousValues() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(122.0, TemperatureUnit.FAHRENHEIT)
                        .convertTo(TemperatureUnit.CELSIUS);

        assertEquals(50.0, result.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_RoundTrip_PreservesValue() {
        Quantity<TemperatureUnit> original =
                new Quantity<>(25.0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> converted =
                original.convertTo(TemperatureUnit.FAHRENHEIT)
                        .convertTo(TemperatureUnit.CELSIUS);

        assertEquals(original.getValue(), converted.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_SameUnit() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                        .convertTo(TemperatureUnit.CELSIUS);

        assertEquals(100.0, result.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_ZeroValue() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(0.0, TemperatureUnit.CELSIUS)
                        .convertTo(TemperatureUnit.FAHRENHEIT);

        assertEquals(32.0, result.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_NegativeValues() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(-20.0, TemperatureUnit.CELSIUS)
                        .convertTo(TemperatureUnit.FAHRENHEIT);

        assertEquals(-4.0, result.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_LargeValues() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(1000.0, TemperatureUnit.CELSIUS)
                        .convertTo(TemperatureUnit.FAHRENHEIT);

        assertEquals(1832.0, result.getValue(), EPSILON);
    }

    // UNSUPPORTED OPERATIONS

    @Test
    void testTemperatureUnsupportedOperation_Add() {
        assertThrows(UnsupportedOperationException.class,
                () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                        .add(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
    }

    @Test
    void testTemperatureUnsupportedOperation_Subtract() {
        assertThrows(UnsupportedOperationException.class,
                () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                        .subtract(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
    }

    @Test
    void testTemperatureUnsupportedOperation_Divide() {
        assertThrows(UnsupportedOperationException.class,
                () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                        .divide(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
    }

    @Test
    void testTemperatureUnsupportedOperation_ErrorMessage() {
        UnsupportedOperationException ex =
                assertThrows(UnsupportedOperationException.class,
                        () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                                .add(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));

        assertTrue(ex.getMessage().contains("does not support"));
    }

    // CROSS CATEGORY TESTS

    @Test
    void testTemperatureVsLengthIncompatibility() {
        assertNotEquals(
                new Quantity<>(100.0, TemperatureUnit.CELSIUS),
                new Quantity<>(100.0, LengthUnit.FOOT)
        );
    }

    @Test
    void testTemperatureVsWeightIncompatibility() {
        assertNotEquals(
                new Quantity<>(50.0, TemperatureUnit.CELSIUS),
                new Quantity<>(50.0, WeightUnit.KILOGRAM)
        );
    }

    @Test
    void testTemperatureVsVolumeIncompatibility() {
        assertNotEquals(
                new Quantity<>(25.0, TemperatureUnit.CELSIUS),
                new Quantity<>(25.0, VolumeUnit.LITRE)
        );
    }

    // OPERATION SUPPORT METHODS

    @Test
    void testOperationSupportMethods_TemperatureUnitAddition() {
        assertFalse(TemperatureUnit.CELSIUS.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_LengthUnitAddition() {
        assertTrue(LengthUnit.FOOT.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_WeightUnitDivision() {
        assertTrue(WeightUnit.KILOGRAM.supportsArithmetic());
    }

    // NULL VALIDATION

    @Test
    void testTemperatureNullUnitValidation() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(100.0, null));
    }

    @Test
    void testTemperatureNullOperandValidation_InComparison() {
        Quantity<TemperatureUnit> q =
                new Quantity<>(100.0, TemperatureUnit.CELSIUS);

        assertFalse(q.equals(null));
    }

}
