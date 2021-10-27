package com.yuo.endless.Recipe;

import com.yuo.endless.Endless;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Optional;

public interface EndlessRecipeType<T extends IRecipe> extends IRecipeType {

    IRecipeType<ExtremeCraftRecipe> EXTREME_CRAFT = register(Endless.MODID + ":extreme_craft");

//    @Override
//    public String toString() {
//        return Endless.MODID + ":extreme_craft";
//    }

    static <T extends IRecipe<?>> IRecipeType<T> register(final String key) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(key), new IRecipeType<T>() {
            public String toString() {
                return key;
            }
        });
    }
}
