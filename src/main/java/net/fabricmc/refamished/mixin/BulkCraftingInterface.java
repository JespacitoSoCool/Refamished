package net.fabricmc.refamished.mixin;

import btw.crafting.recipe.types.BulkRecipe;
import btw.item.tag.TagOrStack;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(BulkRecipe.class)
public interface BulkCraftingInterface {
    @Accessor("metadataExclusive")
    boolean metadataExcl();
    @Accessor("recipeOutputStacks")
    List<ItemStack> output();
    @Accessor("recipeInputStacks")
    List<TagOrStack> input();
}
