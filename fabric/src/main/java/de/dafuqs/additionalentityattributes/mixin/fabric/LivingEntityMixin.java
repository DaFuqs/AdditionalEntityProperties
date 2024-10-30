package de.dafuqs.additionalentityattributes.mixin.fabric;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "createLivingAttributes", require = 1, allow = 1, at = @At("RETURN"))
    private static void additionalEntityAttributes$addAttributes(final CallbackInfoReturnable<AttributeSupplier.Builder> info) {
        info.getReturnValue().add(AdditionalEntityAttributes.WATER_SPEED);
        info.getReturnValue().add(AdditionalEntityAttributes.LAVA_SPEED);
        info.getReturnValue().add(AdditionalEntityAttributes.WIDTH);
        info.getReturnValue().add(AdditionalEntityAttributes.HEIGHT);
        info.getReturnValue().add(AdditionalEntityAttributes.HITBOX_SCALE);
        info.getReturnValue().add(AdditionalEntityAttributes.HITBOX_WIDTH);
        info.getReturnValue().add(AdditionalEntityAttributes.HITBOX_HEIGHT);
        info.getReturnValue().add(AdditionalEntityAttributes.MODEL_SCALE);
        info.getReturnValue().add(AdditionalEntityAttributes.MODEL_WIDTH);
        info.getReturnValue().add(AdditionalEntityAttributes.MODEL_HEIGHT);
        info.getReturnValue().add(AdditionalEntityAttributes.MOB_DETECTION_RANGE);
        info.getReturnValue().add(AdditionalEntityAttributes.MAGIC_PROTECTION);
    }

    @ModifyExpressionValue(method = "jumpInLiquid", at = @At(value = "CONSTANT", args = "doubleValue=0.03999999910593033D"))
    public double additionalEntityAttributes$modifyUpwardSwimming(double original, TagKey<Fluid> fluid) {
        if (fluid == FluidTags.WATER) {
            AttributeInstance waterSpeed = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.WATER_SPEED);
            if (waterSpeed == null) {
                return original;
            } else {
                if (waterSpeed.getBaseValue() != original) {
                    waterSpeed.setBaseValue(original);
                }
                return waterSpeed.getValue();
            }
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(method = "goDownInWater", at = @At(value = "CONSTANT", args = "doubleValue=-0.03999999910593033D"))
    public double additionalEntityAttributes$knockDownwards(double original) {
        AttributeInstance waterSpeed = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.WATER_SPEED);
        if (waterSpeed == null) {
            return original;
        } else {
            if (waterSpeed.getBaseValue() != -original) {
                waterSpeed.setBaseValue(-original);
            }
            return -waterSpeed.getValue();
        }
    }
}