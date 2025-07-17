package top.terry_mc.yc_auto_fishing;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Text;

public final class YCAutoFishing {
    public static final String MOD_ID = "yc_auto_fishing";
    public static final Logger LOGGER = LogManager.getLogger("YC Auto Fishing");
    public static boolean autoFishingEnabled = false;
    private static int stuckTickCount = 0;

    public static void init() {
        // Write common init code here.
        LOGGER.info("YC Auto Fishing loaded.");
        // Remove Repeating Log
        ((org.apache.logging.log4j.core.Logger)LogManager.getRootLogger()).addFilter(new StringMismatchFilter());
    }

    public static void onTitle(Component component) {
        Minecraft minecraft = Minecraft.getInstance();
        InteractionResult result;
        if (isOnYCServer() && (component.getString().contains("右键") || component.getString().contains("剩余点击")) && minecraft.player != null && minecraft.level != null && minecraft.gameMode != null && minecraft.player.getMainHandItem().getItem() == Items.FISHING_ROD) {
            result = minecraft.gameMode.useItem(minecraft.player, InteractionHand.MAIN_HAND);
            if (result.shouldSwing() && component.getString().contains("剩余点击"))
                minecraft.player.swing(InteractionHand.MAIN_HAND);
            stuckTickCount = 0;
        }
    }

    public static void onChatMessage(Component component) {
        if(isOnYCServer() && (component.getString().contains("没有鱼") || component.getString().contains("发生了变动") || component.getString().contains("不在鱼群范围")) && Minecraft.getInstance().player.getMainHandItem().getItem() == Items.FISHING_ROD) {
            if (Minecraft.getInstance().gameMode.useItem(Minecraft.getInstance().player, InteractionHand.MAIN_HAND).shouldSwing())
                Minecraft.getInstance().player.swing(InteractionHand.MAIN_HAND);
            TextDisplayNavigator.navigateToLongestTextDisplay();
        }
    }

    public static void onTick() {
        if(isOnYCServer()) {
            TextDisplayNavigator.tick();
            if(autoFishingEnabled) {
                stuckTickCount++;
                if (stuckTickCount > 200 && TextDisplayNavigator.followProcess!=null && TextDisplayNavigator.followProcess.following().isEmpty()) {
                    Minecraft minecraft = Minecraft.getInstance();
                    minecraft.player.getInventory().selected = 5;
                    minecraft.getConnection().send(new ServerboundSetCarriedItemPacket(5));
                    if (minecraft.player != null && minecraft.level != null && minecraft.gameMode != null && minecraft.player.getMainHandItem().getItem() == Items.FISHING_ROD) {
                        InteractionResult result = minecraft.gameMode.useItem(minecraft.player, InteractionHand.MAIN_HAND);
                        if (result.shouldSwing()) {
                            minecraft.player.swing(InteractionHand.MAIN_HAND);
                        }
                        stuckTickCount = 0;
                    }
                }
            }
        }
    }

    public static boolean isOnYCServer() {
        ServerData serverData = Minecraft.getInstance().getCurrentServer();
        return serverData != null && serverData.ip.contains("ycraft.cn");
    }
}
