package com.invadermonky.casusprotocol.protocols;

import baubles.api.BaubleType;
import com.google.common.collect.Multimap;
import com.invadermonky.casusprotocol.CasusProtocol;
import com.invadermonky.casusprotocol.utils.ConfigHandlerCP;
import com.invadermonky.casusprotocol.utils.ProtocolHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class ProtocolBurning implements IProtocol {
    public static final ResourceLocation NAME = new ResourceLocation(CasusProtocol.MOD_ID, "burning");
    @SubscribeEvent
    public void onPlayerBurning(LivingHurtEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if(event.getSource() == DamageSource.ON_FIRE || event.getSource() == DamageSource.IN_FIRE) {
                ItemStack stack = ProtocolHelper.getProtocolItem(player);
                if (stack.isEmpty()) {
                    return;
                }

                if(ProtocolHelper.isProtocolActive(player, stack, this)) {
                    player.extinguish();
                    event.setAmount(0);
                    event.setCanceled(true);
                } else if(ProtocolHelper.canProtocolActivate(player, stack, this)) {
                    ProtocolHelper.setProtocolActivated(player, stack, this);
                    player.extinguish();
                    event.setAmount(0);
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public ResourceLocation getProtocolName() {
        return NAME;
    }

    @Override
    public boolean canActivate(EntityPlayer player, ItemStack stack) {
        return false;
    }

    @Override
    public void activateProtocol(EntityPlayer player, ItemStack stack) {}

    @Override
    public int getProtocolDuration(EntityPlayer player, ItemStack stack) {
        return ConfigHandlerCP.protocolSettings.burning.duration;
    }

    @Override
    public void onProtocolTick(EntityPlayer player, ItemStack stack) {
        if(player.isBurning()) {
            player.extinguish();
        }
    }

    @Override
    public boolean hasAttributeModifier() {
        return false;
    }

    @Override
    public void getAttributeModifiers(Multimap<String, AttributeModifier> multimap, BaubleType type, ItemStack stack) {}

    @Override
    public void addTooltip(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.AQUA + I18n.format("tooltip.casusprotocol:extinguish"));
    }

    @Override
    public int getEnergyCost() {
        return ConfigHandlerCP.protocolSettings.burning.energyCost;
    }

    @Override
    public int getCooldown() {
        return ConfigHandlerCP.protocolSettings.burning.cooldown;
    }

    @Override
    public boolean hasEvent() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCP.protocolSettings.burning.enable;
    }
}
