# Bundle In Palette

Bundle In Palette adds sixteen dyed variants of the vanilla bundle so you can color‑code your inventory and storage.  
This mod **requires [Bundle In Creative](https://github.com/dmor-me/BundleInCreative)** or any Bundle recipe mod, which unhides the vanilla bundle item so the new recipes have something to work with.

## Features
- 16 fully textured bundle variants covering every vanilla dye color.
- Simple shapeless recipes: combine a vanilla bundle with any dye to recolor it.
- Automatically injected into the Tools & Utilities creative tab for quick access.
- All bundles (vanilla + dyed) share the `forge:bundles` item tag for datapack/mod compatibility.
- Built for Minecraft **1.20.1** with **Forge 47.4.10**.

## Crafting
```
Bundle + Any Dye -> Matching Dyed Bundle
```
The recipes unlock after you acquire at least one vanilla bundle.

## Installation
1. Install Minecraft Forge 1.20.1 (47.x series).
2. Download the latest releases of:
   - [Bundle In Creative](https://github.com/dmor-me/BundleInCreative/releases)
   - Bundle In Palette (this project) from the Releases tab.
3. Place both `.jar` files inside your Minecraft `mods` folder.
4. Launch Minecraft with the Forge profile.

## Developing / Building
```bash
git clone https://github.com/dmor-me/BundleInPalette.git
cd BundleInPalette/BundleInPalette
./gradlew build
```
The built jar can be found under `BundleInPalette/BundleInPalette/build/libs/`.