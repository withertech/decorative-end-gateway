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

package com.withertech.endgateway.client.tile.renderer;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.withertech.endgateway.tiles.GatewayTile;
import com.withertech.endgateway.tiles.GhostGatewayTile;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix4f;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class GhostGatewayRenderer extends TileEntityRenderer<GhostGatewayTile>
{
    private static final Random RANDOM = new Random(31100L);
    private static final List<RenderType> RENDER_TYPES = IntStream.range(0, 16).mapToObj((p_228882_0_) -> RenderType.endPortal(p_228882_0_ + 1)).collect(ImmutableList.toImmutableList());

    public GhostGatewayRenderer(TileEntityRendererDispatcher p_i226019_1_)
    {
        super(p_i226019_1_);
    }

    public void render(GhostGatewayTile p_225616_1_, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_)
    {
        RANDOM.setSeed(31100L);
        double d0 = p_225616_1_.getBlockPos().distSqr(this.renderer.camera.getPosition(), true);
        int i = this.getPasses(d0);
        float f = this.getOffset();
        Matrix4f matrix4f = p_225616_3_.last().pose();
        this.renderCube(p_225616_1_, f, 0.15F, matrix4f, p_225616_4_.getBuffer(RENDER_TYPES.get(0)));

        for (int j = 1; j < i; ++j)
        {
            this.renderCube(p_225616_1_, f, 2.0F / (float) (18 - j), matrix4f, p_225616_4_.getBuffer(RENDER_TYPES.get(j)));
        }

    }

    private void renderCube(GhostGatewayTile p_228883_1_, float p_228883_2_, float p_228883_3_, Matrix4f p_228883_4_, IVertexBuilder p_228883_5_)
    {
        float f = (RANDOM.nextFloat() * 0.5F + 0.1F) * p_228883_3_;
        float f1 = (RANDOM.nextFloat() * 0.5F + 0.4F) * p_228883_3_;
        float f2 = (RANDOM.nextFloat() * 0.5F + 0.5F) * p_228883_3_;
        this.renderFace(p_228883_1_, p_228883_4_, p_228883_5_, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, f, f1, f2, Direction.SOUTH);
        this.renderFace(p_228883_1_, p_228883_4_, p_228883_5_, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, f, f1, f2, Direction.NORTH);
        this.renderFace(p_228883_1_, p_228883_4_, p_228883_5_, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, f, f1, f2, Direction.EAST);
        this.renderFace(p_228883_1_, p_228883_4_, p_228883_5_, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, f, f1, f2, Direction.WEST);
        this.renderFace(p_228883_1_, p_228883_4_, p_228883_5_, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f, f1, f2, Direction.DOWN);
        this.renderFace(p_228883_1_, p_228883_4_, p_228883_5_, 0.0F, 1.0F, p_228883_2_, p_228883_2_, 1.0F, 1.0F, 0.0F, 0.0F, f, f1, f2, Direction.UP);
    }

    private void renderFace(GhostGatewayTile p_228884_1_, Matrix4f p_228884_2_, IVertexBuilder p_228884_3_, float p_228884_4_, float p_228884_5_, float p_228884_6_, float p_228884_7_, float p_228884_8_, float p_228884_9_, float p_228884_10_, float p_228884_11_, float p_228884_12_, float p_228884_13_, float p_228884_14_, Direction p_228884_15_)
    {
        if (p_228884_1_.shouldRenderFace(p_228884_15_))
        {
            p_228884_3_.vertex(p_228884_2_, p_228884_4_, p_228884_6_, p_228884_8_).color(p_228884_12_, p_228884_13_, p_228884_14_, 1.0F).endVertex();
            p_228884_3_.vertex(p_228884_2_, p_228884_5_, p_228884_6_, p_228884_9_).color(p_228884_12_, p_228884_13_, p_228884_14_, 1.0F).endVertex();
            p_228884_3_.vertex(p_228884_2_, p_228884_5_, p_228884_7_, p_228884_10_).color(p_228884_12_, p_228884_13_, p_228884_14_, 1.0F).endVertex();
            p_228884_3_.vertex(p_228884_2_, p_228884_4_, p_228884_7_, p_228884_11_).color(p_228884_12_, p_228884_13_, p_228884_14_, 1.0F).endVertex();
        }

    }

    protected int getPasses(double p_191286_1_)
    {
        int result;
        if (p_191286_1_ > 36864.0D)
        {
            result = 1;
        } else if (p_191286_1_ > 25600.0D)
        {
            result = 3;
        } else if (p_191286_1_ > 16384.0D)
        {
            result = 5;
        } else if (p_191286_1_ > 9216.0D)
        {
            result = 7;
        } else if (p_191286_1_ > 4096.0D)
        {
            result = 9;
        } else if (p_191286_1_ > 1024.0D)
        {
            result = 11;
        } else if (p_191286_1_ > 576.0D)
        {
            result = 13;
        } else
        {
            result = p_191286_1_ > 256.0D ? 14 : 15;
        }
        return result + 1;
    }

    protected float getOffset()
    {
        return 1.0F;
    }
}
