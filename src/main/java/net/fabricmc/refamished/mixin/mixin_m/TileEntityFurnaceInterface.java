package net.fabricmc.refamished.mixin.mixin_m;

import btw.entity.mob.behavior.SkeletonArrowAttackBehavior;
import net.minecraft.src.EntityAIAttackOnCollide;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TileEntityFurnace.class)
public interface TileEntityFurnaceInterface {
    @Accessor("furnaceItemStacks")
    ItemStack[] getItemStacks();
}
