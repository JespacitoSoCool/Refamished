package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.OreChunkBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.IconRegister;
import net.minecraft.src.World;

import java.util.Random;

public class copper_ore_storage_ground extends OreChunkBlock {

    public copper_ore_storage_ground(int iBlockID) {
        super(iBlockID);
        this.hideFromEMI();
    }


    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return RefamishedItems.copperChunk.itemID;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public int idPicked(World world, int x, int y, int z) {
        return RefamishedItems.copperChunk.itemID;
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        this.blockIcon = register.registerIcon("refamished:copper_ore_storage");;
    }
}
