package net.fabricmc.refamished.blocks.decorative.burnt;

import btw.block.BTWBlocks;
import btw.block.util.Flammability;
import btw.item.BTWItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.*;

import java.util.Random;

public class woodBurntSlab extends baseBurntSlab {
    final static String TextureIndex = "refamished:charring/";
    public woodBurntSlab(int blockID) {
        super(blockID, Material.ground);
        this.setTextureName(TextureIndex + "burnt_planks");
        this.setAxesEffectiveOn();
        this.setChiselsEffectiveOn();
        this.setHardness(0.7f);
        this.setResistance(2.0f);
        this.setBuoyant();
        this.setStepSound(soundWoodFootstep);
        this.setUnlocalizedName("burntWoodSlab");
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public int getCombinedBlockID(int var1) {
        return RefamishedBlocks.burntPlanks.blockID;
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return BTWItems.sawDust.itemID;
    }

    @Override
    public int quantityDropped( Random rand )
    {
        return 2;
    }

    @Override
    public boolean dropComponentItemsOnBadBreak(World world, int i, int j, int k, int iMetadata, float fChanceOfDrop) {
        this.dropItemsIndividually(world, i, j, k, BTWItems.sawDust.itemID, 2, 0, fChanceOfDrop);
        return true;
    }

    @Override
    public boolean isBreakableBarricade(World world, int i, int j, int k) {
        return world.getDifficulty().canZombiesBreakBlocks();
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);
        this.iconEmbers = new Icon[4];
        this.iconEmbers[0] = register.registerIcon(TextureIndex + "burnt_planks");
        this.iconEmbers[1] = register.registerIcon(TextureIndex + "burn");
        this.iconEmbers[2] = register.registerIcon(TextureIndex + "burn_heavy");
        this.iconEmbers[3] = register.registerIcon(TextureIndex + "smoldering_planks");
    }
}
