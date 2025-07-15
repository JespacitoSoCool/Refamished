package net.fabricmc.refamished.blocks.decorative.burnt;

import btw.block.blocks.FallingFullBlock;
import btw.client.render.util.RenderUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.Random;

public class baseBurntBlock extends FallingFullBlock {
    public baseBurntBlock(int blockID, Material material) {
        super(blockID, material);
        this.setTickRandomly(true);
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
        return getBurntTexture(iBurnLevel);
    }

    @Environment(value=EnvType.CLIENT)
    public Icon getBurntTexture(int iBurnLevel) {
        if (iBurnLevel > 3) {
            return iconEmbers[3];
        } else if (iconEmbers[iBurnLevel] != null) {
            return iconEmbers[iBurnLevel];
        }
        return iconEmbers[0];
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void renderBlockSecondPass(RenderBlocks renderBlocks, int i, int j, int k, boolean bFirstPassResult) {
        if (bFirstPassResult) {
            int iMetadata = renderBlocks.blockAccess.getBlockMetadata(i,j,k);
            Icon ember = getBurntTexture(renderBlocks.blockAccess,i,j,k);
            if (iMetadata >= 1) {
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
        Icon ember = getBurntTexture(iMetadata);
        if (iMetadata >= 1) {
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
        if (meta >= 1) {
            if (meta >= 2) {
                if (rand.nextInt(24) == 0) {
                    float fVolume = 0.1f + rand.nextFloat() * 0.1f;
                    world.playSound((double)i + 0.5, (double)j + 0.5, (double)k + 0.5, "fire.fire", fVolume, rand.nextFloat() * 0.7f + 0.3f, false);
                }
            }
            if (rand.nextInt(5) == 0) {
                this.emitSmokeParticles(world, (double)i + 0.5, (double)j + 0.5, (double)k + 0.5, rand, this.getBurnLevel(world, i, j, k));
            }
        }
    }

    @Override
    protected void onStartFalling(EntityFallingSand entity) {
        if (entity.metadata > 2) {
            entity.metadata = 15;
        }
        entity.worldObj.playAuxSFX(2276, MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ), 0);
    }

    @Override
    public boolean onFinishedFalling(EntityFallingSand entity, float fFallDistance) {
        if (!entity.worldObj.isRemote && entity.metadata > 2) {
            int i = MathHelper.floor_double(entity.posX);
            int j = MathHelper.floor_double(entity.posY);
            int k = MathHelper.floor_double(entity.posZ);
            int iFallDistance = MathHelper.ceiling_float_int(fFallDistance - 5.0f);
            if (iFallDistance >= 0 && !Material.water.equals(entity.worldObj.getBlockMaterial(i, j, k)) && entity.rand.nextInt(5) < iFallDistance) {
                this.explode(entity.worldObj, (double)i + 0.5, (double)j + 0.5, (double)k + 0.5);
                return false;
            }
            else {
                entity.metadata = 3;
            }
        }
        return true;
    }

    private void explode(World world, double posX, double posY, double posZ) {
        world.newExplosionNoFX(null, posX, posY, posZ, 1.5f, true, false);
        this.notifyNearbyAnimalsFinishedFalling(world, MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
        world.playAuxSFX(2277, MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ), 0);
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random rand) {
        super.updateTick(world,i,j,k,rand);
        if (this.hasWaterToSidesOrTop(world, i, j, k)) {
            int iBurnLevel = this.getBurnLevel(world, i, j, k);
            if (iBurnLevel > 2) {
                this.setBurnLevel(world, i, j, k, 0);
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
            }
        }
        if (rand.nextInt(3) == 0) {
            this.scheduleCheckForFall(world, i, j, k);
        }
    }

    private boolean checkForGoOutInRain(World world, int i, int j, int k) {
        if (world.rand.nextInt(3) == 0 && world.isRainingAtPos(i, j + 1, k)) {
            world.playAuxSFX(2227, i, j, k, 0);
            int iBurnLevel = this.getBurnLevel(world, i, j, k);
            if (iBurnLevel > 0) {
                this.setBurnLevel(world, i, j, k, iBurnLevel - 1);
            }
            return true;
        }
        return false;
    }
}
