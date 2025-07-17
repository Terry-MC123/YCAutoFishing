package top.terry_mc.yc_auto_fishing;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Display.TextDisplay;
import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.process.IFollowProcess;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class TextDisplayNavigator {
    public static final double STOP_DISTANCE = 2;
    public static TextDisplay targetDisplay;
    public static IFollowProcess followProcess;
    private static Vec3 lastPosition = null;
    private static int stuckTickCount = 0;

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
        BaritoneAPI.getSettings().sprintInWater.value=true;
        BaritoneAPI.getSettings().avoidance.value=true;
        BuiltInRegistries.BLOCK.getTag(BlockTags.FENCE_GATES).ifPresent(tag -> {
            for(Holder<Block> block:tag) {
                BaritoneAPI.getSettings().blocksToAvoid.value.add(block.value());
            }
        });
        BaritoneAPI.getSettings().followRadius.value=2;
        IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();

        if (mc.player == null || targetDisplay == null || !targetDisplay.isAlive()) {
            cleanup();
            return;
        }

        Vec3 nowPosition = mc.player.position();
        if(lastPosition!=null && nowPosition.distanceTo(lastPosition) <= 0.01) {
            stuckTickCount++;
        }
        else {
            stuckTickCount = 0;
        }
        lastPosition = nowPosition;
        if(stuckTickCount>200) {
            navigateToLongestTextDisplay();
            stuckTickCount=0;
            lastPosition = null;
            return;
        }

        double distance = nowPosition.distanceTo(targetDisplay.position());
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
                if (display.textRenderState() == null || !display.textRenderState().text().getString().contains("鱼群") || display.textRenderState().text().getString().contains("库存：空") || display.textRenderState().text().getString().contains("库存: 空") || display.textRenderState().text().getString().contains("库存：少") || display.textRenderState().text().getString().contains("库存: 少"))
                    continue;

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