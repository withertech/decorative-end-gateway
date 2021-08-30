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

package com.withertech.endgateway;

import com.withertech.endgateway.blocks.GatewayBlock;
import com.withertech.endgateway.blocks.GhostGatewayBlock;
import com.withertech.endgateway.client.item.renderer.GatewayItemRenderer;
import com.withertech.endgateway.client.tile.renderer.GatewayTileRenderer;
import com.withertech.endgateway.client.tile.renderer.GhostGatewayRenderer;
import com.withertech.endgateway.tiles.GatewayTile;
import com.withertech.endgateway.tiles.GhostGatewayTile;
import com.withertech.witherlib.data.BuilderDataGenerator;
import com.withertech.witherlib.data.BuilderRecipeProvider;
import com.withertech.witherlib.registration.BuilderForgeRegistry;
import com.withertech.witherlib.registration.BuilderMod;
import com.withertech.witherlib.registration.ModData;
import com.withertech.witherlib.registration.TypedRegKey;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

@Mod(EndGateway.MODID)
public class EndGateway extends BuilderMod
{
    public static final String MODID = "endgateway";
    public static final Logger LOGGER = LogManager.getLogger();
    public static EndGateway INSTANCE;

    public EndGateway()
    {
        super(new ModData(MODID, FMLJavaModLoadingContext.get().getModEventBus()));
        INSTANCE = this;
        MinecraftForge.EVENT_BUS.register(this);
        MOD.MOD_EVENT_BUS.addListener(this::onClientSetup);
    }

    @Override
    protected BuilderForgeRegistry<Block> registerBlocks()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.BLOCKS)
                .add(TypedRegKey.block("gateway_block", GatewayBlock.class), () -> new GatewayBlock(AbstractBlock.Properties.of(Material.STONE).lightLevel(value -> 15)))
                .add(TypedRegKey.block("ghost_gateway_block", GhostGatewayBlock.class), () -> new GhostGatewayBlock(AbstractBlock.Properties.of(Material.STONE).noCollission().lightLevel(value -> 15)))
                .build();
    }

    @Override
    protected BuilderForgeRegistry<Item> registerItems()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.ITEMS)
                .add(TypedRegKey.item("gateway_block", BlockItem.class), () -> new BlockItem(getBlocks().get(TypedRegKey.block("gateway_block", GatewayBlock.class)).get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS).setISTER(() -> GatewayItemRenderer::new)))
                .add(TypedRegKey.item("ghost_gateway_block", BlockItem.class), () -> new BlockItem(getBlocks().get(TypedRegKey.block("ghost_gateway_block", GhostGatewayBlock.class)).get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS).setISTER(() -> GatewayItemRenderer::new)))
                .build();
    }

    @Override
    protected BuilderForgeRegistry<TileEntityType<?>> registerTiles()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.TILE_ENTITIES)
                .add(TypedRegKey.tile("gateway_tile", GatewayTile.class), () -> TileEntityType.Builder.of(GatewayTile::new, getBlocks().get(TypedRegKey.block("gateway_block", GatewayBlock.class)).get()).build(null))
                .add(TypedRegKey.tile("ghost_gateway_tile", GhostGatewayTile.class), () -> TileEntityType.Builder.of(GhostGatewayTile::new, getBlocks().get(TypedRegKey.block("ghost_gateway_block", GhostGatewayBlock.class)).get()).build(null))
                .build();
    }

    @Override
    protected BuilderDataGenerator registerDataGenerators()
    {
        return BuilderDataGenerator.builder(MOD)

                .addBlockState(builderBlockStateGenerator ->
                {
                    builderBlockStateGenerator.simpleBlock(
                            getBlocks().get(TypedRegKey.block("gateway_block", GatewayBlock.class)).get()
                    );
                    builderBlockStateGenerator.simpleBlock(
                            getBlocks().get(TypedRegKey.block("ghost_gateway_block", GhostGatewayBlock.class)).get()
                    );
                })
                .addItemModel(builderItemModelProvider ->
                {
                    builderItemModelProvider.blockBuilder(getBlocks().get(TypedRegKey.block("gateway_block", GatewayBlock.class)).get());
                    builderItemModelProvider.blockBuilder(getBlocks().get(TypedRegKey.block("ghost_gateway_block", GhostGatewayBlock.class)).get());
                })
                .addBlockLootTable(builderBlockLootTableProvider ->
                {
                    builderBlockLootTableProvider.dropSelf(getBlocks().get(TypedRegKey.block("gateway_block", GatewayBlock.class)).get());
                    builderBlockLootTableProvider.dropSelf(getBlocks().get(TypedRegKey.block("ghost_gateway_block", GhostGatewayBlock.class)).get());
                }, new ArrayList<>(getBlocks().getREGISTRY().getEntries()))
                .addRecipe(iFinishedRecipeConsumer ->
                {
                    ShapedRecipeBuilder.shaped(getItems().get(TypedRegKey.item("gateway_block", BlockItem.class)).get())
                            .pattern(" E ")
                            .pattern("ESE")
                            .pattern(" E ")
                            .define('E', Items.ENDER_EYE)
                            .define('S', Items.END_STONE)
                            .unlockedBy("has_ender_eye", BuilderRecipeProvider.has(Items.ENDER_EYE))
                            .unlockedBy("has_end_stone", BuilderRecipeProvider.has(Items.END_STONE))
                            .save(iFinishedRecipeConsumer);
                    ShapelessRecipeBuilder.shapeless(getItems().get(TypedRegKey.item("ghost_gateway_block", BlockItem.class)).get())
                            .requires(getItems().get(TypedRegKey.item("gateway_block", BlockItem.class)).get())
                            .requires(Items.REDSTONE_BLOCK)
                            .unlockedBy("has_gateway_block", BuilderRecipeProvider.has(getItems().get(TypedRegKey.item("gateway_block", BlockItem.class)).get()))
                            .save(iFinishedRecipeConsumer);
                })
                .addLang(builderLangProvider ->
                {
                    builderLangProvider.add(getBlocks().get(TypedRegKey.block("gateway_block", GatewayBlock.class)).get(), "Decorative End Gateway");
                    builderLangProvider.add(getBlocks().get(TypedRegKey.block("ghost_gateway_block", GhostGatewayBlock.class)).get(), "Decorative End Gateway (Ghost)");
                })
                .build();
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event)
    {
        ClientRegistry.bindTileEntityRenderer(getTiles().get(TypedRegKey.tile("gateway_tile", GatewayTile.class)).get(), GatewayTileRenderer::new);
        ClientRegistry.bindTileEntityRenderer(getTiles().get(TypedRegKey.tile("ghost_gateway_tile", GhostGatewayTile.class)).get(), GhostGatewayRenderer::new);
    }
}
