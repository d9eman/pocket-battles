package pocketbattles.strategy;

import pocketbattles.action.BattleAction;
import pocketbattles.action.UseMoveAction;
import pocketbattles.model.Trainer;
import pocketbattles.move.Move;

import java.util.List;

public class RandomOpponentMoveStrategy implements OpponentMoveStrategy {
    private final RandomSource random;

    public RandomOpponentMoveStrategy(RandomSource random) {
        this.random = random;
    }

    @Override
    public BattleAction chooseAction(Trainer self, Trainer opponent) {
        List<Move> moves = self.getActivePokemon().getMoves();
        return new UseMoveAction(moves.get(random.nextInt(moves.size())));
    }
}
