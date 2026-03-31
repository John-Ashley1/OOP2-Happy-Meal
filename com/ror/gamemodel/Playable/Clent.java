package com.ror.gamemodel.Playable;

import com.ror.gamemodel.Entity;
import com.ror.gamemodel.Skill;
import com.ror.gameutil.BattleView;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Happy Clent: The eccentric thinker[cite: 124].
 */
public class Clent extends Entity {

    public Clent() {
        // Base HP: 2000 [cite: 125]
        super("Happy Clent", 2000, 10, 10, 10);
        // Note: Set Base Mana to 500 [cite: 126]
        setupSkills();
    }

    @Override
    protected void setupSkills() {
        this.skills = new ArrayList<>();

        // Skill 1: Book of IT [cite: 127]
        this.skills.add(new Skill("Book of IT", "Mana Regen: 60. Damage: 100-200.", 0) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                int damage = ThreadLocalRandom.current().nextInt(100, 201);
                target.takeDamage(damage);
                // user.restoreMana(60);
                view.logMessage("📖 " + user.getName() + " throws the Book of IT! Deals " + damage + " damage and regains 60 Mana.");
            }
        });

        // Skill 2: Think of IT [cite: 128]
        this.skills.add(new Skill("Think of IT", "Mana Cost: 200. Damage: 300-400.", 4) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                // user.consumeMana(200);
                int damage = ThreadLocalRandom.current().nextInt(300, 401);
                target.takeDamage(damage);
                view.logMessage("🧠 " + user.getName() + " uses Think of IT, causing " + damage + " psychic damage!");
            }
        });

        // Skill 3: Come to me [cite: 129]
        this.skills.add(new Skill("Come to me", "Mana Cost: 300. Damage: 450-500.", 5) {
            @Override
            public void apply(Entity user, Entity target, BattleView view) {
                // user.consumeMana(300);
                int damage = ThreadLocalRandom.current().nextInt(450, 501);
                target.takeDamage(damage);
                view.logMessage("🌀 " + user.getName() + " commands 'Come to me', crushing the target for " + damage + " damage!");
            }
        });
    }
}