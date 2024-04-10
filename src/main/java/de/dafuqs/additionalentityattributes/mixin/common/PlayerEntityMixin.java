package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import de.dafuqs.additionalentityattributes.AdditionalEntityAttributesEntityTags;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import de.dafuqs.additionalentityattributes.AdditionalEntityAttributesEntityTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import java.util.List;
import java.util.function.Predicate;

@Mixin(Player.class)
public abstract class PlayerEntityMixin {
	@SubscribeEvent
	public void registerPlayerAttributes(EntityAttributeModificationEvent e) {
		e.add(EntityType.PLAYER, AdditionalEntityAttributes.WATER_VISIBILITY.get());
		e.add(EntityType.PLAYER, AdditionalEntityAttributes.LAVA_VISIBILITY.get());
		e.add(EntityType.PLAYER, AdditionalEntityAttributes.CRITICAL_BONUS_DAMAGE.get());
		e.add(EntityType.PLAYER, AdditionalEntityAttributes.DIG_SPEED.get());
		e.add(EntityType.PLAYER, AdditionalEntityAttributes.BONUS_LOOT_COUNT_ROLLS.get());
		e.add(EntityType.PLAYER, AdditionalEntityAttributes.BONUS_RARE_LOOT_ROLLS.get());
		e.add(EntityType.PLAYER, AdditionalEntityAttributes.DROPPED_EXPERIENCE.get());
		e.add(EntityType.PLAYER, AdditionalEntityAttributes.COLLECTION_RANGE.get());
	}

	/**
	 * By default, the additional crit damage is a 50% bonus
	 */
	@SubscribeEvent
	public void onCriticalHit(CriticalHitEvent e) {
		AttributeInstance criticalDamageMultiplier = e.getEntity().getAttribute(AdditionalEntityAttributes.CRITICAL_BONUS_DAMAGE.get());
		if (!(criticalDamageMultiplier == null)) {
			e.setDamageModifier(1 + (float) criticalDamageMultiplier.getValue());
		}
	}


//	@ModifyConstant(method = "attack(Lnet/minecraft/entity/Entity;)V", constant = @Constant(floatValue = 1.5F))
//	public float additionalEntityAttributes$applyCriticalBonusDamage(float original) {
//		AttributeInstance criticalDamageMultiplier = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.CRITICAL_BONUS_DAMAGE);
//		if (criticalDamageMultiplier == null) {
//			return original;
//		} else {
//			return 1 + (float) criticalDamageMultiplier.getValue();
//		}
//	}


	@SubscribeEvent
	public void blockBreakSpeed(PlayerEvent.BreakSpeed e) {
		float f = e.getOriginalSpeed();
		AttributeInstance instance = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.DIG_SPEED.get());

		if (instance != null) {
			for (AttributeModifier modifier : instance.getModifiers()) {
				float amount = (float) modifier.getAmount();

				if (modifier.getOperation() == AttributeModifier.Operation.ADDITION)
					f += amount;
				else
					f *= (amount + 1);
			}
			e.setNewSpeed(f);
		}
	}

//	@ModifyVariable(method = "getDigSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffectUtil;hasDigSpeed(Lnet/minecraft/world/entity/LivingEntity;)Z"), index = 2)
//	private float additionalEntityAttributes$adjustBlockBreakingSpeed(float f) {
//		AttributeInstance instance = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.DIG_SPEED.get());
//
//		if (instance != null) {
//			for (AttributeModifier modifier : instance.getModifiers()) {
//				float amount = (float) modifier.getAmount();
//
//				if (modifier.getOperation() == AttributeModifier.Operation.ADDITION)
//					f += amount;
//				else
//					f *= (amount + 1);
//			}
//		}
//
//		return f;
//	}

	@ModifyVariable(method = "aiStep", at = @At("STORE"))
	private List<Entity> additionalEntityAttributes$adjustCollectionRange(List<Entity> original) {
		Player thisPlayer = (Player)(Object) this;
		AttributeInstance instance = thisPlayer.getAttribute(AdditionalEntityAttributes.COLLECTION_RANGE.get());

		if (instance != null && instance.getValue() > 0) {
			AABB expandedBox;
			if (thisPlayer.isPassenger() && !thisPlayer.getVehicle().isRemoved()) {
				expandedBox = thisPlayer.getBoundingBox().minmax(thisPlayer.getVehicle().getBoundingBox()).inflate(1.0, 0.0, 1.0).inflate(instance.getValue());
			} else {
				expandedBox = thisPlayer.getBoundingBox().inflate(1.0, 0.5, 1.0).inflate(instance.getValue());
			}

			original.addAll(thisPlayer.level().getEntities(thisPlayer, expandedBox, new Predicate<Entity>() {
				@Override
				public boolean test(Entity entity) {
					EntityType<?> type = entity.getType();
					return type.is(AdditionalEntityAttributesEntityTags.AFFECTED_BY_COLLECTION_RANGE) && !original.contains(entity);
				}
			}));
		}
		return original;
	}
	
}