public class QuantityMeasurementApp {

    // Step 1: Update Enum with Yards and Centimeters
    // Base Unit: INCHES (1.0)
    public enum LengthUnit {
        FEET(12.0),
        INCHES(1.0),
        YARDS(36.0),         // 1 Yard = 3 Feet = 36 Inches
        CENTIMETERS(0.393701); // 1 CM = 0.393701 Inches

        public final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }
    }

    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength that = (QuantityLength) obj;

            // Convert to base unit (Inches) for comparison
            double value1 = this.value * this.unit.conversionFactor;
            double value2 = that.value * that.unit.conversionFactor;

            // Using a small delta for floating point comparison of CM
            return Math.abs(value1 - value2) < 0.00001;
        }
    }

    public static void main(String[] args) {
        // Yard to Feet
        System.out.println("1 Yard == 3 Feet: " +
                new QuantityLength(1.0, LengthUnit.YARDS).equals(new QuantityLength(3.0, LengthUnit.FEET)));

        // Yard to Inches
        System.out.println("1 Yard == 36 Inches: " +
                new QuantityLength(1.0, LengthUnit.YARDS).equals(new QuantityLength(36.0, LengthUnit.INCHES)));

        // Centimeters to Inches
        System.out.println("1 CM == 0.393701 Inches: " +
                new QuantityLength(1.0, LengthUnit.CENTIMETERS).equals(new QuantityLength(0.393701, LengthUnit.INCHES)));

        // Transitive Property (1 Yard -> 3 Feet -> 36 Inches)
        QuantityLength y = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength f = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength i = new QuantityLength(36.0, LengthUnit.INCHES);
        System.out.println("Transitive (Y=F and F=I, so Y=I): " + (y.equals(f) && f.equals(i) && y.equals(i)));
    }
}
