package pocketbattles.model;

import pocketbattles.move.Move;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Pokemon {
    private static final int MIN_STAGE = -6;
    private static final int MAX_STAGE = 6;
    private static final int BASE_STAGE_NUMERATOR = 2;

    private final String name;
    private final int level;
    private final List<Type> types;
    private final Stats baseStats;
    private final List<Move> moves;
    private final Map<StageStat, Integer> statStages = new EnumMap<>(StageStat.class);

    private int currentHp;
    private boolean protectedThisTurn;

    public Pokemon(String name, int level, List<Type> types, Stats baseStats, List<Move> moves) {
        validateName(name);
        validateLevel(level);
        validateTypes(types);
        validateMoves(moves);

        this.name = name;
        this.level = level;
        this.types = new ArrayList<>(types);
        this.baseStats = baseStats;
        this.moves = new ArrayList<>(moves);
        this.currentHp = baseStats.hp();
        initializeStages();
    }

    private void validateName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Pokemon name is required.");
        }
    }

    private void validateLevel(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Level must be positive.");
        }
    }

    private void validateTypes(List<Type> value) {
        if (value == null || value.isEmpty() || value.size() > 2) {
            throw new IllegalArgumentException("Pokemon must have one or two types.");
        }
    }

    private void validateMoves(List<Move> value) {
        if (value == null || value.isEmpty() || value.size() > 4) {
            throw new IllegalArgumentException("Pokemon must have one to four moves.");
        }
    }

    private void initializeStages() {
        for (StageStat stageStat : StageStat.values()) {
            statStages.put(stageStat, 0);
        }
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

    public Type getPrimaryType() {
        return types.get(0);
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

        int safeDamage = Math.max(0, damage);
        currentHp = Math.max(0, currentHp - safeDamage);
    }

    public void heal(int amount) {
        int safeAmount = Math.max(0, amount);
        currentHp = Math.min(baseStats.hp(), currentHp + safeAmount);
    }

    public void resetTurnFlags() {
        protectedThisTurn = false;
    }

    public void setProtectedThisTurn(boolean value) {
        protectedThisTurn = value;
    }

    public boolean isProtectedThisTurn() {
        return protectedThisTurn;
    }

    public int getEffectiveAttack() {
        return effectiveStat(StageStat.ATTACK);
    }

    public int getEffectiveDefense() {
        return effectiveStat(StageStat.DEFENSE);
    }

    public int getEffectiveSpecialAttack() {
        return effectiveStat(StageStat.SPECIAL_ATTACK);
    }

    public int getEffectiveSpecialDefense() {
        return effectiveStat(StageStat.SPECIAL_DEFENSE);
    }

    public int getEffectiveSpeed() {
        return effectiveStat(StageStat.SPEED);
    }

    public void changeStage(StageStat stageStat, int delta) {
        int nextStage = statStages.get(stageStat) + delta;
        statStages.put(stageStat, clampStage(nextStage));
    }

    public String getSpriteKey() {
        return name.toLowerCase()
                .replace("♀", "f")
                .replace("♂", "m")
                .replace(".", "")
                .replace("'", "")
                .replace(" ", "-");
    }

    private int effectiveStat(StageStat stageStat) {
        int stage = statStages.get(stageStat);
        int stat = switch (stageStat) {
            case ATTACK -> baseStats.attack();
            case DEFENSE -> baseStats.defense();
            case SPECIAL_ATTACK -> baseStats.specialAttack();
            case SPECIAL_DEFENSE -> baseStats.specialDefense();
            case SPEED -> baseStats.speed();
        };
        return applyStage(stat, stage);
    }

    private int clampStage(int stage) {
        return Math.max(MIN_STAGE, Math.min(MAX_STAGE, stage));
    }

    private int applyStage(int stat, int stage) {
        if (stage >= 0) {
            return stat * (BASE_STAGE_NUMERATOR + stage) / BASE_STAGE_NUMERATOR;
        }

        return stat * BASE_STAGE_NUMERATOR / (BASE_STAGE_NUMERATOR - stage);
    }
}
