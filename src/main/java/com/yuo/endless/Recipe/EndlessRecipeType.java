package com.yuo.endless.Recipe;

import com.yuo.endless.Endless;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public interface EndlessRecipeType<T extends IRecipe> extends IRecipeType {

    IRecipeType<ExtremeCraftRecipe> EXTREME_CRAFT = register(Endless.MODID + ":extreme_craft");
    IRecipeType<NeutroniumRecipe> NEUTRONIUM = register(Endless.MODID + ":neutronium");

    static <T extends IRecipe<?>> IRecipeType<T> register(final String key) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(key), new IRecipeType<T>() {
            public String toString() {
                return key;
            }
        });
    }
}
