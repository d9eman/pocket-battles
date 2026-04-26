package pocketbattles.move;

import pocketbattles.battle.BattleContext;
import pocketbattles.model.Pokemon;

public class ProtectEffect implements MoveEffect {
    @Override
    public void apply(BattleContext context, Pokemon user, Pokemon target) {
        user.setProtectedThisTurn(true);
        context.publish(user.getName() + " is protected this turn.");
    }
}
