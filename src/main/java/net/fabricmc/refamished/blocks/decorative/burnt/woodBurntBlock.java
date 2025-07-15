package net.fabricmc.refamished.blocks.decorative.burnt;

import btw.block.blocks.FallingFullBlock;
import btw.block.util.Flammability;
import btw.client.render.util.RenderUtils;
import btw.item.BTWItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.Random;

public class woodBurntBlock extends baseBurntBlock {
    public woodBurntBlock(int blockID) {
        super(blockID, Material.wood);
        this.setTickRandomly(true);
        this.setTextureName(TextureIndex + "burnt_planks");
        this.setAxesEffectiveOn();
        this.setChiselsEffectiveOn();
        this.setHardness(0.7f);
        this.setResistance(2.0f);
        this.setBuoyant();
        this.setStepSound(soundWoodFootstep);
        this.setUnlocalizedName("burntWood");
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return BTWItems.sawDust.itemID;
    }

    @Override
    public int quantityDropped( Random rand )
    {
        return 3;
    }

    @Override
    public boolean dropComponentItemsOnBadBreak(World world, int i, int j, int k, int iMetadata, float fChanceOfDrop) {
        this.dropItemsIndividually(world, i, j, k, BTWItems.sawDust.itemID, 3, 0, fChanceOfDrop);
        return true;
    }

    @Override
    public boolean isBreakableBarricade(World world, int i, int j, int k) {
        return world.getDifficulty().canZombiesBreakBlocks();
    }

    final static String TextureIndex = "refamished:charring/";

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
