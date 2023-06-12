package me.krxwallo.switcher.mixin;

import com.google.common.base.MoreObjects;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameModeSelectionScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Keyboard.class)
public class KeyboardMixin {
	@Shadow @Final private MinecraftClient client;

	/**
	 * Injection to always:
	 * - open the game mode selection screen when F3+F4 is pressed
	 * - or switch the game mode accordingly when F3+N is pressed,
	 * thereby bypassing the permission check when opening.
	 * @param key the key that was pressed with F3
	 */
	@Inject(at = @At("HEAD"), method = "processF3", cancellable = true)
	private void processF3(int key, CallbackInfoReturnable<Boolean> cir) {
		switch (key) {
			case 293 -> { // F4
				MinecraftClient.getInstance().setScreen(new GameModeSelectionScreen());
				cir.setReturnValue(true);
			}
			case 78 -> { // N
				if (client.player == null) return;
				if (client.interactionManager == null) return;
				if (!this.client.player.isSpectator())
					this.client.player.networkHandler.sendCommand("gamemode spectator");
				else {
					ClientPlayNetworkHandler networkHandler = this.client.player.networkHandler;
					GameMode previousGameMode = this.client.interactionManager.getPreviousGameMode();
					networkHandler.sendCommand("gamemode " + MoreObjects.firstNonNull(previousGameMode, GameMode.CREATIVE).getName());
				}
				cir.setReturnValue(true);
			}
		}

	}
}
