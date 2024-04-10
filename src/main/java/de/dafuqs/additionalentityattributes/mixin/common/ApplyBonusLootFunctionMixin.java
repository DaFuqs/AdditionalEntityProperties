package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ApplyBonusCount.class)
public abstract class ApplyBonusLootFunctionMixin {

	@Shadow
	@Final
	private ApplyBonusCount.Formula formula;

	@Shadow
	@Final
	private Holder<Enchantment> enchantment;

	@ModifyVariable(method = "run", at = @At(value = "STORE"), ordinal = 1)
	public int additionalEntityAttributes$applyBonusLoot(int oldValue, ItemStack stack, LootContext context) {
		// if the player has the ANOTHER_DRAW effect the bonus loot of
		// this function gets rerolled potency+1 times and the best one taken
		ItemStack itemStack = context.getParamOrNull(LootContextParams.TOOL);
		Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
		if (itemStack != null && entity instanceof LivingEntity livingEntity) {
			int enchantmentLevel = itemStack.getEnchantmentLevel(this.enchantment.value());
			if (enchantmentLevel > 0) {
				AttributeInstance attributeInstance = livingEntity.getAttribute(AdditionalEntityAttributes.BONUS_LOOT_COUNT_ROLLS.get());
				if (attributeInstance != null) {
					int bonusRollCount = (int) attributeInstance.getValue();
					int highestRoll = oldValue;
					for (int i = 0; i < bonusRollCount; i++) {
						int thisRoll = this.formula.calculateNewCount(context.getRandom(), stack.getCount(), enchantmentLevel);
						highestRoll = Math.max(highestRoll, thisRoll);
					}
					return highestRoll;
				}
			}
		}
		return oldValue;
	}
	
}
