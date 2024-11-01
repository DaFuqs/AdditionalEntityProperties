package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LootItemRandomChanceCondition.class)
public abstract class LootItemRandomChanceConditionMixin {

	@Shadow
	@Final
	private NumberProvider chance;

	@Inject(at = @At("RETURN"), method = "test(Lnet/minecraft/world/level/storage/loot/LootContext;)Z", cancellable = true)
	public void additionalEntityAttributes$applyBonusLoot(LootContext lootContext, CallbackInfoReturnable<Boolean> cir) {
		// if the result was to not drop a drop before reroll
		if (!cir.getReturnValue() && lootContext.getParamOrNull(LootContextParams.ATTACKING_ENTITY) instanceof LivingEntity livingEntity) {
			AttributeInstance attributeInstance = livingEntity.getAttribute(AdditionalEntityAttributes.BONUS_RARE_LOOT_ROLLS);
			if (attributeInstance != null) {
				cir.setReturnValue(lootContext.getRandom().nextFloat() < this.chance.getFloat(lootContext) * (float) attributeInstance.getValue());
			}
		}
	}
	
}
