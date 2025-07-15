package net.fabricmc.refamished.blocks.decorative.burnt;

import btw.block.BTWBlocks;
import btw.block.blocks.FallingFullBlock;
import btw.client.render.util.RenderUtils;
import btw.item.BTWItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

import java.util.Random;

public class charredDirt extends baseCharredBlock {
    final static String TextureIndex = "refamished:charring/";
    public charredDirt(int blockID) {
        super(blockID, Material.ground);
        this.setHardness(0.7f);
        this.setShovelsEffectiveOn();
        this.setHoesEffectiveOn();
        this.setStepSound(BTWBlocks.dirtStepSound);
        this.setUnlocalizedName("charred_dirt");
        this.setTextureName(TextureIndex+"charred_earth");
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setTickRandomly(true);
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return BTWItems.dirtPile.itemID;
    }

    @Override
    public int quantityDropped( Random rand )
    {
        return 3;
    }

    @Override
    public boolean dropComponentItemsOnBadBreak(World world, int i, int j, int k, int iMetadata, float fChanceOfDrop) {
        this.dropItemsIndividually(world, i, j, k, BTWItems.dirtPile.itemID, 2, 0, fChanceOfDrop);
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
        this.iconEmbers = new Icon[6];
        this.iconEmbers[0] = register.registerIcon(TextureIndex + "earth_healing_3");
        this.iconEmbers[1] = register.registerIcon(TextureIndex + "earth_healing_2");
        this.iconEmbers[2] = register.registerIcon(TextureIndex + "earth_healing");
        this.iconEmbers[3] = register.registerIcon(TextureIndex + "charred_earth");
        this.iconEmbers[4] = register.registerIcon(TextureIndex + "burn");
        this.iconEmbers[5] = register.registerIcon(TextureIndex + "burn_heavy");
    }

    public int getHealingBlock() {
        return BTWBlocks.looseDirt.blockID;
    }
}
