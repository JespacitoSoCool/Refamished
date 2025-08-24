package net.fabricmc.refamished.misc;

import btw.block.BTWBlocks;
import btw.entity.mob.villager.trade.TradeItem;
import btw.entity.mob.villager.trade.TradeList;
import btw.entity.mob.villager.trade.TradeProvider;
import btw.item.BTWItems;
import btw.util.color.Color;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.items.others.qualityBlueprints;
import net.minecraft.src.Block;
import net.minecraft.src.Enchantment;
import net.minecraft.src.Entity;
import net.minecraft.src.Item;

public class VillagerTrades {

    private static final int FARMER = 0;
    private static final int LIBRARIAN = 1;
    private static final int PRIEST = 2;
    private static final int BLACKSMITH = 3;
    private static final int BUTCHER = 4;

    public static void addVillagerTrades() {
        //VillagerTrades.addFarmerTrades();
    }

    private static void addFarmerTrades() {
        /*
        TradeProvider.getBuilder().profession(0).level(1).buy().item(RefamishedItems.berries.itemID, 0).itemCount(5, 7).defaultTrade().addToTradeList();
        TradeProvider.getBuilder().profession(0).level(1).buy().item(RefamishedItems.berries.itemID, 1).itemCount(5, 7).defaultTrade().addToTradeList();
        TradeProvider.getBuilder().profession(0).level(1).buy().item(RefamishedItems.berries.itemID, 2).itemCount(5, 7).defaultTrade().addToTradeList();
        TradeProvider.getBuilder().profession(0).level(1).buy().item(RefamishedItems.berries.itemID, 3).itemCount(5, 7).defaultTrade().addToTradeList();
        TradeProvider.getBuilder().profession(0).level(1).buy().item(RefamishedItems.rib.itemID).itemCount(3, 6).defaultTrade().addToTradeList();

        TradeProvider.getBuilder().profession(0).level(2).buy().item(RefamishedItems.clay_mud.itemID).itemCount(12, 16).defaultTrade().addToTradeList();
        TradeProvider.getBuilder().profession(0).level(2).buy().item(RefamishedItems.pie_cooked.itemID, 0).itemCount(2,3).defaultTrade().addToTradeList();
        TradeProvider.getBuilder().profession(0).level(2).buy().item(RefamishedItems.pie_cooked.itemID, 1).itemCount(2,3).defaultTrade().addToTradeList();
        TradeProvider.getBuilder().profession(0).level(2).buy().item(RefamishedItems.pie_cooked.itemID, 2).itemCount(2,3).defaultTrade().addToTradeList();
        TradeProvider.getBuilder().profession(0).level(2).buy().item(RefamishedItems.pie_cooked.itemID, 3).itemCount(2,3).defaultTrade().addToTradeList();

        TradeProvider.getBuilder().profession(0).level(3).buy().item(RefamishedItems.saltpeter.itemID).itemCount(9,17).defaultTrade().addToTradeList();
        TradeProvider.getBuilder().profession(0).level(3).buy().item(RefamishedItems.cut_sugar_cane.itemID).itemCount(22,32).defaultTrade().addToTradeList();
        TradeProvider.VariantStep woolString = TradeProvider.getBuilder().profession(0).level(3).variants();
        for (int i = 0; i < 16; i++) {
            woolString.addTradeVariant(TradeProvider.getBuilder().profession(0).level(4).buy().item(RefamishedItems.wool_string.itemID,i).itemCount(2, 3).build());
        }
        woolString.finishVariants().addToTradeList();
        */
    }


    private static void addBlacksmithTrades() {
        /*
        TradeProvider.getBuilder().profession(3).level(2).buy().item(RefamishedItems.coke_coal.itemID).itemCount(8, 12).addToTradeList();

        TradeProvider.getBuilder().profession(3).level(3).buy().item(RefamishedItems.gildedIngot.itemID).itemCount(7, 8).addToTradeList();

        TradeProvider.getBuilder().profession(3).level(4).buy().item(RefamishedItems.gildedIngot.itemID).itemCount(7, 8).addToTradeList();
        */
    }

    private static int blueprint(String blueprint) {
        return qualityBlueprints.getIndex(blueprint);
    }
}
