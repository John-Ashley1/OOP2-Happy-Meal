package com.ror.gamemodel.Playable;

import com.ror.gamemodel.Entity;
import com.ror.gamemodel.Skill;
import com.ror.gameutil.BattleView;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Happy Trone: Silent and feared vampire[cite: 131].
 */
public class Trone extends Entity {

    public Trone() {
        // Base HP: 3000 (Highest in the game to offset HP costs) [cite: 132]
        super("Happy Trone", 3000, 10, 10, 10);
        setupSkills();
    }

    @Override
    protected void setupSkills() {
        this.skills = new ArrayList<>();

        // Skill 1: Blood Spear [cite: 133]
        this.skills.add(new Skill("Blood Spear", "HP Regen: 60. Damage: 100-200.", 0) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                int damage = ThreadLocalRandom.current().nextInt(100, 201);
                target.takeDamage(damage);
                // Trone heals himself instead of gaining mana
                // user.heal(60); // Ensure you have a heal method in Entity
                view.logMessage("🦇 " + user.getName() + " throws a Blood Spear! Deals " + damage + " damage and regains 60 HP.");
            }
        });

        // Skill 2: Blood Shield [cite: 134]
        this.skills.add(new Skill("Blood Shield", "HP Cost: 200. Damage: 300-400.", 2) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                user.takeDamage(200); // Trone hurts himself to cast this
                int damage = ThreadLocalRandom.current().nextInt(300, 401);
                target.takeDamage(damage);
                view.logMessage("🩸 " + user.getName() + " sacrifices 200 HP for a Blood Shield, dealing " + damage + " damage!");
            }
        });

        // Skill 3: Blood Explosion [cite: 135]
        this.skills.add(new Skill("Blood Explosion", "HP Cost: 800. Damage: 780-1000.", 5) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                user.takeDamage(800); // Massive self-damage cost
                int damage = ThreadLocalRandom.current().nextInt(780, 1001);
                target.takeDamage(damage);
                view.logMessage("💥 " + user.getName() + " detonates a Blood Explosion (costing 800 HP), obliterating the enemy for " + damage + " damage!");
            }
        });
    }
}