package pocketbattles.model;

public record Stats(
        int hp,
        int attack,
        int defense,
        int specialAttack,
        int specialDefense,
        int speed
) {
    public Stats {
        if (hp <= 0 || attack <= 0 || defense <= 0 || specialAttack <= 0 || specialDefense <= 0 || speed <= 0) {
            throw new IllegalArgumentException("All stats must be positive.");
        }
    }
}
