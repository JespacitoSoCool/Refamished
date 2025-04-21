package net.fabricmc.refamished.mixin.mixin_b;

import btw.block.blocks.DiamondOreBlock;
import btw.block.blocks.HempCropBlock;
import net.fabricmc.refamished.items.tools.bladeIron;
import net.fabricmc.refamished.itemsbase.blade;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemShears;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HempCropBlock.class)
public class hempCropMixin {
    @Inject(method = "harvestBlock",at = @At("RETURN"), cancellable = true)
    private void canUseBlade(World world, EntityPlayer player, int i, int j, int k, int iMetadata, CallbackInfo ci) {
        HempCropBlock this_ = (HempCropBlock)(Object)this;
        if (!(world.isRemote || player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof bladeIron || !world.getDifficulty().doesHempRequireShears() || world.getBlockId(i, j - 1, k) != this_.blockID)) {
            this_.dropBlockAsItem(world, i, j - 1, k, world.getBlockMetadata(i, j - 1, k), 0);
            world.setBlockToAir(i, j - 1, k);
            ci.cancel();
        }
    }
}
