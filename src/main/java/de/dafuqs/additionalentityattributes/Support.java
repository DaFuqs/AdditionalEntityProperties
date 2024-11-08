package de.dafuqs.additionalentityattributes;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.player.PlayerEntity;

public class Support {
	
	public static float getExperienceMod(PlayerEntity player) {
		if(player == null) {
			return 1.0F;
		}
		EntityAttributeInstance attributeInstance = player.getAttributeInstance(AdditionalEntityAttributes.DROPPED_EXPERIENCE);
		if (attributeInstance == null) {
			return 1.0F;
		}
		return (float) attributeInstance.getValue();
	}
	
	public static double getMobDetectionValue(LivingEntity entity, double original) {
		if (entity == null) {
			return original;
		}
		
		EntityAttributeInstance instance = entity.getAttributeInstance(AdditionalEntityAttributes.MOB_DETECTION_RANGE);
		if (instance != null) {
			for (EntityAttributeModifier modifier : instance.getModifiers()) {
				float amount = (float) modifier.getValue();
				
				if (modifier.getOperation() == EntityAttributeModifier.Operation.ADDITION)
					original += amount;
				else
					original *= (amount + 1);
			}
		}
		
		return original;
	}
	
}
