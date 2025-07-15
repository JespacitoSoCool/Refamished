package net.fabricmc.refamished.mixin.mixin_b;

import btw.block.BTWBlocks;
import btw.block.blocks.HempCropBlock;
import btw.item.BTWItems;
import btw.item.util.ItemUtils;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.items.tools.bladeIron;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(BlockGrass.class)
public class blockGrassMixin {
    @Inject(method = "convertBlock",at = @At("HEAD"), cancellable = true)
    private void canUseBlade(ItemStack stack, World world, int x, int y, int z, int fromSide, CallbackInfoReturnable<Boolean> cir) {
        world.setBlockWithNotify(x, y, z, BTWBlocks.looseDirt.blockID);
        BlockGrass this_ = (BlockGrass)(Object)this;
        BiomeGenBase biome = world.getBiomeGenForCoords(x,z);
        Random rand = world.rand;
        if (!world.isRemote) {
            world.playAuxSFX(2291, x, y, z, 0);
            if (world.rand.nextInt(22) == 0) {
                if (biome == BiomeGenBase.desert || biome == BiomeGenBase.desertHills) {
                    ItemUtils.ejectStackFromBlockTowardsFacing(world, x, y, z, new ItemStack(BTWItems.hempSeeds), fromSide);
                }
            }
            if (world.rand.nextInt(25) == 0) {
                if (biome == BiomeGenBase.forest || biome == BiomeGenBase.forestHills || biome == BiomeGenBase.plains) {
                    ItemUtils.ejectStackFromBlockTowardsFacing(world, x, y, z, new ItemStack(BTWItems.wheatSeeds), fromSide);
                }
                else if (biome == BiomeGenBase.jungle || biome == BiomeGenBase.jungleHills) {
                    if (rand.nextBoolean()) {
                        ItemUtils.ejectStackFromBlockTowardsFacing(world, x, y, z, new ItemStack(BTWItems.cocoaBeans), fromSide);
                    }
                    else {
                        ItemUtils.ejectStackFromBlockTowardsFacing(world, x, y, z, new ItemStack(RefamishedItems.berrySeeds,1,3), fromSide);
                    }
                }
                else if (biome == BiomeGenBase.taiga || biome == BiomeGenBase.taigaHills) {
                    ItemUtils.ejectStackFromBlockTowardsFacing(world, x, y, z, new ItemStack(RefamishedItems.berrySeeds,1,rand.nextInt(2)), fromSide);
                }
                else if (biome == BiomeGenBase.swampland) {
                    if (rand.nextBoolean()) {
                        ItemUtils.ejectStackFromBlockTowardsFacing(world, x, y, z, new ItemStack(BTWItems.wheatSeeds), fromSide);
                    }
                    else {
                        ItemUtils.ejectStackFromBlockTowardsFacing(world, x, y, z, new ItemStack(RefamishedItems.berrySeeds,1,2), fromSide);
                    }
                }
            }
        }
        cir.cancel();
        cir.setReturnValue(true);
    }
}
