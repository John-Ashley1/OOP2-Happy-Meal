package com.ror.gameutil;

import com.ror.gamemodel.Entity;
import com.ror.gamemodel.Skill;
import com.ror.gamemodel.Playable.Mark; // For testing
import com.ror.gamemodel.Playable.Ted;  // For testing

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GuiBattleArena extends JFrame implements BattleView {

    // Data Models
    private Entity player1;
    private Entity player2;
    private boolean isPlayer1Turn = true;

    // UI Components
    private JProgressBar topHealthBar, bottomHealthBar;
    private JLabel topNameLabel, bottomNameLabel, bottomStatsLabel;
    private JTextArea combatLog;
    private JPanel skillsPanel;

    public GuiBattleArena(Entity player1, Entity player2) {
        this.player1 = player1;
        this.player2 = player2;

        // 1. Window Setup
        setTitle("Happy Meal Tournament - Battle");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout(10, 10));

        // Add some padding around the edges of the window
        ((JPanel)getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        // 2. Build the UI Sections
        buildTopPanel();
        buildCenterLog();
        buildBottomPanel();

        // 3. Start the Game!
        logMessage("A battle begins! " + player1.getName() + " vs. " + player2.getName() + ".");
        updateTurnUI();
    }

    // --- UI CONSTRUCTION ---

    private void buildTopPanel() {
        // Matches the "Goblin" section in your screenshot
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        topNameLabel = new JLabel(player2.getName());
        topNameLabel.setForeground(Color.WHITE);
        topNameLabel.setFont(new Font("Monospaced", Font.BOLD, 18));

        topHealthBar = new JProgressBar(0, player2.getMaxHealth());
        topHealthBar.setValue(player2.getCurrentHealth());
        topHealthBar.setPreferredSize(new Dimension(300, 25));
        topHealthBar.setForeground(Color.GREEN);
        topHealthBar.setBackground(Color.DARK_GRAY);
        // Removes the percentage text so it's just a solid bar like your image
        topHealthBar.setStringPainted(false);

        topPanel.add(topNameLabel);
        topPanel.add(topHealthBar);

        add(topPanel, BorderLayout.NORTH);
    }

    private void buildCenterLog() {
        // Matches the black text area
        combatLog = new JTextArea();
        combatLog.setBackground(Color.BLACK);
        combatLog.setForeground(Color.WHITE); // Default white text
        combatLog.setFont(new Font("Monospaced", Font.PLAIN, 16));
        combatLog.setEditable(false);
        combatLog.setLineWrap(true);
        combatLog.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(combatLog);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        // Force the scrollbar to match the dark theme a bit better if possible
        scrollPane.getVerticalScrollBar().setBackground(Color.BLACK);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void buildBottomPanel() {
        // This holds BOTH the player stats box AND the skills box
        JPanel bottomWrapper = new JPanel(new BorderLayout(0, 10));
        bottomWrapper.setBackground(Color.BLACK);

        // -- Player Status Box (Matches "Sky Mage" section) --
        JPanel statusBox = new JPanel(new BorderLayout(15, 0));
        statusBox.setBackground(Color.BLACK);
        statusBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                new EmptyBorder(10, 10, 10, 10)
        ));

        bottomNameLabel = new JLabel(player1.getName());
        bottomNameLabel.setForeground(Color.WHITE);
        bottomNameLabel.setFont(new Font("Monospaced", Font.BOLD, 18));

        bottomHealthBar = new JProgressBar(0, player1.getMaxHealth());
        bottomHealthBar.setValue(player1.getCurrentHealth());
        bottomHealthBar.setPreferredSize(new Dimension(400, 30));
        bottomHealthBar.setForeground(Color.GREEN);
        bottomHealthBar.setBackground(Color.DARK_GRAY);
        bottomHealthBar.setStringPainted(true); // Shows "HP: X/Y" text on the bar

        // We use this to show Mana instead of Level!
        bottomStatsLabel = new JLabel("Mana: " + player1.getCurrentMana());
        bottomStatsLabel.setForeground(Color.WHITE);
        bottomStatsLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));

        statusBox.add(bottomNameLabel, BorderLayout.WEST);
        statusBox.add(bottomHealthBar, BorderLayout.CENTER);
        statusBox.add(bottomStatsLabel, BorderLayout.EAST);

        // -- Skills Box (Matches the 3 buttons at the bottom) --
        skillsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        skillsPanel.setBackground(Color.BLACK);
        skillsPanel.setPreferredSize(new Dimension(800, 60));

        bottomWrapper.add(statusBox, BorderLayout.NORTH);
        bottomWrapper.add(skillsPanel, BorderLayout.SOUTH);

        add(bottomWrapper, BorderLayout.SOUTH);
    }

    // --- GAME LOGIC & UI UPDATES ---

    private void updateTurnUI() {
        // 1. Determine whose turn it is
        Entity activePlayer = isPlayer1Turn ? player1 : player2;

        logMessage("\n--- " + activePlayer.getName().toUpperCase() + "'S TURN ---");

        // 2. Reduce cooldowns for the active player at the start of their turn
        for (Skill skill : activePlayer.getSkills()) {
            skill.reduceCooldown();
        }

        // 3. Update Health Bars and Mana Labels
        topHealthBar.setValue(player2.getCurrentHealth());
        bottomHealthBar.setValue(player1.getCurrentHealth());
        bottomHealthBar.setString("HP: " + player1.getCurrentHealth() + "/" + player1.getMaxHealth());
        bottomStatsLabel.setText("Mana: " + player1.getCurrentMana());

        // 4. Clear old buttons and generate new ones for the active player
        skillsPanel.removeAll();

        for (Skill skill : activePlayer.getSkills()) {
            JButton skillBtn = new JButton();
            skillBtn.setBackground(Color.BLACK);
            skillBtn.setForeground(Color.WHITE);
            skillBtn.setFont(new Font("Monospaced", Font.BOLD, 14));
            skillBtn.setFocusPainted(false);
            skillBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

            if (skill.isReady()) {
                skillBtn.setText(skill.getName());
                skillBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                // Add the click event
                skillBtn.addActionListener(e -> executeSkill(skill, activePlayer));
            } else {
                skillBtn.setText(skill.getName() + " (CD: " + skill.getCooldown() + ")");
                skillBtn.setForeground(Color.GRAY);
                skillBtn.setEnabled(false);
            }
            skillsPanel.add(skillBtn);
        }

        // Force the GUI to redraw the buttons
        skillsPanel.revalidate();
        skillsPanel.repaint();
    }

    private void executeSkill(Skill skill, Entity activePlayer) {
        Entity targetPlayer = isPlayer1Turn ? player2 : player1;

        // Apply the skill math (this triggers our logMessage below!)
        skill.apply(activePlayer, targetPlayer, this);
        skill.resetCooldown();

        // Check for game over
        if (targetPlayer.isDead()) {
            updateHealthBarsFinal();
            logMessage("\n*** K.O.! " + targetPlayer.getName() + " has been defeated! ***");
            logMessage("*** WINNER: " + activePlayer.getName().toUpperCase() + " ***");
            skillsPanel.removeAll(); // Remove buttons so they can't keep attacking
            skillsPanel.revalidate();
            skillsPanel.repaint();
            return;
        }

        // Swap turns and update the UI for the next player
        isPlayer1Turn = !isPlayer1Turn;
        updateTurnUI();
    }

    private void updateHealthBarsFinal() {
        topHealthBar.setValue(player2.getCurrentHealth());
        bottomHealthBar.setValue(player1.getCurrentHealth());
        bottomHealthBar.setString("HP: " + player1.getCurrentHealth() + "/" + player1.getMaxHealth());
    }

    // Implementation of the BattleView Interface
    @Override
    public void logMessage(String message) {
        combatLog.append(message + "\n");
        // Auto-scroll to the bottom of the text area
        combatLog.setCaretPosition(combatLog.getDocument().getLength());
    }

    // --- QUICK TEST ---
    public static void main(String[] args) {
        // This is just to test the UI quickly without navigating your whole menu
        SwingUtilities.invokeLater(() -> {
            Entity p1 = new Mark();
            Entity p2 = new Ted();
            new GuiBattleArena(p1, p2).setVisible(true);
        });
    }
}