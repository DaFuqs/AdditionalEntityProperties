package de.dafuqs.additionalentityattributes.mixin.fabric;

import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

	@Inject(method = "createAttributes", require = 1, allow = 1, at = @At("RETURN"))
	private static void additionalEntityAttributes$addPlayerAttributes(final CallbackInfoReturnable<AttributeSupplier.Builder> info) {
		info.getReturnValue().add(AdditionalEntityAttributes.WATER_VISIBILITY);
		info.getReturnValue().add(AdditionalEntityAttributes.LAVA_VISIBILITY);
		info.getReturnValue().add(AdditionalEntityAttributes.CRITICAL_BONUS_DAMAGE);
		info.getReturnValue().add(AdditionalEntityAttributes.BONUS_LOOT_COUNT_ROLLS);
		info.getReturnValue().add(AdditionalEntityAttributes.BONUS_RARE_LOOT_ROLLS);
		info.getReturnValue().add(AdditionalEntityAttributes.DROPPED_EXPERIENCE);
		info.getReturnValue().add(AdditionalEntityAttributes.COLLECTION_RANGE);
	}
	
}