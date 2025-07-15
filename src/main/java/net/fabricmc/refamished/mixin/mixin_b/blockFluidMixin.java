package net.fabricmc.refamished.mixin.mixin_b;

import btw.block.BTWBlocks;
import btw.block.blocks.SlabBlock;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.blocks.decorative.burnt.baseBurntBlock;
import net.fabricmc.refamished.blocks.decorative.burnt.baseBurntSlab;
import net.fabricmc.refamished.mixin.interfaces.blockFireInterface;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(BlockFluid.class)
public class blockFluidMixin {
    @Inject(method = "randomDisplayTick",at = @At("HEAD"))
    private void wh(World par1World, int x, int y, int z, Random par5Random, CallbackInfo ci) {
        BlockFluid ts = (BlockFluid)(Object)this;
        if (ts.blockMaterial == Material.lava && par1World.getBlockMaterial(x, y + 1, z) == Material.air && !par1World.isBlockOpaqueCube(x, y + 1, z)) {
                if (par5Random.nextInt(3) == 0) {
                    double xPos = (double)x + 0.5 - 0.6 + par5Random.nextDouble() * 1.2;
                    double yPos = (double)y + 0.5 + 0.25 + par5Random.nextDouble() * 0.25;
                    double zPos = (double)z + 0.5 - 0.6 + par5Random.nextDouble() * 1.2;
                    par1World.spawnParticle("firedot", xPos, yPos, zPos, 0.0, 0.0, 0.0);
                }
            }
    }
}
