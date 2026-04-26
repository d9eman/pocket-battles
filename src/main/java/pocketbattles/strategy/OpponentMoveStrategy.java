package pocketbattles.strategy;

import pocketbattles.action.BattleAction;
import pocketbattles.model.Trainer;

public interface OpponentMoveStrategy {
    BattleAction chooseAction(Trainer self, Trainer opponent);
}
