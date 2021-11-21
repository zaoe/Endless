package com.yuo.endless;

import com.yuo.endless.Blocks.BlockRegistry;
import com.yuo.endless.Container.ContainerTypeRegistry;
import com.yuo.endless.Entity.EntityRegistry;
import com.yuo.endless.Entity.GapingVooidRender;
import com.yuo.endless.Entity.InfinityArrowRender;
import com.yuo.endless.Entity.InfinityArrowSubRender;
import com.yuo.endless.Gui.ExtremeCraftScreen;
import com.yuo.endless.Gui.NeutronCollectorScreen;
import com.yuo.endless.Gui.NeutroniumCompressorScreen;
import com.yuo.endless.Items.ItemRegistry;
import com.yuo.endless.Items.Singularity;
import com.yuo.endless.Recipe.RecipeSerializerRegistry;
import com.yuo.endless.Sound.ModSounds;
import com.yuo.endless.Tiles.TileTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Supplier;

@Mod("endless")
@Mod.EventBusSubscriber(modid = Endless.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Endless {
	public static final String MODID = "endless";
	public Endless() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::clientSetup);
//        modEventBus.addGenericListener(IRecipeSerializer.class, RecipeTypeRegistry::registerRecipes); //注册合成配方
		//注册物品至mod总线
        ItemRegistry.ITEMS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        EntityRegistry.ENTITY_TYPES.register(modEventBus);
        TileTypeRegistry.TILE_ENTITIES.register(modEventBus);
        ContainerTypeRegistry.CONTAINERS.register(modEventBus);
        RecipeSerializerRegistry.RECIPE_TYPES.register(modEventBus);
        ModSounds.SOUNDS.register(modEventBus);
    }

//    @SubscribeEvent
//    public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> evt) {
//        IForgeRegistry<IRecipeSerializer<?>> r = evt.getRegistry();
//        r.registerAll(RecipeSerializerRegistry.EXTREME_CRAFT.get(), RecipeSerializerRegistry.NEUTRONIUM.get());
//    }

    @SubscribeEvent
    public void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() ->{
            setBowProperty(ItemRegistry.infinityBow.get());
            setInfinityToolProperty(ItemRegistry.infinityPickaxe.get(), "hammer");
            setInfinityToolProperty(ItemRegistry.infinityShovel.get(), "destroyer");
        });
        //绑定Container和ContainerScreen
        event.enqueueWork(() -> {
            ScreenManager.registerFactory(ContainerTypeRegistry.extremeCraftContainer.get(), ExtremeCraftScreen::new);
            ScreenManager.registerFactory(ContainerTypeRegistry.neutronCollectorContainer.get(), NeutronCollectorScreen::new);
            ScreenManager.registerFactory(ContainerTypeRegistry.neutroniumCompressorContainer.get(), NeutroniumCompressorScreen::new);
        });
        registerEntityRender(event.getMinecraftSupplier()); //注册客户端渲染
    }

    //使用动态属性来切换无尽镐，铲形态
    private void setInfinityToolProperty(Item item, String prop) {
        ItemModelsProperties.registerProperty(item, new ResourceLocation(Endless.MODID,
                prop), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null){
                return 0.0f;
            }
            if (itemStack.getTag() != null && itemStack.getTag().getBoolean(prop)){
                    return 1.0f;
            }
            return 0.0f;
        });
    }

    private void registerEntityRender(Supplier<Minecraft> minecraft){
        ItemRenderer renderer = minecraft.get().getItemRenderer();
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.ENDEST_PEARL.get(), manager -> new SpriteRenderer<>(manager, renderer)); //投掷物渲染
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFINITY_ARROW.get(), manager -> new InfinityArrowRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.INFINITY_ARROW_SUB.get(), manager -> new InfinityArrowSubRender(manager));
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.GAPING_VOID.get(), manager -> new GapingVooidRender(manager)); //渲染实体
    }

    //注册物品染色
    @SubscribeEvent
    public static void itemColors(ColorHandlerEvent.Item event) {
        // 第二个参数代表“所有需要使用此 IItemColor 的物品”，是一个 var-arg Item。
        // 有鉴于第一个参数是一个只有一个方法的接口，我们也可以直接在这里使用 lambda 表达式。
        event.getItemColors().register((stack, color) ->{
            return Singularity.getColor(stack, color);
        }, ItemRegistry.singularityIron.get(), ItemRegistry.singularityGold.get(), ItemRegistry.singularityDiamond.get()
                , ItemRegistry.singularityEmerald.get(), ItemRegistry.singularityNetherite.get(), ItemRegistry.singularityCoal.get()
                , ItemRegistry.singularityLapis.get(), ItemRegistry.singularityRedstone.get(), ItemRegistry.singularityQuartz.get());

        // 出于某些原因，你还可以在这里拿到之前的 `BlockColors`。在某些时候这个玩意会很有用。
//        BlockColors blockColorHandler = event.getBlockColors();
    }

    //设置弓物品的动态属性
    private void setBowProperty(Item item){
        ItemModelsProperties.registerProperty(item, new ResourceLocation(Endless.MODID,
                "pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0F;
            }
        });
        ItemModelsProperties.registerProperty(item, new ResourceLocation(Endless.MODID,
                "pulling"), (itemStack, clientWorld, livingEntity) -> {
            return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });
    }
}
