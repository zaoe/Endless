package com.yuo.endless.Recipe;

import com.yuo.endless.Endless;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeSerializerRegistry {
    public static final DeferredRegister RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Endless.MODID);

    public static final RegistryObject<IRecipeSerializer<?>> EXTREME_CRAFT = RECIPE_TYPES.register("extreme_craft", () -> new ExtremeCraftRecipe.Serializer());
    public static final RegistryObject<IRecipeSerializer<?>> NEUTRONIUM = RECIPE_TYPES.register("neutronium", () -> new NeutroniumRecipe.Serializer());

}
