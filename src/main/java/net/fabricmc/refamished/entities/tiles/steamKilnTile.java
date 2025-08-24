package net.fabricmc.refamished.entities.tiles;

import btw.block.tileentity.TileEntityDataPacketHandler;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.blocks.steamKiln;
import net.fabricmc.refamished.misc.Recipes.CokeOvenSmeltingRecipes;
import net.minecraft.src.*;

import java.util.Random;

public class steamKilnTile extends copperConductTile {

    public static int maxSteam = 6020;
    public static boolean canTransfer = false;
    private int cookTime = 0;

    @Override
    public void updateEntity() {
        super.updateEntity();
        int meth = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        int targetX = xCoord;
        int targetY = yCoord;
        int targetZ = zCoord;
        int normalX = 0;
        int normalY = 0;
        int normalZ = 0;
        switch (meth & 7) {
            case 2: targetZ--; normalZ=-1; break;
            case 3: targetZ++; normalZ=1; break;
            case 4: targetX--; normalX=-1; break;
            case 5: targetX++; normalX=1; break;
            default: break;
        }
        Random rand = worldObj.rand;

        int blockId = worldObj.getBlockId(targetX, targetY, targetZ);
        int blockMeta = worldObj.getBlockMetadata(targetX, targetY, targetZ);

        steamKiln uhh = (steamKiln)Block.blocksList[worldObj.getBlockId(xCoord, yCoord, zCoord)];

        if (blockId != 0 && steam >= 4 && uhh.isPowered(meth) &&
                (isBlockInRing(targetX, targetY, targetZ, 1) || isBlockInRing(targetX, targetY, targetZ, 2)) &&
        !worldObj.isAirBlock(targetX+normalX,targetY,targetZ+normalZ)) {

            //if ((int)worldObj.getTotalWorldTime()%10 == 0) {
            //}
            //worldObj.setBlock(targetX, targetY, targetZ, Block.stone.blockID, 0, 3);

            CokeOvenSmeltingRecipes.RecipeEntry recipe = CokeOvenSmeltingRecipes.getInstance().getMatchingRecipe(new ItemStack(Block.blocksList[blockId],1, blockMeta),99);
            if (recipe != null && recipe.output.getItem() instanceof ItemBlock block) {
                drainSteam(6);
                int cockingTime = recipe.cookTimeTicks;
                cookTime++;
                if (cookTime >= cockingTime) {
                    worldObj.setBlockAndMetadataWithNotify(targetX, targetY, targetZ,block.getBlockID(),recipe.output.getItemDamage());
                    worldObj.playSound((double)targetX + 0.5, (double)targetY + 0.5, (double)targetZ + 0.5, "random.fizz", 0.3f + rand.nextFloat()*0.1f, 0.5f + rand.nextFloat() * 0.1f);
                    if (block.getBlockID() == RefamishedBlocks.cokeBlock.blockID) {
                        fillUpWithTarFreakyEmoji(targetX, targetY, targetZ);
                    }
                }
            }
            else {
                cookTime = 0;
            }
        }
        else {
            cookTime = 0;
        }
    }

    private boolean isBlockInRing(int x, int y, int z, int axis) {
        if (axis == 1) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dy == -1 && dx == 0) {
                        Block getBlock = Block.blocksList[worldObj.getBlockId(x+ dx, y + dy, z)];
                        if (getBlock == null || getBlock.isCollidable()) {
                            continue;
                        }
                    }
                    if (worldObj.getBlockId(x + dx, y + dy, z) != RefamishedBlocks.softBrickMortar.blockID && worldObj.getBlockId(x+ dx, y + dy, z) != RefamishedBlocks.tar_tank.blockID) return false;
                }
            }
            return true;
        } else if (axis == 2) {
            for (int dz = -1; dz <= 1; dz++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if ((dz == 0 && dy == 0)) continue;
                    if (dy == -1 && dz == 0) {
                        Block getBlock = Block.blocksList[worldObj.getBlockId(x, y + dy, z + dz)];
                        if (getBlock == null || getBlock.isCollidable()) {
                            continue;
                        }
                    }
                    if (worldObj.getBlockId(x, y + dy, z + dz) != RefamishedBlocks.softBrickMortar.blockID && worldObj.getBlockId(x, y + dy, z + dz) != RefamishedBlocks.tar_tank.blockID) return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean fillUpWithTarFreakyEmoji(int x, int y, int z) {
        for (int dx = -1; dx <= 1; dx++) {
            if (dx == 0) continue;
            if (worldObj.getBlockId(x + dx, y, z) == RefamishedBlocks.tar_tank.blockID) {
                TileEntity tile = worldObj.getBlockTileEntity(x + dx, y, z);
                if (tile instanceof tarTankTile tarTank) {
                    tarTank.addTar(1250);
                    return true;
                }
            }
        }
        for (int dz = -1; dz <= 1; dz++) {
            if (dz == 0) continue;
            if (worldObj.getBlockId(x, y, z + dz) == RefamishedBlocks.tar_tank.blockID) {
                TileEntity tile = worldObj.getBlockTileEntity(x, y, z + dz);
                if (tile instanceof tarTankTile tarTank) {
                    tarTank.addTar(1250);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        cookTime = tag.getInteger("cookTime");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("cookTime", cookTime);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("x", steam);
        tag.setInteger("c", cookTime);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, tag);
    }

    @Override
    public void readNBTFromPacket(NBTTagCompound tag) {
        steam = tag.getInteger("x");
        cookTime = tag.getInteger("c");
        this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
    }
}