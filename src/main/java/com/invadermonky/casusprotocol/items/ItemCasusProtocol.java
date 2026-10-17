package com.invadermonky.casusprotocol.items;

import baubles.api.BaubleType;
import com.expandedevents.api.baubles.IAttributeBauble;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.invadermonky.casusprotocol.CasusProtocol;
import com.invadermonky.casusprotocol.ProtocolRegistry;
import com.invadermonky.casusprotocol.capability.EnergyHandlerItem;
import com.invadermonky.casusprotocol.protocols.IProtocol;
import com.invadermonky.casusprotocol.utils.ConfigHandlerCP;
import com.invadermonky.casusprotocol.utils.ProtocolHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class ItemCasusProtocol extends Item implements IAttributeBauble {
    public static final String TAG_COOLDOWN = "cooldown";
    public static final String TAG_DURATION = "duration";
    public static final String TAG_ENERGY = EnergyHandlerItem.TAG_ENERGY;
    public static final String TAG_HAS_ATTRIBUTES = "hasAttributes";
    public static final String TAG_PROTOCOL = "protocol";

    public ItemCasusProtocol() {
        this.setRegistryName(CasusProtocol.MOD_ID, "casus_protocol");
        this.setTranslationKey(Objects.requireNonNull(this.getRegistryName()).toString());
        this.setCreativeTab(CreativeTabs.MISC);
        this.setMaxStackSize(1);
        this.addPropertyOverride(new ResourceLocation("activation"), ((stack, worldIn, entityIn) -> ProtocolHelper.getCooldown(stack) <= 0 ? 0 : 1));
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase entityLiving) {
        if(entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            int cooldown = ProtocolHelper.getCooldown(stack);
            int duration = ProtocolHelper.getDuration(stack);
            if (duration > 0) {
                ProtocolHelper.setDuration(stack, duration - 1);
                IProtocol protocol = ProtocolHelper.getProtocol(stack);
                if (protocol != null && entityLiving instanceof EntityPlayer) {
                    protocol.onProtocolTick(player, stack);
                }
            } else {
                ProtocolHelper.removeActiveProtocol(stack);
            }

            if (cooldown > 0) {
                ProtocolHelper.setCooldown(stack, cooldown - 1);
            } else {
                if(player.ticksExisted > 0 && player.ticksExisted % ConfigHandlerCP.itemSettings.updateInterval == 0) {
                    List<IProtocol> protocols = ProtocolRegistry.getPossibleProtocols(player, stack);
                    if(!protocols.isEmpty()) {
                        IProtocol protocol = protocols.get(player.getRNG().nextInt(protocols.size()));
                        protocol.activateProtocol(player, stack);
                        ProtocolHelper.setProtocolActivated(player, stack, protocol);
                    }
                }
            }
        }
    }

    @Override
    public void onUpdate(@NotNull ItemStack stack, World worldIn, @NotNull Entity entity, int itemSlot, boolean isSelected) {
        if(!worldIn.isRemote && entity instanceof EntityPlayer) {
            int cooldown = ProtocolHelper.getCooldown(stack);
            int duration = ProtocolHelper.getDuration(stack);
            if (duration > 0) {
                ProtocolHelper.setDuration(stack, duration - 1);
            } else {
                ProtocolHelper.removeActiveProtocol(stack);
            }

            if(cooldown > 0) {
                ProtocolHelper.setCooldown(stack, cooldown - 1);
            }
        }
    }

    @Override
    public @NotNull Multimap<String, AttributeModifier> getBaubleAttributeModifiers(BaubleType type, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if(type == this.getBaubleType(stack) && ProtocolHelper.getHasAttributeModifier(stack)) {
            IProtocol protocol = ProtocolHelper.getProtocol(stack);
            if(protocol != null) {
                protocol.getAttributeModifiers(multimap, type, stack);
            }
        }
        return multimap;
    }

    @Override
    public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.CHARM;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(ConfigHandlerCP.itemSettings.useRF) {
            return new EnergyHandlerItem(stack)
                    .setCapacity(50000)
                    .setMaxReceive(2048);
        } else {
            return null;
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return stack.hasCapability(CapabilityEnergy.ENERGY, null);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
        if(storage != null) {
            return (double) (storage.getMaxEnergyStored() - ProtocolHelper.getEnergy(stack)) / (double) storage.getMaxEnergyStored();
        }
        return super.getDurabilityForDisplay(stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        IProtocol protocol = ProtocolHelper.getProtocol(stack);
        if(protocol != null) {
            protocol.addTooltip(stack, worldIn, tooltip, flagIn);
        }
        int cooldown = ProtocolHelper.getCooldown(stack);
        if(cooldown > 0) {
            cooldown = (cooldown / 20);
            int minutes = cooldown / 60;
            int seconds = cooldown % 60;
            String time = String.format("%d:%02d", minutes, seconds);
            tooltip.add(I18n.format("tooltip.casusprotocol:cooldown", time));
        }
        IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
        if(storage != null) {
            int energy = ProtocolHelper.getEnergy(stack);
            int capacity = storage.getMaxEnergyStored();
            tooltip.add(I18n.format("tooltip.casusprotocol:energy", energy, capacity));
        }
    }

}
