import java.util.Objects;

public class QuantityMeasurementApp {

    public enum LengthUnit {
        FEET(12.0), INCHES(1.0), YARDS(36.0), CENTIMETERS(0.393701);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        private double toBase(double value) {
            return value * this.conversionFactor;
        }

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
         * UC6: Add using the unit of the first operand as default
         */
        public QuantityLength add(QuantityLength that) {
            return add(this, that, this.unit);
        }

        /**
         * UC7: Addition with explicit target unit specification (Static Overload)
         */
        public static QuantityLength add(QuantityLength l1, QuantityLength l2, LengthUnit targetUnit) {
            Objects.requireNonNull(l1, "First operand is null");
            Objects.requireNonNull(l2, "Second operand is null");
            Objects.requireNonNull(targetUnit, "Target unit is null");

            // Convert both to base, sum, and convert to target
            double sumInBase = l1.unit.toBase(l1.value) + l2.unit.toBase(l2.value);
            double resultValue = targetUnit.fromBase(sumInBase);

            return new QuantityLength(resultValue, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityLength that = (QuantityLength) obj;
            return Math.abs(this.unit.toBase(this.value) - that.unit.toBase(that.value)) < 0.001;
        }

        @Override
        public String toString() {
            return String.format("%.3f %s", value, unit);
        }
    }

    public static void main(String[] args) {
        QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength twelveInches = new QuantityLength(12.0, LengthUnit.INCHES);

        // UC7 Test: 1 Foot + 12 Inches -> Result in YARDS
        QuantityLength resultInYards = QuantityLength.add(oneFoot, twelveInches, LengthUnit.YARDS);

        System.out.println("Adding " + oneFoot + " and " + twelveInches);
        System.out.println("Target Unit: YARDS");
        System.out.println("Result: " + resultInYards); // Should be ~0.667 YARDS

        // UC7 Test: Symmetry/Commutativity
        QuantityLength resultSymmetric = QuantityLength.add(twelveInches, oneFoot, LengthUnit.YARDS);
        System.out.println("Commutative Check: " + resultInYards.equals(resultSymmetric));
    }
}
