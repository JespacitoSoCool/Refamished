package net.fabricmc.refamished.blocks.decorative;

import btw.block.BTWBlocks;
import btw.item.util.ItemUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

import java.util.List;
import java.util.Random;

public class RottenWeb extends BlockWeb {
    public RottenWeb(int par1) {
        super(par1);
        this.setHardness(5.0f);
        this.setUnlocalizedName("rotten_web");
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, int i, int j, int k, int iMetadata) {
        if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemShears && this.getDamageLevel(iMetadata) == 0) {
            player.addStat(StatList.mineBlockStatArray[this.blockID], 1);
        } else {
            super.harvestBlock(world, player, i, j, k, iMetadata);
        }
    }

    @Override
    public boolean convertBlock(ItemStack stack, World world, int i, int j, int k, int iFromSide) {
        int iOldMetadata = world.getBlockMetadata(i, j, k);
        int iDamageLevel = this.getDamageLevel(iOldMetadata);
        if (iDamageLevel < 3) {
            this.setDamageLevel(world, i, j, k, iDamageLevel + 1);
            return true;
        }
        return false;
    }

    @Environment(value=EnvType.CLIENT)
    private Icon[] iconByDamageArray;

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        this.iconByDamageArray = new Icon[4];
        this.iconByDamageArray[0] = this.blockIcon = register.registerIcon("refamished:refamished:rotten_web");
        this.iconByDamageArray[1] = register.registerIcon("refamished:rotten_web_1");
        this.iconByDamageArray[2] = register.registerIcon("refamished:rotten_web_2");
        this.iconByDamageArray[3] = register.registerIcon("refamished:rotten_web_3");
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getIcon(int iSide, int iMetadata) {
        return this.iconByDamageArray[this.getDamageLevel(iMetadata)];
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
        renderer.setRenderBounds(this.getBlockBoundsFromPoolBasedOnState(renderer.blockAccess, i, j, k));
        return renderer.renderCrossedSquares(this, i, j, k);
    }
}
