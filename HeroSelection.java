import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class HeroSelection extends JFrame implements ActionListener {

    JButton mark, ted, den, ashley, vince, zack, clent, trone, backButton;
    String playerName;
    String mode; 
    String difficulty; 
    ArrayList<String> availableHeroes;
    String player1Hero = null;
    String player2Hero = null;

    public HeroSelection(String playerName, String mode, String difficulty) {
        this.playerName = playerName;
        this.mode = mode;
        this.difficulty = difficulty;

        setTitle("Select Your Hero");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] heroes = {"Happy Mark", "Happy Ted", "Happy Den", "Happy Ashley",
                           "Happy Vince", "Happy Zack", "Happy Clent", "Happy Trone"};
        availableHeroes = new ArrayList<>();
        for(String h : heroes) availableHeroes.add(h);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel heroPanel = new JPanel(new GridLayout(4, 2, 15, 15));

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

        backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBackground(Color.LIGHT_GRAY);
        backButton.addActionListener(e -> {
            new GameModeMenu(playerName); 
            dispose(); 
        });

        JPanel backPanel = new JPanel();
        backPanel.add(backButton);
        mainPanel.add(backPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createHeroButton(String heroName, String imagePath) {
        JButton btn = new JButton(heroName);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);

        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(scaledImage));

        btn.addActionListener(this);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton selected = (JButton)e.getSource();
        String chosenHero = selected.getText();

        switch(mode) {
            case "PvP":
                handlePvP(chosenHero);
                break;
            case "PvAI":
                handlePvAI(chosenHero);
                break;
            case "Arcade":
                handleArcade(chosenHero);
                break;
        }
    }

    private void handlePvP(String chosenHero) {
        if(player1Hero == null) {
            player1Hero = chosenHero;
            availableHeroes.remove(chosenHero);
            JOptionPane.showMessageDialog(this, "Player 1 selected: " + chosenHero + "\nPlayer 2, choose your hero.");
            refreshButtons();
        } else {
            player2Hero = chosenHero;
            JOptionPane.showMessageDialog(this, "Player 2 selected: " + chosenHero + "\nBattle begins!");
            dispose();
        }
    }

    private void handlePvAI(String chosenHero) {
        player1Hero = chosenHero;
        availableHeroes.remove(chosenHero);

        Random rand = new Random();
        player2Hero = availableHeroes.get(rand.nextInt(availableHeroes.size()));

        JOptionPane.showMessageDialog(this, "You selected: " + player1Hero + "\nAI selected: " + player2Hero +
                "\nBattle begins on " + difficulty + " mode!");
        dispose();
    }

    private void handleArcade(String chosenHero) {
        player1Hero = chosenHero;
        availableHeroes.remove(chosenHero);

        Random rand = new Random();
        player2Hero = availableHeroes.get(rand.nextInt(availableHeroes.size()));

        JOptionPane.showMessageDialog(this, "You selected: " + player1Hero + "\nEnemy selected: " + player2Hero +
                "\nPrepare for the Arcade battle!");
        dispose();
    }

    private void refreshButtons() {
        for(Component comp : ((JPanel)getContentPane().getComponent(0)).getComponents()) {
            if(comp instanceof JPanel) {
                for(Component btn : ((JPanel)comp).getComponents()) {
                    if(btn instanceof JButton) {
                        JButton b = (JButton)btn;
                        if(!availableHeroes.contains(b.getText()) && b != backButton) {
                            b.setEnabled(false);
                        }
                    }
                }
            }
        }
    }
}