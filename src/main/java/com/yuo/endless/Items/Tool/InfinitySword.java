package com.yuo.endless.Items.Tool;

import com.yuo.endless.tab.ModGroup;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;

import java.util.HashMap;
import java.util.Map;

public class InfinitySword extends SwordItem {
    public InfinitySword() {
        super(MyItemTier.INFINITY, 0, -2.4f, new Properties().group(ModGroup.myGroup).isImmuneToFire());
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)){ //防止添加到其它物品页
            Map<Enchantment, Integer> map = new HashMap<Enchantment, Integer>();
            map.put(Enchantments.LOOTING, 10);
            ItemStack stack = new ItemStack(this);
            EnchantmentHelper.setEnchantments(map, stack);
            items.add(stack);
        }
    }

    //攻击实体
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target instanceof EnderDragonEntity){
            EnderDragonEntity drageon = (EnderDragonEntity) target; //攻击末影龙
            drageon.attackEntityPartFrom(drageon.dragonPartHead, DamageSource.causePlayerDamage((PlayerEntity) attacker), 10000);
        }else target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) attacker), 10000.0f);
        return super.hitEntity(stack, target, attacker);
    }
}
