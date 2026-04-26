package pocketbattles.strategy;

import pocketbattles.move.Move;

public interface AccuracyPolicy {
    boolean hits(Move move);
}
