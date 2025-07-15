package top.terry_mc.yc_auto_fishing.neoforge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientChatReceivedEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import top.terry_mc.yc_auto_fishing.YCAutoFishing;

@EventBusSubscriber
@Mod(top.terry_mc.yc_auto_fishing.YCAutoFishing.MOD_ID)
public final class YCAutoFishingNeoForge {
    public YCAutoFishingNeoForge() {
        // Run our common setup.
        top.terry_mc.yc_auto_fishing.YCAutoFishing.init();
    }
    @SubscribeEvent
    public void onTick(ClientTickEvent.Post event) {
        YCAutoFishing.onTick();
    }
    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
        YCAutoFishing.onChatMessage(event.getMessage());
    }
}
