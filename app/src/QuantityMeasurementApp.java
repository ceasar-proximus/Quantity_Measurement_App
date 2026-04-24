public class QuantityMeasurementApp {

    // Inner class for Feet
    public static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Feet feet = (Feet) obj;
            return Double.compare(feet.value, value) == 0;
        }
    }

    // Inner class for Inches (Added for UC2)
    public static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Inches inches = (Inches) obj;
            return Double.compare(inches.value, value) == 0;
        }
    }

    // Static method to handle Feet comparison
    public static boolean compareFeet(double value1, double value2) {
        Feet f1 = new Feet(value1);
        Feet f2 = new Feet(value2);
        return f1.equals(f2);
    }

    // Static method to handle Inches comparison
    public static boolean compareInches(double value1, double value2) {
        Inches i1 = new Inches(value1);
        Inches i2 = new Inches(value2);
        return i1.equals(i2);
    }

    public static void main(String[] args) {
        // Checking Inches Equality
        boolean inchResult = compareInches(1.0, 1.0);
        System.out.println("Input: 1.0 inch and 1.0 inch");
        System.out.println("Output: Equal (" + inchResult + ")");

        // Checking Feet Equality
        boolean feetResult = compareFeet(1.0, 1.0);
        System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Output: Equal (" + feetResult + ")");
    }
}
