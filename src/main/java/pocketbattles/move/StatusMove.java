package pocketbattles.move;

import pocketbattles.battle.BattleContext;
import pocketbattles.model.Pokemon;
import pocketbattles.model.Type;

public class StatusMove implements Move {
    private final String name;
    private final Type type;
    private final int accuracy;
    private final int priority;
    private final MoveEffect effect;

    public StatusMove(String name, Type type, int accuracy, int priority, MoveEffect effect) {
        this.name = name;
        this.type = type;
        this.accuracy = accuracy;
        this.priority = priority;
        this.effect = effect;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public int getAccuracy() {
        return accuracy;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void use(BattleContext context, Pokemon user, Pokemon target) {
        context.publish(user.getName() + " used " + name + ".");

        if (!context.getAccuracyPolicy().hits(this)) {
            context.publish(name + " failed!");
            return;
        }

        effect.apply(context, user, target);
    }
}
