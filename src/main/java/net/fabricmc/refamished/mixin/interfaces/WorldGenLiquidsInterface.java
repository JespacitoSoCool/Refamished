package net.fabricmc.refamished.mixin.interfaces;

import net.minecraft.src.Minecraft;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.WorldClient;
import net.minecraft.src.WorldGenLiquids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldGenLiquids.class)
public interface WorldGenLiquidsInterface {
    @Accessor("liquidBlockId")
    int getLiquid();
}
