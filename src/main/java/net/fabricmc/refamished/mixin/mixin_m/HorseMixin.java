package net.fabricmc.refamished.mixin.mixin_m;

import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.EntityCow;
import net.minecraft.src.EntityHorse;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityHorse.class)
public class HorseMixin {
    @Inject(method = "dropFewItems",at = @At("HEAD"), cancellable = true)
    private void dropItems(boolean killedByPlayer, int lootingModifier, CallbackInfo ci) {
        EntityHorse horse = (EntityHorse)(Object)this;
        ci.cancel();
        if (!horse.isStarving()) {
            int numDrops = horse.rand.nextInt(3) + horse.rand.nextInt(1 + lootingModifier) + 3;
            if (horse.isFamished()) {
                numDrops /= 2;
            }
            for (int i = 0; i < numDrops; ++i) {
                int type_ = horse.getHorseType();
                int variant = horse.getHorseVariant() & 0xFF;
                if (type_ == 0) {
                    ItemStack hideStack = new ItemStack(RefamishedItems.horsehide, 1, variant);
                    horse.entityDropItem(hideStack, 0.0F);
                }
                else if (type_ == 1)
                {
                    ItemStack hideStack = new ItemStack(RefamishedItems.horsehide, 1, 6);
                    horse.entityDropItem(hideStack, 0.0F);
                }
                else if (type_ == 2)
                {
                    ItemStack hideStack = new ItemStack(RefamishedItems.horsehide, 1, 8);
                    horse.entityDropItem(hideStack, 0.0F);
                }
            }
            if (!horse.hasHeadCrabbedSquid()) {
                numDrops = horse.rand.nextInt(3) + 2 + horse.rand.nextInt(1 + lootingModifier);
                if (horse.isFamished()) {
                    numDrops /= 2;
                }
                for (int iTempCount = 0; iTempCount < numDrops; ++iTempCount) {
                    if (horse.isBurning()) {
                        if (horse.worldObj.getDifficulty().shouldBurningMobsDropCookedMeat()) {
                            horse.dropItem(BTWItems.cookedCheval.itemID, 1);
                            continue;
                        }
                        horse.dropItem(BTWItems.burnedMeat.itemID, 1);
                        continue;
                    }
                    horse.dropItem(BTWItems.rawCheval.itemID, 1);
                }
            }
        }
    }
}
