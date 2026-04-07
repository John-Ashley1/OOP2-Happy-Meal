import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

/**
 * Player vs AI Mode
 * Player selects a hero, then fights a randomly chosen CPU opponent.
 */
public class PlayerVsAI extends JFrame implements ActionListener {

    private String playerName;

    private static final String[] HERO_NAMES = {
        "Happy Mark", "Happy Ted", "Happy Den", "Happy Ashley",
        "Happy Vince", "Happy Zack", "Happy Clent", "Happy Trone"
    };
    private static final String[] HERO_FILES = {
        "HappyMark.png", "HappyTed.png", "HappyDen.png", "HappyAshley.png",
        "HappyVince.png", "HappyZack.png", "HappyClent.png", "HappyTrone.png"
    };

    public PlayerVsAI(String playerName) {
        this.playerName = playerName;

        setTitle("Player vs AI — Select Your Hero");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(new Color(30, 30, 30));

        // Title
        JLabel title = new JLabel("🤖  PLAYER VS AI  —  SELECT YOUR HERO", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(new Color(100, 220, 255));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        outer.add(title, BorderLayout.NORTH);

        // Subtitle
        JLabel sub = new JLabel("Choose your fighter. The AI will pick its champion.", JLabel.CENTER);
        sub.setFont(new Font("Serif", Font.ITALIC, 16));
        sub.setForeground(new Color(140, 160, 180));
        sub.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBackground(new Color(30, 30, 30));
        topSection.add(title, BorderLayout.NORTH);
        topSection.add(sub, BorderLayout.SOUTH);
        outer.add(topSection, BorderLayout.NORTH);

        // Hero grid
        JPanel grid = new JPanel(new GridLayout(2, 4, 30, 30));
        grid.setBackground(new Color(30, 30, 30));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 60, 60, 60));

        for (int i = 0; i < HERO_NAMES.length; i++) {
            grid.add(heroBtn(HERO_FILES[i], HERO_NAMES[i]));
        }

        outer.add(grid, BorderLayout.CENTER);
        setContentPane(outer);
        setVisible(true);
    }

    private JButton heroBtn(String file, String name) {
        ImageIcon icon = null;
        java.net.URL u = getClass().getResource("/images/" + file);
        if (u != null)
            icon = new ImageIcon(new ImageIcon(u).getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH));

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
            public void mouseEntered(MouseEvent e) { b.setBorder(BorderFactory.createLineBorder(new Color(100, 220, 255), 3)); }
            public void mouseExited(MouseEvent e)  { b.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); }
        });
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String chosen = e.getActionCommand();
        Hero player = HeroFactory.create(chosen);

        // Pick a random CPU opponent (different from player's choice)
        Random rand = new Random();
        String opp = HERO_NAMES[rand.nextInt(HERO_NAMES.length)];
        while (opp.equals(chosen))
            opp = HERO_NAMES[rand.nextInt(HERO_NAMES.length)];

        Hero cpu = HeroFactory.create(opp);
        new BattleScreen(playerName, player, "CPU — " + opp, cpu);
        dispose();
    }
}
