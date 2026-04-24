public class QuantityMeasurementApp {
    // Inner class representing Feet measurement
    public static class Feet {
        private final double value;

        // Constructor to initialize value
        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            // 1. Check for same reference (Reflexive)
            if (this == obj) return true;

            // 2. Check for null and ensure same class (Type Safety)
            if (obj == null || getClass() != obj.getClass()) return false;

            // 3. Cast to Feet type
            Feet that = (Feet) obj;

            // 4. Compare double values precisely
            return Double.compare(this.value, that.value) == 0;
        }
    }

    public static void main(String[] args) {
        // Step 6: Test values
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        Feet f3 = new Feet(2.0);

        System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Output: Equal (" + f1.equals(f2) + ")");

        System.out.println("\nInput: 1.0 ft and 2.0 ft");
        System.out.println("Output: Equal (" + f1.equals(f3) + ")");
    }
}
