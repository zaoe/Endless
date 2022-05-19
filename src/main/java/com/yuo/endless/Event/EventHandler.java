package com.yuo.endless.Event;

import com.yuo.endless.Armor.InfinityArmor;
import com.yuo.endless.Endless;
import com.yuo.endless.Items.ItemRegistry;
import com.yuo.endless.Items.Tool.*;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

/**
 * 事件处理类
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Endless.MOD_ID)
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
        LivingEntity living = event.getEntityLiving();
        Boolean hasChest = living.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == ItemRegistry.infinityChest.get();
        Boolean hasLeg = living.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == ItemRegistry.infinityLegs.get();
        Boolean hasHead = living.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == ItemRegistry.infinityHead.get();
        Boolean hasFeet = living.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == ItemRegistry.infinityFeet.get();
        if (living instanceof PlayerEntity){ //怪物无法触发全部伤害减免
            PlayerEntity player = (PlayerEntity) living;
            if (isInfinite(player)){
                if (InfinityDamageSource.isInfinity(event.getSource())){ //是无尽伤害 减免至10点
                    ItemStack stack = player.getActiveItemStack();
                    if (!stack.isEmpty() && isInfinityItem(stack)) { //玩家在使用无尽剑或弓时，不会受伤
                        event.setAmount(5);
                    } else event.setAmount(10);
                } else {
                    event.setAmount(0);
                    event.setCanceled(true);
                }
            }else if (hasChest || hasFeet || hasHead || hasLeg){
                event.setAmount(event.getAmount() * 0.001f);
            }
        }else if (hasChest || hasFeet || hasHead || hasLeg){ //怪物也可触发减伤
            event.setAmount(event.getAmount() * 0.001f); //减伤99.9%
        }
    }
    //无尽胸甲 飞行 护腿 行走速度增加
    @SubscribeEvent
    public static void updatePlayerAbilityStatus(LivingEvent.LivingUpdateEvent event) {
        LivingEntity living = event.getEntityLiving();
        if (living instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) living;
            ItemStack chest = player.getItemStackFromSlot(EquipmentSlotType.CHEST);
            ItemStack legs = player.getItemStackFromSlot(EquipmentSlotType.LEGS);
            boolean hasHead = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == ItemRegistry.infinityHead.get();
            boolean hasChest = chest.getItem() == ItemRegistry.infinityChest.get();
            boolean hasLegs = legs.getItem() == ItemRegistry.infinityLegs.get();
            boolean hasFeet = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == ItemRegistry.infinityFeet.get();
            //防止其它模组飞行装备无法使用
            String key = player.getGameProfile().getName()+":"+player.world.isRemote;
            //head
            if (playersWithHead.contains(key)){
                if (hasHead){

                }else {
                    playersWithHead.remove(key);
                }
            }else if (hasHead){
                playersWithHead.add(key);
            }
            //chest
            if (playersWithChest.contains(key)) {
                if (hasChest) {
                    player.abilities.allowFlying = true;
                    if (chest.hasTag() && chest.getOrCreateTag().getBoolean("flag"))
                        player.abilities.setFlySpeed(0.1f); //飞行速度2倍
                }else {
                    if (!player.isCreative()) {
                        player.abilities.allowFlying = false;
                        player.abilities.isFlying = false;
                        player.abilities.setFlySpeed(0.05f);
                    }
                    playersWithChest.remove(key);
                }
            }else if (hasChest) {
                playersWithChest.add(key);
            }
            //legs
            if (playersWithLegs.contains(key)) {
                ModifiableAttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
                if (hasLegs) {
                    if (legs.getOrCreateTag().getBoolean("flag") && attribute != null && !attribute.hasModifier(InfinityArmor.modifier))
                        attribute.applyPersistentModifier(InfinityArmor.modifier); //行走速度
                }else {
                    if (attribute != null && attribute.hasModifier(InfinityArmor.modifier))
                        attribute.removeModifier(InfinityArmor.modifier);
                    playersWithLegs.remove(key);
                }
            } else if (hasLegs) {
                playersWithLegs.add(key);
            }
            //feet
            if (playersWithFeet.contains(key)){
                if (hasFeet){

                }else {
                    playersWithFeet.remove(key);
                }
            }else if (hasFeet){
                playersWithFeet.add(key);
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
            ItemStack feet = player.getItemStackFromSlot(EquipmentSlotType.FEET);
            if (playersWithFeet.contains(key) && feet.hasTag() && feet.getOrCreateTag().getBoolean("flag")) {
                player.setMotion(0, 1.0f, 0);
            }
        }
    }

    //玩家合成无尽装备时添加附魔
    @SubscribeEvent
    public static void opTool(PlayerEvent.ItemCraftedEvent event){
        ItemStack stack = event.getCrafting();
        if (stack.getItem().equals(ItemRegistry.infinitySword.get())){
            Map<Enchantment, Integer> map = new HashMap<>();
            map.put(Enchantments.LOOTING, 10);
            EnchantmentHelper.setEnchantments( map, stack);
        }
        if (stack.getItem().equals(ItemRegistry.infinityPickaxe.get())){
            Map<Enchantment, Integer> map = new HashMap<>();
            map.put(Enchantments.FORTUNE, 10);
            EnchantmentHelper.setEnchantments( map, stack);
        }
    }
//    //不会被烧毁的物品
//    @SubscribeEvent
//    public static void entityItemUnDeath(ItemEvent event) { //物品实体事件
//        ItemEntity entityItem = event.getEntityItem();
//        Item item = entityItem.getItem().getItem();
//        if(item instanceof InfinityArmor || item instanceof InfinityAxe || item instanceof InfinityBow ||
//                item instanceof InfinityHoe || item instanceof InfinityShovel || item instanceof InfinityPickaxe ||
//                item instanceof InfinitySword) {
//            entityItem.setInvulnerable(true); // 设置物品实体不会死亡
//        }
//    }

    //原版掉落修改
    @SubscribeEvent
    public static void dropsItem(LivingDropsEvent event){
        LivingEntity entityLiving = event.getEntityLiving();
        Entity source = event.getSource().getTrueSource();
        if (!(source instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) source;

        if (entityLiving instanceof SkeletonEntity){ //炽焰之啄颅剑击杀小白掉落调零骷髅头
            ItemStack heldItem = player.getHeldItem(Hand.MAIN_HAND);
            if (heldItem.getItem() instanceof SkullfireSword){
                spawnDrops(Items.WITHER_SKELETON_SKULL, 1, entityLiving.world, entityLiving.getPosition(), event);
            }
        }
    }

    //无尽镐 锤形态右键获取基岩
    @SubscribeEvent
    public static void breakBedrock(PlayerInteractEvent.RightClickBlock event){
        ItemStack stack = event.getItemStack();
        BlockPos pos = event.getPos();
        World world = event.getWorld();
        PlayerEntity player = event.getPlayer();
        if (stack.getItem() instanceof InfinityPickaxe && world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK)){
            if (stack.hasTag() && stack.getOrCreateTag().getBoolean("hammer")){
                world.addEntity(new ItemEntity(world, player.getPosX(), player.getPosY(), player.getPosZ(), new ItemStack(Blocks.BEDROCK)));
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) { //不能被无尽伤害外的攻击杀死
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (isInfinite(player) && !InfinityDamageSource.isInfinity(event.getSource())) {
                event.setCanceled(true);
                player.setHealth(player.getMaxHealth());
            }
        }
    }

    @SubscribeEvent
    public static void diggity(PlayerEvent.BreakSpeed event) {
        if (!event.getEntityLiving().getHeldItem(Hand.MAIN_HAND).isEmpty()) {
            ItemStack held = event.getEntityLiving().getHeldItem(Hand.MAIN_HAND);
            if (held.getItem() == ItemRegistry.infinityPickaxe.get() || held.getItem() == ItemRegistry.infinityShovel.get() ||
            held.getItem() == ItemRegistry.infinityAxe.get()) {
                if (!event.getEntityLiving().isOnGround() || event.getEntityLiving().isInWater()) { //未站立状态,在水中 破坏速度加倍
                    event.setNewSpeed(event.getOriginalSpeed() * 2);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onGetHurt(LivingHurtEvent event) { //不能伤害无尽套玩家
        if (!(event.getEntityLiving() instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        ItemStack stack = player.getActiveItemStack();
        if (!stack.isEmpty() && isInfinityItem(stack)) { //玩家在使用无尽剑或弓时，不会受伤
            event.setAmount(10);
            event.setCanceled(true);
        }
        if (isInfinite(player) && !InfinityDamageSource.isInfinity(event.getSource())) {
            event.setCanceled(true);
        }
    }

    static boolean isInfinityItem(ItemStack stack){
        return stack.getItem() == ItemRegistry.infinitySword.get() || stack.getItem() == ItemRegistry.infinityBow.get();
    }

    //玩家不会被无尽伤害外攻击
    @SubscribeEvent
    public static void onAttacked(LivingAttackEvent event) {
        if (!(event.getEntityLiving() instanceof PlayerEntity)) {
            return;
        }
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof PlayerEntity) {
            return;
        }
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        if (isInfinite(player) && !InfinityDamageSource.isInfinity(event.getSource())) {
            event.setCanceled(true);
        }
        String key = player.getGameProfile().getName()+":"+player.world.isRemote;
        if (event.getSource().isFireDamage() && playersWithLegs.contains(key)){
            event.setCanceled(true);
        }
    }

    /**
     * 判断玩家是否穿戴全套无尽装备
     * @param player 玩家
     * @return 结果
     */
    public static boolean isInfinite(PlayerEntity player) {
        for (EquipmentSlotType slot : EquipmentSlotType.values()) {
            if (slot.getSlotType() != EquipmentSlotType.Group.ARMOR) {
                continue;
            }
            ItemStack stack = player.getItemStackFromSlot(slot);
            if (stack.isEmpty() || !(stack.getItem() instanceof InfinityArmor)) {
                return false;
            }
        }
        return true;
    }
    //玩家登入
    @SubscribeEvent
    public static void playerLogin(PlayerEvent.PlayerLoggedInEvent event){
        //重置双爆属性
        PlayerEntity player = event.getPlayer();
        //发送消息
        player.sendMessage(new TranslationTextComponent("endless.message.login")
                .setStyle(Style.EMPTY.setHoverEvent(HoverEvent.Action.SHOW_TEXT.deserialize(new TranslationTextComponent("endless.message.login0")))
                        .setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://space.bilibili.com/21854371"))), UUID.randomUUID());
    }

    //恒星燃料
    @SubscribeEvent
    public static void starFuel(FurnaceFuelBurnTimeEvent event){
        Item item = event.getItemStack().getItem();
        if (item == ItemRegistry.starFuel.get()){
            event.setBurnTime(Integer.MAX_VALUE);
        }
    }

//    @SubscribeEvent
//    public static void recipeChange(DifficultyChangeEvent event){
//        Difficulty difficulty = event.getDifficulty();
//        CompressorManager.changeAllCount(difficulty);
//    }
    /**
     * 添加额外掉落
     * @param item 需要掉落的物品
     * @param count 数量
     * @param world 世界
     * @param pos 坐标
     * @param event 事件
     */
    private static void spawnDrops(Item item, int count, World world, BlockPos pos, LivingDropsEvent event){
        ItemStack stack1 = new ItemStack(item, count);
        ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack1);
        event.getDrops().add(itemEntity);
    }
}

