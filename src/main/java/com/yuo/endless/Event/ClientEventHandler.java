package com.yuo.endless.Event;

import com.yuo.endless.Endless;
import com.yuo.endless.Items.ItemRegistry;
import com.yuo.endless.Items.Singularity;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.Date;

/**
 * Description: 客户端事件
 * Author: cnlimiter
 * Date: 2022/5/21 23:27
 * Version: 1.0
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Endless.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {

    //染色
    @SubscribeEvent
    public static void itemColors(ColorHandlerEvent.Item event) {
        // 第二个参数代表“所有需要使用此 IItemColor 的物品”，是一个 var-arg Item。
        // 有鉴于第一个参数是一个只有一个方法的接口，我们也可以直接在这里使用 lambda 表达式。
        for (RegistryObject<Item> entry : ItemRegistry.ITEMS.getEntries()) {
            Item item = entry.get();
            if (item instanceof Singularity){
                event.getItemColors().register(Singularity::getColor, item);
            }
        }
//        event.getItemColors().register(Singularity::getColor, ItemRegistry.singularityIron.get(), ItemRegistry.singularityGold.get(),
//                ItemRegistry.singularityDiamond.get(), ItemRegistry.singularityEmerald.get(), ItemRegistry.singularityNetherite.get(),
//                ItemRegistry.singularityCoal.get(), ItemRegistry.singularityLapis.get(), ItemRegistry.singularityRedstone.get(),
//                ItemRegistry.singularityQuartz.get(), ItemRegistry.singularityClay.get());

        // 出于某些原因，你还可以在这里拿到之前的 `BlockColors`。在某些时候这个玩意会很有用。
//        BlockColors blockColorHandler = event.getBlockColors();
    }

    public static Date lastplayedlog = null;
    public static Date lastplayedleaf = null;
//    @SubscribeEvent
    public static void sound(PlaySoundEvent event){
        String name = event.getName().trim();
        //在连锁破坏时 以下声音设置最小播放间隔
        if (name.equals("block.grass.break") || name.equals("block.wood.break")) {
            Date now = new Date();
            Date then;

            if (name.equals("block.grass.break")) {
                then = lastplayedleaf;
                lastplayedleaf = now;
            }
            else {
                then = lastplayedlog;
                lastplayedlog = now;
            }

            if (then != null) {
                long ms = (now.getTime()-then.getTime());
                if (ms < 10) {
                    event.setResultSound(null);
                }
            }
        }
    }
}
