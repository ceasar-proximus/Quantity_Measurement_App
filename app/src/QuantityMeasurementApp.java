public class QuantityMeasurementApp {

    // Step 1: Enum to define units and their conversion factors to a base unit (Inches)
    public enum LengthUnit {
        FEET(12.0), INCHES(1.0);

        public final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }
    }

    // Step 2: Generic Quantity class for Length
    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {
            // Standard equality checks
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength that = (QuantityLength) obj;

            // Convert both values to a base unit (Inches) before comparing
            double value1 = this.value * this.unit.conversionFactor;
            double value2 = that.value * that.unit.conversionFactor;

            return Double.compare(value1, value2) == 0;
        }
    }

    public static void main(String[] args) {
        // Test Case: Feet to Inches (1 ft = 12 inch)
        QuantityLength oneFeet = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength twelveInches = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println("Input: 1.0 ft and 12.0 inches");
        System.out.println("Output: Equal (" + oneFeet.equals(twelveInches) + ")");

        // Test Case: Inch to Inch
        QuantityLength oneInch = new QuantityLength(1.0, LengthUnit.INCHES);
        QuantityLength anotherInch = new QuantityLength(1.0, LengthUnit.INCHES);

        System.out.println("\nInput: 1.0 inch and 1.0 inch");
        System.out.println("Output: Equal (" + oneInch.equals(anotherInch) + ")");
    }
}
