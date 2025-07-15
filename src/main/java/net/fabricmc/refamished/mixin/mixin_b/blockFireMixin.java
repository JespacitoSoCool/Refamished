package net.fabricmc.refamished.mixin.mixin_b;

import btw.block.BTWBlocks;
import btw.block.blocks.SlabBlock;
import btw.block.blocks.WoodSlabBlock;
import btw.item.BTWItems;
import btw.item.util.ItemUtils;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.blocks.decorative.burnt.baseBurntBlock;
import net.fabricmc.refamished.blocks.decorative.burnt.baseBurntSlab;
import net.fabricmc.refamished.blocks.decorative.burnt.burntChest;
import net.fabricmc.refamished.mixin.interfaces.blockFireInterface;
import net.minecraft.src.*;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(BlockFire.class)
public class blockFireMixin {
//    @Inject(method = "tryToDestroyBlockWithFire",at = @At("TAIL"), cancellable = true)
//    public void tryToDestroyBlockWithFire(World world, int x, int y, int z, int chanceToDestroy, Random random, int sourceMetadata, CallbackInfo ci) {
//        //if (random.nextInt(2) < 1) {
//            int id = world.getBlockId(x, y, z);
//            if (id == Block.dirt.blockID || id == BTWBlocks.looseDirt.blockID) {
//                world.setBlockWithNotify(x,y,z,RefamishedBlocks.charredDirt.blockID);
//                ci.cancel();
//            }
//            else if (id == Block.chest.blockID || id == BTWBlocks.chest.blockID) {
//                TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
//                if (tileEntity != null) {
//                    tileEntity.validate();
//                    world.setBlockWithNotify(x, y, z, RefamishedBlocks.burntChest.blockID);
//                    world.setBlockTileEntity(x, y, z, tileEntity);
//                    ci.cancel();
//                }
//            }
//        //}
//    }

    @Unique
    public boolean attemptToBurn(World world, int x, int y, int z, Random random) {
        if (random.nextInt(5) < 1) {
        int id = world.getBlockId(x, y, z);
            Block get = Block.blocksList[id];
            int meta = world.getBlockMetadata(x, y, z);
            if (id == Block.dirt.blockID || id == Block.grass.blockID || id == BTWBlocks.looseDirt.blockID) {
                world.setBlockWithNotify(x,y,z,RefamishedBlocks.charredDirt.blockID);

                return true;
            }
            else if (id == Block.tallGrass.blockID || id == Block.deadBush.blockID) {
                int idBelow = world.getBlockId(x, y-1, z);
                if (idBelow == Block.dirt.blockID || idBelow == Block.grass.blockID || idBelow == BTWBlocks.looseDirt.blockID) {
                    world.setBlockWithNotify(x,y-1,z,RefamishedBlocks.charredDirt.blockID);
                    world.setBlockWithNotify(x,y,z,Block.fire.blockID);
                    return true;
                }
                return true;
            }
            else if (id == BTWBlocks.looseDirtSlab.blockID || id == BTWBlocks.grassSlab.blockID) {
                world.setBlockWithNotify(x,y,z,RefamishedBlocks.charredDirtSlab.blockID);
                return true;
            }
            else if (id == Block.planks.blockID) {
                world.setBlockWithNotify(x,y,z,RefamishedBlocks.burntPlanks.blockID);
                return true;
            }
            else if (id == Block.woodSingleSlab.blockID && get instanceof WoodSlabBlock) {
                WoodSlabBlock slab = (WoodSlabBlock) get;
                boolean isUpsideDown = slab.getIsUpsideDown(meta);
                world.setBlockAndMetadataWithNotify(x, y, z, RefamishedBlocks.burntPlanksSlab.blockID, isUpsideDown ? 1 : 0);
                return true;
            }

            else if (id == RefamishedBlocks.charredDirt.blockID || id == RefamishedBlocks.charredDirtSlab.blockID) {
                if (meta <= 14) {
                    world.setBlockMetadataWithNotify(x,y,z,meta+1);
                }
                return true;
            }
            else if (get instanceof baseBurntBlock) {
                baseBurntBlock bloc = (baseBurntBlock)get;
                if (bloc.getBurnLevel(world.getBlockMetadata(x,y,z)) <= 4) {
                    world.setBlockMetadataWithNotify(x,y,z,meta+1);
                }
                return true;
            }
            else if (get instanceof SlabBlock && get instanceof baseBurntSlab) {
                baseBurntSlab bloc = (baseBurntSlab)get;
                if (bloc.getBurnLevel(world.getBlockMetadata(x,y,z)) <= 4) {
                    world.setBlockMetadataWithNotify(x,y,z,meta+2);
                }
                return true;
            }
//        else if (id == Block.chest.blockID || id == BTWBlocks.chest.blockID) {
//            Block ts = Block.blocksList[id];
//            if (ts instanceof BlockChest)
//            {
//                blockChestMixin.burn();
//                burntChest chest = (burntChest)ts;
//                chest.burn(world,x,y,z);
//            }
//        }
        }
        return false;
    }


    @Inject(method = "updateTick",at = @At("TAIL"), cancellable = true)
    private void thisShit(World world, int x, int y, int z, Random rand, CallbackInfo ci) {
        BlockFire fire = (BlockFire)(Object)this;
        blockFireInterface fint = (blockFireInterface)fire;
        if (world.getGameRules().getGameRuleBooleanValue("doFireTick")) {
            boolean haveBurnt = attemptToBurn(world,x,y+1,z,rand) ||
                    attemptToBurn(world,x,y-1,z,rand) ||
                    attemptToBurn(world,x+1,y,z,rand) ||
                    attemptToBurn(world,x-1,y,z,rand) ||
                    attemptToBurn(world,x,y,z+1,rand) ||
                    attemptToBurn(world,x,y,z+1,rand);
            if (haveBurnt) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "randomDisplayTick",at = @At("HEAD"))
    private void wh(World world, int x, int y, int z, Random rand, CallbackInfo ci) {
        if (world.getBlockId(x,y-1,z) != BTWBlocks.hibachi.blockID && world.getBlockId(x,y-2,z) != BTWBlocks.hibachi.blockID) {
            for (int iTempCount = 0; iTempCount < 1; ++iTempCount) {
                double xPos = (double)x + 0.5 - 0.6 + rand.nextDouble() * 1.2;
                double yPos = (double)y + 0.5 + 0.25 + rand.nextDouble() * 0.25;
                double zPos = (double)z + 0.5 - 0.6 + rand.nextDouble() * 1.2;
                world.spawnParticle("firedot", xPos, yPos, zPos, 0.0, 0.0, 0.0);
            }
            for (int iTempCount = 3; iTempCount <5; ++iTempCount) {
                double xPos = (double)x + 0.5 - 0.6 + rand.nextDouble() * 1.2;
                double yPos = (double)y + 0.5 + 0.25 + rand.nextDouble() * 0.25;
                double zPos = (double)z + 0.5 - 0.6 + rand.nextDouble() * 1.2;
                world.spawnParticle("thicksmoke", xPos, yPos, zPos, 0.0, 0.0, 0.0);
            }
        }
        else {
            double xPos = (double)x + 0.5 - 0.6 + rand.nextDouble() * 1.2;
            double yPos = (double)y + 0.5 + 0.25 + rand.nextDouble() * 0.25;
            double zPos = (double)z + 0.5 - 0.6 + rand.nextDouble() * 1.2;
            world.spawnParticle("firedot", xPos, yPos, zPos, 0.0, 0.0, 0.0);
        }
    }
}
