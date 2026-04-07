import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class PlayerVsPVP extends JFrame implements ActionListener {
    private String player1Name;
    private String player2Name;
    private boolean isPlayer1Selecting = true;
    private String player1HeroName = null;

    private static final String[] HERO_NAMES = {
        "Happy Mark", "Happy Ted", "Happy Den", "Happy Ashley",
        "Happy Vince", "Happy Zack", "Happy Clent", "Happy Trone"
    };
    private static final String[] HERO_FILES = {
        "HappyMark.png", "HappyTed.png", "HappyDen.png", "HappyAshley.png",
        "HappyVince.png", "HappyZack.png", "HappyClent.png", "HappyTrone.png"
    };

    public PlayerVsPVP(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;

        setTitle("Player vs Player — Hero Selection");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        updateSelectionScreen();
        setVisible(true);
    }

    private void updateSelectionScreen() {
        getContentPane().removeAll();

        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(new Color(30, 30, 30));

        String currentPlayer = isPlayer1Selecting ? player1Name : player2Name;
        String titleText = isPlayer1Selecting ? 
            "🤜 PLAYER 1 — SELECT YOUR HERO" : 
            "🤛 PLAYER 2 — SELECT YOUR HERO";

        JLabel title = new JLabel(titleText, JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(new Color(100, 220, 255));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        JLabel sub = new JLabel(currentPlayer + " — Choose your fighter.", JLabel.CENTER);
        sub.setFont(new Font("Serif", Font.ITALIC, 18));
        sub.setForeground(new Color(140, 160, 180));
        sub.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBackground(new Color(30, 30, 30));
        topSection.add(title, BorderLayout.NORTH);
        topSection.add(sub, BorderLayout.SOUTH);

        // Hero grid
        JPanel grid = new JPanel(new GridLayout(2, 4, 30, 30));
        grid.setBackground(new Color(30, 30, 30));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 60, 60, 60));

        for (int i = 0; i < HERO_NAMES.length; i++) {
            JButton btn = heroBtn(HERO_FILES[i], HERO_NAMES[i]);
            // Disable already chosen hero for Player 2
            if (!isPlayer1Selecting && HERO_NAMES[i].equals(player1HeroName)) {
                btn.setEnabled(false);
                btn.setBackground(new Color(40, 40, 40));
            }
            grid.add(btn);
        }

        outer.add(topSection, BorderLayout.NORTH);
        outer.add(grid, BorderLayout.CENTER);

        setContentPane(outer);
        revalidate();
        repaint();
    }

    private JButton heroBtn(String file, String name) {
        ImageIcon icon = null;
        java.net.URL u = getClass().getResource("/images/" + file);
        if (u != null) {
            icon = new ImageIcon(new ImageIcon(u).getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH));
        }

        JButton b = new JButton(name, icon);
        b.setHorizontalTextPosition(JButton.CENTER);
        b.setVerticalTextPosition(JButton.BOTTOM);
        b.setHorizontalAlignment(JButton.CENTER);
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(60, 60, 60));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        b.setActionCommand(name);
        b.addActionListener(this);

        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { 
                if (b.isEnabled()) b.setBorder(BorderFactory.createLineBorder(new Color(100, 220, 255), 3)); 
            }
            public void mouseExited(MouseEvent e) { 
                if (b.isEnabled()) b.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); 
            }
        });

        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String chosen = e.getActionCommand();

        if (isPlayer1Selecting) {
            player1HeroName = chosen;
            isPlayer1Selecting = false;
            updateSelectionScreen();   // Switch to Player 2 selection
        } else {
            // Both heroes selected
            Hero player1Hero = HeroFactory.create(player1HeroName);
            Hero player2Hero = HeroFactory.create(chosen);

            new BattleScreen(player1Name, player1Hero, player2Name, player2Hero);
            dispose();
        }
    }
}
