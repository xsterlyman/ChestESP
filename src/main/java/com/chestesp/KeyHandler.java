package com.chestesp;

import com.chestesp.gui.GuiESP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyHandler {

    public static final KeyBinding OPEN_GUI = new KeyBinding(
        "Ouvrir ChestESP",
        Keyboard.KEY_INSERT,
        "ChestESP"
    );

    public static final KeyBinding TOGGLE = new KeyBinding(
        "Activer/Desactiver ChestESP",
        Keyboard.KEY_HOME,
        "ChestESP"
    );

    public KeyHandler() {
        ClientRegistry.registerKeyBinding(OPEN_GUI);
        ClientRegistry.registerKeyBinding(TOGGLE);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (OPEN_GUI.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiESP());
        }
        if (TOGGLE.isPressed()) {
            ChestESP.config.enabled = !ChestESP.config.enabled;
            String msg = ChestESP.config.enabled
                ? "\u00a7aChestESP \u00a7factive"
                : "\u00a7cChestESP \u00a7fdesactive";
            Minecraft.getMinecraft().thePlayer.addChatMessage(
                new net.minecraft.util.ChatComponentText(msg)
            );
        }
    }
}
