package pocketbattles.action;

import pocketbattles.battle.BattleContext;
import pocketbattles.model.Trainer;

public interface BattleAction {
    int priority();
    void execute(BattleContext context, Trainer self, Trainer opponent);
}
