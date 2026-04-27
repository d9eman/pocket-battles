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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class BattleUI extends JFrame implements BattleEventListener {
    private static final int WINDOW_WIDTH = 1100;
    private static final int WINDOW_HEIGHT = 720;
    
    private static final int SPRITE_SIZE = 165;
    private static final int LOG_PANEL_WIDTH = 330;
    private static final int RANDOM_TEAM_SIZE = 3;

    private static final int STATUS_PANEL_WIDTH = 360;
    private static final int STATUS_PANEL_HEIGHT = 105;

    private static final double OPPONENT_SPRITE_CENTER_X = 0.7;
    private static final double OPPONENT_SPRITE_CENTER_Y = 0.4;
    private static final double PLAYER_SPRITE_CENTER_X = 0.3;
    private static final double PLAYER_SPRITE_CENTER_Y = 0.7;

    private final RandomSource random;
    private final BattleEngine engine;
    private final OpponentMoveStrategy opponentMoveStrategy;
    private final PokemonSpriteFactory spriteFactory = new PokemonSpriteFactory();

    private Trainer player;
    private Trainer opponent;
    private boolean awaitingForcedSwitch;
    private boolean matchEndHandled;

    private final JLabel playerNameLabel = new JLabel();
    private final JLabel opponentNameLabel = new JLabel();
    private final JLabel playerSpriteLabel = new JLabel();
    private final JLabel opponentSpriteLabel = new JLabel();

    private final JProgressBar playerHpBar = new JProgressBar();
    private final JProgressBar opponentHpBar = new JProgressBar();

    private final JTextArea battleLog = new JTextArea();

    private final JPanel moveButtonPanel = new JPanel(new GridLayout(2, 2, 8, 8));
    private final JPanel switchButtonPanel = new JPanel(new GridLayout(2, 3, 8, 8));

    private final List<JButton> moveButtons = new ArrayList<>();
    private final List<JButton> switchButtons = new ArrayList<>();

    public BattleUI() {
        random = new JavaRandomSource();

        TypeChart typeChart = new SimpleTypeChart();
        DamageCalculator damageCalculator = new SimpleDamageCalculator(typeChart, random);
        AccuracyPolicy accuracyPolicy = new SimpleAccuracyPolicy(random);

        BattleEventPublisher publisher = new BattleEventPublisher();
        publisher.subscribe(this);

        BattleContext battleContext = new BattleContext(damageCalculator, accuracyPolicy, publisher);
        engine = new BattleEngine(battleContext, random);
        opponentMoveStrategy = new RandomOpponentMoveStrategy(random);

        chooseBattleMode();
        buildWindow();
        refreshScreen();
        appendLog("Battle started! Choose a move or switch Pokemon.");
    }

    private void chooseBattleMode() {
        String[] choices = {"3v3 Random Battle", "6v6 Rival Battle"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose a battle mode:",
                "Pocket Battles",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choices,
                choices[1]
        );

        if (choice == 0) {
            player = new Trainer("You", PokemonFactory.randomTeam(random, RANDOM_TEAM_SIZE));
            opponent = new Trainer("Computer", PokemonFactory.randomTeam(random, RANDOM_TEAM_SIZE));
            return;
        }

        player = new Trainer("You", PokemonFactory.demoPlayerTeam());
        opponent = new Trainer("Rival", PokemonFactory.demoOpponentTeam());
    }

    private void buildWindow() {
        setTitle("Pocket Battles - Pokemon Battle Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setMinimumSize(new Dimension(980, 650));
        setLayout(new BorderLayout(12, 12));
        getRootPane().setBorder(new EmptyBorder(12, 12, 12, 12));

        add(createBattleFieldPanel(), BorderLayout.CENTER);
        add(createLogPanel(), BorderLayout.EAST);
        add(createControlPanel(), BorderLayout.SOUTH);
    }

    private JPanel createBattleFieldPanel() {
        JPanel opponentStatusPanel = createStatusPanel("Opponent", opponentNameLabel, opponentHpBar);
        JPanel playerStatusPanel = createStatusPanel("Your Pokemon", playerNameLabel, playerHpBar);

        BattleBackgroundPanel panel = new BattleBackgroundPanel() {
            @Override
            public void doLayout() {
                layoutBattlefieldComponents(this, opponentStatusPanel, playerStatusPanel);
            }
        };

        panel.setLayout(null);
        panel.setBorder(BorderFactory.createLineBorder(new Color(80, 120, 70), 2));

        opponentSpriteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        opponentSpriteLabel.setVerticalAlignment(SwingConstants.CENTER);
        playerSpriteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerSpriteLabel.setVerticalAlignment(SwingConstants.CENTER);

        panel.add(opponentStatusPanel);
        panel.add(playerStatusPanel);
        panel.add(opponentSpriteLabel);
        panel.add(playerSpriteLabel);

        return panel;
    }

    private void layoutBattlefieldComponents(JPanel panel,
                                         JPanel opponentStatusPanel,
                                         JPanel playerStatusPanel) {
        int width = panel.getWidth();
        int height = panel.getHeight();

        opponentStatusPanel.setBounds(
                36,
                28,
                STATUS_PANEL_WIDTH,
                STATUS_PANEL_HEIGHT
        );

        playerStatusPanel.setBounds(
                width - STATUS_PANEL_WIDTH - 45,
                height - STATUS_PANEL_HEIGHT - 42,
                STATUS_PANEL_WIDTH,
                STATUS_PANEL_HEIGHT
        );

        centerComponentAt(
                opponentSpriteLabel,
                width * OPPONENT_SPRITE_CENTER_X,
                height * OPPONENT_SPRITE_CENTER_Y,
                SPRITE_SIZE,
                SPRITE_SIZE
        );

        centerComponentAt(
                playerSpriteLabel,
                width * PLAYER_SPRITE_CENTER_X,
                height * PLAYER_SPRITE_CENTER_Y,
                SPRITE_SIZE,
                SPRITE_SIZE
        );
    }

    private void centerComponentAt(JLabel label, double centerX, double centerY, int width, int height) {
        int x = (int) centerX - width / 2;
        int y = (int) centerY - height / 2;
        label.setBounds(x, y, width, height);
    }

    private JPanel createStatusPanel(String title, JLabel nameLabel, JProgressBar hpBar) {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(new Color(255, 255, 255, 225));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70)),
                new EmptyBorder(10, 12, 10, 12)
        ));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(STATUS_PANEL_WIDTH, STATUS_PANEL_HEIGHT));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 13f));

        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 17f));
        nameLabel.setPreferredSize(new Dimension(STATUS_PANEL_WIDTH - 30, 30));

        hpBar.setStringPainted(true);

        panel.add(titleLabel);
        panel.add(nameLabel);
        panel.add(hpBar);

        return panel;
    }

    private JPanel createLogPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setPreferredSize(new Dimension(LOG_PANEL_WIDTH, 0));
        panel.setBorder(BorderFactory.createTitledBorder("Battle Log"));

        battleLog.setEditable(false);
        battleLog.setLineWrap(true);
        battleLog.setWrapStyleWord(true);
        battleLog.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        panel.add(new JScrollPane(battleLog), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createControlPanel() {
        JPanel controls = new JPanel(new BorderLayout(8, 8));

        moveButtonPanel.setBorder(BorderFactory.createTitledBorder("Moves"));
        switchButtonPanel.setBorder(BorderFactory.createTitledBorder("Switch Pokemon"));

        controls.add(moveButtonPanel, BorderLayout.CENTER);
        controls.add(switchButtonPanel, BorderLayout.SOUTH);

        return controls;
    }

    private void refreshScreen() {
        updatePokemonDisplay(
                player.getActivePokemon(),
                playerNameLabel,
                playerHpBar,
                playerSpriteLabel,
                true
        );

        updatePokemonDisplay(
                opponent.getActivePokemon(),
                opponentNameLabel,
                opponentHpBar,
                opponentSpriteLabel,
                false
        );

        rebuildMoveButtons();
        rebuildSwitchButtons();
        updateButtonState();

        revalidate();
        repaint();
    }

    private void updatePokemonDisplay(Pokemon pokemon,
                                      JLabel nameLabel,
                                      JProgressBar hpBar,
                                      JLabel spriteLabel,
                                      boolean backSprite) {
        nameLabel.setText(formatPokemonLabel(pokemon));

        hpBar.setMaximum(pokemon.getMaxHp());
        hpBar.setValue(pokemon.getCurrentHp());
        hpBar.setString(pokemon.getCurrentHp() + " / " + pokemon.getMaxHp() + " HP");

        if (backSprite) {
            spriteLabel.setIcon(spriteFactory.createBackSprite(pokemon, SPRITE_SIZE));
        } else {
            spriteLabel.setIcon(spriteFactory.createFrontSprite(pokemon, SPRITE_SIZE));
        }
    }

    private String formatPokemonLabel(Pokemon pokemon) {
        return pokemon.getName() + "  [" + formatTypes(pokemon) + "]";
    }

    private String formatTypes(Pokemon pokemon) {
        StringBuilder text = new StringBuilder();
        List<?> types = pokemon.getTypes();

        for (int index = 0; index < types.size(); index++) {
            if (index > 0) {
                text.append(" / ");
            }
            text.append(types.get(index));
        }

        return text.toString();
    }

    private void rebuildMoveButtons() {
        moveButtons.clear();
        moveButtonPanel.removeAll();

        for (Move move : player.getActivePokemon().getMoves()) {
            JButton button = new JButton(move.getName() + " (" + move.getType() + ")");
            button.addActionListener(event -> handleMoveSelection(move));
            moveButtons.add(button);
            moveButtonPanel.add(button);
        }
    }

    private void rebuildSwitchButtons() {
        switchButtons.clear();
        switchButtonPanel.removeAll();

        List<Pokemon> team = player.getTeam();

        for (int index = 0; index < team.size(); index++) {
            Pokemon pokemon = team.get(index);
            JButton button = new JButton(switchLabel(pokemon));
            int switchIndex = index;

            button.addActionListener(event -> handleSwitchSelection(switchIndex));

            switchButtons.add(button);
            switchButtonPanel.add(button);
        }
    }

    private String switchLabel(Pokemon pokemon) {
        if (pokemon.isFainted()) {
            return pokemon.getName() + " (FNT)";
        }
        return pokemon.getName();
    }

    private void handleMoveSelection(Move move) {
        if (battleIsOver()) {
            return;
        }

        if (awaitingForcedSwitch) {
            appendLog("Choose a Pokemon to send in.");
            return;
        }

        playTurn(new UseMoveAction(move));
    }

    private void handleSwitchSelection(int switchIndex) {
        if (battleIsOver()) {
            return;
        }

        if (awaitingForcedSwitch) {
            forcePlayerSwitch(switchIndex);
            return;
        }

        playTurn(new SwitchPokemonAction(switchIndex));
    }

    private void playTurn(BattleAction playerAction) {
        try {
            BattleAction opponentAction = opponentMoveStrategy.chooseAction(opponent, player);
            engine.playTurn(player, playerAction, opponent, opponentAction);
            handlePostTurnState();
        } catch (IllegalArgumentException exception) {
            appendLog(exception.getMessage());
        }
    }

    private void handlePostTurnState() {
        autoSwitchOpponentIfNeeded();
        promptForPlayerSwitchIfNeeded();
        refreshScreen();
        announceWinnerIfNeeded();
    }

    private void autoSwitchOpponentIfNeeded() {
        if (!opponent.getActivePokemon().isFainted() || !opponent.hasRemainingPokemon()) {
            return;
        }

        List<Integer> switchableIndexes = opponent.switchableIndexes();
        int chosenIndex = switchableIndexes.get(random.nextInt(switchableIndexes.size()));

        opponent.switchTo(chosenIndex);
        appendLog(opponent.getName() + " sent out " + opponent.getActivePokemon().getName() + ".");
    }

    private void promptForPlayerSwitchIfNeeded() {
        if (!player.getActivePokemon().isFainted() || !player.hasRemainingPokemon()) {
            awaitingForcedSwitch = false;
            return;
        }

        awaitingForcedSwitch = true;
        appendLog("Choose which Pokemon to send in.");
    }

    private void forcePlayerSwitch(int switchIndex) {
        try {
            player.switchTo(switchIndex);
            awaitingForcedSwitch = false;

            appendLog(player.getName() + " sent out " + player.getActivePokemon().getName() + ".");

            refreshScreen();
            announceWinnerIfNeeded();
        } catch (IllegalArgumentException exception) {
            appendLog(exception.getMessage());
        }
    }

    private void announceWinnerIfNeeded() {
        if (!battleIsOver() || matchEndHandled) {
            updateButtonState();
            return;
        }

        matchEndHandled = true;

        if (!player.hasRemainingPokemon()) {
            appendLog("You lost the battle.");
        } else {
            appendLog("You won the battle!");
        }

        updateButtonState();
        askToRestartOrQuit();
    }

    private void askToRestartOrQuit() {
        String[] choices = {"Restart", "Quit"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "The battle is over. What would you like to do?",
                "Battle Over",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                choices,
                choices[0]
        );

        if (choice == 0) {
            dispose();
            SwingUtilities.invokeLater(() -> new BattleUI().setVisible(true));
        } else {
            dispose();
        }
    }

    private boolean battleIsOver() {
        return !player.hasRemainingPokemon() || !opponent.hasRemainingPokemon();
    }

    private void updateButtonState() {
        boolean canUseMoves = !battleIsOver()
                && !awaitingForcedSwitch
                && !player.getActivePokemon().isFainted();

        for (JButton button : moveButtons) {
            button.setEnabled(canUseMoves);
        }

        List<Pokemon> team = player.getTeam();

        for (int index = 0; index < switchButtons.size(); index++) {
            JButton button = switchButtons.get(index);
            Pokemon pokemon = team.get(index);

            boolean selectable = index != player.getActiveIndex()
                    && !pokemon.isFainted()
                    && !battleIsOver();

            button.setEnabled(selectable);
        }
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