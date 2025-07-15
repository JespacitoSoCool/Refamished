package net.fabricmc.refamished.mixin.mixin_m;

import btw.block.BTWBlocks;
import net.fabricmc.refamished.RefamishedMod;
import net.minecraft.src.*;
import net.minecraft.src.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityGhast.class)
public class GhastMixin {
    @Inject(method = "canEntityBeSeen", at = @At("HEAD"), cancellable = true)
    private void ignoreNetherrackInSight(Entity target, CallbackInfoReturnable<Boolean> cir) {
        EntityGhast ghast = (EntityGhast)(Object)this;

        if (ghast.worldObj.getDifficulty() != null && ghast.worldObj.getDifficulty() == RefamishedMod.CRUEL) {
            Vec3 from = ghast.worldObj.getWorldVec3Pool().getVecFromPool(ghast.posX, ghast.posY + (double)ghast.height / 2.0, ghast.posZ);
            Vec3 to = ghast.worldObj.getWorldVec3Pool().getVecFromPool(target.posX, target.posY + (double)target.getEyeHeight(), target.posZ);

            MovingObjectPosition result = ghast.worldObj.rayTraceBlocks_do_do(from, to, false, true);

            if (result == null) {
                cir.setReturnValue(true);
            } else {
                int blockId = ghast.worldObj.getBlockId(result.blockX, result.blockY, result.blockZ);

                if (blockId == Block.netherrack.blockID || blockId == Block.oreNetherQuartz.blockID) {
                    cir.setReturnValue(true);
                } else {
                    cir.setReturnValue(false);
                }
            }
            cir.cancel();
        }
    }
}
