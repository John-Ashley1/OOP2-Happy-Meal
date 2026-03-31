import java.util.Random;

public class HappyTed extends Hero {
    private Random rand = new Random();

    public HappyTed() { super("Happy Ted", 2000, 400); }

    @Override
    public String[] getSkillList() {
        return new String[]{
            "Shield Bash (100-200 dmg, +50 Mana)",
            "Guardian Stand (300-400 dmg, -200 Mana)",
            "Unbreakable Wall (450-500 dmg, -300 Mana)"
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
