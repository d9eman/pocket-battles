package pocketbattles.factory;

import pocketbattles.model.DamageClass;
import pocketbattles.model.Type;
import pocketbattles.move.DamagingMove;
import pocketbattles.move.Move;
import pocketbattles.move.ProtectEffect;
import pocketbattles.move.RaiseAttackEffect;
import pocketbattles.move.RaiseSpecialAttackEffect;
import pocketbattles.move.StatusMove;

public class MoveFactory {
    public static Move tackle() {
        return new DamagingMove("Tackle", Type.NORMAL, 40, 100, 0, DamageClass.PHYSICAL);
    }

    public static Move bodySlam() {
        return new DamagingMove("Body Slam", Type.NORMAL, 85, 100, 0, DamageClass.PHYSICAL);
    }

    public static Move flamethrower() {
        return new DamagingMove("Flamethrower", Type.FIRE, 90, 100, 0, DamageClass.SPECIAL);
    }

    public static Move firePunch() {
        return new DamagingMove("Fire Punch", Type.FIRE, 75, 100, 0, DamageClass.PHYSICAL);
    }

    public static Move surf() {
        return new DamagingMove("Surf", Type.WATER, 90, 100, 0, DamageClass.SPECIAL);
    }

    public static Move hydroPump() {
        return new DamagingMove("Hydro Pump", Type.WATER, 110, 80, 0, DamageClass.SPECIAL);
    }

    public static Move energyBall() {
        return new DamagingMove("Energy Ball", Type.GRASS, 90, 100, 0, DamageClass.SPECIAL);
    }

    public static Move vineWhip() {
        return new DamagingMove("Vine Whip", Type.GRASS, 45, 100, 0, DamageClass.PHYSICAL);
    }

    public static Move thunderbolt() {
        return new DamagingMove("Thunderbolt", Type.ELECTRIC, 90, 100, 0, DamageClass.SPECIAL);
    }

    public static Move thunderShock() {
        return new DamagingMove("Thunder Shock", Type.ELECTRIC, 40, 100, 0, DamageClass.SPECIAL);
    }

    public static Move earthquake() {
        return new DamagingMove("Earthquake", Type.GROUND, 100, 100, 0, DamageClass.PHYSICAL);
    }

    public static Move rockSlide() {
        return new DamagingMove("Rock Slide", Type.ROCK, 75, 90, 0, DamageClass.PHYSICAL);
    }

    public static Move ironTail() {
        return new DamagingMove("Iron Tail", Type.STEEL, 100, 75, 0, DamageClass.PHYSICAL);
    }

    public static Move shadowBall() {
        return new DamagingMove("Shadow Ball", Type.GHOST, 80, 100, 0, DamageClass.SPECIAL);
    }

    public static Move auraSphere() {
        return new DamagingMove("Aura Sphere", Type.FIGHTING, 80, 100, 0, DamageClass.SPECIAL);
    }

    public static Move dragonClaw() {
        return new DamagingMove("Dragon Claw", Type.DRAGON, 80, 100, 0, DamageClass.PHYSICAL);
    }

    public static Move iceBeam() {
        return new DamagingMove("Ice Beam", Type.ICE, 90, 100, 0, DamageClass.SPECIAL);
    }

    public static Move moonblast() {
        return new DamagingMove("Moonblast", Type.FAIRY, 95, 100, 0, DamageClass.SPECIAL);
    }

    public static Move protect() {
        return new StatusMove("Protect", Type.NORMAL, 100, 4, new ProtectEffect());
    }

    public static Move swordsDance() {
        return new StatusMove("Swords Dance", Type.NORMAL, 100, 0, new RaiseAttackEffect(2));
    }

    public static Move calmMind() {
        return new StatusMove("Calm Mind", Type.NORMAL, 100, 0, new RaiseSpecialAttackEffect(1));
    }
}
