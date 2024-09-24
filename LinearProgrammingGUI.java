import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;

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

class ConstraintRow {

    private JPanel rowPanel;
    JTextField x1Field;
    JTextField x2Field;
    JComboBox<String> inequalityBox;
    JTextField resultField;

    // Constructor to create the row with its components
    public ConstraintRow() {
        rowPanel = new JPanel();
        rowPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        rowPanel.setBackground(Color.decode("#FAF7F0"));

        JLabel lcX1 = new JLabel("X1 +");
        x1Field = new JTextField();
        x1Field.setPreferredSize(new Dimension(50, 40));
        x1Field.setFont(new Font("Times New Roman", Font.BOLD, 20));
        x1Field.setBackground(Color.decode("#B17457"));

        JLabel lcX2 = new JLabel("X2");

        lcX1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lcX2.setFont(new Font("Times New Roman", Font.BOLD, 20));

        x2Field = new JTextField();
        x2Field.setPreferredSize(new Dimension(50, 40));
        x2Field.setFont(new Font("Times New Roman", Font.BOLD, 20));
        x2Field.setBackground(Color.decode("#B17457"));

        String[] inequalities = {"<=", ">="};
        inequalityBox = new JComboBox<>(inequalities);
        inequalityBox.setPreferredSize(new Dimension(60, 40));
        inequalityBox.setFont(new Font("Times New Roman", Font.BOLD, 20));
        inequalityBox.setBackground(Color.decode("#B17457"));

        resultField = new JTextField();
        resultField.setPreferredSize(new Dimension(50, 40));
        resultField.setFont(new Font("Times New Roman", Font.BOLD, 20));
        resultField.setBackground(Color.decode("#B17457"));

        rowPanel.add(x1Field);
        rowPanel.add(lcX1);
        rowPanel.add(x2Field);
        rowPanel.add(lcX2);
        rowPanel.add(inequalityBox);
        rowPanel.add(resultField);
    }

    // Method to return the JPanel representing this row
    public JPanel getRowPanel() {
        return rowPanel;
    }
}

public class LinearProgrammingGUI {

    private static void setForegroundColor(Container container, Color color) {
        for (Component component : container.getComponents()) {
            if (component instanceof Container) {
                // Recursively set foreground color for nested containers
                setForegroundColor((Container) component, color);
            }
            component.setForeground(Color.decode("#4A4947")); // Set the foreground color
        }
    }

    static ArrayList<ConstraintRow> constraintRows = new ArrayList<>();
    static JPanel rowContainer;
    static JComboBox<String> comboBox;
    static JTextField output;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Linear Programming Problems");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.getContentPane().setBackground(Color.decode("#FAF7F0"));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("LPP Solver");
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        heading.setForeground(Color.BLACK);
        heading.setFont(new Font("Times New Roman", Font.BOLD, 20));

        frame.add(Box.createRigidArea(new Dimension(0, 10)));
        frame.add(heading);
        frame.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel objFunction = new JPanel();
        objFunction.setLayout(new FlowLayout(FlowLayout.CENTER));
        objFunction.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        objFunction.setBackground(Color.decode("#FAF7F0"));

        JLabel lz = new JLabel("Z =");
        JLabel lx1 = new JLabel("X1 +");
        JLabel lx2 = new JLabel("X2");
        JTextField tx1 = new JTextField();
        JTextField tx2 = new JTextField();
        lz.setPreferredSize(new Dimension(30, 40));
        lz.setFont(new Font("Arial", Font.BOLD, 20));
        lx1.setPreferredSize(new Dimension(50, 40));
        lx1.setFont(new Font("Arial", Font.BOLD, 20));
        lx2.setPreferredSize(new Dimension(50, 40));
        lx2.setFont(new Font("Arial", Font.BOLD, 20));
        tx1.setPreferredSize(new Dimension(50, 40));
        tx1.setFont(new Font("Arial", Font.BOLD, 20));
        tx1.setBackground(Color.decode("#B17457"));
        tx2.setPreferredSize(new Dimension(50, 40));
        tx2.setFont(new Font("Arial", Font.BOLD, 20));
        tx2.setBackground(Color.decode("#B17457"));

        objFunction.add(lz);
        objFunction.add(tx1);
        objFunction.add(lx1);
        objFunction.add(tx2);
        objFunction.add(lx2);

        frame.add(objFunction);

        String[] options = {"Maximize", "Minimize"};
        comboBox = new JComboBox<>(options);
        comboBox.setFont(new Font("Arial", Font.BOLD, 20));
        comboBox.setBackground(Color.decode("#B17457"));

        JPanel maxMin = new JPanel();
        maxMin.setLayout(new FlowLayout(FlowLayout.CENTER));
        maxMin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        maxMin.setBackground(Color.decode("#FAF7F0"));

        // Set preferred size for the JComboBox
        comboBox.setPreferredSize(new Dimension(120, 40));
        maxMin.add(comboBox);
        frame.add(maxMin);
        rowContainer = new JPanel();
        rowContainer.setLayout(new BoxLayout(rowContainer, BoxLayout.Y_AXIS));
        frame.add(rowContainer);
        addConstraintRow();
        addConstraintRow();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        JButton plusButton = new JButton("+");
        plusButton.setPreferredSize(new Dimension(50, 30));
        plusButton.addActionListener(e -> {
            if (constraintRows.size() < 10) {
                addConstraintRow();
                frame.revalidate();
                frame.repaint();
            }
        });
        plusButton.setBackground(Color.decode("#B17457"));
        plusButton.setFont(new Font("Arial", Font.BOLD, 20));

        JButton minusButton = new JButton("-");
        minusButton.setPreferredSize(new Dimension(50, 30));
        minusButton.addActionListener(e -> {
            if (constraintRows.size() > 1) {
                removeConstraintRow();
                frame.revalidate();
                frame.repaint();
            }
        });
        minusButton.setBackground(Color.decode("#B17457"));
        minusButton.setFont(new Font("Arial", Font.BOLD, 20));

        buttonPanel.add(plusButton);
        buttonPanel.add(minusButton);
        buttonPanel.setBackground(Color.decode("#FAF7F0"));

        frame.add(buttonPanel);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        calculateButton.addActionListener(e -> {
            try {
                double x1Value = Double.parseDouble(tx1.getText().trim());
                double x2Value = Double.parseDouble(tx2.getText().trim());
                calculateResult(x1Value, x2Value);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numeric values for X1 and X2.\nIn objective function", "Input Error", JOptionPane.ERROR_MESSAGE);
            }

        });
        calculateButton.setBackground(Color.decode("#B17457"));

        output = new JTextField();
        output.setPreferredSize(new Dimension(400, 45));
        output.setFont(new Font("Times New Roman", Font.BOLD, 16));
        output.setEditable(false);
        output.setBackground(Color.decode("#B17457"));
        // Center the button and output
        JPanel row6 = new JPanel();
        row6.setLayout(new FlowLayout(FlowLayout.CENTER));
        row6.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        row6.setBackground(Color.decode("#FAF7F0"));
        row6.add(calculateButton);
        row6.add(output);

        frame.add(row6);
        setForegroundColor(frame, Color.decode("#7CF5FF"));
        frame.setVisible(true);
    }

    // add another constraint row
    private static void addConstraintRow() {
        ConstraintRow constraintRow = new ConstraintRow();
        constraintRows.add(constraintRow);//add rows
        rowContainer.add(constraintRow.getRowPanel());
    }

    // remove last row
    private static void removeConstraintRow() {
        if (!constraintRows.isEmpty()) {
            ConstraintRow lastRow = constraintRows.remove(constraintRows.size() - 1);//pop out last element ad set that last row
            rowContainer.remove(lastRow.getRowPanel());
        }
    }

    static double getMaxX1(double[][] constraints, int[] conditions) {
        double max_x1 = Double.POSITIVE_INFINITY; // Initialize max_x1 to positive infinity
        for (int i = 0; i < constraints.length; i++) {
            if (constraints[i][0] != 0) { // Avoid division by zero
                if (conditions[i] < 1) { // Check if the condition is less than or equal
                    max_x1 = Math.min(max_x1, constraints[i][2] / constraints[i][0]); // Update max_x1
                }
            }
        }
        return max_x1; // Return the maximum value of x1 found
    }

    public static double getMaxX2(double[][] constraints, int[] conditions) {
        double max_x2 = Double.POSITIVE_INFINITY; // Initialize max_x2 to positive infinity
        for (int i = 0; i < constraints.length; i++) {
            if (constraints[i][1] != 0) { // Avoid division by zero
                if (conditions[i] != 1) { // Check if the condition is not greater than or equal
                    max_x2 = Math.min(max_x2, constraints[i][2] / constraints[i][1]); // Update max_x2
                }
            }
        }
        return max_x2; // Return the maximum value of x2 found
    }

    private static Function[] createFunctions(double[][] constraints) {
        Function[] functions = new Function[constraints.length]; // Array to hold Function objects
        for (int i = 0; i < constraints.length; i++) {
            functions[i] = new Function(constraints[i][1], constraints[i][2], constraints[i][0]); // Create Function objects
        }
        return functions; // Return the array of Function objects
    }

    private static double[] solveMaximize(double[][] constraints, Function[] functions, double[] coefficients, int[] conditions) {
        double maximumVal = Double.NEGATIVE_INFINITY; // Initialize maximumVal to negative infinity
        double maxX2 = getMaxX2(constraints, conditions); // Get the maximum value of x2 based on constraints
        double step = 0.0001; // Define a small step for incrementing x values
        double x = 0; // Initialize x
        double optimalX1 = 0;
        double optimalX2 = 0;

        while (x <= maxX2) { // Loop through possible values of x up to maxX2
            double xCheckedVal = getMaxX1(constraints, conditions); // Get the highest possible value for x1
            double xVal = 0;

            for (int i = 0; i < functions.length; i++) {
                if (conditions[i] != 1) {
                    xVal = functions[i].getX1(x); // Calculate corresponding x1 for current x2
                }
                if (xVal >= 0) {
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

                x += step;
                continue;
            }
            if (value > maximumVal) {
                maximumVal = value;
                optimalX1 = xCheckedVal;
                optimalX2 = x;
            }
            x += step;
        }
        return new double[]{maximumVal, optimalX1, optimalX2};
    }

    public static double getMinX2(double constraints[][], int[] conditions) {
        double min_x2 = 0;
        for (int i = 0; i < constraints.length; i++) {
            if (constraints[i][1] != 0) {
                if (conditions[i] == 1) { // Check if the condition is greater than or equal
                    min_x2 = Math.max(min_x2, constraints[i][2] / constraints[i][1]);
                }
            }
        }
        return min_x2; // Return the maximum value of x2 found
    }

    private static double[] solveMinimize(double[][] constraints, Function[] functions, double[] coefficients, int[] conditions) {
        double minimumVal = Double.POSITIVE_INFINITY;
        double maxX2 = getMinX2(constraints, conditions);
        double step = 0.0001;
        double x = 0;
        double optimalX1 = 0;
        double optimalX2 = 0;

        while (x <= maxX2) {
            double xCheckedVal = 0;
            double xVal = 0;

            for (int i = 0; i < functions.length; i++) {
                if (conditions[i] == 1) {
                    xVal = functions[i].getX1(x);
                }
                if (xVal >= 0) {
                    xCheckedVal = Math.max(xCheckedVal, xVal);
                }
            }

            int errors = 0;
            double value = coefficients[0] * xCheckedVal + coefficients[1] * x;
            if (x == 0) {
                System.out.println(xCheckedVal);
                System.out.println(x);
                System.out.println(value);
            }
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
            if (value < minimumVal) {
                minimumVal = value;
                optimalX1 = xCheckedVal;
                optimalX2 = x;
            }
            x += step;
        }

        return new double[]{minimumVal, optimalX1, optimalX2};
    }

    static void calculateResult(double tx1, double tx2) {

        //getting values from gui
        int numConstraints = constraintRows.size();
        double[][] constraints = new double[numConstraints][3];
        int[] conditions = new int[numConstraints];

        double[] coefficients = new double[2];
        coefficients[0] = tx1;
        coefficients[1] = tx2;
        boolean inputError = false; // Flag to check for input errors

        try {
            for (int i = 0; i < numConstraints; i++) {
                ConstraintRow row = constraintRows.get(i);
                // Check if inputs are valid
                try {
                    double x1Value = Double.parseDouble(row.x1Field.getText().trim());
                    double x2Value = Double.parseDouble(row.x2Field.getText().trim());
                    double resultValue = Double.parseDouble(row.resultField.getText().trim());

                    constraints[i][0] = x1Value;
                    constraints[i][1] = x2Value;
                    constraints[i][2] = resultValue;

                    String inequality = (String) row.inequalityBox.getSelectedItem();
                    if ("<=".equals(inequality)) {
                        conditions[i] = 0; // 0 for <=
                    } else {
                        conditions[i] = 1; // 1 for >=
                    }
                } catch (NumberFormatException ex) {
                    // Invalid input found in this row, show error message and set the error flag
                    inputError = true;
                    JOptionPane.showMessageDialog(null,
                            "Invalid input in constraint row " + (i + 1) + ". Please enter valid numeric values.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                    break; // Stop further processing and let the user correct the input
                }
            }

            if (inputError) {
                return; // Exit the function without further processing
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error occurred while processing constraints. Please check the inputs.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return; // Stop execution if there was a general exception
        }
        boolean max = "Maximize" == comboBox.getSelectedItem();
        double[] ans = new double[3];

        Function[] functions = createFunctions(constraints);
        if (max) {
            ans = solveMaximize(constraints, functions, coefficients, conditions);
        } else {
            ans = solveMinimize(constraints, functions, coefficients, conditions);
        }

        for (int i = 0; i < ans.length; i++) {
            DecimalFormat df = new DecimalFormat("#.####");
            String formatted = df.format(ans[i]); // Format the number
            ans[i] = Double.parseDouble(formatted);
        }
        if (ans[0] == Double.NEGATIVE_INFINITY || ans[0] == Double.POSITIVE_INFINITY) {
            output.setText(("NO SOLUTION"));
        }
        output.setText("Optimal Output " + ans[0] + " at " + ans[1] + "x1 and " + ans[2] + "x2");
        System.out.println("Optimal Output " + ans[0] + " at " + ans[1] + "x1 and " + ans[2] + "x2");
    }

}
