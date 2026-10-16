package com.invadermonky.casusprotocol.protocols;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import com.google.common.collect.Multimap;
import com.invadermonky.casusprotocol.CasusProtocol;
import com.invadermonky.casusprotocol.utils.ConfigHandlerCP;
import com.invadermonky.casusprotocol.utils.ModIds;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import teamroots.embers.api.capabilities.EmbersCapabilities;
import teamroots.embers.api.power.IEmberCapability;

import java.util.List;

public class ProtocolRestoreEmber implements IProtocol {
    public static final ResourceLocation NAME = new ResourceLocation(CasusProtocol.MOD_ID, "restore_ember");

    @Override
    public ResourceLocation getProtocolName() {
        return NAME;
    }

    @Override
    public boolean canActivate(EntityPlayer player, ItemStack stack) {
        IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
        for(int i = 0; i < handler.getSlots(); i++) {
            ItemStack slotStack = handler.getStackInSlot(i);
            IEmberCapability capability = slotStack.getCapability(EmbersCapabilities.EMBER_CAPABILITY, null);
            if(capability != null) {
                return capability.getEmber() == 0;
            }
        }

        for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack slotStack = player.inventory.getStackInSlot(i);
            IEmberCapability capability = slotStack.getCapability(EmbersCapabilities.EMBER_CAPABILITY, null);
            if(capability != null) {
                return capability.getEmber() == 0;
            }
        }
        return false;
    }

    @Override
    public void activateProtocol(EntityPlayer player, ItemStack stack) {
        IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
        for(int i = 0; i < handler.getSlots(); i++) {
            ItemStack slotStack = handler.getStackInSlot(i);
            IEmberCapability capability = slotStack.getCapability(EmbersCapabilities.EMBER_CAPABILITY, null);
            if(capability != null) {
                capability.addAmount(10, false);
                return;
            }
        }

        for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack slotStack = player.inventory.getStackInSlot(i);
            IEmberCapability capability = slotStack.getCapability(EmbersCapabilities.EMBER_CAPABILITY, null);
            if(capability != null) {
                capability.addAmount(10, false);
                return;
            }
        }
    }

    @Override
    public int getProtocolDuration(EntityPlayer player, ItemStack stack) {
        return 0;
    }

    @Override
    public void onProtocolTick(EntityPlayer player, ItemStack stack) {}

    @Override
    public boolean hasAttributeModifier() {
        return false;
    }

    @Override
    public void getAttributeModifiers(Multimap<String, AttributeModifier> multimap, BaubleType type, ItemStack stack) {}

    @Override
    public void addTooltip(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {}

    @Override
    public int getEnergyCost() {
        return ConfigHandlerCP.protocolSettings.restoreEmberEmbers.energyCost;
    }

    @Override
    public int getCooldown() {
        return ConfigHandlerCP.protocolSettings.restoreEmberEmbers.cooldown;
    }

    @Override
    public boolean hasEvent() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return ModIds.embers.isLoaded && ConfigHandlerCP.protocolSettings.restoreEmberEmbers.enable;
    }
}
