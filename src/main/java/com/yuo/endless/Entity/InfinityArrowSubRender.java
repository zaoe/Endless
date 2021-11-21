package com.yuo.endless.Entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yuo.endless.Endless;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.ResourceLocation;

public class InfinityArrowSubRender extends ArrowRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Endless.MODID, "textures/entity/heavenarrow.png");

    public InfinityArrowSubRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        return TEXTURE;
    }

    @Override
    public void render(AbstractArrowEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}