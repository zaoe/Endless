package com.yuo.endless.Tiles;

import com.yuo.endless.Recipe.NeutroniumRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.world.World;

import java.util.Set;
import java.util.stream.Collectors;

//机器方块公用方法
public class TileUtils {
    /**
     * 查询当前容器内物品是否有输出，如有返回所需数量，无则返回0
     * @param world 世界
     * @param recipeType 配方类型
     * @param inventory 容器
     * @return 所需输入数量
     */
    public static int getRecipeCount(World world, IRecipeType recipeType, IInventory inventory){
        Set<IRecipe<?>> recipes = world.getRecipeManager().getRecipes().stream().filter(recipe ->
                recipe.getType() == recipeType).collect(Collectors.toSet());
        for (IRecipe recipe : recipes) {
            NeutroniumRecipe neutroniumRecipe = (NeutroniumRecipe) recipe;
            if (neutroniumRecipe.matches(inventory, world)){
                return neutroniumRecipe.getCount();
            }
        }
        return 0;
    }
    //获取配方输出

    /**
     * 根据输入物品获取输出物品
     * @param stack 输入物品
     * @param world
     * @param recipeType 配方类型
     * @return 输出物品
     */
    public static ItemStack getRecipeOut(ItemStack stack, World world, IRecipeType recipeType){
        if (stack.isEmpty()) return ItemStack.EMPTY;
        Inventory inventory = new Inventory(2);
        inventory.setInventorySlotContents(0, stack);
        Set<IRecipe<?>> recipes = world.getRecipeManager().getRecipes().stream().filter(recipe ->
                recipe.getType() == recipeType).collect(Collectors.toSet());
        for (IRecipe recipe : recipes) {
            NeutroniumRecipe neutroniumRecipe = (NeutroniumRecipe) recipe;
            if (neutroniumRecipe.matches(inventory, world)){
                return neutroniumRecipe.getRecipeOutput();
            }
        }
        return ItemStack.EMPTY;
    }
}
