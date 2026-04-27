package pocketbattles.model;

import java.util.ArrayList;
import java.util.List;

public class Trainer {
    private final String name;
    private final List<Pokemon> team;
    private int activeIndex;

    public Trainer(String name, List<Pokemon> team) {
        validateName(name);
        validateTeam(team);
        this.name = name;
        this.team = new ArrayList<>(team);
    }

    private void validateName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Trainer name is required.");
        }
    }

    private void validateTeam(List<Pokemon> value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Trainer must have at least one Pokemon.");
        }
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
        validateSwitchIndex(index);
        activeIndex = index;
    }

    public boolean hasRemainingPokemon() {
        return team.stream().anyMatch(pokemon -> !pokemon.isFainted());
    }

    public List<Integer> switchableIndexes() {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < team.size(); i++) {
            Pokemon pokemon = team.get(i);
            if (i != activeIndex && !pokemon.isFainted()) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    public int firstHealthyBenchIndex() {
        List<Integer> switchable = switchableIndexes();
        return switchable.isEmpty() ? -1 : switchable.get(0);
    }

    private void validateSwitchIndex(int index) {
        if (index < 0 || index >= team.size()) {
            throw new IllegalArgumentException("Invalid switch index.");
        }
        if (index == activeIndex) {
            throw new IllegalArgumentException("That Pokemon is already active.");
        }
        if (team.get(index).isFainted()) {
            throw new IllegalArgumentException("Cannot switch to a fainted Pokemon.");
        }
    }
}
