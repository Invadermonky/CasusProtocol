package com.invadermonky.casusprotocol.protocols;

import baubles.api.BaubleType;
import com.google.common.collect.Multimap;
import com.invadermonky.casusprotocol.CasusProtocol;
import com.invadermonky.casusprotocol.utils.ConfigHandlerCP;
import com.invadermonky.casusprotocol.utils.ProtocolHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.UUID;

public class ProtocolEscape implements IProtocol {
    public static final ResourceLocation NAME = new ResourceLocation(CasusProtocol.MOD_ID, "escape");
    public static final UUID MODIFIER_UUID = UUID.fromString("ba62bf27-faf3-4f3a-9d5a-dbaeb8ed659a");
    public static final AttributeModifier MOVEMENT_SPEED = new AttributeModifier(MODIFIER_UUID, "Movement speed modifier", 0.1, Constants.AttributeModifierOperation.MULTIPLY);

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLivingHurt(LivingDamageEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer && event.getSource().getTrueSource() instanceof EntityMob) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if(player.getHealth() - event.getAmount() <= 4.0) {
                ItemStack stack = ProtocolHelper.getProtocolItem(player);
                if(stack.isEmpty() || !ProtocolHelper.canProtocolActivate(player, stack, this) || MobEffects.ABSORPTION == null) {
                    return;
                }

                ProtocolHelper.setProtocolActivated(player, stack, this);
                player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, this.getProtocolDuration(player, stack), 0, true, false));
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
        return ConfigHandlerCP.protocolSettings.escape.duration;
    }

    @Override
    public void onProtocolTick(EntityPlayer player, ItemStack stack) {}

    @Override
    public boolean hasAttributeModifier() {
        return true;
    }

    @Override
    public void getAttributeModifiers(Multimap<String, AttributeModifier> multimap, BaubleType type, ItemStack stack) {
        multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), MOVEMENT_SPEED);
    }

    @Override
    public void addTooltip(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {}

    @Override
    public int getEnergyCost() {
        return ConfigHandlerCP.protocolSettings.escape.energyCost;
    }

    @Override
    public int getCooldown() {
        return ConfigHandlerCP.protocolSettings.escape.cooldown;
    }

    @Override
    public boolean hasEvent() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCP.protocolSettings.escape.enable;
    }
}
