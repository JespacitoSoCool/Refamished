package net.fabricmc.refamished.misc.CustomRecipes.crafting;

import btw.item.items.ArmorItemMod;
import com.prupe.mcpatcher.cc.ColorizeEntity;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

import java.util.ArrayList;

public class RecipesArmorPigment implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World) {
        ItemStack var3 = null;
        ArrayList<ItemStack> var4 = new ArrayList<ItemStack>();
        for (int var5 = 0; var5 < par1InventoryCrafting.getSizeInventory(); ++var5) {
            ItemStack var6 = par1InventoryCrafting.getStackInSlot(var5);
            if (var6 == null) continue;
            if (var6.getItem() instanceof ItemArmor) {
                ItemArmor var7 = (ItemArmor)var6.getItem();
                if (var7 instanceof ArmorItemMod ? !((ArmorItemMod)var7).hasCustomColors() || var3 != null : var7.getArmorMaterial() != EnumArmorMaterial.CLOTH || var3 != null) {
                    return false;
                }
                var3 = var6;
                continue;
            }
            if (var6.itemID != RefamishedItems.pigment.itemID) {
                return false;
            }
            var4.add(var6);
        }
        return var3 != null && !var4.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
        int var17;
        float var11;
        float var10;
        int var9;
        int var7;
        ItemStack var2 = null;
        int[] var3 = new int[3];
        int var4 = 0;
        int var5 = 0;
        ItemArmor var6 = null;
        for (var7 = 0; var7 < par1InventoryCrafting.getSizeInventory(); ++var7) {
            ItemStack var8 = par1InventoryCrafting.getStackInSlot(var7);
            if (var8 == null) continue;
            if (var8.getItem() instanceof ItemArmor) {
                var6 = (ItemArmor)var8.getItem();
                if (var6 instanceof ArmorItemMod ? !((ArmorItemMod)var6).hasCustomColors() || var2 != null : var6.getArmorMaterial() != EnumArmorMaterial.CLOTH || var2 != null) {
                    return null;
                }
                var2 = var8.copy();
                var2.stackSize = 1;
                if (!var6.hasColor(var8)) continue;
                var9 = var6.getColor(var2);
                var10 = (float)(var9 >> 16 & 0xFF) / 255.0f;
                var11 = (float)(var9 >> 8 & 0xFF) / 255.0f;
                float var12 = (float)(var9 & 0xFF) / 255.0f;
                var4 = (int)((float)var4 + Math.max(var10, Math.max(var11, var12)) * 255.0f);
                var3[0] = (int)((float)var3[0] + var10 * 255.0f);
                var3[1] = (int)((float)var3[1] + var11 * 255.0f);
                var3[2] = (int)((float)var3[2] + var12 * 255.0f);
                ++var5;
                continue;
            }
            if (var8.itemID != RefamishedItems.pigment.itemID) {
                return null;
            }
            float[] var14 = MinecraftServer.getIsServer() ? EntitySheep.fleeceColorTable[BlockColored.getBlockFromDye(var8.getItemDamage())] : ColorizeEntity.getArmorDyeColor(EntitySheep.fleeceColorTable[BlockColored.getBlockFromDye(var8.getItemDamage())], BlockColored.getBlockFromDye(var8.getItemDamage()));
            int var16 = (int)(var14[0] * 255.0f);
            int var15 = (int)(var14[1] * 255.0f);
            var17 = (int)(var14[2] * 255.0f);
            var4 += Math.max(var16, Math.max(var15, var17));
            var3[0] = var3[0] + var16;
            var3[1] = var3[1] + var15;
            var3[2] = var3[2] + var17;
            ++var5;
        }
        if (var6 == null) {
            return null;
        }
        var7 = var3[0] / var5;
        int var13 = var3[1] / var5;
        var9 = var3[2] / var5;
        var10 = (float)var4 / (float)var5;
        var11 = Math.max(var7, Math.max(var13, var9));
        var7 = (int)((float)var7 * var10 / var11);
        var13 = (int)((float)var13 * var10 / var11);
        var9 = (int)((float)var9 * var10 / var11);
        var17 = (var7 << 8) + var13;
        var17 = (var17 << 8) + var9;
        var6.func_82813_b(var2, var17);
        return var2;
    }

    @Override
    public int getRecipeSize() {
        return 10;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    @Override
    public boolean matches(IRecipe recipe) {
        return false;
    }

    @Override
    public boolean hasSecondaryOutput() {
        return false;
    }

    @Override
    public ItemStack[] getSecondaryOutput(IInventory inventory) {
        return null;
    }
}
