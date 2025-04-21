package net.fabricmc.refamished.misc;

import btw.util.sounds.AddonSoundRegistryEntry;
import net.fabricmc.refamished.interfaces.EnumArmorMaterialInterface;
import net.fabricmc.refamished.interfaces.EnumToolMaterialInterface;
import net.minecraft.src.EnumArmorMaterial;
import net.minecraft.src.EnumToolMaterial;

public class RefamishedSoundManager {
    public static final AddonSoundRegistryEntry CROSSBOW_LOAD = new AddonSoundRegistryEntry("refamished:item.crossbow.Crossbow_loading_end");
    public static final AddonSoundRegistryEntry CROSSBOW_LOAD_END = new AddonSoundRegistryEntry("refamished:item.crossbow.Crossbow_loading_end");
    public static final AddonSoundRegistryEntry CROSSBOW_SHOOT = new AddonSoundRegistryEntry("refamished:item.crossbow.Crossbow_shoot",3);
}
