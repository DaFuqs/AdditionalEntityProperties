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

    @Shadow
    @Nullable
    protected Player lastHurtByPlayer;


    @SubscribeEvent
    public void livingEntityAttributes(EntityAttributeModificationEvent e) {
        for (EntityType<? extends LivingEntity> living : e.getTypes()) {
//            e.add(living, AdditionalEntityAttributes.WATER_SPEED.get());
            e.add(living, AdditionalEntityAttributes.LAVA_SPEED.get());
            e.add(living, AdditionalEntityAttributes.LUNG_CAPACITY.get());
            e.add(living, AdditionalEntityAttributes.JUMP_HEIGHT.get());
            e.add(living, AdditionalEntityAttributes.MAGIC_PROTECTION.get());
        }
    }

    @ModifyExpressionValue(method = "swimUpward", at = @At(value = "CONSTANT", args = "doubleValue=0.03999999910593033D"))
    public double additionalEntityAttributes$modifyUpwardSwimming(double original, TagKey<Fluid> fluid) {
        if (fluid == FluidTags.WATER) {
            AttributeInstance waterSpeed = ((LivingEntity) (Object) this).getAttributeInstance(AdditionalEntityAttributes.WATER_SPEED);
            if (waterSpeed == null) {
                return original;
            } else {
                if (waterSpeed.getBaseValue() != original) {
                    waterSpeed.setBaseValue(original);
                }
                return waterSpeed.getValue();
            }
        } else {
            return original;
        }
    }

    @Environment(EnvType.CLIENT)
    @ModifyExpressionValue(method = "knockDownwards", at = @At(value = "CONSTANT", args = "doubleValue=-0.03999999910593033D"))
    public double additionalEntityAttributes$knockDownwards(double original) {
        AttributeInstance waterSpeed = ((LivingEntity) (Object) this).getAttributeInstance(AdditionalEntityAttributes.WATER_SPEED);
        if (waterSpeed == null) {
            return original;
        } else {
            if (waterSpeed.getBaseValue() != -original) {
                waterSpeed.setBaseValue(-original);
            }
            return -waterSpeed.getValue();
        }
    }

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

    @SubscribeEvent
    public void onLivingExperienceDrop(LivingExperienceDropEvent e) {
        if (!(e.getAttackingPlayer() == null)) {
            e.setDroppedExperience((int) (e.getDroppedExperience() * Support.getExperienceMod(e.getAttackingPlayer())));
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