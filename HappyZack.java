import java.util.Random;

public class HappyZack extends Hero {
    private Random rand = new Random();

    public HappyZack() { super("Happy Zack", 2000, 500); }

    @Override
    public String[] getSkillList() {
        return new String[]{
            "Track Field Warm Up (100-200 dmg, +60 Mana)",
            "Javelin Throw (300-400 dmg, -200 Mana)",
            "Crushing Knee (450-500 dmg, -300 Mana)"
        };
    }

    @Override
    public int useSkill(int i) {
        switch (i) {
            case 0: mana += 60;  return 100 + rand.nextInt(101);
            case 1: if (mana < 200) return -1; mana -= 200; return 300 + rand.nextInt(101);
            case 2: if (mana < 300) return -1; mana -= 300; return 450 + rand.nextInt(51);
        }
        return 0;
    }
}
