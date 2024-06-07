package de.dafuqs.additionalentityattributes.mixin.common;

import de.dafuqs.additionalentityattributes.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(value = Block.class)
public abstract class BlockMixin {
	
	@Unique
	private PlayerEntity additionalEntityAttributes$breakingPlayer;

	@ModifyArg(method = "dropExperience", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ExperienceOrbEntity;spawn(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/Vec3d;I)V"))
	private int additionalEntityAttributes$modifyExperience(int originalXP) {
		return (int) (originalXP * Support.getExperienceMod(additionalEntityAttributes$breakingPlayer));
	}

	@Inject(method = "afterBreak", at = @At("HEAD"))
	public void additionalEntityAttributes$saveBreakingPlayer(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack, CallbackInfo callbackInfo) {
		additionalEntityAttributes$breakingPlayer = player;
	}

}
