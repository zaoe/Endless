package com.yuo.endless.Gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.yuo.endless.Container.ExtremeCraftContainer;
import com.yuo.endless.Container.NeutronCollectorContainer;
import com.yuo.endless.Endless;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.FurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class NeutronCollectorScreen extends ContainerScreen<NeutronCollectorContainer>{
    private final ResourceLocation TORCH_PLACER_RESOURCE = new ResourceLocation(Endless.MODID, "textures/gui/neutron_collector_gui.png");
    private final int textureWidth = 238;
    private final int textureHeight = 256;

    public NeutronCollectorScreen(NeutronCollectorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.xSize = textureWidth;
        this.ySize = textureHeight;
    }

    //渲染背景
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F); //确保颜色正常
        this.minecraft.getTextureManager().bindTexture(TORCH_PLACER_RESOURCE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        blit(matrixStack, i, j, 0, 0, xSize, ySize);
        int k = this.container.getTimer();
        this.blit(matrixStack, i + 105, j + 31 + 12 - k, 176, 24 - k, 4, k);
    }

    //渲染组件
    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F); //确保颜色正常
        drawString(matrixStack, this.font, new TranslationTextComponent("gui.endless.neutron_collector"),180,8, 0x696969);
        drawString(matrixStack, this.font, new TranslationTextComponent("gui.endless.inventory"), 8, 174, 0x696969);
    }

//    @Override
//    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//        renderBackground(matrixStack);
//        super.render(matrixStack, mouseX, mouseY, partialTicks);
//        renderHoveredTooltip(matrixStack, mouseX, mouseY);
//    }
}
