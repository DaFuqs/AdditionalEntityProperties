package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyVariable(method = "getDamageAfterMagicAbsorb", at = @At(value = "LOAD", ordinal = 4), argsOnly = true)
    private float additionalEntityAttributes$reduceMagicDamage(float damage, DamageSource source) {
        AttributeInstance magicProt = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.MAGIC_PROTECTION);

        if (magicProt == null) {
            return damage;
        }

        if (source.is(AdditionalEntityAttributesDamageTypeTags.IS_MAGIC) && magicProt.getValue() > 0) {
            damage = (float) Math.max(damage - magicProt.getValue(), 0);
        }
        return damage;
    }
}