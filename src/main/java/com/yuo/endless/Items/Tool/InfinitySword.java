package com.yuo.endless.Items.Tool;

import com.yuo.endless.Armor.InfinityArmor;
import com.yuo.endless.tab.ModGroup;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class InfinitySword extends SwordItem{

    public InfinitySword() {
        super(MyItemTier.INFINITY_ARMS, 0, -2.4f, new Properties().group(ModGroup.myGroup).isImmuneToFire());
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

    @Override
    public boolean hasEffect(ItemStack stack) {
        return false;
    }

    //是否允许铁砧附魔
    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    //攻击实体
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.world.isRemote) return true;
        if (target instanceof EnderDragonEntity && attacker instanceof PlayerEntity){
            EnderDragonEntity drageon = (EnderDragonEntity) target; //攻击末影龙
            drageon.attackEntityPartFrom(drageon.dragonPartHead, DamageSource.causePlayerDamage((PlayerEntity) attacker), Float.POSITIVE_INFINITY);
        }else if (target instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) target;
            if (InfinityArmor.FLAG){
                player.attackEntityFrom(new InfinityDamageSource(attacker), 10.0f);
            }else player.attackEntityFrom(new InfinityDamageSource(attacker), Float.POSITIVE_INFINITY);
        }
        else target.attackEntityFrom(new InfinityDamageSource(attacker), Float.POSITIVE_INFINITY);
        target.setHealth(0);
        target.onDeath(new InfinityDamageSource(attacker));
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return new EndlessItemEntity(world, location.getPosX(), location.getPosY(), location.getPosZ(), itemstack);
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }
}
