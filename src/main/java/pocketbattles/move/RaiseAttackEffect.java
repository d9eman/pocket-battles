package pocketbattles.move;

import pocketbattles.battle.BattleContext;
import pocketbattles.model.Pokemon;

public class RaiseAttackEffect implements MoveEffect {
    private final int stages;

    public RaiseAttackEffect(int stages) {
        this.stages = stages;
    }

    @Override
    public void apply(BattleContext context, Pokemon user, Pokemon target) {
        user.changeAttackStage(stages);
        context.publish(user.getName() + "'s Attack rose!");
    }
}
