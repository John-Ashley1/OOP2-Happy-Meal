import java.util.Random;

public class HappyDen extends Hero {
    private Random rand = new Random();

    public HappyDen() { super("Happy Den", 1650, 320); }

    @Override
    public String[] getSkillList() {
        return new String[]{
            "Hard Punch (100-200 dmg, +50 Mana)",
            "Meteor Uppercut (300-400 dmg, -200 Mana)",
            "Almighty Fist (450-500 dmg, -300 Mana)"
        };
    }

    @Override
    public int useSkill(int i) {
        switch (i) {
            case 0: mana += 50;  return 100 + rand.nextInt(101);
            case 1: if (mana < 200) return -1; mana -= 200; return 300 + rand.nextInt(101);
            case 2: if (mana < 300) return -1; mana -= 300; return 450 + rand.nextInt(51);
        }
        return 0;
    }
}
