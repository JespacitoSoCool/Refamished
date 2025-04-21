package net.fabricmc.refamished.entities;

import btw.entity.EntityWithCustomPacket;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

public class EntityCopperBolt extends EntityArrow implements EntityWithCustomPacket {

    public EntityCopperBolt(World par1World) {
        super(par1World);
    }

    public EntityCopperBolt(World par1World, double par2, double par4, double par6) {
        super(par1World);
        this.renderDistanceWeight = 10.0;
        this.setSize(0.5f, 0.5f);
        this.setPosition(par2, par4, par6);
        this.yOffset = 0.0f;
    }

    public EntityCopperBolt(World par1World, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase, float par4, float par5) {
        super(par1World);
        this.renderDistanceWeight = 10.0;
        this.shootingEntity = par2EntityLivingBase;
        if (par2EntityLivingBase instanceof EntityPlayer) {
            this.canBePickedUp = 1;
        }
        this.posY = par2EntityLivingBase.posY + (double)par2EntityLivingBase.getEyeHeight() - (double)0.1f;
        double var6 = par3EntityLivingBase.posX - par2EntityLivingBase.posX;
        double var8 = par3EntityLivingBase.boundingBox.minY + (double)(par3EntityLivingBase.height / 3.0f) - this.posY;
        double var10 = par3EntityLivingBase.posZ - par2EntityLivingBase.posZ;
        double var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10);
        if (var12 >= 1.0E-7) {
            float var14 = (float)(Math.atan2(var10, var6) * 180.0 / Math.PI) - 90.0f;
            float var15 = (float)(-(Math.atan2(var8, var12) * 180.0 / Math.PI));
            double var16 = var6 / var12;
            double var18 = var10 / var12;
            this.setLocationAndAngles(par2EntityLivingBase.posX + var16, this.posY, par2EntityLivingBase.posZ + var18, var14, var15);
            this.yOffset = 0.0f;
            float var20 = (float)var12 * 0.2f;
            this.setThrowableHeading(var6, var8 + (double)var20, var10, par4, par5);
        }
    }

    public EntityCopperBolt(World par1World, EntityLivingBase par2EntityLivingBase, float par3) {
        super(par1World);
        this.renderDistanceWeight = 10.0;
        this.shootingEntity = par2EntityLivingBase;
        if (par2EntityLivingBase instanceof EntityPlayer) {
            this.canBePickedUp = 1;
        }
        this.setSize(0.5f, 0.5f);
        this.setLocationAndAngles(par2EntityLivingBase.posX, par2EntityLivingBase.posY + (double)par2EntityLivingBase.getEyeHeight(), par2EntityLivingBase.posZ, par2EntityLivingBase.rotationYaw, par2EntityLivingBase.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
        this.posY -= (double)0.1f;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI);
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * (float)Math.PI);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, par3 * 1.5f, 1.0f);
    }

    @Override
    protected float getDamageMultiplier() {
        return 0.9f;
    }

    @Override
    public Item getCorrespondingItem() {
        return RefamishedItems.bolt_iron;
    }


    @Override
    public Packet getSpawnPacketForThisEntity() {
        return new Packet23VehicleSpawn(this, EntityCopperBolt.getVehicleSpawnPacketType(), this.shootingEntity == null ? this.entityId : this.shootingEntity.entityId);
    }

    @Override
    public int getTrackerViewDistance() {
        return 64;
    }

    @Override
    public int getTrackerUpdateFrequency() {
        return 20;
    }

    @Override
    public boolean getTrackMotion() {
        return false;
    }

    @Override
    public boolean shouldServerTreatAsOversized() {
        return false;
    }

    public static int getVehicleSpawnPacketType() {
        return 3105;
    }
}
