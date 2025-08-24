package net.fabricmc.refamished.mixin.mixin_m;

import btw.entity.attribute.BTWAttributes;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.itemsbase.craftedArmor;
import net.fabricmc.refamished.misc.DifficultyCruel;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.fabricmc.refamished.quality.ArmorQualityHelper;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(EntityLivingBase.class)
public class LivingBaseMixin {
    @Inject(method = "getSpeedModifier",at = @At("TAIL"), cancellable = true)
    private void modify(CallbackInfoReturnable<Float> cir) {
        EntityLivingBase liver = (EntityLivingBase)(Object)this;
        if (liver.worldObj.getDifficulty() instanceof DifficultyCruel)
        {
            if ((Object) this instanceof EntityPigZombie) {
                EntityPigZombie zombie = (EntityPigZombie) (Object) this;
                float maxHealth = zombie.getMaxHealth();
                float currentHealth = zombie.getHealth();

                float healthRatio = currentHealth / maxHealth;
                float speedMultiplier = 1.0f + (1.0f - healthRatio) * 0.4f;
                System.out.println(speedMultiplier);
                cir.setReturnValue(cir.getReturnValue() * speedMultiplier);
            }
        }
    }
    @Inject(method = "getTotalArmorValue", at = @At("RETURN"), cancellable = true)
    protected void GetWornArmorValue(CallbackInfoReturnable<Integer> cir)
    {
        float iArmor = 0;
        if ((Object) this instanceof EntityLivingBase) {
            EntityLivingBase liver = (EntityLivingBase) (Object) this;

            for ( int iSlot = 1; iSlot < 5; iSlot++ )
            {
                ItemStack tempStack = liver.getCurrentItemOrArmor(iSlot);

                if ( tempStack != null )
                {
                    float iArmorWeight = ArmorQualityHelper.getArmorBonus(ArmorQualityHelper.getArmorQuality(tempStack));
                    iArmor += iArmorWeight;
                    //System.out.println("Armor Weight: " + iArmorWeight);
                    if (tempStack.getItem() instanceof craftedArmor armorC) {
                        iArmor += craftedArmor.getTrait(tempStack,"protection",armorC.armorType);
                    }
                }
            }
            if (iArmor > 25) {
                iArmor = 25;
            }
            if (iArmor <= 0) {
                iArmor = 0;
            }
            cir.setReturnValue((int) (cir.getReturnValue()+iArmor));
        }
    }

    protected boolean getIsWeighted()
    {
        EntityLivingBase liver = (EntityLivingBase) (Object) this;
        LivingBaseInterface FuckNamingNowINameEverythingLikeIWant = (LivingBaseInterface) (Object) this;
        float iArmor = (float) liver.getEntityAttribute(BTWAttributes.armorWeight).getAttributeValue();
        for ( int iSlot = 1; iSlot < 5; iSlot++ )
        {
            ItemStack tempStack = liver.getCurrentItemOrArmor(iSlot);

            if ( tempStack != null )
            {
                float iArmorWeight = ArmorQualityHelper.getWeight(ArmorQualityHelper.getArmorQuality(tempStack));
                iArmor += iArmorWeight;
                //System.out.println("Armor Weight: " + iArmorWeight);
                if (tempStack.getItem() instanceof craftedArmor armorC) {
                    iArmor += craftedArmor.getTrait(tempStack,"weight",armorC.armorType);
                }
            }
        }
        if (iArmor > 10) {
           return true;
        }
        return FuckNamingNowINameEverythingLikeIWant.isWeightedByHeadCrabButCooler();
    }

    @Inject(method = "isWeighted", at = @At("HEAD"), cancellable = true)
    protected void IsWeighted(CallbackInfoReturnable<Boolean> cir)
    {
        cir.setReturnValue(getIsWeighted());
    }

    @Inject(method = "canSwim", at = @At("HEAD"), cancellable = true)
    protected void canFuckingSwim(CallbackInfoReturnable<Boolean> cir)
    {
        cir.setReturnValue(!getIsWeighted());
    }
}
