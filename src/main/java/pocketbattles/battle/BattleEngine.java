package pocketbattles.battle;

import pocketbattles.action.BattleAction;
import pocketbattles.model.Trainer;
import pocketbattles.strategy.RandomSource;

public class BattleEngine {
    private final BattleContext context;
    private final RandomSource random;

    public BattleEngine(BattleContext context, RandomSource random) {
        this.context = context;
        this.random = random;
    }

    public void playTurn(Trainer trainer1, BattleAction action1, Trainer trainer2, BattleAction action2) {
        trainer1.getActivePokemon().resetTurnFlags();
        trainer2.getActivePokemon().resetTurnFlags();

        OrderedPair ordered = orderActions(trainer1, action1, trainer2, action2);

        ordered.firstAction.execute(context, ordered.firstTrainer, ordered.secondTrainer);

        if (!ordered.secondTrainer.getActivePokemon().isFainted()) {
            ordered.secondAction.execute(context, ordered.secondTrainer, ordered.firstTrainer);
        }
    }

    private OrderedPair orderActions(Trainer trainer1, BattleAction action1, Trainer trainer2, BattleAction action2) {
        if (action1.priority() != action2.priority()) {
            return action1.priority() > action2.priority()
                    ? new OrderedPair(trainer1, action1, trainer2, action2)
                    : new OrderedPair(trainer2, action2, trainer1, action1);
        }

        int speed1 = trainer1.getActivePokemon().getEffectiveSpeed();
        int speed2 = trainer2.getActivePokemon().getEffectiveSpeed();

        if (speed1 != speed2) {
            return speed1 > speed2
                    ? new OrderedPair(trainer1, action1, trainer2, action2)
                    : new OrderedPair(trainer2, action2, trainer1, action1);
        }

        return random.nextBoolean()
                ? new OrderedPair(trainer1, action1, trainer2, action2)
                : new OrderedPair(trainer2, action2, trainer1, action1);
    }

    private record OrderedPair(
            Trainer firstTrainer,
            BattleAction firstAction,
            Trainer secondTrainer,
            BattleAction secondAction
    ) {}
}
