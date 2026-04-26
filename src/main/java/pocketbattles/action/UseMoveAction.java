package pocketbattles.action;

import pocketbattles.battle.BattleContext;
import pocketbattles.model.Pokemon;
import pocketbattles.model.Trainer;
import pocketbattles.move.Move;

public class UseMoveAction implements BattleAction {
    private final Move move;

    public UseMoveAction(Move move) {
        this.move = move;
    }

    @Override
    public int priority() {
        return move.getPriority();
    }

    @Override
    public void execute(BattleContext context, Trainer self, Trainer opponent) {
        Pokemon user = self.getActivePokemon();
        Pokemon target = opponent.getActivePokemon();

        if (user.isFainted()) {
            return;
        }

        move.use(context, user, target);
    }
}
