/*
 * witherlib-forge
 * Copyright (C) 2021 WitherTech
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.withertech.testmod.blocks;

import com.withertech.testmod.TestMod;
import com.withertech.testmod.containers.TestContainer;
import com.withertech.testmod.tiles.TestTileEntity;
import com.withertech.witherlib.block.BaseTileBlock;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlib.util.TextComponents;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestTileBlock extends BaseTileBlock<TestTileEntity>
{
    public TestTileBlock(boolean saveTileData, Properties properties)
    {
        super(saveTileData, properties);
    }

    @Override
    protected Container createMenu(int id, PlayerEntity player, BlockPos pos)
    {
        return new TestContainer(id, player, pos);
    }

    @Override
    protected ITextComponent getDisplayName(TestTileEntity tile)
    {
        return TextComponents.block(this).get();
    }

    @Nonnull
    @Override
    public BlockRenderType getRenderShape(@Nonnull BlockState state)
    {
        return BlockRenderType.INVISIBLE;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(@Nonnull IBlockReader world)
    {
        return TestMod.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("test_tile", TestTileEntity.class)).get().create();
    }
}
