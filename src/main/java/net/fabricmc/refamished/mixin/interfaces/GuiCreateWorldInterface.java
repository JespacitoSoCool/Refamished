package net.fabricmc.refamished.mixin.interfaces;

import net.minecraft.src.GuiCreateWorld;
import net.minecraft.src.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiCreateWorld.class)
public interface GuiCreateWorldInterface {
    @Accessor("difficultyID")
    int getId();
    @Accessor("difficultyID")
    void setId(int id);
    @Accessor("moreOptions")
    boolean getOptions();
    @Accessor("textboxSeed")
    public abstract GuiTextField getTextboxSeed();
    @Accessor("bonusItems")
    public abstract void setBonusItems(boolean value);
    @Accessor("commandsAllowed")
    public abstract void setCommandValue(boolean value);
    @Accessor("bonusItems")
    boolean bonusItems();
    @Accessor("commandsAllowed")
    boolean commandsAllowed();
}
