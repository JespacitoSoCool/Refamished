package net.fabricmc.refamished.misc;

import com.prupe.mcpatcher.mal.block.RenderBlocksUtils;
import net.minecraft.src.*;

import java.util.List;

public abstract class PrimitiveGeometricPort {
    public abstract PrimitiveGeometricPort MakeTemporaryCopy();

    public abstract void RotateAroundJToFacing( int iFacing );

    public abstract void TiltToFacingAlongJ( int iFacing );

    public abstract void Translate( double dDeltaX, double dDeltaY, double dDeltaZ );

    public void AddIntersectingBoxesToCollisionList(World world, int i, int j, int k, AxisAlignedBB boxToIntersect, List collisionList )
    {
    }

    public int GetAssemblyID()
    {
        return -1;
    }

    public abstract boolean RenderAsBlock(RenderBlocks renderBlocks, Block block, int i, int j, int k );

    public abstract boolean RenderAsBlockWithColorMultiplier( RenderBlocks renderBlocks, Block block, int i, int j, int k, float fRed, float fGreen, float fBlue );

    public boolean RenderAsBlockWithColorMultiplier( RenderBlocks renderBlocks, Block block, int i, int j, int k )
    {
        int iColorMultiplier = block.colorMultiplier( renderBlocks.blockAccess, i, j, k );

        float fRed = (float)(iColorMultiplier >> 16 & 255) / 255F;
        float fGreen = (float)(iColorMultiplier >> 8 & 255) / 255F;
        float fBlue = (float)(iColorMultiplier & 255) / 255F;

        RenderBlocksUtils.setupColorMultiplierForceNoAO(block, renderBlocks.blockAccess, i, j, k, fRed, fGreen, fBlue, true);

        return RenderAsBlockWithColorMultiplier( renderBlocks, block, i, j, k, fRed, fGreen, fBlue );
    }

    public abstract boolean RenderAsBlockWithTexture( RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon icon );

    public abstract boolean RenderAsBlockFullBrightWithTexture( RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon icon );

    public abstract void RenderAsItemBlock( RenderBlocks renderBlocks, Block block, int iItemDamage );

    public abstract void RenderAsFallingBlock( RenderBlocks renderBlocks, Block block, int i, int j, int k, int iMetadata );
}
