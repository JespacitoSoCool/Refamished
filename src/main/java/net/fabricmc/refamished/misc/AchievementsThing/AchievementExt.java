package net.fabricmc.refamished.misc.AchievementsThing;

import net.minecraft.src.*;

public interface AchievementExt {
    void nightmareMode$appendParent(Achievement achievementToAdd);
    void nightmareMode$setHidden(boolean value);
    void nightmareMode$setDisplay(int row, int column);
    Achievement[] nightmareMode$removeParent(Achievement[] original, Achievement toRemove);
}