package top.terry_mc.yc_auto_fishing;

import baritone.api.BaritoneAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Text;

public final class YCAutoFishing {
    public static final String MOD_ID = "yc_auto_fishing";
    public static final Logger LOGGER = LogManager.getLogger("YC Auto Fishing");
    public static boolean autoFishingEnabled = false;
    public static int stuckTickCount = 0;

    public static void init() {
        // Write common init code here.
        // Remove Repeating Log
        ((org.apache.logging.log4j.core.Logger)LogManager.getRootLogger()).addFilter(new StringMismatchFilter());
        LOGGER.info("YC Auto Fishing loaded.");
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
                if(TextDisplayNavigator.followProcess==null || TextDisplayNavigator.followProcess.following().isEmpty()) {
                    stuckTickCount++;
                }
                else {
                    stuckTickCount = 0;
                }
                if (stuckTickCount > 400 && (TextDisplayNavigator.followProcess==null || TextDisplayNavigator.followProcess.following().isEmpty())) {
                    Minecraft minecraft = Minecraft.getInstance();
                    minecraft.player.getInventory().selected = 5;
                    minecraft.getConnection().send(new ServerboundSetCarriedItemPacket(5));
                    if (minecraft.player != null && minecraft.level != null && minecraft.gameMode != null && minecraft.player.getMainHandItem().getItem() == Items.FISHING_ROD) {
                        InteractionResult result = minecraft.gameMode.useItem(minecraft.player, InteractionHand.MAIN_HAND);
                        if (result.shouldSwing()) {
                            minecraft.player.swing(InteractionHand.MAIN_HAND);
                        }
                    }
                }
                if(stuckTickCount>400) stuckTickCount = 0;
            }
        }
    }

    public static boolean isOnYCServer() {
        ServerData serverData = Minecraft.getInstance().getCurrentServer();
        return serverData != null && serverData.ip.contains("ycraft.cn");
    }
}
