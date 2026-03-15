package com.chestesp;

import java.awt.Color;

public class ESPConfig {

    public boolean enabled = true;

    public boolean showChest = true;
    public int chestColor = new Color(255, 170, 0, 200).getRGB();

    public boolean showTrappedChest = true;
    public int trappedChestColor = new Color(255, 50, 50, 200).getRGB();

    public boolean showEnderChest = true;
    public int enderChestColor = new Color(130, 0, 255, 200).getRGB();

    public boolean showDispenser = true;
    public int dispenserColor = new Color(50, 200, 255, 200).getRGB();

    public boolean showDropper = true;
    public int dropperColor = new Color(50, 255, 100, 200).getRGB();

    public static final int[] PRESET_COLORS = {
        new Color(255, 255, 255, 200).getRGB(),
        new Color(255, 85,  85,  200).getRGB(),
        new Color(255, 170, 0,   200).getRGB(),
        new Color(255, 255, 0,   200).getRGB(),
        new Color(85,  255, 85,  200).getRGB(),
        new Color(0,   200, 200, 200).getRGB(),
        new Color(85,  85,  255, 200).getRGB(),
        new Color(170, 0,   255, 200).getRGB(),
        new Color(255, 85,  255, 200).getRGB(),
        new Color(180, 180, 180, 200).getRGB(),
    };

    public static final String[] COLOR_NAMES = {
        "Blanc", "Rouge", "Orange", "Jaune", "Vert",
        "Cyan", "Bleu", "Violet", "Rose", "Gris"
    };
}
