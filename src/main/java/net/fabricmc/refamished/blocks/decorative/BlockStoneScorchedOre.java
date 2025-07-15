package net.fabricmc.refamished.blocks.decorative;

import btw.block.BTWBlocks;
import btw.block.blocks.OreBlockStaged;
import btw.item.BTWItems;
import btw.world.util.difficulty.Difficulty;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

import java.util.List;
import java.util.Random;

public class BlockStoneScorchedOre extends OreBlockStaged {
    public BlockStoneScorchedOre(int par1) {
        super(par1);
        setUnlocalizedName("scorched_stone_ore");
        setHardness(1F);
        setShovelsEffectiveOn();
        this.setStepSound(soundStoneFootstep);
        setTextureName("refamished:scorched_stone_ore");
    }

    @Override
    public int idDropped( int iMetaData, Random rand, int iFortuneModifier )
    {
        return RefamishedItems.scorched_rock.itemID;
    }

    @Override
    public int quantityDropped( Random rand )
    {
        return 2 + rand.nextInt( 3 );
    }

    @Override
    public int idDroppedOnConversion(Difficulty difficulty, int iMetadata) {
        return RefamishedItems.scorched_rock.itemID;
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

        m_IconNaturalClay = register.registerIcon( "refamished:scorched_stone_ore" );
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
