import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameModeMenu extends JFrame implements ActionListener {

    JButton pvp, ai, arcade, backButton;
    String playerName;

    public GameModeMenu(String name) {

        playerName = name;

        setTitle("Select Game Mode");
        setSize(900,600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(20,20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel title = new JLabel("Choose Game Mode", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3,1,10,10));

        pvp = new JButton("Player vs Player");
        ai = new JButton("Player vs AI");
        arcade = new JButton("Arcade Mode");

        JButton[] buttons = {pvp, ai, arcade};
        for(JButton b : buttons) {
            b.setFont(new Font("Arial", Font.BOLD, 24));
            b.addActionListener(this);
            buttonPanel.add(b);
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBackground(Color.LIGHT_GRAY);
        backButton.addActionListener(e -> {
            new HappyMealGame(); 
            dispose();
        });

        JPanel backPanel = new JPanel();
        backPanel.add(backButton);
        mainPanel.add(backPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String selectedMode = "";
        String difficulty = "Medium"; 

        if(e.getSource() == pvp) {
            selectedMode = "PvP";
        } else if(e.getSource() == ai) {
            selectedMode = "PvAI";

            String[] options = {"Easy", "Medium", "Hard"};
            difficulty = (String) JOptionPane.showInputDialog(this,
                    "Select AI Difficulty:",
                    "Difficulty",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    "Medium");
            if(difficulty == null) difficulty = "Medium"; 
        } else if(e.getSource() == arcade) {
            selectedMode = "Arcade";
            difficulty = "Medium"; 
        }

        new HeroSelection(playerName, selectedMode, difficulty);
        dispose();
    }
}