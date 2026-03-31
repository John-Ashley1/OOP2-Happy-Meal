public abstract class Hero {
    protected String name;
    protected int hp;
    protected int mana;

    public Hero(String name, int hp, int mana) {
        this.name = name;
        this.hp   = hp;
        this.mana = mana;
    }

    public String getName()         { return name; }
    public int    getHP()           { return hp;   }
    public int    getMana()         { return mana; }
    public void   setHP(int hp)     { this.hp   = hp; }
    public void   setMana(int mana) { this.mana = mana; }

    public abstract String[] getSkillList();
    public abstract int useSkill(int index);
    public void reduceCooldowns() {}
}
