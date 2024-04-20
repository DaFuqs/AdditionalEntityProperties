package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import de.dafuqs.additionalentityattributes.AdditionalEntityAttributesEntityTags;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

@Mixin(Player.class)
public abstract class PlayerEntityMixin {

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