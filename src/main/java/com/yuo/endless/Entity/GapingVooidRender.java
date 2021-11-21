package com.yuo.endless.Entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.yuo.endless.Endless;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GapingVooidRender extends EntityRenderer<GapingVoidEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Endless.MODID, "textures/entity/void.png");

    private EntityModel<Entity> gapingVoid;

    public GapingVooidRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.gapingVoid = new GapingVoidModel();
    }

    @Override
    public ResourceLocation getEntityTexture(GapingVoidEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(GapingVoidEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.push();
        IVertexBuilder builder = bufferIn.getBuffer(this.gapingVoid.getRenderType(this.getEntityTexture(entityIn)));
        float scale = GapingVoidEntity.getVoidScale(entityIn.getAge() + partialTicks); //缩放值
        matrixStackIn.scale(scale, scale, scale); //缩放模型
        matrixStackIn.translate(0, -scale * 0.1d,0);
        this.gapingVoid.render(matrixStackIn, builder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0f,1.0f,1.0f,1.0f);
        matrixStackIn.pop();
    }
}
