import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

class ConstraintRow {
    private JPanel rowPanel;
    private JTextField x1Field;
    private JTextField x2Field;
    private JComboBox<String> inequalityBox;
    private JTextField resultField;

    // Constructor to create the row with its components
    public ConstraintRow() {
        rowPanel = new JPanel();
        rowPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); 
        rowPanel.setBackground(new Color(209, 197, 180)); 

        JLabel lcX1 = new JLabel("X1 +");
        x1Field = new JTextField();
        x1Field.setPreferredSize(new Dimension(50, 30));

        JLabel lcX2 = new JLabel("X2");
        x2Field = new JTextField();
        x2Field.setPreferredSize(new Dimension(50, 30));

        String[] inequalities = {"<=", ">="};
        inequalityBox = new JComboBox<>(inequalities);
        inequalityBox.setPreferredSize(new Dimension(60, 30));

        resultField = new JTextField();
        resultField.setPreferredSize(new Dimension(50, 30));

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

    static ArrayList<ConstraintRow> constraintRows = new ArrayList<>();
    static JPanel rowContainer;


    public static void main(String[] args) {
  
        JFrame frame = new JFrame("Linear Programming Problems");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.getContentPane().setBackground(new Color(209, 197, 180));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
   
        JLabel heading = new JLabel("LPP Solver");
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        heading.setForeground(Color.BLACK);

        frame.add(Box.createRigidArea(new Dimension(0, 10)));
        frame.add(heading);
        frame.add(Box.createRigidArea(new Dimension(0, 10)));
 
        rowContainer = new JPanel();
        rowContainer.setLayout(new BoxLayout(rowContainer, BoxLayout.Y_AXIS));
        frame.add(rowContainer);
        addConstraintRow();
        addConstraintRow();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JButton plusButton = new JButton("+");
        plusButton.setPreferredSize(new Dimension(50, 30));
        plusButton.addActionListener(e -> {
            if (constraintRows.size() < 10) {
                addConstraintRow();
                frame.revalidate();
                frame.repaint();
            }
        });


        JButton minusButton = new JButton("-");
        minusButton.setPreferredSize(new Dimension(50, 30));
        minusButton.addActionListener(e -> {
            if (constraintRows.size() > 1) {
                removeConstraintRow();
                frame.revalidate();
                frame.repaint();
            }
        });


        buttonPanel.add(plusButton);
        buttonPanel.add(minusButton);
        buttonPanel.setBackground(new Color(209, 197, 180));

        frame.add(buttonPanel);

        JButton calculateButton = new JButton("Calculate");
        JTextField output = new JTextField();
        output.setPreferredSize(new Dimension(200, 40));

        // Center the button and output
        JPanel row6 = new JPanel();
        row6.setLayout(new FlowLayout(FlowLayout.CENTER));
        row6.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); 
        row6.setBackground(new Color(209, 197, 180)); 
        row6.add(calculateButton);
        row6.add(output);

        frame.add(row6);

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
}
