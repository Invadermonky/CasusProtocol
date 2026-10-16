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
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class ProtocolDrowning implements IProtocol {
    public static final ResourceLocation NAME = new ResourceLocation(CasusProtocol.MOD_ID, "drowning");

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if(event.getSource() == DamageSource.DROWN && player.getAir() <= 0) {
                ItemStack stack = ProtocolHelper.getProtocolItem(player);
                if(stack.isEmpty() || !ProtocolHelper.canProtocolActivate(player, stack, this)) {
                    return;
                }

                ProtocolHelper.setProtocolActivated(player, stack, this);
                player.setAir(300);
                event.setAmount(0);
                event.setCanceled(true);

                if(player.world.isRemote) {
                    player.playSound(SoundEvents.ENTITY_GENERIC_SPLASH, 1.0f, 1.0f);
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
        return ConfigHandlerCP.protocolSettings.drowning.duration;
    }

    @Override
    public void onProtocolTick(EntityPlayer player, ItemStack stack) {
        if(player.getAir() < 300) {
            player.setAir(300);
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
        tooltip.add(TextFormatting.AQUA + I18n.format("tooltip.casusprotocol:underwater_breathing"));
    }

    @Override
    public int getEnergyCost() {
        return ConfigHandlerCP.protocolSettings.drowning.energyCost;
    }

    @Override
    public int getCooldown() {
        return ConfigHandlerCP.protocolSettings.drowning.cooldown;
    }

    @Override
    public boolean hasEvent() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCP.protocolSettings.drowning.enable;
    }
}
