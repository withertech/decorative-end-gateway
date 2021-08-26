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

package com.withertech.testmod.gui;

import com.withertech.testmod.TestMod;
import com.withertech.testmod.blocks.TestTileBlock;
import com.withertech.testmod.client.tile.renderer.TestTileEntityRenderer;
import com.withertech.testmod.containers.TestContainer;
import com.withertech.testmod.screens.TestScreen;
import com.withertech.testmod.tiles.TestTileEntity;
import com.withertech.witherlib.gui.TileGui;
import com.withertech.witherlib.registration.TypedRegKey;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Function;

public class TestTileGui extends TileGui<TestTileBlock, TestTileEntity, TestContainer, TestScreen, TestTileEntityRenderer>
{
    @Override
    protected RegistryObject<TestTileBlock> registerBlock()
    {
        return TestMod.INSTANCE.REGISTRY.getBlock(TypedRegKey.block("test_tile_block", TestTileBlock.class));
    }

    @Override
    protected RegistryObject<TileEntityType<TestTileEntity>> registerTile()
    {
        return TestMod.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("test_tile", TestTileEntity.class));
    }

    @Override
    protected RegistryObject<ContainerType<TestContainer>> registerContainer()
    {
        return TestMod.INSTANCE.REGISTRY.getContainer(TypedRegKey.container("test_container", TestContainer.class));
    }

    @Override
    protected ScreenManager.IScreenFactory<TestContainer, TestScreen> registerScreen()
    {
        return TestScreen::new;
    }

    @Override
    @Nullable
    protected Function<? super TileEntityRendererDispatcher, TestTileEntityRenderer> registerTer()
    {
        return TestTileEntityRenderer::new;
    }

}
