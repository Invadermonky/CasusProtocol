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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.mana.IManaItem;

import java.util.List;

public class ProtocolRestoreMana implements IProtocol {
    public static final ResourceLocation NAME = new ResourceLocation(CasusProtocol.MOD_ID, "restore_mana_bot");

    @Override
    public ResourceLocation getProtocolName() {
        return NAME;
    }

    @Override
    public boolean canActivate(EntityPlayer player, ItemStack stack) {
        IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
        for(int i = 0; i < handler.getSlots(); i++) {
            ItemStack slotStack = handler.getStackInSlot(i);
            if(!slotStack.isEmpty() && slotStack.getItem() instanceof IManaItem) {
                return ((IManaItem) slotStack.getItem()).getMana(slotStack) <= 0;
            }
        }

        for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack slotStack = player.inventory.getStackInSlot(i);
            if(!slotStack.isEmpty() && slotStack.getItem() instanceof IManaItem) {
                return ((IManaItem) slotStack.getItem()).getMana(slotStack) <= 0;
            }
        }
        return false;
    }

    @Override
    public void activateProtocol(EntityPlayer player, ItemStack stack) {
        IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
        for(int i = 0; i < handler.getSlots(); i++) {
            ItemStack slotStack = handler.getStackInSlot(i);
            if(!slotStack.isEmpty() && slotStack.getItem() instanceof IManaItem) {
                ((IManaItem) slotStack.getItem()).addMana(slotStack, 2000);
                return;
            }
        }

        for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack slotStack = player.inventory.getStackInSlot(i);
            if(!slotStack.isEmpty() && slotStack.getItem() instanceof IManaItem) {
                ((IManaItem) slotStack.getItem()).addMana(slotStack, 2000);
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

    @SideOnly(Side.CLIENT)
    @Override
    public void addTooltip(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {}

    @Override
    public int getEnergyCost() {
        return ConfigHandlerCP.protocolSettings.restoreManaBot.energyCost;
    }

    @Override
    public int getCooldown() {
        return ConfigHandlerCP.protocolSettings.restoreManaBot.cooldown;
    }

    @Override
    public boolean hasEvent() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return ModIds.botania.isLoaded && ConfigHandlerCP.protocolSettings.restoreManaBot.enable;
    }
}
