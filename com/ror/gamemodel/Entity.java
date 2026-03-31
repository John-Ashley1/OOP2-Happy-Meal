package com.ror.gamemodel;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    protected String name;
    protected int maxHealth;
    protected int currentHealth;
    protected int maxMana;       // Added Mana for the Happy Meal tournament!
    protected int currentMana;
    protected int atk;
    protected int def;
    protected int speed;
    protected boolean shieldActive;
    protected List<Skill> skills;

    // Updated Constructor to include maxMana
    public Entity(String name, int maxHealth, int maxMana, int atk, int def) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.atk = atk;
        this.def = def;
        this.speed = speed;
        this.shieldActive = false;
        this.skills = new ArrayList<>();
    }

    // Every specific hero MUST define their own skills
    protected abstract void setupSkills();

    // --- Core Combat Mechanics ---
    public void takeDamage(int amount) {
        if (shieldActive) {
            amount = amount / 2; // Shield cuts damage in half
            shieldActive = false; // Shield breaks after one hit
        }
        this.currentHealth -= amount;
        if (this.currentHealth < 0) this.currentHealth = 0;
    }

    public void heal(int amount) {
        this.currentHealth += amount;
        if (this.currentHealth > this.maxHealth) this.currentHealth = this.maxHealth;
    }

    public void consumeMana(int amount) {
        this.currentMana -= amount;
        if (this.currentMana < 0) this.currentMana = 0;
    }

    public void restoreMana(int amount) {
        this.currentMana += amount;
        if (this.currentMana > this.maxMana) this.currentMana = this.maxMana;
    }

    public void levelUp() {
        // Base level up logic (can be overridden by specific characters)
        this.maxHealth += 10;
        this.currentHealth = this.maxHealth;
        this.atk += 2;
        this.def += 2;
    }

    // --- Getters and Setters ---
    public String getName() { return name; }
    public int getCurrentHealth() { return currentHealth; }
    public int getMaxHealth() { return maxHealth; }
    public int getCurrentMana() { return currentMana; }
    public int getMaxMana() { return maxMana; }
    public int getAtk() { return atk; }
    public int getDef() { return def; }
    public int getSpeed() { return speed; }
    public boolean isShieldActive() { return shieldActive; }
    public void setShieldActive(boolean shieldActive) { this.shieldActive = shieldActive; }
    public List<Skill> getSkills() { return skills; }

    public void setAtk(int atk) { this.atk = atk; }
    public void setDef(int def) { this.def = def; }

    public boolean isDead() { return currentHealth <= 0; }
}