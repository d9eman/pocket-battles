package pocketbattles.strategy;

import pocketbattles.model.Pokemon;
import pocketbattles.move.DamagingMove;

public interface DamageCalculator {
    int calculate(Pokemon attacker, Pokemon defender, DamagingMove move);
}
