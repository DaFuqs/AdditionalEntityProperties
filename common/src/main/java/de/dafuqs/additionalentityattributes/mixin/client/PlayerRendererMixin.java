package de.dafuqs.additionalentityattributes.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import de.dafuqs.additionalentityattributes.Support;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// We run at a lower priority because we clamp values.
// This is so Pehkui and other mods that may touch this may run without limits.
@Mixin(value = PlayerRenderer.class, priority = 500)
public class PlayerRendererMixin {
    @ModifyArg(method = "getRenderOffset(Lnet/minecraft/client/player/AbstractClientPlayer;F)Lnet/minecraft/world/phys/Vec3;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;<init>(DDD)V"), index = 1)
    private double additionalEntityAttributes$applyModelScaleToPlayerOffset(double d, @Local(argsOnly = true) AbstractClientPlayer playerEntity) {
        return (Support.getModelHeight(playerEntity, (d * 16.0F / 2.0F)) * -2.0F) / 16.0F;
    }
}
