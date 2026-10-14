# AreaMining (Paper 1.21.11)

Verzauberung **Area Mining** für Spitzhacken und Schaufeln:

| Stufe | Fläche |
|-------|--------|
| I     | 3×3    |
| II    | 4×4    |
| III   | 5×5    |

Die Fläche liegt immer senkrecht zur angeklickten Blockseite. **Schleichen** = nur ein Block.

## Verzauberung geben
```
/enchant @s areamining:area_mining 3
```
(Item in die Hand nehmen.) Per Datapack/Loot-Tables kann sie ebenfalls vergeben werden.

## Bauen
- Lokal: `gradle build` (Java 21) → `build/libs/AreaMining-1.0.0.jar` in `plugins/` legen.
- GitHub: pushen → Actions baut automatisch (Artifact). Tag `v1.0.0` pushen → automatisches Release mit JAR.

## Hinweis
Die Verzauberung wird im Bootstrapper registriert, daher `paper-plugin.yml`. Server einmal komplett neu starten (kein /reload).
