package net.fabricmc.refamished.mixin.mixin_m;

import btw.entity.attribute.BTWAttributes;
import net.fabricmc.refamished.itemsbase.craftedArmor;
import net.fabricmc.refamished.misc.DifficultyCruel;
import net.fabricmc.refamished.quality.ArmorQualityHelper;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Shadow
    private int fire;

    protected int getTotalWornStat(String stat)
    {
        Entity liver = (Entity) (Object) this;
        int iStat = 0;
        ItemStack[] var4 = liver.getLastActiveItems();
        if (var4 == null) {
            return 0;
        }
        for (ItemStack tempStack : var4) {
            if (tempStack != null) {
                if (tempStack.getItem() instanceof craftedArmor armorC) {
                    iStat += craftedArmor.getTraitLevel(tempStack, stat);
                }
            }
        }
        return iStat;
    }

    @Inject(method = "setFire", at = @At("HEAD"), cancellable = true)
    protected void setFire(int par1, CallbackInfo ci)
    {
        Entity this_ = (Entity)(Object)this;
        double temper = (1-(getTotalWornStat("temper")*0.05));
        double heattrap = (1+(Math.min(getTotalWornStat("heattrap")-getTotalWornStat("temper"),0)*0.05));
        //System.out.println(getTotalWornStat("temper"));
        //System.out.println();
        int var2 = (int)((float)par1 * 20f * temper * heattrap);
        if (this.fire < (var2 = EnchantmentProtection.getFireTimeForEntity(this_, var2))) {
            this.fire = var2;
        }
        ci.cancel();
    }
}
