package net.fabricmc.refamished.blocks;

import btw.block.MechanicalBlock;
import btw.block.util.MechPowerUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.entities.tiles.copperConductTile;
import net.fabricmc.refamished.entities.tiles.steamKilnTile;
import net.fabricmc.refamished.misc.RenderShit;
import net.minecraft.src.*;

import java.util.Random;

public class steamKiln extends steamContainer implements MechanicalBlock {

    @Environment(value=EnvType.CLIENT)
    protected Icon front;
    @Environment(value=EnvType.CLIENT)
    protected Icon side;
    @Environment(value=EnvType.CLIENT)
    protected Icon input;

    public steamKiln(int i) {
        super(i, Material.iron);
        setHardness(1.5F);
        setResistance(8.0F);
        setStepSound(soundMetalFootstep);
        setUnlocalizedName("steam_kiln");
        setCreativeTab(CreativeTabs.tabRedstone);
        this.setTickRandomly(true);
        setTextureName("refamished:copper_block");
    }

    @Override
    public int tickRate(World world) {
        return 10;
    }

    @Override
    public void onBlockAdded(World world, int i, int j, int k) {
        super.onBlockAdded(world, i, j, k);
        world.scheduleBlockUpdate(i, j, k, this.blockID, this.tickRate(world));
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new steamKilnTile();
    }

    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
        int var7 = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0f / 360.0f) + 0.5) & 3;
        if (var7 == 0) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
        }
        if (var7 == 1) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
        }
        if (var7 == 2) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
        }
        if (var7 == 3) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
        }
        if (par6ItemStack.hasDisplayName()) {
            ((TileEntityFurnace)par1World.getBlockTileEntity(par2, par3, par4)).setGuiDisplayName(par6ItemStack.getDisplayName());
        }
    }

    public boolean isCurrentStateValid(World world, int i, int j, int k) {
        boolean bReceivingPower = this.isInputtingMechanicalPower(world, i, j, k);
        boolean bOn = this.isPowered(world.getBlockMetadata(i,j,k));
        return bOn == bReceivingPower;
    }

    @Override
    public void randomUpdateTick(World world, int i, int j, int k, Random rand) {
        if (!this.isCurrentStateValid(world, i, j, k) && !world.isUpdateScheduledForBlock(i, j, k, this.blockID)) {
            world.scheduleBlockUpdate(i, j, k, this.blockID, this.tickRate(world));
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int iBlockID) {
        if (!this.isCurrentStateValid(world, i, j, k) && !world.isUpdatePendingThisTickForBlock(i, j, k, this.blockID)) {
            world.scheduleBlockUpdate(i, j, k, this.blockID, this.tickRate(world));
        }
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random) {
        boolean bReceivingPower = this.isInputtingMechanicalPower(world, i, j, k);
        boolean bOn = this.isPowered(world.getBlockMetadata(i,j,k));
        if (bOn != bReceivingPower) {
            this.setPowered(world, i, j, k, bReceivingPower);
        }
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getIcon(int iSide, int iMetadata) {
        int iMetadataStripped = iMetadata & 7;
        return (iSide == oppositeSide(iMetadataStripped) && iMetadata!=0) ? this.input : iSide == 1 ? this.blockIcon : (iSide == 0 ? this.blockIcon : (iSide != iMetadataStripped ? this.side : this.front));
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        this.blockIcon = register.registerIcon("refamished:copper_block");
        this.front = register.registerIcon("refamished:steam_kiln_front");
        this.side = register.registerIcon("refamished:steam_kiln_side");
        this.input = register.registerIcon("refamished:steam_kiln_input");
    }

    public boolean isPowered(int meta) {
        return (meta & 8) != 0;
    }

    public int getFacing(int meta) {
        return meta & 7;
    }

    public void setPowered(World world, int x, int y, int z, boolean powered) {
        int meta = world.getBlockMetadata(x, y, z);
        int facing = getFacing(meta);
        int newMeta = facing | (powered ? 8 : 0);

        if (meta != newMeta) {
            world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);
        }
    }

    @Override
    public boolean canOutputMechanicalPower() {
        return false;
    }

    @Override
    public boolean canInputMechanicalPower() {
        return false;
    }

    @Override
    public boolean isInputtingMechanicalPower(World world, int i, int j, int k) {
        return MechPowerUtils.isBlockPoweredByAxle(world, i, j, k, this) || MechPowerUtils.isBlockPoweredByHandCrank(world, i, j, k);
    }

    @Override
    public boolean isOutputtingMechanicalPower(World var1, int var2, int var3, int var4) {
        return false;
    }

    @Override
    public boolean canInputAxlePowerToFacing(World world, int i, int j, int k, int iFacing) {
        return oppositeSide(world.getBlockMetadata(i,j,k) & 7) == iFacing;
    }

    @Override
    public void overpower(World var1, int var2, int var3, int var4) {

    }

    @Environment(value=EnvType.CLIENT)
    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        int meta = world.getBlockMetadata(i,j,k);
        if (this.isPowered(meta)) {
            world.playSound((double)i + 0.5, (double)j + 0.5, (double)k + 0.5, "fire.fire", 0.3f + random.nextFloat()*0.1f, 2f + random.nextFloat() * 0.1f);
        }
    }
}