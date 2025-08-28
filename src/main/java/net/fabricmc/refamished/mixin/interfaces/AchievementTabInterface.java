package net.fabricmc.refamished.mixin.interfaces;

import btw.achievement.AchievementTab;
import net.minecraft.src.Achievement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AchievementTab.class)
public interface AchievementTabInterface {
    @Accessor("name")
    String getName();
}