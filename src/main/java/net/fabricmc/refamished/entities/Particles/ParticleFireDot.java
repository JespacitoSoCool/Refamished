package net.fabricmc.refamished.entities.Particles;

import net.minecraft.src.*;
import org.lwjgl.Sys;

public class ParticleFireDot extends EntityFX {
    private final Icon fireIcon;
    private final float uMin, uMax, vMin, vMax;

    public ParticleFireDot(World world, double x, double y, double z) {
        super(world, x, y, z, 0, 0, 0);
        fireIcon = Block.fire.getIcon(0, 1);

        float subDiv = 16f;
        float randX = rand.nextInt(16);
        float randY = rand.nextInt(16);

        uMin = fireIcon.getMinU() + (randX / subDiv) * (fireIcon.getMaxU() - fireIcon.getMinU());
        uMax = uMin + (1f / subDiv) * (fireIcon.getMaxU() - fireIcon.getMinU());
        vMin = fireIcon.getMinV() + (randY / subDiv) * (fireIcon.getMaxV() - fireIcon.getMinV());
        vMax = vMin + (1f / subDiv) * (fireIcon.getMaxV() - fireIcon.getMinV());

        // Set scale and lifetime
        this.particleScale = 0.3F;
        this.particleGravity = -0.1F;
        this.particleMaxAge = 40 + rand.nextInt(60);

        // Set bright color (fire orange)
        this.particleRed = 1.0F;
        this.particleGreen = 0.5F + rand.nextFloat() * 0.5F;
        this.particleBlue = 0.0F;

        // Random small motion
        this.motionX = (rand.nextDouble() - 0.5) * 0.07;
        this.motionY = 0.2 + (rand.nextDouble() * 0.05);
        this.motionZ = (rand.nextDouble() - 0.5) * 0.07;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void renderParticle(Tessellator tessellator, float partialTicks, float rotX, float rotZ, float rotYZ, float rotXY, float rotXZ) {
        float scale = 0.1f * this.particleScale;
        float interpX = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
        float interpY = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
        float interpZ = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);

        // Make the particle fully bright
        float lifeRatio = (float)this.particleAge / (float)this.particleMaxAge;
        // Alpha fades starting at 50% life
        float alpha = 1.0f;
        if (lifeRatio > 0.5f) {
            alpha = 1.0f - (lifeRatio - 0.5f) * 2.0f; // Fade out linearly to 0
        }

        tessellator.setBrightness(240);
        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, alpha);

        tessellator.addVertexWithUV(interpX - rotX * scale - rotXY * scale, interpY - rotZ * scale, interpZ - rotYZ * scale - rotXZ * scale, uMin, vMax);
        tessellator.addVertexWithUV(interpX - rotX * scale + rotXY * scale, interpY + rotZ * scale, interpZ - rotYZ * scale + rotXZ * scale, uMin, vMin);
        tessellator.addVertexWithUV(interpX + rotX * scale + rotXY * scale, interpY + rotZ * scale, interpZ + rotYZ * scale + rotXZ * scale, uMax, vMin);
        tessellator.addVertexWithUV(interpX + rotX * scale - rotXY * scale, interpY - rotZ * scale, interpZ + rotYZ * scale - rotXZ * scale, uMax, vMax);
    }
}
