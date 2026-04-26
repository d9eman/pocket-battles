package pocketbattles.model;

import pocketbattles.move.Move;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    private final String name;
    private final int level;
    private final List<Type> types;
    private final Stats baseStats;
    private final List<Move> moves;

    private int currentHp;
    private int attackStage = 0;
    private int defenseStage = 0;
    private int specialAttackStage = 0;
    private int specialDefenseStage = 0;
    private int speedStage = 0;
    private boolean protectedThisTurn = false;

    public Pokemon(String name, int level, List<Type> types, Stats baseStats, List<Move> moves) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Pokemon name is required.");
        }
        if (level <= 0) {
            throw new IllegalArgumentException("Level must be positive.");
        }
        if (types == null || types.isEmpty() || types.size() > 2) {
            throw new IllegalArgumentException("Pokemon must have one or two types.");
        }
        if (moves == null || moves.isEmpty() || moves.size() > 4) {
            throw new IllegalArgumentException("Pokemon must have one to four moves.");
        }

        this.name = name;
        this.level = level;
        this.types = new ArrayList<>(types);
        this.baseStats = baseStats;
        this.moves = new ArrayList<>(moves);
        this.currentHp = baseStats.hp();
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public List<Type> getTypes() {
        return List.copyOf(types);
    }

    public List<Move> getMoves() {
        return List.copyOf(moves);
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getMaxHp() {
        return baseStats.hp();
    }

    public double getHpPercent() {
        return currentHp / (double) getMaxHp();
    }

    public boolean isFainted() {
        return currentHp <= 0;
    }

    public void takeDamage(int damage) {
        if (protectedThisTurn) {
            return;
        }
        currentHp = Math.max(0, currentHp - Math.max(0, damage));
    }

    public void heal(int amount) {
        currentHp = Math.min(baseStats.hp(), currentHp + Math.max(0, amount));
    }

    public void resetTurnFlags() {
        protectedThisTurn = false;
    }

    public void setProtectedThisTurn(boolean protectedThisTurn) {
        this.protectedThisTurn = protectedThisTurn;
    }

    public boolean isProtectedThisTurn() {
        return protectedThisTurn;
    }

    public int getEffectiveAttack() {
        return applyStage(baseStats.attack(), attackStage);
    }

    public int getEffectiveDefense() {
        return applyStage(baseStats.defense(), defenseStage);
    }

    public int getEffectiveSpecialAttack() {
        return applyStage(baseStats.specialAttack(), specialAttackStage);
    }

    public int getEffectiveSpecialDefense() {
        return applyStage(baseStats.specialDefense(), specialDefenseStage);
    }

    public int getEffectiveSpeed() {
        return applyStage(baseStats.speed(), speedStage);
    }

    public void changeAttackStage(int delta) {
        attackStage = clampStage(attackStage + delta);
    }

    public void changeDefenseStage(int delta) {
        defenseStage = clampStage(defenseStage + delta);
    }

    public void changeSpecialAttackStage(int delta) {
        specialAttackStage = clampStage(specialAttackStage + delta);
    }

    public void changeSpecialDefenseStage(int delta) {
        specialDefenseStage = clampStage(specialDefenseStage + delta);
    }

    public void changeSpeedStage(int delta) {
        speedStage = clampStage(speedStage + delta);
    }

    private int clampStage(int stage) {
        return Math.max(-6, Math.min(6, stage));
    }

    private int applyStage(int stat, int stage) {
        if (stage >= 0) {
            return stat * (2 + stage) / 2;
        }
        return stat * 2 / (2 - stage);
    }
}
