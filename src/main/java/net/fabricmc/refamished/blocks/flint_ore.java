package net.fabricmc.refamished.blocks;

import btw.block.BTWBlocks;
import btw.item.BTWItems;
import net.minecraft.src.*;

import java.util.Random;

public class flint_ore extends Block {


    public flint_ore(int par1) {
        super(par1,Material.ground);
        setUnlocalizedName("flint_ore");
        setHardness(1F);
        setShovelsEffectiveOn();
        this.setStepSound(BTWBlocks.grassStepSound);
        setTextureName("refamished:flint_ore");
    }

    @Override
    public int idDropped( int iMetaData, Random rand, int iFortuneModifier )
    {
        return Item.flint.itemID;
    }

    @Override
    public int quantityDropped( Random rand )
    {
        return 1 + rand.nextInt( 1 );
    }

    @Override
    public void dropBlockAsItemWithChance( World world, int i, int j, int k, int iMetaData, float fChance, int iFortuneModifier )
    {
        super.dropBlockAsItemWithChance(world, i, j, k, iMetaData, fChance, iFortuneModifier );

        if ( !world.isRemote )
        {
            this.dropItemsIndividually( world, i, j, k, BTWItems.dirtPile.itemID, 6, 0, fChance );
        }
    }

    @Override
    public boolean dropComponentItemsOnBadBreak( World world, int i, int j, int k, int iMetadata, float fChance )
    {

        dropItemsIndividually( world, i, j, k, Item.flint.itemID, 1, 0, fChance );

        dropItemsIndividually( world, i, j, k, BTWItems.dirtPile.itemID, 4, 0, fChance );

        return true;
    }

    @Override
    protected boolean canSilkHarvest()
    {
        // can't silk harvest due to conflicting drops and conversion recipes old clay blocks

        return false;
    }

    @Override
    public boolean canTransmitRotationVerticallyOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
    {
        return false;
    }

    @Override
    public boolean canBePistonShoveled( World world, int i, int j, int k )
    {
        return true;
    }

    //------------- Class Specific Methods ------------//

    //----------- Client Side Functionality -----------//

    protected Icon m_IconNaturalClay;

    @Override
    public void registerIcons( IconRegister register )
    {
        super.registerIcons( register );

        m_IconNaturalClay = register.registerIcon( "refamished:flint_ore" );
    }

    @Override
    public Icon getBlockTexture( IBlockAccess blockAccess, int i, int j, int k, int iSide )
    {
        return m_IconNaturalClay;
    }
}
