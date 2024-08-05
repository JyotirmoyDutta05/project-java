import javax.swing.*;
import java.awt.*;

public class LinearProgrammingGUI {

    public static void main(String[] args) {
        // Create a new JFrame
        JFrame frame = new JFrame("Linear Programming Problems");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);

        // Set the layout to null for absolute positioning
        frame.setLayout(null);

        // Set background color
        frame.getContentPane().setBackground(new Color(209, 197, 180));

        // Create the heading label
        JLabel heading = new JLabel("LPP Solver");
        heading.setBounds(50, 10, 400, 30);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.BLACK);

        // Create labels and text fields for the Z function
        JLabel lz = new JLabel("Z =");
        JLabel lx1 = new JLabel("X1 +");
        JLabel lx2 = new JLabel("X2");
        JTextField tx1 = new JTextField();
        JTextField tx2 = new JTextField();

        lz.setBounds(160, 50, 30, 30);
        tx1.setBounds(200, 50, 50, 30);
        lx1.setBounds(260, 50, 50, 30);
        tx2.setBounds(310, 50, 50, 30);
        lx2.setBounds(370, 50, 50, 30);

        // Create radio buttons for Maximize and Minimize
        JRadioButton max = new JRadioButton("Maximize");
        JRadioButton min = new JRadioButton("Minimize");
        ButtonGroup bg = new ButtonGroup();
        bg.add(max);
        bg.add(min);
        max.setBounds(150, 100, 100, 30);
        min.setBounds(260, 100, 100, 30);
        max.setSelected(true);

        // Create the constraints instruction label
        JLabel constraintsLabel = new JLabel("Enter the constraints as shown:");
        constraintsLabel.setBounds(50, 150, 400, 30);
        constraintsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        constraintsLabel.setForeground(Color.BLACK);

        // Create labels and text fields for constraints
        JLabel lc1x1 = new JLabel("X1 +");
        JLabel lc1x2 = new JLabel("X2");
        JLabel lc2x1 = new JLabel("X1 +");
        JLabel lc2x2 = new JLabel("X2");
        JTextField tc1x1 = new JTextField();
        JTextField tc1x2 = new JTextField();
        JTextField tc2x1 = new JTextField();
        JTextField tc2x2 = new JTextField();
        JTextField tc1r1 = new JTextField();
        JTextField tc2r2 = new JTextField();
        String[] inequalities = {"<=", ">="};
        JComboBox<String> cb1 = new JComboBox<>(inequalities);
        JComboBox<String> cb2 = new JComboBox<>(inequalities);

        lc1x1.setBounds(230, 200, 30, 30);
        tc1x1.setBounds(170, 200, 50, 30);
        lc1x2.setBounds(330, 200, 30, 30);
        tc1x2.setBounds(270, 200, 50, 30);
        cb1.setBounds(350, 200, 60, 30);
        tc1r1.setBounds(420, 200, 50, 30);

        lc2x1.setBounds(230, 250, 30, 30);
        tc2x1.setBounds(170, 250, 50, 30);
        lc2x2.setBounds(330, 250, 30, 30);
        tc2x2.setBounds(270, 250, 50, 30);
        cb2.setBounds(350, 250, 60, 30);
        tc2r2.setBounds(420, 250, 50, 30);

        // Create the calculate button
        JButton calculateButton = new JButton("Calculate");
        calculateButton.setBounds(250, 300, 100, 30);

        // Set text color to black for all labels and text fields
        heading.setForeground(Color.BLACK);
        lz.setForeground(Color.BLACK);
        lx1.setForeground(Color.BLACK);
        lx2.setForeground(Color.BLACK);
        max.setForeground(Color.BLACK);
        min.setForeground(Color.BLACK);
        constraintsLabel.setForeground(Color.BLACK);
        lc1x1.setForeground(Color.BLACK);
        lc1x2.setForeground(Color.BLACK);
        lc2x1.setForeground(Color.BLACK);
        lc2x2.setForeground(Color.BLACK);

        // Add components to the frame
        frame.add(heading);
        frame.add(lz);
        frame.add(tx1);
        frame.add(lx1);
        frame.add(tx2);
        frame.add(lx2);
        frame.add(max);
        frame.add(min);
        frame.add(constraintsLabel);
        frame.add(lc1x1);
        frame.add(tc1x1);
        frame.add(lc1x2);
        frame.add(tc1x2);
        frame.add(cb1);
        frame.add(tc1r1);
        frame.add(lc2x1);
        frame.add(tc2x1);
        frame.add(lc2x2);
        frame.add(tc2x2);
        frame.add(cb2);
        frame.add(tc2r2);
        frame.add(calculateButton);

        // Set frame to visible
        frame.setVisible(true);
    }
}
