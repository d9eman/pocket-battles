package pocketbattles.observer;

import pocketbattles.battle.BattleEvent;

import java.util.logging.Logger;

public class ConsoleBattleLogger implements BattleEventListener {
    private static final Logger LOGGER = Logger.getLogger(ConsoleBattleLogger.class.getName());

    @Override
    public void onEvent(BattleEvent event) {
        LOGGER.info(event.message());
    }
}
