package de.dafuqs.additionalentityattributes;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

public class Support {
	
	public static float getExperienceMod(Player player) {
		if(player == null) {
			return 1.0F;
		}
		AttributeInstance attributeInstance = player.getAttribute(AdditionalEntityAttributes.DROPPED_EXPERIENCE.get());
		if (attributeInstance == null) {
			return 1.0F;
		}
		return (float) attributeInstance.getValue();
	}
	
}
