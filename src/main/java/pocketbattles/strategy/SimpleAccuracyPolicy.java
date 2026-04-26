package pocketbattles.strategy;

import pocketbattles.move.Move;

public class SimpleAccuracyPolicy implements AccuracyPolicy {
    private final RandomSource random;

    public SimpleAccuracyPolicy(RandomSource random) {
        this.random = random;
    }

    @Override
    public boolean hits(Move move) {
        return random.nextDouble() * 100 < move.getAccuracy();
    }
}
