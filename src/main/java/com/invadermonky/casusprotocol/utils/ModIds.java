package com.invadermonky.casusprotocol.utils;

import net.minecraftforge.fml.common.Loader;

public enum ModIds {
    botania("botania"),
    embers("embers"),
    natures_aura("naturesaura"),
    survival_tools("survivaltools"),
    thaumcraft("thaumcraft");

    public final String modId;
    public final boolean isLoaded;

    ModIds(String modId) {
        this.modId = modId;
        this.isLoaded = Loader.isModLoaded(modId);
    }
}
