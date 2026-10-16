package com.invadermonky.casusprotocol;

import com.invadermonky.casusprotocol.items.ItemCasusProtocol;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

@Mod(
        modid = CasusProtocol.MOD_ID,
        name = CasusProtocol.MOD_NAME,
        version = CasusProtocol.MOD_VERSION,
        dependencies = CasusProtocol.DEPENDENCIES
)
public class CasusProtocol {
    public static final String MOD_ID = Tags.MOD_ID;
    public static final String MOD_NAME = Tags.MOD_NAME;
    public static final String MOD_VERSION = Tags.VERSION;
    public static final String DEPENDENCIES = "required-after:baubles" +
            ";required-after:expandedevents";

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    public static Item CASUS_PROTOCOL;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ProtocolRegistry.initProtocols();
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        CASUS_PROTOCOL = new ItemCasusProtocol();
        event.getRegistry().register(CASUS_PROTOCOL);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(CASUS_PROTOCOL, 0, new ModelResourceLocation(Objects.requireNonNull(CASUS_PROTOCOL.getRegistryName()), "inventory"));
    }
}
