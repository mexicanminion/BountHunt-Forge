package net.mexicanminion.bountyhunt.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.mexicanminion.bountyhunt.BountyHuntMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CommonScreen extends AbstractContainerScreen<SetBountyMenu> {

    private int containerRows = 6;
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BountyHuntMod.MODID, "textures/gui/generic_54.png");

    public CommonScreen(SetBountyMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageHeight = 114 + this.containerRows * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        //RenderSystem.setShader(GameRenderer::getPositionTexShader);
        //RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(GUI_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
        guiGraphics.blit(GUI_TEXTURE, i, j + this.imageHeight * 18 + 17, 0, 126, this.imageWidth, 96);

    }
}
