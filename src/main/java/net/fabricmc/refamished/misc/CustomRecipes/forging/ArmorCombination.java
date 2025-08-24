package net.fabricmc.refamished.misc.CustomRecipes.forging;

import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.items.materials.metallurgyArmor;
import net.fabricmc.refamished.misc.Recipes.ForgingPlansRecipes;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ArmorCombination extends ForgingPlansRecipes.RecipeEntry {

    private final Object[][] armorPairs = new Object[][] {
            { "helm", "coif", RefamishedItems.craftedHelmet },
            { "pauldrons", "cuirass", RefamishedItems.craftedChestplate },
            { "tassets", "greaves", RefamishedItems.craftedLeggings },
            { "threads", "sabatons", RefamishedItems.craftedBoots },
    };

    public ArmorCombination() {
        super(Arrays.asList(new ItemStack(RefamishedItems.metallurgyArmor),new ItemStack(RefamishedItems.metallurgyArmor),new ItemStack(RefamishedItems.sugar_resin)), new ItemStack(RefamishedItems.craftedLeggings), 200, 0);
    }

    public String getOutputNbt(List<ItemStack> inventoryInputs) {
        if (inventoryInputs == null || inventoryInputs.isEmpty()) return "";
        for (Object[] pair : armorPairs) {
            String topName = (String) pair[0];
            String bottomName = (String) pair[1];
            Item resultItem = (Item) pair[2];

            String topMat = null;
            String bottomMat = null;

            for (ItemStack part : inventoryInputs) {
                if (part == null) continue;
                if (metallurgyArmor.isPartOf(part, topName)) {
                    topMat = metallurgyArmor.getMaterial(part, topName);
                } else if (metallurgyArmor.isPartOf(part, bottomName)) {
                    bottomMat = metallurgyArmor.getMaterial(part, bottomName);
                }

                if (topMat != null && bottomMat != null) break;
            }

            if (topMat != null && bottomMat != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("DynamicId=").append(resultItem.itemID).append(";");
                sb.append("Top=").append(topMat).append(";");
                sb.append("Bottom=").append(bottomMat).append(";");

                return sb.toString();
            }
        }
        return "";
    }


    @Override
    public boolean matches(List<ItemStack> inventoryInputs) {
        if (inputs == null || inputs.isEmpty()) return false;
        if (inventoryInputs == null || inventoryInputs.isEmpty()) return false;

        for (ItemStack required : inputs) {
            int needed = required.stackSize;
            for (ItemStack given : inventoryInputs) {
                if (given == null) continue;
                if (given.itemID != required.itemID) continue;

                int take = Math.min(given.stackSize, needed);
                needed -= take;
                if (needed <= 0) break;
            }

            if (needed > 0) return false;
        }

        return true;
    }

    public ItemStack get(List<ItemStack> inventoryInputs) {
        for (Object[] pair : armorPairs) {
            String topName = (String) pair[0];
            String bottomName = (String) pair[1];
            Item resultItem = (Item) pair[2];

            boolean hasTop = false;
            boolean hasBottom = false;

            ItemStack newItem = new ItemStack(resultItem, 1);
            NBTTagCompound tag = new NBTTagCompound();
            newItem.setTagCompound(tag);

            for (ItemStack part : inventoryInputs) {
                if (part == null) continue;

                if (metallurgyArmor.isPartOf(part, bottomName)) {
                    hasBottom = true;
                    tag.setString("Bottom", metallurgyArmor.getMaterial(part, bottomName));
                } else if (metallurgyArmor.isPartOf(part, topName)) {
                    hasTop = true;
                    tag.setString("Top", metallurgyArmor.getMaterial(part, topName));
                }
            }

            if (hasTop && hasBottom) {
                return newItem;
            }
        }
        return null;
    }

    @Override
    public ItemStack getOutput(List<ItemStack> inventoryInputs) {
        for (Object[] pair : armorPairs) {
            String topName = (String) pair[0];
            String bottomName = (String) pair[1];
            Item resultItem = (Item) pair[2];

            String topMat = null;
            String bottomMat = null;

            for (ItemStack stack : inventoryInputs) {
                if (stack == null) continue;

                if (metallurgyArmor.isPartOf(stack, topName)) {
                    topMat = metallurgyArmor.getMaterial(stack, topName);
                }
                else if (metallurgyArmor.isPartOf(stack, bottomName)) {
                    bottomMat = metallurgyArmor.getMaterial(stack, bottomName);
                }
            }

            if (topMat != null && bottomMat != null) {
                ItemStack result = new ItemStack(resultItem, 1);
                NBTTagCompound tag = new NBTTagCompound();
                tag.setString("Top", topMat);
                tag.setString("Bottom", bottomMat);
                result.setTagCompound(tag);
                return result;
            }
        }

        System.out.println("Nothing?");
        return null;
    }

    @Override
    public List<ItemStack> getDynamicInput(List<ItemStack> inventoryInputs) {
        List<ItemStack> required = new ArrayList<ItemStack>();
        if (inventoryInputs == null || inventoryInputs.isEmpty()) return required;

        for (int p = 0; p < armorPairs.length; p++) {
            String topKey = (String) armorPairs[p][0];
            String botKey = (String) armorPairs[p][1];

            ItemStack foundTop = null;
            ItemStack foundBottom = null;

            for (ItemStack s : inventoryInputs) {
                if (s == null) continue;
                if (foundTop == null && metallurgyArmor.isPartOf(s, topKey)) {
                    foundTop = new ItemStack(s.itemID, 1, s.getItemDamage());
                } else if (foundBottom == null && metallurgyArmor.isPartOf(s, botKey)) {
                    foundBottom = new ItemStack(s.itemID, 1, s.getItemDamage());
                }

                if (foundTop != null && foundBottom != null) break;
            }

            if (foundTop != null && foundBottom != null) {
                required.add(foundTop);
                required.add(foundBottom);
                return required;
            }
        }

        return required;
    }


    public Boolean isCombinationRecipe() {
        return true;
    }


}
