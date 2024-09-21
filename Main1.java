import java.util.Scanner;

// Class representing a linear function for constraints
class Function {
    private double coefficient; // Coefficient of x2
    private double resource;    // Resource limit
    private double divisor;     // Coefficient of x1

    // Constructor to initialize the function parameters
    public Function(double coefficient, double resource, double divisor) {
        this.coefficient = coefficient;
        this.resource = resource;
        this.divisor = divisor;
    }

    // Method to calculate x1 based on a given value of x2
    public double getX1(double x2) {
        return (resource - coefficient * x2) / divisor; // Calculate x1 using the constraint equation
    }
}

public class Main1 {

    // Method to get the maximum possible value of x1 based on constraints
    private static double getMaxX1(double[][] constraints, double[] resources, int[] conditions) {
        double max_x1 = Double.POSITIVE_INFINITY; // Initialize max_x1 to positive infinity
        for (int i = 0; i < resources.length; i++) {
            if (constraints[i][0] != 0) { // Avoid division by zero
                if (conditions[i] < 1) { // Check if the condition is less than or equal
                    max_x1 = Math.min(max_x1, resources[i] / constraints[i][0]); // Update max_x1
                }
            }
        }
        return max_x1; // Return the maximum value of x1 found
    }

    // Method to get the maximum possible value of x2 based on constraints
    public static double getMaxX2(double[][] constraints, double[] resources, int[] conditions) {
        double max_x2 = Double.POSITIVE_INFINITY; // Initialize max_x2 to positive infinity
        for (int i = 0; i < resources.length; i++) {
            if (constraints[i][1] != 0) { // Avoid division by zero
                if (conditions[i] != 1) { // Check if the condition is not greater than or equal
                    max_x2 = Math.min(max_x2, resources[i] / constraints[i][1]); // Update max_x2
                }
            }
        }
        return max_x2; // Return the maximum value of x2 found
    }

    // Method to create Function objects from constraints and resources
    private static Function[] createFunctions(double[][] constraints, double[] resources) {
        Function[] functions = new Function[constraints.length]; // Array to hold Function objects
        for (int i = 0; i < constraints.length; i++) {
            functions[i] = new Function(constraints[i][1], resources[i], constraints[i][0]); // Create Function objects
        }
        return functions; // Return the array of Function objects
    }

    // Method to solve for the maximum value based on constraints and coefficients
    private static double solveMaximize(double[][] constraints, double[] resources, Function[] functions, double[] coefficients, int[] conditions) {
        double maximumVal = Double.NEGATIVE_INFINITY; // Initialize maximumVal to negative infinity
        double maxX2 = getMaxX2(constraints, resources, conditions); // Get the maximum value of x2 based on constraints
        double step = 0.0001; // Define a small step for incrementing x values
        double x = 0; // Initialize x

        while (x <= maxX2) { // Loop through possible values of x up to maxX2
            double xCheckedVal = getMaxX1(constraints, resources, conditions); // Get the highest possible value for x1
            double xVal = 0;

            for (int i = 0; i < functions.length; i++) {
                if (conditions[i] != 1) { 
                    xVal = functions[i].getX1(x); // Calculate corresponding x1 for current x2
                }
                if (xVal >= 0) { // Ensure xVal is non-negative
                    xCheckedVal = Math.min(xCheckedVal, xVal); // Update checked value for x1 
                }
            }

            int errors = 0; 
            double value = coefficients[0] * xCheckedVal + coefficients[1] * x; // Calculate objective function value

            for (int i = 0; i < constraints.length; i++) { 
                // Ensure that current values of x1 and x2 follow restraints
                if (conditions[i] == 1 && constraints[i][0] * xCheckedVal + constraints[i][1] * x < constraints[i][2]) {
                    errors++; 
                } else if (conditions[i] == 0 && constraints[i][0] * xCheckedVal + constraints[i][1] * x > constraints[i][2]) {
                    errors++;
                }
            }

            if (errors > 0) { 
                // Should have 0 errors or we don't update max value
                x += step;
                continue;
            }
            maximumVal = Math.max(maximumVal, value); // Update maximum value found if valid
            x += step; // Increment x by defined step size
        }
        return maximumVal; // Return the found maximum value 
    }

    // Method to solve for the minimum value based on constraints and coefficients
    private static double solveMinimize(double[][] constraints, double[] resources, Function[] functions, double[] coefficients, int[] conditions) {
        double minimumVal = Double.POSITIVE_INFINITY; // Initialize minimumVal to positive infinity
        double maxX2 = getMaxX2(constraints, resources, conditions); 
        double step = 0.0001; 
        double x = 0;

        while (x <= maxX2) { 
            double xCheckedVal = getMaxX1(constraints, resources, conditions); 
            double xVal = 0;

            for (int i = 0; i < functions.length; i++) {
                if (conditions[i] != 1) { 
                    xVal = functions[i].getX1(x); 
                }
                if (xVal >= 0) { 
                    xCheckedVal = Math.min(xCheckedVal, xVal); 
                }
            }

            int errors = 0;
            double value = coefficients[0] * xCheckedVal + coefficients[1] * x;

            for (int i = 0; i < constraints.length; i++) { 
                if (conditions[i] == 1 && constraints[i][0] * xCheckedVal + constraints[i][1] * x < constraints[i][2]) {
                    errors++;
                } else if (conditions[i] == 0 && constraints[i][0] * xCheckedVal + constraints[i][1] * x > constraints[i][2]) {
                    errors++;
                }
            }

            if (errors > 0) { 
                x += step;
                continue;
            }
            minimumVal = Math.min(minimumVal, value); 
            x += step;
        }
        
        return minimumVal; 
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of constraints: ");
        int numConstraints = scanner.nextInt(); 

        double[][] constraints = new double[numConstraints][3]; 
        int[] conditions = new int[numConstraints]; 
        
        System.out.println("Enter the constraints in the format 'coeff_x1 coeff_x2 (< , > , <= , >= , =) resource':");
        
        for (int i = 0; i < numConstraints; i++) {
            System.out.print("Constraint " + (i + 1) + ": ");
            constraints[i][0] = scanner.nextDouble(); // Coefficient of x1
            constraints[i][1] = scanner.nextDouble(); // Coefficient of x2
            constraints[i][2] = scanner.nextDouble(); // Resource limit
            
            System.out.print("Enter condition for constraint " + (i + 1) + " (0 for <= , 1 for >=): ");
            conditions[i] = scanner.nextInt(); 
        }

        System.out.print("Enter the coefficients for the objective function in the format 'coeff_x1 coeff_x2': ");
        
        double[] coefficients = new double[2]; 
        coefficients[0] = scanner.nextDouble(); 
        coefficients[1] = scanner.nextDouble(); 

        Function[] functions = createFunctions(constraints, new double[numConstraints]);

        System.out.println("Maximum value: " + solveMaximize(constraints, new double[numConstraints], functions, coefficients, conditions));
        
        System.out.println("Minimum value: " + solveMinimize(constraints, new double[numConstraints], functions, coefficients, conditions));
        
        scanner.close(); // Close the scanner resource at end of usage.
    }
}