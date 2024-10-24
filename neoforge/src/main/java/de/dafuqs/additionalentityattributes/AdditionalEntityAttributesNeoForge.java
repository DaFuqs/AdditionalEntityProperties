package de.dafuqs.additionalentityattributes;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(modid = AdditionalEntityAttributes.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class AdditionalEntityAttributesNeoForge {
    @SubscribeEvent
    public static void registerContent(RegisterEvent event) {
        AdditionalEntityAttributes.init();
    }

    @SubscribeEvent
    public static void modifyAttributes(EntityAttributeModificationEvent event) {
        for (EntityType<? extends LivingEntity> type : event.getTypes()) {
            event.add(type, AdditionalEntityAttributes.WATER_SPEED);
            event.add(type, AdditionalEntityAttributes.LAVA_SPEED);
            event.add(type, AdditionalEntityAttributes.WIDTH);
            event.add(type, AdditionalEntityAttributes.HEIGHT);
            event.add(type, AdditionalEntityAttributes.HITBOX_SCALE);
            event.add(type, AdditionalEntityAttributes.HITBOX_WIDTH);
            event.add(type, AdditionalEntityAttributes.HITBOX_HEIGHT);
            event.add(type, AdditionalEntityAttributes.MODEL_SCALE);
            event.add(type, AdditionalEntityAttributes.MODEL_WIDTH);
            event.add(type, AdditionalEntityAttributes.MODEL_HEIGHT);
            event.add(type, AdditionalEntityAttributes.MOB_DETECTION_RANGE);
            event.add(type, AdditionalEntityAttributes.MAGIC_PROTECTION);
        }
        event.add(EntityType.PLAYER, AdditionalEntityAttributes.WATER_VISIBILITY);
        event.add(EntityType.PLAYER, AdditionalEntityAttributes.LAVA_VISIBILITY);
        event.add(EntityType.PLAYER, AdditionalEntityAttributes.CRITICAL_BONUS_DAMAGE);
        event.add(EntityType.PLAYER, AdditionalEntityAttributes.BONUS_LOOT_COUNT_ROLLS);
        event.add(EntityType.PLAYER, AdditionalEntityAttributes.BONUS_RARE_LOOT_ROLLS);
        event.add(EntityType.PLAYER, AdditionalEntityAttributes.DROPPED_EXPERIENCE);
        event.add(EntityType.PLAYER, AdditionalEntityAttributes.COLLECTION_RANGE);
    }
}