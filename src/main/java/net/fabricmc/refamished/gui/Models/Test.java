package net.fabricmc.refamished.gui.Models;

import net.fabricmc.refamished.gui.ModelPreviewGui;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static List<ModelPreviewGui.BlockData> getModel() {
        List<ModelPreviewGui.BlockData> structure = new ArrayList<>();
        structure.add(new ModelPreviewGui.BlockData(0, 0, 0, 1, 0));
        return structure;
    }
}
