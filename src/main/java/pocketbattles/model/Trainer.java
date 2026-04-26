package pocketbattles.model;

import java.util.ArrayList;
import java.util.List;

public class Trainer {
    private final String name;
    private final List<Pokemon> team;
    private int activeIndex = 0;

    public Trainer(String name, List<Pokemon> team) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Trainer name is required.");
        }
        if (team == null || team.isEmpty()) {
            throw new IllegalArgumentException("Trainer must have at least one Pokemon.");
        }
        this.name = name;
        this.team = new ArrayList<>(team);
    }

    public String getName() {
        return name;
    }

    public List<Pokemon> getTeam() {
        return List.copyOf(team);
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public Pokemon getActivePokemon() {
        return team.get(activeIndex);
    }

    public void switchTo(int index) {
        if (index < 0 || index >= team.size()) {
            throw new IllegalArgumentException("Invalid switch index.");
        }
        if (index == activeIndex) {
            throw new IllegalArgumentException("That Pokemon is already active.");
        }
        if (team.get(index).isFainted()) {
            throw new IllegalArgumentException("Cannot switch to a fainted Pokemon.");
        }
        activeIndex = index;
    }

    public boolean hasRemainingPokemon() {
        return team.stream().anyMatch(pokemon -> !pokemon.isFainted());
    }

    public int firstHealthyBenchIndex() {
        for (int i = 0; i < team.size(); i++) {
            if (i != activeIndex && !team.get(i).isFainted()) {
                return i;
            }
        }
        return -1;
    }
}
