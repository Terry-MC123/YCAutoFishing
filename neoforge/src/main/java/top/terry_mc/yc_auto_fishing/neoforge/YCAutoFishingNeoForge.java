package top.terry_mc.yc_auto_fishing.neoforge;

import com.mojang.blaze3d.platform.InputConstants;
import cpw.mods.util.Lazy;
import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientChatReceivedEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;
import top.terry_mc.yc_auto_fishing.TextDisplayNavigator;
import top.terry_mc.yc_auto_fishing.YCAutoFishing;

@EventBusSubscriber
@Mod(top.terry_mc.yc_auto_fishing.YCAutoFishing.MOD_ID)
public final class YCAutoFishingNeoForge {
    public YCAutoFishingNeoForge() {
        // Run our common setup.
        top.terry_mc.yc_auto_fishing.YCAutoFishing.init();
    }
    private static Lazy<KeyMapping> toggleAutoFishing = Lazy.of(()->new KeyMapping(
            "key.yc_auto_fishing.toggle_auto_fishing",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            "category.yc_auto_fishing"
    ));
    private static boolean isToggleAutoFishingDown;
    @SubscribeEvent
    public void onTick(ClientTickEvent.Post event) {
        YCAutoFishing.onTick();
        if(toggleAutoFishing.get().isDown()) {
            isToggleAutoFishingDown = true;
        }
        else if(isToggleAutoFishingDown) {
            isToggleAutoFishingDown = false;
            YCAutoFishing.autoFishingEnabled = !YCAutoFishing.autoFishingEnabled;
            if(YCAutoFishing.autoFishingEnabled) {
                TextDisplayNavigator.navigateToLongestTextDisplay();
            }
            else if (TextDisplayNavigator.followProcess!=null) {
                TextDisplayNavigator.followProcess.cancel();
            }
        }
    }
    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
        YCAutoFishing.onChatMessage(event.getMessage());
    }
    @SubscribeEvent
    public void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(toggleAutoFishing.get());
    }
}
