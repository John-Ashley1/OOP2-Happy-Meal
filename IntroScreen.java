import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntroScreen extends JFrame {
    private JPanel mainPanel;
    private JTextArea story;
    private JScrollPane scrollPane;
    private JButton startButton;

    public IntroScreen() {
        // 1. Window Setup
        setTitle("Happy Meal Tournament - Joy Arena");
        setSize(800, 600); // Expanded the resolution for a better cinematic feel
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 2. Custom Background Panel
        mainPanel = new JPanel(new BorderLayout(20, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Default dark arcade gradient (Midnight Blue to Deep Purple)
                GradientPaint gp = new GradientPaint(0, 0, new Color(15, 15, 25), 0, getHeight(), new Color(45, 10, 50));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // TODO: When you have a background image asset, uncomment these lines:
                // Image bgImage = new ImageIcon("images/arena_background.jpg").getImage();
                // g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        // Add padding around the edges of the window
        mainPanel.setBorder(new EmptyBorder(50, 60, 50, 60));
        setContentPane(mainPanel);

        // 3. Story Text Area Setup
        story = new JTextArea();
        story.setText("GAME STORY\n\n" +
                "In the city of Joy Arena, warriors from different backgrounds gather to compete in the legendary Happy Meal Tournament.\n\n" +
                "This is not a war. It is a battle of skill, strength, and strategy. Fighters challenge each other in 1v1 turn-based duels, proving who is the strongest hero in the arena.\n\n" +
                "Every fighter carries their own story, training, and unique abilities. Some fight to prove their strength, others to protect their honor, and some simply enjoy the thrill of competition.");

        story.setEditable(false);
        story.setLineWrap(true);
        story.setWrapStyleWord(true);
        story.setOpaque(false); // Makes the text area background transparent
        story.setForeground(new Color(255, 215, 0)); // Arcade Gold text color
        story.setFont(new Font("Monospaced", Font.BOLD, 18)); // Retro terminal feel

        // 4. Scroll Pane Setup
        scrollPane = new JScrollPane(story);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); // Critical for transparency
        scrollPane.setBorder(null); // Removes the default 3D border

        // 5. Start Button Setup
        startButton = new JButton("ENTER THE ARENA");
        startButton.setFont(new Font("Monospaced", Font.BOLD, 22));
        startButton.setBackground(new Color(220, 20, 60)); // Crimson Red
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false); // Removes the ugly dotted line when clicked
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Custom padding inside the button
        startButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 100, 100), 2),
                new EmptyBorder(15, 40, 15, 40)
        ));

        // 6. Action Listener to move to the next screen
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("DEBUG: Button clicked! Transitioning to HappyMealGame...");
                // Initialize the next node in our UI pipeline
                new HappyMealGame().setVisible(true);
                dispose(); // Destroy this window to free up memory
            }
        });

        // 7. Assembly
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Keep background visible
        buttonPanel.add(startButton);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // --- UPDATED MAIN METHOD WITH DEBUG LOGS ---
    public static void main(String[] args) {
        System.out.println("DEBUG: Booting up the Joy Arena...");

        SwingUtilities.invokeLater(() -> {
            System.out.println("DEBUG: Spawning the Intro Screen window...");
            new IntroScreen().setVisible(true);
        });
    }
}