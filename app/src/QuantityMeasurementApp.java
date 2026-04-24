import java.util.Objects;

/**
 * UC8: Single-file implementation with standalone-style architecture.
 * The Enum now holds the "Responsibility" for conversion.
 */
public class QuantityMeasurementApp {

    // --- Standalone Enum Architecture ---
    enum LengthUnit {
        FEET(12.0),
        INCHES(1.0),
        YARDS(36.0),
        CENTIMETERS(0.393701);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        // Unit's new responsibility: Convert TO base (Inches)
        public double convertToBaseUnit(double value) {
            return value * this.conversionFactor;
        }

        // Unit's new responsibility: Convert FROM base (Inches)
        public double convertFromBaseUnit(double baseValue) {
            return baseValue / this.conversionFactor;
        }
    }

    // --- Quantity Class (Delegates logic to the Unit) ---
    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid number");
            this.value = value;
            this.unit = Objects.requireNonNull(unit, "Unit required");
        }

        public QuantityLength convertTo(LengthUnit targetUnit) {
            double base = this.unit.convertToBaseUnit(this.value);
            return new QuantityLength(targetUnit.convertFromBaseUnit(base), targetUnit);
        }

        public static QuantityLength add(QuantityLength l1, QuantityLength l2, LengthUnit target) {
            double sumBase = l1.unit.convertToBaseUnit(l1.value) + l2.unit.convertToBaseUnit(l2.value);
            return new QuantityLength(target.convertFromBaseUnit(sumBase), target);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityLength that = (QuantityLength) obj;
            return Math.abs(this.unit.convertToBaseUnit(this.value) -
                    that.unit.convertToBaseUnit(that.value)) < 0.001;
        }

        @Override
        public String toString() {
            return String.format("%.2f %s", value, unit);
        }
    }

    // --- Main Method for Verification ---
    public static void main(String[] args) {
        // Equality Check
        QuantityLength oneYard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength threeFeet = new QuantityLength(3.0, LengthUnit.FEET);
        System.out.println("1 Yard == 3 Feet: " + oneYard.equals(threeFeet));

        // Conversion Check
        System.out.println("1 Yard in Inches: " + oneYard.convertTo(LengthUnit.INCHES));

        // Addition Check
        QuantityLength sum = QuantityLength.add(oneYard, new QuantityLength(12, LengthUnit.INCHES), LengthUnit.FEET);
        System.out.println("1 Yard + 12 Inches in Feet: " + sum); // Result: 4.00 FEET
    }
}
