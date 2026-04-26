package pocketbattles.battle;

import pocketbattles.observer.BattleEventPublisher;
import pocketbattles.strategy.AccuracyPolicy;
import pocketbattles.strategy.DamageCalculator;

public class BattleContext {
    private final DamageCalculator damageCalculator;
    private final AccuracyPolicy accuracyPolicy;
    private final BattleEventPublisher publisher;

    public BattleContext(DamageCalculator damageCalculator,
                         AccuracyPolicy accuracyPolicy,
                         BattleEventPublisher publisher) {
        this.damageCalculator = damageCalculator;
        this.accuracyPolicy = accuracyPolicy;
        this.publisher = publisher;
    }

    public DamageCalculator getDamageCalculator() {
        return damageCalculator;
    }

    public AccuracyPolicy getAccuracyPolicy() {
        return accuracyPolicy;
    }

    public void publish(String message) {
        publisher.publish(new BattleEvent(message));
    }
}
