package net.fabricmc.refamished.entities.render;

import com.prupe.mcpatcher.cc.ColorizeEntity;
import net.fabricmc.refamished.entities.EntitySKOrb;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class RenderSKOrb extends Render {

    private static final ResourceLocation experienceOrbTextures = new ResourceLocation("textures/entity/experience_orb.png");

    public RenderSKOrb() {
        this.shadowSize = 0.15f;
        this.shadowOpaque = 0.75f;
    }

    public void renderTheXPOrb(EntitySKOrb par1EntityXPOrb, double par2, double par4, double par6, float par8, float par9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        this.bindEntityTexture(par1EntityXPOrb);

        int var10 = par1EntityXPOrb.getTextureByXP();
        float var11 = (float)(var10 % 4 * 16) / 64.0f;
        float var12 = (float)(var10 % 4 * 16 + 16) / 64.0f;
        float var13 = (float)(var10 / 4 * 16) / 64.0f;
        float var14 = (float)(var10 / 4 * 16 + 16) / 64.0f;
        float var15 = 1.0f;
        float var16 = 0.5f;
        float var17 = 0.25f;

        int var18 = par1EntityXPOrb.getBrightnessForRender(par9);
        int var19 = var18 % 65536;
        int var20 = var18 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var19 / 1.0f, (float)var20 / 1.0f);

        // **Override default color calculation**
        float fadeFactor = (MathHelper.sin(((float)par1EntityXPOrb.xpColor + par9) * 0.1f) + 1.0f) * 0.5f;
        int red   = (int) (255 * (1.0f - fadeFactor) + 74 * fadeFactor);  // White (255) → Blue (74)
        int green = (int) (255 * (1.0f - fadeFactor) + 144 * fadeFactor); // White (255) → Blue (144)
        int blue  = (int) (255 * (1.0f - fadeFactor) + 226 * fadeFactor); // White (255) → Blue (226)
        int color = (red << 16) | (green << 8) | blue;

        GL11.glColor4f(red / 255.0f, green / 255.0f, blue / 255.0f, 1.0f);

        GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);

        float var24 = 0.3f;
        GL11.glScalef(var24, var24, var24);

        Tessellator var25 = Tessellator.instance;
        var25.startDrawingQuads();
        var25.setColorRGBA_I(color, 255); // **Force Blue-White fade**
        var25.setNormal(0.0f, 1.0f, 0.0f);

        var25.addVertexWithUV(0.0f - var16, 0.0f - var17, 0.0, var11, var14);
        var25.addVertexWithUV(var15 - var16, 0.0f - var17, 0.0, var12, var14);
        var25.addVertexWithUV(var15 - var16, 1.0f - var17, 0.0, var12, var13);
        var25.addVertexWithUV(0.0f - var16, 1.0f - var17, 0.0, var11, var13);

        var25.draw();

        GL11.glDisable(3042);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }

    protected ResourceLocation getExperienceOrbTextures(EntitySKOrb par1EntityXPOrb) {
        return experienceOrbTextures;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return this.getExperienceOrbTextures((EntitySKOrb)par1Entity);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderTheXPOrb((EntitySKOrb)par1Entity, par2, par4, par6, par8, par9);
    }
}
