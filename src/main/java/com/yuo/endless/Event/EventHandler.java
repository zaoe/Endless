package com.yuo.endless.Event;

import com.yuo.endless.Armor.InfinityArmor;
import com.yuo.endless.Endless;
import com.yuo.endless.Items.ItemRegistry;
import com.yuo.endless.Items.Tool.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 事件处理类
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Endless.MODID)
public class EventHandler {
    public static List<String> playersWithHead = new ArrayList<String>();
    public static List<String> playersWithChest = new ArrayList<String>();
    public static List<String> playersWithLegs = new ArrayList<String>();
    public static List<String> playersWithFeet = new ArrayList<String>();

    //无尽鞋子 无摔落伤害
    @SubscribeEvent
    public static void playerFall(LivingFallEvent event) {
        LivingEntity living = event.getEntityLiving();
        if (living instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)living;
            String key = player.getGameProfile().getName()+":"+player.world.isRemote;
            if (playersWithFeet.contains(key)) {
                event.setCanceled(true);
            }
        }
    }
    //无尽装备 不受伤害
    @SubscribeEvent
    public static void opArmsImmuneDamage(LivingDamageEvent event){
        LivingEntity entityLiving = event.getEntityLiving();
        if (entityLiving instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) entityLiving;
            Boolean hasChest = player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == ItemRegistry.infinityHead.get();
            Boolean hasLeg = player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == ItemRegistry.infinityChest.get();
            Boolean hasHead = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == ItemRegistry.infinityLegs.get();
            Boolean hasFeet = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == ItemRegistry.infinityLegs.get();
            if (hasChest && hasFeet && hasHead && hasLeg){
                event.setAmount(0);
                return;
            }
            if (hasChest || hasFeet || hasHead || hasLeg){
                event.setAmount(event.getAmount() * 0.999f); //减伤99.9%
            }
        }

    }
    //无尽胸甲 飞行 护腿 行走速度增加
    @SubscribeEvent
    public static void updatePlayerAbilityStatus(LivingEvent.LivingUpdateEvent event) {
        LivingEntity living = event.getEntityLiving();
        if (living instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) living;
            Boolean hasChest = player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == ItemRegistry.infinityChest.get();
            Boolean hasLegs = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == ItemRegistry.infinityLegs.get();
            //防止其它模组飞行装备无法使用
            String key = player.getGameProfile().getName()+":"+player.world.isRemote;
            //head
            //chest
            if (playersWithChest.contains(key)) {
                if (hasChest) {
                    player.abilities.allowFlying = true;
                }else {
                    if (!player.isCreative()) {
                        player.abilities.allowFlying = false;
                        player.abilities.isFlying = false;
                    }
                    playersWithChest.remove(key);
                }
            }else if (hasChest) {
                playersWithChest.add(key);
            }
            //feet
            //legs
            if (playersWithLegs.contains(key)) {
                if (hasLegs) {
                    player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3f); //行走速度
                } else {
                    player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1f);
                    playersWithLegs.remove(key);
                }
            } else if (hasLegs) {
                playersWithLegs.add(key);
            }
        }
    }

    //无尽鞋子 增加跳跃高度
    @SubscribeEvent
    public static void jumpBoost(LivingEvent.LivingJumpEvent event) {
        LivingEntity living = event.getEntityLiving();
        if (living instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)living;
            String key = player.getGameProfile().getName()+":"+player.world.isRemote;
            if (playersWithFeet.contains(key)) {
                player.setMotion(0, 1.0f, 0);
                return;
            }
        }
    }

    //玩家合成无尽装备时添加附魔
    @SubscribeEvent
    public static void opTool(PlayerEvent.ItemCraftedEvent event){
        ItemStack stack = event.getCrafting();
        if (stack.getItem().equals(ItemRegistry.infinitySword.get())){
            Map<Enchantment, Integer> map = new HashMap<Enchantment, Integer>();
            map.put(Enchantments.LOOTING, 10);
            EnchantmentHelper.setEnchantments( map, stack);
        }
        if (stack.getItem().equals(ItemRegistry.infinityPickaxe.get())){
            Map<Enchantment, Integer> map = new HashMap<Enchantment, Integer>();
            map.put(Enchantments.FORTUNE, 10);
            EnchantmentHelper.setEnchantments( map, stack);
        }
    }
    //不会被烧毁的物品
    @SubscribeEvent
    public static void entityItemUnDeath(ItemEvent event) { //物品实体事件
        ItemEntity entityItem = event.getEntityItem();
        Item item = entityItem.getItem().getItem();
        if(item instanceof InfinityArmor || item instanceof InfinityAxe || item instanceof InfinityBow ||
                item instanceof InfinityHoe || item instanceof InfinityShovel || item instanceof InfinityPickaxe ||
                item instanceof InfinitySword) {
            entityItem.setInvulnerable(true); // 设置物品实体不会死亡
        }
    }

}

