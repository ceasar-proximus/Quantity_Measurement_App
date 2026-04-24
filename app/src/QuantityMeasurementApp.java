import java.util.Objects;

/**
 * UC5: Generic Quantity Measurement API
 * Supports Equality, Conversion, and Overloaded demonstration methods.
 */
public class QuantityMeasurementApp {

    public enum LengthUnit {
        FEET(12.0),
        INCHES(1.0),
        YARDS(36.0),
        CENTIMETERS(0.393701);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        // Private helper to get conversion factor
        private double toBaseUnit(double value) {
            return value * this.conversionFactor;
        }

        private double fromBaseUnit(double baseValue) {
            return baseValue / this.conversionFactor;
        }
    }

    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be a finite number");
            this.unit = Objects.requireNonNull(unit, "Unit cannot be null");
            this.value = value;
        }

        /**
         * Converts current instance to a new unit.
         * @return A new QuantityLength instance with the converted value.
         */
        public QuantityLength convertTo(LengthUnit targetUnit) {
            double baseValue = this.unit.toBaseUnit(this.value);
            double convertedValue = targetUnit.fromBaseUnit(baseValue);
            return new QuantityLength(convertedValue, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityLength that = (QuantityLength) obj;

            // Rounding to 4 decimal places for stable comparison
            double v1 = Math.round(this.unit.toBaseUnit(this.value) * 10000.0) / 10000.0;
            double v2 = Math.round(that.unit.toBaseUnit(that.value) * 10000.0) / 10000.0;
            return Double.compare(v1, v2) == 0;
        }

        @Override
        public String toString() {
            return String.format("%.2f %s", value, unit);
        }
    }

    // --- API Methods / Method Overloading ---

    /**
     * Overload 1: Convert raw values directly
     */
    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        QuantityLength source = new QuantityLength(value, from);
        QuantityLength result = source.convertTo(to);
        System.out.println("Conversion: " + source + " -> " + result);
    }

    /**
     * Overload 2: Convert an existing object
     */
    public static void demonstrateLengthConversion(QuantityLength length, LengthUnit to) {
        QuantityLength result = length.convertTo(to);
        System.out.println("Object Conversion: " + length + " converted to " + result);
    }

    public static void demonstrateLengthEquality(QuantityLength l1, QuantityLength l2) {
        System.out.println("Equality: " + l1 + " == " + l2 + " is " + l1.equals(l2));
    }

    public static void main(String[] args) {
        // Test Conversion Logic
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);
        demonstrateLengthConversion(1.0, LengthUnit.YARDS, LengthUnit.INCHES);

        // Test Method Overloading
        QuantityLength cmValue = new QuantityLength(10.0, LengthUnit.CENTIMETERS);
        demonstrateLengthConversion(cmValue, LengthUnit.INCHES);

        // Test Equality API
        QuantityLength oneYard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength threeFeet = new QuantityLength(3.0, LengthUnit.FEET);
        demonstrateLengthEquality(oneYard, threeFeet);
    }
}
