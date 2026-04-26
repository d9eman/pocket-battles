package pocketbattles.strategy;

import java.util.Random;

public class JavaRandomSource implements RandomSource {
    private final Random random = new Random();

    @Override
    public double nextDouble() {
        return random.nextDouble();
    }

    @Override
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
