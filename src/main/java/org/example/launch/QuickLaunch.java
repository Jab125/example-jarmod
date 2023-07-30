package org.example.launch;

import com.jab125.tweakloader.classloader.TweakingClassLoader;

import net.minecraft.client.main.Main;

import java.nio.file.Path;


// utility for quickly launching minecraft, MUST RUN THROUGH THE TWEAK LAUNCHER
public class QuickLaunch {
    public static void main(String[] args) {
        if (!(QuickLaunch.class.getClassLoader() instanceof TweakingClassLoader tweakingClassLoader)) throw new RuntimeException("TJM loaded without tweaker!");
        // do anything to the tweaking class loader
        // for example: tweakingClassLoader.addURL(...);
        Main.main(new String[]{"--accessToken", "", "--version", "DEV", "--assetsDir", Path.of("cache/minecraft/assets").toString(), "--assetIndex", "5"});
    }
}
