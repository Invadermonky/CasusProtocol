package com.invadermonky.casusprotocol.protocols;

import baubles.api.BaubleType;
import com.google.common.collect.Multimap;
import com.invadermonky.casusprotocol.CasusProtocol;
import com.invadermonky.casusprotocol.utils.ConfigHandlerCP;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ProtocolRestoreHealth implements IProtocol {
    public static final ResourceLocation NAME = new ResourceLocation(CasusProtocol.MOD_ID, "restore_health");

    @Override
    public ResourceLocation getProtocolName() {
        return NAME;
    }

    @Override
    public boolean canActivate(EntityPlayer player, ItemStack stack) {
        return player.getHealth() < (player.getMaxHealth() / 2);
    }

    @Override
    public void activateProtocol(EntityPlayer player, ItemStack stack) {
        player.heal(4.0f);
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

    @SideOnly(Side.CLIENT)
    @Override
    public void addTooltip(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {}

    @Override
    public int getEnergyCost() {
        return ConfigHandlerCP.protocolSettings.restoreHealth.energyCost;
    }

    @Override
    public int getCooldown() {
        return ConfigHandlerCP.protocolSettings.restoreHealth.cooldown;
    }

    @Override
    public boolean hasEvent() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCP.protocolSettings.restoreHealth.enable;
    }
}
