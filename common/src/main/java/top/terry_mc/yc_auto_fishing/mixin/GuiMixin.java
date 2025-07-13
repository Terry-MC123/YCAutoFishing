package top.terry_mc.yc_auto_fishing.mixin;

import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.terry_mc.yc_auto_fishing.YCAutoFishing;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(method = "setSubtitle", at = @At("HEAD"))
    public void setSubtitleMixin(Component component, CallbackInfo ci) {
        YCAutoFishing.onTitle(component);
    }
    @Inject(method = "setTitle", at = @At("HEAD"))
    public void setTitleMixin(Component component, CallbackInfo ci) {
        YCAutoFishing.onTitle(component);
    }
}
