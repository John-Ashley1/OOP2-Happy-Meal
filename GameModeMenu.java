import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class GameModeMenu extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JButton pvp, ai, arcade, backButton;
    private String playerName;

    public GameModeMenu(String name) {
        this.playerName = name;

        // 1. Window Setup
        setTitle("Happy Meal Tournament - Select Game Mode");
        setSize(800, 600); // Standardized to match previous screens
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 2. Custom Background Panel (GridBagLayout centers its contents)
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

        // 3. The Menu Panel (Vertical stacking)
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false); // Transparent so gradient shows

        // --- TITLE ---
        JLabel title = new JLabel("CHOOSE GAME MODE");
        title.setFont(new Font("Monospaced", Font.BOLD, 36));
        title.setForeground(new Color(255, 215, 0)); // Arcade Gold
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- BUTTONS ---
        pvp = new JButton("PLAYER VS PLAYER");
        ai = new JButton("PLAYER VS AI");
        arcade = new JButton("ARCADE MODE");
        backButton = new JButton("BACK");

        // Style the main game modes with Crimson Red
        Color actionColor = new Color(220, 20, 60);
        styleButton(pvp, actionColor);
        styleButton(ai, actionColor);
        styleButton(arcade, actionColor);

        // Style the Back button differently so it doesn't distract from the main modes
        styleButton(backButton, new Color(70, 70, 80));

        // Add Action Listeners
        pvp.addActionListener(this);
        ai.addActionListener(this);
        arcade.addActionListener(this);

        // Custom inline listener for the Back Button
        backButton.addActionListener(e -> {
            System.out.println("DEBUG: Returning to Registration Screen...");
            new HappyMealGame().setVisible(true);
            dispose();
        });

        // 4. Assemble the Menu with rigid spacing
        menuPanel.add(title);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 50))); // Large gap below title
        menuPanel.add(pvp);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Gap between buttons
        menuPanel.add(ai);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        menuPanel.add(arcade);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 60))); // Larger gap before Back button
        menuPanel.add(backButton);

        mainPanel.add(menuPanel);
    }

    // Helper method to keep our button styling DRY (Don't Repeat Yourself)
    private void styleButton(JButton btn, Color bgColor) {
        btn.setFont(new Font("Monospaced", Font.BOLD, 20));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Force all buttons to be the same size so the menu looks clean
        Dimension buttonSize = new Dimension(350, 50);
        btn.setPreferredSize(buttonSize);
        btn.setMaximumSize(buttonSize);
        btn.setMinimumSize(buttonSize);

        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.brighter(), 2),
                new EmptyBorder(10, 20, 10, 20)
        ));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedMode = "";
        String difficulty = "Medium";

        if (e.getSource() == pvp) {
            selectedMode = "PvP";
            System.out.println("DEBUG: Selected Mode - PvP");

        } else if (e.getSource() == ai) {
            selectedMode = "PvAI";
            System.out.println("DEBUG: Selected Mode - PvAI. Prompting for difficulty...");

            String[] options = {"Easy", "Medium", "Hard"};
            // Note: This dialog uses the standard OS theme. We can custom-theme this later if desired!
            difficulty = (String) JOptionPane.showInputDialog(this,
                    "Select AI Difficulty:",
                    "Difficulty",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    "Medium");

            if (difficulty == null) {
                System.out.println("DEBUG: Difficulty selection cancelled. Defaulting to Medium.");
                difficulty = "Medium";
            }

        } else if (e.getSource() == arcade) {
            selectedMode = "Arcade";
            difficulty = "Medium";
            System.out.println("DEBUG: Selected Mode - Arcade");
        }

        System.out.println("DEBUG: Transitioning to HeroSelection (Player: " + playerName + ", Mode: " + selectedMode + ", Diff: " + difficulty + ")");

        // Ensure your HeroSelection class is ready to receive these!
        new HeroSelection(playerName, selectedMode, difficulty).setVisible(true);
        dispose();
    }

    // Main method for testing this screen in isolation (using a dummy name)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("DEBUG: Testing GameModeMenu in isolation...");
            new GameModeMenu("TestPlayer").setVisible(true);
        });
    }
}