package pocketbattles.factory;

import pocketbattles.model.Pokemon;
import pocketbattles.model.PokemonBuilder;
import pocketbattles.model.Type;

import pocketbattles.strategy.RandomSource;

import java.util.ArrayList;

import java.util.List;

public final class PokemonFactory {
    private static final int DEMO_LEVEL = 50;

    private PokemonFactory() {
    }

    public static List<Pokemon> demoPlayerTeam() {
        return List.of(charizard(), lucario(), starmie(), tyranitar(), scizor(), gardevoir());
    }

    public static List<Pokemon> demoOpponentTeam() {
        return List.of(blastoise(), venusaur(), garchomp(), gengar(), jolteon(), mamoswine());
    }

    public static List<Pokemon> randomTeam(RandomSource random, int teamSize) {
        List<Pokemon> availablePokemon = new ArrayList<>(allPokemon());
        List<Pokemon> team = new ArrayList<>();

        while (team.size() < teamSize && !availablePokemon.isEmpty()) {
            int index = random.nextInt(availablePokemon.size());
            team.add(availablePokemon.remove(index));
        }

        return team;
    }

    public static List<Pokemon> allPokemon() {
        return List.of(
                charizard(), blastoise(), venusaur(), pikachu(), garchomp(), gengar(),
                lucario(), dragonite(), snorlax(), gardevoir(), alakazam(), tyranitar(),
                scizor(), mamoswine(), heracross(), jolteon(), starmie(), arcanine(),
                togekiss(), absol()
        );
    }

    public static Pokemon charizard() {
        return builder("Charizard")
                .type(Type.FIRE)
                .type(Type.FLYING)
                .stats(153, 84, 78, 109, 85, 100)
                .move(MoveFactory.flamethrower())
                .move(MoveFactory.airSlash())
                .move(MoveFactory.dragonPulse())
                .move(MoveFactory.scaryFace())
                .build();
    }

    public static Pokemon blastoise() {
        return builder("Blastoise")
                .type(Type.WATER)
                .stats(154, 83, 100, 85, 105, 78)
                .move(MoveFactory.surf())
                .move(MoveFactory.iceBeam())
                .move(MoveFactory.darkPulse())
                .move(MoveFactory.ironDefense())
                .build();
    }

    public static Pokemon venusaur() {
        return builder("Venusaur")
                .type(Type.GRASS)
                .type(Type.POISON)
                .stats(155, 82, 83, 100, 100, 80)
                .move(MoveFactory.energyBall())
                .move(MoveFactory.sludgeBomb())
                .move(MoveFactory.earthquake())
                .move(MoveFactory.growl())
                .build();
    }

    public static Pokemon pikachu() {
        return builder("Pikachu")
                .type(Type.ELECTRIC)
                .stats(110, 55, 40, 75, 50, 110)
                .move(MoveFactory.thunderbolt())
                .move(MoveFactory.ironTail())
                .move(MoveFactory.agility())
                .move(MoveFactory.tailWhip())
                .build();
    }

    public static Pokemon garchomp() {
        return builder("Garchomp")
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
        return builder("Gengar")
                .type(Type.GHOST)
                .type(Type.POISON)
                .stats(135, 65, 60, 130, 75, 110)
                .move(MoveFactory.shadowBall())
                .move(MoveFactory.sludgeBomb())
                .move(MoveFactory.thunderbolt())
                .move(MoveFactory.nastyPlot())
                .build();
    }

    public static Pokemon lucario() {
        return builder("Lucario")
                .type(Type.FIGHTING)
                .type(Type.STEEL)
                .stats(145, 110, 70, 115, 70, 90)
                .move(MoveFactory.closeCombat())
                .move(MoveFactory.flashCannon())
                .move(MoveFactory.extremeSpeed())
                .move(MoveFactory.calmMind())
                .build();
    }

    public static Pokemon dragonite() {
        return builder("Dragonite")
                .type(Type.DRAGON)
                .type(Type.FLYING)
                .stats(166, 134, 95, 100, 100, 80)
                .move(MoveFactory.dragonClaw())
                .move(MoveFactory.firePunch())
                .move(MoveFactory.aerialAce())
                .move(MoveFactory.agility())
                .build();
    }

    public static Pokemon snorlax() {
        return builder("Snorlax")
                .type(Type.NORMAL)
                .stats(235, 110, 65, 65, 110, 30)
                .move(MoveFactory.bodySlam())
                .move(MoveFactory.earthquake())
                .move(MoveFactory.crunch())
                .move(MoveFactory.screech())
                .build();
    }

    public static Pokemon gardevoir() {
        return builder("Gardevoir")
                .type(Type.FAIRY)
                .stats(68, 65, 65, 125, 115, 80)
                .move(MoveFactory.moonblast())
                .move(MoveFactory.psychic())
                .move(MoveFactory.calmMind())
                .move(MoveFactory.protect())
                .build();
    }

    public static Pokemon alakazam() {
        return builder("Alakazam")
                .type(Type.PSYCHIC)
                .stats(130, 50, 45, 135, 95, 120)
                .move(MoveFactory.psychic())
                .move(MoveFactory.shadowBall())
                .move(MoveFactory.calmMind())
                .move(MoveFactory.scaryFace())
                .build();
    }

    public static Pokemon tyranitar() {
        return builder("Tyranitar")
                .type(Type.ROCK)
                .type(Type.DARK)
                .stats(175, 134, 110, 95, 100, 61)
                .move(MoveFactory.stoneEdge())
                .move(MoveFactory.crunch())
                .move(MoveFactory.earthquake())
                .move(MoveFactory.ironDefense())
                .build();
    }

    public static Pokemon scizor() {
        return builder("Scizor")
                .type(Type.BUG)
                .type(Type.STEEL)
                .stats(145, 130, 100, 55, 80, 65)
                .move(MoveFactory.xScissor())
                .move(MoveFactory.ironTail())
                .move(MoveFactory.aerialAce())
                .move(MoveFactory.swordsDance())
                .build();
    }

    public static Pokemon mamoswine() {
        return builder("Mamoswine")
                .type(Type.ICE)
                .type(Type.GROUND)
                .stats(185, 130, 80, 70, 60, 80)
                .move(MoveFactory.earthquake())
                .move(MoveFactory.iceBeam())
                .move(MoveFactory.iceShard())
                .move(MoveFactory.scaryFace())
                .build();
    }

    public static Pokemon heracross() {
        return builder("Heracross")
                .type(Type.BUG)
                .type(Type.FIGHTING)
                .stats(155, 125, 75, 40, 95, 85)
                .move(MoveFactory.closeCombat())
                .move(MoveFactory.xScissor())
                .move(MoveFactory.rockSlide())
                .move(MoveFactory.swordsDance())
                .build();
    }

    public static Pokemon jolteon() {
        return builder("Jolteon")
                .type(Type.ELECTRIC)
                .stats(140, 65, 60, 110, 95, 130)
                .move(MoveFactory.thunderbolt())
                .move(MoveFactory.shadowBall())
                .move(MoveFactory.agility())
                .move(MoveFactory.scaryFace())
                .build();
    }

    public static Pokemon starmie() {
        return builder("Starmie")
                .type(Type.WATER)
                .type(Type.PSYCHIC)
                .stats(135, 75, 85, 100, 85, 115)
                .move(MoveFactory.surf())
                .move(MoveFactory.psychic())
                .move(MoveFactory.iceBeam())
                .move(MoveFactory.calmMind())
                .build();
    }

    public static Pokemon arcanine() {
        return builder("Arcanine")
                .type(Type.FIRE)
                .stats(165, 110, 80, 100, 80, 95)
                .move(MoveFactory.flamethrower())
                .move(MoveFactory.crunch())
                .move(MoveFactory.closeCombat())
                .move(MoveFactory.agility())
                .build();
    }

    public static Pokemon togekiss() {
        return builder("Togekiss")
                .type(Type.FAIRY)
                .type(Type.FLYING)
                .stats(160, 50, 95, 120, 115, 80)
                .move(MoveFactory.airSlash())
                .move(MoveFactory.dazzlingGleam())
                .move(MoveFactory.calmMind())
                .move(MoveFactory.thunderbolt())
                .build();
    }

    public static Pokemon absol() {
        return builder("Absol")
                .type(Type.DARK)
                .stats(140, 130, 60, 75, 60, 75)
                .move(MoveFactory.crunch())
                .move(MoveFactory.shadowClaw())
                .move(MoveFactory.swordsDance())
                .move(MoveFactory.scaryFace())
                .build();
    }

    private static PokemonBuilder builder(String name) {
        return new PokemonBuilder().named(name).level(DEMO_LEVEL);
    }
}
