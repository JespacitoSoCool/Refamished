package net.fabricmc.refamished.mixin;


import btw.BTWMod;
import btw.item.BTWItems;
import btw.item.items.*;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.items.tools.*;
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
		Item.itemsList[Item.sugar.itemID] = new HighResolutionFoodItem(Item.sugar.itemID-256,1, 0.1f,false, "sugar")
				.setBuoyant().setBellowsBlowDistance(3).setIncineratedInCrucible().setFilterableProperties(8).setUnlocalizedName("sugar").setTextureName("sugar");
		Item.itemsList[Item.pickaxeStone.itemID] = new pickaxeDecorated(Item.pickaxeStone.itemID-256, EnumToolMaterial.STONE).setUnlocalizedName("pickaxeStone").setCreativeTab(CreativeTabs.tabTools);
		Item.itemsList[Item.axeStone.itemID] = new axeDecorated(Item.axeStone.itemID-256, EnumToolMaterial.STONE).setUnlocalizedName("hatchetStone").setCreativeTab(CreativeTabs.tabTools);
		Item.itemsList[Item.hoeStone.itemID] = new hoeDecorated(Item.hoeStone.itemID-256, EnumToolMaterial.STONE).setUnlocalizedName("hoeStone").setCreativeTab(CreativeTabs.tabTools);
		Item.itemsList[Item.shovelStone.itemID] = new shovelDecorated(Item.shovelStone.itemID-256).setCreativeTab(CreativeTabs.tabTools);

		Item.itemsList[Item.porkCooked.itemID] = new ItemFood(Item.porkCooked.itemID-256, 6, 0.25f, true).setUnlocalizedName("porkchopCooked").setTextureName("porkchop_cooked");
		Item.itemsList[Item.beefCooked.itemID] = new ItemFood(Item.beefCooked.itemID-256, 6, 0.25f, true).setUnlocalizedName("beefCooked").setTextureName("beef_cooked");
		Item.itemsList[Item.fishCooked.itemID] = new ItemFood(Item.fishCooked.itemID-256, 5, 0.25f, true).setUnlocalizedName("fishCooked").setTextureName("fish_cooked");
		Item.itemsList[Item.chickenCooked.itemID] = new ItemFood(Item.chickenCooked.itemID-256, 5, 0.25f, true).setUnlocalizedName("chickenCooked").setTextureName("chicken_cooked");
		Item.itemsList[Item.porkRaw.itemID] = new FoodItem(Item.porkRaw.itemID-256, 5, 0.25f, true, "porkchopRaw", true).setStandardFoodPoisoningEffect().setTextureName("porkchop_raw");
		Item.itemsList[Item.beefRaw.itemID] = new FoodItem(Item.beefRaw.itemID-256, 5, 0.25f, true, "beefRaw", true).setStandardFoodPoisoningEffect().setTextureName("beef_raw");
		Item.itemsList[Item.fishRaw.itemID] = new FoodItem(Item.fishRaw.itemID-256, 4, 0.25f, false, "fishRaw").setStandardFoodPoisoningEffect().setTextureName("fish_raw");
		Item.itemsList[Item.chickenRaw.itemID] = new FoodItem(Item.chickenRaw.itemID-256, 4, 0.25f, false, "chickenRaw").setStandardFoodPoisoningEffect().setTextureName("chicken_raw");
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
