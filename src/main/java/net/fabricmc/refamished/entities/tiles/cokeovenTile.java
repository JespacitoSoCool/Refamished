package net.fabricmc.refamished.entities.tiles;

import btw.block.tileentity.OvenTileEntity;
import btw.block.tileentity.TileEntityDataPacketHandler;
import btw.item.util.ItemUtils;
import btw.world.util.BlockPos;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.blocks.CokeOven;
import net.fabricmc.refamished.misc.Recipes.CokeOvenSmeltingRecipes;
import net.minecraft.src.*;
import org.lwjgl.Sys;

public class cokeovenTile extends TileEntityFurnace
        implements TileEntityDataPacketHandler {
    private boolean lightOnNextUpdate = false;
    private ItemStack cookStack = null;
    public int fireLevel = 0;

    @Override
    public void updateEntity() {
        boolean bInventoryChanged = false;
        if (!this.worldObj.isRemote) {
            if (CokeOven.isValidStructure(worldObj,xCoord,yCoord,zCoord))
            {
                fireLevel = CokeOven.getFirePower(worldObj,xCoord,yCoord,zCoord);
            }
            else {
                fireLevel = 0;
            }
            if (isBurning()) {
                ++this.furnaceCookTime;
                //System.out.println(this.getCookTimeForCurrentItem()-this.furnaceCookTime);
                if (this.furnaceCookTime >= this.getCookTimeForCurrentItem()) {
                    this.furnaceCookTime = 0;
                    this.smeltItem();
                    bInventoryChanged = true;
                }
            } else {
                this.furnaceCookTime = 0;
            }
            this.updateCookStack();
        }
        if (bInventoryChanged) {
            this.onInventoryChanged();
        }
    }

    @Override
    public boolean isBurning() {
        return this.fireLevel > 0;
    }

    @Override
    protected int getCookTimeForCurrentItem() {
        int iCookTimeShift = 0;
        if (this.furnaceItemStacks[0] != null) {
            iCookTimeShift = CokeOvenSmeltingRecipes.getInstance().getCookTime(this.furnaceItemStacks[0],fireLevel);
        }
        return iCookTimeShift;
    }

    @Override
    protected boolean canSmelt() {
        if (this.furnaceItemStacks[0] == null) {
            return false;
        }
        ItemStack var1 = CokeOvenSmeltingRecipes.getInstance().getResult(this.furnaceItemStacks[0],fireLevel);
        if (var1 == null) {
            return false;
        }
        return true;
    }

    @Override
    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack var1 = CokeOvenSmeltingRecipes.getInstance().getResult(this.furnaceItemStacks[0],fireLevel);
            if (var1.getItem().itemID == RefamishedItems.coke_coal.itemID)
            {
                TileEntity tile = worldObj.getBlockTileEntity(xCoord,yCoord-1,zCoord);
                if (tile instanceof tarTankTile) {
                    tarTankTile tank = (tarTankTile)tile;
                    tank.addTar(250);
                }
                this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord-1, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
            }
            if (this.furnaceItemStacks[2] == null) {
                this.furnaceItemStacks[2] = var1.copy();
            } else if (this.furnaceItemStacks[2].itemID == var1.itemID) {
                this.furnaceItemStacks[2].stackSize += var1.stackSize;
            }
            --this.furnaceItemStacks[0].stackSize;
            if (this.furnaceItemStacks[0].stackSize <= 0) {
                this.furnaceItemStacks[0] = null;
            }
        }
    }

    @Override
    public String getInvName() {
        return "container.fcFurnaceBrick";
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        if (this.cookStack != null) {
            NBTTagCompound cookTag = new NBTTagCompound();
            this.cookStack.writeToNBT(cookTag);
            tag.setCompoundTag("x", cookTag);
        }
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, tag);
    }

    @Override
    public void readNBTFromPacket(NBTTagCompound tag) {
        NBTTagCompound cookTag = tag.getCompoundTag("x");
        if (cookTag != null) {
            this.cookStack = ItemStack.loadItemStackFromNBT(cookTag);
        }
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
    }

    private void updateCookStack() {
        ItemStack newCookStack = this.furnaceItemStacks[0];
        if (newCookStack == null && (newCookStack = this.furnaceItemStacks[2]) == null) {
            newCookStack = this.furnaceItemStacks[1];
        }
        if (!ItemStack.areItemStacksEqual(newCookStack, this.cookStack)) {
            this.setCookStack(newCookStack);
        }
    }

    public void setCookStack(ItemStack stack) {
        this.cookStack = stack != null ? stack.copy() : null;
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    public ItemStack getCookStack() {
        return cookStack;
    }

    public void givePlayerCookStack(EntityPlayer player, int iFacing) {
        if (!this.worldObj.isRemote) {
            this.ejectAllNotCookStacksToFacing(player, iFacing);
        }
        ItemUtils.givePlayerStackOrEjectFromTowardsFacing(player, this.cookStack, this.xCoord, this.yCoord, this.zCoord, iFacing);
        this.furnaceItemStacks[0] = null;
        this.furnaceItemStacks[1] = null;
        this.furnaceItemStacks[2] = null;
        this.setCookStack(null);
        this.onInventoryChanged();
    }

    private void ejectAllNotCookStacksToFacing(EntityPlayer player, int iFacing) {
        if (this.furnaceItemStacks[0] != null && !ItemStack.areItemStacksEqual(this.furnaceItemStacks[0], this.cookStack)) {
            ItemUtils.ejectStackFromBlockTowardsFacing(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.furnaceItemStacks[0], iFacing);
            this.furnaceItemStacks[0] = null;
        }
        if (this.furnaceItemStacks[1] != null && !ItemStack.areItemStacksEqual(this.furnaceItemStacks[1], this.cookStack)) {
            ItemUtils.ejectStackFromBlockTowardsFacing(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.furnaceItemStacks[1], iFacing);
            this.furnaceItemStacks[1] = null;
        }
        if (this.furnaceItemStacks[2] != null && !ItemStack.areItemStacksEqual(this.furnaceItemStacks[2], this.cookStack)) {
            ItemUtils.ejectStackFromBlockTowardsFacing(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.furnaceItemStacks[2], iFacing);
            this.furnaceItemStacks[2] = null;
        }
        this.onInventoryChanged();
    }

    public void addCookStack(ItemStack stack) {
        this.furnaceItemStacks[0] = stack;
        this.onInventoryChanged();
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public int[] getSlotsForFace(int i) {
        return new int[0];
    }
}
