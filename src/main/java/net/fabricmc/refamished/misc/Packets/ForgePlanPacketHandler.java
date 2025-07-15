package net.fabricmc.refamished.misc.Packets;

import btw.item.BTWItems;
import btw.item.items.SwordItem;
import btw.item.items.ToolItem;
import btw.network.packet.handler.CustomPacketHandler;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.entities.tiles.steelAnvilContainer;
import net.fabricmc.refamished.entities.tiles.steelAnvilTile;
import net.fabricmc.refamished.entities.tiles.stoneAnvilContainer;
import net.fabricmc.refamished.entities.tiles.stoneAnvilTile;
import net.fabricmc.refamished.itemsbase.hammer;
import net.fabricmc.refamished.itemsbase.machete;
import net.fabricmc.refamished.misc.ReMaterials;
import net.fabricmc.refamished.misc.Recipes.ForgingPlansRecipes;
import net.minecraft.src.*;
import org.lwjgl.Sys;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForgePlanPacketHandler implements CustomPacketHandler {

    @Override
    public void handleCustomPacket(Packet250CustomPayload packet, EntityPlayer player) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.data));

        try {
            int packetId = dis.readByte();

            if (packetId == 1) {
                DataInputStream input = new DataInputStream(new ByteArrayInputStream(packet.data));
                try {

                    if (player.openContainer instanceof stoneAnvilContainer) {
                        stoneAnvilContainer container = (stoneAnvilContainer) player.openContainer;
                        stoneAnvilTile anvil = container.tile;

                        int tool = dis.readInt();
                        int strength = dis.readInt();
                        anvil.interactAction(tool,strength*4,player,anvil);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (packetId == 2) {
                int recipeIndex = dis.readInt();
                int baseItemID = dis.readInt();
                //int baseStackSize = dis.readInt();
                //int baseDamage = dis.readInt();

                ItemStack base = new ItemStack(baseItemID, 1, 0);

                if (!(player.openContainer instanceof stoneAnvilContainer)) return;

                stoneAnvilContainer container = (stoneAnvilContainer) player.openContainer;
                stoneAnvilTile tile = container.tile;

                int availableSlot = -1;
                for (int i = 1; i < tile.sizeInv(); i++) {
                    if (tile.getStackInSlot(i) == null || tile.getStackInSlot(i).stackSize <= 0) {
                        availableSlot = i;
                        break;
                    }
                }

                if (availableSlot == -1) {
                    return;
                }

                List<ItemStack> inputs = new ArrayList<>();
                for (int i = 0; i < tile.sizeInv(); i++) {
                    ItemStack slot = tile.getStackInSlot(i);
                    if (slot != null) inputs.add(slot.copy());
                }

                List<ForgingPlansRecipes.RecipeEntry> matches = ForgingPlansRecipes.getInstance().getMatchingRecipes(inputs);
                if (recipeIndex < 0 || recipeIndex >= matches.size()) return;

                ForgingPlansRecipes.RecipeEntry selected = matches.get(recipeIndex);
                ItemStack expectedBase = selected.inputs.get(0);

                //if (!baseMatches(base, expectedBase)) return;

                // Validate player has all required items
                if (!hasAllRequiredInputs(tile, selected.inputs)) {
                    System.out.println("AHHHHHHHH");
                    return;
                }

                // Consume required items
                consumeRequiredInputs(tile, selected.inputs);

                // Give the planified output
                int metaPlan = getIngotMetaPlan(baseItemID);
                if (metaPlan == 16) {
                    metaPlan = getIngotMetaPlan(selected.output.itemID);
                }
                ItemStack plan = new ItemStack(RefamishedItems.forgingPlan,1,metaPlan);
                NBTTagCompound tag = new NBTTagCompound();
                Random rand = player.rand;

                int totalReq = selected.forgeTime;
                tag.setString("PlanOutput", selected.output.getUnlocalizedName());
                tag.setInteger("ShatterMax", selected.forgeTime);
                tag.setInteger("Mistake", 1);
                tag.setInteger("Metadata", selected.output.getItemDamage());

                int[] getDefaultData = splitRandomly(rand,totalReq);

                tag.setInteger("Blow", getDefaultData[0]);
                tag.setInteger("Cut", getDefaultData[1]);
                tag.setInteger("Grip", getDefaultData[2]);

                int[] getReq = splitRandomly(rand,totalReq);

                tag.setInteger("BlowReq", getReq[0]);
                tag.setInteger("CutReq", getReq[1]);
                tag.setInteger("GripReq", getReq[2]);
                plan.setTagCompound(tag);

                tile.setInventorySlotContents(availableSlot, plan);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[] splitRandomly(Random rand, int totalReq) {
        float r1 = rand.nextFloat();
        float r2 = rand.nextFloat();
        float r3 = rand.nextFloat();
        float total = r1 + r2 + r3;

        int blow = Math.round((r1 / total) * totalReq);
        int cut = Math.round((r2 / total) * totalReq);
        int grip = totalReq - blow - cut;
        return new int[]{blow, cut, grip};
    }

    private int getIngotMetaPlan(int id) {
        final int copper = RefamishedItems.copperIngot.itemID;
        final int iron = Item.ingotIron.itemID;
        final int gold = Item.ingotGold.itemID;
        final int cobalt = RefamishedItems.cobaltzureIngot.itemID;
        final int gilded = RefamishedItems.gildedIngot.itemID;
        final int diamond = BTWItems.diamondIngot.itemID;
        final int copperNugget = RefamishedItems.copperNugget.itemID;
        final int ironNugget = BTWItems.ironNugget.itemID;
        final int goldNugget = Item.goldNugget.itemID;
        final int gildedNugget = RefamishedItems.gildedNugget.itemID;
        Item itemThing = Item.itemsList[id];
        if (itemThing instanceof SwordItem swordItem) {
            String material = swordItem.getToolMaterialName();
            return material.equals(ReMaterials.COPPER.name()) ? 0 : material.equals(EnumToolMaterial.IRON.name()) ? 1 :
                    material.equals(EnumToolMaterial.GOLD.name()) ? 2 : material.equals(ReMaterials.COBALTZURE.name()) ? 3 :
                            material.equals(ReMaterials.GILDEDIRON.name()) ? 4 : material.equals(EnumToolMaterial.EMERALD.name()) ? 5
                                    : material.equals(ReMaterials.DIAMONDTIP.name()) ? 1 : 0;
        }
        else if (itemThing instanceof ToolItem toolItem) {
            EnumToolMaterial material = toolItem.toolMaterial;
            return material == ReMaterials.COPPER ? 0 : material == EnumToolMaterial.IRON ? 1 :
                    material == EnumToolMaterial.GOLD ? 2 : material == ReMaterials.COBALTZURE ? 3 :
                            material == ReMaterials.GILDEDIRON ? 4 : material == EnumToolMaterial.EMERALD ? 5 :
                                    material == ReMaterials.DIAMONDTIP ? 1 : 0;
        }
        else if (itemThing instanceof hammer hammerItem) {
            EnumToolMaterial material = hammerItem.m_eMaterial;
            return material == ReMaterials.COPPER ? 0 : material == EnumToolMaterial.IRON ? 1 :
                    material == EnumToolMaterial.GOLD ? 2 : material == ReMaterials.COBALTZURE ? 3 :
                            material == ReMaterials.GILDEDIRON ? 4 : material == EnumToolMaterial.EMERALD ? 5 :
                                    material == ReMaterials.DIAMONDTIP ? 1 : 0;
        }
        else if (itemThing instanceof machete macheteItem) {
            EnumToolMaterial material = macheteItem.m_eMaterial;
            return material == ReMaterials.COPPER ? 0 : material == EnumToolMaterial.IRON ? 1 :
                    material == EnumToolMaterial.GOLD ? 2 : material == ReMaterials.COBALTZURE ? 3 :
                            material == ReMaterials.GILDEDIRON ? 4 : material == EnumToolMaterial.EMERALD ? 5 :
                                    material == ReMaterials.DIAMONDTIP ? 1 : 0;
        }
        else if (itemThing instanceof ItemShears shearItem) {
            return shearItem == RefamishedItems.copperShears ? 0 : shearItem == Item.shears ? 1 :
                    shearItem == RefamishedItems.gildedShears ? 4 : shearItem == BTWItems.diamondShears ? 5 : 0;
        }
        else if (itemThing instanceof ItemShears shearItem) {
            return shearItem == RefamishedItems.copperShears ? 0 : shearItem == Item.shears ? 1 :
                    shearItem == RefamishedItems.gildedShears ? 4 : shearItem == BTWItems.diamondShears ? 5 : 0;
        }
        else {
            String un = itemThing.getUnlocalizedName();
            return id == copper ? 0 : id == iron ? 1 : id == gold ? 2 : id == cobalt ? 3 : id == gilded ? 4 : id == diamond ? 5 :
            id == copperNugget ? 0 : id == ironNugget ? 1 : id == goldNugget ? 2 : id == gildedNugget ? 4 :
            un.contains("iron") ? 1 : un.contains("gold") ? 2 : un.contains("cobalt") ? 3 : un.contains("gilded") ? 4 : un.contains("diamond") ? 5
            : un.contains("steel") ? 6 : 16;
        }
//        return id == copper ? 0 : id == iron ? 1 : id == gold ? 2 : id == cobalt ? 3 : id == gilded ? 4 : id == diamond ? 5 :
//                id == copperNugget ? 0 : id == ironNugget ? 1 : id == goldNugget ? 2 : id == gildedNugget ? 4 : 16;
    }

    private boolean baseMatches(ItemStack a, ItemStack b) {
        return a.itemID == b.itemID && a.getItemDamage() == b.getItemDamage();
    }

    private boolean hasAllRequiredInputs(IInventory inv, List<ItemStack> required) {
        List<ItemStack> invCopy = new ArrayList<>();
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack s = inv.getStackInSlot(i);
            if (s != null) invCopy.add(s.copy());
        }

        for (ItemStack req : required) {
            int needed = req.stackSize;
            for (ItemStack stack : invCopy) {
                if (stack.itemID == req.itemID && stack.getItemDamage() == req.getItemDamage()) {
                    int taken = Math.min(stack.stackSize, needed);
                    stack.stackSize -= taken;
                    needed -= taken;
                    if (needed <= 0) break;
                }
            }
            if (needed > 0) return false; // not enough of this input
        }
        return true;
    }

    private void consumeRequiredInputs(IInventory inv, List<ItemStack> required) {
        for (ItemStack req : required) {
            int remaining = req.stackSize;
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack s = inv.getStackInSlot(i);
                if (s != null && s.itemID == req.itemID && s.getItemDamage() == req.getItemDamage()) {
                    int used = Math.min(s.stackSize, remaining);
                    s.stackSize -= used;
                    remaining -= used;
                    if (s.stackSize <= 0) {
                        inv.setInventorySlotContents(i, null);
                    }
                    if (remaining <= 0) break;
                }
            }
        }
    }
}
