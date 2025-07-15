package net.fabricmc.refamished.entities.tiles;

import btw.block.tileentity.CookingVesselTileEntity;
import btw.block.tileentity.TileEntityDataPacketHandler;
import btw.inventory.container.InventoryContainer;
import btw.inventory.util.InventoryUtils;
import btw.item.BTWItems;
import btw.item.items.*;
import btw.item.util.ItemUtils;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.blocks.CokeOven;
import net.fabricmc.refamished.items.tools.ArmorItemCopper;
import net.fabricmc.refamished.items.tools.ArmorItemDiamondRefDoSomething;
import net.fabricmc.refamished.items.tools.ArmorItemSteelButReal;
import net.fabricmc.refamished.itemsbase.hammer;
import net.fabricmc.refamished.misc.ReMaterials;
import net.fabricmc.refamished.misc.Recipes.CokeOvenSmeltingRecipes;
import net.fabricmc.refamished.misc.Recipes.ForgingPlansRecipes;
import net.fabricmc.refamished.misc.Recipes.SmithingRecipes;
import net.fabricmc.refamished.misc.RefamishedSoundManager;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.fabricmc.refamished.quality.ToolQuality;
import net.minecraft.src.*;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class stoneAnvilTile extends TileEntity implements IInventory {
    public ItemStack[] inventory = new ItemStack[sizeInv()];

    public int sizeInv() {
        return 6;
    }
    public int getBonusLevel() {
        return 1;
    }

    public static int getShatter(ItemStack stack) {
        if (stack == null || !stack.hasTagCompound()) return 0;
        return stack.getTagCompound().getInteger("Shatter");
    }

    public static void setShatter(ItemStack stack, int value) {
        if (stack == null) return;
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger("Shatter", value);
    }

    public static void addShatter(ItemStack stack, int amount) {
        if (stack == null) return;
        int current = getShatter(stack);
        setShatter(stack, current + amount);
    }

    public static int getTag(ItemStack stack, String tag) {
        if (stack == null || !stack.hasTagCompound()) return 0;
        return stack.getTagCompound().getInteger(tag);
    }

    public static void setTag(ItemStack stack, int value, String tag) {
        if (stack == null) return;
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger(tag, value);
    }

    public static void addTag(ItemStack stack, int amount, String tag) {
        if (stack == null) return;
        int current = getTag(stack,tag);
        setTag(stack, current + amount,tag);
    }

    public void applyMinigameResult(boolean success, EntityPlayer player, stoneAnvilContainer gui, stoneAnvilContainer contain, stoneAnvilTile anvil) {
        ItemStack hammerStack = contain.getSlot(0).getStack();
        if (!isItemValidForSlot(0, hammerStack)) return;

        hammer hammerData = (hammer) hammerStack.getItem();
        World world = player.worldObj;

        int recipeTypeId = -5;
        int ingotSlot = -1;

        NBTTagCompound planOutput = null;
        for (int i = 1; i <= inventory.length; i++) {
            ItemStack ingot = contain.getSlot(i).getStack();
            if (ingot != null && SmithingRecipes.getInstance().isValidRecipe(ingot)) {
                ingotSlot = i;
                recipeTypeId = 1;
                break;
            }
            /*
                else if (ingot != null && ingot.getTagCompound() != null && ingot.getTagCompound().hasKey("PlanOutput")) {
                planOutput = ingot.getTagCompound();
                ingotSlot = i;
                recipeTypeId = 2;
                break;
            }
             */
            else if (ingot != null) {
                if (getBonusLevel() == 1 && ingot.getItem() instanceof ToolItem toolItem && ingot.getItemDamage() < ingot.getMaxDamage()-1 && (ToolQuality.toolHaveQualities(ingot.getItem()))) {
                    if (toolItem.toolMaterial != EnumToolMaterial.STONE) {continue;}
                    world.playSoundAtEntity(player, RefamishedSoundManager.METAL_HIT.sound(), 0.75f, 1.1f + world.rand.nextFloat() * 0.1f);
                    if (!ingot.hasTagCompound()) {
                        ingot.setTagCompound(new NBTTagCompound());
                    }
                    ToolQuality quality = ToolQuality.getRandomQuality(1);
                    ingot.getTagCompound().setString("ToolQuality", quality.getName());
                    hammerStack.damageItem(1,player);
                    if (hammerStack.getItemDamage() >= hammerStack.getMaxDamage()) {
                        contain.getSlot(0).putStack(null);
                    }
                    ingot.damageItem(1,player);
                    if (ingot.getItemDamage() >= ingot.getMaxDamage()) {
                        contain.getSlot(i).putStack(null);
                    }
                    return;
                } else if (getBonusLevel() != 1 && ((ToolQuality.toolHaveQualities(ingot.getItem())) || (ArmorQuality.armorHaveQualities(ingot.getItem())) )) {
                    Item required = materialForTool(ingot.getItem());
                    if (required == null) return;

                    boolean hasMaterial = false;
                    int materialSlot = -1;

                    for (int j = 1; j < inventory.length; j++) {
                        if (j == i) continue;
                        ItemStack maybeMaterial = contain.getSlot(j).getStack();
                        if (maybeMaterial != null && maybeMaterial.getItem() == required) {
                            hasMaterial = true;
                            materialSlot = j;
                            break;
                        }
                    }

                    if (!hasMaterial) return;

                    ItemStack matStack = contain.getSlot(materialSlot).getStack();
                    matStack.stackSize--;
                    if (matStack.stackSize <= 0) {
                        contain.getSlot(materialSlot).putStack(null);
                    }

                    world.playSoundAtEntity(player, RefamishedSoundManager.METAL_HIT.sound(), 0.75f, 1.1f + world.rand.nextFloat() * 0.1f);

                    if (!ingot.hasTagCompound()) {
                        ingot.setTagCompound(new NBTTagCompound());
                    }

                    if (ToolQuality.toolHaveQualities(ingot.getItem())) {
                        ToolQuality quality = ToolQuality.getRandomQualityReroll(getBonusLevel());
                        ingot.getTagCompound().setString("ToolQuality", quality.getName());
                    }
                    else if (ArmorQuality.armorHaveQualities(ingot.getItem())) {
                        ArmorQuality quality = ArmorQuality.getRandomQualityReroll(getBonusLevel());
                        ingot.getTagCompound().setString("ToolQuality", quality.getName());
                    }

                    hammerStack.damageItem(1, player);
                    if (hammerStack.getItemDamage() >= hammerStack.getMaxDamage()) {
                        contain.getSlot(0).putStack(null);
                    }

                    return;
                }
            }
        }
        if (ingotSlot == -1) return;

        ItemStack ingot = contain.getSlot(ingotSlot).getStack();

        List<Integer> outputSlots = new ArrayList<>();
        for (int i = 1; i <= inventory.length; i++) {
            if (i == ingotSlot) continue;
            if (contain.getSlot(i).getStack() == null) {
                outputSlots.add(i);
            }
        }

        if (recipeTypeId == 1) {
            world.playSoundAtEntity(player, RefamishedSoundManager.METAL_HIT.sound(), 0.75f, 1.1f + world.rand.nextFloat() * 0.1f);
            addShatter(ingot, success ? hammerData.shatter() : hammerData.missShatter());
            hammerStack.damageItem(1,player);
            if (hammerStack.getItemDamage() >= hammerStack.getMaxDamage() || hammerStack.stackSize <= 0) {
                contain.getSlot(0).putStack(null);
            }

            SmithingRecipes.RecipeEntry recipe = SmithingRecipes.getInstance().getMatchingRecipe(ingot);
            if (recipe == null) return;
            ItemStack[] outputs = recipe.firstOutput;
            if (outputSlots.size() < outputs.length) return;

            int shatter = getShatter(ingot);
            if (shatter >= recipe.shatterAmount) {
                ingot.stackSize--;
                if (ingot.stackSize <= 0) {
                    contain.getSlot(ingotSlot).putStack(null);
                }

                addShatter(ingot, -recipe.shatterAmount);
                for (ItemStack output : outputs) {
                    int remaining = output.stackSize;

                    for (int i = 1; i <= inventory.length; i++) {
                        if (i == ingotSlot || contain.getSlot(i).getStack() == null) continue;

                        ItemStack existingStack = contain.getSlot(i).getStack();
                        int cur = output.itemID;
                        int existing = contain.getSlot(i).getStack().itemID;
                        int curMet = output.getItemDamage();
                        int existingMet = contain.getSlot(i).getStack().getItemDamage();
                        if (existing == cur && existingMet == curMet) {

                            int max = existingStack.getMaxStackSize();
                            int canAdd = Math.min(remaining, max - existingStack.stackSize);

                            if (canAdd > 0) {
                                existingStack.stackSize += canAdd;
                                remaining -= canAdd;
                            }

                            if (remaining <= 0) break;
                        }
                    }

                    for (int i = 1; i <= inventory.length; i++) {
                        if (i == ingotSlot) continue;
                        if (contain.getSlot(i).getStack() == null) {
                            int max = output.getMaxStackSize();
                            int toPlace = Math.min(remaining, max);
                            if (toPlace > 0) {
                                ItemStack newStack = output.copy();
                                newStack.stackSize = toPlace;
                                contain.getSlot(i).putStack(newStack);
                                remaining -= toPlace;
                            }

                            if (remaining <= 0) break;
                        }
                    }

                    if (remaining > 0) {
                        ItemStack drop = output.copy();
                        drop.stackSize = remaining;
                        player.dropPlayerItemWithRandomChoice(drop, false);
                    }
                }

            }
        }


        this.onInventoryChanged();
    }

    public void interactAction(int actionID, int strength, EntityPlayer player, stoneAnvilTile anvil) {
        NBTTagCompound planTags = null;
        int planSlot = getForgingSlot(anvil);
        ItemStack plan = getForgingItemStack(anvil);
        ItemStack hammerStack = anvil.getStackInSlot(0);
        World world = player.worldObj;
        System.out.println("Tool : "+actionID);
        System.out.println("Strength : "+strength);
        if (plan != null) {
            world.playSoundAtEntity(player, RefamishedSoundManager.METAL_HIT.sound(), 0.75f, 1.1f + world.rand.nextFloat() * 0.1f);
            hammerStack.damageItem(1,player);
            if (hammerStack.getItemDamage() >= hammerStack.getMaxDamage() || hammerStack.stackSize <= 0) {
                anvil.setInventorySlotContents(0,null);
            }
            planTags = plan.getTagCompound();
            for (int i = 0; i < 3; i++) {
                String name = i == 0 ? "Blow" : i == 1 ? "Cut" : "Grip";
                boolean rightTool = (actionID-1) == i;
                int strengthRe = rightTool ? strength : -strength/2;
                addTag(plan,strengthRe,name);
            }
            int shatterMax = getTag(plan,"ShatterMax");
            boolean hitBlow = isWithinFlagHitbox(getTag(plan,"Blow"), getTag(plan,"BlowReq"), shatterMax);
            boolean hitCut  = isWithinFlagHitbox(getTag(plan,"Cut"), getTag(plan,"CutReq"), shatterMax);
            boolean hitGrip = isWithinFlagHitbox(getTag(plan,"Grip"), getTag(plan,"GripReq"), shatterMax);
            if (hitBlow && hitCut && hitGrip) {
                //System.out.println("YEEEEE");
                int slot = getFreeSlot(anvil);
                //System.out.println("1");
                if (slot != -1) {
                    //System.out.println("2");
                    ForgingPlansRecipes.RecipeEntry recipe = ForgingPlansRecipes.getInstance().getMatchingRecipes(planTags.getString("PlanOutput"));
                    if (recipe == null) {
                        return;
                    }
                    //System.out.println("3");
                    plan.stackSize--;
                    if (plan.stackSize <= 0) {
                        anvil.setInventorySlotContents(planSlot,null);
                    }
                    ItemStack outputs = recipe.output.copy();
                    anvil.setInventorySlotContents(slot,outputs);
                    Item.itemsList[outputs.itemID].onCreated(outputs,world,player);
                }
            }
        }
        this.onInventoryChanged();
    }

    public static boolean isWithinFlagHitbox(int current, int required, int shatterMax) {
        if (shatterMax <= 0 || required < 0 || current < 0) {
            System.out.println("what");
            return false;
        }

        float currentNorm = (float) current / shatterMax;
        float requiredNorm = (float) required / shatterMax;

        float flagNormWidth = 8f / 59f;
        float halfFlagNorm = flagNormWidth / 2f;

        return currentNorm >= (requiredNorm - halfFlagNorm) && currentNorm <= (requiredNorm + halfFlagNorm);
    }

    public Item materialForTool(Item tool) {
        if (tool instanceof SwordItem ite) {
            return ite.getToolMaterialName() == EnumToolMaterial.IRON.name() ? BTWItems.ironNugget :
                    ite.getToolMaterialName() == EnumToolMaterial.GOLD.name() ? Item.goldNugget :
                            ite.getToolMaterialName() == EnumToolMaterial.EMERALD.name() ? RefamishedItems.steelIngot :
                                    ite.getToolMaterialName() == EnumToolMaterial.SOULFORGED_STEEL.name() ? BTWItems.steelNugget :
                                            ite.getToolMaterialName() == ReMaterials.DIAMONDTIP.name() ? BTWItems.ironNugget :
                                                    ite.getToolMaterialName() == ReMaterials.GILDEDIRON.name() ? RefamishedItems.gildedNugget :
                                                            ite.getToolMaterialName() == ReMaterials.COPPER.name() ? RefamishedItems.copperNugget :
                                                                    null;
        }
        else if (tool instanceof ToolItem ite) {
            return ite.toolMaterial == EnumToolMaterial.IRON ? BTWItems.ironNugget :
                    ite.toolMaterial == EnumToolMaterial.GOLD ? Item.goldNugget :
                            ite.toolMaterial == EnumToolMaterial.EMERALD ? RefamishedItems.steelIngot :
                                    ite.toolMaterial == EnumToolMaterial.SOULFORGED_STEEL ? BTWItems.steelNugget :
                                            ite.toolMaterial == ReMaterials.DIAMONDTIP ? BTWItems.ironNugget :
                                                    ite.toolMaterial == ReMaterials.GILDEDIRON ? RefamishedItems.gildedNugget :
                                                            ite.toolMaterial == ReMaterials.COPPER ? RefamishedItems.copperNugget :
                                                                    null;
        }
        else if (tool instanceof ArmorItem ite) {
            return ite instanceof ArmorItemIron ? BTWItems.ironNugget : ite instanceof ArmorItemGold ? Item.goldNugget :
                    ite instanceof ArmorItemDiamondRefDoSomething ? RefamishedItems.steelIngot : ite instanceof ArmorItemSteel ? BTWItems.soulforgedSteelIngot :
                            ite instanceof ArmorItemLeather ? BTWItems.cutLeather : ite instanceof ArmorItemTanned ? BTWItems.cutTannedLeather :
                                    ite instanceof ArmorItemPadded ? BTWItems.padding : ite instanceof ArmorItemSteelButReal ? RefamishedItems.steelIngot :
                                            ite instanceof ArmorItemChain ? BTWItems.mail : ite instanceof ArmorItemCopper ? RefamishedItems.copperNugget : null;
        }
        return null;
    }

    public int materialAmountForTool(Item tool) {
        if (tool instanceof SwordItem ite) {
            return ite.getToolMaterialName() == EnumToolMaterial.EMERALD.name() ? 1 : 2;
        } else if (tool instanceof ToolItem ite) {
            return ite.toolMaterial == EnumToolMaterial.EMERALD ? 1 : 2;
        }
        return 2;
    }

    public static boolean isForging(stoneAnvilTile container) {
        ItemStack get;
        for (int j = 1; j < container.inventory.length; j++) {
            ItemStack maybeMaterial = container.inventory[j];
            if (maybeMaterial != null && maybeMaterial.getTagCompound() != null && maybeMaterial.getTagCompound().hasKey("PlanOutput")) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack getForgingItemStack(stoneAnvilTile container) {
        ItemStack get;
        for (int j = 1; j < container.inventory.length; j++) {
            ItemStack maybeMaterial = container.inventory[j];
            if (maybeMaterial != null && maybeMaterial.getTagCompound() != null && maybeMaterial.getTagCompound().hasKey("PlanOutput")) {
                return maybeMaterial;
            }
        }
        return null;
    }

    public static int getForgingSlot(stoneAnvilTile container) {
        for (int j = 1; j < container.inventory.length; j++) {
            ItemStack maybeMaterial = container.inventory[j];
            if (maybeMaterial != null && maybeMaterial.getTagCompound() != null && maybeMaterial.getTagCompound().hasKey("PlanOutput")) {
                return j;
            }
        }
        return -1;
    }

    public static int getFreeSlot(stoneAnvilTile container) {
        for (int j = 1; j < container.inventory.length; j++) {
            ItemStack maybeMaterial = container.inventory[j];
            if (maybeMaterial == null || maybeMaterial.stackSize <= 0) {
                return j;
            }
        }
        return -1;
    }

    @Override
    public int getSizeInventory() {
        return sizeInv();
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory[i];
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack) {
        inventory[i] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public ItemStack decrStackSize(int i, int count) {
        return InventoryUtils.decreaseStackSize(this, i, count);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return null;
    }

    @Override
    public String getInvName() {
        return "container.stoneAnvil";
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this &&
                player.getDistanceSq((double) this.xCoord + 0.5, (double) this.yCoord + 0.5, (double) this.zCoord + 0.5) <= 64.0;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void openChest() {

    }

    @Override
    public void closeChest() {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        if (i == 0 && itemStack != null) {
            return itemStack.getItem() instanceof hammer;
        }
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagList items = new NBTTagList();
        for (int i = 0; i < this.getSizeInventory(); i++) {
            ItemStack stack = this.getStackInSlot(i);
            if (stack != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte)i);
                stack.writeToNBT(itemTag);
                items.appendTag(itemTag);
            }
        }
        compound.setTag("Items", items);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagList items = compound.getTagList("Items");
        for (int i = 0; i < items.tagCount(); i++) {
            NBTTagCompound itemTag = (NBTTagCompound) items.tagAt(i);
            int slot = itemTag.getByte("Slot") & 255;
            if (slot >= 0 && slot < this.getSizeInventory()) {
                this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemTag));
            }
        }
    }

}