package de.dafuqs.additionalentityattributes.mixin.common;

import com.llamalad7.mixinextras.sugar.Local;
import de.dafuqs.additionalentityattributes.Support;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TargetingConditions.class)
public class TargetingConditionsMixin {
    @ModifyArg(method = "test", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(DD)D"), index = 1)
    private double additionalEntityAttributes$setMobDetectionMinimum(double original, @Local(ordinal = 1, argsOnly = true) LivingEntity targetEntity) {
        return Support.getMobDetectionValue(targetEntity, original);
    }
}
