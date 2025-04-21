package net.fabricmc.refamished.mixin;

import net.minecraft.src.NetClientHandler;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NetClientHandler.class)
public interface NetClientHandlerAccessor {

}