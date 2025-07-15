package net.fabricmc.refamished.mixin.mixin_b;

import btw.block.blocks.EmeraldOreBlock;
import btw.block.blocks.OvenBlock;
import net.fabricmc.refamished.misc.Recipes.FurnaceMetaRecipes;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OvenBlock.class)
public class ovenBlockMixin {
    @Inject(method = "isValidCookItem",at = @At("HEAD"), cancellable = true)
    public void getRequiredToolLevelForOre(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        boolean canCook = FurnaceMetaRecipes.getInstance().getMatchingRecipe(stack) != null;
        if (canCook) {
            cir.setReturnValue(true);
        }
    }
}
