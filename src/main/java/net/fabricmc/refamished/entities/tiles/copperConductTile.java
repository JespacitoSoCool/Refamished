package net.fabricmc.refamished.entities.tiles;

import btw.block.tileentity.TileEntityDataPacketHandler;
import net.minecraft.src.*;

public class copperConductTile extends TileEntity implements TileEntityDataPacketHandler {

    public static int maxSteam = 6000;
    public static boolean canTransfer = true;
    public int steam = 0;

    public int getSteamAmount() {
        return steam;
    }

    public void addSteam(int amount) {
        steam = Math.min(maxSteam, steam + amount);
        syncDataToClient();
    }

    public void drainSteam(int amount) {
        steam = Math.max(0, steam - amount);
        syncDataToClient();
    }

    public boolean depositSteam(int amount) {
        if (amount <= steam)
        {
            drainSteam(amount);
            return true;
        }
        return false;
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote && canTransfer) {
            transferSteamToNeighbors();
        }
    }

    private void transferSteamToNeighbors() {
        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;

        // 6 directions
        int[][] dirs = {
                { 1, 0, 0 }, { -1, 0, 0 },
                { 0, 1, 0 }, { 0, -1, 0 },
                { 0, 0, 1 }, { 0, 0, -1 }
        };

        for (int[] d : dirs) {
            TileEntity te = worldObj.getBlockTileEntity(x + d[0], y + d[1], z + d[2]);
            if (te instanceof copperConductTile) {
                copperConductTile neighbor = (copperConductTile) te;
                balanceWith(neighbor);
            }
        }
    }

    private static final int MAX_TRANSFER = 5;

    private void balanceWith(copperConductTile other) {
        if (this.steam == other.steam) return;

        if (this.steam > other.steam) {
            int move = Math.min((this.steam - other.steam) / 2, MAX_TRANSFER);
            this.steam -= move;
            other.steam += move;
        } else {
            int move = Math.min((other.steam - this.steam) / 2, MAX_TRANSFER);
            other.steam -= move;
            this.steam += move;
        }

        this.syncDataToClient();
        other.syncDataToClient();
    }



    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        steam = tag.getInteger("steam");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("steam", steam);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("x", steam);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, tag);
    }

    @Override
    public void readNBTFromPacket(NBTTagCompound tag) {
        steam = tag.getInteger("x");
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
    }

    public void syncDataToClient() {
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
