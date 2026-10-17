package com.invadermonky.casusprotocol.protocols;

import baubles.api.BaubleType;
import com.google.common.collect.Multimap;
import com.invadermonky.casusprotocol.CasusProtocol;
import com.invadermonky.casusprotocol.utils.ConfigHandlerCP;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ProtocolCureVillager implements IProtocol {
    public static final ResourceLocation NAME = new ResourceLocation(CasusProtocol.MOD_ID, "cure_villager");

    @Override
    public ResourceLocation getProtocolName() {
        return NAME;
    }

    @Override
    public boolean canActivate(EntityPlayer player, ItemStack stack) {
        List<EntityZombieVillager> zombieVillagers = player.world.getEntitiesWithinAABB(EntityZombieVillager.class, new AxisAlignedBB(player.getPosition()).grow(8));
        return zombieVillagers.stream().anyMatch(entity -> !entity.isConverting());
    }

    @Override
    public void activateProtocol(EntityPlayer player, ItemStack stack) {
        List<EntityZombieVillager> zombieVillagers = player.world.getEntitiesWithinAABB(EntityZombieVillager.class, new AxisAlignedBB(player.getPosition()).grow(8));
        for(EntityZombieVillager entity : zombieVillagers) {
            if(!entity.isConverting()) {
                if(!player.world.isRemote) {
                    entity.startConverting(player.getUniqueID(), entity.getRNG().nextInt(2401) + 3600);
                }
                break;
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
        return ConfigHandlerCP.protocolSettings.cureVillager.energyCost;
    }

    @Override
    public int getCooldown() {
        return ConfigHandlerCP.protocolSettings.cureVillager.cooldown;
    }

    @Override
    public boolean hasEvent() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCP.protocolSettings.cureVillager.enable;
    }
}
