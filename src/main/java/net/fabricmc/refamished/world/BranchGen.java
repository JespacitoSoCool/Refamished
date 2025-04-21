package net.fabricmc.refamished.world;

import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.*;

import java.util.Random;

public class BranchGen extends WorldGenerator {
    private final int branchBlockID;
    public BranchGen(int ID)
    {
        this.branchBlockID = ID;
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {

        BiomeGenBase currentBiome = par1World.getBiomeGenForCoords( par3, par5 );

        boolean bIsValidBiome = currentBiome == BiomeGenBase.forest ||
                currentBiome == BiomeGenBase.taiga || currentBiome == BiomeGenBase.forestHills || currentBiome == BiomeGenBase.taigaHills
                || currentBiome == BiomeGenBase.swampland || currentBiome == BiomeGenBase.jungle;

        for (int var6 = 0; var6 < 9; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if ( !bIsValidBiome )
            {
                continue;
            }
            if (par1World.isAirBlock(var7, var8, var9) && (!par1World.canBlockSeeTheSky(var7, var8, var9) && var8 < 127 && var8 > 50) &&
                    Block.blocksList[this.branchBlockID].canBlockStayDuringGenerate(par1World, var7, var8, var9))
            {
                par1World.setBlock(var7, var8, var9, this.branchBlockID, 0, 2);

            }
        }

        return true;
    }
}
