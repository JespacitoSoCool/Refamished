package net.fabricmc.refamished.mixin.interfaces;

import net.minecraft.src.BlockFire;
import net.minecraft.src.FoodStats;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockFire.class)
public interface blockFireInterface {
    @Invoker("canNeighborBurn")
    boolean canNeighborBurn_(World par1World, int par2, int par3, int par4);
}
