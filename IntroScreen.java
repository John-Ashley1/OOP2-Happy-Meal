import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IntroScreen extends JFrame implements ActionListener {

    JButton startButton;

    public IntroScreen() {

        setTitle("Happy Meal");
        setSize(900,600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JTextArea story = new JTextArea();
        story.setText(
            "GAME STORY\n\n" +
            "In the city of Joy Arena, warriors from different backgrounds gather " +
            "to compete in the legendary Happy Meal Tournament.\n\n" +
            "This is not a war. It is a battle of skill, strength, and strategy. " +
            "Fighters challenge each other in 1v1 turn-based duels, proving who " +
            "is the strongest hero in the arena.\n\n" +
            "Every fighter carries their own story, training, and unique abilities. " +
            "Some fight to prove their strength, others to protect their honor, " +
            "and some simply enjoy the thrill of competition."
        );

        story.setEditable(false);
        story.setFont(new Font("Arial", Font.PLAIN, 18)); 
        story.setLineWrap(true);
        story.setWrapStyleWord(true);
        story.setMargin(new Insets(15,15,15,15));
        story.setBackground(new Color(245,245,245));

        JScrollPane scrollPane = new JScrollPane(story);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        startButton = new JButton("START GAME");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setBackground(new Color(255,165,0));
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        startButton.setPreferredSize(new Dimension(200, 60));
        startButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new HappyMealGame(); 
        dispose(); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new IntroScreen());
    }
}