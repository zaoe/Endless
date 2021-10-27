package com.yuo.endless.Gui;

import com.yuo.endless.Endless;
import com.yuo.endless.Recipe.EndlessRecipeType;
import com.yuo.endless.Recipe.RecipeTypeRegistry;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypeRegistry {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Endless.MODID);

    public static final RegistryObject<ContainerType<ExtremeCraftContainer>> extremeCraftContainer = CONTAINERS.register("extreme_craft_container", () ->
            IForgeContainerType.create((int windowId, PlayerInventory inv, PacketBuffer data) ->
                    new ExtremeCraftContainer(windowId, inv)));
}
