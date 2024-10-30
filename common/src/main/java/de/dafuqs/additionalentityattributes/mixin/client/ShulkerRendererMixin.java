package de.dafuqs.additionalentityattributes.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import de.dafuqs.additionalentityattributes.Support;
import net.minecraft.client.renderer.entity.ShulkerRenderer;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// We run at a lower priority because we clamp values.
// This is so Pehkui and other mods that may touch this may run without limits.
@Mixin(value = ShulkerRenderer.class, priority = 500)
public class ShulkerRendererMixin {
    @ModifyReturnValue(method = "getRenderOffset(Lnet/minecraft/world/entity/monster/Shulker;F)Lnet/minecraft/world/phys/Vec3;", at = @At("RETURN"))
    private Vec3 additionalEntityAttributes$applyModelScaleToShulkerOffset(Vec3 original, Shulker shulkerEntity, float f) {
        return original.multiply(Support.getModelWidth(shulkerEntity, 1.0F), Support.getModelHeight(shulkerEntity, 1.0F), Support.getModelWidth(shulkerEntity, 1.0F));
    }
}
