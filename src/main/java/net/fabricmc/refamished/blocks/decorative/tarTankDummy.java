package net.fabricmc.refamished.blocks.decorative;

import btw.block.BTWBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.entities.tiles.tarTankTile;
import net.minecraft.src.*;

public class tarTankDummy extends Block {

    public tarTankDummy(int i) {
        super(i, Material.rock);
        setTextureName("refamished:tar");
        setUnlocalizedName("tar_tank");
        this.setPicksEffectiveOn();
        this.setChiselsEffectiveOn();
        this.setStepSound(BTWBlocks.clayBrickStepSound);
        this.setHardness(0.7f);
        this.setResistance(3.0f);
    }
}
