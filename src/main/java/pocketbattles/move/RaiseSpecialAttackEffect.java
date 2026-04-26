package pocketbattles.move;

import pocketbattles.battle.BattleContext;
import pocketbattles.model.Pokemon;

public class RaiseSpecialAttackEffect implements MoveEffect {
    private final int stages;

    public RaiseSpecialAttackEffect(int stages) {
        this.stages = stages;
    }

    @Override
    public void apply(BattleContext context, Pokemon user, Pokemon target) {
        user.changeSpecialAttackStage(stages);
        context.publish(user.getName() + "'s Special Attack rose!");
    }
}
