package net.fabricmc.refamished.items;

import btw.util.color.Color;
import net.minecraft.src.*;
import org.lwjgl.Sys;

import java.util.List;

public class saltpeter extends Item {
    public saltpeter(int par1) {
        super(par1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
        this.setBellowsBlowDistance(2);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ) {
        if (this.applyBoneMeal(world, i, j, k)) {
            --itemStack.stackSize;
            return true;
        }
        return false;
    }

    private boolean applyBoneMeal(World world, int i, int j, int k) {
        Block targetBlock = Block.blocksList[world.getBlockId(i, j, k)];
        return targetBlock != null && targetBlock.attemptToApplyFertilizerTo(world, i, j, k);
    }

    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int var4 = 0; var4 < 16; ++var4) {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int tintIndex) {
        int damage = stack.getItemDamage(); // Current damage value
        int maxDamage = stack.getMaxDamage(); // Max possible damage

        // Avoid division by zero
        if (maxDamage <= 0) {
            maxDamage = 1;
        }

        // Ensure floating-point division
        float progress = (float) damage / (float) maxDamage;

        // Debug progress value
        //System.out.println("Damage: " + damage + ", Max Damage: " + maxDamage + ", Progress: " + progress);

        // Interpolate color
        return interpolateColor(0xFFFFFF, 0xB4C8DB, progress/16f);
    }


    /**
     * Interpolates between two colors based on the progress.
     *
     * @param startColor Starting color
     * @param endColor   Ending color
     * @param progress   Progress value between 0.0 and 1.0
     * @return Interpolated color
     */
    private int interpolateColor(int startColor, int endColor, float progress) {
        //System.out.println(progress);
        progress = Math.min(1.0f, Math.max(0.0f, progress));

        int r1 = (startColor >> 16) & 0xFF;
        int g1 = (startColor >> 8) & 0xFF;
        int b1 = startColor & 0xFF;

        int r2 = (endColor >> 16) & 0xFF;
        int g2 = (endColor >> 8) & 0xFF;
        int b2 = endColor & 0xFF;

        int r = (int) (r1 + (r2 - r1) * progress);
        int g = (int) (g1 + (g2 - g1) * progress);
        int b = (int) (b1 + (b2 - b1) * progress);

        return (r << 16) | (g << 8) | b;
    }

}
