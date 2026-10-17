package com.invadermonky.casusprotocol;

import com.invadermonky.casusprotocol.protocols.*;
import com.invadermonky.casusprotocol.utils.ConfigHandlerCP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProtocolRegistry {
    public static Map<ResourceLocation, IProtocol> protocols = new HashMap<>();

    public static void registerProtocol(IProtocol protocol) {
        if(protocol.isEnabled()) {
            protocols.put(protocol.getProtocolName(), protocol);
            if(protocol.hasEvent()) {
                MinecraftForge.EVENT_BUS.register(protocol);
            }
        } else {
            protocols.remove(protocol.getProtocolName());
            if(protocol.hasEvent()) {
                MinecraftForge.EVENT_BUS.unregister(protocol);
            }
        }
    }

    public static List<IProtocol> getPossibleProtocols(EntityPlayer player, ItemStack stack) {
        return protocols.values().stream().filter(protocol -> protocol.canActivate(player, stack)).collect(Collectors.toList());
    }

    @Nullable
    public static IProtocol getProtocol(ResourceLocation protocolName) {
        return protocols.get(protocolName);
    }

    @Nullable
    public static IProtocol getProtocol(String protocolName) {
        return getProtocol(new ResourceLocation(protocolName));
    }

    public static void initProtocols() {
        registerProtocol(new ProtocolRestoreAura());
        registerProtocol(new ProtocolBurning());
        registerProtocol(new ProtocolCreeper());
        registerProtocol(new ProtocolDragonBreath());
        registerProtocol(new ProtocolDrowning());
        registerProtocol(new ProtocolRestoreEmber());
        registerProtocol(new ProtocolEscape());
        registerProtocol(new ProtocolExposure());
        registerProtocol(new ProtocolFalling());
        registerProtocol(new ProtocolGuardianBeam());
        registerProtocol(new ProtocolRestoreHealth());
        registerProtocol(new ProtocolRestoreHealth());
        registerProtocol(new ProtocolRestoreHunger());
        registerProtocol(new ProtocolRestoreMana());
        registerProtocol(new ProtocolRestoreThirst());
        registerProtocol(new ProtocolRestoreVis());
        registerProtocol(new ProtocolCureVillager());
        for(String str : ConfigHandlerCP.protocolSettings.harmfulEffect.potionEffects) {
            Potion potion = ForgeRegistries.POTIONS.getValue(new ResourceLocation(str));
            if(potion != null) {
                registerProtocol(new ProtocolHarmfulEffect(potion));
            }
        }
    }

}
