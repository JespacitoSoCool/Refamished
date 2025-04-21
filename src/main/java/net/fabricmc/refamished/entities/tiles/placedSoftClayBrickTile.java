package net.fabricmc.refamished.entities.tiles;

import btw.block.BTWBlocks;
import btw.block.blocks.UnfiredBrickBlock;
import btw.block.tileentity.TileEntityDataPacketHandler;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.blocks.placedSoftClayBrick;
import net.minecraft.src.*;

public class placedSoftClayBrickTile
        extends TileEntity
        implements TileEntityDataPacketHandler {

    private static final int TIME_TO_COOK = 11900;
    private static final int RAIN_COOK_DECAY = 10;
    private int cookCounter = 0;
    private boolean isCooking = false;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey("fcCookCounter")) {
            this.cookCounter = tag.getInteger("fcCookCounter");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("fcCookCounter", this.cookCounter);
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
        boolean bNewCooking = iBlockCurrentNaturalLight >= 15;
        int iBlockAboveID = this.worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord);
        Block blockAbove = Block.blocksList[iBlockAboveID];
        if (blockAbove != null && blockAbove.isGroundCover()) {
            bNewCooking = false;
        }
        if (bNewCooking != this.isCooking) {
            this.isCooking = bNewCooking;
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
        placedSoftClayBrick brickBlock = RefamishedBlocks.clayMudBrickGround;
        if (this.isCooking) {
            ++this.cookCounter;
            if (this.cookCounter >= 11900) {
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
            int iCookLevel = (int)((float)this.cookCounter / 11900.0f * 7.0f) + 1;
            return MathHelper.clamp_int(iCookLevel, 0, 7);
        }
        return 0;
    }
}
