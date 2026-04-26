package pocketbattles.model;

import pocketbattles.move.Move;

import java.util.ArrayList;
import java.util.List;

public class PokemonBuilder {
    private String name;
    private int level = 50;
    private final List<Type> types = new ArrayList<>();
    private Stats stats;
    private final List<Move> moves = new ArrayList<>();

    public PokemonBuilder named(String name) {
        this.name = name;
        return this;
    }

    public PokemonBuilder level(int level) {
        this.level = level;
        return this;
    }

    public PokemonBuilder type(Type type) {
        types.add(type);
        return this;
    }

    public PokemonBuilder stats(int hp, int attack, int defense, int specialAttack, int specialDefense, int speed) {
        this.stats = new Stats(hp, attack, defense, specialAttack, specialDefense, speed);
        return this;
    }

    public PokemonBuilder move(Move move) {
        moves.add(move);
        return this;
    }

    public Pokemon build() {
        if (stats == null) {
            throw new IllegalStateException("Stats must be set before building a Pokemon.");
        }
        return new Pokemon(name, level, types, stats, moves);
    }
}
