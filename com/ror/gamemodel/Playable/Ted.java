package com.ror.gamemodel.Playable;

import com.ror.gamemodel.Entity;
import com.ror.gamemodel.Skill;
import com.ror.gameutil.BattleView;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Happy Ted: Big-hearted mountain defender[cite: 86, 88].
 */
public class Ted extends Entity {

    public Ted() {
        // Base HP: 2000 [cite: 89]
        super("Happy Ted", 2000, 10, 10, 10);
        // Note: Set Base Mana to 400 [cite: 90]
        setupSkills();
    }

    @Override
    protected void setupSkills() {
        this.skills = new ArrayList<>();

        // Skill 1: Shield Bash [cite: 91]
        this.skills.add(new Skill("Shield Bash", "Mana Regen: 50. Damage: 100-200.", 0) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                int damage = ThreadLocalRandom.current().nextInt(100, 201);
                target.takeDamage(damage);
                // user.restoreMana(50);
                view.logMessage("🛡️ " + user.getName() + " uses Shield Bash! Deals " + damage + " damage and regains 50 Mana.");
            }
        });

        // Skill 2: Guardian Stand [cite: 91]
        this.skills.add(new Skill("Guardian Stand", "Mana Cost: 200. Damage: 300-400.", 4) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                // user.consumeMana(200);
                int damage = ThreadLocalRandom.current().nextInt(300, 401);
                target.takeDamage(damage);
                view.logMessage("⛰️ " + user.getName() + " takes a Guardian Stand, striking for " + damage + " damage!");
            }
        });

        // Skill 3: Unbreakable Wall [cite: 91]
        this.skills.add(new Skill("Unbreakable Wall", "Mana Cost: 300. Damage: 450-500.", 5) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                // user.consumeMana(300);
                int damage = ThreadLocalRandom.current().nextInt(450, 501);
                target.takeDamage(damage);
                view.logMessage("🧱 " + user.getName() + " summons an Unbreakable Wall, crushing the enemy for " + damage + " damage!");
            }
        });
    }
}