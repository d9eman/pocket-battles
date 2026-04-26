package pocketbattles.move;

import pocketbattles.battle.BattleContext;
import pocketbattles.model.DamageClass;
import pocketbattles.model.Pokemon;
import pocketbattles.model.Type;

public class DamagingMove implements Move {
    private final String name;
    private final Type type;
    private final int power;
    private final int accuracy;
    private final int priority;
    private final DamageClass damageClass;

    public DamagingMove(String name, Type type, int power, int accuracy, int priority, DamageClass damageClass) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.priority = priority;
        this.damageClass = damageClass;
    }

    public int getPower() {
        return power;
    }

    public DamageClass getDamageClass() {
        return damageClass;
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
            context.publish(name + " missed!");
            return;
        }

        if (target.isProtectedThisTurn()) {
            context.publish(target.getName() + " protected itself!");
            return;
        }

        int damage = context.getDamageCalculator().calculate(user, target, this);
        target.takeDamage(damage);

        context.publish(target.getName() + " took " + damage + " damage.");

        if (target.isFainted()) {
            context.publish(target.getName() + " fainted!");
        }
    }
}
