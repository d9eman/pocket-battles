package pocketbattles.move;

import pocketbattles.battle.BattleContext;
import pocketbattles.model.Pokemon;

public interface MoveEffect {
    void apply(BattleContext context, Pokemon user, Pokemon target);
}
