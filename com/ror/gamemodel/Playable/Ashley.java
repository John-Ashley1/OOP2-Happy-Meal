package com.ror.gamemodel.Playable;

import com.ror.gamemodel.Entity;
import com.ror.gamemodel.Skill;
import com.ror.gameutil.BattleView;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Happy Ashley: Elite combat tactician[cite: 100].
 */
public class Ashley extends Entity {

    public Ashley() {
        // Base HP: 1500 [cite: 103]
        super("Happy Ashley", 1500, 10, 10, 10);
        // Note: Set Base Mana to 400 [cite: 104]
        setupSkills();
    }

    @Override
    protected void setupSkills() {
        this.skills = new ArrayList<>();

        // Skill 1: Focus Break [cite: 105]
        this.skills.add(new Skill("Focus Break", "Mana Regen: 60. Damage: 100-200.", 0) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                int damage = ThreadLocalRandom.current().nextInt(100, 201);
                target.takeDamage(damage);
                // user.restoreMana(60);
                view.logMessage("👁️ " + user.getName() + " uses Focus Break! Deals " + damage + " damage and regains 60 Mana.");
            }
        });

        // Skill 2: Shadow Dash [cite: 105]
        this.skills.add(new Skill("Shadow Dash", "Mana Cost: 200. Damage: 300-400.", 4) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                // user.consumeMana(200);
                int damage = ThreadLocalRandom.current().nextInt(300, 401);
                target.takeDamage(damage);
                view.logMessage("🌑 " + user.getName() + " vanishes into a Shadow Dash, striking for " + damage + " damage!");
            }
        });

        // Skill 3: Phoenix Drive [cite: 105]
        this.skills.add(new Skill("Phoenix Drive", "Mana Cost: 300. Damage: 450-500.", 5) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                // user.consumeMana(300);
                int damage = ThreadLocalRandom.current().nextInt(450, 501);
                target.takeDamage(damage);
                view.logMessage("🔥 " + user.getName() + " erupts with a Phoenix Drive, dealing " + damage + " damage!");
            }
        });
    }
}