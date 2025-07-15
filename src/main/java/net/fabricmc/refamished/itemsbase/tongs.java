package net.fabricmc.refamished.itemsbase;

import btw.block.BTWBlocks;
import btw.block.tileentity.PlacedToolTileEntity;
import btw.entity.attribute.AttributeOperation;
import btw.item.PlaceableAsItem;
import btw.util.MiscUtils;
import btw.world.util.BlockPos;
import btw.world.util.WorldUtils;
import com.google.common.collect.Multimap;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.misc.ResharedMonsterAttributes;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.quality.ToolQualityHelper;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;

public class tongs extends Item
{
    public tongs(int iItemID, int damage)
    {
        super(iItemID);
        setFull3D();
        this.setMaxStackSize(1);
        setCreativeTab(CreativeTabs.tabTools);
        setMaxDamage(damage);
    }

    @Override
    public boolean isFull3D() {
        return true;
    }
}
