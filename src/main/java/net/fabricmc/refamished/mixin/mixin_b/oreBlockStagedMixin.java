package net.fabricmc.refamished.mixin.mixin_b;

import btw.block.blocks.LapisOreBlock;
import btw.block.blocks.OreBlockStaged;
import btw.block.blocks.RoughStoneBlock;
import net.fabricmc.refamished.mixin.interfaces.blockOreStagedInter;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OreBlockStaged.class)
public class oreBlockStagedMixin {
    @Inject(method = "convertBlock",at = @At("HEAD"), cancellable = true)
    public void getRequiredToolLevelForOre(ItemStack stack, World world, int i, int j, int k, int iFromSide, CallbackInfoReturnable<Boolean> cir) {
        OreBlockStaged this_ = (OreBlockStaged)(Object)this;
        blockOreStagedInter inter = (blockOreStagedInter)(Object)this;
        int iLevel;
        int iOldMetadata = world.getBlockMetadata(i, j, k);
        int iStrata = this_.getStrata(iOldMetadata);
        world.setBlockAndMetadataWithNotify(i, j, k, RoughStoneBlock.strataLevelBlockArray[iStrata].blockID, 4);
        if (!world.isRemote && (iLevel = inter.getCLT(stack, world, i, j, k)) > 0) {
            world.playAuxSFX(2269, i, j, k, 0);
            if (iLevel >= 3) {
                inter.ejectGood(stack, world, i, j, k, iOldMetadata, iFromSide);
            } else if (iLevel == 2) {
                inter.ejectStone(stack, world, i, j, k, iOldMetadata, iFromSide);
            }
        }
        cir.setReturnValue(true);
    }
    @Inject(method = "<init>",at = @At("TAIL"))
    public void getRequiredToolLevelForOre(int iBlockID, CallbackInfo ci) {
        OreBlockStaged this_ = (OreBlockStaged)(Object)this;
        this_.setChiselsEffectiveOn(false);
    }
}
