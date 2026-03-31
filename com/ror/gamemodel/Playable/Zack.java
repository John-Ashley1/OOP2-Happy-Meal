package com.ror.gamemodel.Playable;

import com.ror.gamemodel.Entity;
import com.ror.gamemodel.Skill;
import com.ror.gameutil.BattleView;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Happy Zack: Competitive and determined athlete[cite: 117].
 */
public class Zack extends Entity {

    public Zack() {
        // Base HP: 2000 [cite: 118]
        super("Happy Zack", 2000, 10, 10, 10);
        // Note: Set Base Mana to 350 [cite: 119]
        setupSkills();
    }

    @Override
    protected void setupSkills() {
        this.skills = new ArrayList<>();

        // Skill 1: Track field worm up [cite: 120]
        this.skills.add(new Skill("Track Field Warm Up", "Mana Regen: 60. Damage: 100-200.", 0) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                int damage = ThreadLocalRandom.current().nextInt(100, 201);
                target.takeDamage(damage);
                // user.restoreMana(60);
                view.logMessage("🏃 " + user.getName() + " does a Track Field Warm Up! Deals " + damage + " damage and regains 60 Mana.");
            }
        });

        // Skill 2: Javelin Throw [cite: 121]
        this.skills.add(new Skill("Javelin Throw", "Mana Cost: 200. Damage: 300-400.", 4) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                // user.consumeMana(200);
                int damage = ThreadLocalRandom.current().nextInt(300, 401);
                target.takeDamage(damage);
                view.logMessage("🎯 " + user.getName() + " executes a Javelin Throw for " + damage + " damage!");
            }
        });

        // Skill 3: Egoist [cite: 122]
        this.skills.add(new Skill("Egoist", "Mana Cost: 300. Damage: 450-500.", 5) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                // user.consumeMana(300);
                int damage = ThreadLocalRandom.current().nextInt(450, 501);
                target.takeDamage(damage);
                view.logMessage("👑 " + user.getName() + " activates Egoist, overwhelming the enemy with " + damage + " damage!");
            }
        });
    }
}