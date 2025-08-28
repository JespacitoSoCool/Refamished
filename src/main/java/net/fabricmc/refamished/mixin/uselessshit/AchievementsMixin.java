package net.fabricmc.refamished.mixin.uselessshit;

import btw.achievement.AchievementTabList;
import btw.achievement.BTWAchievements;
import net.fabricmc.refamished.misc.AchievementsThing.RefAchievements;
import net.fabricmc.refamished.mixin.interfaces.AchievementInterface;
import net.minecraft.src.Achievement;
import net.minecraft.src.AchievementList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(BTWAchievements.class)
public class AchievementsMixin {
	@Inject(at = @At("TAIL"), method = "initialize", remap = false)
	private static void before(CallbackInfo info) {
		RefAchievements.initialize();
		for (Set<Achievement<?>> set : AchievementList.achievementsByEventType.values()) {
			for (Achievement<?> achievement : set) {
				//System.out.println(achievement.tab.getName());
				if (achievement.tab == BTWAchievements.TAB_GETTING_STARTED) {
					AchievementList.achievementsByEventType.remove(achievement);
				}
			}
		}
	}

	@Inject(at = @At("TAIL"), method = "<clinit>", remap = false)
	private static void AHHHHHH(CallbackInfo info) {
		for (Set<Achievement<?>> set : AchievementList.achievementsByEventType.values()) {
			for (Achievement<?> achievement : set) {
				//System.out.println(achievement.tab.getName());
				if (achievement.tab == BTWAchievements.TAB_GETTING_STARTED) {
					kill(achievement);
				}
			}
		}
		//AchievementTabList.tabList.remove(BTWAchievements.TAB_GETTING_STARTED);
	}

	@Unique
	private static void kill(Achievement acObj){
		acObj.tab.achievementList.remove(acObj);
	}

	@Unique
	private static void setAchievementTabName(Achievement acObj){
		acObj.tab.achievementList.remove(acObj);
	}
}
