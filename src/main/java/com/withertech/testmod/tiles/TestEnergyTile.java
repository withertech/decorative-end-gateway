package com.withertech.testmod.tiles;

import com.withertech.testmod.TestMod;
import com.withertech.witherlib.nbt.SerializableEnergyStorage;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlib.tile.BaseTileEntity;
import com.withertech.witherlib.util.SyncVariable;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestEnergyTile extends BaseTileEntity<TestEnergyTile>
{
    @SyncVariable(name = "Energy")
    public final LazyOptional<SerializableEnergyStorage> energy = LazyOptional.of(this::createEnergyStorage);

    public TestEnergyTile()
    {
        super(TestMod.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("test_energy_tile", TestEnergyTile.class)).get());
    }

    @Nonnull
    private SerializableEnergyStorage createEnergyStorage()
    {
        return new SerializableEnergyStorage(1000, 10)
        {
            @Override
            public int receiveEnergy(int maxReceive, boolean simulate)
            {
                dataChanged();
                return super.receiveEnergy(maxReceive, simulate);
            }

            @Override
            public int extractEnergy(int maxExtract, boolean simulate)
            {
                dataChanged();
                return super.extractEnergy(maxExtract, simulate);
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == CapabilityEnergy.ENERGY)
        {
            return energy.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected void invalidateCaps()
    {
        super.invalidateCaps();
        energy.invalidate();
    }
}
