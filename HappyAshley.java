import java.util.Random;

public class HappyAshley extends Hero {
    private Random rand = new Random();
    private int cd1 = 0, cd2 = 0;

    public HappyAshley() { super("Happy Ashley", 1500, 400); }

    @Override
    public String[] getSkillList() {
        return new String[]{
            "Focus Break (100-200 dmg, +50 Mana)",
            "Shadow Dash (300-400 dmg, -200 Mana)" + (cd1 > 0 ? " [CD:" + cd1 + "]" : ""),
            "Phoenix Drive (450-500 dmg, -300 Mana)" + (cd2 > 0 ? " [CD:" + cd2 + "]" : "")
        };
    }

    @Override
    public void reduceCooldowns() {
        if (cd1 > 0) cd1--;
        if (cd2 > 0) cd2--;
    }

    @Override
    public int useSkill(int i) {
        switch (i) {
            case 0: mana += 70; return 100 + rand.nextInt(101);
            case 1: if (cd1 > 0) return -2; if (mana < 200) return -1; mana -= 200; cd1 = 4; return 300 + rand.nextInt(101);
            case 2: if (cd2 > 0) return -2; if (mana < 300) return -1; mana -= 300; cd2 = 5; return 450 + rand.nextInt(51);
        }
        return 0;
    }
}
