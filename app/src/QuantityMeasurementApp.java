import java.util.Objects;

public class QuantityMeasurementApp {

    public enum LengthUnit {
        FEET(12.0), INCHES(1.0), YARDS(36.0), CENTIMETERS(0.393701);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        // Helper to get total value in base unit (Inches)
        private double toBase(double value) {
            return value * this.conversionFactor;
        }

        // Helper to convert from base unit back to this unit
        private double fromBase(double baseValue) {
            return baseValue / this.conversionFactor;
        }
    }

    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
            this.unit = Objects.requireNonNull(unit, "Unit cannot be null");
            this.value = value;
        }

        /**
         * UC6: Adds another length to this one.
         * Returns result in the unit of the first operand (this).
         */
        public QuantityLength add(QuantityLength that) {
            Objects.requireNonNull(that, "Operand cannot be null");

            // 1. Convert both to base unit
            double sumInBase = this.unit.toBase(this.value) + that.unit.toBase(that.value);

            // 2. Convert sum back to 'this' unit
            double finalValue = this.unit.fromBase(sumInBase);

            return new QuantityLength(finalValue, this.unit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityLength that = (QuantityLength) obj;
            // Precision-safe comparison (4 decimal places)
            return Math.abs(this.unit.toBase(this.value) - that.unit.toBase(that.value)) < 0.0001;
        }

        @Override
        public String toString() {
            return String.format("%.2f %s", value, unit);
        }
    }

    // --- API Demonstration Methods ---

    public static void demonstrateAddition(QuantityLength l1, QuantityLength l2) {
        QuantityLength result = l1.add(l2);
        System.out.println("Addition: (" + l1 + ") + (" + l2 + ") = " + result);
    }

    public static void main(String[] args) {
        // Test 1: 1 ft + 12 in = 2 ft
        QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength twelveInches = new QuantityLength(12.0, LengthUnit.INCHES);
        demonstrateAddition(oneFoot, twelveInches);

        // Test 2: 2 in + 5 cm = ? in
        QuantityLength twoInches = new QuantityLength(2.0, LengthUnit.INCHES);
        QuantityLength fiveCm = new QuantityLength(5.0, LengthUnit.CENTIMETERS);
        demonstrateAddition(twoInches, fiveCm);

        // Test 3: Commutative Property (A+B should equal B+A in value)
        QuantityLength resultAB = oneFoot.add(twelveInches); // Results in Feet
        QuantityLength resultBA = twelveInches.add(oneFoot); // Results in Inches
        System.out.println("Commutative Check (Equal values?): " + resultAB.equals(resultBA));
    }
}
