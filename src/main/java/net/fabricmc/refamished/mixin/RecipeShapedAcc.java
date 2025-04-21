package net.fabricmc.refamished.mixin;

import btw.item.items.ToolItem;
import net.minecraft.src.Container;
import net.minecraft.src.ContainerWorkbench;
import net.minecraft.src.InventoryCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InventoryCrafting.class)
public interface RecipeShapedAcc {

}