package top.terry_mc.yc_auto_fishing.mixin;

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
        if(TextDisplayNavigator.targetDisplay!=null) {
            guiGraphics.drawString(this.minecraft.font, "Target: " + TextDisplayNavigator.targetDisplay.textRenderState().text().getString(), 0, 300, 0xffffffff);
        }
    }
}
