package top.terry_mc.yc_auto_fishing.fabric;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import top.terry_mc.yc_auto_fishing.TextDisplayNavigator;
import top.terry_mc.yc_auto_fishing.YCAutoFishing;

public final class YCAutoFishingFabric implements ModInitializer {
    private static KeyMapping toggleAutoFishing;
    private static boolean isToggleAutoFishingDown=false;
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        // Run our common setup.
        YCAutoFishing.init();
        toggleAutoFishing = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.yc_auto_fishing.toggle_auto_fishing",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_Y,
                "category.yc_auto_fishing"
        ));
        ClientTickEvents.END_CLIENT_TICK.register((minecraft -> {
            YCAutoFishing.onTick();
            if(toggleAutoFishing.isDown()) {
                isToggleAutoFishingDown = true;
            }
            else if(isToggleAutoFishingDown) {
                isToggleAutoFishingDown = false;
                YCAutoFishing.autoFishingEnabled = !YCAutoFishing.autoFishingEnabled;
                if(YCAutoFishing.autoFishingEnabled) {
                    TextDisplayNavigator.navigateToLongestTextDisplay();
                }
            }
        }));
        ClientReceiveMessageEvents.GAME.register((component, overlay) -> {
            YCAutoFishing.onChatMessage(component);
        });
    }
}
