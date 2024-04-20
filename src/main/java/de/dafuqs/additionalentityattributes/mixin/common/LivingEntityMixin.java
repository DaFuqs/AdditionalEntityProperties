package de.dafuqs.additionalentityattributes.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import de.dafuqs.additionalentityattributes.Support;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyExpressionValue(method = "travel", at = {@At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 0), @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 1), @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 2)})
    private double additionalEntityAttributes$increasedLavaSpeed(double original) {
        AttributeInstance lavaSpeed = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.LAVA_SPEED.get());
        if (lavaSpeed == null) {
            return original;
        } else {
            if (lavaSpeed.getBaseValue() != original) {
                lavaSpeed.setBaseValue(original);
            }
            return lavaSpeed.getValue();
        }
    }

    @ModifyVariable(method = "getDamageAfterMagicAbsorb", at = @At(value = "LOAD", ordinal = 4), argsOnly = true)
    private float additionalEntityAttributes$reduceMagicDamage(float damage, DamageSource source) {
        AttributeInstance magicProt = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.MAGIC_PROTECTION.get());

        if (magicProt == null) {
            return damage;
        }

        if (source.is(DamageTypeTags.WITCH_RESISTANT_TO) && magicProt.getValue() > 0) {
            damage = (float) Math.max(damage - magicProt.getValue(), 0);
        }
        return damage;
    }

    @ModifyReturnValue(method = "getJumpPower", at = @At("RETURN"))
    public float additionalEntityAttributes$modifyJumpVelocity(float original) {
        AttributeInstance instance = ((LivingEntity) (Object) this).getAttribute(AdditionalEntityAttributes.JUMP_HEIGHT.get());

        if (instance != null) {
            float totalAmount = original;
            for (AttributeModifier modifier : instance.getModifiers()) {
                float amount = (float) modifier.getAmount();

                if (modifier.getOperation() == AttributeModifier.Operation.ADDITION)
                    totalAmount += amount;
                else
                    totalAmount *= (amount + 1);
            }

            // Players will run this method twice, so we have to do
            // some math to make sure that it's accurate.
            if ((LivingEntity)(Object)this instanceof Player) {
                totalAmount = original + (totalAmount - original) / 2;
            }
            original = totalAmount;
        }

        return original;
    }
}