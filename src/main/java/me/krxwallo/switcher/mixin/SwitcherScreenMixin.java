package me.krxwallo.switcher.mixin;

import net.minecraft.client.gui.screen.GameModeSelectionScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameModeSelectionScreen.class)
public class SwitcherScreenMixin {
    /**
     * Redirect the `hasPermissionLevel(2) call to always return true, so that the game mode command is always sent.`
     * @param player our client player
     * @param level the permission level to check/ignore
     * @return true
     */
    @Redirect(method = "apply(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/gui/screen/GameModeSelectionScreen$GameModeSelection;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasPermissionLevel(I)Z"))
    private static boolean ignorePermissionLevel(ClientPlayerEntity player, int level) {
        return true; // Always send the command, even without the permission level
    }
}
