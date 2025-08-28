package net.fabricmc.refamished.mixin.uselessshit;

import net.fabricmc.refamished.misc.AchievementsThing.AchievementExt;
import net.minecraft.src.Achievement;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Achievement.class)
public class AchievementMixin implements AchievementExt {
    @Shadow
    @Final
    @Mutable
    public Achievement[] parentAchievements;
    @Mutable @Shadow @Final public String name;
    @Shadow public boolean isHidden;
    @Mutable @Shadow @Final public int displayRow;
    @Mutable @Shadow @Final public int displayColumn;

    @Override
    public void nightmareMode$appendParent(Achievement achievementToAdd) {
        if (parentAchievements == null) {
            parentAchievements = new Achievement[] { achievementToAdd };
        } else {
            Achievement[] extended = new Achievement[parentAchievements.length + 1];
            System.arraycopy(parentAchievements, 0, extended, 0, parentAchievements.length);
            extended[parentAchievements.length] = achievementToAdd;
            parentAchievements = extended;
        }
    }

    @Override
    public void nightmareMode$setHidden(boolean value) {
        this.isHidden = value;
    }

    @Override
    public void nightmareMode$setDisplay(int row, int column) {
        this.displayRow = row;
        this.displayColumn = column;
    }
    @Override
    public Achievement[] nightmareMode$removeParent(Achievement[] original, Achievement toRemove) {
        if (original == null || original.length == 0) {
            return original; // nothing to remove
        }

        int count = 0;
        for (Achievement a : original) {
            if (a != toRemove) {
                count++;
            }
        }

        if (count == original.length) {
            // not found
            return original;
        }

        Achievement[] reduced = new Achievement[count];
        int idx = 0;
        for (Achievement a : original) {
            if (a != toRemove) {
                reduced[idx++] = a;
            }
        }
        return reduced;
    }

}