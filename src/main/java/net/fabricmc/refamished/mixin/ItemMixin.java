package net.fabricmc.refamished.mixin;


import btw.item.items.ClubItem;
import btw.item.items.HighResolutionFoodItem;
import btw.item.items.SwordItem;
import btw.item.items.ToolItem;
import net.fabricmc.refamished.items.tools.axeDecorated;
import net.fabricmc.refamished.items.tools.hoeDecorated;
import net.fabricmc.refamished.items.tools.pickaxeDecorated;
import net.fabricmc.refamished.items.tools.shovelDecorated;
import net.fabricmc.refamished.itemsbase.blade;
import net.fabricmc.refamished.itemsbase.machete;
import net.fabricmc.refamished.quality.*;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Item.class)
public abstract class ItemMixin {

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void replaceItem(CallbackInfo ci) {
		Item.itemsList[Item.sugar.itemID] = new HighResolutionFoodItem(Item.sugar.itemID,1, 0.1f,false, "sugar")
				.setBuoyant().setBellowsBlowDistance(3).setIncineratedInCrucible().setFilterableProperties(8).setUnlocalizedName("sugar").setTextureName("sugar");
		Item.itemsList[Item.pickaxeStone.itemID] = new pickaxeDecorated(Item.pickaxeStone.itemID, EnumToolMaterial.STONE).setUnlocalizedName("pickaxeStone").setCreativeTab(CreativeTabs.tabTools);
		Item.itemsList[Item.axeStone.itemID] = new axeDecorated(Item.axeStone.itemID, EnumToolMaterial.STONE).setUnlocalizedName("hatchetStone").setCreativeTab(CreativeTabs.tabTools);
		Item.itemsList[Item.hoeStone.itemID] = new hoeDecorated(Item.hoeStone.itemID, EnumToolMaterial.STONE).setUnlocalizedName("hoeStone").setCreativeTab(CreativeTabs.tabTools);
		Item.itemsList[Item.shovelStone.itemID] = new shovelDecorated(Item.shovelStone.itemID).setCreativeTab(CreativeTabs.tabTools);
	}

	@Inject(method = "onCreated", at = @At("HEAD"))
	private void assignToolQuality(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, CallbackInfo ci) {
		if (par1ItemStack.getItem() instanceof ItemSword)
		{
			int maxDurability = par1ItemStack.getMaxDamage();
			int experience = (int) (maxDurability/7);

			SkillManager.addExperience(par3EntityPlayer,"Artisanry", experience);
			if (!par2World.isRemote)
			{
				if (!par1ItemStack.hasTagCompound()) {
					par1ItemStack.setTagCompound(new NBTTagCompound());
				}
				ToolQuality quality = ToolQuality.getRandomQuality();
				par1ItemStack.getTagCompound().setString("ToolQuality", quality.getName());
			}
		}
	}

	@Inject(method = "hitEntity", at = @At("HEAD"))
	private void applyToolQualityDamage(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker, CallbackInfoReturnable<Boolean> cir) {
		// Fetch ToolQuality from the ItemStack
		if (stack.getItem() instanceof SwordItem) {
			ToolQuality quality = ToolQualityHelper.getToolQuality(stack);
			if (quality != null) {
				float bonusDamage = ToolQualityHelper.getAttackDamageBonus(quality);

				target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), bonusDamage);
			}
		}
	}

	@Inject(method = "addInformation", at = @At("HEAD"))
	private  void addQualityInfo(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4, CallbackInfo ci) {
		if (par1ItemStack.getItem() instanceof ToolItem || par1ItemStack.getItem() instanceof ItemSword || par1ItemStack.getItem() instanceof ClubItem
				|| par1ItemStack.getItem() instanceof machete || par1ItemStack.getItem() instanceof blade)
		{
			ToolQuality quality = ToolQualityHelper.getToolQuality(par1ItemStack);

			par3List.add( "Quality: " + quality.getColor() + quality.getName());

			List<String> descriptions = ToolQualityEffectHelper.generateDescription(quality);
			par3List.addAll(descriptions);
		}
		else if (par1ItemStack.getItem() instanceof ItemArmor)
		{
			ArmorQuality quality = ArmorQualityHelper.getArmorQuality(par1ItemStack);

			par3List.add( "Quality: " + quality.getColor() + quality.getName());

			List<String> descriptions = ArmorQualityEffectHelper.generateDescription(quality);
			par3List.addAll(descriptions);
		}
	}
}
