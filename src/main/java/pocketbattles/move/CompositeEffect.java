package pocketbattles.move;

import pocketbattles.battle.BattleContext;
import pocketbattles.model.Pokemon;

import java.util.List;

public class CompositeEffect implements MoveEffect {
    private final List<MoveEffect> effects;

    public CompositeEffect(List<MoveEffect> effects) {
        this.effects = List.copyOf(effects);
    }

    @Override
    public void apply(BattleContext context, Pokemon user, Pokemon target) {
        for (MoveEffect effect : effects) {
            effect.apply(context, user, target);
        }
    }
}
