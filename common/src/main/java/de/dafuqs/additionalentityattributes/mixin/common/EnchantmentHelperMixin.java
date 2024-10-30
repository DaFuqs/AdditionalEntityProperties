package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
	
	@Inject(method = "processMobExperience", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;runIterationOnEquipment(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/enchantment/EnchantmentHelper$EnchantmentInSlotVisitor;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void additionalEntityAttributes$modifyMobExperience(ServerLevel world, Entity attacker, Entity mob, int baseMobExperience, CallbackInfoReturnable<Integer> cir, LivingEntity livingAttacker, MutableFloat mutableFloat) {
		mutableFloat.setValue(mutableFloat.floatValue() * Support.getExperienceMod(livingAttacker));
	}
	
}