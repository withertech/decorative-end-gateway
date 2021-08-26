package com.withertech.testmod.screens;

import com.withertech.testmod.containers.TestEnergyContainer;
import com.withertech.testmod.tiles.TestEnergyTile;
import com.withertech.witherlib.gui.TileEntityBaseContainerScreen;
import com.withertech.witherlib.gui.widget.EnergyBarWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public class TestEnergyScreen extends TileEntityBaseContainerScreen<TestEnergyTile, TestEnergyContainer>
{
    public TestEnergyScreen(TestEnergyContainer screenContainer, PlayerInventory playerInventory, ITextComponent title)
    {
        super(screenContainer, title);
    }

    @Override
    protected int sizeX(@Nonnull TestEnergyTile object)
    {
        return 176;
    }

    @Override
    protected int sizeY(@Nonnull TestEnergyTile object)
    {
        return 222;
    }

    @Override
    protected void addWidgets(@Nonnull TestEnergyTile object)
    {
        object.energy.ifPresent(serializableEnergyStorage ->
        {
            addWidget(new EnergyBarWidget(8, 16, 20, 52, serializableEnergyStorage::getEnergyStored, serializableEnergyStorage::getMaxEnergyStored));
        });
    }
}
