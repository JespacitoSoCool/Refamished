package net.fabricmc.refamished.blocks.crops;

import btw.block.BTWBlocks;
import btw.block.blocks.DailyGrowthCropsBlock;
import com.prupe.mcpatcher.cc.ColorizeBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

public class blackberryBush extends cranberryBush {
    public blackberryBush(int iBlockID) {
        super(iBlockID);
        this.setUnlocalizedName("blackberryBush");
        setTextureName("refamished:bush_stage_5");
    }
    @Override
    public int damageDropped(int meta) {
        return 3;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        int iTempIndex;
        this.iconArray = new Icon[8];
        this.secondRender = new Icon[2];
        super.registerIcons(register);
        this.secondRender[0] = register.registerIcon("refamished:variant/crops/blackberry_low");
        this.secondRender[1] = register.registerIcon("refamished:variant/crops/blackberry");
    }
}
