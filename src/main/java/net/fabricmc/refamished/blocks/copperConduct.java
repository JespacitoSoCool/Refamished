package net.fabricmc.refamished.blocks;

import btw.block.BTWBlocks;
import btw.block.model.BlockModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.entities.tiles.copperConductTile;
import net.fabricmc.refamished.entities.tiles.tarTankTile;
import net.fabricmc.refamished.interfaces.IConductConnectable;
import net.fabricmc.refamished.misc.RenderShit;
import net.minecraft.src.*;

public class copperConduct extends steamContainer {

    public copperConduct(int i) {
        super(i, Material.iron);
        setHardness(1.5F);
        setResistance(8.0F);
        setStepSound(soundMetalFootstep);
        setUnlocalizedName("copper_conduct");
        setCreativeTab(CreativeTabs.tabRedstone);
        setTextureName("refamished:copper_conduct_closed");
        this.initBlockBounds(0, 0, 0, 1, 1f, 1);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new copperConductTile();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        int meta = 2; // default Z
        // Orient by the face clicked (like many pipes)
        int side = BlockPistonBase.determineOrientation(world, x, y, z, placer);
        // Up/Down -> Y axis; East/West -> X; North/South -> Z
        if (side == 0 || side == 1) meta = 1;           // Y
        else if (side == 4 || side == 5) meta = 0;      // X
        else meta = 2;                                   // Z
        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }

    public static Icon bend;
    public static Icon closed;

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);
        bend = register.registerIcon("refamished:copper_conduct_bend");
        closed = register.registerIcon("refamished:copper_conduct_closed");
    }


    @Override
    @Environment(EnvType.CLIENT)
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
        return RenderShit.renderBlockCopperConduct(renderer,this, i, j, k);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        // Bounding box for collision/selection
        float min = 3f / 16f;
        float max = 13f / 16f;

        boolean north = connectsTo(world, x, y, z - 1);
        boolean south = connectsTo(world, x, y, z + 1);
        boolean west  = connectsTo(world, x - 1, y, z);
        boolean east  = connectsTo(world, x + 1, y, z);
        boolean down  = connectsTo(world, x, y - 1, z);
        boolean up    = connectsTo(world, x, y + 1, z);

        float minX = west ? 0f : min;
        float maxX = east ? 1f : max;
        float minY = down ? 0f : min;
        float maxY = up ? 1f : max;
        float minZ = north ? 0f : min;
        float maxZ = south ? 1f : max;

        this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide) {
        return true;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void renderBlockAsItem(RenderBlocks renderBlocks, int iItemDamage, float fBrightness) {
        renderBlocks.renderBlockAsItemVanilla(this, iItemDamage, fBrightness);
    }
}