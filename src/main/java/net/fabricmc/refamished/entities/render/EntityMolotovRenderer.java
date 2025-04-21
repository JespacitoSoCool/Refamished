package net.fabricmc.refamished.entities.render;

import com.prupe.mcpatcher.cit.CITUtils;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class EntityMolotovRenderer extends Render {
    private Item field_94151_a;
    private int field_94150_f;

    public EntityMolotovRenderer(Item par1Item, int par2) {
        this.field_94151_a = par1Item;
        this.field_94150_f = par2;
    }

    public EntityMolotovRenderer(Item par1Item) {
        this(par1Item, 0);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        Icon var10 = CITUtils.getEntityIcon(this.field_94151_a.getIconFromDamage(this.field_94150_f), par1Entity);
        if (var10 != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)par2, (float)par4, (float)par6);
            GL11.glEnable(32826);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            this.bindEntityTexture(par1Entity);
            Tessellator var11 = Tessellator.instance;
            if (var10 == ItemPotion.func_94589_d("bottle_splash")) {
                int var12 = PotionHelper.func_77915_a(((EntityPotion)par1Entity).getPotionDamage(), false);
                float var13 = (float)(var12 >> 16 & 0xFF) / 255.0f;
                float var14 = (float)(var12 >> 8 & 0xFF) / 255.0f;
                float var15 = (float)(var12 & 0xFF) / 255.0f;
                GL11.glColor3f(var13, var14, var15);
                GL11.glPushMatrix();
                this.func_77026_a(var11, ItemPotion.func_94589_d("overlay"));
                GL11.glPopMatrix();
                GL11.glColor3f(1.0f, 1.0f, 1.0f);
            }
            this.func_77026_a(var11, var10);
            GL11.glDisable(32826);
            GL11.glPopMatrix();
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return TextureMap.locationItemsTexture;
    }

    private void func_77026_a(Tessellator par1Tessellator, Icon par2Icon) {
        float var3 = par2Icon.getMinU();
        float var4 = par2Icon.getMaxU();
        float var5 = par2Icon.getMinV();
        float var6 = par2Icon.getMaxV();
        float var7 = 1.0f;
        float var8 = 0.5f;
        float var9 = 0.25f;
        GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setNormal(0.0f, 1.0f, 0.0f);
        par1Tessellator.addVertexWithUV(0.0f - var8, 0.0f - var9, 0.0, var3, var6);
        par1Tessellator.addVertexWithUV(var7 - var8, 0.0f - var9, 0.0, var4, var6);
        par1Tessellator.addVertexWithUV(var7 - var8, var7 - var9, 0.0, var4, var5);
        par1Tessellator.addVertexWithUV(0.0f - var8, var7 - var9, 0.0, var3, var5);
        par1Tessellator.draw();
    }
}
