package com.ror.gameutil;

import com.ror.gamemodel.Entity;
import com.ror.gamemodel.Skill;
import com.ror.gamemodel.Playable.*;

import java.util.Scanner;

public class ConsolePvPBattle {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BattleView view = new ConsoleBattleView();

        System.out.println("=========================================");
        System.out.println("   WELCOME TO THE HAPPY MEAL TOURNAMENT  ");
        System.out.println("=========================================");

        // 1. Character Selection Phase
        Entity player1 = selectHero(scanner, "Player 1");
        Entity player2 = selectHero(scanner, "Player 2");

        System.out.println("\n*** BATTLE START: " + player1.getName() + " VS " + player2.getName() + " ***\n");

        boolean isPlayer1Turn = true;

        // 2. The Combat Loop
        while (!player1.isDead() && !player2.isDead()) {
            Entity activePlayer = isPlayer1Turn ? player1 : player2;
            Entity targetPlayer = isPlayer1Turn ? player2 : player1;

            System.out.println("\n-----------------------------------------");
            System.out.println(">>> " + activePlayer.getName().toUpperCase() + "'S TURN <<<");

            // Display Stats
            System.out.println(player1.getName() + " - HP: " + player1.getCurrentHealth() + "/" + player1.getMaxHealth() + " | Mana: " + player1.getCurrentMana());
            System.out.println(player2.getName() + " - HP: " + player2.getCurrentHealth() + "/" + player2.getMaxHealth() + " | Mana: " + player2.getCurrentMana());
            System.out.println("-----------------------------------------");

            // Reduce cooldowns at the start of the player's turn
            for (Skill skill : activePlayer.getSkills()) {
                skill.reduceCooldown();
            }

            // Display Available Skills
            System.out.println("Choose your skill:");
            int skillIndex = 1;
            for (Skill skill : activePlayer.getSkills()) {
                String status = skill.isReady() ? "[READY]" : "[COOLDOWN: " + skill.getCooldown() + " turns]";
                System.out.println(skillIndex + ". " + skill.getName() + " " + status + " - " + skill.getDescription());
                skillIndex++;
            }

            // Get valid input
            int choice = -1;
            boolean validChoice = false;
            while (!validChoice) {
                System.out.print("Enter skill number: ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    if (choice >= 1 && choice <= activePlayer.getSkills().size()) {
                        Skill selectedSkill = activePlayer.getSkills().get(choice - 1);
                        if (selectedSkill.isReady()) {
                            validChoice = true;

                            // 3. Execute the Skill
                            System.out.println("\n");
                            selectedSkill.apply(activePlayer, targetPlayer, view);
                            selectedSkill.resetCooldown(); // Put it on cooldown
                        } else {
                            System.out.println("That skill is on cooldown! Choose another.");
                        }
                    } else {
                        System.out.println("Invalid number. Try again.");
                    }
                } else {
                    System.out.println("Please enter a number.");
                    scanner.next(); // clear invalid input
                }
            }

            // 4. End of Turn Checks
            if (targetPlayer.isDead()) {
                System.out.println("\n=========================================");
                System.out.println("K.O.! " + targetPlayer.getName() + " has been defeated!");
                System.out.println("WINNER: " + activePlayer.getName().toUpperCase() + "!");
                System.out.println("=========================================");
                break;
            }

            // Swap turns
            isPlayer1Turn = !isPlayer1Turn;

            // Optional: Small pause to make it readable
            try { Thread.sleep(1500); } catch (InterruptedException e) {}
        }

        scanner.close();
    }

    // --- Helper Method for Hero Selection ---
    private static Entity selectHero(Scanner scanner, String playerLabel) {
        System.out.println("\n" + playerLabel + ", choose your hero:");
        System.out.println("1. Mark\n2. Ted\n3. Den\n4. Ashley\n5. Vince\n6. Zack\n7. Clent\n8. Trone");

        while (true) {
            System.out.print("Enter hero number (1-8): ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1: return new Mark();
                    case 2: return new Ted();
                    case 3: return new Den();
                    case 4: return new Ashley();
                    case 5: return new Vince();
                    case 6: return new Zack();
                    case 7: return new Clent();
                    case 8: return new Trone();
                    default: System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Please enter a number.");
                scanner.next();
            }
        }
    }
}