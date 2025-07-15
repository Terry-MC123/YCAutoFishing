package top.terry_mc.yc_auto_fishing;

import net.minecraft.client.Minecraft;
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
        if((component.getString().contains("右键") || component.getString().contains("剩余点击")) && minecraft.player != null && minecraft.level != null && minecraft.gameMode!=null && minecraft.player.getMainHandItem().getItem() == Items.FISHING_ROD) {
            result = minecraft.gameMode.useItem(minecraft.player, InteractionHand.MAIN_HAND);
            if(result.shouldSwing() && component.getString().contains("剩余点击")) minecraft.player.swing(InteractionHand.MAIN_HAND);
        }
    }
}
