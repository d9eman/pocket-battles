package pocketbattles.strategy;

public interface RandomSource {
    double nextDouble();
    boolean nextBoolean();
    int nextInt(int bound);
}
