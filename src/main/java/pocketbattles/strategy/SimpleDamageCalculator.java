package pocketbattles.strategy;

import pocketbattles.model.Pokemon;
import pocketbattles.move.DamagingMove;

public class SimpleDamageCalculator implements DamageCalculator {
    private final TypeChart typeChart;
    private final RandomSource random;

    public SimpleDamageCalculator(TypeChart typeChart, RandomSource random) {
        this.typeChart = typeChart;
        this.random = random;
    }

    @Override
    public int calculate(Pokemon attacker, Pokemon defender, DamagingMove move) {
        int level = attacker.getLevel();
        int power = move.getPower();
        int attack = move.getDamageClass().attackingStat(attacker);
        int defense = move.getDamageClass().defendingStat(defender);

        int baseDamage = (((2 * level / 5 + 2) * power * attack / defense) / 50) + 2;

        double stab = attacker.getTypes().contains(move.getType()) ? 1.5 : 1.0;
        double typeMultiplier = typeChart.multiplier(move.getType(), defender.getTypes());
        double randomFactor = 0.85 + (random.nextDouble() * 0.15);

        if (typeMultiplier == 0.0) {
            return 0;
        }

        int damage = (int) Math.floor(baseDamage * stab * typeMultiplier * randomFactor);
        return Math.max(1, damage);
    }
}
