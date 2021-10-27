package com.yuo.endless.Recipe;

import com.yuo.endless.Endless;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class RecipeTypeRegistry {
    public static final IRecipeSerializer<ExtremeCraftRecipe> EXTREME_CRAFT_RECIPE_TYPE = ExtremeCraftRecipe.SERIALIZER;

    public static final DeferredRegister RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Endless.MODID);

    public static final RegistryObject<IRecipeSerializer<?>> EXTREME_CRAFT = RECIPE_TYPES.register("extreme_craft",
            () -> ExtremeCraftRecipe.SERIALIZER);


//    public static void registerRecipes(Register<IRecipeSerializer<?>> event){
//        registerRecipe(event, EXTREME_CRAFT_RECIPE_TYPE, ExtremeCraftRecipe.SERIALIZER);
//    }
//
//    public static void registerRecipe(Register<IRecipeSerializer<?>> event, IRecipeType<?> type, IRecipeSerializer<?> serializer){
//        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(type.toString()), type);
//        event.getRegistry().register(serializer);
//    }
//
//    public static Map<ResourceLocation, IRecipe<?>> getRecipes(IRecipeType<?> type, RecipeManager manager){
//        Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes = ObfuscationReflectionHelper.getPrivateValue(RecipeManager.class, manager, "field_199522_d");
//        return recipes.get(type);
//    }
}
