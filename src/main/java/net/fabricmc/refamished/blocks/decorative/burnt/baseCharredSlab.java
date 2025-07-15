package net.fabricmc.refamished.blocks.decorative.burnt;

import btw.block.blocks.FallingFullBlock;
import btw.block.blocks.FallingSlabBlock;
import btw.client.render.util.RenderUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.*;

import java.util.Random;

public class baseCharredSlab extends FallingSlabBlock {
    public baseCharredSlab(int blockID, Material material) {
        super(blockID, material);
        this.setTickRandomly(true);
    }

    @Override
    public int getCombinedBlockID(int var1) {
        return 0;
    }

    @Override
    public boolean getIsUpsideDown(int iMetadata) {
        return false;
    }

    @Environment(value=EnvType.CLIENT)
    public Icon[] iconEmbers;

    public int getBurnLevel(IBlockAccess blockAccess, int i, int j, int k) {
        int iMetadata = blockAccess.getBlockMetadata(i, j, k);
        return this.getBurnLevel(iMetadata);
    }

    public int getBurnLevel(int iMetadata) {
        return iMetadata;
    }

    public void setBurnLevel(World world, int i, int j, int k, int iLevel) {
        world.setBlockMetadataWithNotify(i, j, k, iLevel);
    }

    private void emitSmokeParticles(World world, double dCenterX, double dCenterY, double dCenterZ, Random rand, int iBurnLevel) {
        for (int iTempCount = 0; iTempCount < 5; ++iTempCount) {
            double xPos = dCenterX - 0.6 + rand.nextDouble() * 1.2;
            double yPos = dCenterY + 0.25 + rand.nextDouble() * 0.25;
            double zPos = dCenterZ - 0.6 + rand.nextDouble() * 1.2;
            if (iBurnLevel > 0) {
                world.spawnParticle("fcwhitesmoke", xPos, yPos, zPos, 0.0, 0.0, 0.0);
                continue;
            }
            world.spawnParticle("largesmoke", xPos, yPos, zPos, 0.0, 0.0, 0.0);
        }
    }

    @Environment(value=EnvType.CLIENT)
    public Icon getBurntTexture(IBlockAccess ba, int i, int j, int k) {
        int iBurnLevel = ba.getBlockMetadata(i,j,k);
        if (iBurnLevel >= 0 && iBurnLevel <= 2) {
            return iconEmbers[iBurnLevel];
        }
        else if (iBurnLevel == 3) {
            return iconEmbers[3];
        }
        else if (iBurnLevel > 3 && iBurnLevel <= 6) {
            return iconEmbers[4];
        }
        else if (iBurnLevel > 6) {
            return iconEmbers[5];
        }
        return iconEmbers[3];
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void renderBlockSecondPass(RenderBlocks renderBlocks, int i, int j, int k, boolean bFirstPassResult) {
        if (bFirstPassResult) {
            int iMetadata = renderBlocks.blockAccess.getBlockMetadata(i,j,k);
            Icon ember = getBurntTexture(renderBlocks.blockAccess,i,j,k);
            if (iMetadata >= 4) {
                RenderUtils.renderBlockFullBrightWithTexture(renderBlocks, renderBlocks.blockAccess, i, j, k, ember);
            }
            else {
                this.renderBlockWithTexture(renderBlocks, i, j, k, ember);
            }
        }
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void renderFallingBlock(RenderBlocks renderBlocks, int i, int j, int k, int iMetadata) {
        renderBlocks.setRenderAllFaces(true);
        renderBlocks.setRenderBounds(this.getFixedBlockBoundsFromPool());
        renderBlocks.renderStandardBlock(this, i, j, k);
        Icon ember = getBurntTexture(renderBlocks.blockAccess,i,j,k);
        if (iMetadata <= 4) {
            RenderUtils.renderBlockFullBrightWithTexture(renderBlocks, renderBlocks.blockAccess, i, j, k, ember);
            this.renderBlockWithTexture(renderBlocks, i, j, k, ember);
        }
        else {
            this.renderBlockWithTexture(renderBlocks, i, j, k, ember);
        }
        renderBlocks.setRenderAllFaces(false);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void randomDisplayTick(World world, int i, int j, int k, Random rand) {
        int meta = world.getBlockMetadata(i,j,k);
        if (meta <= 3) {
            return;
        }
        if (rand.nextInt(5) == 0) {
            this.emitSmokeParticles(world, (double)i + 0.5, (double)j + 0.5, (double)k + 0.5, rand, this.getBurnLevel(world, i, j, k));
        }
        if (rand.nextInt(24) == 0) {
            float fVolume = 0.1f + rand.nextFloat() * 0.1f;
            world.playSound((double)i + 0.5, (double)j + 0.5, (double)k + 0.5, "fire.fire", fVolume, rand.nextFloat() * 0.7f + 0.3f, false);
        }
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random rand) {
        super.updateTick(world,i,j,k,rand);
        if (this.hasWaterToSidesOrTop(world, i, j, k)) {
            int iBurnLevel = this.getBurnLevel(world, i, j, k);
            if (iBurnLevel > 2) {
                this.setBurnLevel(world, i, j, k, 2);
                world.playAuxSFX(2227, i, j, k, 0);
            }
        }
    }

    @Override
    public void randomUpdateTick(World world, int i, int j, int k, Random rand) {
        super.randomUpdateTick(world,i,j,k,rand);
        if (world.getGameRules().getGameRuleBooleanValue("doFireTick") && !this.checkForGoOutInRain(world, i, j, k)) {
            int iBurnLevel = this.getBurnLevel(world, i, j, k);
            if (rand.nextInt(5) == 0) {
                if (iBurnLevel > 0) {
                    this.setBurnLevel(world, i, j, k, iBurnLevel - 1);
                }
                else {
                    world.setBlockWithNotify(i, j, k, getHealingBlock());
                }
            }
            this.scheduleCheckForFall(world, i, j, k);
        }
    }

    public int getHealingBlock() {
        return 0;
    }

    private boolean checkForGoOutInRain(World world, int i, int j, int k) {
        if (world.rand.nextInt(3) == 0 && world.isRainingAtPos(i, j + 1, k)) {
            world.playAuxSFX(2227, i, j, k, 0);
            int iBurnLevel = this.getBurnLevel(world, i, j, k);
            if (iBurnLevel > 0) {
                this.setBurnLevel(world, i, j, k, iBurnLevel - 1);
            }
            else
            {
                world.setBlockWithNotify(i, j, k, getHealingBlock());
            }
            return true;
        }
        return false;
    }
}
