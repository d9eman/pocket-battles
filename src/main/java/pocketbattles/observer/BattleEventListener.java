package pocketbattles.observer;

import pocketbattles.battle.BattleEvent;

public interface BattleEventListener {
    void onEvent(BattleEvent event);
}
