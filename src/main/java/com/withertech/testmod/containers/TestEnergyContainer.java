package com.withertech.testmod.containers;

import com.withertech.testmod.TestMod;
import com.withertech.testmod.tiles.TestEnergyTile;
import com.withertech.witherlib.gui.TileEntityBaseContainer;
import com.withertech.witherlib.registration.TypedRegKey;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class TestEnergyContainer extends TileEntityBaseContainer<TestEnergyContainer, TestEnergyTile>
{
    public TestEnergyContainer(int id, PlayerEntity player, BlockPos pos)
    {
        super(TestMod.INSTANCE.REGISTRY.getContainer(TypedRegKey.container("test_energy_container", TestEnergyContainer.class)).get(), id, player, pos);
        addSlots();
    }

    @Override
    protected void addSlots(PlayerEntity player, @Nonnull TestEnergyTile object)
    {
        addPlayerSlots(8, 140);
    }
}
