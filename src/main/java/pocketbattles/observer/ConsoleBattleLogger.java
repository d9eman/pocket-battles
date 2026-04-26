package pocketbattles.observer;

import pocketbattles.battle.BattleEvent;

public class ConsoleBattleLogger implements BattleEventListener {
    @Override
    public void onEvent(BattleEvent event) {
        System.out.println(event.message());
    }
}
