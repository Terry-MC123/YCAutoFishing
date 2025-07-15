package top.terry_mc.yc_auto_fishing.mixin;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.terry_mc.yc_auto_fishing.TextDisplayNavigator;
import top.terry_mc.yc_auto_fishing.YCAutoFishing;

import java.util.ArrayList;
import java.util.List;

@Mixin(Gui.class)
public class GuiMixin {
    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "setSubtitle", at = @At("HEAD"))
    public void setSubtitleMixin(Component component, CallbackInfo ci) {
        YCAutoFishing.onTitle(component);
    }
    @Inject(method = "setTitle", at = @At("HEAD"))
    public void setTitleMixin(Component component, CallbackInfo ci) {
        YCAutoFishing.onTitle(component);
    }
    @Inject(method = "render", at = @At("TAIL"))
    public void renderMixin(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if(!YCAutoFishing.isOnYCServer()) return;
        Window window = this.minecraft.getWindow();
        List<String> lines = new ArrayList<>();
        lines.add("YC Auto Fishing Overlay");
        lines.add("");
        lines.add("Rendered fish schools: "+ TextDisplayNavigator.fishSchoolCount);
        lines.add("");
        if(TextDisplayNavigator.targetDisplay!=null) {
            lines.add("Current target:");
            lines.add("");
            lines.addAll(List.of((TextDisplayNavigator.targetDisplay.textRenderState().text().getString().split("\n"))));
            for(int i=0;i<lines.size();i++) {
                guiGraphics.drawString(this.minecraft.font, lines.get(i), 0, window.getGuiScaledHeight()/4 + 10 * (i+1), 0xffffffff);
            }
        }
    }
}
