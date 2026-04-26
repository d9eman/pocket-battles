package pocketbattles.battle;

import pocketbattles.action.BattleAction;
import pocketbattles.action.UseMoveAction;
import pocketbattles.factory.PokemonFactory;
import pocketbattles.model.Trainer;
import pocketbattles.observer.BattleEventPublisher;
import pocketbattles.observer.ConsoleBattleLogger;
import pocketbattles.strategy.AccuracyPolicy;
import pocketbattles.strategy.DamageCalculator;
import pocketbattles.strategy.JavaRandomSource;
import pocketbattles.strategy.RandomSource;
import pocketbattles.strategy.SimpleAccuracyPolicy;
import pocketbattles.strategy.SimpleDamageCalculator;
import pocketbattles.strategy.SimpleTypeChart;
import pocketbattles.strategy.TypeChart;

public class BattleDemo {
    public static void main(String[] args) {
        RandomSource random = new JavaRandomSource();
        TypeChart typeChart = new SimpleTypeChart();
        DamageCalculator damageCalculator = new SimpleDamageCalculator(typeChart, random);
        AccuracyPolicy accuracyPolicy = new SimpleAccuracyPolicy(random);

        BattleEventPublisher publisher = new BattleEventPublisher();
        publisher.subscribe(new ConsoleBattleLogger());

        BattleContext context = new BattleContext(damageCalculator, accuracyPolicy, publisher);
        BattleEngine engine = new BattleEngine(context, random);

        Trainer player = new Trainer("Player", PokemonFactory.demoPlayerTeam());
        Trainer rival = new Trainer("Rival", PokemonFactory.demoOpponentTeam());

        BattleAction playerAction = new UseMoveAction(player.getActivePokemon().getMoves().get(0));
        BattleAction rivalAction = new UseMoveAction(rival.getActivePokemon().getMoves().get(0));

        engine.playTurn(player, playerAction, rival, rivalAction);
    }
}
