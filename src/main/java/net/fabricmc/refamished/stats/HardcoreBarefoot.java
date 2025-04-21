package net.fabricmc.refamished.stats;

import btw.item.BTWItems;
import net.minecraft.src.Item;
import java.util.HashMap;
import java.util.Map;

public class HardcoreBarefoot {
    public static final Map<Integer, Float> CUSTOM_SPEEDS = new HashMap<>();

    static {
        CUSTOM_SPEEDS.put(Item.bootsLeather.itemID, 1.1F);
        CUSTOM_SPEEDS.put(Item.bootsChain.itemID, 1.05F);
        CUSTOM_SPEEDS.put(BTWItems.woolBoots.itemID, 1.1F);
        CUSTOM_SPEEDS.put(BTWItems.gimpBoots.itemID, 1.1F);
        CUSTOM_SPEEDS.put(BTWItems.paddedBoots.itemID, 1.1F);
        CUSTOM_SPEEDS.put(BTWItems.tannedLeatherBoots.itemID, 1.1F);
    }

    public static void addBootSpeed(int itemId, float speedMultiplier) {
        CUSTOM_SPEEDS.put(itemId, speedMultiplier);
    }
}
