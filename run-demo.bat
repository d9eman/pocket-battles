@echo off
setlocal
powershell -NoProfile -ExecutionPolicy Bypass -Command "Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue; New-Item -ItemType Directory out | Out-Null; javac -d out (Get-ChildItem -Recurse src\main\java\*.java).FullName; if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }; java -cp 'out;src\main\resources' pocketbattles.ui.BattleUI"
