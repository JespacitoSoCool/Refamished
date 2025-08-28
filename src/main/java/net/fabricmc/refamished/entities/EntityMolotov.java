package net.fabricmc.refamished.entities;

import btw.entity.EntityWithCustomPacket;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

public class EntityMolotov extends EntityThrowable implements EntityWithCustomPacket {
    public EntityMolotov(World par1World) {
        super(par1World);
    }

    public EntityMolotov(World world, EntityLivingBase entityLivingBase) {
        super(world, entityLivingBase);
    }

    public EntityMolotov(World world, double d, double e, double f) {
        super(world, d, e, f);
    }

    @Override
    protected void onImpact(MovingObjectPosition movingObjectPosition) {
        int n;
        if (movingObjectPosition.entityHit != null) {
            n = 0;
            if (movingObjectPosition.entityHit instanceof EntityBlaze) {
                n = 3;
            }
        }
        this.playSound("random.glass", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
        for (n = 0; n < 12; ++n) {
            this.worldObj.spawnParticle("iconcrack_"+Item.glassBottle.itemID, this.posX, this.posY, this.posZ, (float)(Math.random() * 2.0 - 1.0) * 0.1f, (float)(Math.random() * 2.0 - 1.0) * 0.1f, (float)(Math.random() * 2.0 - 1.0) * 0.1f);
        }
        for (int i = 0; i < 64; ++i) {
            this.worldObj.spawnParticle("iconcrack_"+ RefamishedItems.pile_ashes.itemID, this.posX, this.posY, this.posZ, (float)(Math.random() * 2.0 - 1.0) * 0.4f, (float)(Math.random() * 2.0 - 1.0) * 0.4f, (float)(Math.random() * 2.0 - 1.0) * 0.4f);
        }
        if (!this.worldObj.isRemote) {
            for (int dy = -1; dy < 2; dy++) {
                for (int dx = -2; dx < 1; dx++) {
                    for (int dz = -2; dz < 1; dz++) {
                        int checkX = (int)this.posX + dx;
                        int checkY = (int)this.posY + dy;
                        int checkZ = (int)this.posZ + dz;
                        if (worldObj.isAirBlock(checkX, checkY, checkZ))
                        {
                            worldObj.setBlock(checkX, checkY, checkZ, Block.fire.blockID);
                        }
                    }
                }
            }
            this.setDead();
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
        return new Packet23VehicleSpawn(this, 3374, this.getThrower() == null ? this.entityId : this.getThrower().entityId);
    }
}
