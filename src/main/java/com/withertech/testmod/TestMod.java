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

package com.withertech.testmod;

import com.withertech.testmod.blocks.TestBlock;
import com.withertech.testmod.blocks.TestEnergyBlock;
import com.withertech.testmod.blocks.TestTileBlock;
import com.withertech.testmod.client.entity.renderer.TestEntityRenderer;
import com.withertech.testmod.containers.TestContainer;
import com.withertech.testmod.containers.TestEnergyContainer;
import com.withertech.testmod.entities.TestEntity;
import com.withertech.testmod.fluids.TestFluid;
import com.withertech.testmod.gui.TestEnergyGui;
import com.withertech.testmod.gui.TestTileGui;
import com.withertech.testmod.items.TestItem;
import com.withertech.testmod.tiles.TestEnergyTile;
import com.withertech.testmod.tiles.TestTileEntity;
import com.withertech.witherlib.data.BuilderDataGenerator;
import com.withertech.witherlib.data.BuilderRecipeProvider;
import com.withertech.witherlib.registration.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.EnchantRandomly;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;

@Mod(TestMod.MODID)
public class TestMod extends BuilderMod
{
    public static final String MODID = "testmod";
    public static final Logger LOGGER = LogManager.getLogger();
    public static TestMod INSTANCE;


    public TestMod()
    {
        super(new ModData(MODID, FMLJavaModLoadingContext.get().getModEventBus()));
        INSTANCE = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    protected BuilderForgeRegistry<Block> registerBlocks()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.BLOCKS)
                .add(TypedRegKey.block("test_block", TestBlock.class), () -> new TestBlock(AbstractBlock.Properties.of(Material.STONE)))
                .add(TypedRegKey.block("test_fluid", FlowingFluidBlock.class), () -> new FlowingFluidBlock(() -> getFluids().get(TypedRegKey.fluid("test_fluid", TestFluid.Source.class)).get(), AbstractBlock.Properties.of(Material.WATER).strength(100.0F).noDrops()))
                .add(TypedRegKey.block("test_tile_block", TestTileBlock.class), () -> new TestTileBlock(true, AbstractBlock.Properties.of(Material.STONE)))
                .add(TypedRegKey.block("test_energy_block", TestEnergyBlock.class), () -> new TestEnergyBlock(true, AbstractBlock.Properties.of(Material.STONE)))
                .build();
    }

    @Override
    protected BuilderForgeRegistry<Item> registerItems()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.ITEMS)
                .add(TypedRegKey.item("test_item", TestItem.class), () -> new TestItem(new Item.Properties().tab(getTabs().getTab(MODID))))
                .add(TypedRegKey.item("test_block", BlockItem.class), () -> new BlockItem(getBlocks().get(TypedRegKey.block("test_block", TestBlock.class)).get(), new Item.Properties().tab(getTabs().getTab(MODID))))
                .add(TypedRegKey.item("test_fluid_bucket", BucketItem.class), () -> new BucketItem(() -> getFluids().get(TypedRegKey.fluid("test_fluid", TestFluid.Source.class)).get(), new Item.Properties().tab(getTabs().getTab(MODID))))
                .add(TypedRegKey.item("test_tile_block", BlockItem.class), () -> new BlockItem(getBlocks().get(TypedRegKey.block("test_tile_block", TestTileBlock.class)).get(), new Item.Properties().tab(getTabs().getTab(MODID))))
                .add(TypedRegKey.item("test_energy_block", BlockItem.class), () -> new BlockItem(getBlocks().get(TypedRegKey.block("test_energy_block", TestEnergyBlock.class)).get(), new Item.Properties().tab(getTabs().getTab(MODID))))
                .build();
    }

    @Override
    protected BuilderForgeRegistry<Fluid> registerFluids()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.FLUIDS)
                .add(TypedRegKey.fluid("test_fluid", TestFluid.Source.class), TestFluid.Source::new)
                .add(TypedRegKey.fluid("test_fluid_flowing", TestFluid.Flowing.class), TestFluid.Flowing::new)
                .build();
    }

    @Override
    protected BuilderForgeRegistry<EntityType<?>> registerEntities()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.ENTITIES)
                .add(TypedRegKey.entity("test_entity", TestEntity.class), () -> EntityType.Builder.of(TestEntity::new, EntityClassification.CREATURE).sized(0.9f, 1.3f).build(MOD.modLocation("test_entity").toString()))
                .build();
    }

    @Override
    protected BuilderForgeRegistry<TileEntityType<?>> registerTiles()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.TILE_ENTITIES)
                .add(TypedRegKey.tile("test_tile", TestTileEntity.class), () -> TileEntityType.Builder.of(TestTileEntity::new, getBlocks().get(TypedRegKey.block("test_tile_block", TestTileBlock.class)).get()).build(null))
                .add(TypedRegKey.tile("test_energy_tile", TestEnergyTile.class), () -> TileEntityType.Builder.of(TestEnergyTile::new, getBlocks().get(TypedRegKey.block("test_energy_block", TestEnergyBlock.class)).get()).build(null))
                .build();
    }

    @Override
    protected BuilderForgeRegistry<ContainerType<?>> registerContainers()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.CONTAINERS)
                .add(TypedRegKey.container("test_container", TestContainer.class), () -> IForgeContainerType.create((windowId, inv, data) -> new TestContainer(windowId, inv.player, data.readBlockPos())))
                .add(TypedRegKey.container("test_energy_container", TestEnergyContainer.class), () -> IForgeContainerType.create((windowId, inv, data) -> new TestEnergyContainer(windowId, inv.player, data.readBlockPos())))
                .build();
    }

    @Override
    protected BuilderGuiTileRegistry registerGuis()
    {
        return BuilderGuiTileRegistry.builder()
                .add(TypedRegKey.gui("test_gui", TestTileGui.class), new TestTileGui())
                .add(TypedRegKey.gui("test_energy_gui", TestEnergyGui.class), new TestEnergyGui())
                .build();
    }

    @Override
    protected BuilderEntityAttributeRegistry registerEntityAttributes()
    {
        return BuilderEntityAttributeRegistry.builder()
                .addEntity("test_entity", () -> MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.23F))
                .build();
    }

    @Override
    protected BuilderEntityRendererRegistry registerEntityRenderers()
    {
        return BuilderEntityRendererRegistry.builder()
                .addEntity("test_entity", TestEntityRenderer::new)
                .build();
    }

    @Override
    protected BuilderTagRegistry registerTags()
    {
        return BuilderTagRegistry.builder(MOD)
                .addBlock("test_block")
                .addItem("test_item")
                .addFluid("test_fluid")
                .build();
    }

    @Override
    protected BuilderTabRegistry registerTabs()
    {
        return BuilderTabRegistry.builder()
                .addGroup(MODID, new ItemGroup(MODID)
                {

                    @Nonnull
                    @Override
                    public ItemStack makeIcon()
                    {
                        return new ItemStack(getItems().get(TypedRegKey.item("test_item", TestItem.class)).get());
                    }
                })
                .build();
    }

    @Override
    protected BuilderDataGenerator registerDataGenerators()
    {
        return BuilderDataGenerator.builder(MOD)

                .addBlockState(builderBlockStateGenerator ->
                {
                    builderBlockStateGenerator.simpleBlock(
                            getBlocks().get(TypedRegKey.block("test_block", TestBlock.class)).get()
                    );
                    builderBlockStateGenerator.simpleBlock(
                            getBlocks().get(TypedRegKey.block("test_tile_block", TestTileBlock.class)).get()
                    );
                    builderBlockStateGenerator.simpleBlock(
                            getBlocks().get(TypedRegKey.block("test_energy_block", TestEnergyBlock.class)).get()
                    );
                    builderBlockStateGenerator.simpleBlock(
                            getBlocks().get(TypedRegKey.block("test_fluid", FlowingFluidBlock.class)).get(),
                            builderBlockStateGenerator.models()
                                    .getBuilder("test_fluid")
                                    .texture("particle",
                                            builderBlockStateGenerator.modLoc("block/test_fluid_still")
                                    )
                    );
                })
                .addItemModel(builderItemModelProvider ->
                {
                    builderItemModelProvider.blockBuilder(getBlocks().get(TypedRegKey.block("test_block", TestBlock.class)).get());
                    builderItemModelProvider.blockBuilder(getBlocks().get(TypedRegKey.block("test_tile_block", TestTileBlock.class)).get());
                    builderItemModelProvider.blockBuilder(getBlocks().get(TypedRegKey.block("test_energy_block", TestEnergyBlock.class)).get());
                    builderItemModelProvider.builder(getItems().get(TypedRegKey.item("test_item", TestItem.class)).get(), builderItemModelProvider.getGenerated());
                    builderItemModelProvider.builder(getItems().get(TypedRegKey.item("test_fluid_bucket", BucketItem.class)).get(), builderItemModelProvider.getGenerated());
                })
                .addBlockTag(builderBlockTagsProvider ->
                {
                    builderBlockTagsProvider.tag(getTags().getBlock("test_block"))
                            .add(getBlocks().get(TypedRegKey.block("test_block", TestBlock.class)).get());
                })
                .addItemTag(builderItemTagsProvider ->
                {
                    builderItemTagsProvider.tag(getTags().getItem("test_item"))
                            .add(getItems().get(TypedRegKey.item("test_item", TestItem.class)).get());
                })
                .addFluidTag(builderFluidTagsProvider ->
                {
                    builderFluidTagsProvider.tag(FluidTags.WATER)
                            .add(getFluids().get(TypedRegKey.fluid("test_fluid", TestFluid.Source.class)).get())
                            .add(getFluids().get(TypedRegKey.fluid("test_fluid_flowing", TestFluid.Flowing.class)).get());
                    builderFluidTagsProvider.tag(getTags().getFluid("test_fluid"))
                            .add(getFluids().get(TypedRegKey.fluid("test_fluid", TestFluid.Source.class)).get())
                            .add(getFluids().get(TypedRegKey.fluid("test_fluid_flowing", TestFluid.Flowing.class)).get());

                })
                .addBlockLootTable(builderBlockLootTableProvider ->
                {
                    builderBlockLootTableProvider.dropSelf(getBlocks().get(TypedRegKey.block("test_block", TestBlock.class)).get());
                    builderBlockLootTableProvider.dropSelf(getBlocks().get(TypedRegKey.block("test_tile_block", TestTileBlock.class)).get());
                    builderBlockLootTableProvider.dropSelf(getBlocks().get(TypedRegKey.block("test_energy_block", TestEnergyBlock.class)).get());
                }, new ArrayList<>(getBlocks().getREGISTRY().getEntries()))
                .addChestLootTable(consumer ->
                {
                    consumer.accept(MOD.modLocation("chests/test"), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(Items.GOLDEN_APPLE).setWeight(20)).add(ItemLootEntry.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE)).add(ItemLootEntry.lootTableItem(Items.NAME_TAG).setWeight(30)).add(ItemLootEntry.lootTableItem(Items.BOOK).setWeight(10).apply(EnchantRandomly.randomApplicableEnchantment())).add(ItemLootEntry.lootTableItem(Items.IRON_PICKAXE).setWeight(5)).add(EmptyLootEntry.emptyItem().setWeight(5))).withPool(LootPool.lootPool().setRolls(RandomValueRange.between(2.0F, 4.0F)).add(ItemLootEntry.lootTableItem(Items.IRON_INGOT).setWeight(10).apply(SetCount.setCount(RandomValueRange.between(1.0F, 5.0F)))).add(ItemLootEntry.lootTableItem(Items.GOLD_INGOT).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1.0F, 3.0F)))).add(ItemLootEntry.lootTableItem(Items.REDSTONE).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(4.0F, 9.0F)))).add(ItemLootEntry.lootTableItem(Items.LAPIS_LAZULI).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(4.0F, 9.0F)))).add(ItemLootEntry.lootTableItem(Items.DIAMOND).setWeight(3).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F)))).add(ItemLootEntry.lootTableItem(Items.COAL).setWeight(10).apply(SetCount.setCount(RandomValueRange.between(3.0F, 8.0F)))).add(ItemLootEntry.lootTableItem(Items.BREAD).setWeight(15).apply(SetCount.setCount(RandomValueRange.between(1.0F, 3.0F)))).add(ItemLootEntry.lootTableItem(Items.MELON_SEEDS).setWeight(10).apply(SetCount.setCount(RandomValueRange.between(2.0F, 4.0F)))).add(ItemLootEntry.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(10).apply(SetCount.setCount(RandomValueRange.between(2.0F, 4.0F)))).add(ItemLootEntry.lootTableItem(Items.BEETROOT_SEEDS).setWeight(10).apply(SetCount.setCount(RandomValueRange.between(2.0F, 4.0F))))).withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(3)).add(ItemLootEntry.lootTableItem(Blocks.RAIL).setWeight(20).apply(SetCount.setCount(RandomValueRange.between(4.0F, 8.0F)))).add(ItemLootEntry.lootTableItem(Blocks.POWERED_RAIL).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1.0F, 4.0F)))).add(ItemLootEntry.lootTableItem(Blocks.DETECTOR_RAIL).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1.0F, 4.0F)))).add(ItemLootEntry.lootTableItem(Blocks.ACTIVATOR_RAIL).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1.0F, 4.0F)))).add(ItemLootEntry.lootTableItem(Blocks.TORCH).setWeight(15).apply(SetCount.setCount(RandomValueRange.between(1.0F, 16.0F))))));
                })
                .addEntityLootTable(builderEntityLootTableProvider ->
                {
                    builderEntityLootTableProvider.add(getEntities().get(TypedRegKey.entity("test_entity", TestEntity.class)).get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(getItems().get(TypedRegKey.item("test_item", TestItem.class)).get()).setWeight(100))));
                }, new ArrayList<>(getEntities().getREGISTRY().getEntries()))
                .addRecipe(iFinishedRecipeConsumer ->
                {
                    ShapelessRecipeBuilder.shapeless(Items.DIAMOND)
                            .requires(Items.COAL_BLOCK, 9)
                            .unlockedBy("has_coal_block", BuilderRecipeProvider.has(Items.COAL_BLOCK))
                            .save(iFinishedRecipeConsumer);
                })
                .addLang(builderLangProvider ->
                {
                    builderLangProvider.add(getItems().get(TypedRegKey.item("test_item", TestItem.class)).get(), "Test Item");
                    builderLangProvider.add(getItems().get(TypedRegKey.item("test_fluid_bucket", BucketItem.class)).get(), "Test Fluid Bucket");
                    builderLangProvider.add(getBlocks().get(TypedRegKey.block("test_block", TestBlock.class)).get(), "Test Block");
                    builderLangProvider.add(getBlocks().get(TypedRegKey.block("test_tile_block", TestTileBlock.class)).get(), "Test Tile Block");
                    builderLangProvider.add(getBlocks().get(TypedRegKey.block("test_energy_block", TestEnergyBlock.class)).get(), "Test Energy Block");
                    builderLangProvider.add(getEntities().get(TypedRegKey.entity("test_entity", TestEntity.class)).get(), "Test Entity");
                    builderLangProvider.add(getFluids().get(TypedRegKey.fluid("test_fluid", TestFluid.Source.class)).get().getAttributes().getTranslationKey(), "Test Fluid");
                    builderLangProvider.add(((TranslationTextComponent) getTabs().getTab(MODID).getDisplayName()).getKey(), "Test Tab");
                })
                .build();
    }
}
