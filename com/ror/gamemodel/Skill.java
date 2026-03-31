package com.ror.gamemodel;

import com.ror.gameutil.BattleView;

public abstract class Skill {
    private String name;
    private String description;
    private int cooldown;
    private int currentCooldown;

    public Skill(String name, String description, int cooldown) {
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;
        this.currentCooldown = 0; // Ready to use immediately
    }

    // The magic happens here: User casts the skill ON the Target, and the View logs it.
    public abstract void apply(Entity user, Entity target, BattleView view);

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getCooldown() { return cooldown; }

    public boolean isReady() { return currentCooldown == 0; }
    public void resetCooldown() { this.currentCooldown = this.cooldown; }
    public void reduceCooldown() {
        if (this.currentCooldown > 0) this.currentCooldown--;
    }
}