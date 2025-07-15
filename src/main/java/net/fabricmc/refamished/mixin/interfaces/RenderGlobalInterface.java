package net.fabricmc.refamished.mixin.interfaces;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderGlobal.class)
public interface RenderGlobalInterface {
    @Accessor("mc")
    Minecraft getMc();
    @Accessor("theWorld")
    WorldClient getWorld();
}
