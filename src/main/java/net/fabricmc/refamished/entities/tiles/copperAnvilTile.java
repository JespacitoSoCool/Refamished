package net.fabricmc.refamished.entities.tiles;

import net.fabricmc.refamished.itemsbase.hammer;
import net.fabricmc.refamished.misc.ReMaterials;
import net.fabricmc.refamished.misc.Recipes.SmithingRecipes;
import net.fabricmc.refamished.misc.RefamishedSoundManager;
import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.List;

public class copperAnvilTile extends stoneAnvilTile {

    @Override
    public int sizeInv() {
        return 8;
    }

    @Override
    public String getInvName() {
        return "container.copperAnvil";
    }

    @Override
    public int getBonusLevel() {
        return 2;
    }

    public void applyMinigameResult(boolean success, EntityPlayer player, stoneAnvilContainer gui, copperAnvilContainer contain, copperAnvilTile anvil) {
        super.applyMinigameResult(success,player,gui,(stoneAnvilContainer) contain,(stoneAnvilTile)anvil);
    }
}