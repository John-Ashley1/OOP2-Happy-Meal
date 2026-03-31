package com.ror.gamemodel.Playable;

import com.ror.gamemodel.Entity;
import com.ror.gamemodel.Skill;
import com.ror.gameutil.BattleView;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Happy Vince: Prized Muay Thai fighter[cite: 107, 108].
 */
public class Vince extends Entity {

    public Vince() {
        // Base HP: 2000 [cite: 111]
        super("Happy Vince", 2000, 10, 10, 10);
        // Note: Set Base Mana to 350 [cite: 112]
        setupSkills();
    }

    @Override
    protected void setupSkills() {
        this.skills = new ArrayList<>();

        // Skill 1: Lightning Job [cite: 113]
        this.skills.add(new Skill("Lightning Job", "Mana Regen: 60. Damage: 100-200.", 0) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                int damage = ThreadLocalRandom.current().nextInt(100, 201);
                target.takeDamage(damage);
                // user.restoreMana(60);
                view.logMessage("⚡ " + user.getName() + " throws a Lightning Job! Deals " + damage + " damage and regains 60 Mana.");
            }
        });

        // Skill 2: Left-Right Hook Combo [cite: 114]
        this.skills.add(new Skill("Left-Right Hook Combo", "Mana Cost: 200. Damage: 300-400.", 4) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                // user.consumeMana(200);
                int damage = ThreadLocalRandom.current().nextInt(300, 401);
                target.takeDamage(damage);
                view.logMessage("🥊 " + user.getName() + " lands a Left-Right Hook Combo for " + damage + " damage!");
            }
        });

        // Skill 3: Crushing Knee [cite: 115]
        this.skills.add(new Skill("Crushing Knee", "Mana Cost: 300. Damage: 450-500.", 5) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                // user.consumeMana(300);
                int damage = ThreadLocalRandom.current().nextInt(450, 501);
                target.takeDamage(damage);
                view.logMessage("🦵 " + user.getName() + " delivers a Crushing Knee, dealing " + damage + " damage!");
            }
        });
    }
}