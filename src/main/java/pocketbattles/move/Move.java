package pocketbattles.move;

import pocketbattles.battle.BattleContext;
import pocketbattles.model.Pokemon;
import pocketbattles.model.Type;

public interface Move {
    String getName();
    Type getType();
    int getAccuracy();
    int getPriority();
    void use(BattleContext context, Pokemon user, Pokemon target);
}
