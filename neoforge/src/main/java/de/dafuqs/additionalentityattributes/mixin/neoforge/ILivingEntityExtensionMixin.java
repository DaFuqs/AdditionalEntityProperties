package de.dafuqs.additionalentityattributes.mixin.neoforge;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
import net.neoforged.neoforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ILivingEntityExtension.class)
public interface ILivingEntityExtensionMixin {

    @ModifyExpressionValue(method = "jumpInFluid", at = @At(value = "CONSTANT", args = "doubleValue=0.03999999910593033D"))
    default double additionalEntityAttributes$modifyUpwardSwimming(double original, FluidType type) {
        if (type == NeoForgeMod.WATER_TYPE) {
            AttributeInstance waterSpeed = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.WATER_SPEED);
            if (waterSpeed == null) {
                return original;
            }
            if (waterSpeed.getBaseValue() != original) {
                waterSpeed.setBaseValue(original);
            }
            return waterSpeed.getValue();
        }
        return original;
    }

    @ModifyExpressionValue(method = "sinkInFluid", at = @At(value = "CONSTANT", args = "doubleValue=-0.03999999910593033D"))
    default double additionalEntityAttributes$knockDownwards(double original, FluidType type) {
        if (type == NeoForgeMod.WATER_TYPE) {
            AttributeInstance waterSpeed = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.WATER_SPEED);
            if (waterSpeed == null) {
                return original;
            }
            if (waterSpeed.getBaseValue() != -original) {
                waterSpeed.setBaseValue(-original);
            }
            return -waterSpeed.getValue();
        }
        return original;
    }
}