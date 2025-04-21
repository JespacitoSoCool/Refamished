package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.StairsBlock;
import btw.block.model.BlockModel;
import com.prupe.mcpatcher.mal.block.RenderBlocksUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.*;
import org.lwjgl.Sys;

public class copperStairBlock extends StairsBlock {

    public copperStairBlock(int par1) {
        super(par1, RefamishedBlocks.copperBlock,0);
        setHardness(5.0f);
        setResistance(10.0f);
        setStepSound(soundMetalFootstep);
        setUnlocalizedName("block_copper_stair");
        setTextureName("refamished:copper_block");
        setPicksEffectiveOn();
        setCreativeTab(CreativeTabs.tabBlock);
    }

    private Icon blockFace;
    private Icon slab;
    private Icon slabAlt;
    private Icon stairLeft;
    private Icon stairRight;
    private Icon stairLeftAlt;
    private Icon stairRightAlt;

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);
        this.blockFace = register.registerIcon("refamished:copper_block");
        this.slab = register.registerIcon("refamished:copper_slab");
        this.slabAlt = register.registerIcon("refamished:copper_slab_alt");
        this.stairLeft = register.registerIcon("refamished:copper_slab_left");
        this.stairRight = register.registerIcon("refamished:copper_slab_right");
        this.stairLeftAlt = register.registerIcon("refamished:copper_slab_left_alt");
        this.stairRightAlt = register.registerIcon("refamished:copper_slab_right_alt");
    }

    private Icon getRotatedIcon(int faceId) {
        switch (faceId) {
            case 2: return this.stairRight;
            case 3: return this.stairLeft;
            case 4: return this.slab;
            case 5: return this.blockFace;
            case 6: return this.stairRightAlt;
            case 7: return this.stairLeftAlt;
            default: return this.slab;
        }
    }

    private int[] rotateSideTextures(int metadata) {
        switch (metadata) {
            case 0:
                return new int[]{2, 3, 4, 5};
            case 1:
                return new int[]{7, 6, 5, 4};
            case 2:
                return new int[]{4, 5, 3, 2};
            case 3:
                return new int[]{5, 4, 6, 7};
            default:
                return new int[]{2, 3, 4, 5};
        }
    }

    @Override
    @Environment(value = EnvType.CLIENT)
    public void renderBlockAsItem(RenderBlocks renderBlocks, int itemDamage, float brightness) {
        renderBlocks.renderBlockAsItemVanilla(this, 0, brightness);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getIcon(int iSide, int iMetadata) {
        if (iMetadata > 3) {
            iMetadata = 0;
        }
        if (iSide == 0) {
            return this.blockFace;
        }
        if (iSide == 1) {
            if (iMetadata == 2 || iMetadata == 3)
            {
                return this.slab;
            }
            return this.slabAlt;
        }

        int[] rotatedFaces = rotateSideTextures(iMetadata);

        switch (iSide) {
            case 2: return getRotatedIcon(rotatedFaces[0]);
            case 3: return getRotatedIcon(rotatedFaces[1]);
            case 4: return getRotatedIcon(rotatedFaces[2]);
            case 5: return getRotatedIcon(rotatedFaces[3]);
            default: return this.blockFace;
        }
    }

}
