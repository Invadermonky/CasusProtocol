package com.invadermonky.casusprotocol.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnergyHandlerItem implements IEnergyStorage, ICapabilityProvider {
    public static final String TAG_ENERGY = "energy";

    public final ItemStack stack;
    private int energy;
    private int capacity;
    private int maxReceive;
    private int maxExtract;

    public EnergyHandlerItem(ItemStack stack) {
        this.stack = stack;
        this.energy = stack.getTagCompound() != null ? stack.getTagCompound().getInteger("energy") : 0;
    }

    public EnergyHandlerItem setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public EnergyHandlerItem setMaxExtract(int maxExtract) {
        this.maxExtract = maxExtract;
        return this;
    }

    public EnergyHandlerItem setMaxReceive(int maxReceive) {
        this.maxReceive = maxReceive;
        return this;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if(!canReceive())
            return 0;

        int received = Math.min(this.capacity - this.energy, Math.min(this.maxReceive, maxReceive));
        if(!simulate) {
            this.energy += received;
            this.stack.setTagInfo(TAG_ENERGY, new NBTTagInt(this.energy));
        }
        return received;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if(!canExtract())
            return 0;

        int extracted = Math.min(this.energy, Math.min(this.maxExtract, maxExtract));
        if(!simulate) {
            this.energy -= extracted;
            this.stack.setTagInfo(TAG_ENERGY, new NBTTagInt(this.energy));
        }
        return extracted;
    }

    @Override
    public int getEnergyStored() {
        return this.energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return this.capacity;
    }

    @Override
    public boolean canExtract() {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY ? CapabilityEnergy.ENERGY.cast(this) : null;
    }

}
