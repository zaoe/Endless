package com.yuo.endless.Items.Tool;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class InfinityDamageSource extends EntityDamageSource {

    public InfinityDamageSource(LivingEntity living) {
        super("infinity", living);
    }

    @Override
    public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn) {
        ItemStack itemstack = damageSourceEntity instanceof LivingEntity ? ((LivingEntity)damageSourceEntity).getHeldItem(Hand.MAIN_HAND) : null;
        String s = "death.attack.infinity";
        int rando = entityLivingBaseIn.getEntityWorld().rand.nextInt(5);
        if (rando != 0) {
            s = s + "." + rando;
        }
        return new TranslationTextComponent(s, entityLivingBaseIn.getDisplayName(), damageSourceEntity.getDisplayName(),itemstack.getDisplayName());
    }

    //是否根据难度缩放伤害值
    @Override
    public boolean isDifficultyScaled() {
        return false;
    }
}
