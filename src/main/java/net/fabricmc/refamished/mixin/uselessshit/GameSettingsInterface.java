package net.fabricmc.refamished.mixin.uselessshit;

import net.minecraft.src.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Random;

@Mixin(GameSettings.class)
public interface GameSettingsInterface {
    @Accessor("fovSetting")
    float getFoodLevel();
}
