package com.ror.gamemodel.Playable;

import com.ror.gamemodel.Entity;
import com.ror.gamemodel.Skill;
import com.ror.gameutil.BattleView;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Happy Den: 20-time world boxing champion[cite: 93, 95].
 */
public class Den extends Entity {

    public Den() {
        // Base HP: 1650 [cite: 96]
        super("Happy Den", 1650, 10, 10, 10);
        // Note: Set Base Mana to 320 [cite: 97]
        setupSkills();
    }

    @Override
    protected void setupSkills() {
        this.skills = new ArrayList<>();

        // Skill 1: Hard Punch [cite: 98]
        this.skills.add(new Skill("Hard Punch", "Mana Regen: 50. Damage: 100-200.", 0) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                int damage = ThreadLocalRandom.current().nextInt(100, 201);
                target.takeDamage(damage);
                // user.restoreMana(50);
                view.logMessage("🥊 " + user.getName() + " throws a Hard Punch! Deals " + damage + " damage and regains 50 Mana.");
            }
        });

        // Skill 2: Meteor Uppercut [cite: 98]
        this.skills.add(new Skill("Meteor Uppercut", "Mana Cost: 200. Damage: 300-400.", 4) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                // user.consumeMana(200);
                int damage = ThreadLocalRandom.current().nextInt(300, 401);
                target.takeDamage(damage);
                view.logMessage("☄️ " + user.getName() + " launches a Meteor Uppercut, dealing " + damage + " damage!");
            }
        });

        // Skill 3: Almighty Fist [cite: 98]
        this.skills.add(new Skill("Almighty Fist", "Mana Cost: 300. Damage: 450-500.", 5) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                // user.consumeMana(300);
                int damage = ThreadLocalRandom.current().nextInt(450, 501);
                target.takeDamage(damage);
                view.logMessage("⭐ " + user.getName() + " strikes with the Almighty Fist for " + damage + " damage!");
            }
        });
    }
}