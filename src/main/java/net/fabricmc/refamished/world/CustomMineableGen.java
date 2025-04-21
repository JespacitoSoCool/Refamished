package net.fabricmc.refamished.world;

import btw.world.util.BlockPos;
import net.minecraft.src.Block;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenMinable;

import java.util.Random;

public class CustomMineableGen extends WorldGenMinable {

    private int minableBlockId;
    private int alt1_minableBlockId;

    /** The number of blocks to generate. */
    private int numberOfBlocks;
    private int typeofGen;
    private float airExposureRatio;

    private int isReplaceable(int id,int type) {
        if (type == 0)
        {
            if (Block.dirt.blockID == id || Block.grass.blockID == id)	{
                return minableBlockId;
            }
        }
        else if (type == 1)
        {
            if (Block.dirt.blockID == id) {
                return minableBlockId;
            } else if (Block.grass.blockID == id) {
                return alt1_minableBlockId;
            }
        }
        if (Block.stone.blockID == id)
        {
            return minableBlockId;
        }
        return 0;
    }

    public CustomMineableGen(int par1, int par2, int par3,int alt_id1)
    {
        super(0,0,0);
        this.minableBlockId = par1;
        this.numberOfBlocks = par2;
        this.typeofGen = par3;
        this.alt1_minableBlockId = alt_id1;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        float var6 = par2Random.nextFloat() * (float)Math.PI;
        double var7 = (float)(par3 + 8) + MathHelper.sin(var6) * (float)this.numberOfBlocks / 8.0f;
        double var9 = (float)(par3 + 8) - MathHelper.sin(var6) * (float)this.numberOfBlocks / 8.0f;
        double var11 = (float)(par5 + 8) + MathHelper.cos(var6) * (float)this.numberOfBlocks / 8.0f;
        double var13 = (float)(par5 + 8) - MathHelper.cos(var6) * (float)this.numberOfBlocks / 8.0f;
        double var15 = par4 + par2Random.nextInt(3) - 2;
        double var17 = par4 + par2Random.nextInt(3) - 2;
        for (int var19 = 0; var19 <= this.numberOfBlocks; ++var19) {
            double var20 = var7 + (var9 - var7) * (double)var19 / (double)this.numberOfBlocks;
            double var22 = var15 + (var17 - var15) * (double)var19 / (double)this.numberOfBlocks;
            double var24 = var11 + (var13 - var11) * (double)var19 / (double)this.numberOfBlocks;
            double var26 = par2Random.nextDouble() * (double)this.numberOfBlocks / 16.0;
            double var28 = (double)(MathHelper.sin((float)var19 * (float)Math.PI / (float)this.numberOfBlocks) + 1.0f) * var26 + 1.0;
            double var30 = (double)(MathHelper.sin((float)var19 * (float)Math.PI / (float)this.numberOfBlocks) + 1.0f) * var26 + 1.0;
            int var32 = MathHelper.floor_double(var20 - var28 / 2.0);
            int var33 = MathHelper.floor_double(var22 - var30 / 2.0);
            int var34 = MathHelper.floor_double(var24 - var28 / 2.0);
            int var35 = MathHelper.floor_double(var20 + var28 / 2.0);
            int var36 = MathHelper.floor_double(var22 + var30 / 2.0);
            int var37 = MathHelper.floor_double(var24 + var28 / 2.0);
            for (int var38 = var32; var38 <= var35; ++var38) {
                double var39 = ((double)var38 + 0.5 - var20) / (var28 / 2.0);
                if (!(var39 * var39 < 1.0)) continue;
                for (int var41 = var33; var41 <= var36; ++var41) {
                    double var42 = ((double)var41 + 0.5 - var22) / (var30 / 2.0);
                    if (!(var39 * var39 + var42 * var42 < 1.0)) continue;
                    for (int var44 = var34; var44 <= var37; ++var44) {
                        double var45 = ((double)var44 + 0.5 - var24) / (var28 / 2.0);
                        int replaceId = isReplaceable(par1World.getBlockId(var38, var41, var44),typeofGen);
                        if (!(var39 * var39 + var42 * var42 + var45 * var45 < 1.0) || replaceId != 0) continue;
                        int metadata = 0;
                        Block block = Block.blocksList[this.minableBlockId];
                        if (block.hasStrata() && var41 <= 48 + par1World.rand.nextInt(2)) {
                            int strataLevel = 1;
                            if (var41 <= 24 + par1World.rand.nextInt(2)) {
                                strataLevel = 2;
                            }
                            metadata = block.getMetadataConversionForStrataLevel(strataLevel, 0);
                        }
                        if (!this.shouldSpawnOreBlock(par1World, par2Random, var38, var41, var44)) continue;
                        par1World.setBlock(var38, var41, var44, replaceId, metadata, 2);
                    }
                }
            }
        }
        return true;
    }

    public WorldGenMinable setNeedsAirExposure(float ratio) {
        if (ratio < 0.0f || ratio > 1.0f) {
            throw new IllegalArgumentException("Ratio for air exposure must be between 0 and 1, inclusive");
        }
        this.airExposureRatio = ratio;
        return this;
    }

    public WorldGenMinable setNeedsAirExposure() {
        return this.setNeedsAirExposure(1.0f);
    }

    public WorldGenMinable setNoAirExposure(float ratio) {
        if (ratio < 0.0f || ratio > 1.0f) {
            throw new IllegalArgumentException("Ratio for air exposure must be between 0 and 1, inclusive");
        }
        this.airExposureRatio = -ratio;
        return this;
    }

    public WorldGenMinable setNoAirExposure() {
        return this.setNoAirExposure(1.0f);
    }

    private boolean shouldSpawnOreBlock(World world, Random rand, int x, int y, int z) {
        if (this.airExposureRatio == 0.0f) {
            return true;
        }
        boolean blockExposedToAir = this.isBlockExposedToAir(world, x, y, z);
        if (!blockExposedToAir && this.airExposureRatio > 0.0f) {
            return rand.nextFloat() > this.airExposureRatio;
        }
        if (blockExposedToAir && this.airExposureRatio < 0.0f) {
            float ratio = -this.airExposureRatio;
            return rand.nextFloat() > ratio;
        }
        return true;
    }

    private boolean isBlockExposedToAir(World world, int x, int y, int z) {
        for (int i = 0; i < 6; ++i) {
            BlockPos pos = new BlockPos(x, y, z);
            pos.addFacingAsOffset(i);
            if (!world.isAirBlock(pos.x, pos.y, pos.z)) continue;
            return true;
        }
        return false;
    }
}
