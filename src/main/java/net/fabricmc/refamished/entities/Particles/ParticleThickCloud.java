package net.fabricmc.refamished.entities.Particles;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ParticleThickCloud extends EntityFX {
    private final ResourceLocation textureAtlas = new ResourceLocation("refamished:textures/particle/smoke.png");
    private final int frames = 12;   // Total frames
    private final int frameSize = 16; // Size of each frame in pixels
    private final int atlasSize = 8; // 4x3 frames in a 64x48 texture (if 16x16 each)

    private int frameOffset;         // Random starting frame
    private float alpha;             // Fade-out alpha

    private static final List<ParticleThickCloud> ACTIVE_PARTICLES = new ArrayList<>();

    public ParticleThickCloud(World world, double x, double y, double z) {
        super(world, x, y, z, 0, 0, 0);

        // Randomize starting frame (0-5)
        this.frameOffset = rand.nextInt(6);
        ACTIVE_PARTICLES.add(this);
        this.particleScale = 0.6F + rand.nextFloat();
        this.particleGravity = (rand.nextFloat() * -0.2F)-0.2f;
        this.particleMaxAge = 75 + rand.nextInt(50); // Duration

        // Motion (gentle drift)
        this.motionX = (rand.nextDouble() - 0.5) * 0.07;
        this.motionY = 0.03 + (rand.nextDouble() * 0.02);
        this.motionZ = (rand.nextDouble() - 0.5) * 0.07;

        // Color (cloudy white)
        this.particleRed = this.particleGreen = this.particleBlue = 0.9f + rand.nextFloat() * 0.1f;
    }


    @Override
    public void onUpdate() {
        super.onUpdate();
        // Do collision detection
        checkParticleCollisions();

        // Remove if dead
        if (this.isDead) {
            ACTIVE_PARTICLES.remove(this);
        }
    }

    @Override
    public int getFXLayer() {
        return 2;
    }

    @Override
    public void renderParticle(Tessellator tessellator, float partialTicks, float rotX, float rotZ, float rotYZ, float rotXY, float rotXZ) {
        float scale = this.particleScale;
        float interpX = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
        float interpY = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
        float interpZ = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);

        int frameIndex = ((this.particleAge / this.particleMaxAge) + this.frameOffset) % frames;

        int textureWidth = frames * frameSize;

        GL11.glDepthMask(false);
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Minecraft.getMinecraft().renderEngine.bindTexture(textureAtlas);

        int frameX = frameIndex % atlasSize;
        int frameY = frameIndex / atlasSize;
        float uMin = frameX * (16f / (atlasSize * frameSize));
        float uMax = uMin + (16f / (atlasSize * frameSize));
        float vMin = frameY * (16f / (atlasSize * frameSize));
        float vMax = vMin + (16f / (atlasSize * frameSize));
        // Fade-out alpha
        float lifeRatio = (float)this.particleAge / (float)this.particleMaxAge;
        alpha = lifeRatio < 0.7f ? 1.0f : 1.0f - (lifeRatio - 0.7f) / 0.3f;

        tessellator.setBrightness(240);
        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, alpha*0.65f);

        tessellator.addVertexWithUV(interpX - rotX * scale - rotXY * scale, interpY - rotZ * scale, interpZ - rotYZ * scale - rotXZ * scale, uMin, vMax);
        tessellator.addVertexWithUV(interpX - rotX * scale + rotXY * scale, interpY + rotZ * scale, interpZ - rotYZ * scale + rotXZ * scale, uMin, vMin);
        tessellator.addVertexWithUV(interpX + rotX * scale + rotXY * scale, interpY + rotZ * scale, interpZ + rotYZ * scale + rotXZ * scale, uMax, vMin);
        tessellator.addVertexWithUV(interpX + rotX * scale - rotXY * scale, interpY - rotZ * scale, interpZ + rotYZ * scale - rotXZ * scale, uMax, vMax);

        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopAttrib();
    }

    private void checkParticleCollisions() {
        for (ParticleThickCloud other : ACTIVE_PARTICLES) {
            if (other != this && !other.isDead) {
                double dx = this.posX - other.posX;
                double dy = this.posY - other.posY;
                double dz = this.posZ - other.posZ;
                double distSq = dx*dx + dy*dy + dz*dz;
                double collisionDistance = 0.2; // adjust size of "particle"
                if (distSq < collisionDistance * collisionDistance) {
                    // Collision behavior: e.g., stop, bounce, die
                    handleCollisionWith(other);
                }
            }
        }
    }

    private void handleCollisionWith(ParticleThickCloud other) {
        // Example: just stop both particles
        this.motionX = this.motionY = this.motionZ = 0;
        other.motionX = other.motionY = other.motionZ = 0;
    }
}
