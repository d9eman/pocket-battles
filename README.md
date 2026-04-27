# Pocket Battles: Pokemon Battle Simulator

Pocket Battles is a simplified pokemon battle simulator in java inspired by the structure and feel of Pokemon Showdown. Pocket Battles allows you to do either a 3v3 random pokemon battle (from a pool of 20 pokemon) or a 6v6 rival battle (with a fixed team). The battes do not have the entire functionality of the games, but they have all types with their appropriate strengths and weaknesses, all stats, phsyical/special move split (one targets Defense and the other Special Defense), move types, and accuracy. The game utilizes a simplified version of the real pokemon damage formula, utilizing the users stats + any stat changes + the opoonents defense stats + the moves power + a scaling damage roll + type effectiveness to calculate the moves damage.

## What the demo supports

- Turn-based one-vs-one Pokemon battles
- Three Pokemon per side. Or a 6v6
- Move buttons and switch buttons in a simple UI
- HP bars for both active Pokemon
- Battle log showing every turn event
- Physical and special damaging moves
- Stat changing and status moves such as Protect, Swords Dance, and Calm Mind
- Move priority
- Speed-based turn order
- Switching before attacks
- Same-type attack bonus
- Dual-type effectiveness, including 4x weaknesses and immunities
- Dependency injection for battle rules and randomness

## How to run

```powershell
.\run-demo.bat
```

Run tests:

```powershell
.\run-tests.bat
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
└── ui/          Swing user interface, battlefield, and sprite loading
```

## Five object-oriented patterns

Strategy Pattern: DamageCalculator, Accuracy, Types, MoveStrategies

Factory Pattern: MoveFactory and PokemonFactory

Builder Pattern: Pokemon Builder

Command Pattern: Attacking and Switching

Observer Pattern: BattleEvents are published and logged to battle log


## Pokemon currently included


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
- Alakazam
- Tyranitar
- Scizor
- Mamoswine
- Heracross
- Jolteon
- Starmie
- Arcanine
- Togekiss
- Absol

AI Note: AI was utilized in the creation of the UI (even though the sprites and background were all found and implemented  by me) and AI helped pool together pokemon stats and their moves.