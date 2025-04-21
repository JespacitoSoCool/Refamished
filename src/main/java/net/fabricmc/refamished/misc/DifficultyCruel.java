package net.fabricmc.refamished.misc;

import btw.world.util.difficulty.Difficulty;

public class DifficultyCruel extends Difficulty {
    public DifficultyCruel(String name) {
        super(name);
    }
    @Override
    public boolean shouldGrassBeLoosenedWhenDug() {
        return true;
    }

    @Override
    public boolean canZombiesBreakBlocks() {
        return true;
    }

    @Override
    public float getZombieFollowDistanceMultiplier() {
        return 3f;
    }

    @Override
    public boolean canCreepersBreachWalls() {
        return true;
    }

    @Override
    public float getCreeperFollowDistanceMultiplier() {
        return 2.0f;
    }

    @Override
    public boolean canEndermenMovePlayer() {
        return true;
    }

    @Override
    public boolean shouldSkeletonsSeekSpidersToMount() {
        return true;
    }

    @Override
    public boolean canWitherSkeletonsSpawnUnderground() {
        return true;
    }

    @Override
    public float getAbandonmentRangeMultiplier() {
        return 2f;
    }

    @Override
    public boolean hasAbandonedStructures() {
        return true;
    }

    @Override
    public boolean canSwitchDifficulty() {
        return false;
    }
}
