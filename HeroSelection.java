import com.ror.gamemodel.Entity;
import com.ror.gamemodel.Playable.*;
import com.ror.gameutil.GuiBattleArena;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class HeroSelection extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JButton mark, ted, den, ashley, vince, zack, clent, trone, backButton;
    private JLabel instructionLabel;

    private String playerName;
    private String mode;
    private String difficulty;
    private ArrayList<String> availableHeroes;
    private String player1Hero = null;
    private String player2Hero = null;

    public HeroSelection(String playerName, String mode, String difficulty) {
        this.playerName = playerName;
        this.mode = mode;
        this.difficulty = difficulty;

        // 1. Window Setup
        setTitle("Happy Meal Tournament - Select Your Hero");
        setSize(800, 750); // Slightly taller to fit the images comfortably
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Populate available heroes list
        String[] heroes = {"Happy Mark", "Happy Ted", "Happy Den", "Happy Ashley",
                "Happy Vince", "Happy Zack", "Happy Clent", "Happy Trone"};
        availableHeroes = new ArrayList<>();
        for(String h : heroes) availableHeroes.add(h);

        // 2. Custom Background Panel
        mainPanel = new JPanel(new BorderLayout(10, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(15, 15, 25), 0, getHeight(), new Color(45, 10, 50));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(mainPanel);

        // --- TOP: Title & Instructions ---
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setOpaque(false);

        JLabel title = new JLabel("CHOOSE YOUR FIGHTER", JLabel.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 32));
        title.setForeground(new Color(255, 215, 0)); // Arcade Gold

        instructionLabel = new JLabel(playerName.toUpperCase() + ", SELECT YOUR HERO!", JLabel.CENTER);
        instructionLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        instructionLabel.setForeground(Color.WHITE);

        topPanel.add(title);
        topPanel.add(instructionLabel);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // --- CENTER: Hero Grid ---
        JPanel heroPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        heroPanel.setOpaque(false);

        mark = createHeroButton("Happy Mark", "images/mark.jpg");
        ted = createHeroButton("Happy Ted", "images/ted.jpg");
        den = createHeroButton("Happy Den", "images/den.jpg");
        ashley = createHeroButton("Happy Ashley", "images/ashley.jpg");
        vince = createHeroButton("Happy Vince", "images/vince.jpg");
        zack = createHeroButton("Happy Zack", "images/zack.jpg");
        clent = createHeroButton("Happy Clent", "images/clent.jpg");
        trone = createHeroButton("Happy Trone", "images/trone.jpg");

        JButton[] heroButtons = {mark, ted, den, ashley, vince, zack, clent, trone};
        for(JButton b : heroButtons) {
            heroPanel.add(b);
        }
        mainPanel.add(heroPanel, BorderLayout.CENTER);

        // --- BOTTOM: Back Button ---
        JPanel backPanel = new JPanel();
        backPanel.setOpaque(false);

        backButton = new JButton("BACK TO MENU");
        backButton.setFont(new Font("Monospaced", Font.BOLD, 18));
        backButton.setBackground(new Color(70, 70, 80));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY, 2),
                new EmptyBorder(10, 20, 10, 20)
        ));

        backButton.addActionListener(e -> {
            new GameModeMenu(playerName).setVisible(true);
            dispose();
        });

        backPanel.add(backButton);
        mainPanel.add(backPanel, BorderLayout.SOUTH);
    }

    // Helper method to style the character portraits
    private JButton createHeroButton(String heroName, String imagePath) {
        JButton btn = new JButton(heroName);
        btn.setFont(new Font("Monospaced", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(Color.BLACK);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new LineBorder(new Color(220, 20, 60), 2)); // Crimson border

        try {
            ImageIcon icon = new ImageIcon(imagePath);
            // Scale image nicely to fit the grid
            Image scaledImage = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(scaledImage));
        } catch (Exception ex) {
            System.out.println("DEBUG: Could not load image " + imagePath);
        }

        btn.addActionListener(this);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) return;

        JButton selected = (JButton)e.getSource();
        String chosenHero = selected.getText();

        switch(mode) {
            case "PvP": handlePvP(chosenHero); break;
            case "PvAI": handlePvAI(chosenHero); break;
            case "Arcade": handleArcade(chosenHero); break;
        }
    }

    private void handlePvP(String chosenHero) {
        if(player1Hero == null) {
            player1Hero = chosenHero;
            availableHeroes.remove(chosenHero);

            // Update UI to prompt Player 2
            instructionLabel.setText("PLAYER 2, SELECT YOUR HERO!");
            instructionLabel.setForeground(new Color(50, 255, 50)); // Green to indicate shift
            refreshButtons();

        } else {
            player2Hero = chosenHero;
            launchBattleArena();
        }
    }

    private void handlePvAI(String chosenHero) {
        player1Hero = chosenHero;
        availableHeroes.remove(chosenHero);

        Random rand = new Random();
        player2Hero = availableHeroes.get(rand.nextInt(availableHeroes.size()));

        JOptionPane.showMessageDialog(this, "You selected: " + player1Hero + "\nAI selected: " + player2Hero, "Match Found", JOptionPane.INFORMATION_MESSAGE);
        launchBattleArena();
    }

    private void handleArcade(String chosenHero) {
        player1Hero = chosenHero;
        availableHeroes.remove(chosenHero);

        Random rand = new Random();
        player2Hero = availableHeroes.get(rand.nextInt(availableHeroes.size()));

        JOptionPane.showMessageDialog(this, "You selected: " + player1Hero + "\nEnemy selected: " + player2Hero, "Arcade Challenger", JOptionPane.WARNING_MESSAGE);
        launchBattleArena();
    }

    // --- THE ENGINE HOOK ---
    // This converts the String names into our actual data objects and starts the game
    private void launchBattleArena() {
        Entity p1 = createHeroEntity(player1Hero);
        Entity p2 = createHeroEntity(player2Hero);

        System.out.println("DEBUG: Launching Arena! " + p1.getName() + " VS " + p2.getName());

        new GuiBattleArena(p1, p2).setVisible(true);
        dispose();
    }

    // Factory method to generate specific characters
    private Entity createHeroEntity(String heroName) {
        switch(heroName) {
            case "Happy Mark": return new Mark();
            case "Happy Ted": return new Ted();
            case "Happy Den": return new Den();
            case "Happy Ashley": return new Ashley();
            case "Happy Vince": return new Vince();
            case "Happy Zack": return new Zack();
            case "Happy Clent": return new Clent();
            case "Happy Trone": return new Trone();
            default: return new Mark(); // Fallback
        }
    }

    private void refreshButtons() {
        // Disables the button of the hero that was already picked
        JButton[] allButtons = {mark, ted, den, ashley, vince, zack, clent, trone};
        for (JButton btn : allButtons) {
            if (!availableHeroes.contains(btn.getText())) {
                btn.setEnabled(false);
                btn.setBorder(new LineBorder(Color.DARK_GRAY, 2));
            }
        }
    }
}