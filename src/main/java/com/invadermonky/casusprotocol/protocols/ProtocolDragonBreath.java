package com.invadermonky.casusprotocol.protocols;

import baubles.api.BaubleType;
import com.google.common.collect.Multimap;
import com.invadermonky.casusprotocol.CasusProtocol;
import com.invadermonky.casusprotocol.utils.ConfigHandlerCP;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class ProtocolDragonBreath implements IProtocol {
    public static final ResourceLocation NAME = new ResourceLocation(CasusProtocol.MOD_ID, "dragon_breath");

    @Override
    public ResourceLocation getProtocolName() {
        return NAME;
    }

    @Override
    public boolean canActivate(EntityPlayer player, ItemStack stack) {
        List<EntityAreaEffectCloud> clouds = player.world.getEntitiesWithinAABB(EntityAreaEffectCloud.class, new AxisAlignedBB(player.getPosition()).grow(8));
        return clouds.stream().anyMatch(cloud -> cloud.effects.stream().anyMatch(effect -> effect.getPotion().isBadEffect() && cloud.getOwner() != player));
    }

    @Override
    public void activateProtocol(EntityPlayer player, ItemStack stack) {
        List<EntityAreaEffectCloud> clouds = player.world.getEntitiesWithinAABB(EntityAreaEffectCloud.class, new AxisAlignedBB(player.getPosition()).grow(8));

        outer:
        for(EntityAreaEffectCloud cloud : clouds) {
            for(PotionEffect effect : cloud.effects) {
                if(effect.getPotion().isBadEffect() && cloud.getOwner() != player) {
                    cloud.setDead();
                    continue outer;
                }
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

    @Override
    public void addTooltip(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {}

    @Override
    public int getEnergyCost() {
        return ConfigHandlerCP.protocolSettings.dragonBreath.energyCost;
    }

    @Override
    public int getCooldown() {
        return ConfigHandlerCP.protocolSettings.dragonBreath.cooldown;
    }

    @Override
    public boolean hasEvent() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCP.protocolSettings.dragonBreath.enable;
    }
}
