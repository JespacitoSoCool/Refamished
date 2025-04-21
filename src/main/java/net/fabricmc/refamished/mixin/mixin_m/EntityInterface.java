package net.fabricmc.refamished.mixin.mixin_m;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.xml.crypto.Data;
import java.util.Random;

@Mixin(Entity.class)
public interface EntityInterface {
    @Accessor("dataWatcher")
    DataWatcher getWatcher();
}
