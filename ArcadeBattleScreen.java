import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.Random;

/**
 * ArcadeBattleScreen
 * Handles one round of Arcade Mode. On win, advances to the next round.
 * Player HP/Mana are partially restored (+30%) between rounds.
 */
public class ArcadeBattleScreen extends JFrame {

    private Hero player, enemy;
    private String playerName, enemyName;
    private List<String> opponents;
    private int currentRound;
    private int totalRounds;
    private boolean playerTurn = true;
    private Random rand = new Random();

    // Player UI
    private JLabel       p1Portrait;
    private JProgressBar p1HPBar, p1MPBar;
    private JLabel       p1HPLbl, p1MPLbl;
    private JButton[]    p1Skills = new JButton[3];

    // Enemy UI
    private JLabel       p2Portrait;
    private JProgressBar p2HPBar, p2MPBar;
    private JLabel       p2HPLbl, p2MPLbl;
    private JButton[]    p2Skills = new JButton[3];

    private JTextArea battleLog;
    private JLabel roundLabel;
    private int p1MaxHP, p1MaxMP, p2MaxHP, p2MaxMP;

    public ArcadeBattleScreen(String playerName, Hero player, List<String> opponents, int roundIndex) {
        this.playerName   = playerName;
        this.player       = player;
        this.opponents    = opponents;
        this.currentRound = roundIndex;
        this.totalRounds  = opponents.size();

        String oppName = opponents.get(roundIndex);
        this.enemy     = HeroFactory.create(oppName);
        this.enemyName = "CPU — " + oppName;

        this.p1MaxHP = player.getHP();
        this.p1MaxMP = Math.max(player.getMana(), 1);
        this.p2MaxHP = enemy.getHP();
        this.p2MaxMP = Math.max(enemy.getMana(), 1);

        setTitle("🏆 Arcade Mode — Round " + (roundIndex + 1) + " of " + totalRounds);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(new Color(15, 15, 15));

        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(15, 15, 15));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 16, 6, 16));

        roundLabel = new JLabel(
            "🏆  ARCADE MODE  —  ROUND " + (roundIndex + 1) + " / " + totalRounds + "  ⚔",
            JLabel.CENTER);
        roundLabel.setFont(new Font("Serif", Font.BOLD, 26));
        roundLabel.setForeground(new Color(255, 200, 50));
        topBar.add(roundLabel, BorderLayout.CENTER);

        JButton exitBtn = new JButton("✖  Forfeit & Exit");
        exitBtn.setFont(new Font("Arial", Font.BOLD, 13));
        exitBtn.setForeground(new Color(255, 100, 100));
        exitBtn.setBackground(new Color(40, 20, 20));
        exitBtn.setFocusPainted(false);
        exitBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 50, 50), 1),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)));
        exitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exitBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { exitBtn.setBackground(new Color(80, 20, 20)); }
            public void mouseExited(MouseEvent e)  { exitBtn.setBackground(new Color(40, 20, 20)); }
        });
        exitBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Forfeit Arcade Mode and return to Game Mode menu?",
                "Forfeit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                new GameModeMenu(playerName);
                dispose();
            }
        });
        topBar.add(exitBtn, BorderLayout.EAST);
        root.add(topBar, BorderLayout.NORTH);

        // Arena
        JPanel arena = new JPanel(new GridLayout(1, 2, 20, 0));
        arena.setBackground(new Color(15, 15, 15));
        arena.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        arena.add(buildFighterPanel(player, playerName, true));
        arena.add(buildFighterPanel(enemy,  enemyName,  false));
        root.add(arena, BorderLayout.CENTER);

        // Battle log
        battleLog = new JTextArea(6, 0);
        battleLog.setEditable(false);
        battleLog.setBackground(new Color(10, 10, 10));
        battleLog.setForeground(new Color(200, 200, 200));
        battleLog.setFont(new Font("Monospaced", Font.PLAIN, 13));
        battleLog.setMargin(new Insets(8, 15, 8, 15));
        battleLog.setLineWrap(true);
        battleLog.setWrapStyleWord(true);

        JScrollPane logScroll = new JScrollPane(battleLog);
        logScroll.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(60, 60, 60)));
        logScroll.getViewport().setBackground(new Color(10, 10, 10));
        logScroll.setPreferredSize(new Dimension(0, 140));
        root.add(logScroll, BorderLayout.SOUTH);

        add(root);
        setVisible(true);

        log("🏆 ARCADE MODE — ROUND " + (roundIndex + 1) + " of " + totalRounds);
        log(playerName + " (" + player.getName() + ")  VS  " + enemyName);
        log("─────────────────────────────────────");
        log("It's " + playerName + "'s turn — choose a skill!");
    }

    private JPanel buildFighterPanel(Hero hero, String label, boolean isP1) {
        JPanel col = new JPanel(new BorderLayout(0, 8));
        col.setBackground(new Color(15, 15, 15));
        col.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        int portraitSize = 320;
        ImageIcon icon = Util.loadHeroIcon(hero.getName(), portraitSize);
        JLabel portrait = new JLabel(icon, JLabel.CENTER);
        portrait.setPreferredSize(new Dimension(portraitSize + 20, portraitSize + 20));
        portrait.setBorder(BorderFactory.createLineBorder(
            isP1 ? new Color(255, 200, 50) : new Color(200, 80, 80), 3));
        portrait.setBackground(new Color(30, 30, 30));
        portrait.setOpaque(true);
        if (isP1) p1Portrait = portrait; else p2Portrait = portrait;

        JLabel nameLabel = new JLabel(label + " — " + hero.getName(), JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 17));
        nameLabel.setForeground(isP1 ? new Color(255, 200, 50) : new Color(255, 120, 120));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

        JPanel skillRow = new JPanel(new GridLayout(1, 3, 8, 0));
        skillRow.setBackground(new Color(15, 15, 15));
        String[] skillList = hero.getSkillList();
        JButton[] btns = isP1 ? p1Skills : p2Skills;
        Color borderHover = isP1 ? new Color(255, 200, 50) : new Color(200, 80, 80);

        for (int i = 0; i < 3; i++) {
            final int idx = i;
            String skillLabel = "<html><center><b>" + shortSkillName(skillList[i]) + "</b><br>"
                + "<small>" + skillCost(skillList[i]) + "</small></center></html>";
            btns[i] = new JButton(skillLabel);
            btns[i].setFont(new Font("Arial", Font.PLAIN, 12));
            btns[i].setForeground(isP1 ? new Color(255, 200, 50) : new Color(255, 120, 120));
            btns[i].setBackground(new Color(35, 35, 35));
            btns[i].setFocusPainted(false);
            btns[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
                BorderFactory.createEmptyBorder(8, 4, 8, 4)));
            btns[i].setEnabled(isP1);
            btns[i].addActionListener(e -> playerAttack(idx));
            btns[i].addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    if (btns[idx].isEnabled())
                        btns[idx].setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(borderHover, 2),
                            BorderFactory.createEmptyBorder(8, 4, 8, 4)));
                }
                public void mouseExited(MouseEvent e) {
                    btns[idx].setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
                        BorderFactory.createEmptyBorder(8, 4, 8, 4)));
                }
            });
            skillRow.add(btns[i]);
        }

        JProgressBar hpBar = bar(hero.getHP(), hero.getHP(), new Color(80, 200, 80));
        JProgressBar mpBar = bar(hero.getMana(), Math.max(hero.getMana(), 1), new Color(80, 120, 220));
        JLabel hpLbl = statLabel("HP: " + hero.getHP() + " / " + hero.getHP(), new Color(180, 255, 180));
        JLabel mpLbl = statLabel("MP: " + hero.getMana() + " / " + Math.max(hero.getMana(), 1), new Color(150, 180, 255));

        if (isP1) { p1HPBar = hpBar; p1MPBar = mpBar; p1HPLbl = hpLbl; p1MPLbl = mpLbl; }
        else      { p2HPBar = hpBar; p2MPBar = mpBar; p2HPLbl = hpLbl; p2MPLbl = mpLbl; }

        JPanel bars = new JPanel();
        bars.setLayout(new BoxLayout(bars, BoxLayout.Y_AXIS));
        bars.setBackground(new Color(15, 15, 15));
        hpBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 16));
        mpBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 16));
        bars.add(hpLbl); bars.add(hpBar);
        bars.add(Box.createVerticalStrut(4));
        bars.add(mpLbl); bars.add(mpBar);

        JPanel bottom = new JPanel(new BorderLayout(0, 8));
        bottom.setBackground(new Color(15, 15, 15));
        bottom.add(skillRow, BorderLayout.NORTH);
        bottom.add(bars, BorderLayout.CENTER);

        col.add(nameLabel, BorderLayout.NORTH);
        col.add(portrait,  BorderLayout.CENTER);
        col.add(bottom,    BorderLayout.SOUTH);
        return col;
    }

    private void playerAttack(int idx) {
        if (!playerTurn) return;
        setSkills(false);
        int dmg = player.useSkill(idx);
        if (dmg == -1) { log("❌ Not enough mana/HP!"); setSkills(true); return; }
        if (dmg == -2) { log("⏳ Skill on cooldown!");  setSkills(true); return; }

        enemy.setHP(enemy.getHP() - dmg);
        log("▶ " + playerName + " uses " + shortSkillName(player.getSkillList()[idx]) + " → " + dmg + " damage!");
        updateBars();
        flashPortrait(p2Portrait, new Color(255, 80, 80));

        if (enemy.getHP() <= 0) { enemy.setHP(0); updateBars(); roundWon(); return; }
        player.reduceCooldowns();
        playerTurn = false;

        Timer t = new Timer(900, e -> enemyAI());
        t.setRepeats(false); t.start();
    }

    private void enemyAI() {
        int dmg = -1, usedSkill = 0;
        for (int i = 2; i >= 0; i--) {
            dmg = enemy.useSkill(i);
            if (dmg >= 0) { usedSkill = i; break; }
        }
        if (dmg < 0) dmg = 0;

        player.setHP(player.getHP() - dmg);
        log("◀ " + enemyName + " uses " + shortSkillName(enemy.getSkillList()[usedSkill]) + " → " + dmg + " damage!");
        updateBars();
        flashPortrait(p1Portrait, new Color(255, 80, 80));

        if (player.getHP() <= 0) { player.setHP(0); updateBars(); arcadeOver(false); return; }
        enemy.reduceCooldowns();
        playerTurn = true;
        refreshSkillLabels();
        log("─────────────────────────────────────");
        log("It's " + playerName + "'s turn — choose a skill!");
        setSkills(true);
    }

    private void roundWon() {
        setSkills(false);
        log("─────────────────────────────────────");
        log("✅ Round " + (currentRound + 1) + " cleared!");

        boolean isFinalRound = (currentRound + 1) >= totalRounds;

        Timer t = new Timer(1500, e -> {
            if (isFinalRound) {
                arcadeOver(true);
            } else {
                // Restore 30% HP and MP before next round
                int hpRestore  = (int)(p1MaxHP * 0.30);
                int mpRestore  = (int)(p1MaxMP * 0.30);
                player.setHP(Math.min(player.getHP() + hpRestore, p1MaxHP));
                player.setMana(Math.min(player.getMana() + mpRestore, p1MaxMP));

                JOptionPane.showMessageDialog(this,
                    "Round " + (currentRound + 1) + " cleared!\n\n" +
                    "+ " + hpRestore + " HP restored\n" +
                    "+ " + mpRestore + " MP restored\n\n" +
                    "Prepare for Round " + (currentRound + 2) + "!",
                    "Round Complete!", JOptionPane.INFORMATION_MESSAGE);

                new ArcadeBattleScreen(playerName, player, opponents, currentRound + 1);
                dispose();
            }
        });
        t.setRepeats(false); t.start();
    }

    private void arcadeOver(boolean won) {
        setSkills(false);
        log("─────────────────────────────────────");
        if (won) {
            log("🏆 " + playerName + " is the ARCADE CHAMPION!");
        } else {
            log("💀 " + playerName + " was defeated. Arcade run over.");
        }

        Timer t = new Timer(2000, e -> {
            String msg = won
                ? "🏆 CONGRATULATIONS!\n" + playerName + " cleared all " + totalRounds + " rounds!\nYou are the Arcade Champion!"
                : "💀 You were defeated in Round " + (currentRound + 1) + ".\nBetter luck next time!";
            JOptionPane.showMessageDialog(this, msg, won ? "ARCADE CHAMPION!" : "Game Over",
                won ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            new GameModeMenu(playerName);
            dispose();
        });
        t.setRepeats(false); t.start();
    }

    private void flashPortrait(JLabel portrait, Color flashColor) {
        Color original = portrait.getBorder() instanceof javax.swing.border.LineBorder
            ? ((javax.swing.border.LineBorder) portrait.getBorder()).getLineColor()
            : new Color(80, 80, 80);
        portrait.setBorder(BorderFactory.createLineBorder(flashColor, 5));
        Timer timer = new Timer(300, e -> portrait.setBorder(BorderFactory.createLineBorder(original, 3)));
        timer.setRepeats(false); timer.start();
    }

    private void updateBars() {
        p1HPBar.setValue(Math.max(player.getHP(), 0)); p1MPBar.setValue(Math.max(player.getMana(), 0));
        p1HPLbl.setText("HP: " + Math.max(player.getHP(), 0) + " / " + p1MaxHP);
        p1MPLbl.setText("MP: " + Math.max(player.getMana(), 0) + " / " + p1MaxMP);
        p2HPBar.setValue(Math.max(enemy.getHP(), 0));  p2MPBar.setValue(Math.max(enemy.getMana(), 0));
        p2HPLbl.setText("HP: " + Math.max(enemy.getHP(), 0) + " / " + p2MaxHP);
        p2MPLbl.setText("MP: " + Math.max(enemy.getMana(), 0) + " / " + p2MaxMP);
    }

    private void refreshSkillLabels() {
        String[] s1 = player.getSkillList();
        for (int i = 0; i < 3; i++)
            p1Skills[i].setText("<html><center><b>" + shortSkillName(s1[i]) + "</b><br><small>" + skillCost(s1[i]) + "</small></center></html>");
    }

    private void setSkills(boolean en) { for (JButton b : p1Skills) b.setEnabled(en); }

    private void log(String msg) {
        battleLog.append(msg + "\n");
        battleLog.setCaretPosition(battleLog.getDocument().getLength());
    }

    private static JProgressBar bar(int val, int max, Color c) {
        JProgressBar b = new JProgressBar(0, max); b.setValue(val);
        b.setForeground(c); b.setBackground(new Color(50, 50, 50)); b.setBorderPainted(false); return b;
    }

    private static JLabel statLabel(String text, Color c) {
        JLabel l = new JLabel(text); l.setForeground(c); l.setFont(new Font("Arial", Font.PLAIN, 13)); return l;
    }

    private static String shortSkillName(String full) {
        return full.contains("(") ? full.substring(0, full.indexOf("(")).trim() : full;
    }

    private static String skillCost(String full) {
        if (!full.contains("(")) return "";
        String inside = full.substring(full.indexOf("(") + 1, full.lastIndexOf(")"));
        return inside.contains(",") ? inside.substring(inside.lastIndexOf(",") + 1).trim() : inside.trim();
    }
}
