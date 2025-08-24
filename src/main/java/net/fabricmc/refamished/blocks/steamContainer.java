package net.fabricmc.refamished.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.entities.tiles.copperConductTile;
import net.fabricmc.refamished.interfaces.IConductConnectable;
import net.fabricmc.refamished.misc.RenderShit;
import net.minecraft.src.*;

public class steamContainer extends BlockContainer {

    public steamContainer(int i, Material idk) {
        super(i, idk);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new copperConductTile();
    }

    public static int[] SIDE_OFFSETS_X = { 0,  0,  0,  0, -1,  1 };
    public static int[] SIDE_OFFSETS_Y = {-1,  1,  0,  0,  0,  0 };
    public static int[] SIDE_OFFSETS_Z = { 0,  0, -1,  1,  0,  0 };

    public static boolean connectsTo(World world, int x, int y, int z, int side) {
        int nx = x + SIDE_OFFSETS_X[side];
        int ny = y + SIDE_OFFSETS_Y[side];
        int nz = z + SIDE_OFFSETS_Z[side];

        int id = world.getBlockId(nx, ny, nz);
        Block b = Block.blocksList[id];
        if (b == null) return false;

        if (b instanceof steamContainer) return true;

        TileEntity te = world.getBlockTileEntity(nx, ny, nz);
        if (te instanceof IConductConnectable) {
            return ((IConductConnectable) te).canConnectFromSide(world, nx, ny, nz, oppositeSide(side));
        }
        if (b instanceof IConductConnectable) {
            return ((IConductConnectable) b).canConnectFromSide(world, nx, ny, nz, oppositeSide(side));
        }

        return false;
    }

    public static boolean connectsTo(IBlockAccess access, int x, int y, int z, int side) {
        int nx = x + SIDE_OFFSETS_X[side];
        int ny = y + SIDE_OFFSETS_Y[side];
        int nz = z + SIDE_OFFSETS_Z[side];

        int id = access.getBlockId(nx, ny, nz);
        Block b = Block.blocksList[id];
        if (b == null) return false;

        if (b instanceof steamContainer) return true;

        TileEntity te = access.getBlockTileEntity(nx, ny, nz);
        if (te instanceof IConductConnectable) {
            return ((IConductConnectable) te).canConnectFromSide(access, nx, ny, nz, oppositeSide(side));
        }
        if (b instanceof IConductConnectable) {
            return ((IConductConnectable) b).canConnectFromSide(access, nx, ny, nz, oppositeSide(side));
        }

        return false;
    }

    public static int oppositeSide(int side) {
        switch (side) {
            case 0: return 1;
            case 1: return 0;
            case 2: return 3;
            case 3: return 2;
            case 4: return 5;
            case 5: return 4;
            default: return side;
        }
    }

    public boolean connectsTo(IBlockAccess world, int x, int y, int z) {
        Block b = Block.blocksList[world.getBlockId(x, y, z)];
        return b != null;
    }

}