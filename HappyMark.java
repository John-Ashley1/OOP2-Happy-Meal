import java.util.Random;

public class HappyMark extends Hero {
    private Random rand = new Random();

    public HappyMark() { super("Happy Mark", 1800, 450); }

    @Override
    public String[] getSkillList() {
        return new String[]{
            "Joyful Slam (100-200 dmg, +50 Mana)",
            "Guardian Stand (300-350 dmg, -200 Mana)",
            "Festival Fury (400-500 dmg, -300 Mana)"
        };
    }

    @Override
    public int useSkill(int i) {
        switch (i) {
            case 0: mana += 50;  return 100 + rand.nextInt(101);
            case 1: if (mana < 200) return -1; mana -= 200; return 300 + rand.nextInt(151);
            case 2: if (mana < 300) return -1; mana -= 300; return 400 + rand.nextInt(101);
        }
        return 0;
    }
}
