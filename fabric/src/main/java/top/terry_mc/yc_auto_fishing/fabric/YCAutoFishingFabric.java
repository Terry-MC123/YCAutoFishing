package top.terry_mc.yc_auto_fishing.fabric;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import top.terry_mc.yc_auto_fishing.YCAutoFishing;

public final class YCAutoFishingFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        // Run our common setup.
        YCAutoFishing.init();
    }
}
