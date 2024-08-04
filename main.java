
class Function { // used in stage 2

    private double coefficient;
    private double resource;
    private double divisor;

    public Function(double coefficient, double resource, double divisor) { // constructor to allow us to make functions in stage 2
        this.coefficient = coefficient;
        this.resource = resource;
        this.divisor = divisor;
    }

    public double getX1(double x2) {
        return (resource - coefficient * x2) / divisor; // how to find x1 when given x2 with eqn divisor*x1+coeff*x2<=resource
    }
}

public class main {

    private static double getMaxX1(double[][] constraints, double[] resources, int[] conditions) {
        double max_x1 = Double.POSITIVE_INFINITY;
        for (int i = 0; i < resources.length; i++) {
            if (constraints[i][0] != 0) { // Avoid division by zero
                if (conditions[i] < 1) {
                    max_x1 = Math.min(max_x1, resources[i] / constraints[i][0]);
                }
            }
        }
        return max_x1;
    }

    private static double getMinX1(double[][] constraints, double[] resources, int[] conditions) {
        double min_x1 = 0;
        for (int i = 0; i < resources.length; i++) {
            if (constraints[i][0] != 0) { // Avoid division by zero
                if (conditions[i] == 1) {
                    min_x1 = Math.max(min_x1, resources[i] / constraints[i][0]);
                }
            }
        }
        return min_x1;
    }

    public static double getMaxX2(double[][] constraints, double[] resources, int[] conditions) { // find constraints for x2
        double max_x2 = Double.POSITIVE_INFINITY;
        for (int i = 0; i < resources.length; i++) {
            if (constraints[i][1] != 0) { // Avoid division by zero
                if (conditions[i] != 1) {
                    max_x2 = Math.min(max_x2, resources[i] / constraints[i][1]);
                }
            }
        }
        return max_x2;
    }

    // private static double getMinX2(double[][] constraints, double[] resources,int [] conditions) {
    //     double min_x2 = 0;
    //     for (int i = 0; i < resources.length; i++) {
    //         if (constraints[i][1] != 0) { // Avoid division by zero
    //           if (conditions[i] ==1)
    //             min_x2 = Math.max(min_x2, resources[i] / constraints[i][1]);
    //         }
    //     }
    //     return min_x2;
    // }
    private static Function[] createFunctions(double[][] constraints, double[] resources) {
        Function[] functions = new Function[constraints.length];
        for (int i = 0; i < constraints.length; i++) {
            functions[i] = new Function(constraints[i][1], resources[i], constraints[i][0]);
        }
        return functions;
    }

    private static double solve(double[][] constraints, double[] resources, Function[] functions, double[] coefficients, int[] conditions) {
        double maximumVal = Double.NEGATIVE_INFINITY;
        double maxX2 = getMaxX2(constraints, resources, conditions);
        double step = 0.0001;
        double x = 0;
        double xCheckedVal, xVal = 0;
        System.out.println(x);
        System.out.println(maxX2);
        System.out.println(getMaxX1(constraints, resources, conditions));
        System.out.println(getMinX1(constraints, resources, conditions));
        while (x <= maxX2) {
            xCheckedVal = getMaxX1(constraints, resources, conditions);
            for (int i = 0; i < functions.length; i++) {
                if (conditions[i] != 1) {
                    xVal = functions[i].getX1(x);
                }
                if (xVal >= 0) { // Ensure xVal is non-negative

                    xCheckedVal = Math.min(xCheckedVal, xVal);
                }
            }
            int errors = 0;
            double value = coefficients[0] * xCheckedVal + coefficients[1] * x;
            for (int i = 0; i < constraints.length; i++) {
                if (conditions[i] == 1 && constraints[i][0] * xCheckedVal + constraints[i][1] * x < constraints[i][2]) {
                    errors++; 
                }else if (conditions[i] == 0 && constraints[i][0] * xCheckedVal + constraints[i][1] * x > constraints[i][2]) {
                    errors++;
                }
            }
            if (errors > 0) {
                x += step;
                continue;
            }
            maximumVal = Math.max(maximumVal, value);
            x += step;
        }
        return maximumVal;
    }

    public static void main(String[] args) {
        double[][] constraints = {{10, 17, 120}, {13, 21, 20}, {4, 5, 48}};// accept input
        int[] conditions = {0, 1, 0};
        double[] coefficients = {40, 50};
        double[] resources = new double[constraints.length];
        int counter = 0;

        for (int i = 0; i < constraints.length; i++) {
            for (int j = 0; j < constraints[i].length; j++) {
                if (j == 2) {
                    resources[counter] = constraints[i][j];
                    counter++;
                }
            }
        }

        Function[] functions = createFunctions(constraints, resources);
        System.out.println(solve(constraints, resources, functions, coefficients, conditions));
    }
}
