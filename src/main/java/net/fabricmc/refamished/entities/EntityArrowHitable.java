package net.fabricmc.refamished.entities;

import btw.block.BTWBlocks;
import btw.entity.EntityWithCustomPacket;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.mixin.mixin_m.ArrowInterface;
import net.minecraft.src.*;

import java.util.List;

public class EntityArrowHitable extends EntityArrow {

    boolean oldIsGround = false;
    public EntityArrowHitable(World par1World) {
        super(par1World);
    }

    public EntityArrowHitable(World par1World, double par2, double par4, double par6) {
        super(par1World);
        this.renderDistanceWeight = 10.0;
        this.setSize(0.5f, 0.5f);
        this.setPosition(par2, par4, par6);
        this.yOffset = 0.0f;
    }

    public EntityArrowHitable(World par1World, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase, float par4, float par5) {
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

    public EntityArrowHitable(World par1World, EntityLivingBase par2EntityLivingBase, float par3) {
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
    public void onUpdate() {
        ArrowInterface arrowInt = (ArrowInterface)this;
        int ticksInAir = arrowInt.getTicksInAir();
        int ticksInGround = arrowInt.getTicksInGround();
        AxisAlignedBB var2;
        int var16;
        if (!this.inGround) {
            int var9;
            List var6 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            for (var9 = 0; var9 < var6.size(); ++var9) {
                Entity var10 = (Entity)var6.get(var9);
                if (!var10.canBeCollidedWith() || var10 == this.shootingEntity && ticksInAir < 5) continue;
                if (var10 instanceof EntityLivingBase) {
                    onEntityHit(var10);
                }
            }
        }
        super.onUpdate();
    }

    public void onEntityHit(Entity ent) {}
}
