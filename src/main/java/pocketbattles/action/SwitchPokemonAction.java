package pocketbattles.action;

import pocketbattles.battle.BattleContext;
import pocketbattles.model.Trainer;

public class SwitchPokemonAction implements BattleAction {
    private final int newIndex;

    public SwitchPokemonAction(int newIndex) {
        this.newIndex = newIndex;
    }

    @Override
    public int priority() {
        return 6;
    }

    @Override
    public void execute(BattleContext context, Trainer self, Trainer opponent) {
        String oldName = self.getActivePokemon().getName();
        self.switchTo(newIndex);
        context.publish(self.getName() + " switched from " + oldName + " to " + self.getActivePokemon().getName() + ".");
    }
}
