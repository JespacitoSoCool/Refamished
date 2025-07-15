package net.fabricmc.refamished.world;

import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.Unique;

import java.util.Random;

public class ScorchedStoneGen extends WorldGenerator {

    int[] options = {
            RefamishedBlocks.scorchedStone.blockID,
            RefamishedBlocks.scorchedStoneOre.blockID,
    };

    private int getIdk(int[] what)
    {
        Random random = new Random();
        int randomIndex = random.nextInt(what.length);
        return what[randomIndex];
    }
    private int numberOfBlocks;

    public ScorchedStoneGen(int i) {
        this.numberOfBlocks = i;
    }

    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {
        if (world.getBlockMaterial(i, j, k) != Material.lava) {
            return false;
        }
        //System.out.println("LAVA "+i+" "+j+" "+k);
        int n = random.nextInt(this.numberOfBlocks - 2) + 2;
        int n2 = 1;
        for (int i2 = i - n; i2 <= i + n; ++i2) {
            for (int i3 = k - n; i3 <= k + n; ++i3) {
                int n3 = i2 - i;
                int n4 = i3 - k;
                if (n3 * n3 + n4 * n4 > n * n) continue;
                for (int i4 = j - n2; i4 <= j + n2; ++i4) {
                    int n5 = world.getBlockId(i2, i4, i3);
                    if (n5 == Block.stone.blockID)
                    {
                            int meta = world.getBlockMetadata(i2, i4, i3) + random.nextInt(2)*3;
                            //System.out.println("HI "+i2+" "+i4+" "+i3);
                            int getId = getIdk(options);
                            //System.out.println(i2 +" "+i4+" "+i3);
                            if (getId == RefamishedBlocks.scorchedStoneOre.blockID)
                            {
                                world.setBlock(i2, i4, i3, getId, 0, 2);
                            }
                            else {
                                world.setBlockAndMetadata(i2, i4, i3, getId,meta);
                            }
                    }
                }
            }
        }
        return true;
    }
}
