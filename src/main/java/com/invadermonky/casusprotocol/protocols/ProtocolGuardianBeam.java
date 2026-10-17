package com.invadermonky.casusprotocol.protocols;

import baubles.api.BaubleType;
import com.google.common.collect.Multimap;
import com.invadermonky.casusprotocol.CasusProtocol;
import com.invadermonky.casusprotocol.utils.ConfigHandlerCP;
import com.invadermonky.casusprotocol.utils.ProtocolHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.UUID;

public class ProtocolGuardianBeam implements IProtocol {
    public static final ResourceLocation NAME = new ResourceLocation(CasusProtocol.MOD_ID, "guardian_beam");
    public static final UUID MODIFIER_UUID = UUID.fromString("9c713303-939f-46bf-a42b-fbab82c64646");
    public static final AttributeModifier SWIM_SPEED = new AttributeModifier(MODIFIER_UUID, "Swim speed modifier", 0.1, Constants.AttributeModifierOperation.MULTIPLY);

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLivingHurt(LivingDamageEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if(event.getSource().getTrueSource() instanceof EntityGuardian && !event.getSource().damageType.equals("thorns")) {
                ItemStack stack = ProtocolHelper.getProtocolItem(player);
                if(stack.isEmpty() || !ProtocolHelper.canProtocolActivate(player, stack, this)) {
                    return;
                }

                ProtocolHelper.setProtocolActivated(player, stack, this);
                player.setAir(300);
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
        return ConfigHandlerCP.protocolSettings.guardianBeam.duration;
    }

    @Override
    public void onProtocolTick(EntityPlayer player, ItemStack stack) {}

    @Override
    public boolean hasAttributeModifier() {
        return true;
    }

    @Override
    public void getAttributeModifiers(Multimap<String, AttributeModifier> multimap, BaubleType type, ItemStack stack) {
        multimap.put(EntityPlayer.SWIM_SPEED.getName(), SWIM_SPEED);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addTooltip(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {}

    @Override
    public int getEnergyCost() {
        return ConfigHandlerCP.protocolSettings.guardianBeam.energyCost;
    }

    @Override
    public int getCooldown() {
        return ConfigHandlerCP.protocolSettings.guardianBeam.cooldown;
    }

    @Override
    public boolean hasEvent() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCP.protocolSettings.guardianBeam.enable;
    }
}
