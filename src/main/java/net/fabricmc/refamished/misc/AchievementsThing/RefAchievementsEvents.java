package net.fabricmc.refamished.misc.AchievementsThing;

import btw.achievement.event.AchievementEventDispatcher;
import btw.achievement.event.BTWAchievementEvents;

public class RefAchievementsEvents extends BTWAchievementEvents {
    public static class WelcomeEvent extends AchievementEventDispatcher.AchievementEvent<None> {
        public WelcomeEvent() {
            super();
        }
    }
    public static class Troweling extends AchievementEventDispatcher.AchievementEvent<None> {
        public Troweling() {
            super();
        }
    }
    public static class StoneAnvil extends AchievementEventDispatcher.AchievementEvent<None> {
        public StoneAnvil() {
            super();
        }
    }
    public record SkillEventData(String skill, int Level) {
    }
    public static class Skill extends AchievementEventDispatcher.AchievementEvent<SkillEventData> {
    }


}