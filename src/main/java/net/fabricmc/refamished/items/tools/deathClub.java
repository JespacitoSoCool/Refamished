package net.fabricmc.refamished.items.tools;

import btw.item.items.ClubItem;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import org.lwjgl.Sys;

public class deathClub extends ClubItem {

    protected int var;
    public deathClub(int iItemID, int iDurability, int iWeaponDamage, String sName, int Var) {
        super(iItemID, iDurability, iWeaponDamage, sName);
        var = Var;
    }

    private Icon defaultHandle;
    private Icon[] handleTextures = new Icon[2]; // 0 = Default, 1 = Colored Handle
    private Icon[] headTextures = new Icon[4]; // 0 = Oak, 1 = Spruce, 2 = Birch, 3 = Jungle

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);

        // Handle textures
        defaultHandle = register.registerIcon("refamished:pieces/handle"); // Default handle
        handleTextures[0] = defaultHandle; // Default
        handleTextures[1] = register.registerIcon("refamished:pieces/death_club"); // Colored handle

        // Head textures
        headTextures[0] = register.registerIcon("refamished:pieces/oak_club");    // Oak Head
        headTextures[1] = register.registerIcon("refamished:pieces/spruce_club"); // Spruce Head
        headTextures[2] = register.registerIcon("refamished:pieces/birch_club");  // Birch Head
        headTextures[3] = register.registerIcon("refamished:pieces/jungle_club"); // Jungle Head
    }

    @Override
    public Icon getIconFromDamageForRenderPass(int damage, int renderPass) {
        if (renderPass == 0) {
            return handleTextures[1];
        }
        return headTextures[var];
    }
}
