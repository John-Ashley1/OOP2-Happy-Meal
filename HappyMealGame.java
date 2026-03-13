import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HappyMealGame extends JFrame implements ActionListener {

    JTextField nameField, ageField;
    JButton confirmButton;

    public HappyMealGame() {

        setTitle("HAPPY MEAL");
        setSize(900,600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6,1));

        JLabel title = new JLabel("WELCOME TO HAPPY MEAL TOURNAMENT", JLabel.CENTER);

        JLabel nameLabel = new JLabel("Enter Name:");
        nameField = new JTextField();

        JLabel ageLabel = new JLabel("Enter Age:");
        ageField = new JTextField();

        confirmButton = new JButton("CONFIRM");
        confirmButton.addActionListener(this);

        add(title);
        add(nameLabel);
        add(nameField);
        add(ageLabel);
        add(ageField);
        add(confirmButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String name = nameField.getText().trim();
        String age = ageField.getText().trim();

        if (name.isEmpty() || age.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both your Name and Age to continue.",
                "Input Required",
                JOptionPane.WARNING_MESSAGE
            );
            return; 
        }

        try {
            int ageNumber = Integer.parseInt(age);
            if (ageNumber <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid numeric Age.",
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        JOptionPane.showMessageDialog(this,
            "Welcome " + name + "!\nAge: " + age +
            "\nPrepare for the Happy Meal Tournament!"
        );

        new GameModeMenu(name);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new IntroScreen());
    }
}