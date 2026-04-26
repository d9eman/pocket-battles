package pocketbattles.ui;

import pocketbattles.action.BattleAction;
import pocketbattles.action.SwitchPokemonAction;
import pocketbattles.action.UseMoveAction;
import pocketbattles.battle.BattleContext;
import pocketbattles.battle.BattleEngine;
import pocketbattles.battle.BattleEvent;
import pocketbattles.factory.PokemonFactory;
import pocketbattles.model.Pokemon;
import pocketbattles.model.Trainer;
import pocketbattles.move.Move;
import pocketbattles.observer.BattleEventListener;
import pocketbattles.observer.BattleEventPublisher;
import pocketbattles.strategy.AccuracyPolicy;
import pocketbattles.strategy.DamageCalculator;
import pocketbattles.strategy.JavaRandomSource;
import pocketbattles.strategy.OpponentMoveStrategy;
import pocketbattles.strategy.RandomOpponentMoveStrategy;
import pocketbattles.strategy.RandomSource;
import pocketbattles.strategy.SimpleAccuracyPolicy;
import pocketbattles.strategy.SimpleDamageCalculator;
import pocketbattles.strategy.SimpleTypeChart;
import pocketbattles.strategy.TypeChart;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class BattleUI extends JFrame implements BattleEventListener {
    private final Trainer player;
    private final Trainer opponent;
    private final BattleEngine engine;
    private final OpponentMoveStrategy opponentMoveStrategy;

    private final JLabel playerNameLabel = new JLabel();
    private final JLabel opponentNameLabel = new JLabel();
    private final JProgressBar playerHpBar = new JProgressBar();
    private final JProgressBar opponentHpBar = new JProgressBar();
    private final JTextArea battleLog = new JTextArea();
    private final JPanel moveButtonPanel = new JPanel(new GridLayout(2, 2, 8, 8));
    private final JPanel switchButtonPanel = new JPanel(new GridLayout(1, 3, 8, 8));
    private final List<JButton> actionButtons = new ArrayList<>();

    public BattleUI() {
        RandomSource random = new JavaRandomSource();
        TypeChart typeChart = new SimpleTypeChart();
        DamageCalculator damageCalculator = new SimpleDamageCalculator(typeChart, random);
        AccuracyPolicy accuracyPolicy = new SimpleAccuracyPolicy(random);

        BattleEventPublisher publisher = new BattleEventPublisher();
        publisher.subscribe(this);

        BattleContext context = new BattleContext(damageCalculator, accuracyPolicy, publisher);
        this.engine = new BattleEngine(context, random);
        this.opponentMoveStrategy = new RandomOpponentMoveStrategy(random);
        this.player = new Trainer("You", PokemonFactory.demoPlayerTeam());
        this.opponent = new Trainer("Computer", PokemonFactory.demoOpponentTeam());

        buildWindow();
        refreshScreen();
        appendLog("Battle started! Choose a move or switch Pokemon.");
    }

    private void buildWindow() {
        setTitle("Pocket Battles - Pokemon Battle Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 620);
        setMinimumSize(new Dimension(760, 560));
        setLayout(new BorderLayout(12, 12));

        JPanel battlePanel = new JPanel(new GridLayout(1, 2, 12, 12));
        battlePanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 0, 12));
        battlePanel.add(createPokemonPanel("Your Active Pokemon", playerNameLabel, playerHpBar));
        battlePanel.add(createPokemonPanel("Opponent Active Pokemon", opponentNameLabel, opponentHpBar));
        add(battlePanel, BorderLayout.NORTH);

        battleLog.setEditable(false);
        battleLog.setLineWrap(true);
        battleLog.setWrapStyleWord(true);
        add(new JScrollPane(battleLog), BorderLayout.CENTER);

        JPanel controls = new JPanel(new BorderLayout(8, 8));
        controls.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
        moveButtonPanel.setBorder(BorderFactory.createTitledBorder("Moves"));
        switchButtonPanel.setBorder(BorderFactory.createTitledBorder("Switch"));
        controls.add(moveButtonPanel, BorderLayout.CENTER);
        controls.add(switchButtonPanel, BorderLayout.SOUTH);
        add(controls, BorderLayout.SOUTH);
    }

    private JPanel createPokemonPanel(String title, JLabel nameLabel, JProgressBar hpBar) {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        hpBar.setStringPainted(true);
        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(hpBar, BorderLayout.CENTER);
        return panel;
    }

    private void refreshScreen() {
        updatePokemonPanel(player.getActivePokemon(), playerNameLabel, playerHpBar);
        updatePokemonPanel(opponent.getActivePokemon(), opponentNameLabel, opponentHpBar);
        rebuildMoveButtons();
        rebuildSwitchButtons();
        updateButtonState();
        revalidate();
        repaint();
    }

    private void updatePokemonPanel(Pokemon pokemon, JLabel label, JProgressBar hpBar) {
        label.setText(pokemon.getName() + " " + pokemon.getTypes());
        hpBar.setMaximum(pokemon.getMaxHp());
        hpBar.setValue(pokemon.getCurrentHp());
        hpBar.setString(pokemon.getCurrentHp() + " / " + pokemon.getMaxHp() + " HP");
    }

    private void rebuildMoveButtons() {
        moveButtonPanel.removeAll();

        for (Move move : player.getActivePokemon().getMoves()) {
            JButton button = new JButton(move.getName() + " (" + move.getType() + ")");
            button.addActionListener(event -> playPlayerAction(new UseMoveAction(move)));
            moveButtonPanel.add(button);
            actionButtons.add(button);
        }
    }

    private void rebuildSwitchButtons() {
        switchButtonPanel.removeAll();
        List<Pokemon> team = player.getTeam();

        for (int i = 0; i < team.size(); i++) {
            Pokemon pokemon = team.get(i);
            JButton button = new JButton(pokemon.getName());
            final int switchIndex = i;
            button.setEnabled(i != player.getActiveIndex() && !pokemon.isFainted());
            button.addActionListener(event -> playPlayerAction(new SwitchPokemonAction(switchIndex)));
            switchButtonPanel.add(button);
            actionButtons.add(button);
        }
    }

    private void playPlayerAction(BattleAction playerAction) {
        if (battleIsOver()) {
            return;
        }

        try {
            BattleAction opponentAction = opponentMoveStrategy.chooseAction(opponent, player);
            engine.playTurn(player, playerAction, opponent, opponentAction);
            handleAutomaticSwitches();
            refreshScreen();
            announceWinnerIfNeeded();
        } catch (IllegalArgumentException exception) {
            appendLog(exception.getMessage());
        }
    }

    private void handleAutomaticSwitches() {
        if (player.getActivePokemon().isFainted() && player.hasRemainingPokemon()) {
            int nextIndex = player.firstHealthyBenchIndex();
            player.switchTo(nextIndex);
            appendLog(player.getName() + " sent out " + player.getActivePokemon().getName() + ".");
        }

        if (opponent.getActivePokemon().isFainted() && opponent.hasRemainingPokemon()) {
            int nextIndex = opponent.firstHealthyBenchIndex();
            opponent.switchTo(nextIndex);
            appendLog(opponent.getName() + " sent out " + opponent.getActivePokemon().getName() + ".");
        }
    }

    private void announceWinnerIfNeeded() {
        if (!player.hasRemainingPokemon()) {
            appendLog("You lost the battle.");
        } else if (!opponent.hasRemainingPokemon()) {
            appendLog("You won the battle!");
        }
        updateButtonState();
    }

    private boolean battleIsOver() {
        return !player.hasRemainingPokemon() || !opponent.hasRemainingPokemon();
    }

    private void updateButtonState() {
        boolean enabled = !battleIsOver();
        for (JButton button : actionButtons) {
            button.setEnabled(enabled && button.isEnabled());
        }
        actionButtons.clear();
    }

    @Override
    public void onEvent(BattleEvent event) {
        appendLog(event.message());
    }

    private void appendLog(String message) {
        battleLog.append(message + System.lineSeparator());
        battleLog.setCaretPosition(battleLog.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BattleUI().setVisible(true));
    }
}
