package me.krxwallo.switcher.mixin;

import net.minecraft.client.gui.screen.GameModeSelectionScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameModeSelectionScreen.class)
public class SwitcherScreenMixin {
    @Redirect(method = "apply(Lnet/minecraft/client/MinecraftClient;Ljava/util/Optional;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasPermissionLevel(I)Z"))
    private static boolean apply(ClientPlayerEntity player, int level) {
        return true; // Always send the command, even without the permission level
    }
}
