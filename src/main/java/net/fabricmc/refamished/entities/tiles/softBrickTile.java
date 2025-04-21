package net.fabricmc.refamished.entities.tiles;

import btw.block.BTWBlocks;
import btw.block.tileentity.TileEntityDataPacketHandler;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.blocks.decorative.soft_brick;
import net.fabricmc.refamished.blocks.placedSoftClayBrick;
import net.minecraft.src.*;

public class softBrickTile
        extends TileEntity
        implements TileEntityDataPacketHandler {

    private static final int TIME_TO_COOK = 3000;
    private static final int RAIN_COOK_DECAY = 10;
    private static final int COOK_MULT = 10;
    private int cookCounter = 0;
    private boolean isCooking = false;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey("reCookTimer")) {
            this.cookCounter = tag.getInteger("reCookTimer");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("reCookTimer", this.cookCounter);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote) {
            this.updateCooking();
        } else if (this.isCooking && this.worldObj.rand.nextInt(20) == 0) {
            double xPos = (float)this.xCoord + 0.25f + this.worldObj.rand.nextFloat() * 0.5f;
            double yPos = (float)this.yCoord + 0.5f + this.worldObj.rand.nextFloat() * 0.25f;
            double zPos = (float)this.zCoord + 0.25f + this.worldObj.rand.nextFloat() * 0.5f;
            this.worldObj.spawnParticle("fcwhitesmoke", xPos, yPos, zPos, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("x", this.isCooking);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, tag);
    }

    @Override
    public void readNBTFromPacket(NBTTagCompound tag) {
        this.isCooking = tag.getBoolean("x");
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
    }

    public void updateCooking() {
        int iCurrentCookLevel;
        int iDisplayedCookLevel;
        int iBlockMaxNaturalLight = this.worldObj.getBlockNaturalLightValueMaximum(this.xCoord, this.yCoord, this.zCoord);
        int iBlockCurrentNaturalLight = iBlockMaxNaturalLight - this.worldObj.skylightSubtracted;
        int campfire = this.worldObj.getBlockId(this.xCoord, this.yCoord - 2, this.zCoord);
        boolean bNewCooking = campfire == BTWBlocks.largeCampfire.blockID
                || campfire == BTWBlocks.mediumCampfire.blockID
                || campfire == BTWBlocks.stokedFire.blockID
                || campfire == Block.fire.blockID;

        if (bNewCooking != this.isCooking) {
            this.isCooking = bNewCooking;
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
        soft_brick brickBlock = RefamishedBlocks.softBrickGround;
        if (this.isCooking) {
            this.cookCounter += COOK_MULT;
            if (this.cookCounter >= TIME_TO_COOK) {
                brickBlock.onFinishedCooking(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
                return;
            }
        } else if (this.isRainingOnBrick(this.worldObj, this.xCoord, this.yCoord, this.zCoord)) {
            this.cookCounter -= 10;
            if (this.cookCounter < 0) {
                this.cookCounter = 0;
            }
        }
        if ((iDisplayedCookLevel = brickBlock.getCookLevel(this.worldObj, this.xCoord, this.yCoord, this.zCoord)) != (iCurrentCookLevel = this.computeCookLevel())) {
            brickBlock.setCookLevel(this.worldObj, this.xCoord, this.yCoord, this.zCoord, iCurrentCookLevel);
        }
    }

    public boolean isRainingOnBrick(World world, int i, int j, int k) {
        return world.isRaining() && world.isRainingAtPos(i, j, k);
    }

    private int computeCookLevel() {
        if (this.cookCounter > 0) {
            int iCookLevel = (int)((float)this.cookCounter / TIME_TO_COOK * 3.0f) + 1;
            return MathHelper.clamp_int(iCookLevel, 0, 3);
        }
        return 0;
    }
}
