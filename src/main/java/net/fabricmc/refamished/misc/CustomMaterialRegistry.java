package net.fabricmc.refamished.misc;

import net.minecraft.src.EnumToolMaterial;

import java.util.HashMap;
import java.util.Map;

public class CustomMaterialRegistry {
    public static final Map<String, EnumToolMaterial> customMaterials = new HashMap<>();

    public static EnumToolMaterial registerCustomMaterial(String name, int harvestLevel, int maxUses, float efficiency, float damage, int enchantability, int infernalCost, int infernalEnchants) {
        // Use reflection to create the EnumToolMaterial dynamically
        EnumToolMaterial material = createEnumToolMaterial(name, harvestLevel, maxUses, efficiency, damage, enchantability, infernalCost, infernalEnchants);
        customMaterials.put(name, material);
        return material;
    }

    public static EnumToolMaterial getCustomMaterial(String name) {
        return customMaterials.get(name);
    }

    public static EnumToolMaterial createEnumToolMaterial(String name, int harvestLevel, int maxUses, float efficiency, float damage, int enchantability, int infernalCost, int infernalEnchants) {
        try {
            // Dynamically create the EnumToolMaterial instance
            return EnumToolMaterial.class.getDeclaredConstructor(
                    String.class, int.class, int.class, int.class, float.class, float.class, int.class, int.class, int.class
            ).newInstance(name, harvestLevel, maxUses, efficiency, damage, enchantability, infernalCost, infernalEnchants);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create EnumToolMaterial: " + name, e);
        }
    }
}