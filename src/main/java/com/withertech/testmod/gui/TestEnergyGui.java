package com.withertech.testmod.gui;

import com.withertech.testmod.TestMod;
import com.withertech.testmod.blocks.TestEnergyBlock;
import com.withertech.testmod.containers.TestEnergyContainer;
import com.withertech.testmod.screens.TestEnergyScreen;
import com.withertech.testmod.tiles.TestEnergyTile;
import com.withertech.witherlib.gui.TileGui;
import com.withertech.witherlib.registration.TypedRegKey;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Function;

public class TestEnergyGui extends TileGui<TestEnergyBlock, TestEnergyTile, TestEnergyContainer, TestEnergyScreen, TileEntityRenderer<TestEnergyTile>>
{
    @Override
    protected RegistryObject<TestEnergyBlock> registerBlock()
    {
        return TestMod.INSTANCE.REGISTRY.getBlock(TypedRegKey.block("test_energy_block", TestEnergyBlock.class));
    }

    @Override
    protected RegistryObject<TileEntityType<TestEnergyTile>> registerTile()
    {
        return TestMod.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("test_energy_tile", TestEnergyTile.class));
    }

    @Override
    protected RegistryObject<ContainerType<TestEnergyContainer>> registerContainer()
    {
        return TestMod.INSTANCE.REGISTRY.getContainer(TypedRegKey.container("test_energy_container", TestEnergyContainer.class));
    }

    @Override
    protected ScreenManager.IScreenFactory<TestEnergyContainer, TestEnergyScreen> registerScreen()
    {
        return TestEnergyScreen::new;
    }

    @Nullable
    @Override
    protected Function<? super TileEntityRendererDispatcher, TileEntityRenderer<TestEnergyTile>> registerTer()
    {
        return null;
    }
}
