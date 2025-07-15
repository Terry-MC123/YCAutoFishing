package top.terry_mc.yc_auto_fishing;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.filter.StringMatchFilter;

public final class YCAutoFishing {
    public static final String MOD_ID = "yc_auto_fishing";
    public static final Logger LOGGER = LogManager.getLogger("YC Auto Fishing");

    public static void init() {
        // Write common init code here.
        LOGGER.info("YC Auto Fishing loaded.");
        // Remove Repeating Log
        ((org.apache.logging.log4j.core.Logger)LogManager.getRootLogger()).addFilter(new StringMatchFilter.Builder().setMatchString("Received passengers for unknown entity").build());
    }

    public static void onTitle(Component component) {
        Minecraft minecraft = Minecraft.getInstance();
        InteractionResult result;
        if(isOnYCServer() && (component.getString().contains("右键") || component.getString().contains("剩余点击")) && minecraft.player != null && minecraft.level != null && minecraft.gameMode!=null && minecraft.player.getMainHandItem().getItem() == Items.FISHING_ROD) {
            result = minecraft.gameMode.useItem(minecraft.player, InteractionHand.MAIN_HAND);
            if(result.shouldSwing() && component.getString().contains("剩余点击")) minecraft.player.swing(InteractionHand.MAIN_HAND);
        }
    }

    public static void onChatMessage(Component component) {
        if(isOnYCServer() && component.getString().contains("没有鱼")) {
            TextDisplayNavigator.navigateToLongestTextDisplay();
            onTitle(Component.nullToEmpty("剩余点击"));//我表示不想写了，直接调用已经写好的得了（
        }
    }

    public static void onTick() {
        if(isOnYCServer()) TextDisplayNavigator.tick();
    }

    private static boolean isOnYCServer() {
        ServerData serverData = Minecraft.getInstance().getCurrentServer();
        return serverData != null && serverData.ip.contains("ycraft.cn");
    }
}
