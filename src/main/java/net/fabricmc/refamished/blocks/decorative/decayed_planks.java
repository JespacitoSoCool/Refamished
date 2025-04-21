package net.fabricmc.refamished.blocks.decorative;

import btw.block.util.Flammability;
import btw.item.BTWItems;
import btw.item.util.ItemUtils;
import com.jcraft.jorbis.Block;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.BlockWood;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

import java.util.List;

public class decayed_planks extends BlockWood {
    public decayed_planks(int blockID) {
        super(blockID);
        this.setAxesEffectiveOn();
        this.setHardness(0.5f);
        this.setResistance(3.0f);
        this.setFireProperties(Flammability.PLANKS);
        this.setBuoyant();
        this.setStepSound(soundWoodFootstep);
        this.setUnlocalizedName("decayed_wood");
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        dropBlockAsItem_do( par1World, par2, par3, par4, new ItemStack(BTWItems.sawDust, 2, 0 ));
    }

    @Override
    public void breakBlock( World world, int i, int j, int k, int iBlockID, int iMetadata )
    {
        super.breakBlock( world, i, j, k, iBlockID, iMetadata );
    }

    @Override
    public boolean dropComponentItemsOnBadBreak(World world, int i, int j, int k, int iMetadata, float fChanceOfDrop )
    {
        dropBlockAsItem_do( world, i, j, k, new ItemStack(BTWItems.sawDust, 2, 0 ));

        return true;
    }

    //----------- Client Side Functionality -----------//

    @Override
    @Environment(value= EnvType.CLIENT)
    public void getSubBlocks(int iBlockID, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(blockID, 1, 0));
    }
    @Override
    public int getRenderType() {
        return 0; // Use the default block rendering type
    }
}
