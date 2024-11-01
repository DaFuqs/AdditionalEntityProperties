package de.dafuqs.additionalentityattributes.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import de.dafuqs.additionalentityattributes.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyArg(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V", ordinal = 0))
    public float additionalEntityAttributes$waterSpeed(float original) {
        AttributeInstance waterSpeed = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.WATER_SPEED);
        if (waterSpeed == null) {
            return original;
        } else {
            if (waterSpeed.getBaseValue() != original) {
                waterSpeed.setBaseValue(original);
            }
            return (float) waterSpeed.getValue();
        }
    }

    @ModifyExpressionValue(method = "travel", at = {@At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 0), @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 1), @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 2)})
    private double additionalEntityAttributes$increasedLavaSpeed(double original) {
        AttributeInstance lavaSpeed = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.LAVA_SPEED);
        if (lavaSpeed == null) {
            return original;
        } else {
            if (lavaSpeed.getBaseValue() != original) {
                lavaSpeed.setBaseValue(original);
            }
            return lavaSpeed.getValue();
        }
    }

    @ModifyVariable(method = "getDamageAfterMagicAbsorb", at = @At(value = "LOAD", ordinal = 4), argsOnly = true)
    private float additionalEntityAttributes$reduceMagicDamage(float damage, DamageSource source) {
        AttributeInstance magicProt = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.MAGIC_PROTECTION);

        if (magicProt == null) {
            return damage;
        }

        if (source.is(AdditionalEntityAttributesDamageTypeTags.IS_MAGIC) && magicProt.getValue() > 0) {
            damage = (float) Math.max(damage - magicProt.getValue(), 0);
        }
        return damage;
    }
}