package pocketbattles.factory;

import pocketbattles.model.DamageClass;
import pocketbattles.model.StageStat;
import pocketbattles.model.Type;
import pocketbattles.move.ChangeStageEffect;
import pocketbattles.move.CompositeEffect;
import pocketbattles.move.DamagingMove;
import pocketbattles.move.EffectTarget;
import pocketbattles.move.Move;
import pocketbattles.move.MoveEffect;
import pocketbattles.move.ProtectEffect;
import pocketbattles.move.StatusMove;

import java.util.List;

public final class MoveFactory {
    private static final int ALWAYS_HITS = 100;
    private static final int DEFAULT_PRIORITY = 0;
    private static final int PROTECT_PRIORITY = 4;

    private MoveFactory() {
    }

    public static Move tackle() {
        return physical("Tackle", Type.NORMAL, 40, ALWAYS_HITS);
    }

    public static Move bodySlam() {
        return physical("Body Slam", Type.NORMAL, 85, ALWAYS_HITS);
    }

    public static Move extremeSpeed() {
        return new DamagingMove("Extreme Speed", Type.NORMAL, 80, ALWAYS_HITS, 2, DamageClass.PHYSICAL);
    }

    public static Move flamethrower() {
        return special("Flamethrower", Type.FIRE, 90, ALWAYS_HITS);
    }

    public static Move fireBlast() {
        return special("Fire Blast", Type.FIRE, 110, 85);
    }

    public static Move firePunch() {
        return physical("Fire Punch", Type.FIRE, 75, ALWAYS_HITS);
    }

    public static Move surf() {
        return special("Surf", Type.WATER, 90, ALWAYS_HITS);
    }

    public static Move hydroPump() {
        return special("Hydro Pump", Type.WATER, 110, 80);
    }

    public static Move energyBall() {
        return special("Energy Ball", Type.GRASS, 90, ALWAYS_HITS);
    }

    public static Move gigaDrain() {
        return special("Giga Drain", Type.GRASS, 75, ALWAYS_HITS);
    }

    public static Move vineWhip() {
        return physical("Vine Whip", Type.GRASS, 45, ALWAYS_HITS);
    }

    public static Move thunderbolt() {
        return special("Thunderbolt", Type.ELECTRIC, 90, ALWAYS_HITS);
    }

    public static Move thunderShock() {
        return special("Thunder Shock", Type.ELECTRIC, 40, ALWAYS_HITS);
    }

    public static Move iceBeam() {
        return special("Ice Beam", Type.ICE, 90, ALWAYS_HITS);
    }

    public static Move iceShard() {
        return new DamagingMove("Ice Shard", Type.ICE, 40, ALWAYS_HITS, 1, DamageClass.PHYSICAL);
    }

    public static Move closeCombat() {
        return physical("Close Combat", Type.FIGHTING, 120, ALWAYS_HITS);
    }

    public static Move auraSphere() {
        return special("Aura Sphere", Type.FIGHTING, 80, ALWAYS_HITS);
    }

    public static Move poisonJab() {
        return physical("Poison Jab", Type.POISON, 80, ALWAYS_HITS);
    }

    public static Move sludgeBomb() {
        return special("Sludge Bomb", Type.POISON, 90, ALWAYS_HITS);
    }

    public static Move earthquake() {
        return physical("Earthquake", Type.GROUND, 100, ALWAYS_HITS);
    }

    public static Move aerialAce() {
        return physical("Aerial Ace", Type.FLYING, 60, ALWAYS_HITS);
    }

    public static Move airSlash() {
        return special("Air Slash", Type.FLYING, 75, 95);
    }

    public static Move psychic() {
        return special("Psychic", Type.PSYCHIC, 90, ALWAYS_HITS);
    }

    public static Move psybeam() {
        return special("Psybeam", Type.PSYCHIC, 65, ALWAYS_HITS);
    }

    public static Move xScissor() {
        return physical("X-Scissor", Type.BUG, 80, ALWAYS_HITS);
    }

    public static Move bugBuzz() {
        return special("Bug Buzz", Type.BUG, 90, ALWAYS_HITS);
    }

    public static Move rockSlide() {
        return physical("Rock Slide", Type.ROCK, 75, 90);
    }

    public static Move stoneEdge() {
        return physical("Stone Edge", Type.ROCK, 100, 80);
    }

    public static Move shadowBall() {
        return special("Shadow Ball", Type.GHOST, 80, ALWAYS_HITS);
    }

    public static Move shadowClaw() {
        return physical("Shadow Claw", Type.GHOST, 70, ALWAYS_HITS);
    }

    public static Move dragonClaw() {
        return physical("Dragon Claw", Type.DRAGON, 80, ALWAYS_HITS);
    }

    public static Move dragonPulse() {
        return special("Dragon Pulse", Type.DRAGON, 85, ALWAYS_HITS);
    }

    public static Move crunch() {
        return physical("Crunch", Type.DARK, 80, ALWAYS_HITS);
    }

    public static Move darkPulse() {
        return special("Dark Pulse", Type.DARK, 80, ALWAYS_HITS);
    }

    public static Move ironTail() {
        return physical("Iron Tail", Type.STEEL, 100, 75);
    }

    public static Move flashCannon() {
        return special("Flash Cannon", Type.STEEL, 80, ALWAYS_HITS);
    }

    public static Move moonblast() {
        return special("Moonblast", Type.FAIRY, 95, ALWAYS_HITS);
    }

    public static Move dazzlingGleam() {
        return special("Dazzling Gleam", Type.FAIRY, 80, ALWAYS_HITS);
    }

    public static Move protect() {
        return status("Protect", Type.NORMAL, ALWAYS_HITS, PROTECT_PRIORITY, new ProtectEffect());
    }

    public static Move swordsDance() {
        return status("Swords Dance", Type.NORMAL, ALWAYS_HITS, DEFAULT_PRIORITY,
                selfStage(StageStat.ATTACK, 2));
    }

    public static Move calmMind() {
        return status("Calm Mind", Type.PSYCHIC, ALWAYS_HITS, DEFAULT_PRIORITY,
                new CompositeEffect(List.of(
                        selfStage(StageStat.SPECIAL_ATTACK, 1),
                        selfStage(StageStat.SPECIAL_DEFENSE, 1)
                )));
    }

    public static Move ironDefense() {
        return status("Iron Defense", Type.STEEL, ALWAYS_HITS, DEFAULT_PRIORITY,
                selfStage(StageStat.DEFENSE, 2));
    }

    public static Move agility() {
        return status("Agility", Type.PSYCHIC, ALWAYS_HITS, DEFAULT_PRIORITY,
                selfStage(StageStat.SPEED, 2));
    }

    public static Move nastyPlot() {
        return status("Nasty Plot", Type.DARK, ALWAYS_HITS, DEFAULT_PRIORITY,
                selfStage(StageStat.SPECIAL_ATTACK, 2));
    }

    public static Move growl() {
        return status("Growl", Type.NORMAL, ALWAYS_HITS, DEFAULT_PRIORITY,
                opponentStage(StageStat.ATTACK, -1));
    }

    public static Move tailWhip() {
        return status("Tail Whip", Type.NORMAL, ALWAYS_HITS, DEFAULT_PRIORITY,
                opponentStage(StageStat.DEFENSE, -1));
    }

    public static Move screech() {
        return status("Screech", Type.NORMAL, 85, DEFAULT_PRIORITY,
                opponentStage(StageStat.DEFENSE, -2));
    }

    public static Move scaryFace() {
        return status("Scary Face", Type.NORMAL, ALWAYS_HITS, DEFAULT_PRIORITY,
                opponentStage(StageStat.SPEED, -2));
    }

    private static Move physical(String name, Type type, int power, int accuracy) {
        return new DamagingMove(name, type, power, accuracy, DEFAULT_PRIORITY, DamageClass.PHYSICAL);
    }

    private static Move special(String name, Type type, int power, int accuracy) {
        return new DamagingMove(name, type, power, accuracy, DEFAULT_PRIORITY, DamageClass.SPECIAL);
    }

    private static Move status(String name, Type type, int accuracy, int priority, MoveEffect effect) {
        return new StatusMove(name, type, accuracy, priority, effect);
    }

    private static MoveEffect selfStage(StageStat stat, int stages) {
        return new ChangeStageEffect(EffectTarget.SELF, stat, stages);
    }

    private static MoveEffect opponentStage(StageStat stat, int stages) {
        return new ChangeStageEffect(EffectTarget.OPPONENT, stat, stages);
    }
}
