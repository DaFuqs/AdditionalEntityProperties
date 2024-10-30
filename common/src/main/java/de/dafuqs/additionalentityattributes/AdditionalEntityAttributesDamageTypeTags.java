package de.dafuqs.additionalentityattributes;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class AdditionalEntityAttributesDamageTypeTags {

    public static final TagKey<DamageType> IS_MAGIC = TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(AdditionalEntityAttributes.MOD_ID, "is_magic"));

}
