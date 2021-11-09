package com.yuo.endless.Items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InfinityArrow extends ArrowItem {

    public InfinityArrow() {
        super(new Properties());
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        return super.createArrow(worldIn, stack, shooter);
    }
}
