package net.fabricmc.refamished.itemsbase;

import btw.entity.attribute.BTWAttributes;
import btw.item.items.ArmorItemMod;
import com.google.common.collect.Multimap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.interfaces.IconLargedByItemStack;
import net.fabricmc.refamished.items.materials.metallurgyArmor;
import net.fabricmc.refamished.misc.ReMaterials;
import net.fabricmc.refamished.misc.Recipes.ForgingPlansRecipes;
import net.fabricmc.refamished.misc.materialTrait.materialTrait;
import net.fabricmc.refamished.misc.materialTrait.materialTraitHandler;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.Sys;

import java.util.*;

public class craftedArmor extends ArmorItemMod implements IconLargedByItemStack
{
    public static final String[] material = new String[]{"iron","gold","diamond","steel","copper","blood"};
    public static final String[] heads = new String[]{"helm","coif","pauldrons","cuirass","tassets","greaves","threads","sabatons"};
    private final static String[] parts = metallurgyArmor.parts;
    private final Icon[] iconByMetadataArray = new Icon[parts.length];

    public static final Float[] durability = new Float[]{10f,3.5f,16.5f,12.5f,4f,10f};
    public static final float[][] protection = new float[][] {
            {1, 3, 2.5f, 1},
            {1, 2.5f, 1.5f, 0.5f},
            {1.5f, 4, 3, 1.5f},
            {1f,3.5f,3f,1f},
            {1, 3, 2.5f, 1},
            {1, 3, 2.5f, 1},
    };
    public static final float[][] weight = new float[][] {
            {2.5f, 4, 3.5f, 2f},
            {2.5f, 4, 3.5f, 2f},
            {4f,5f,4.5f,3f},
            {4f,5.5f,4.5f,3.5f},
            {2.5f, 4, 3.5f, 2f},
            {2.5f, 4, 3.5f, 2f},
    };
    public static final String[][] traits = new String[][] {
            {"heattrap","cumbersome"},
            {"softness"},
            {"reckless"},
            {"temper"},
            {"anchor","brutal"},
            {"vessel","necrotic"},
    };

    public static HashMap<String, Integer> getAllTraitsWithLevels(ItemStack stack) {
        HashMap<String, Integer> traitLevels = new HashMap<>();

        int topMatIndex = getIntMaterial(stack, "Top");
        int bottomMatIndex = getIntMaterial(stack, "Bottom");
        java.util.function.Consumer<String> addTrait = (traitName) -> {
            materialTrait traitData = materialTraitHandler.getTrait(traitName);
            if (traitData == null) return;

            int currentLevel = traitLevels.getOrDefault(traitName, 0);
            int newLevel = currentLevel + 1;

            if (newLevel > traitData.getMaxLevel()) {
                newLevel = traitData.getMaxLevel();
            }

            traitLevels.put(traitName, newLevel);
        };

        if (topMatIndex >= 0 && topMatIndex < traits.length) {
            for (String traitName : traits[topMatIndex]) {
                addTrait.accept(traitName);
            }
        }

        if (bottomMatIndex >= 0 && bottomMatIndex < traits.length) {
            for (String traitName : traits[bottomMatIndex]) {
                addTrait.accept(traitName);
            }
        }

        return traitLevels;
    }

    public static int getTraitLevel(ItemStack stack, String trait) {
        HashMap<String, Integer> traitsWithLevels = getAllTraitsWithLevels(stack);
        if (traitsWithLevels.containsKey(trait)) {
            return traitsWithLevels.get(trait);
        }
        if (Objects.equals(trait, "temped") && hasMaterialTrait(stack,"temper")) {
            return 0;
        }
        if (Objects.equals(trait, "cumbersome") && isMatchingItem(stack)) {
            return 0;
        }
        return 0;
    }

    public static int getEquippedTraitLevel(EntityPlayer player, String trait) {
        int highestLevel = 0;

        for (ItemStack armorPiece : player.inventory.armorInventory) {
            if (armorPiece != null) {
                int level = getTraitLevel(armorPiece, trait);
                if (level > highestLevel) {
                    highestLevel = level;
                }
            }
        }

        return highestLevel;
    }

    static public String getPart(ItemStack stack, String part)
    {
        NBTTagCompound tag = stack.getTagCompound();
        if ( tag == null ) {
            return "iron_helm";
        }
        else
        {
            if ( tag.hasKey( part ) )
            {
                return tag.getString( part ).replace("item.metallurgyarmor.","");
            }
        }
        return "Unknown";
    }

    public static int getMaterialIndex(String name) {
        int index = 0;
        for (String mat : material) {
            if (mat.equals(name)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    static public String getTopPart(int armorType)
    {
        return armorType == 0 ? "helm" : armorType == 1 ? "pauldrons" : armorType == 2 ? "tassets" : armorType == 3 ? "threads" : "idk";
    }

    static public String getBottomPart(int armorType)
    {
        return armorType == 0 ? "coif" : armorType == 1 ? "cuirass" : armorType == 2 ? "greaves" : armorType == 3 ? "sabatons" : "idk";
    }

    static public String intType(int armorType)
    {
        return armorType == 0 ? "helmet" : armorType == 1 ? "chestplate" : armorType == 2 ? "leggings" : armorType == 3 ? "boots" : "idk";
    }

    private static String trans(String the_epik_string_is_coming) {
        return StatCollector.translateToLocalFormatted(the_epik_string_is_coming);
    }

    private static String superGetMaterial(String mat) {
        for (int i = 0; i < heads.length; i++) {
            String the = heads[i];
            mat = mat.replace(the,"");
        }
        return mat;
    }

    public static boolean isMatchingItem(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        if ( tag != null )
        {
            if ( tag.hasKey( "Top" ) && tag.hasKey( "Bottom" ) && superGetMaterial(tag.getString("Top")).equals(superGetMaterial(tag.getString("Bottom"))) )
            {
                return true;
            }
        }
        return false;
    }

    private static int getIntMaterial(ItemStack stack, String part) {
        String idk2 = superGetMaterial(getPart(stack,part));
        for (int i = 0; i < material.length; i++) {
            String the = material[i];
            if (the.equals(idk2)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean hasMaterialTrait(ItemStack stack, String traitName) {
        int intMatTop = getIntMaterial(stack, "Top");
        int intMatBottom = getIntMaterial(stack, "Bottom");
        for (String trait : traits[intMatTop]) {
            if (trait.equalsIgnoreCase(traitName)) return true;
        }
        for (String trait : traits[intMatBottom]) {
            if (trait.equalsIgnoreCase(traitName)) return true;
        }
        return false;
    }

    public static float getTrait(ItemStack stack, String part, int armorType) {
        int intMatTop = getIntMaterial(stack, "Top");
        int intMatBottom = getIntMaterial(stack, "Bottom");
        if (part.equalsIgnoreCase("weight")) {
            return weight[intMatTop][armorType] + weight[intMatBottom][armorType];
        }
        else if (part.equalsIgnoreCase("protection")) {
            float total = protection[intMatTop][armorType] + protection[intMatBottom][armorType];
            if (hasMaterialTrait(stack,"anchor")) {
                total++;
            }
            return total;
        }
        else if (part.equalsIgnoreCase("durability")) {
            return (durability[intMatTop]*16)+(durability[intMatBottom]*16);
        }
        return -1;
    }

    public static boolean damageArmor(ItemStack stack, int amount, EntityPlayer plr) {
        if (stack == null || amount <= 0) {
            return false;
        }

        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        craftedArmor thing = (craftedArmor)stack.getItem();
        int maxDur = (int) getTrait(stack,"durability",thing.armorType);

        NBTTagCompound tag = stack.getTagCompound();

        int current = tag.hasKey("CurrentDamage") ? tag.getInteger("CurrentDamage") : 0;

        current += amount;
        if (current > maxDur) {
            current = maxDur;
        }

        tag.setInteger("CurrentDamage", current);

//        int visualDamage = Math.round(((float) current / (float) maxDur) * stack.getMaxDamage());
//        if (visualDamage >= stack.getMaxDamage()) {
//            visualDamage = stack.getMaxDamage();
//        }
//        stack.setItemDamage(visualDamage);

        return current >= maxDur;
    }

    public craftedArmor(int iItemID, int iArmorType) {
        super(iItemID, ReMaterials.COPPERARMOR, 2, iArmorType, 0);
        this.setInfernalMaxEnchantmentCost(15);
        this.setInfernalMaxNumEnchants(1);
        this.damageReduceAmount = 0;
        setCreativeTab(null);
        hideFromEMI();
        setMaxDamage(0);
        setUnlocalizedName("crafted_armor");
    }

    @Override
    public String getItemDisplayName(ItemStack stack) {
        String topName = trans("tinker.material."+superGetMaterial(getPart(stack,"Top")));
        String bottomName = trans("tinker.material."+superGetMaterial(getPart(stack,"Bottom")));
        String armorName = trans("tinker.part."+intType(armorType));
        if (isMatchingItem(stack)) {
            return topName+" "+armorName;
        }
        return topName+"-"+bottomName+" "+armorName;
    }

    @Override
    public Multimap getItemAttributeModifiers() {
        Multimap attributeModifiers = super.getItemAttributeModifiers();
        attributeModifiers.clear();
        return attributeModifiers;
    }

    private static String intToRoman(int num) {
        String[] romans = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};
        if (num > 0 && num <= romans.length) {
            return romans[num - 1];
        }
        return String.valueOf(num);
    }


    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced) {
        super.addInformation(stack, player, info, advanced);
        if (stack.getTagCompound() != null) {
            NBTTagCompound tags = stack.getTagCompound();
            info.add("");
            double hungerCostMultiplierBase = (double)getTrait(stack,"weight",armorType) / 44.0;
            double hungerCostMultiplier = Math.round(hungerCostMultiplierBase * 100.0);
            String baseName = getString(hungerCostMultiplier /= 100.0);
            info.add("§9+"+trans(baseName)+" Hunger Cost");
            info.add("§9+"+getTrait(stack,"weight",armorType)+" Armor Weight");
            info.add("§9+"+getTrait(stack,"protection",armorType)+" Armor");
            info.add("");
            HashMap<String, Integer> traitsWithLevels = getAllTraitsWithLevels(stack);
            for (Map.Entry<String, Integer> entry : traitsWithLevels.entrySet()) {
                String trait = entry.getKey();
                int level = entry.getValue();

                materialTrait getTraitData = materialTraitHandler.getTrait(trait);
                if (getTraitData != null) {
                    String levelRoman = intToRoman(level);
                    info.add(getTraitData.getFormat() + trans("tinker.trait." + trait) + " (" + levelRoman + ")");
                }
            }

        }
    }

    private static @NotNull String getString(double magnitude) {
        String baseName = "attribute.name." + BTWAttributes.hungerCost.getAttributeUnlocalizedName();
        if (magnitude < 0.08) {
            baseName = baseName + ".tiny";
        }
        else if (magnitude < 0.16) {
            baseName = baseName + ".small";
        }
        else if (magnitude < 0.24) {
            baseName = baseName + ".medium";
        }
        else if (baseName.equals("attribute.name." + BTWAttributes.hungerCost.getAttributeUnlocalizedName())) {
            baseName = baseName + ".large";
        }
        return baseName;
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        for (int i = 0; i < parts.length; i++) {
            this.iconByMetadataArray[i] = register.registerIcon("refamished:metallurgy/crafted_armor/" + parts[i]);
        }
    }

    @Override
    public Icon getIcon(ItemStack stack, EntityPlayer player, int render) {
        //System.out.println("Name " + getPart(stack,"Top")+"_"+getTopPart(armorType));
        //System.out.println("Name " + getPart(stack,"Bottom")+"_"+getTopPart(armorType));
        if (render == 0)
        {
            return iconByMetadataArray[metallurgyArmor.getPartIndex(getPart(stack,"Top")+"_"+getTopPart(armorType))];
        }
        return iconByMetadataArray[metallurgyArmor.getPartIndex(getPart(stack,"Bottom")+"_"+getBottomPart(armorType))];
    }

    @Override
    public int getRenderers(ItemStack stack) {
        return 2;
    }
    @Override
    public String getWornTexturePrefix() {
        return "";
    }
    @Override
    public String getWornTextureDirectory() {
        return "refamished:textures/models/armor/crafted/";
    }

    public static String getWornTexturePrefix(ItemStack stack, int layer) {
        return layer == 0 ? superGetMaterial(getPart(stack,"Top"))+"_top" : superGetMaterial(getPart(stack,"Bottom"))+"_lower";
    }
}