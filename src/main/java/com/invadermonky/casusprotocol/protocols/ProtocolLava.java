package com.invadermonky.casusprotocol.protocols;

import baubles.api.BaubleType;
import com.google.common.collect.Multimap;
import com.invadermonky.casusprotocol.CasusProtocol;
import com.invadermonky.casusprotocol.utils.ConfigHandlerCP;
import com.invadermonky.casusprotocol.utils.ProtocolHelper;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class ProtocolLava implements IProtocol {
    public static final ResourceLocation NAME = new ResourceLocation(CasusProtocol.MOD_ID, "lava");

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if(event.getSource() == DamageSource.LAVA && player.isInsideOfMaterial(Material.LAVA)) {
                ItemStack stack = ProtocolHelper.getProtocolItem(player);
                if (stack.isEmpty() || !ProtocolHelper.canProtocolActivate(player, stack, this) || MobEffects.FIRE_RESISTANCE == null) {
                    return;
                }

                ProtocolHelper.setProtocolActivated(player, stack, this);
                player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, this.getProtocolDuration(player, stack), 0, true, false));
                if(player.world.isRemote) {
                    player.playSound(SoundEvents.ENTITY_SPLASH_POTION_BREAK, 1.0f, 1.0f);
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
        return ConfigHandlerCP.protocolSettings.lava.duration;
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
        return ConfigHandlerCP.protocolSettings.lava.energyCost;
    }

    @Override
    public int getCooldown() {
        return ConfigHandlerCP.protocolSettings.lava.cooldown;
    }

    @Override
    public boolean hasEvent() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCP.protocolSettings.lava.enable;
    }
}
