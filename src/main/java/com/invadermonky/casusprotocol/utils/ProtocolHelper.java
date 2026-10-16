package com.invadermonky.casusprotocol.utils;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import com.invadermonky.casusprotocol.CasusProtocol;
import com.invadermonky.casusprotocol.ProtocolRegistry;
import com.invadermonky.casusprotocol.protocols.IProtocol;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

import static com.invadermonky.casusprotocol.items.ItemCasusProtocol.*;

public class ProtocolHelper {
    public static ItemStack getProtocolItem(EntityPlayer player) {
        IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
        for(int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if(!stack.isEmpty() && stack.getItem() == CasusProtocol.CASUS_PROTOCOL) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    public static boolean isProtocolActive(EntityPlayer player, ItemStack stack, IProtocol protocol) {
        if(!stack.isEmpty()) {
            IProtocol stackProtocol = getProtocol(stack);
            return stackProtocol == protocol && getDuration(stack) > 0;
        }
        return false;
    }

    public static boolean canProtocolActivate(EntityPlayer player, ItemStack stack, IProtocol protocol) {
        if(!stack.isEmpty() && getCooldown(stack) <= 0) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            return storage == null || ProtocolHelper.getEnergy(stack) >= protocol.getEnergyCost();
        }
        return false;
    }

    public static void setProtocolActivated(EntityPlayer player, ItemStack stack, IProtocol protocol) {
        setProtocol(stack, protocol);
        setCooldown(stack, protocol.getCooldown());
        drainEnergy(stack, protocol.getEnergyCost());
        setHasAttributeModifier(stack, protocol.hasAttributeModifier());
        int duration = protocol.getProtocolDuration(player, stack);
        if(duration > 0) {
            setDuration(stack, duration);
        }
        if(!player.world.isRemote) {
            String message = "chat." + protocol.getProtocolName().toString();
            if(I18n.canTranslate(message)) {
                player.sendStatusMessage(new TextComponentTranslation(message).setStyle(new Style().setColor(TextFormatting.DARK_AQUA)), true);
            }
        }
    }

    public static void removeActiveProtocol(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        if(tag != null) {
            tag.removeTag(TAG_DURATION);
            tag.removeTag(TAG_HAS_ATTRIBUTES);
            tag.removeTag(TAG_PROTOCOL);
        }
    }

    public static int getCooldown(ItemStack stack) {
        return stack.getTagCompound() != null ? stack.getTagCompound().getInteger(TAG_COOLDOWN) : 0;
    }

    public static void setCooldown(ItemStack stack, int cooldown) {
        stack.setTagInfo(TAG_COOLDOWN, new NBTTagInt(cooldown));
    }

    public static int getDuration(ItemStack stack) {
        return stack.getTagCompound() != null ? stack.getTagCompound().getInteger(TAG_DURATION) : 0;
    }

    public static void setDuration(ItemStack stack, int duration) {
        stack.setTagInfo(TAG_DURATION, new NBTTagInt(duration));
    }

    public static int getEnergy(ItemStack stack) {
        return stack.getTagCompound() != null ? stack.getTagCompound().getInteger(TAG_ENERGY) : 0;
    }

    public static void setEnergy(ItemStack stack, int energy) {
        stack.setTagInfo(TAG_ENERGY, new NBTTagInt(energy));
    }

    public static void drainEnergy(ItemStack stack, int drainedEnergy) {
        setEnergy(stack, Math.max(0, getEnergy(stack) - drainedEnergy));
    }

    public static boolean getHasAttributeModifier(ItemStack stack) {
        return stack.getTagCompound() == null || stack.getTagCompound().getBoolean(TAG_HAS_ATTRIBUTES);
    }

    public static void setHasAttributeModifier(ItemStack stack, boolean hasAttributesModifiers) {
        if(getHasAttributeModifier(stack) != hasAttributesModifiers) {
            stack.setTagInfo(TAG_HAS_ATTRIBUTES, new NBTTagByte(((byte) (hasAttributesModifiers ? 1 : 0))));
        }
    }

    @Nullable
    public static IProtocol getProtocol(ItemStack stack) {
        String protocolName = stack.getTagCompound() != null ? stack.getTagCompound().getString(TAG_PROTOCOL) : "";
        return !protocolName.isEmpty() ? ProtocolRegistry.getProtocol(protocolName) : null;
    }

    public static void setProtocol(ItemStack stack, @Nullable IProtocol protocol) {
        if(protocol == null) {
            if(stack.getTagCompound() != null) {
                stack.getTagCompound().removeTag(TAG_PROTOCOL);
            }
        } else {
            stack.setTagInfo(TAG_PROTOCOL, new NBTTagString(protocol.getProtocolName().toString()));
        }
    }
}
