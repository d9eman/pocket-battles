package pocketbattles.observer;

import pocketbattles.battle.BattleEvent;

import java.util.ArrayList;
import java.util.List;

public class BattleEventPublisher {
    private final List<BattleEventListener> listeners = new ArrayList<>();

    public void subscribe(BattleEventListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(BattleEventListener listener) {
        listeners.remove(listener);
    }

    public void publish(BattleEvent event) {
        for (BattleEventListener listener : List.copyOf(listeners)) {
            listener.onEvent(event);
        }
    }
}
