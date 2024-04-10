package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LootItemRandomChanceWithLootingCondition.class)
public abstract class RandomChanceWithLootingLootConditionMixin {
	
	@Shadow
	@Final
	private float percent;
	
	@Inject(at = @At("RETURN"), method = "test(Lnet/minecraft/world/level/storage/loot/LootContext;)Z", cancellable = true)
	public void additionalEntityAttributes$applyBonusLoot(LootContext lootContext, CallbackInfoReturnable<Boolean> cir) {
		// if the result was to not drop a drop before reroll
		if (!cir.getReturnValue() && this.percent < 1.0F && lootContext.getParamOrNull(LootContextParams.KILLER_ENTITY) instanceof LivingEntity livingEntity) {
			AttributeInstance attributeInstance = livingEntity.getAttribute(AdditionalEntityAttributes.BONUS_RARE_LOOT_ROLLS.get());
			if (attributeInstance != null) {
				cir.setReturnValue(lootContext.getRandom().nextFloat() < this.percent * (float) attributeInstance.getValue());
			}
		}
	}
	
}
