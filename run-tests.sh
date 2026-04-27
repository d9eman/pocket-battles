#!/usr/bin/env bash
set -euo pipefail
rm -rf out
mkdir -p out
find src/main/java -name "*.java" > sources.txt
javac -d out @sources.txt
find src/test/java -name "*.java" > test-sources.txt
javac -cp "out:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" -d out @test-sources.txt
java -cp "out:src/main/resources:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore pocketbattles.BattleEngineTest
