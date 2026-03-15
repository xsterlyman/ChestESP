# ChestESP - Mod Minecraft 1.8.9 Forge

## Description

Affiche une boîte colorée (ESP) autour de tous les contenants proches :
- **Coffre normal** (orange par défaut)
- **Coffre piégé** (rouge par défaut)
- **Coffre de l'Ender** (violet par défaut)
- **Dispenser** (cyan par défaut)
- **Dropper** (vert par défaut)

---

## Installation

### Prérequis
- Java 8 (JDK 8)
- Minecraft Forge 1.8.9 installé
- Gradle (ou utiliser le wrapper `gradlew` inclus)

### Étapes

1. **Télécharger Forge MDK 1.8.9** depuis [files.minecraftforge.net](https://files.minecraftforge.net)
2. **Extraire le MDK** et copier les fichiers suivants dedans :
   - `gradlew`, `gradlew.bat`, `gradle/` (depuis le MDK)
3. **Compiler le mod** :
   ```bash
   gradlew setupDecompWorkspace
   gradlew build
   ```
4. Le `.jar` sera dans `build/libs/ChestESP-1.0.0.jar`
5. **Copier le .jar** dans le dossier `mods/` de Minecraft

---

## Utilisation

| Touche     | Action                        |
|------------|-------------------------------|
| `INSERT`   | Ouvrir l'interface de config  |
| `HOME`     | Activer / désactiver l'ESP    |

### Interface de configuration
- **Cliquer sur le nom** d'un contenant → activer/désactiver son ESP
- **Cliquer sur une couleur** → changer la couleur de ce contenant

### Couleurs disponibles
Blanc, Rouge, Orange, Jaune, Vert, Cyan, Bleu, Violet, Rose, Gris

---

## Structure du projet

```
chestesp-mod/
├── build.gradle
├── gradle.properties
├── settings.gradle
└── src/main/java/com/chestesp/
    ├── ChestESP.java       <- Classe principale du mod
    ├── ESPConfig.java      <- Configuration (couleurs, activé/désactivé)
    ├── ESPRenderer.java    <- Rendu OpenGL des boîtes ESP
    ├── KeyHandler.java     <- Gestion des touches
    └── gui/
        └── GuiESP.java     <- Interface graphique de configuration
```
