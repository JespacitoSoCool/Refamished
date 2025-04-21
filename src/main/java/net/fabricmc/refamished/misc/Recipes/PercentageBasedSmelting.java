package net.fabricmc.refamished.misc.Recipes;

import btw.crafting.manager.BulkCraftingManager;
import btw.crafting.manager.CrucibleStokedCraftingManager;

public class PercentageBasedSmelting extends BulkCraftingManager {
    private static final PercentageBasedSmelting instance = new PercentageBasedSmelting();

    public static final PercentageBasedSmelting getInstance() {
        return instance;
    }

    private PercentageBasedSmelting() {
    }
}
