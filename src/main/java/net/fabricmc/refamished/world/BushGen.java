package net.fabricmc.refamished.world;

import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

import java.util.Random;

public class BushGen extends WorldGenerator {
    private final int bushBlockID = RefamishedBlocks.bushWild.blockID;
    public BushGen() {}

    public int getBushMetadata(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        BiomeGenBase currentBiome = par1World.getBiomeGenForCoords( par3, par5 );
        if (currentBiome == BiomeGenBase.forest || currentBiome == BiomeGenBase.forestHills || currentBiome == BiomeGenBase.plains)
        {
            return 0;
        }
        else if (currentBiome == BiomeGenBase.swampland)
        {
            return par2Random.nextInt(2)*3;
        }
        else if (currentBiome == BiomeGenBase.jungle)
        {
            return par2Random.nextInt(2)*4;
        }
        else if (currentBiome == BiomeGenBase.taiga || currentBiome == BiomeGenBase.taigaHills || currentBiome == BiomeGenBase.iceMountains)
        {
            return par2Random.nextInt(3);
        }
        return -1;
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
            int meta = getBushMetadata(par1World,par2Random,par3,par4,par5);
            if (meta >= 0 && par1World.isAirBlock(var7, var8, var9) && (!par1World.canBlockSeeTheSky(var7, var8, var9) && var8 < 127 && var8 > 50) &&
                    Block.blocksList[this.bushBlockID].canBlockStayDuringGenerate(par1World, var7, var8, var9))
            {
                par1World.setBlock(var7, var8, var9, this.bushBlockID, 0,2);
                par1World.setBlockMetadata(var7, var8, var9, meta);

            }
        }

        return true;
    }
}
