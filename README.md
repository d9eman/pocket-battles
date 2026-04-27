# Pocket Battles: Pokemon Battle Simulator

Pocket Battles is a simplified Java battle simulator inspired by the structure and feel of Pokemon Showdown. The goal is not to recreate every official Pokemon mechanic. Instead, this project focuses on a clean object-oriented battle engine that is easy to explain during interview grading.

## What the demo supports

- Turn-based one-vs-one Pokemon battles
- Three Pokemon per side
- Move buttons and switch buttons in a simple Swing UI
- HP bars for both active Pokemon
- Battle log showing every turn event
- Physical and special damaging moves
- Status moves such as Protect, Swords Dance, and Calm Mind
- Move priority
- Speed-based turn order
- Switching before attacks
- Same-type attack bonus
- Dual-type effectiveness, including 4x weaknesses and immunities
- Dependency injection for battle rules and randomness

The project intentionally skips IVs, EVs, natures, items, abilities, and full level scaling. Every Pokemon is effectively built for a simple level-50 style demo.

## How to run

From the project root:

```bash
./gradlew run
```

If your local setup does not have the Gradle wrapper, use your installed Gradle:

```bash
gradle run
```

To run tests:

```bash
./gradlew test
```

or:

```bash
gradle test
```

The main UI class is:

```text
pocketbattles.ui.BattleUI
```

The console-only demo class is:

```text
pocketbattles.battle.BattleDemo
```

## Source organization / modules

```text
src/main/java/pocketbattles/
├── action/      Command objects for player choices
├── battle/      Core battle engine, context, and events
├── factory/     Move and Pokemon creation
├── model/       Pokemon, Trainer, Stats, Type, and PokemonBuilder
├── move/        Move hierarchy and move effects
├── observer/    Battle event listeners and publishers
├── strategy/    Swappable battle policies and AI choices
└── ui/          Swing user interface
```

## Five object-oriented patterns to explain

### 1. Strategy Pattern

**Where:**
- `pocketbattles.strategy.DamageCalculator`
- `pocketbattles.strategy.SimpleDamageCalculator`
- `pocketbattles.strategy.AccuracyPolicy`
- `pocketbattles.strategy.SimpleAccuracyPolicy`
- `pocketbattles.strategy.TypeChart`
- `pocketbattles.strategy.SimpleTypeChart`
- `pocketbattles.strategy.OpponentMoveStrategy`
- `pocketbattles.strategy.RandomOpponentMoveStrategy`

**How to explain it:**

The battle engine does not hard-code the damage formula, accuracy logic, type chart, or opponent decision-making. It depends on interfaces. That means we can swap in a different damage calculator, type chart, or computer-player strategy without rewriting the engine.

### 2. Factory Pattern

**Where:**
- `pocketbattles.factory.MoveFactory`
- `pocketbattles.factory.PokemonFactory`

**How to explain it:**

Pokemon and moves require several fields. The factories centralize that construction so the rest of the code can ask for `PokemonFactory.charizard()` or `MoveFactory.thunderbolt()` instead of duplicating constructor details everywhere.

### 3. Builder Pattern

**Where:**
- `pocketbattles.model.PokemonBuilder`

**How to explain it:**

Pokemon have many construction steps: name, level, one or two types, stats, and up to four moves. The builder makes this readable and prevents giant constructors from being scattered throughout the code. The factory uses the builder to create each predefined Pokemon.

### 4. Command Pattern

**Where:**
- `pocketbattles.action.BattleAction`
- `pocketbattles.action.UseMoveAction`
- `pocketbattles.action.SwitchPokemonAction`

**How to explain it:**

Each turn choice is represented as an object. The battle engine does not need a giant `if` statement asking whether the player attacked or switched. It just receives two `BattleAction` objects, orders them by priority and speed, and calls `execute`.

### 5. Observer Pattern

**Where:**
- `pocketbattles.observer.BattleEventPublisher`
- `pocketbattles.observer.BattleEventListener`
- `pocketbattles.observer.ConsoleBattleLogger`
- `pocketbattles.observer.FileBattleLogger`
- `pocketbattles.ui.BattleUI`

**How to explain it:**

The battle engine publishes battle events like "Charizard used Flamethrower." It does not know whether those messages go to the console, a file, or the GUI. The UI is just another listener. This keeps the engine decoupled from presentation.

## Dependency injection

The project demonstrates dependency injection in the UI and console demo setup:

```java
RandomSource random = new JavaRandomSource();
TypeChart typeChart = new SimpleTypeChart();
DamageCalculator damageCalculator = new SimpleDamageCalculator(typeChart, random);
AccuracyPolicy accuracyPolicy = new SimpleAccuracyPolicy(random);
BattleEventPublisher publisher = new BattleEventPublisher();

BattleContext context = new BattleContext(damageCalculator, accuracyPolicy, publisher);
BattleEngine engine = new BattleEngine(context, random);
```

`BattleEngine` does not create its own random source, damage calculator, accuracy policy, or event publisher. These are passed in from the outside, which makes the engine easier to test and modify.

## Pokemon currently included

The factories currently include:

- Charizard
- Blastoise
- Venusaur
- Pikachu
- Garchomp
- Gengar
- Lucario
- Dragonite
- Snorlax
- Gardevoir

The UI demo uses:

```text
Player:   Charizard, Pikachu, Snorlax
Computer: Blastoise, Venusaur, Garchomp
```

## Moves currently included

- Tackle
- Body Slam
- Flamethrower
- Fire Punch
- Surf
- Hydro Pump
- Energy Ball
- Vine Whip
- Thunderbolt
- Thunder Shock
- Earthquake
- Rock Slide
- Iron Tail
- Shadow Ball
- Aura Sphere
- Dragon Claw
- Ice Beam
- Moonblast
- Protect
- Swords Dance
- Calm Mind

## What to demo during grading

1. Run the UI.
2. Click a damaging move and show the battle log updating.
3. Point out that faster Pokemon usually move first unless priority changes it.
4. Use Protect and show that damage is blocked for that turn.
5. Switch Pokemon and show that switching happens before a regular attack.
6. Point to the five pattern locations in the package tree.
7. Explain that the battle engine depends on interfaces, not hard-coded concrete classes.

## Current limitations

This is still a simplified simulator. It does not include:

- IVs
- EVs
- Natures
- Items
- Abilities
- Weather
- Full status conditions like burn/paralysis/sleep
- Full official Pokemon data
- Online multiplayer

Those would be natural future extensions, but they are intentionally outside the current scope so the project stays focused on object-oriented design.
