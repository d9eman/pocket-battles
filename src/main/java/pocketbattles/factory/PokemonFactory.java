package pocketbattles.factory;

import pocketbattles.model.Pokemon;
import pocketbattles.model.PokemonBuilder;
import pocketbattles.model.Type;

import java.util.List;

public class PokemonFactory {
    public static List<Pokemon> demoPlayerTeam() {
        return List.of(charizard(), pikachu(), snorlax());
    }

    public static List<Pokemon> demoOpponentTeam() {
        return List.of(blastoise(), venusaur(), garchomp());
    }

    public static Pokemon charizard() {
        return new PokemonBuilder()
                .named("Charizard")
                .type(Type.FIRE)
                .type(Type.FLYING)
                .stats(153, 84, 78, 109, 85, 100)
                .move(MoveFactory.flamethrower())
                .move(MoveFactory.firePunch())
                .move(MoveFactory.dragonClaw())
                .move(MoveFactory.protect())
                .build();
    }

    public static Pokemon blastoise() {
        return new PokemonBuilder()
                .named("Blastoise")
                .type(Type.WATER)
                .stats(154, 83, 100, 85, 105, 78)
                .move(MoveFactory.surf())
                .move(MoveFactory.hydroPump())
                .move(MoveFactory.iceBeam())
                .move(MoveFactory.protect())
                .build();
    }

    public static Pokemon venusaur() {
        return new PokemonBuilder()
                .named("Venusaur")
                .type(Type.GRASS)
                .type(Type.POISON)
                .stats(155, 82, 83, 100, 100, 80)
                .move(MoveFactory.energyBall())
                .move(MoveFactory.vineWhip())
                .move(MoveFactory.calmMind())
                .move(MoveFactory.protect())
                .build();
    }

    public static Pokemon pikachu() {
        return new PokemonBuilder()
                .named("Pikachu")
                .type(Type.ELECTRIC)
                .stats(110, 55, 40, 75, 50, 110)
                .move(MoveFactory.thunderbolt())
                .move(MoveFactory.thunderShock())
                .move(MoveFactory.ironTail())
                .move(MoveFactory.protect())
                .build();
    }

    public static Pokemon garchomp() {
        return new PokemonBuilder()
                .named("Garchomp")
                .type(Type.DRAGON)
                .type(Type.GROUND)
                .stats(183, 130, 95, 80, 85, 102)
                .move(MoveFactory.earthquake())
                .move(MoveFactory.dragonClaw())
                .move(MoveFactory.rockSlide())
                .move(MoveFactory.swordsDance())
                .build();
    }

    public static Pokemon gengar() {
        return new PokemonBuilder()
                .named("Gengar")
                .type(Type.GHOST)
                .type(Type.POISON)
                .stats(135, 65, 60, 130, 75, 110)
                .move(MoveFactory.shadowBall())
                .move(MoveFactory.thunderbolt())
                .move(MoveFactory.calmMind())
                .move(MoveFactory.protect())
                .build();
    }

    public static Pokemon lucario() {
        return new PokemonBuilder()
                .named("Lucario")
                .type(Type.FIGHTING)
                .type(Type.STEEL)
                .stats(145, 110, 70, 115, 70, 90)
                .move(MoveFactory.auraSphere())
                .move(MoveFactory.firePunch())
                .move(MoveFactory.swordsDance())
                .move(MoveFactory.protect())
                .build();
    }

    public static Pokemon dragonite() {
        return new PokemonBuilder()
                .named("Dragonite")
                .type(Type.DRAGON)
                .type(Type.FLYING)
                .stats(166, 134, 95, 100, 100, 80)
                .move(MoveFactory.dragonClaw())
                .move(MoveFactory.firePunch())
                .move(MoveFactory.swordsDance())
                .move(MoveFactory.protect())
                .build();
    }

    public static Pokemon snorlax() {
        return new PokemonBuilder()
                .named("Snorlax")
                .type(Type.NORMAL)
                .stats(235, 110, 65, 65, 110, 30)
                .move(MoveFactory.bodySlam())
                .move(MoveFactory.earthquake())
                .move(MoveFactory.swordsDance())
                .move(MoveFactory.protect())
                .build();
    }

    public static Pokemon sylveon() {
        return new PokemonBuilder()
                .named("Sylveon")
                .type(Type.FAIRY)
                .stats(170, 65, 65, 110, 130, 60)
                .move(MoveFactory.moonblast())
                .move(MoveFactory.calmMind())
                .move(MoveFactory.protect())
                .move(MoveFactory.tackle())
                .build();
    }
}
