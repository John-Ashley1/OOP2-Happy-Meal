import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class HappyMealGame extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JTextField nameField, ageField;
    private JButton confirmButton;

    public HappyMealGame() {
        // 1. Window Setup (Matching the IntroScreen dimensions)
        setTitle("Player Registration");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 2. Custom Background Panel
        // Using GridBagLayout here automatically centers whatever we put inside it
        mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Deep arcade gradient (Midnight Blue to Deep Purple)
                GradientPaint gp = new GradientPaint(0, 0, new Color(15, 15, 25), 0, getHeight(), new Color(45, 10, 50));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        setContentPane(mainPanel);

        // 3. The Registration Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS)); // Vertical stacking
        formPanel.setOpaque(false); // Make it transparent so the gradient shows through

        // Shared Fonts and Colors
        Font titleFont = new Font("Monospaced", Font.BOLD, 28);
        Font labelFont = new Font("Monospaced", Font.BOLD, 18);
        Font inputFont = new Font("Monospaced", Font.BOLD, 20);
        Color arcadeGold = new Color(255, 215, 0);
        Color terminalGreen = new Color(50, 255, 50);

        // --- TITLE ---
        JLabel title = new JLabel("Welcome to the Happy Meal Tournament");
        title.setFont(titleFont);
        title.setForeground(arcadeGold);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- NAME INPUT ---
        JLabel nameLabel = new JLabel("ENTER NAME:");
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameField = new JTextField(15);
        nameField.setFont(inputFont);
        nameField.setBackground(new Color(10, 10, 15)); // Almost black
        nameField.setForeground(terminalGreen); // Retro terminal text
        nameField.setCaretColor(terminalGreen);
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setMaximumSize(new Dimension(300, 45)); // Prevent stretching
        nameField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(arcadeGold, 2),
                new EmptyBorder(5, 10, 5, 10)
        ));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- AGE INPUT ---
        JLabel ageLabel = new JLabel("ENTER AGE:");
        ageLabel.setFont(labelFont);
        ageLabel.setForeground(Color.WHITE);
        ageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ageField = new JTextField(15);
        ageField.setFont(inputFont);
        ageField.setBackground(new Color(10, 10, 15));
        ageField.setForeground(terminalGreen);
        ageField.setCaretColor(terminalGreen);
        ageField.setHorizontalAlignment(JTextField.CENTER);
        ageField.setMaximumSize(new Dimension(300, 45));
        ageField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(arcadeGold, 2),
                new EmptyBorder(5, 10, 5, 10)
        ));
        ageField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- CONFIRM BUTTON ---
        confirmButton = new JButton("CONFIRM");
        confirmButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        confirmButton.setBackground(new Color(220, 20, 60)); // Crimson Red
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        confirmButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 100, 100), 2),
                new EmptyBorder(12, 30, 12, 30)
        ));
        confirmButton.addActionListener(this);

        // 4. Assemble the Form (Using rigid areas for spacing)
        formPanel.add(title);
        formPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Gap
        formPanel.add(nameLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(nameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        formPanel.add(ageLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(ageField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        formPanel.add(confirmButton);

        mainPanel.add(formPanel); // Add the centered form to the main background
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

        System.out.println("DEBUG: Registration complete. Transitioning to GameModeMenu...");

        // Ensure GameModeMenu is built and ready in your project!
        new GameModeMenu(name).setVisible(true);
        dispose();
    }

    // Main method tailored to test this screen in isolation
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("DEBUG: Testing HappyMealGame Registration Screen...");
            new HappyMealGame().setVisible(true);
        });
    }
}