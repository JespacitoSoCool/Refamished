package net.fabricmc.refamished.mixin;

import net.fabricmc.refamished.skill.SkillCraftingHandler;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;

@Mixin(ShapedRecipes.class)
public class RecipeShapedMixin {
    @Unique
    EntityPlayer cachedPlayer;

    @Inject(method = "matches(Lnet/minecraft/src/InventoryCrafting;Lnet/minecraft/src/World;)Z", at = @At("RETURN"), cancellable = true)
    public void onCraftAttempt(InventoryCrafting par1InventoryCrafting, World par2World, CallbackInfoReturnable<Boolean> cir) {
        if (par1InventoryCrafting instanceof InventoryCrafting) {
            InventoryCrafting craftingMatrix = par1InventoryCrafting;

            EntityPlayer player = null;

            boolean returnValue = cir.getReturnValue();

            if (player == null || player.worldObj.isRemote) {
                return;
            }

            ItemStack resultItem = CraftingManager.getInstance().findMatchingRecipe(craftingMatrix, player.worldObj);

            if (resultItem != null && !SkillCraftingHandler.canPlayerCraft(player, resultItem)) {
                cir.setReturnValue(false);
                cir.cancel();
                //player.addChatMessage("You do not meet the skill requirements to craft this item.");
            }
            cir.setReturnValue(returnValue);
        }
    }
}
