package net.fabricmc.refamished.mixin;

import btw.crafting.recipe.types.BulkRecipe;
import btw.item.tag.TagInstance;
import btw.item.tag.TagOrStack;
import net.minecraft.src.*;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BulkRecipe.class)
public abstract class BulkCraftingMixin {
    @Final
    @Shadow
    private List<ItemStack> recipeOutputStacks;
    @Final
    @Shadow
    private List<TagOrStack> recipeInputStacks;
    @Final
    @Shadow
    private boolean metadataExclusive;

    private boolean doStacksMatch(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem().itemID == stack2.getItem().itemID && stack1.stackSize == stack2.stackSize && stack1.getItemDamage() == stack2.getItemDamage();
    }

    @Inject(method = "matches", at = @At("HEAD"), cancellable = true, remap = false)
    private void can(BulkRecipe recipe, CallbackInfoReturnable<Boolean> cir) {
        BulkRecipe self = (BulkRecipe)(Object)this;
        BulkCraftingInterface recipeInt = (BulkCraftingInterface) recipe;

        List<ItemStack> recipeOutputStacks = recipeInt.output();
        List<TagOrStack> recipeInputStacks = recipeInt.input();
        boolean metadataExclusive = recipeInt.metadataExcl();

        // quick size/flag checks
        if (this.metadataExclusive != metadataExclusive
                || this.recipeInputStacks.size() != recipeInputStacks.size()
                || this.recipeOutputStacks.size() != recipeOutputStacks.size()) {
            cir.setReturnValue(false);
            return;
        }

        for (int i = 0; i < this.recipeInputStacks.size(); i++) {
            TagOrStack tagOrStack1 = this.recipeInputStacks.get(i);
            TagOrStack tagOrStack2 = recipeInputStacks.get(i);

            if (tagOrStack1 instanceof ItemStack && tagOrStack2 instanceof ItemStack) {
                if (!this.doStacksMatch((ItemStack) tagOrStack1, (ItemStack) tagOrStack2)) {
                    cir.setReturnValue(false);
                    return;
                }
            }
            else if (tagOrStack1 instanceof TagInstance && tagOrStack2 instanceof TagInstance) {
                if (!((TagInstance) tagOrStack1).equals((TagInstance) tagOrStack2)) {
                    cir.setReturnValue(false);
                    return;
                }
            }
            else {
                cir.setReturnValue(false);
                return;
            }
        }

        for (int i = 0; i < this.recipeOutputStacks.size(); i++) {
            if (!this.doStacksMatch(this.recipeOutputStacks.get(i), recipeOutputStacks.get(i))) {
                cir.setReturnValue(false);
                return;
            }
        }

        cir.setReturnValue(true);
    }

}