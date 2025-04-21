package net.fabricmc.refamished.mixin.mixin_m;

import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Icon;
import net.minecraft.src.RenderItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Random;

@Mixin(RenderItem.class)
public interface RenderItemInterface {
    @Accessor("random")
    Random getRand();
    @Invoker("renderDroppedItem")
    void render(EntityItem par1EntityItem, Icon par2Icon, int par3, float par4, float par5, float par6, float par7);
}
