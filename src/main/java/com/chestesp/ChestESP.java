package com.chestesp;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ChestESP.MODID, version = ChestESP.VERSION, name = ChestESP.NAME, clientSideOnly = true)
public class ChestESP {

    public static final String MODID = "chestesp";
    public static final String VERSION = "1.0.0";
    public static final String NAME = "ChestESP";

    public static ESPConfig config = new ESPConfig();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new ESPConfig();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ESPRenderer());
        MinecraftForge.EVENT_BUS.register(new KeyHandler());
    }
}
