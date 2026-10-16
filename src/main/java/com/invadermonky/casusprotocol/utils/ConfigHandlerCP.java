package com.invadermonky.casusprotocol.utils;

import com.invadermonky.casusprotocol.CasusProtocol;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = CasusProtocol.MOD_ID)
public class ConfigHandlerCP {
    @Config.LangKey("config.casusprotocol.item_settings")
    public static final ItemSettings itemSettings = new ItemSettings();
    @Config.LangKey("config.casusprotocol.protocol_settings")
    public static final ProtocolSettings protocolSettings = new ProtocolSettings();

    public static class ItemSettings {
        @Config.RequiresMcRestart
        @Config.Name("Use RF")
        @Config.Comment({
                "The Casus Protocol item will use RF whenever a protocol activates. Individual protocol costs can be",
                "set in their configuration settings."
        })
        public boolean useRF = true;

        @Config.RangeInt(min = 1, max = 10000)
        @Config.Name("Update Interval")
        @Config.Comment({
                "The time, in ticks, between each protocol activation check. Protocols fired by events are excluded",
                "from this delay."
        })
        public int updateInterval = 200;
    }


    public static class ProtocolSettings {
        @Config.LangKey("config.casusprotocol.burning")
        @Config.Comment("Extinguishes the player and proveids immunite to fire for a set duration.")
        public DurationProtocolConfig burning = new DurationProtocolConfig();
        @Config.LangKey("config.casusprotocol.creeper")
        @Config.Comment("Targets a nearby creeper and transforms it into leaves.")
        public GenericProtocolConfig creeper = new GenericProtocolConfig();
        @Config.LangKey("config.casusprotocol.cure_villager")
        @Config.Comment("Targets a nearby zombie villager and begins the cure zombification process.")
        public GenericProtocolConfig cureVillager = new GenericProtocolConfig();
        @Config.LangKey("config.casusprotocol.dragon_breath")
        @Config.Comment("Detects and cleans up nearby puddles of Dragon's Breath.")
        public GenericProtocolConfig dragonBreath = new GenericProtocolConfig();
        @Config.LangKey("config.casusprotocol.drowning")
        @Config.Comment("Prevents drowning and grants water breathing for a short duration.")
        public DurationProtocolConfig drowning = new DurationProtocolConfig();
        @Config.LangKey("config.casusprotocol.escape")
        @Config.Comment("Grants movement speed and a small amount of absorption after taking damage that reduces the player to low health.")
        public DurationProtocolConfig escape = new DurationProtocolConfig();
        @Config.LangKey("config.casusprotocol.exposure")
        @Config.Comment("Attempts to regulate player temperature. Requires Survival Tools to be installed.")
        public GenericProtocolConfig exposure = new GenericProtocolConfig();
        @Config.LangKey("config.casusprotocol.falling")
        @Config.Comment("Negates one instance of fall damage.")
        public GenericProtocolConfig falling = new GenericProtocolConfig();
        @Config.LangKey("config.casusprotocol.guardian_beam")
        @Config.Comment("When hit by guardian beam damage, grants a full oxygen bar and grants increased swim speed for s hort duration.")
        public DurationProtocolConfig guardianBeam = new DurationProtocolConfig();
        @Config.LangKey("config.casusprotocol.harmful_effects")
        @Config.Comment("Removes specific harmful potion effects and grants immunity to that effect for a short duration.")
        public HarmfulEffectProtocolConfig harmfulEffect = new HarmfulEffectProtocolConfig();
        @Config.LangKey("config.casusprotocol.lava")
        @Config.Comment("Negates one instance of lava damage and grants Fire Resistance for a short duration.")
        public DurationProtocolConfig lava = new DurationProtocolConfig();
        @Config.LangKey("config.casusprotocol.restore_aura_na")
        @Config.Comment("Requires Nature's Aura. Attempts to fill an empty Aura Trove in the player's inventory with a small amount of aura.")
        public GenericProtocolConfig restoreAuraNA = new GenericProtocolConfig();
        @Config.LangKey("config.casusprotocol.restore_ember_embers")
        @Config.Comment("Requires Embers. Attempts to fill an empty Ember container in the player's inventory with a small amount of ember.")
        public GenericProtocolConfig restoreEmberEmbers = new GenericProtocolConfig();
        @Config.LangKey("config.casusprotocol.restore_health")
        @Config.Comment("Restores two hearts of health if the player's health is below 50%.")
        public GenericProtocolConfig restoreHealth = new GenericProtocolConfig();
        @Config.LangKey("config.casusprotocol.restore_hunger")
        @Config.Comment("Restores two bars of hunger if the player's hunger is low.")
        public GenericProtocolConfig restoreHunger = new GenericProtocolConfig();
        @Config.LangKey("config.casusprotocol.restore_mana_bot")
        @Config.Comment("Requires Botania. Attempts to fill an emtpy Mana Tablet in the player's inventory with a small amount of mana.")
        public GenericProtocolConfig restoreManaBot = new GenericProtocolConfig();
        @Config.LangKey("config.casusprotocol.restore_thirst")
        @Config.Comment("Requires Survival Tools. Restores a small amount of thirst if the player's thirst is too low.")
        public GenericProtocolConfig restoreThirst = new GenericProtocolConfig();
        @Config.LangKey("config.casusprotocol.restore_vis_tc")
        @Config.Comment("Requires Thaumcraft. Attempts to fill an emtpy Vis consuming item in the player's inventory with a small amount of Vis.")
        public GenericProtocolConfig restoreVisTC = new GenericProtocolConfig();

        public static class DurationProtocolConfig {
            @Config.RangeInt(min = 0, max = 10000)
            @Config.Name("Protocol Cooldown")
            @Config.Comment("The cooldown incurred by the Casus Protocol after removing a harmful effects.")
            public int cooldown = 1200;

            @Config.RangeInt(min = 0, max = 10000)
            @Config.Name("Protocol Duration")
            @Config.Comment("The duration, in ticks, this protocol will last after activating.")
            public int duration = 200;

            @Config.RequiresMcRestart
            @Config.Name("Enable Protocol")
            @Config.Comment("Enables this protocol effect.")
            public boolean enable = true;

            @Config.RangeInt(min = 0, max = 50000)
            @Config.Name("Energy Cost")
            @Config.Comment("The energy cost, in RF, this protocol will consume when activated.")
            public int energyCost = 2000;
        }

        public static class GenericProtocolConfig {
            @Config.RangeInt(min = 0, max = 10000)
            @Config.Name("Protocol Cooldown")
            @Config.Comment("The cooldown incurred by the Casus Protocol after removing a harmful effects.")
            public int cooldown = 3600;

            @Config.RequiresMcRestart
            @Config.Name("Enable Protocol")
            @Config.Comment("Enables this protocol effect.")
            public boolean enable = true;

            @Config.RangeInt(min = 0, max = 50000)
            @Config.Name("Energy Cost")
            @Config.Comment("The energy cost, in RF, this protocol will consume when activated.")
            public int energyCost = 2000;
        }

        public static class HarmfulEffectProtocolConfig {
            @Config.RangeInt(min = 0, max = 10000)
            @Config.Name("Protocol Cooldown")
            @Config.Comment("The cooldown incurred by the Casus Protocol after removing a harmful effects.")
            public int cooldown = 3600;

            @Config.RangeInt(min = 0, max = 10000)
            @Config.Name("Effect Immunity Duration")
            @Config.Comment("The duration, in ticks, the player will be immune to this effect after the protocol activates.")
            public int duration = 200;

            @Config.RangeInt(min = 0, max = 50000)
            @Config.Name("Energy Cost")
            @Config.Comment("The energy cost, in RF, this protocol will consume when activated.")
            public int energyCost = 2000;

            @Config.RequiresMcRestart
            @Config.Name("Protocol Effects")
            @Config.Comment({
                    "The registry Ids of harmful potion effects that will be removed by this protocol. Each entry is its",
                    "own unique protocol that will only provide removal and immunity for that specific effect."
            })
            public String[] potionEffects = new String[] {
                    "minecraft:poison",
                    "minecraft:wither"
            };
        }
    }

    @Mod.EventBusSubscriber(modid = CasusProtocol.MOD_ID)
    public static class EventChangeListener {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(CasusProtocol.MOD_ID)) {
                ConfigManager.sync(CasusProtocol.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
