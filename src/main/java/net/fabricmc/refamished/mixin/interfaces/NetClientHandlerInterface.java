package net.fabricmc.refamished.mixin.interfaces;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NetClientHandler.class)
public interface NetClientHandlerInterface {
    @Accessor("worldClient")
    WorldClient getClient();
    @Accessor("mc")
    Minecraft getMc();
}
