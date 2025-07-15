package net.fabricmc.refamished.entities.tiles;

import btw.block.tileentity.TileEntityDataPacketHandler;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.blocks.cowhideTanning;
import net.minecraft.src.*;

public class tanningHorse extends TileEntity
        implements TileEntityDataPacketHandler {
    private final float m_iTimeToCook = ( 30 * 60 * 20 );
    private final float m_iRainCookDecay = 2;
    private int cookCounter = 0;
    private boolean isCooking = false;
    private int cookLevel; // 0–7


    public int getCookLevel() {
        return cookLevel;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey("fcCookCounter")) {
            this.cookCounter = tag.getInteger("fcCookCounter");
        }
        if (tag.hasKey("CookLevel")) {
            this.cookLevel = tag.getInteger("CookLevel");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("fcCookCounter", this.cookCounter);
        tag.setInteger("CookLevel", cookLevel);
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
        tag.setInteger("l", this.cookLevel);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, tag);
    }

    @Override
    public void readNBTFromPacket(NBTTagCompound tag) {
        this.isCooking = tag.getBoolean("x");
        this.cookLevel = tag.getInteger("l");
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
    }

    public void updateCooking() {
        int iCurrentCookLevel;
        int iDisplayedCookLevel;
        int iBlockAboveID = this.worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord);
        Block blockAbove = Block.blocksList[iBlockAboveID];
            this.isCooking = true;
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        cowhideTanning brickBlock = RefamishedBlocks.preparedCowhideBlock;
        if (this.isCooking) {
            ++this.cookCounter;
            if (this.cookCounter >= m_iTimeToCook) {
                brickBlock.onFinishedCooking(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
                return;
            }
        } else if (this.isRainingOnBrick(this.worldObj, this.xCoord, this.yCoord, this.zCoord)) {
            this.cookCounter -= m_iRainCookDecay;
            if (this.cookCounter < 0) {
                this.cookCounter = 0;
            }
        }
        if ((iDisplayedCookLevel = brickBlock.getCookLevel(this.worldObj, this.xCoord, this.yCoord, this.zCoord)) != (iCurrentCookLevel = this.computeCookLevel())) {
            cookLevel = iCurrentCookLevel;
        }
    }

    public boolean isRainingOnBrick(World world, int i, int j, int k) {
        return world.isRaining() && world.isRainingAtPos(i, j, k);
    }

    private int computeCookLevel() {
        if (this.cookCounter > 0) {
            int iCookLevel = (int)((float)this.cookCounter / m_iTimeToCook * 8.0f) + 1;
            return MathHelper.clamp_int(iCookLevel, 0, 8);
        }
        return 0;
    }
}
