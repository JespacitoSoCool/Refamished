package net.fabricmc.refamished.blocks.decorative.burnt;

import btw.block.blocks.FallingSlabBlock;
import btw.client.render.util.RenderUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.entities.tiles.burntChestTile;
import net.minecraft.src.*;

import java.util.Random;

public class burntChest extends BlockChest {
    public burntChest(int blockID) {
        super(blockID,0);
        this.setTickRandomly(true);
        this.setUnlocalizedName("burnt_chest");
    }
    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        if (par1World.isRemote) {
            return true;
        }
        par1World.destroyBlock(par2,par3,par4,false);
        return true;
    }

    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("refamished:charring/burnt_planks");
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new burntChestTile();
    }
}
