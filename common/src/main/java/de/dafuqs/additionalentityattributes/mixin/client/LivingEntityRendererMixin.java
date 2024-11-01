package de.dafuqs.additionalentityattributes.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.blaze3d.vertex.PoseStack;
import de.dafuqs.additionalentityattributes.Support;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

// We run at a lower priority because we clamp values.
// This is so Pehkui and other mods that may touch this may run without limits.
@Mixin(value = LivingEntityRenderer.class, priority = 500)
public abstract class LivingEntityRendererMixin {
    @ModifyArgs(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", ordinal = 0))
    private void additionalEntityAttributes$applyModelScales(Args args, LivingEntity livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource bufferSource, int i) {
        float x = args.get(0);
        float y = args.get(1);
        float z = args.get(2);
        args.set(0, (float)Support.getModelWidth(livingEntity, x));
        args.set(1, (float)Support.getModelHeight(livingEntity, y));
        args.set(2, (float)Support.getModelWidth(livingEntity, z));
    }

    @ModifyReturnValue(method = "getShadowRadius(Lnet/minecraft/world/entity/LivingEntity;)F", at = @At("RETURN"))
    private float additionalEntityAttributes$modifyShadowRadius(float original, LivingEntity livingEntity) {
        return (float)Support.getModelWidth(livingEntity, original / ((EntityRendererAccessor)this).additionalEntityAttributes$getShadowRadius()) * ((EntityRendererAccessor)this).additionalEntityAttributes$getShadowRadius();
    }
}
