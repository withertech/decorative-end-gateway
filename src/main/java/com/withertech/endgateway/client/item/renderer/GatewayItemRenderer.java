package com.withertech.endgateway.client.item.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.withertech.endgateway.EndGateway;
import com.withertech.endgateway.blocks.GatewayBlock;
import com.withertech.endgateway.blocks.GhostGatewayBlock;
import com.withertech.endgateway.tiles.GatewayTile;
import com.withertech.endgateway.tiles.GhostGatewayTile;
import com.withertech.witherlib.registration.TypedRegKey;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.EnderChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class GatewayItemRenderer extends ItemStackTileEntityRenderer
{
	@Override
	public void renderByItem(@Nonnull ItemStack itemStackIn, @Nonnull ItemCameraTransforms.TransformType transformTypeIn, @Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		super.renderByItem(itemStackIn, transformTypeIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);

		Item item1 = itemStackIn.getItem();
		if (item1 instanceof BlockItem)
		{
			GatewayTile gatewayTile = EndGateway.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("gateway_tile", GatewayTile.class)).get().create();
			GhostGatewayTile ghostGatewayTile = EndGateway.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("ghost_gateway_tile", GhostGatewayTile.class)).get().create();
			TileEntity tileEntity = null;
			BlockItem blockItem1 = (BlockItem) item1;
			if (blockItem1.getBlock() instanceof GatewayBlock)
				tileEntity = gatewayTile;
			else if (blockItem1.getBlock() instanceof GhostGatewayBlock)
				tileEntity = ghostGatewayTile;
			if (tileEntity != null)
				TileEntityRendererDispatcher.instance.renderItem(tileEntity, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);

		}

	}
}
