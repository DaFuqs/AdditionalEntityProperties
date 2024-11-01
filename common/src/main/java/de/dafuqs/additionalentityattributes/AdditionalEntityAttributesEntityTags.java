package de.dafuqs.additionalentityattributes;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class AdditionalEntityAttributesEntityTags {
	
	public static final TagKey<EntityType<?>> AFFECTED_BY_COLLECTION_RANGE = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(AdditionalEntityAttributes.MOD_ID, "affected_by_collection_range"));

}