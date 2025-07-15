package net.fabricmc.refamished.blocks;

import btw.block.BTWBlocks;
import btw.block.model.AnvilModel;
import btw.block.model.BlockModel;
import btw.item.BTWItems;
import btw.world.util.WorldUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.entities.tiles.copperAnvilTile;
import net.fabricmc.refamished.entities.tiles.steelAnvilTile;
import net.fabricmc.refamished.entities.tiles.stoneAnvilContainer;
import net.fabricmc.refamished.misc.RefContains;
import net.minecraft.src.*;

import java.util.Random;

public class steelAnvil extends stoneAnvil {

    public steelAnvil(int par1) {
        super(par1);
        setUnlocalizedName("steel_anvil");
        setHardness(3F);
        setPicksEffectiveOn();
        this.setStepSound(soundAnvilFootstep);
        setTextureName("refamished:copper_slab");
    }

    @Override
    public int idDropped( int iMetaData, Random rand, int iFortuneModifier )
    {
        return RefamishedItems.steelIngot.itemID;
    }

    @Override
    public int quantityDropped( Random rand )
    {
        return 7;
    }

    @Override
    public void dropBlockAsItemWithChance( World world, int i, int j, int k, int iMetaData, float fChance, int iFortuneModifier )
    {
        if ( !world.isRemote )
        {
            this.dropItemsIndividually( world, i, j, k, RefamishedItems.steelIngot.itemID, 7, 0, fChance );
        }
    }

    @Override
    public boolean dropComponentItemsOnBadBreak( World world, int i, int j, int k, int iMetadata, float fChance )
    {
        this.dropItemsIndividually( world, i, j, k, RefamishedItems.steelIngot.itemID, 7, 0, fChance );

        return true;
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick) {
        if (!world.isRemote && !WorldUtils.doesBlockHaveLargeCenterHardpointToFacing(world, i, j + 1, k, 0) && player instanceof EntityPlayerMP) {

            stoneAnvilContainer container = new stoneAnvilContainer(player.inventory, (steelAnvilTile) world.getBlockTileEntity(i,j,k));
            RefamishedMod.serverOpenCustomInterface((EntityPlayerMP)player, container, RefContains.steelAnvil);
        }
        return true;
    }

    //----------- Client Side Functionality -----------//

    public static Icon front;
    public static Icon side;
    public static Icon top;

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        this.blockIcon = register.registerIcon("refamished:steel_block");
        this.side = register.registerIcon("refamished:variant/steel_anvil_side");
        this.top = register.registerIcon("refamished:variant/steel_anvil_top");
        this.front = register.registerIcon("refamished:variant/steel_anvil_front");
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getIcon(int iSide, int iMetadata) {
        if (iSide == 1)
        {
            return this.top;
        }
        else if (iSide == 1)
        {
            return this.top;
        }
        if (iMetadata%2 == 0) {
            if (iSide == 2 || iSide == 3)
            {
                return this.side;
            }
            else if (iSide == 4 || iSide == 5)
            {
                return this.front;
            }
        }
        else {
            if (iSide == 2 || iSide == 3)
            {
                return this.front;
            }
            else if (iSide == 4 || iSide == 5)
            {
                return this.side;
            }
        }
        return this.blockIcon;
    }

    AnvilModel model = new AnvilModel();

    @Override
    @Environment(value=EnvType.CLIENT)
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
        int iMetadata = renderer.blockAccess.getBlockMetadata(i, j, k);
        int iFacing = this.getFacing(iMetadata);
        BlockModel transformedModel = this.model.makeTemporaryCopy();
        transformedModel.rotateAroundYToFacing(iFacing);
        renderer.setUVRotateTop(this.convertFacingToTopTextureRotation(iFacing));
        renderer.setUVRotateBottom(this.convertFacingToBottomTextureRotation(iFacing));
        boolean bReturnValue = transformedModel.renderAsBlock(renderer, this, i, j, k);
        renderer.clearUVRotation();
        return bReturnValue;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide) {
        return this.currentBlockRenderer.shouldSideBeRenderedBasedOnCurrentBounds(iNeighborI, iNeighborJ, iNeighborK, iSide);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void renderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness) {
        this.model.renderAsItemBlock(renderer, this, iItemDamage);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
        AxisAlignedBB transformedBox = this.model.boxSelection.makeTemporaryCopy();
        transformedBox.rotateAroundYToFacing(this.getFacing(world, i, j, k));
        transformedBox.offset(i, j, k);
        return transformedBox;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new steelAnvilTile();
    }
}
