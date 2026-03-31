package com.ror.gamemodel.Playable;

import com.ror.gamemodel.Entity;
import com.ror.gamemodel.Skill;
import com.ror.gameutil.BattleView;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Happy Mark: Cheerful street performer[cite: 79, 81].
 */
public class Mark extends Entity {

    public Mark() {
        // Name, MaxHealth, Attack, Defense, Speed (Using 10 for unused base stats)
        // Base HP: 1800 [cite: 82]
        super("Happy Mark", 1800, 10, 10, 10);
        // Note: Set Base Mana to 450 in your Entity class [cite: 83]
        setupSkills();
    }

    @Override
    protected void setupSkills() {
        this.skills = new ArrayList<>();

        // Skill 1: Joyful Slam [cite: 84]
        this.skills.add(new Skill("Joyful Slam", "Mana Regen: 50. Damage: 100-200.", 0) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                int damage = ThreadLocalRandom.current().nextInt(100, 201);
                target.takeDamage(damage);
                // user.restoreMana(50); // Uncomment once added to Entity
                view.logMessage("🎪 " + user.getName() + " uses Joyful Slam! Deals " + damage + " damage and regains 50 Mana.");
            }
        });

        // Skill 2: Guardian Break [cite: 84]
        this.skills.add(new Skill("Guardian Break", "Mana Cost: 200. Damage: 300-350.", 3) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                // user.consumeMana(200);
                int damage = ThreadLocalRandom.current().nextInt(300, 351);
                target.takeDamage(damage);
                view.logMessage("🔨 " + user.getName() + " shatters defenses with Guardian Break, dealing " + damage + " damage!");
            }
        });

        // Skill 3: Festival Fury [cite: 84]
        this.skills.add(new Skill("Festival Fury", "Mana Cost: 300. Damage: 400-500.", 5) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                // user.consumeMana(300);
                int damage = ThreadLocalRandom.current().nextInt(400, 501);
                target.takeDamage(damage);
                view.logMessage("🎆 " + user.getName() + " unleashes Festival Fury for a massive " + damage + " damage!");
            }
        });
    }
}