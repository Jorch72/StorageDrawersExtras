package com.jaquadro.minecraft.storagedrawersextra;

import com.jaquadro.minecraft.storagedrawers.core.handlers.GuiHandler;
import com.jaquadro.minecraft.storagedrawersextra.config.ConfigManagerExt;
import com.jaquadro.minecraft.storagedrawersextra.core.CommonProxy;
import com.jaquadro.minecraft.storagedrawersextra.core.ModBlocks;
import com.jaquadro.minecraft.storagedrawersextra.core.ModRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;

@Mod(modid = StorageDrawersExtra.MOD_ID, name = StorageDrawersExtra.MOD_NAME, version = StorageDrawersExtra.MOD_VERSION,
    dependencies = "required-after:StorageDrawers;required-after:Chameleon[2.2.1,2.3.0);after:waila;",
    guiFactory = StorageDrawersExtra.SOURCE_PATH + "core.ModGuiFactory",
    acceptedMinecraftVersions = "[1.9,1.11)")
public class StorageDrawersExtra
{
    public static final String MOD_ID = "storagedrawersextra";
    public static final String MOD_NAME = "Storage Drawers Extras";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String SOURCE_PATH = "com.jaquadro.minecraft.storagedrawersextra.";

    public static final ModBlocks blocks = new ModBlocks();
    public static final ModRecipes recipes = new ModRecipes();

    public static ConfigManagerExt config;

    @Mod.Instance(MOD_ID)
    public static StorageDrawersExtra instance;

    @SidedProxy(clientSide = SOURCE_PATH + "core.ClientProxy", serverSide = SOURCE_PATH + "core.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        config = new ConfigManagerExt(new File(event.getModConfigurationDirectory(), MOD_ID + ".cfg"));

        blocks.init();

        proxy.initDynamic();
        proxy.initClient();
        proxy.registerRenderers();
    }

    public void init (FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        MinecraftForge.EVENT_BUS.register(instance);
    }

    @Mod.EventHandler
    public void postInit (FMLPostInitializationEvent event) {
        recipes.init();
    }

    @SubscribeEvent
    public void onConfigChanged (ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(MOD_ID))
            config.syncConfig();
    }
}
