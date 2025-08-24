package net.fabricmc.refamished.misc;

import com.prupe.mcpatcher.mal.block.RenderBlocksUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.blocks.copperConduct;
import net.fabricmc.refamished.entities.tiles.copperAnvilTile;
import net.fabricmc.refamished.entities.tiles.steelAnvilTile;
import net.fabricmc.refamished.entities.tiles.stoneAnvilTile;
import net.fabricmc.refamished.gui.StoneAnvilGui;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.RenderBlocks;

public class RenderShit {
    public static boolean renderBlockCopperConduct(RenderBlocks renderer,copperConduct block, int x, int y, int z) {
        float min = 3f / 16f;
        float max = 13f / 16f;

        // Base cube
        renderer.setRenderBounds(min, min, min, max, max, max);
        //renderer.setOverrideBlockTexture(copperConduct.closed);
        renderer.renderStandardBlock(block, x, y, z);
        //renderer.clearOverrideBlockTexture();

        // Connections
        if (block.connectsTo(renderer.blockAccess, x, y - 1, z)) {
            renderer.setRenderBounds(min, 0f, min, max, min, max);
            //renderer.setOverrideBlockTexture(isOpen(renderer,x,y,z,2) ? copperConduct.bend : copperConduct.closed);
            renderer.renderStandardBlock(block, x, y, z);
            //renderer.clearOverrideBlockTexture();
        }
        if (block.connectsTo(renderer.blockAccess, x, y + 1, z)) {
            renderer.setRenderBounds(min, max, min, max, 1f, max);
            //renderer.setOverrideBlockTexture(isOpen(renderer,x,y,z,2) ? copperConduct.bend : copperConduct.closed);
            renderer.renderStandardBlock(block, x, y, z);
            //renderer.clearOverrideBlockTexture();
        }
        if (block.connectsTo(renderer.blockAccess, x - 1, y, z)) {
            renderer.setRenderBounds(0f, min, min, min, max, max);
            //renderer.setOverrideBlockTexture(isOpen(renderer,x,y,z,2) ? copperConduct.bend : copperConduct.closed);
            renderer.renderStandardBlock(block, x, y, z);
            //renderer.clearOverrideBlockTexture();
        }
        if (block.connectsTo(renderer.blockAccess, x + 1, y, z)) {
            renderer.setRenderBounds(max, min, min, 1f, max, max);
            //renderer.setOverrideBlockTexture(isOpen(renderer,x,y,z,2) ? copperConduct.bend : copperConduct.closed);
            renderer.renderStandardBlock(block, x, y, z);
            //renderer.clearOverrideBlockTexture();
        }
        if (block.connectsTo(renderer.blockAccess, x, y, z - 1)) {
            renderer.setRenderBounds(min, min, 0f, max, max, min);
            //renderer.setOverrideBlockTexture(isOpen(renderer,x,y,z,2) ? copperConduct.bend : copperConduct.closed);
            renderer.renderStandardBlock(block, x, y, z);
            //renderer.clearOverrideBlockTexture();
        }
        if (block.connectsTo(renderer.blockAccess, x, y, z + 1)) {
            renderer.setRenderBounds(min, min, max, max, max, 1f);
            //renderer.setOverrideBlockTexture(isOpen(renderer,x,y,z,2) ? copperConduct.bend : copperConduct.closed);
            renderer.renderStandardBlock(block, x, y, z);
            //renderer.clearOverrideBlockTexture();
        }

        return true;
    }

    public static boolean isOpen(RenderBlocks renderer,int x, int y, int z, int side) {
        for (int var3 = 0; var3 < 6; ++var3) {
            if (var3 == side) continue;
            if (var3 == copperConduct.oppositeSide(side) && copperConduct.connectsTo(renderer.blockAccess,x,y,z,copperConduct.oppositeSide(side))) {
                return false;
            }
            if (!copperConduct.connectsTo(renderer.blockAccess,x,y,z,var3)) {
                return false;
            }
        }
        return true;
    }
}
