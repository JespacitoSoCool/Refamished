package net.fabricmc.refamished.blocks.decorative;

import btw.block.BTWBlocks;
import btw.block.blocks.OreBlockStaged;
import btw.item.BTWItems;
import btw.item.items.HoeItem;
import btw.item.util.ItemUtils;
import btw.world.util.difficulty.Difficulty;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.itemsbase.hammer;
import net.minecraft.src.*;

import java.util.List;
import java.util.Random;

public class DenseStone extends OreBlockStaged {
    public DenseStone(int par1) {
        super(par1);
        setUnlocalizedName("dense_stone");
        setHardness(2F);
        setShovelsEffectiveOn();
        this.setStepSound(BTWBlocks.stoneStepSound);
        setTextureName("refamished:dense_stone");
    }

    @Override
    public boolean canConvertBlock(ItemStack stack, World world, int x, int y, int z) {
        return stack != null && stack.getItem() instanceof hammer;
    }

    @Override
    public boolean convertBlock(ItemStack stack, World world, int x, int y, int z, int fromSide) {
        world.setBlockWithNotify(x, y, z, RefamishedBlocks.stoneAnvil.blockID);
        if (!world.isRemote) {
            world.playSoundEffect(x,y,z, this.stepSound.getBreakSound(), (this.stepSound.getStepVolume() + 1.0f) / 2.0f, this.stepSound.getStepPitch() * 0.8f);
        }
        return true;
    }

    @Override
    public int idDropped( int iMetaData, Random rand, int iFortuneModifier )
    {
        return BTWItems.stone.itemID;
    }

    @Override
    public int quantityDropped( Random rand )
    {
        return 2 + rand.nextInt( 2 );
    }

    @Override
    public int idDroppedOnConversion(Difficulty difficulty, int iMetadata) {
        return BTWItems.stone.itemID;
    }

    @Override
    public int quantityDroppedOnConversion( Random rand )
    {
        return 2 + rand.nextInt( 2 );
    }

    @Environment(value= EnvType.CLIENT)
    protected Icon m_IconNaturalClay;

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons( IconRegister register )
    {
        super.registerIcons( register );

        m_IconNaturalClay = register.registerIcon( "refamished:dense_stone" );
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public Icon getBlockTexture( IBlockAccess blockAccess, int i, int j, int k, int iSide )
    {
        return m_IconNaturalClay;
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void getSubBlocks(int blockID, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(blockID, 1, 0));
    }
}
