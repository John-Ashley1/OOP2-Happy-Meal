import java.util.Random;

public class HappyTrone extends Hero {
    private Random rand = new Random();

    public HappyTrone() { super("Happy Trone", 3000, 0); }

    @Override
    public String[] getSkillList() {
        return new String[]{
            "Blood Spear (100-200 dmg, +60 HP)",
            "Blood Shield (300-400 dmg, -200 HP)",
            "Blood Explosion (450-500 dmg, -300 HP)"
        };
    }

    @Override
    public int useSkill(int i) {
        switch (i) {
            case 0: hp += 60;  return 100 + rand.nextInt(101);
            case 1: if (hp < 200) return -1; hp -= 200; return 300 + rand.nextInt(101);
            case 2: if (hp < 300) return -1; hp -= 300; return 450 + rand.nextInt(51);
        }
        return 0;
    }
}
