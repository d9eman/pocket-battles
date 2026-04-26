package pocketbattles.observer;

import pocketbattles.battle.BattleEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileBattleLogger implements BattleEventListener {
    private final Path path;

    public FileBattleLogger(Path path) {
        this.path = path;
    }

    @Override
    public void onEvent(BattleEvent event) {
        try {
            Files.writeString(
                    path,
                    event.message() + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException("Could not write battle log", e);
        }
    }
}
