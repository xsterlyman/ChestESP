package com.chestesp;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.util.List;

public class ESPRenderer {

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        ESPConfig cfg = ChestESP.config;
        if (!cfg.enabled) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null) return;

        float pt = event.partialTicks;
        double px = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * pt;
        double py = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * pt;
        double pz = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * pt;

        GL11.glPushMatrix();
        GL11.glTranslated(-px, -py, -pz);

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(2.0f);

        List loadedTEs = mc.theWorld.loadedTileEntityList;

        for (Object obj : loadedTEs) {
            if (obj instanceof TileEntityEnderChest) {
                if (cfg.showEnderChest) {
                    drawBox(((TileEntityEnderChest) obj).getPos(), cfg.enderChestColor);
                }
            } else if (obj instanceof TileEntityChest) {
                TileEntityChest te = (TileEntityChest) obj;
                Block block = mc.theWorld.getBlockState(te.getPos()).getBlock();
                if (block == Blocks.trapped_chest) {
                    if (cfg.showTrappedChest) {
                        drawBox(te.getPos(), cfg.trappedChestColor);
                    }
                } else {
                    if (cfg.showChest) {
                        drawBox(te.getPos(), cfg.chestColor);
                    }
                }
            } else if (obj instanceof TileEntityDropper) {
                if (cfg.showDropper) {
                    drawBox(((TileEntityDropper) obj).getPos(), cfg.dropperColor);
                }
            } else if (obj instanceof TileEntityDispenser) {
                if (cfg.showDispenser) {
                    drawBox(((TileEntityDispenser) obj).getPos(), cfg.dispenserColor);
                }
            }
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glLineWidth(1.0f);
        GL11.glPopMatrix();
    }

    private void drawBox(BlockPos pos, int colorRGB) {
        Color c = new Color(colorRGB, true);
        GL11.glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);

        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        double w = 1.0;
        double h = 1.0;
        double d = 1.0;

        double expand = 0.01;
        double minX = x - expand;
        double minY = y - expand;
        double minZ = z - expand;
        double maxX = x + w + expand;
        double maxY = y + h + expand;
        double maxZ = z + d + expand;

        drawBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);

        GL11.glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 0.15f);
        GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
        GL11.glPolygonOffset(1.0f, 1.0f);
        drawFilledBox(minX, minY, minZ, maxX, maxY, maxZ);
        GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
    }

    private void drawBoundingBox(double minX, double minY, double minZ,
                                  double maxX, double maxY, double maxZ) {
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex3d(minX, minY, minZ);
        GL11.glVertex3d(maxX, minY, minZ);
        GL11.glVertex3d(maxX, minY, maxZ);
        GL11.glVertex3d(minX, minY, maxZ);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex3d(minX, maxY, minZ);
        GL11.glVertex3d(maxX, maxY, minZ);
        GL11.glVertex3d(maxX, maxY, maxZ);
        GL11.glVertex3d(minX, maxY, maxZ);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(minX, minY, minZ); GL11.glVertex3d(minX, maxY, minZ);
        GL11.glVertex3d(maxX, minY, minZ); GL11.glVertex3d(maxX, maxY, minZ);
        GL11.glVertex3d(maxX, minY, maxZ); GL11.glVertex3d(maxX, maxY, maxZ);
        GL11.glVertex3d(minX, minY, maxZ); GL11.glVertex3d(minX, maxY, maxZ);
        GL11.glEnd();
    }

    private void drawFilledBox(double minX, double minY, double minZ,
                                double maxX, double maxY, double maxZ) {
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3d(minX, minY, minZ); GL11.glVertex3d(maxX, minY, minZ);
        GL11.glVertex3d(maxX, minY, maxZ); GL11.glVertex3d(minX, minY, maxZ);

        GL11.glVertex3d(minX, maxY, minZ); GL11.glVertex3d(maxX, maxY, minZ);
        GL11.glVertex3d(maxX, maxY, maxZ); GL11.glVertex3d(minX, maxY, maxZ);

        GL11.glVertex3d(minX, minY, minZ); GL11.glVertex3d(minX, maxY, minZ);
        GL11.glVertex3d(minX, maxY, maxZ); GL11.glVertex3d(minX, minY, maxZ);

        GL11.glVertex3d(maxX, minY, minZ); GL11.glVertex3d(maxX, maxY, minZ);
        GL11.glVertex3d(maxX, maxY, maxZ); GL11.glVertex3d(maxX, minY, maxZ);

        GL11.glVertex3d(minX, minY, minZ); GL11.glVertex3d(maxX, minY, minZ);
        GL11.glVertex3d(maxX, maxY, minZ); GL11.glVertex3d(minX, maxY, minZ);

        GL11.glVertex3d(minX, minY, maxZ); GL11.glVertex3d(maxX, minY, maxZ);
        GL11.glVertex3d(maxX, maxY, maxZ); GL11.glVertex3d(minX, maxY, maxZ);
        GL11.glEnd();
    }
}
