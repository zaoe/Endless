package com.yuo.endless.integration.Jei;

import com.yuo.endless.Container.ExtremeCraftContainer;
import com.yuo.endless.Endless;
import com.yuo.endless.Items.ItemRegistry;
import com.yuo.endless.Recipe.CompressorManager;
import com.yuo.endless.Recipe.ExtremeCraftingManager;
import com.yuo.endless.Recipe.RecipeTypeRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;
import java.util.stream.Collectors;

@JeiPlugin
public class EndlessJei implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Endless.MOD_ID, "jei_plugin");
    }

    //插件告诉JEI定制菜谱类别
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new ExtremeCraftRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new NeutroniumCRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    //注册配方类别
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().world).getRecipeManager();
        registration.addRecipes(ExtremeCraftingManager.getInstance().getRecipeList(), ExtremeCraftRecipeCategory.UID);
        registration.addRecipes(recipeManager.getRecipesForType(RecipeTypeRegistry.EXTREME_CRAFT_RECIPE).stream().
                filter(Objects::nonNull).collect(Collectors.toList()), ExtremeCraftRecipeCategory.UID);
        registration.addRecipes(CompressorManager.getRecipes(), NeutroniumCRecipeCategory.UID);
        registration.addRecipes(recipeManager.getRecipesForType(RecipeTypeRegistry.NEUTRONIUM_RECIPE).stream().
                filter(Objects::nonNull).collect(Collectors.toList()), NeutroniumCRecipeCategory.UID);
    }

    //注册+号添加
    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(ExtremeCraftContainer.class, ExtremeCraftRecipeCategory.UID, 0, 81, 82, 36);
    }

    //注册机器合成
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ItemRegistry.extremeCraftingTable.get()), ExtremeCraftRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(ItemRegistry.neutroniumCompressor.get()), NeutroniumCRecipeCategory.UID);
    }
}
