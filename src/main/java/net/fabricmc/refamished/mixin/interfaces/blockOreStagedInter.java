package net.fabricmc.refamished.mixin.interfaces;

import btw.block.blocks.OreBlockStaged;
import net.minecraft.src.BlockFire;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(OreBlockStaged.class)
public interface blockOreStagedInter {
    @Invoker("getConversionLevelForTool")
    int getCLT(ItemStack stack, World world, int i, int j, int k);
    @Invoker("ejectItemsOnStonePickConversion")
    void ejectStone(ItemStack stack, World world, int i, int j, int k, int iOldMetadata, int iFromSide);
    @Invoker("ejectItemsOnGoodPickConversion")
    void ejectGood(ItemStack stack, World world, int i, int j, int k, int iOldMetadata, int iFromSide);
}
