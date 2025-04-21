package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.DoorBlock;
import btw.block.blocks.DoorBlockWood;
import net.minecraft.src.*;

public class thatchDoor extends DoorBlock {
    public thatchDoor(int iBlockID) {
        super(iBlockID, Material.wood);
        this.setHardness(0.5f);
        this.setBuoyant();
        this.setStepSound(soundWoodFootstep);
        this.setUnlocalizedName("doorThatch");
        this.setTextureName("refamished:thatch");
        this.disableStats();
    }

    @Override
    public boolean canPathThroughBlock(IBlockAccess blockAccess, int i, int j, int k, Entity entity, PathFinder pathFinder) {
        return pathFinder.CanPathThroughClosedWoodDoor() || pathFinder.canPathThroughOpenWoodDoor() && this.getBlocksMovement(blockAccess, i, j, k);
    }

    @Override
    public boolean isBreakableBarricade(World world, int i, int j, int k) {
        return true;
    }

    @Override
    public boolean isBreakableBarricadeOpen(IBlockAccess blockAccess, int i, int j, int k) {
        return this.isDoorOpen(blockAccess, i, j, k);
    }

    @Override
    public void onPoweredBlockChange(World par1World, int par2, int par3, int par4, boolean par5) {
    }

    @Override
    public void onAIOpenDoor(World world, int i, int j, int k, boolean bOpen) {
        super.onPoweredBlockChange(world, i, j, k, bOpen);
    }
}
