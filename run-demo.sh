#!/usr/bin/env bash
set -euo pipefail
rm -rf out
mkdir -p out
find src/main/java -name "*.java" > sources.txt
javac -d out @sources.txt
java -cp "out:src/main/resources" pocketbattles.ui.BattleUI
