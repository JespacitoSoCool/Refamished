package net.fabricmc.refamished.mixin.mixin_b;

import btw.block.blocks.BucketBlock;
import btw.block.blocks.BucketBlockWater;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.misc.RefamishedConfig;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockChest.class)
public class blockChestMixin {

    @Unique
    private Boolean keepInv;

    @Inject(method = "breakBlock", at = @At("TAIL"), cancellable = true)
    @Environment(EnvType.CLIENT)
    private void injectRegisterIcons(World par1World, int par2, int par3, int par4, int par5, int par6, CallbackInfo ci) {
        if (keepInv) {
            ci.cancel();
        }
    }

    public void burn(World world, int x, int y,int z) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        int oldMetadata = world.getBlockMetadata(x,y,z);
        keepInv = true;
        world.setBlock(x, y, z, RefamishedBlocks.burntChest.blockID);
        keepInv = false;
        world.setBlockMetadata(x, y, z,oldMetadata);
        if (tileEntity != null) {
            tileEntity.validate();
            world.setBlockTileEntity(x, y, z, tileEntity);
        }
    }
}
