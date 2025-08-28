package net.fabricmc.refamished.entities;

import btw.entity.EntityWithCustomPacket;
import net.minecraft.src.*;

public class EntitySKOrb extends EntityXPOrb implements EntityWithCustomPacket {
    public int xpSkill;
    public boolean notPlayerOwned = false;
    public int xpColor;
    public int xpOrbAge;
    public int field_70532_c;
    private int xpOrbHealth = 5;
    public int xpValue;
    private EntityPlayer closestPlayer;
    private int xpTargetColor;

    public EntitySKOrb(World par1World, double par2, double par4, double par6, int par8, boolean bNotPlayerOwned) {
        this(par1World, par2, par4, par6, par8);
        this.notPlayerOwned = bNotPlayerOwned;
    }

    public EntitySKOrb(World par1World, double par2, double par4, double par6, int par8) {
        super(par1World);
        this.setSize(0.25f, 0.25f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(par2, par4, par6);
        this.rotationYaw = (float)(Math.random() * 360.0);
        this.motionX = (float)(Math.random() * (double)0.2f - (double)0.1f) * 2.0f;
        this.motionY = (float)(Math.random() * 0.2) * 2.0f;
        this.motionZ = (float)(Math.random() * (double)0.2f - (double)0.1f) * 2.0f;
        this.xpValue = par8;
    }

    public EntitySKOrb(World par1World) {
        super(par1World);
        this.setSize(0.25f, 0.25f);
        this.yOffset = this.height / 2.0f;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public int getBrightnessForRender(float par1) {
        float var2 = 0.5f;
        if (var2 < 0.0f) {
            var2 = 0.0f;
        }
        if (var2 > 1.0f) {
            var2 = 1.0f;
        }
        int var3 = super.getBrightnessForRender(par1);
        int var4 = var3 & 0xFF;
        int var5 = var3 >> 16 & 0xFF;
        if ((var4 += (int)(var2 * 15.0f * 16.0f)) > 240) {
            var4 = 240;
        }
        return var4 | var5 << 16;
    }

    @Override
    public void onUpdate() {
        double var7;
        double var5;
        double var3;
        double dDistanceSq;
        super.onUpdate();
        if (this.field_70532_c > 0) {
            --this.field_70532_c;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= (double)0.03f;
        if (this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) == Material.lava) {
            this.motionY = 0.2f;
            this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
            this.playSound("random.fizz", 0.4f, 2.0f + this.rand.nextFloat() * 0.4f);
        }
        if (!this.worldObj.isRemote) {
            this.pushOutOfBlocks(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0, this.posZ);
        }
        double var1 = 8.0;
        if (this.xpTargetColor < this.xpColor - 20 + this.entityId % 100) {
            if ((this.closestPlayer == null || this.closestPlayer.getDistanceSqToEntity(this) > var1 * var1) && !this.notPlayerOwned) {
                this.closestPlayer = this.worldObj.getClosestPlayerToEntity(this, var1);
            }
            this.xpTargetColor = this.xpColor;
        }
        if (this.closestPlayer != null && (dDistanceSq = (var3 = (this.closestPlayer.posX - this.posX) / var1) * var3 + (var5 = (this.closestPlayer.posY + (double)this.closestPlayer.getEyeHeight() - this.posY) / var1) * var5 + (var7 = (this.closestPlayer.posZ - this.posZ) / var1) * var7) < 1.0) {
            double var9 = Math.sqrt(dDistanceSq);
            double var11 = 1.0 - var9;
            var11 *= var11;
            this.motionX += var3 / var9 * var11 * 0.1;
            this.motionY += var5 / var9 * var11 * 0.1;
            this.motionZ += var7 / var9 * var11 * 0.1;
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float var13 = 0.98f;
        if (this.onGround) {
            var13 = 0.58800006f;
            int var4 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
            if (var4 > 0) {
                var13 = Block.blocksList[var4].slipperiness * 0.98f;
            }
        }
        this.motionX *= (double)var13;
        this.motionY *= (double)0.98f;
        this.motionZ *= (double)var13;
        if (this.onGround) {
            this.motionY *= (double)-0.9f;
        }
        ++this.xpColor;
        ++this.xpOrbAge;
        if (this.xpOrbAge >= 6000) {
            this.setDead();
        }
    }

    @Override
    public boolean handleWaterMovement() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
    }

    @Override
    public void dealFireDamage(int par1) {
        this.attackEntityFrom(DamageSource.inFire, par1);
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.setBeenAttacked();
        this.xpOrbHealth = (int)((float)this.xpOrbHealth - par2);
        if (this.xpOrbHealth <= 0) {
            this.setDead();
        }
        return false;
    }

    @Override
    protected boolean pushOutOfBlocks(double par1, double par3, double par5) {
        int var7 = MathHelper.floor_double(par1);
        int var8 = MathHelper.floor_double(par3);
        int var9 = MathHelper.floor_double(par5);
        double var10 = par1 - (double)var7;
        double var12 = par3 - (double)var8;
        double var14 = par5 - (double)var9;
        if (this.worldObj.isBlockNormalCube(var7, var8, var9)) {
            boolean var16 = !this.worldObj.isBlockNormalCube(var7 - 1, var8, var9);
            boolean var17 = !this.worldObj.isBlockNormalCube(var7 + 1, var8, var9);
            boolean var18 = !this.worldObj.isBlockNormalCube(var7, var8 - 1, var9);
            boolean var19 = !this.worldObj.isBlockNormalCube(var7, var8 + 1, var9);
            boolean var20 = !this.worldObj.isBlockNormalCube(var7, var8, var9 - 1);
            boolean var21 = !this.worldObj.isBlockNormalCube(var7, var8, var9 + 1);
            int var22 = -1;
            double var23 = 9999.0;
            if (var16 && var10 < var23) {
                var23 = var10;
                var22 = 0;
            }
            if (var17 && 1.0 - var10 < var23) {
                var23 = 1.0 - var10;
                var22 = 1;
            }
            if (var18 && var12 < var23) {
                var23 = var12;
                var22 = 2;
            }
            if (var19 && 1.0 - var12 < var23) {
                var23 = 1.0 - var12;
                var22 = 3;
            }
            if (var20 && var14 < var23) {
                var23 = var14;
                var22 = 4;
            }
            if (var21 && 1.0 - var14 < var23) {
                var23 = 1.0 - var14;
                var22 = 5;
            }
            float var25 = this.rand.nextFloat() * 0.2f + 0.1f;
            if (var22 == 0) {
                this.motionX = -var25;
            }
            if (var22 == 1) {
                this.motionX = var25;
            }
            if (var22 == 2) {
                this.motionY = -var25;
            }
            if (var22 == 3) {
                this.motionY = var25;
            }
            if (var22 == 4) {
                this.motionZ = -var25;
            }
            if (var22 == 5) {
                this.motionZ = var25;
            }
            return true;
        }
        return false;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("Health", (byte)this.xpOrbHealth);
        par1NBTTagCompound.setShort("Age", (short)this.xpOrbAge);
        par1NBTTagCompound.setShort("Value", (short)this.xpValue);
        par1NBTTagCompound.setBoolean("m_bNotPlayerOwned", this.notPlayerOwned);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.xpOrbHealth = par1NBTTagCompound.getShort("Health") & 0xFF;
        this.xpOrbAge = par1NBTTagCompound.getShort("Age");
        this.xpValue = par1NBTTagCompound.getShort("Value");
        if (par1NBTTagCompound.hasKey("m_bNotPlayerOwned")) {
            this.notPlayerOwned = par1NBTTagCompound.getBoolean("m_bNotPlayerOwned");
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
        if (this.notPlayerOwned) {
            return;
        }
        if (!this.worldObj.isRemote && this.field_70532_c == 0 && par1EntityPlayer.xpCooldown == 0) {
            par1EntityPlayer.xpCooldown = 2;
            this.playSound("random.orb", 0.1f, 0.5f * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.8f));
            par1EntityPlayer.onItemPickup(this, 1);
            par1EntityPlayer.addExperience(this.xpValue);
            this.setDead();
        }
    }

    public int getXpValue() {
        return this.xpValue;
    }

    public int getTextureByXP() {
        return this.xpValue >= 2477 ? 10 : (this.xpValue >= 1237 ? 9 : (this.xpValue >= 617 ? 8 : (this.xpValue >= 307 ? 7 : (this.xpValue >= 149 ? 6 : (this.xpValue >= 73 ? 5 : (this.xpValue >= 37 ? 4 : (this.xpValue >= 17 ? 3 : (this.xpValue >= 7 ? 2 : (this.xpValue >= 3 ? 1 : 0)))))))));
    }

    public static int getXPSplit(int par0) {
        return par0 >= 2477 ? 2477 : (par0 >= 1237 ? 1237 : (par0 >= 617 ? 617 : (par0 >= 307 ? 307 : (par0 >= 149 ? 149 : (par0 >= 73 ? 73 : (par0 >= 37 ? 37 : (par0 >= 17 ? 17 : (par0 >= 7 ? 7 : (par0 >= 3 ? 3 : 1)))))))));
    }

    @Override
    public boolean canAttackWithItem() {
        return false;
    }

    @Override
    protected void doBlockCollisions() {
        int j1;
        int i1;
        int l;
        int k;
        int j;
        int i = MathHelper.floor_double(this.boundingBox.minX + 0.001);
        if (this.worldObj.checkChunksExist(i, j = MathHelper.floor_double(this.boundingBox.minY - 0.01), k = MathHelper.floor_double(this.boundingBox.minZ + 0.001), l = MathHelper.floor_double(this.boundingBox.maxX - 0.001), i1 = MathHelper.floor_double(this.boundingBox.maxY - 0.001), j1 = MathHelper.floor_double(this.boundingBox.maxZ - 0.001))) {
            for (int k1 = i; k1 <= l; ++k1) {
                for (int l1 = j; l1 <= i1; ++l1) {
                    for (int i2 = k; i2 <= j1; ++i2) {
                        int j2 = this.worldObj.getBlockId(k1, l1, i2);
                        if (j2 <= 0) continue;
                        Block.blocksList[j2].onEntityCollidedWithBlock(this.worldObj, k1, l1, i2, this);
                    }
                }
            }
        }
    }

    @Override
    public int getTrackerViewDistance() {
        return 64;
    }

    @Override
    public int getTrackerUpdateFrequency() {
        return 10;
    }

    @Override
    public boolean getTrackMotion() {
        return true;
    }

    @Override
    public boolean shouldServerTreatAsOversized() {
        return false;
    }

    @Override
    public Packet getSpawnPacketForThisEntity() {
        return new Packet26EntityExpOrb();
    }

    public static int getVehicleSpawnPacketType() {
        return 3109;
    }
}
