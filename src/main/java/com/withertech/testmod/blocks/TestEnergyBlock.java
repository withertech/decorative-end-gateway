package com.withertech.testmod.blocks;

import com.withertech.testmod.TestMod;
import com.withertech.testmod.containers.TestEnergyContainer;
import com.withertech.testmod.tiles.TestEnergyTile;
import com.withertech.witherlib.block.BaseTileBlock;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlib.util.TextComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestEnergyBlock extends BaseTileBlock<TestEnergyTile>
{
    public TestEnergyBlock(boolean saveTileData, Properties properties)
    {
        super(saveTileData, properties);
    }

    @Override
    protected Container createMenu(int id, PlayerEntity player, BlockPos pos)
    {
        return new TestEnergyContainer(id, player, pos);
    }

    @Override
    protected ITextComponent getDisplayName(TestEnergyTile tile)
    {
        return TextComponents.block(this).get();
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(@Nonnull IBlockReader world)
    {
        return TestMod.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("test_energy_tile", TestEnergyTile.class)).get().create();
    }
}
