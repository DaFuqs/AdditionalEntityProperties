package de.dafuqs.additionalentityattributes;

import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.material.FogType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public class AdditionalEntityAttributesEvents {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRenderFog(ViewportEvent.RenderFog e) {
        if (e.getType().equals(FogType.LAVA)) {
            AttributeInstance lavaVisibilityAttribute = Minecraft.getInstance().player.getAttribute(AdditionalEntityAttributes.LAVA_VISIBILITY.get());
            if (lavaVisibilityAttribute == null) {
                return;
            }
            if (e.getCamera().getEntity() instanceof LivingEntity living && living.hasEffect(MobEffects.FIRE_RESISTANCE)) {
                e.setNearPlaneDistance((e.getNearPlaneDistance() - (float) lavaVisibilityAttribute.getValue()));
            }
            else {
                e.scaleNearPlaneDistance(((float) lavaVisibilityAttribute.getValue()));
            }
            e.scaleFarPlaneDistance(e.getFarPlaneDistance() * (float) lavaVisibilityAttribute.getValue());
            e.setCanceled(true);
        }
        else if (e.getType().equals(FogType.WATER)) {
            AttributeInstance waterVisibilityAttribute = Minecraft.getInstance().player.getAttribute(AdditionalEntityAttributes.WATER_VISIBILITY.get());
            if (waterVisibilityAttribute == null) {
                return;
            }
            if (waterVisibilityAttribute.getBaseValue() != e.getFarPlaneDistance()) {
                waterVisibilityAttribute.setBaseValue(e.getFarPlaneDistance());
            }
            e.setFarPlaneDistance(e.getFarPlaneDistance() + (float) waterVisibilityAttribute.getValue());
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent e) {
        e.setExpToDrop((int) (e.getExpToDrop() * Support.getExperienceMod(e.getPlayer())));
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

    @SubscribeEvent
    public void blockBreakSpeed(PlayerEvent.BreakSpeed e) {
        float f = e.getOriginalSpeed();
        AttributeInstance instance = e.getEntity().getAttribute(AdditionalEntityAttributes.DIG_SPEED.get());

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

    @SubscribeEvent
    public void onLivingExperienceDrop(LivingExperienceDropEvent e) {
        if (!(e.getAttackingPlayer() == null)) {
            e.setDroppedExperience((int) (e.getDroppedExperience() * Support.getExperienceMod(e.getAttackingPlayer())));
        }
    }
}
