package com.invadermonky.casusprotocol.protocols;

import baubles.api.BaubleType;
import com.google.common.collect.Multimap;
import com.invadermonky.casusprotocol.CasusProtocol;
import com.invadermonky.casusprotocol.utils.ConfigHandlerCP;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ProtocolCreeper implements IProtocol {
    public static final ResourceLocation NAME = new ResourceLocation(CasusProtocol.MOD_ID, "creeper");

    @Override
    public ResourceLocation getProtocolName() {
        return NAME;
    }

    @Override
    public boolean canActivate(EntityPlayer player, ItemStack stack) {
        return !player.world.getEntitiesWithinAABB(EntityCreeper.class, new AxisAlignedBB(player.getPosition()).grow(8)).isEmpty();
    }

    @Override
    public void activateProtocol(EntityPlayer player, ItemStack stack) {
        World world = player.world;
        List<EntityCreeper> creepers = world.getEntitiesWithinAABB(EntityCreeper.class, new AxisAlignedBB(player.getPosition()).grow(8));
        for(EntityCreeper creeper : creepers) {
            if(creeper.isEntityAlive()) {
                BlockPos creeperPos = creeper.getPosition();
                IBlockState state = Blocks.LEAVES.getDefaultState().withProperty(BlockLeaves.DECAYABLE, false).withProperty(BlockLeaves.CHECK_DECAY, false);
                int id = Block.getStateId(state);
                world.setBlockState(creeperPos, state);
                world.setBlockState(creeperPos.up(), state);
                world.playEvent(Constants.WorldEvents.BREAK_BLOCK_EFFECTS, creeperPos, id);
                world.playEvent(Constants.WorldEvents.BREAK_BLOCK_EFFECTS, creeperPos.up(), id);
                creeper.setDead();
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
        return ConfigHandlerCP.protocolSettings.creeper.energyCost;
    }

    @Override
    public int getCooldown() {
        return ConfigHandlerCP.protocolSettings.creeper.cooldown;
    }

    @Override
    public boolean hasEvent() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return ConfigHandlerCP.protocolSettings.creeper.enable;
    }
}
