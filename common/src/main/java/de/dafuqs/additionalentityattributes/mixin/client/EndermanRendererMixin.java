package de.dafuqs.additionalentityattributes.mixin.client;

import de.dafuqs.additionalentityattributes.Support;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.world.entity.monster.EnderMan;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// We run at a lower priority because we clamp values.
// This is so Pehkui and other mods that may touch this may run without limits.
@Mixin(value = EndermanRenderer.class, priority = 500)
public class EndermanRendererMixin {
    @ModifyVariable(method = "getRenderOffset(Lnet/minecraft/world/entity/monster/EnderMan;F)Lnet/minecraft/world/phys/Vec3;", at = @At("LOAD"))
    private double additionalEntityAttributes$applyModelScaleToEndermanOffset(double d, EnderMan endermanEntity, float f) {
        return Support.getModelWidth(endermanEntity, d / 0.02) * 0.02;
    }
}
