package net.fabricmc.refamished.mixin;

import btw.item.items.ProgressiveCraftingItem;
import net.fabricmc.refamished.interfaces.ProgressiveCraftingInterface;
import net.fabricmc.refamished.skill.PlayerSkillData;
import net.fabricmc.refamished.skill.SkillData;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProgressiveCraftingItem.class)
public class ProgressiveCraftingItemMixin implements ProgressiveCraftingInterface {
    @Unique
    float iProgression = 0;

    @Inject(method = "updateUsingItem", at = @At("TAIL"))
    public void update(ItemStack stack, World world, EntityPlayer player, CallbackInfo ci) {
        int iUseCount = player.getItemInUseCount();
        ProgressiveCraftingItem item = (ProgressiveCraftingItem)stack.getItem();
        ProgressiveCraftingInterface inter_item = (ProgressiveCraftingInterface) item;
        //System.out.println("Progression : "+iProgression);
        if (item.getMaxItemUseDuration(stack) - iUseCount > item.getItemUseWarmupDuration()) {
            if (!world.isRemote && iUseCount % 50 == 0) {
                SkillManager.addExperience(player,inter_item.skillName(), 1);
            }
            if (!world.isRemote && iUseCount % 4 == 0) {
                int iDamage = stack.getItemDamage();
                PlayerSkillData data = SkillManager.getSkillData(player);
                int skillLevel = data.getSkillProgress(skillName()).getLevel();
                int levelCap = SkillData.getLevelCap(skillName());

                if (skillLevel == 0)
                {

                }
                else
                {
                    float getExtraProgression = (((float)skillLevel/(float)levelCap)*1.25F);
                    //SetProgression(stack,GetProgression(stack)+getExtraProgression);
                    iProgression = iProgression+getExtraProgression;
                    //int getDamaging = (int) Math.floor(GetProgression(stack));
                    int getDamaging = (int) Math.floor(iProgression);
                    stack.setItemDamage(iDamage-getDamaging);
                    //iDamage -= getDamaging;
                    iProgression = iProgression-getDamaging;
                    System.out.println("Damage : "+getDamaging);
                    //SetProgression(stack,GetProgression(stack)-getDamaging);
                }

            }
        }
    }
}
