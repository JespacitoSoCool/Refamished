package net.fabricmc.refamished.mixin.mixin_m;

import btw.block.blocks.HempCropBlock;
import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.items.tools.bladeIron;
import net.minecraft.src.EntityCow;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityCow.class)
public class CowMixin {
    @Inject(method = "dropFewItems",at = @At("HEAD"), cancellable = true)
    private void dropItems(boolean killedByPlayer, int lootingModifier, CallbackInfo ci) {
        EntityCow cow = (EntityCow)(Object)this;
        ci.cancel();
        if (!cow.isStarving()) {
            int numDrops = cow.rand.nextInt(3) + cow.rand.nextInt(1 + lootingModifier) + 2;
            if (cow.isFamished()) {
                numDrops /= 2;
            }
            for (int i = 0; i < numDrops; ++i) {
                cow.dropItem(RefamishedItems.cowhide.itemID, 1);
            }
            if (!cow.hasHeadCrabbedSquid()) {
                numDrops = cow.rand.nextInt(3) + 2 + cow.rand.nextInt(1 + lootingModifier);
                if (cow.isFamished()) {
                    numDrops /= 2;
                }
                cow.dropItem(RefamishedItems.rib_beef.itemID, 1);
                for (int iTempCount = 0; iTempCount < numDrops; ++iTempCount) {
                    if (cow.isBurning()) {
                        if (cow.worldObj.getDifficulty().shouldBurningMobsDropCookedMeat()) {
                            cow.dropItem(Item.beefCooked.itemID, 1);
                            continue;
                        }
                        cow.dropItem(BTWItems.burnedMeat.itemID, 1);
                        continue;
                    }
                    cow.dropItem(Item.beefRaw.itemID, 1);
                }
            }
        }
    }
}
