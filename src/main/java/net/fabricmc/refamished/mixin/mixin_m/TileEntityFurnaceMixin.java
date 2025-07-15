package net.fabricmc.refamished.mixin.mixin_m;

import btw.block.tileentity.CrucibleTileEntity;
import net.fabricmc.refamished.misc.Recipes.FurnaceMetaRecipes;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TileEntityFurnace.class)
public abstract class TileEntityFurnaceMixin {
    @Shadow protected abstract boolean canSmelt();

    @Inject(method = "getCookTimeForCurrentItem",at = @At("HEAD"), cancellable = true)
    private void getCok(CallbackInfoReturnable<Integer> cir) {
        TileEntityFurnace ts = (TileEntityFurnace) (Object) this;
        TileEntityFurnaceInterface in = (TileEntityFurnaceInterface) this;
        int iCookTimeShift = 0;
        if (in.getItemStacks()[0] != null) {
            iCookTimeShift = FurnaceMetaRecipes.getInstance().getCookTimeBinaryShift(in.getItemStacks()[0]);
        }
        cir.setReturnValue(400 << iCookTimeShift);
    }

    @Unique
    private boolean smeltOrNah() {
        TileEntityFurnace ts = (TileEntityFurnace) (Object) this;
        TileEntityFurnaceInterface in = (TileEntityFurnaceInterface) ts;
        ItemStack[] itemStacks = in.getItemStacks();
        if (itemStacks[0] == null) {
            //System.out.println("Nah - No stack");
            return false;
        }
        if (FurnaceMetaRecipes.getInstance().getMatchingRecipe(itemStacks[0]) == null) {
            //System.out.println("Nah - No Recipe");
            return false;
        }
        ItemStack var1 = FurnaceMetaRecipes.getInstance().getMatchingRecipe(itemStacks[0]).output;
        if (var1 == null) {
            //System.out.println("Nah - No output");
            return false;
        }
        if (itemStacks[2] == null) {
            return true;
        }
        if (!itemStacks[2].isItemEqual(var1)) {
            //System.out.println("Nah - Not equal");
            return false;
        }
        int iOutputStackSizeIfCooked = itemStacks[2].stackSize + var1.stackSize;
        if (iOutputStackSizeIfCooked <= ts.getInventoryStackLimit() && iOutputStackSizeIfCooked <= itemStacks[2].getMaxStackSize()) {
            return true;
        }
        return iOutputStackSizeIfCooked <= var1.getMaxStackSize();
    }
    @Inject(method = "canSmelt",at = @At("HEAD"), cancellable = true)
    private void smeltOrNahInject(CallbackInfoReturnable<Boolean> cir) {
        boolean doSmelt = smeltOrNah();
        if (doSmelt) {
            cir.setReturnValue(doSmelt);
        }
    }

    @Inject(method = "smeltItem",at = @At("HEAD"), cancellable = true)
    private void smelt(CallbackInfo ci) {
        TileEntityFurnace ts = (TileEntityFurnace) (Object) this;
        TileEntityFurnaceInterface in = (TileEntityFurnaceInterface) this;
        ItemStack[] itemStacks = in.getItemStacks();
        FurnaceMetaRecipes.RecipeEntry entry = FurnaceMetaRecipes.getInstance().getMatchingRecipe(itemStacks[0]);
        if (entry != null && smeltOrNah()) {
            ItemStack var1 = entry.output;
            if (itemStacks[2] == null) {
                itemStacks[2] = var1.copy();
            } else if (itemStacks[2] == var1) {
                itemStacks[2].stackSize += var1.stackSize;
            }
            --itemStacks[0].stackSize;
            if (itemStacks[0].stackSize <= 0) {
                itemStacks[0] = null;
            }
        }
    }
}
