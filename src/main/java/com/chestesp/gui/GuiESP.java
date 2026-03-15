package com.chestesp.gui;

import com.chestesp.ChestESP;
import com.chestesp.ESPConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

public class GuiESP extends GuiScreen {

    private static final int SWATCH_SIZE = 18;
    private static final int SWATCH_GAP = 3;
    private static final int ROW_HEIGHT = 30;

    private String selectedRow = null;

    private static final String[] ROW_KEYS = {
        "chest", "trapped", "ender", "dispenser", "dropper"
    };
    private static final String[] ROW_LABELS = {
        "Coffre normal", "Coffre piege", "Coffre de l'Ender", "Dispenser", "Dropper"
    };

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        ESPConfig cfg = ChestESP.config;

        int panelW = 420;
        int panelH = 260;
        int panelX = (width - panelW) / 2;
        int panelY = (height - panelH) / 2;

        drawRect(panelX, panelY, panelX + panelW, panelY + panelH, 0xCC000000);
        drawRect(panelX, panelY, panelX + panelW, panelY + 1, 0xFFAAAAAA);
        drawRect(panelX, panelY + panelH - 1, panelX + panelW, panelY + panelH, 0xFFAAAAAA);
        drawRect(panelX, panelY, panelX + 1, panelY + panelH, 0xFFAAAAAA);
        drawRect(panelX + panelW - 1, panelY, panelX + panelW, panelY + panelH, 0xFFAAAAAA);

        String title = cfg.enabled ? "\u00a7aChestESP \u00a77- \u00a7fON" : "\u00a7cChestESP \u00a77- \u00a7fOFF";
        drawCenteredString(fontRendererObj, title, width / 2, panelY + 10, 0xFFFFFF);

        String hint = "\u00a77[INSERT] Ouvrir  [HOME] Activer/Desactiver";
        drawCenteredString(fontRendererObj, hint, width / 2, panelY + 24, 0xAAAAAA);

        int startY = panelY + 44;

        for (int i = 0; i < ROW_KEYS.length; i++) {
            String key = ROW_KEYS[i];
            String label = ROW_LABELS[i];
            boolean enabled = isEnabled(cfg, key);
            int rowColor = getColor(cfg, key);
            int ry = startY + i * ROW_HEIGHT;

            boolean hovered = mouseX >= panelX + 10 && mouseX <= panelX + 110 && mouseY >= ry && mouseY < ry + ROW_HEIGHT - 2;
            if (hovered) {
                drawRect(panelX + 10, ry, panelX + 112, ry + ROW_HEIGHT - 3, 0x22FFFFFF);
            }

            String toggleText = enabled ? "\u00a7a\u25A0" : "\u00a7c\u25A0";
            drawString(fontRendererObj, toggleText + " \u00a7f" + label, panelX + 16, ry + 8, 0xFFFFFF);

            drawRect(panelX + 112, ry, panelX + 113, ry + ROW_HEIGHT - 3, 0x55FFFFFF);

            for (int ci = 0; ci < ESPConfig.PRESET_COLORS.length; ci++) {
                int cx = panelX + 120 + ci * (SWATCH_SIZE + SWATCH_GAP);
                int cy = ry + 5;
                Color c = new Color(ESPConfig.PRESET_COLORS[ci], true);
                drawRect(cx, cy, cx + SWATCH_SIZE, cy + SWATCH_SIZE, ESPConfig.PRESET_COLORS[ci] | 0xFF000000);

                if (ESPConfig.PRESET_COLORS[ci] == (rowColor & 0x00FFFFFF | 0xFF000000)
                        || new Color(rowColor, true).getRGB() == (new Color(ESPConfig.PRESET_COLORS[ci], true).getRGB())) {
                    drawRect(cx - 1, cy - 1, cx + SWATCH_SIZE + 1, cy + SWATCH_SIZE + 1, 0xFFFFFFFF);
                    drawRect(cx, cy, cx + SWATCH_SIZE, cy + SWATCH_SIZE, ESPConfig.PRESET_COLORS[ci] | 0xFF000000);
                }

                boolean swatchHover = mouseX >= cx && mouseX < cx + SWATCH_SIZE && mouseY >= cy && mouseY < cy + SWATCH_SIZE;
                if (swatchHover) {
                    drawRect(cx - 1, cy - 1, cx + SWATCH_SIZE + 1, cy + SWATCH_SIZE + 1, 0xFFFFFFFF);
                    drawRect(cx, cy, cx + SWATCH_SIZE, cy + SWATCH_SIZE, ESPConfig.PRESET_COLORS[ci] | 0xFF000000);
                    drawCenteredString(fontRendererObj,
                        "\u00a77" + ESPConfig.COLOR_NAMES[ci],
                        width / 2, panelY + panelH - 14, 0xAAAAAA);
                }
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ESPConfig cfg = ChestESP.config;

        int panelW = 420;
        int panelH = 260;
        int panelX = (width - panelW) / 2;
        int panelY = (height - panelH) / 2;
        int startY = panelY + 44;

        for (int i = 0; i < ROW_KEYS.length; i++) {
            String key = ROW_KEYS[i];
            int ry = startY + i * ROW_HEIGHT;

            if (mouseX >= panelX + 10 && mouseX <= panelX + 112 && mouseY >= ry && mouseY < ry + ROW_HEIGHT - 2) {
                toggleEnabled(cfg, key);
                return;
            }

            for (int ci = 0; ci < ESPConfig.PRESET_COLORS.length; ci++) {
                int cx = panelX + 120 + ci * (SWATCH_SIZE + SWATCH_GAP);
                int cy = ry + 5;
                if (mouseX >= cx && mouseX < cx + SWATCH_SIZE && mouseY >= cy && mouseY < cy + SWATCH_SIZE) {
                    setColor(cfg, key, ESPConfig.PRESET_COLORS[ci]);
                    return;
                }
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private boolean isEnabled(ESPConfig cfg, String key) {
        switch (key) {
            case "chest":     return cfg.showChest;
            case "trapped":   return cfg.showTrappedChest;
            case "ender":     return cfg.showEnderChest;
            case "dispenser": return cfg.showDispenser;
            case "dropper":   return cfg.showDropper;
        }
        return false;
    }

    private void toggleEnabled(ESPConfig cfg, String key) {
        switch (key) {
            case "chest":     cfg.showChest = !cfg.showChest; break;
            case "trapped":   cfg.showTrappedChest = !cfg.showTrappedChest; break;
            case "ender":     cfg.showEnderChest = !cfg.showEnderChest; break;
            case "dispenser": cfg.showDispenser = !cfg.showDispenser; break;
            case "dropper":   cfg.showDropper = !cfg.showDropper; break;
        }
    }

    private int getColor(ESPConfig cfg, String key) {
        switch (key) {
            case "chest":     return cfg.chestColor;
            case "trapped":   return cfg.trappedChestColor;
            case "ender":     return cfg.enderChestColor;
            case "dispenser": return cfg.dispenserColor;
            case "dropper":   return cfg.dropperColor;
        }
        return 0xFFFFFFFF;
    }

    private void setColor(ESPConfig cfg, String key, int color) {
        switch (key) {
            case "chest":     cfg.chestColor = color; break;
            case "trapped":   cfg.trappedChestColor = color; break;
            case "ender":     cfg.enderChestColor = color; break;
            case "dispenser": cfg.dispenserColor = color; break;
            case "dropper":   cfg.dropperColor = color; break;
        }
    }
}
