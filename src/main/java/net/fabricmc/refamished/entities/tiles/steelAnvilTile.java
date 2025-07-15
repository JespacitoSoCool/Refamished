package net.fabricmc.refamished.entities.tiles;

import net.minecraft.src.EntityPlayer;

public class steelAnvilTile extends stoneAnvilTile {

    @Override
    public int sizeInv() {
        return 10;
    }

    @Override
    public String getInvName() {
        return "container.steelAnvil";
    }

    @Override
    public int getBonusLevel() {
        return 3;
    }

    public void applyMinigameResult(boolean success, EntityPlayer player, stoneAnvilContainer gui, copperAnvilContainer contain, steelAnvilTile anvil) {
        super.applyMinigameResult(success,player,gui,(stoneAnvilContainer) contain,(stoneAnvilTile)anvil);
    }
}