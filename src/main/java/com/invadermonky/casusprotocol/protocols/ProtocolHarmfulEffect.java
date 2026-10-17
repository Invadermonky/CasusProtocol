package com.invadermonky.casusprotocol.protocols;

import baubles.api.BaubleType;
import com.google.common.collect.Multimap;
import com.invadermonky.casusprotocol.utils.ConfigHandlerCP;
import com.invadermonky.casusprotocol.utils.ProtocolHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ProtocolHarmfulEffect implements IProtocol {
    private final Potion potion;

    public ProtocolHarmfulEffect(Potion potion) {
        this.potion = potion;
    }

    @SubscribeEvent
    public void onPotionApplied(PotionEvent.PotionApplicableEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (event.getPotionEffect().getPotion() == this.potion) {
                ItemStack stack = ProtocolHelper.getProtocolItem(player);
                if(stack.isEmpty())
                    return;

                if(ProtocolHelper.isProtocolActive(player, stack, this)) {
                    event.setResult(Event.Result.DENY);
                } else if(ProtocolHelper.canProtocolActivate(player, stack, this)) {
                    ProtocolHelper.setProtocolActivated(player, stack, this);
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    @Override
    public ResourceLocation getProtocolName() {
        return this.potion.getRegistryName();
    }

    @Override
    public boolean canActivate(EntityPlayer player, ItemStack stack) {
        return player.isPotionActive(this.potion);
    }

    @Override
    public void activateProtocol(EntityPlayer player, ItemStack stack) {
        player.removePotionEffect(this.potion);
    }

    @Override
    public int getProtocolDuration(EntityPlayer player, ItemStack stack) {
        return ConfigHandlerCP.protocolSettings.harmfulEffect.duration;
    }

    @Override
    public void onProtocolTick(EntityPlayer player, ItemStack stack) {
        if(player.isPotionActive(this.potion)) {
            player.removePotionEffect(this.potion);
        }
    }

    @Override
    public boolean hasAttributeModifier() {
        return false;
    }

    @Override
    public void getAttributeModifiers(Multimap<String, AttributeModifier> multimap, BaubleType type, ItemStack stack) {}

    @SideOnly(Side.CLIENT)
    @Override
    public void addTooltip(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GREEN + I18n.format("tooltip.casusprotocol:immunity", I18n.format(this.potion.getName())));
    }

    @Override
    public int getEnergyCost() {
        return ConfigHandlerCP.protocolSettings.harmfulEffect.energyCost;
    }

    @Override
    public int getCooldown() {
        return ConfigHandlerCP.protocolSettings.harmfulEffect.cooldown;
    }

    @Override
    public boolean hasEvent() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
