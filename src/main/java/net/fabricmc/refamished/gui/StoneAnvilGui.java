package net.fabricmc.refamished.gui;

import btw.item.items.ChiselItem;
import net.fabricmc.refamished.entities.tiles.*;
import net.fabricmc.refamished.itemsbase.hammer;
import net.fabricmc.refamished.itemsbase.tongs;
import net.fabricmc.refamished.misc.Recipes.ForgingPlansRecipes;
import net.fabricmc.refamished.misc.Packets.RefamishedPacketManager;
import net.fabricmc.refamished.skill.*;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

public class StoneAnvilGui extends GuiContainer {
    private static final ResourceLocation TEXTURE = new ResourceLocation("refamished:textures/gui/stone_anvil.png");
    private static final ResourceLocation DARK = new ResourceLocation("refamished:textures/gui/anvil_dark.png");
    //private static final Logger log = LogManager.getLogger(StoneAnvilGui.class);
    private stoneAnvilTile tile;
    private boolean wasSpaceDown = false;
    private InventoryPlayer plrInv;
    private List<ForgingPlansRecipes.RecipeEntry> recipeButtons = new ArrayList<>();
    private boolean isForging = false;
    private int toolId = 0;
    private boolean dark = false;

    private void updatePlanButtons() {
        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < tile.sizeInv(); i++) {
            ItemStack stack = tile.getStackInSlot(i);
            if (stack != null) inputs.add(stack);
        }
        recipeButtons = ForgingPlansRecipes.getInstance().getMatchingRecipes(inputs,tile.getBonusLevel());
    }

    public StoneAnvilGui(InventoryPlayer playerInventory, stoneAnvilTile tile) {
        super(new stoneAnvilContainer(playerInventory, tile));
        this.tile = tile;
        this.ySize = 166;
        plrInv = playerInventory;
    }

    public StoneAnvilGui(InventoryPlayer playerInventory, copperAnvilTile tile) {
        super(new copperAnvilContainer(playerInventory, tile));
        this.tile = tile;
        this.ySize = 166;
        plrInv = playerInventory;
    }

    public StoneAnvilGui(InventoryPlayer playerInventory, steelAnvilTile tile) {
        super(new steelAnvilContainer(playerInventory, tile));
        this.tile = tile;
        this.ySize = 166;
        plrInv = playerInventory;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String name = StatCollector.translateToLocal(this.tile.getInvName());
        this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        this.mc.renderEngine.bindTexture(TEXTURE);
        int guiX = (this.width - this.xSize) / 2;
        int guiY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(guiX, guiY, 0, 0, this.xSize, this.ySize);

        if (isMinigameActive()) {
            int barX = guiX + 8;
            int barY = guiY + 66;

            int barWidth = 156;
            int cursorX = (int) (getProgress() * 1);

            int redStart = (int) (getRedStart());

            this.mc.renderEngine.bindTexture(TEXTURE);
            this.drawTexturedModalRect(guiX+7, guiY+65, 0, 166, 162, 5);
            this.drawTexturedModalRect(barX + redStart, barY, 181, 0, redWidth, 6);
            this.drawTexturedModalRect(barX + cursorX, barY, 176, 0, 4, 6);
        }

        this.mc.renderEngine.bindTexture(TEXTURE);
        int usableSlots = tile.sizeInv() - 1;
        int totalWidth = (usableSlots - 1) * 18;
        int startX = guiX + 80 - totalWidth / 2;
        int y = guiY + 46;

        for (int i = 0; i < usableSlots; i++) {
            int x = startX + i * 18;
            drawTexturedModalRect(x - 1, y - 1, 176, 6, 18, 18);
        }

        isForging = false;
        toolId = 0;
        if (tile.isForging(tile)) {
            ItemStack get = tile.getForgingItemStack(tile);
            NBTTagCompound tags = get.getTagCompound();
            int shatterMax = tags.hasKey("ShatterMax") ? tags.getInteger("ShatterMax") : 0;
            int shatter = tags.hasKey("Shatter") ? tags.getInteger("Shatter"): 0;
            int meta = tags.hasKey("Metadata") ? tags.getInteger("Metadata"): 0;
            int blow = tags.hasKey("Blow") ? tags.getInteger("Blow"): 0;
            int blowReq = tags.hasKey("BlowReq") ? tags.getInteger("BlowReq"): 0;
            int cut = tags.hasKey("Cut") ? tags.getInteger("Cut"): 0;
            int cutReq = tags.hasKey("CutReq") ? tags.getInteger("CutReq"): 0;
            int grip = tags.hasKey("Grip") ? tags.getInteger("Grip"): 0;
            int gripReq = tags.hasKey("GripReq") ? tags.getInteger("GripReq"): 0;
            int barWidth = 59;
            int[] reqs = new int[] { blowReq, cutReq, gripReq };

            for (int i = 0; i < 3; i++) {
                this.drawTexturedModalRect(guiX+11, guiY+21+(i*5), 0, 171, barWidth, 5);
            }
            float blowNormal = (float) blow / (float) shatterMax;
            float cutNormal = (float) cut / (float) shatterMax;
            float gripNormal = (float) grip / (float) shatterMax;
            this.drawTexturedModalRect(guiX+11, guiY+21, 0, 176, (int) (blowNormal* barWidth), 5);
            this.drawTexturedModalRect(guiX+11, guiY+26, 0, 181, (int) (cutNormal* barWidth), 5);
            this.drawTexturedModalRect(guiX+11, guiY+31, 0, 186, (int) (gripNormal* barWidth), 5);
            for (int i = 0; i < 3; i++) {
                float norm = (float) reqs[i] / (float) shatterMax;
                //System.out.println(norm);
                int flagX = guiX + 7 + (int)(norm * barWidth);
                int flagY = guiY + 21 + (i * 5);
                this.drawTexturedModalRect(flagX, flagY, 58, 176+(i*5), 8, 5);
            }
            isForging = true;
            ItemStack getTool = tile.getStackInSlot(0);
            if (getTool != null) {
                int by = guiY + 19;
                for (int i = 0; i < 3; i++) {
                    int tx = guiX + 102 + (i*21);
                    toolId = getTool.getItem() instanceof hammer ? 1 : getTool.getItem() instanceof ChiselItem ? 2 : getTool.getItem() instanceof tongs ? 3 : 0;
                    boolean isMouseIn = mouseX >= tx && mouseX < tx + 20 && mouseY >= by && mouseY < by + 20;
                    int he = 176+(i*20);
                    int hi = 104+(toolId-1)*40 + (isMouseIn ? 20 : 0);
                    this.drawTexturedModalRect(tx, by, he, hi, 20, 20);
                    float norm = (float) reqs[i] / (float) shatterMax;
                    int flagX = guiX + 7 + (int)(norm * barWidth);
                    int flagY = guiY + 21 + (i * 5);

                    this.drawTexturedModalRect(flagX, flagY, 58, 176+(i*5), 8, 5);
                }
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
        super.drawScreen(mouseX,mouseY,f);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        int guiX = (this.width - this.xSize) / 2;
        int guiY = (this.height - this.ySize) / 2;

        this.mc.renderEngine.bindTexture(TEXTURE);

        for (int i = 0; i < recipeButtons.size(); i++) {
            int by = guiY + 5 + (i%7) * 22;
            int row = (int) ( ((float)i/7f) );
            int tx = guiX - 20 - (row*20);
            int width = row == 0 ? 20 : 23;
            boolean isMouseIn = mouseX >= tx && mouseX < tx + 20 && mouseY >= by && mouseY < by + 20;
            int he = isMouseIn ? 24 : 44;
            this.mc.renderEngine.bindTexture(TEXTURE);
            GL11.glDisable(GL11.GL_LIGHTING);
            drawTexturedModalRect(tx , by , row == 0 ? 176 : 196, he, width, 20);
            ItemStack output = recipeButtons.get(i).getOutput(Arrays.asList(tile.inventory));
            itemRenderer.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.getTextureManager(), output, tx + 2, by + 2);

            if (isMouseIn && output != null) {
                int itemID = output.itemID;

                List<String> tooltip = new ArrayList<>();
                tooltip.add(EnumChatFormatting.BOLD + output.getDisplayName());
                tooltip.add("");

                tooltip.add(EnumChatFormatting.GRAY + "Requires:");
                for (ItemStack input : recipeButtons.get(i).inputs) {
                    tooltip.add("- " + input.stackSize + "x " + input.getItem().getItemStackDisplayName(input));
                }

                PlayerSkillData skillData = SkillManager.getSkillData(plrInv.player);
                RecipeSkillRequirement skillReq = RecipeSkillManager.getRequirements(itemID);
                if (skillReq != null) {
                    tooltip.add("");
                    //tooltip.add(EnumChatFormatting.YELLOW + "Skill Requirements:");
                    for (Map.Entry<String, Integer> entry : skillReq.getRequirements().entrySet()) {
                        PlayerSkillData.SkillProgress playerLevel = skillData.getSkillProgress(entry.getKey());
                        boolean met = playerLevel.getLevel() >= entry.getValue();

                        tooltip.add(String.format("%s%s: %d/%d",
                                met ? EnumChatFormatting.GREEN : EnumChatFormatting.RED,
                                entry.getKey(),
                                playerLevel.getLevel(),
                                entry.getValue()));
                    }
                }

                // Finally draw the tooltip
                drawCustomHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
            }
        }
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    protected void drawCustomHoveringText(List<String> textLines, int x, int y, FontRenderer font) {
        if (textLines == null || textLines.isEmpty()) return;

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        int tooltipWidth = 0;
        for (String line : textLines) {
            int lineWidth = font.getStringWidth(line);
            if (lineWidth > tooltipWidth) {
                tooltipWidth = lineWidth;
            }
        }

        int tooltipX = x + 12;
        int tooltipY = y - 12;
        int tooltipHeight = 8 + (textLines.size() - 1) * 10;

        int zLevel = 300;
        this.zLevel = zLevel;
        drawGradientRect(tooltipX - 3, tooltipY - 4, tooltipX + tooltipWidth + 3, tooltipY + tooltipHeight + 4, 0xF0101010, 0xF0101010);

        for (int i = 0; i < textLines.size(); ++i) {
            String line = textLines.get(i);
            font.drawStringWithShadow(line, tooltipX, tooltipY, 0xFFFFFF);
            tooltipY += 10;
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        this.zLevel = 0;
    }


    @Override
    protected void keyTyped(char keyChar, int keyCode) {
        super.keyTyped(keyChar, keyCode);
        if (keyCode == Keyboard.KEY_SPACE) {
            startMinigame(plrInv);
            long time = System.currentTimeMillis();
            int frameIndex = (int) ((time / speed));
            startTime = frameIndex;
        }
    }

    public boolean isPlaying = false;
    public int redStart = 0;
    public int redWidth = 8;
    public int redMissZone = 10;
    public long startTime = 0;
    public int speed = 12;

    public boolean isMinigameActive() {
        return isPlaying;
    }

    public float getRedStart() {
        return redStart;
    }

    public void updateMinigame() {
        if (getProgress() >= 160) {
            stopMinigame();
        }
    }

    public static void sendMinigameResult(boolean success) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.writeByte(0);
            dos.writeBoolean(success);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = RefamishedPacketManager.STONE_ANVIL_;
        packet.data = baos.toByteArray();
        packet.length = baos.size();

        Minecraft.getMinecraft().getNetHandler().addToSendQueue(packet);
    }


    public void startMinigame(InventoryPlayer playerInventory) {
        if (!isMinigameActive()) {
            this.isPlaying = true;
            startTime = 0;
            this.redStart = (10 + playerInventory.player.worldObj.rand.nextInt(4))*10; // 100 to 144
        }
    }

    public int getProgress() {
        long time = System.currentTimeMillis();
        long frameIndex = ((time / speed));
        return (int) ((frameIndex-startTime));
    }

    public void stopMinigame() {
        int cursor = getProgress();
        if (isPlaying && cursor >= redStart-redMissZone && cursor <= redStart + redWidth) {
            boolean success = false;
            if (cursor >= redStart && cursor <= redStart + redWidth) {
                success = true;
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);

            try {
                dos.writeByte(1);
                dos.writeBoolean(success);
            } catch (Exception e) {
                e.printStackTrace();
            }

            sendMinigameResult(success);
        }
        isPlaying = false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);

        int guiX = (this.width - this.xSize) / 2;
        int guiY = (this.height - this.ySize) / 2;

        for (int i = 0; i < recipeButtons.size(); i++) {
            int by = guiY + 5 + (i%7) * 22;
            int row = (int) ( ((float)i/7f) );
            int tx = guiX - 20 - (row*21);
            int width = row == 0 ? 20 : 23;

            if (mouseX >= tx && mouseX < tx + 20 && mouseY >= by && mouseY < by + 20) {
                ForgingPlansRecipes.RecipeEntry selected = recipeButtons.get(i);
                if (SkillCraftingHandler.canPlayerCraft(this.plrInv.player, selected.output)) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    DataOutputStream dos = new DataOutputStream(baos);

                    try {
                        dos.writeByte(2);
                        dos.writeInt(i);
                        dos.writeInt(selected.getOutput(Arrays.asList(tile.inventory)).itemID);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Packet250CustomPayload packet = new Packet250CustomPayload();
                    packet.channel = RefamishedPacketManager.FORGE_PLAN;
                    packet.data = baos.toByteArray();
                    packet.length = baos.size();

                    Minecraft.getMinecraft().getNetHandler().addToSendQueue(packet);
                }
            }
        }

        if (isForging && toolId != 0) {
            int by = guiY + 19;
            for (int i = 0; i < 3; i++) {
                int tx = guiX + 102 + (i*21);
                boolean isMouseIn = mouseX >= tx && mouseX < tx + 20 && mouseY >= by && mouseY < by + 20;
                if (isMouseIn) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    DataOutputStream dos = new DataOutputStream(baos);

                    try {
                        dos.writeByte(1);
                        dos.writeInt(toolId);
                        dos.writeInt(i+1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Packet250CustomPayload packet = new Packet250CustomPayload();
                    packet.channel = RefamishedPacketManager.FORGE_PLAN;
                    packet.data = baos.toByteArray();
                    packet.length = baos.size();

                    Minecraft.getMinecraft().getNetHandler().addToSendQueue(packet);
                }
            }
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        boolean isSpaceDown = Keyboard.isKeyDown(Keyboard.KEY_SPACE);

        if (isSpaceDown && !wasSpaceDown) {
            // Space was just pressed
            startMinigame(plrInv);
        } else if (!isSpaceDown && wasSpaceDown) {
            // Space was just released
            stopMinigame();
        }

        wasSpaceDown = isSpaceDown;

        if (isMinigameActive()) {
            updateMinigame();
        }

        updatePlanButtons();
    }

}