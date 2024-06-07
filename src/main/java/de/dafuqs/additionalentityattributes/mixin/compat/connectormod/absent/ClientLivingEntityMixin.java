package de.dafuqs.additionalentityattributes.mixin.compat.connectormod.absent;

import com.llamalad7.mixinextras.injector.*;
import de.dafuqs.additionalentityattributes.*;
import net.fabricmc.api.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Environment(EnvType.CLIENT)
@Mixin(LivingEntity.class)
public abstract class ClientLivingEntityMixin {
	
	@ModifyExpressionValue(method = "knockDownwards", at = @At(value = "CONSTANT", args = "doubleValue=-0.03999999910593033D"))
	public double additionalEntityAttributes$knockDownwards(double original) {
		EntityAttributeInstance waterSpeed = ((LivingEntity) (Object) this).getAttributeInstance(AdditionalEntityAttributes.WATER_SPEED);
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