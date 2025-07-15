package net.fabricmc.refamished.world;

import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.*;

import java.util.Random;

public class BoulderGen extends WorldGenerator {

    public BoulderGen() {
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        while (y > 2 && world.isAirBlock(x, y - 1, z)) {
            y--;
        }

        // Place boulder only on dirt or grass
        int ground = world.getBlockId(x, y - 1, z);
        if (ground != Block.grass.blockID && ground != Block.dirt.blockID && ground != Block.waterStill.blockID && ground != Block.waterMoving.blockID && ground != Block.sand.blockID) {
            return false;
        }

        // Random size (2x2 to 3x3)
        int radius = 1 + rand.nextInt(2);
        int height = radius;
        //int height = 1 + rand.nextInt(2);
        int offset = -rand.nextInt(2);
        int outerBlock = Block.stone.blockID;
        int coreBlock = RefamishedBlocks.denseStone.blockID;

        if (ground == Block.sand.blockID) {
            radius = height;
            if (rand.nextBoolean()) {
                outerBlock = Block.sandStone.blockID;
                coreBlock = Block.sandStone.blockID;
            } else {
                outerBlock = Block.gravel.blockID;
                coreBlock = Block.gravel.blockID;
            }
        }  else if (ground == Block.waterMoving.blockID || ground == Block.waterStill.blockID) {
            int depth = 0;
            for (int dy = y - 1; dy >= y - 3; dy--) {
                int below = world.getBlockId(x, dy, z);
                if (below == Block.waterMoving.blockID || below == Block.waterStill.blockID) {
                    depth++;
                } else {
                    break;
                }
            }

            if (depth <= 3) {
                outerBlock = Block.stone.blockID;
                coreBlock = Block.stone.blockID;
                y--;
            } else {
                return false;
            }
        }

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int dy = -height; dy <= height; dy++) {
                    int bx = x + dx;
                    int by = y + dy + offset;
                    int bz = z + dz;

                    double dist = dx * dx + dz * dz + dy * dy * 0.8; // Slight Y-squash

                    // Tighter threshold + some noise for jagged shape
                    if (dist <= (radius + 0.3) * (radius + 0.3)) {
                        int belowY = by - 1;
                        int blockBelow = world.getBlockId(bx, belowY, bz);
                        Block bBelow = Block.blocksList[blockBelow];

                        // Only place block if it's supported (non-air, non-water, collidable)
                        if (bBelow != null && bBelow.isCollidable()) {
                            int bl = world.getBlockId(bx, by, bz);
                            Block b = Block.blocksList[bl];
                            if (world.getBlockMaterial(bx, by, bz) == Material.water || world.isAirBlock(bx, by, bz) || (b != null && !b.isCollidable())) {
                                world.setBlock(bx, by, bz, outerBlock, 0, 2);
                            }
                        }
                    }

                }
            }
        }

        for (int i = 0; i < 4; i++) {
            int bx = x + rand.nextInt(3) - 1;
            int bz = z + rand.nextInt(3) - 1;
            int by = y + offset + rand.nextInt(2);

            if (world.isAirBlock(bx, by, bz)) {
                world.setBlock(bx, by, bz, outerBlock, 0, 2);
                if (rand.nextBoolean()) world.setBlock(bx, by + 1, bz, outerBlock, 0, 2);
            }
        }

        if (!(world.isAirBlock(x,y,z)&&world.isAirBlock(x,y,z)&&world.isAirBlock(x,y,z)&&
                world.isAirBlock(x,y,z)&&world.isAirBlock(x,y,z)&&world.isAirBlock(x,y,z))) {
            world.setBlock(x, y, z, coreBlock, 0,2);
        }
        else {
            world.setBlock(x, y, z, outerBlock, 0,2);
        }

        return true;
    }
}
