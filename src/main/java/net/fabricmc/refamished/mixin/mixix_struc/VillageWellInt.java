package net.fabricmc.refamished.mixin.mixix_struc;

import net.minecraft.src.ComponentVillageWell;
import net.minecraft.src.StructureBoundingBox;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ComponentVillageWell.class)
public interface VillageWellInt {
    //@Accessor
    //protected void fillWithBlocks(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6, int par7, int par8, int par9, int par10, boolean par11)
}
