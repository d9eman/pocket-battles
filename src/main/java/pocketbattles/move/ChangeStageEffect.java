package pocketbattles.move;

import pocketbattles.battle.BattleContext;
import pocketbattles.model.Pokemon;
import pocketbattles.model.StageStat;

public class ChangeStageEffect implements MoveEffect {
    private static final int SHARP_STAGE = 2;

    private final EffectTarget effectTarget;
    private final StageStat stageStat;
    private final int stageDelta;

    public ChangeStageEffect(EffectTarget effectTarget, StageStat stageStat, int stageDelta) {
        this.effectTarget = effectTarget;
        this.stageStat = stageStat;
        this.stageDelta = stageDelta;
    }

    @Override
    public void apply(BattleContext context, Pokemon user, Pokemon target) {
        Pokemon recipient = effectTarget == EffectTarget.SELF ? user : target;
        recipient.changeStage(stageStat, stageDelta);
        context.publish(recipient.getName() + "'s " + displayName() + " " + stageMessage() + '.');
    }

    private String displayName() {
        return switch (stageStat) {
            case ATTACK -> "Attack";
            case DEFENSE -> "Defense";
            case SPECIAL_ATTACK -> "Special Attack";
            case SPECIAL_DEFENSE -> "Special Defense";
            case SPEED -> "Speed";
        };
    }

    private String stageMessage() {
        if (stageDelta > 0) {
            return stageDelta >= SHARP_STAGE ? "rose sharply" : "rose";
        }

        int loweredBy = Math.abs(stageDelta);
        return loweredBy >= SHARP_STAGE ? "fell harshly" : "fell";
    }
}
