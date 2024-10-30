package de.dafuqs.additionalentityattributes.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {
	
	@ModifyExpressionValue(method = "setupFog", at = @At(value = "CONSTANT", args = "floatValue=0.25F", ordinal = 0))
	private static float additionalEntityAttributes$modifyLavaVisibilityMinWithoutFireResistance(float original, Camera camera) {
		AttributeInstance lavaVisibilityAttribute = camera.getEntity() instanceof LivingEntity living ? living.getAttribute(AdditionalEntityAttributes.LAVA_VISIBILITY) : null;
		if (lavaVisibilityAttribute == null) {
			return original;
		} else {
			if (lavaVisibilityAttribute.getBaseValue() != original) {
				lavaVisibilityAttribute.setBaseValue(original);
			}
			return original - (float) lavaVisibilityAttribute.getValue() * 0.25F;
		}
	}
	
	@ModifyExpressionValue(method = "setupFog", at = @At(value = "CONSTANT", args = "floatValue=1.0F", ordinal = 0))
	private static float additionalEntityAttributes$modifyLavaVisibilityMaxWithoutFireResistance(float original, Camera camera) {
		AttributeInstance lavaVisibilityAttribute = camera.getEntity() instanceof LivingEntity living ? living.getAttribute(AdditionalEntityAttributes.LAVA_VISIBILITY) : null;
		if (lavaVisibilityAttribute == null) {
			return original;
		} else {
			if (lavaVisibilityAttribute.getBaseValue() != original) {
				lavaVisibilityAttribute.setBaseValue(original);
			}
			return (float) lavaVisibilityAttribute.getValue();
		}
	}
	
	@ModifyExpressionValue(method = "setupFog", at = @At(value = "CONSTANT", args = "floatValue=0.0F", ordinal = 0))
	private static float additionalEntityAttributes$modifyLavaVisibilityMinFireResistance(float original, Camera camera) {
		AttributeInstance lavaVisibilityAttribute = camera.getEntity() instanceof LivingEntity living ? living.getAttribute(AdditionalEntityAttributes.LAVA_VISIBILITY) : null;
		if (lavaVisibilityAttribute == null) {
			return original;
		} else {
			if (lavaVisibilityAttribute.getBaseValue() != original) {
				lavaVisibilityAttribute.setBaseValue(original);
			}
			return original - (float) lavaVisibilityAttribute.getValue();
		}
	}

	@ModifyExpressionValue(method = "setupFog", at = @At(value = "CONSTANT", args = "floatValue=5.0F", ordinal = 0))
	private static float additionalEntityAttributes$modifyLavaVisibilityMaxWithFireResistance(float original, Camera camera) {
		AttributeInstance lavaVisibilityAttribute = camera.getEntity() instanceof LivingEntity living ? living.getAttribute(AdditionalEntityAttributes.LAVA_VISIBILITY) : null;
		if (lavaVisibilityAttribute == null) {
			return original;
		} else {
			if (lavaVisibilityAttribute.getBaseValue() != original) {
				lavaVisibilityAttribute.setBaseValue(original);
			}
			return (float) lavaVisibilityAttribute.getValue();
		}
	}
	
	@ModifyExpressionValue(method = "setupFog", at = @At(value = "CONSTANT", args = "floatValue=96F", ordinal = 0))
	private static float additionalEntityAttributes$modifyWaterVisibility(float original, Camera camera) {
		AttributeInstance waterVisibilityAttribute = camera.getEntity() instanceof LivingEntity living ? living.getAttribute(AdditionalEntityAttributes.WATER_VISIBILITY) : null;
		if (waterVisibilityAttribute == null) {
			return original;
		} else {
			if (waterVisibilityAttribute.getBaseValue() != original) {
				waterVisibilityAttribute.setBaseValue(original);
			}
			return (float) waterVisibilityAttribute.getValue();
		}
	}
	
}