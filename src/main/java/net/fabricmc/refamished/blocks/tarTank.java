package net.fabricmc.refamished.blocks;

import btw.block.BTWBlocks;
import btw.block.model.BlockModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.entities.tiles.tarTankTile;
import net.minecraft.src.*;

public class tarTank extends BlockContainer {

    public tarTank(int i) {
        super(i, Material.rock);
        setTextureName("refamished:tar_tank");
        setUnlocalizedName("tar_tank");
        this.setPicksEffectiveOn();
        this.setChiselsEffectiveOn();
        this.setStepSound(BTWBlocks.clayBrickStepSound);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(0.7f);
        this.setResistance(3.0f);
    }
    public static Icon tarTexture;
    public static Icon brick;
    @Override
    public TileEntity createNewTileEntity(World world) {
        return new tarTankTile();
    }
    public static void GivePlayerStackOrEject(EntityPlayer player, ItemStack stack) {
        if (!player.inventory.addItemStackToInventory(stack)) {
            player.dropPlayerItemWithRandomChoice(stack, false);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if (tile instanceof tarTankTile) {
                tarTankTile tank = (tarTankTile)tile;
                ItemStack held = player.getHeldItem();
                if (held != null && held.getItem().itemID == RefamishedItems.test.itemID)
                {
                    tank.addTar(500);
                }
                else if (held != null && held.getItem().itemID == Item.glassBottle.itemID && tank.depositTar(250))
                {
                    if (held.stackSize == 1)
                    {
                        player.inventory.mainInventory[player.inventory.currentItem] = null;
                        player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(RefamishedItems.tar_bottle);
                    }
                    else
                    {
                        GivePlayerStackOrEject(player,new ItemStack(RefamishedItems.tar_bottle));
                        --held.stackSize;
                    }
                }
                else if (held != null && held.getItem().itemID == Item.bucketEmpty.itemID && tank.depositTar(1000))
                {
                    player.inventory.mainInventory[player.inventory.currentItem] = null;
                    player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(RefamishedItems.tar_bucket);
                }
                else if (held != null && held.getItem().itemID == RefamishedItems.tar_bottle.itemID)
                {
                    tank.addTar(250);
                    if (held.stackSize == 1)
                    {
                        player.inventory.mainInventory[player.inventory.currentItem] = null;
                        player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Item.glassBottle);
                    }
                    else
                    {
                        GivePlayerStackOrEject(player,new ItemStack(Item.glassBottle));
                        --held.stackSize;
                    }
                }
                else if (held != null && held.getItem().itemID == RefamishedItems.tar_bucket.itemID)
                {
                    tank.addTar(1000);
                    player.inventory.mainInventory[player.inventory.currentItem] = null;
                    player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Item.bucketEmpty);
                }
                else if (held != null && held.getItem().itemID == RefamishedItems.arrow_flint.itemID && tank.depositTar(75))
                {
                    if (held.stackSize == 1)
                    {
                        player.inventory.mainInventory[player.inventory.currentItem] = null;
                        player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(RefamishedItems.arrow_incendiary);
                    }
                    else
                    {
                        GivePlayerStackOrEject(player,new ItemStack(RefamishedItems.arrow_incendiary));
                        --held.stackSize;
                    }
                }
                //if (!world.isRemote) {
                    //player.addChatMessage("Tar Level: " + ((tarTankTile) tile).getTarAmount() + " / " + tarTankTile.MAX_TAR);
                //}
            }
        return true;
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        this.blockIcon = register.registerIcon("refamished:tar_tank");
        this.tarTexture = register.registerIcon("refamished:tar");
        this.brick = register.registerIcon("refamished:soft_bricks_loose");
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void renderBlockAsItem(RenderBlocks renderBlocks, int iItemDamage, float fBrightness) {
        renderBlocks.renderBlockAsItemVanilla(this, iItemDamage, fBrightness);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getIcon(int iSide, int iMetadata) {
        if (iSide == 0 || iSide == 1)
        {
            return this.brick;
        }
        return this.blockIcon;
    }
}
