import java.util.Random;

public class HappyVince extends Hero {
    private Random rand = new Random();

    public HappyVince() { super("Happy Vince", 2000, 400); }

    @Override
    public String[] getSkillList() {
        return new String[]{
            "Lighting Job (100-200 dmg, +70 Mana)",
            "Left-Right Hook Combo (300-400 dmg, -200 Mana)",
            "Crushing Knee (450-500 dmg, -300 Mana)"
        };
    }

    @Override
    public int useSkill(int i) {
        switch (i) {
            case 0: mana += 70;  return 100 + rand.nextInt(101);
            case 1: if (mana < 200) return -1; mana -= 200; return 300 + rand.nextInt(101);
            case 2: if (mana < 300) return -1; mana -= 300; return 450 + rand.nextInt(51);
        }
        return 0;
    }
}
