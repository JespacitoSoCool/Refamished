package net.fabricmc.refamished.mixin.mixin_i;

import btw.block.BTWBlocks;
import btw.entity.RottenArrowEntity;
import btw.item.BTWItems;
import btw.item.items.ShovelItemStone;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.entities.EntityFlintArrow;
import net.fabricmc.refamished.entities.EntityFrostArrow;
import net.fabricmc.refamished.entities.EntityGoldArrow;
import net.fabricmc.refamished.entities.EntityIncendiaryArrow;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemBow.class)
public class BowItemMixin {
    @Inject(method = "canItemBeFiredAsArrow", at = @At("RETURN"), cancellable = true)
    public void CANBEFIRED(int iItemID, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue())
        {
            cir.setReturnValue(iItemID == RefamishedItems.arrow_incendiary.itemID || iItemID == RefamishedItems.arrow_flint.itemID
                    || iItemID == RefamishedItems.arrow_gold.itemID || iItemID == RefamishedItems.arrow_frozen.itemID);
            cir.cancel();
        }
    }
    @Inject(method = "createArrowEntityForItem", at = @At("RETURN"), cancellable = true)
    public void CREATETHEFREAKINGENTITY(World world, EntityPlayer player, int iItemID, float fPullStrength, CallbackInfoReturnable<EntityArrow> cir) {
        ItemBow THEREALBOW = (ItemBow)(Object)this;
        EntityArrow entityArrow = null;
        if (iItemID == RefamishedItems.arrow_incendiary.itemID) {
            entityArrow = new EntityIncendiaryArrow(world, player, fPullStrength * 0.55f * THEREALBOW.getPullStrengthToArrowVelocityMultiplier());
        } else if (iItemID == RefamishedItems.arrow_flint.itemID) {
            entityArrow = new EntityFlintArrow(world, player, fPullStrength * 0.9f * THEREALBOW.getPullStrengthToArrowVelocityMultiplier());
        } else if (iItemID == RefamishedItems.arrow_gold.itemID) {
            entityArrow = new EntityGoldArrow(world, player, fPullStrength * 0.7f * THEREALBOW.getPullStrengthToArrowVelocityMultiplier());
        }
        else if (iItemID == RefamishedItems.arrow_frozen.itemID) {
            entityArrow = new EntityFrostArrow(world, player, fPullStrength * 0.65f * THEREALBOW.getPullStrengthToArrowVelocityMultiplier());
        }
        if (cir.getReturnValue() == null)
        {
            cir.setReturnValue(entityArrow);
            cir.cancel();
        }
    }
}
