package net.fabricmc.refamished.interfaces;

import net.minecraft.src.IBlockAccess;
import net.minecraft.src.World;

public interface IConductConnectable {
    boolean canConnectFromSide(World world, int x, int y, int z, int side);
    boolean canConnectFromSide(IBlockAccess world, int x, int y, int z, int side);
}