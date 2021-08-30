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

package com.withertech.endgateway.blocks;

import com.withertech.endgateway.EndGateway;
import com.withertech.endgateway.tiles.GatewayTile;
import com.withertech.endgateway.tiles.GhostGatewayTile;
import com.withertech.witherlib.block.BaseTileBlock;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlib.util.TextComponents;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GhostGatewayBlock extends BaseTileBlock<GhostGatewayTile>
{
    public GhostGatewayBlock(Properties properties)
    {
        super(false, properties);
    }

    @Override
    protected Container createMenu(int id, PlayerEntity player, BlockPos pos)
    {
        return null;
    }

    @Nonnull
    @Override
    public ActionResultType use(@Nonnull BlockState state, World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult rayTraceResult)
    {
        return ActionResultType.PASS;
    }

    @Override
    protected ITextComponent getDisplayName(GhostGatewayTile tile)
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
        return EndGateway.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("ghost_gateway_tile", GhostGatewayTile.class)).get().create();
    }
}
