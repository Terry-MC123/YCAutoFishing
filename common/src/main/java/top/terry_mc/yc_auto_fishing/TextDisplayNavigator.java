package top.terry_mc.yc_auto_fishing;

import baritone.api.utils.input.Input;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Display.TextDisplay;
import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.process.IFollowProcess;
import net.minecraft.world.entity.Entity;

public class TextDisplayNavigator {
    public static final double STOP_DISTANCE = 1.5;
    public static TextDisplay targetDisplay;
    public static IFollowProcess followProcess;

    public static void navigateToLongestTextDisplay() {
        IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
        TextDisplay target = findLongestTextDisplay();
        if (target == null) return;

        targetDisplay = target;
        followProcess = baritone.getFollowProcess();
        followProcess.follow((entity)-> entity.getUUID().equals(targetDisplay.getUUID()));
    }

    public static void tick() {
        Minecraft mc = Minecraft.getInstance();
        BaritoneAPI.getSettings().allowBreak.value=false;
        BaritoneAPI.getSettings().allowSprint.value=true;
        IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();

        if (mc.player == null || targetDisplay == null || !targetDisplay.isAlive()) {
            cleanup();
            return;
        }

        double distance = mc.player.position().distanceTo(targetDisplay.position());
        if (distance < STOP_DISTANCE) {
            baritone.getPathingBehavior().cancelEverything();
            mc.player.getInventory().selected = 5;
            mc.getConnection().send(new ServerboundSetCarriedItemPacket(5));
            mc.player.setXRot(65.0F);
            if (mc.gameMode != null) {
                mc.gameMode.useItem(mc.player, InteractionHand.MAIN_HAND);
            }
            mc.player.swing(InteractionHand.MAIN_HAND);
            cleanup();
        }
    }

    private static void cleanup() {
        targetDisplay = null;
        if(followProcess == null) return;
        followProcess.cancel();
        followProcess = null;
    }

    private static TextDisplay findLongestTextDisplay() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return null;

        TextDisplay longestDisplay = null;
        int maxLines = 0;

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity instanceof TextDisplay display) {
                if(!display.textRenderState().text().getString().contains("鱼群")) continue;

                int lineCount = getTextLineCount(display);

                if (lineCount > maxLines) {
                    maxLines = lineCount;
                    longestDisplay = display;
                }
            }
        }

        return longestDisplay;
    }

    private static int getTextLineCount(TextDisplay display) {
        Component text = null;
        if (display.textRenderState() != null) {
            text = display.textRenderState().text();
        }
        if (text == null) return 0;

        return text.getString().split("\n").length;
    }
}