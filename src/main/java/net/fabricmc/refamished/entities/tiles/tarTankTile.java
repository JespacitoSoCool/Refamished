package net.fabricmc.refamished.entities.tiles;

import btw.block.tileentity.TileEntityDataPacketHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class tarTankTile extends TileEntity implements TileEntityDataPacketHandler {
    public static final int MAX_TAR = 8000; // in milliliters = 5 liters
    private int tarAmount = 0;

    public int getTarAmount() {
        return tarAmount;
    }

    public void addTar(int amount) {
        tarAmount = Math.min(MAX_TAR, tarAmount + amount);
        syncTarToClient();
    }

    public void drainTar(int amount) {
        tarAmount = Math.max(0, tarAmount - amount);
        syncTarToClient();
    }

    public boolean depositTar(int amount) {
        if (amount <= tarAmount)
        {
            drainTar(amount);
            return true;
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        tarAmount = tag.getInteger("TarAmount");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("TarAmount", tarAmount);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("x", tarAmount);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, tag);
    }

    @Override
    public void readNBTFromPacket(NBTTagCompound tag) {
        tarAmount = tag.getInteger("x");
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
    }

    public void syncTarToClient() {
        if (!worldObj.isRemote) {
            Packet132TileEntityData packet = (Packet132TileEntityData) getDescriptionPacket();
            for (Object obj : ((WorldServer) worldObj).playerEntities) {
                EntityPlayerMP player = (EntityPlayerMP) obj;
                if (player != null && player.worldObj == this.worldObj) {
                    int dx = player.chunkCoordX - (xCoord >> 4);
                    int dz = player.chunkCoordZ - (zCoord >> 4);
                    if (Math.abs(dx) <= 1 && Math.abs(dz) <= 1) {
                        player.playerNetServerHandler.sendPacket(packet);
                    }
                }
            }
        }
    }
}
