package net.fabricmc.refamished.misc;

import net.minecraft.src.Minecraft;
import org.lwjgl.Sys;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class RefamishedConfig {
    public static boolean oldQualityFlags = false;
    public static boolean refamishedTextures = true;
    public static boolean embersEnabled = true;
    public static boolean smokeEnabled = true;
    public static String serverName = "";
    public static String serverIp = "";

    private static final File configDir = new File(System.getProperty("user.dir"), "config");
    private static final File configFile = new File(configDir, "refamished.cfg");

    public static void load() {
        Map<String, String> entries = new LinkedHashMap<>();
        Properties props = new Properties();

        // Load existing properties if any
        if (configFile.exists()) {
            try (FileInputStream in = new FileInputStream(configFile)) {
                props.load(in);
            } catch (IOException e) {
                System.err.println("[Refamished] Failed to read config: " + e.getMessage());
            }
        }

        // Parse or fallback to default
        oldQualityFlags = parseBoolean(props.getProperty("oldQualityFlags"), false);
        refamishedTextures = parseBoolean(props.getProperty("refamishedTextures"), true);
        embersEnabled = parseBoolean(props.getProperty("embersEnabled"), true);
        smokeEnabled = parseBoolean(props.getProperty("smokeEnabled"), true);
        serverName = parseString(props.getProperty("quickServerName"),"");
        serverIp = parseString(props.getProperty("quickServer"),"");

        // Prepare values and their descriptions
        entries.put("# Use of old quality flags", null);
        entries.put("oldQualityFlags", String.valueOf(oldQualityFlags));

        entries.put("# Use of Refamished Textures", null);
        entries.put("refamishedTextures", String.valueOf(refamishedTextures));

        entries.put("# Toggle Embers", null);
        entries.put("embersEnabled", String.valueOf(embersEnabled));

        entries.put("# Toggle Smoke", null);
        entries.put("smokeEnabled", String.valueOf(smokeEnabled));

        entries.put("# Quick Server Name (String)", null);
        entries.put("quickServerName", String.valueOf(serverName));

        entries.put("# Quick Server Ip (String)", null);
        entries.put("quickServer", String.valueOf(serverIp));

        System.out.println(serverIp+" SERVERRRRRRRRRRRRRR");

        // Write manually
        try {
            if (!configDir.exists()) configDir.mkdirs();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
                writer.write("# Refamished Addon Configuration\n");
                writer.write("# Generated automatically. Edit with caution.\n\n");

                for (Map.Entry<String, String> entry : entries.entrySet()) {
                    if (entry.getValue() == null) {
                        writer.write(entry.getKey() + "\n");
                    } else {
                        writer.write(entry.getKey() + "=" + entry.getValue() + "\n\n");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[Refamished] Failed to save config: " + e.getMessage());
        }
    }

    // === Helper methods ===
    private static boolean parseBoolean(String value, boolean defaultVal) {
        if (value == null) {return defaultVal;}
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            return defaultVal;
        }
    }
    private static String parseString(String value, String defaultVal) {
        if (value == null) {return defaultVal;}
        try {
            return value;
        } catch (Exception e) {
            return defaultVal;
        }
    }
}
