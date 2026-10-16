package com.invadermonky.casusprotocol.protocols;

import baubles.api.BaubleType;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public interface IProtocol {

    ResourceLocation getProtocolName();

    boolean canActivate(EntityPlayer player, ItemStack stack);

    void activateProtocol(EntityPlayer player, ItemStack stack);

    int getProtocolDuration(EntityPlayer player, ItemStack stack);

    void onProtocolTick(EntityPlayer player, ItemStack stack);

    boolean hasAttributeModifier();

    void getAttributeModifiers(Multimap<String, AttributeModifier> multimap, BaubleType type, ItemStack stack);

    @SideOnly(Side.CLIENT)
    void addTooltip(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn);

    int getEnergyCost();

    int getCooldown();

    boolean hasEvent();

    boolean isEnabled();
}
