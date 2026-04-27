package pocketbattles;

import org.junit.Test;
import pocketbattles.action.BattleAction;
import pocketbattles.action.SwitchPokemonAction;
import pocketbattles.action.UseMoveAction;
import pocketbattles.battle.BattleContext;
import pocketbattles.battle.BattleEngine;
import pocketbattles.battle.BattleEvent;
import pocketbattles.factory.PokemonFactory;
import pocketbattles.model.Pokemon;
import pocketbattles.model.Trainer;
import pocketbattles.model.Type;
import pocketbattles.observer.BattleEventListener;
import pocketbattles.observer.BattleEventPublisher;
import pocketbattles.strategy.AccuracyPolicy;
import pocketbattles.strategy.DamageCalculator;
import pocketbattles.strategy.RandomSource;
import pocketbattles.strategy.SimpleAccuracyPolicy;
import pocketbattles.strategy.SimpleDamageCalculator;
import pocketbattles.strategy.SimpleTypeChart;
import pocketbattles.strategy.TypeChart;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BattleEngineTest {

    @Test
    public void grassAgainstWaterGroundIsFourTimesEffective() {
        TypeChart typeChart = new SimpleTypeChart();
        double multiplier = typeChart.multiplier(Type.GRASS, List.of(Type.WATER, Type.GROUND));
        assertEquals(4.0, multiplier, 0.0001);
    }

    @Test
    public void electricMoveDoesNotAffectGroundType() {
        TypeChart typeChart = new SimpleTypeChart();
        double multiplier = typeChart.multiplier(Type.ELECTRIC, List.of(Type.GROUND));
        assertEquals(0.0, multiplier, 0.0001);
    }

    @Test
    public void fasterPokemonActsFirstWhenPriorityIsTheSame() {
        TestSetup setup = new TestSetup();
        Trainer trainer1 = new Trainer("Ash", List.of(PokemonFactory.pikachu()));
        Trainer trainer2 = new Trainer("Misty", List.of(PokemonFactory.blastoise()));

        BattleAction action1 = new UseMoveAction(trainer1.getActivePokemon().getMoves().get(0));
        BattleAction action2 = new UseMoveAction(trainer2.getActivePokemon().getMoves().get(0));

        setup.engine.playTurn(trainer1, action1, trainer2, action2);

        assertFalse(setup.logger.messages.isEmpty());
        assertEquals("Pikachu used Thunderbolt.", setup.logger.messages.get(0));
    }

    @Test
    public void priorityMoveActsBeforeFasterRegularMove() {
        TestSetup setup = new TestSetup();
        Trainer trainer1 = new Trainer("Ash", List.of(PokemonFactory.lucario()));
        Trainer trainer2 = new Trainer("Gary", List.of(PokemonFactory.jolteon()));

        BattleAction extremeSpeed = new UseMoveAction(trainer1.getActivePokemon().getMoves().get(2));
        BattleAction thunderbolt = new UseMoveAction(trainer2.getActivePokemon().getMoves().get(0));

        setup.engine.playTurn(trainer1, extremeSpeed, trainer2, thunderbolt);

        assertEquals("Lucario used Extreme Speed.", setup.logger.messages.get(0));
    }

    @Test
    public void speedBoostCanMakeSlowerPokemonMoveFirst() {
        TestSetup setup = new TestSetup();
        Pokemon arcanine = PokemonFactory.arcanine();
        Pokemon charizard = PokemonFactory.charizard();

        arcanine.getMoves().get(3).use(setup.context, arcanine, charizard);
        setup.logger.messages.clear();

        Trainer trainer1 = new Trainer("Ash", List.of(arcanine));
        Trainer trainer2 = new Trainer("Gary", List.of(charizard));
        BattleAction arcanineAttack = new UseMoveAction(arcanine.getMoves().get(0));
        BattleAction charizardAttack = new UseMoveAction(charizard.getMoves().get(0));

        setup.engine.playTurn(trainer1, arcanineAttack, trainer2, charizardAttack);

        assertEquals("Arcanine used Flamethrower.", setup.logger.messages.get(0));
    }

    @Test
    public void protectBlocksDamageForThatTurn() {
        TestSetup setup = new TestSetup();
        Trainer trainer1 = new Trainer("Ash", List.of(PokemonFactory.gardevoir()));
        Trainer trainer2 = new Trainer("Gary", List.of(PokemonFactory.charizard()));

        Pokemon gardevoir = trainer1.getActivePokemon();
        int startingHp = gardevoir.getCurrentHp();

        BattleAction protect = new UseMoveAction(gardevoir.getMoves().get(3));
        BattleAction fire = new UseMoveAction(trainer2.getActivePokemon().getMoves().get(0));

        setup.engine.playTurn(trainer1, protect, trainer2, fire);

        assertEquals(startingHp, gardevoir.getCurrentHp());
        assertTrue(setup.logger.messages.contains("Gardevoir is protected this turn."));
        assertTrue(setup.logger.messages.contains("Gardevoir protected itself!"));
    }

    @Test
    public void swordsDanceMakesLaterPhysicalMoveDoMoreDamage() {
        TestSetup setup = new TestSetup();

        Pokemon attacker1 = PokemonFactory.garchomp();
        Pokemon defender1 = PokemonFactory.blastoise();
        int defender1StartHp = defender1.getCurrentHp();
        attacker1.getMoves().get(1).use(setup.context, attacker1, defender1);
        int damageWithoutBuff = defender1StartHp - defender1.getCurrentHp();

        Pokemon attacker2 = PokemonFactory.garchomp();
        Pokemon defender2 = PokemonFactory.blastoise();
        attacker2.getMoves().get(3).use(setup.context, attacker2, defender2);
        int defender2StartHp = defender2.getCurrentHp();
        attacker2.getMoves().get(1).use(setup.context, attacker2, defender2);
        int damageWithBuff = defender2StartHp - defender2.getCurrentHp();

        assertTrue(damageWithBuff > damageWithoutBuff);
    }

    @Test
    public void ironDefenseMakesPokemonTakeLessPhysicalDamage() {
        TestSetup setup = new TestSetup();

        Pokemon attacker1 = PokemonFactory.garchomp();
        Pokemon defenderWithoutBoost = PokemonFactory.blastoise();
        int startHpWithoutBoost = defenderWithoutBoost.getCurrentHp();
        attacker1.getMoves().get(1).use(setup.context, attacker1, defenderWithoutBoost);
        int damageWithoutBoost = startHpWithoutBoost - defenderWithoutBoost.getCurrentHp();

        Pokemon attacker2 = PokemonFactory.garchomp();
        Pokemon defenderWithBoost = PokemonFactory.blastoise();
        defenderWithBoost.getMoves().get(3).use(setup.context, defenderWithBoost, attacker2);
        int startHpWithBoost = defenderWithBoost.getCurrentHp();
        attacker2.getMoves().get(1).use(setup.context, attacker2, defenderWithBoost);
        int damageWithBoost = startHpWithBoost - defenderWithBoost.getCurrentHp();

        assertTrue(damageWithBoost < damageWithoutBoost);
    }

    @Test
    public void calmMindBoostsSpecialAttackAndSpecialDefense() {
        TestSetup setup = new TestSetup();
        Pokemon starmie = PokemonFactory.starmie();
        Pokemon blastoise = PokemonFactory.blastoise();

        int specialAttackBefore = starmie.getEffectiveSpecialAttack();
        int specialDefenseBefore = starmie.getEffectiveSpecialDefense();

        starmie.getMoves().get(3).use(setup.context, starmie, blastoise);

        assertTrue(starmie.getEffectiveSpecialAttack() > specialAttackBefore);
        assertTrue(starmie.getEffectiveSpecialDefense() > specialDefenseBefore);
    }

    @Test
    public void calmMindMakesLaterSpecialMoveDoMoreDamage() {
        TestSetup setup = new TestSetup();

        Pokemon attackerWithoutBoost = PokemonFactory.starmie();
        Pokemon defender1 = PokemonFactory.blastoise();
        int defender1StartHp = defender1.getCurrentHp();
        attackerWithoutBoost.getMoves().get(1).use(setup.context, attackerWithoutBoost, defender1);
        int damageWithoutBoost = defender1StartHp - defender1.getCurrentHp();

        Pokemon attackerWithBoost = PokemonFactory.starmie();
        Pokemon defender2 = PokemonFactory.blastoise();
        attackerWithBoost.getMoves().get(3).use(setup.context, attackerWithBoost, defender2);
        int defender2StartHp = defender2.getCurrentHp();
        attackerWithBoost.getMoves().get(1).use(setup.context, attackerWithBoost, defender2);
        int damageWithBoost = defender2StartHp - defender2.getCurrentHp();

        assertTrue(damageWithBoost > damageWithoutBoost);
    }

    @Test
    public void calmMindMakesPokemonTakeLessSpecialDamage() {
        TestSetup setup = new TestSetup();

        Pokemon attacker1 = PokemonFactory.jolteon();
        Pokemon defenderWithoutBoost = PokemonFactory.starmie();
        int startHpWithoutBoost = defenderWithoutBoost.getCurrentHp();
        attacker1.getMoves().get(0).use(setup.context, attacker1, defenderWithoutBoost);
        int damageWithoutBoost = startHpWithoutBoost - defenderWithoutBoost.getCurrentHp();

        Pokemon attacker2 = PokemonFactory.jolteon();
        Pokemon defenderWithBoost = PokemonFactory.starmie();
        defenderWithBoost.getMoves().get(3).use(setup.context, defenderWithBoost, attacker2);
        int startHpWithBoost = defenderWithBoost.getCurrentHp();
        attacker2.getMoves().get(0).use(setup.context, attacker2, defenderWithBoost);
        int damageWithBoost = startHpWithBoost - defenderWithBoost.getCurrentHp();

        assertTrue(damageWithBoost < damageWithoutBoost);
    }

    @Test
    public void switchHappensBeforeRegularAttackAndAttackHitsNewPokemon() {
        TestSetup setup = new TestSetup();

        Pokemon charizard = PokemonFactory.charizard();
        Pokemon snorlax = PokemonFactory.snorlax();
        Pokemon blastoise = PokemonFactory.blastoise();

        Trainer trainer1 = new Trainer("Ash", List.of(charizard, snorlax));
        Trainer trainer2 = new Trainer("Gary", List.of(blastoise));

        int charizardStartHp = charizard.getCurrentHp();
        int snorlaxStartHp = snorlax.getCurrentHp();

        BattleAction switchAction = new SwitchPokemonAction(1);
        BattleAction attackAction = new UseMoveAction(blastoise.getMoves().get(0));

        setup.engine.playTurn(trainer1, switchAction, trainer2, attackAction);

        assertEquals("Snorlax", trainer1.getActivePokemon().getName());
        assertEquals(charizardStartHp, charizard.getCurrentHp());
        assertTrue(snorlax.getCurrentHp() < snorlaxStartHp);
        assertEquals("Ash switched from Charizard to Snorlax.", setup.logger.messages.get(0));
    }

    private static class TestSetup {
        final RandomSource random = new FixedRandomSource(0.0, true);
        final TypeChart typeChart = new SimpleTypeChart();
        final DamageCalculator damageCalculator = new SimpleDamageCalculator(typeChart, random);
        final AccuracyPolicy accuracyPolicy = new SimpleAccuracyPolicy(random);
        final BattleEventPublisher publisher = new BattleEventPublisher();
        final RecordingBattleLogger logger = new RecordingBattleLogger();
        final BattleContext context = new BattleContext(damageCalculator, accuracyPolicy, publisher);
        final BattleEngine engine = new BattleEngine(context, random);

        TestSetup() {
            publisher.subscribe(logger);
        }
    }

    private static class FixedRandomSource implements RandomSource {
        private final double fixedDouble;
        private final boolean fixedBoolean;

        FixedRandomSource(double fixedDouble, boolean fixedBoolean) {
            this.fixedDouble = fixedDouble;
            this.fixedBoolean = fixedBoolean;
        }

        @Override
        public double nextDouble() {
            return fixedDouble;
        }

        @Override
        public boolean nextBoolean() {
            return fixedBoolean;
        }

        @Override
        public int nextInt(int bound) {
            return 0;
        }
    }

    private static class RecordingBattleLogger implements BattleEventListener {
        private final List<String> messages = new ArrayList<>();

        @Override
        public void onEvent(BattleEvent event) {
            messages.add(event.message());
        }
    }
}