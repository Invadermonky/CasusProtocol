# Casus Protocol
The Casus Protocol is a bauble that attempts to "correct" flaws in the surrounding world. When worn and powered with RF, the item will scan the player and the surrounding area and activate a "protocol" which will perform an specified action. Once a protocol has activated, the item will go on cooldown for a short duration.

## Protocols (Vanilla)
- **Burning** - Activates on player taking fire damage. Extinguishes the player and grants fire immunity for 10 seconds.
- **Creeper** - Activates when creeper is detected nearby. Transforms creeper into two leaf blocks.
- **Cure Villager** - Activates when Zombie Villager is detected nearby. Begins the Zombie Villager cure conversion process.
- **Dragon Breath** - Activates when Dragon's Breath puddles are detected nearby. Removes nearby Dragon's Breath clouds.
- **Drowning** - Activates when taking drowning damage. Negates drowning damage and grants water breathing for 10 seconds.
- **Escape** - Activates when taking non-fatal damage that drops player below 4 health. Grants +10% movements speed and two hearts of absorption for 10 seconds.
- **Falling** - Activates when taking fall damage. Negates a single instance of fall damage taken.
- **Guardian Beam** - Activates when taking damage from a Guardian's beam. Grants a full oxygen bar and +10% increased swim speed for 10 seconds.
- **Harmful Effect** - Activates when inflicted with a negative effect. Stops the negative effect from applying and grants immunity to that effect for 10 seconds. By default Casus Protocol supports poison and wither, but additional effects can be added or removed using the configuration.
- **Restore Health** - Activates when player is below 50% health. Heals the player 2 hearts.
- **Restore Hunger** - Activates when the player is missing 4 bars of hunger. Grants 2 bars of hunger.

## Protocols (Modded)
- **Exposure** - Requires [Survival Tools](https://www.curseforge.com/minecraft/mc-mods/survival-tools). Attempts to equalize the player's temperature, increasing or decreasing player body heat.
- **Restore Aura** - Requires [Nature's Aura](https://www.curseforge.com/minecraft/mc-mods/natures-aura). Attempts to fill an empty Aura Trove in the player's inventory with a small amount of Aura.
- **Restore Ember** - Requires [Embers Rekindled](https://www.curseforge.com/minecraft/mc-mods/embers-rekindled). Attempts to fill an empty Ember container in the player's inventory with a small amount of Ember.
- **Restore Mana** - Requires [Botania](https://www.curseforge.com/minecraft/mc-mods/botania). Attempts to fill an empty Mana Tablet in the player's inventory with a small amount of mana.
- **Restore Thirst** - Requires [Survival Tools](https://www.curseforge.com/minecraft/mc-mods/survival-tools). Activates when the player is missing 4 thirst. Grants 2 bars of thirst.
- **Restore Vis** - Requires [Thaumcraft](https://www.curseforge.com/minecraft/mc-mods/thaumcraft). Attempts to fill an empty Vis consuming item in the player's inventory with a small amount of Vis.

## Configuration
Each protocol can be configured independently from one another. Protocol configurations include:
- Enable/Disable - Enable or disable the protocol.
- Cooldown - Determines the item cooldown following a protocol activation.
- Duration - Sets the duration of bonus effects applied by some protocols.
- Energy Cost - Determines the amount of RF consumed when the protocol activates.

## Credits
This mod was made as a thank you to CasusCloud of the Magiculture Team Modpacks server for his effort in finding the cause of a bug that had been plaguing the [Magiculture 2](https://www.curseforge.com/minecraft/modpacks/magiculture-2) modpack for years. Seriously, without his effort we'd still be beating our heads against the wall trying to figure it out.