package com.yuo.endless.Container;

import com.yuo.endless.Recipe.EndlessRecipeType;
import com.yuo.endless.Recipe.ExtremeCraftRecipe;
import com.yuo.endless.Tiles.ExtremeCraftTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.*;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ExtremeCraftContainer extends RecipeBookContainer<CraftingInventory> {

    private ExtremeCraftInventory inputInventory;
    private ExtremeCraftInventoryResult outputInventory;
//    private IInventory extremeCraftInventory = new Inventory(82);
    private final IWorldPosCallable worldPosCallable;
    private final PlayerEntity player;


    public ExtremeCraftContainer(int id, PlayerInventory playerInventory){
        this(id, playerInventory, new ExtremeCraftTile(), IWorldPosCallable.DUMMY);
    }

    public ExtremeCraftContainer(int id, PlayerInventory playerInventory, ExtremeCraftTile tile, IWorldPosCallable worldPosCallableIn) {
        super(ContainerTypeRegistry.extremeCraftContainer.get(), id);
        this.inputInventory = new ExtremeCraftInventory(this, tile);
        this.outputInventory = new ExtremeCraftInventoryResult(tile);
        this.player = playerInventory.player;
        this.worldPosCallable = worldPosCallableIn;
        //添加9*9合成栏
        for (int m = 0; m < 9; m++){
            for (int n = 0; n < 9; n++){
                this.addSlot(new Slot(inputInventory, n + m * 9, 12 + n * 18, 8 + m * 18));
            }
        }
        //添加输出栏
        this.addSlot(new ExtremeCraftReslutSlot(playerInventory.player, inputInventory, outputInventory, 81, 210 , 80));
        //添加玩家物品栏
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 39 + j * 18, 174 + i * 18));
            }
        }
        //添加玩家快捷栏
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 39 + k * 18, 232));
        }

        onCraftMatrixChanged(inputInventory);
    }

    //输入改变时设置输出
    @Override
    public void onCraftMatrixChanged(IInventory matrix) {
        World world = player.world;
        if (world.isRemote) return;
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity)player;
        ItemStack itemstack = ItemStack.EMPTY;
        //获取配方
        Optional<ExtremeCraftRecipe> optional = world.getServer().getRecipeManager().getRecipe(EndlessRecipeType.EXTREME_CRAFT, matrix, world);
        if (optional.isPresent()) {
            ExtremeCraftRecipe recipe = optional.get();
            if (outputInventory.canUseRecipe(world, serverPlayer, recipe)) {
                itemstack = recipe.getCraftingResult(matrix); //获取配方输出
            }
        }
        outputInventory.setInventorySlotContents(81, itemstack);
        serverPlayer.connection.sendPacket(new SSetSlotPacket(windowId, 81, itemstack)); //发包同步数据
//        ItemStack recipeOut = getRecipeOut((ExtremeCraftInventory) matrix);
//        if (recipeOut != null && !recipeOut.isEmpty() && !player.world.isRemote){
//        }
//        super.onCraftMatrixChanged(matrix);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.inputInventory.isUsableByPlayer(playerIn);
    }

    //玩家shift行为
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemstack = itemStack1.copy();
            if (index == 81){
                if (!this.mergeItemStack(itemStack1, 3, 39, true)) return ItemStack.EMPTY;
                slot.onSlotChange(itemStack1, itemstack);
            } else if (index >= 82){
                if (index >= 82 && index < 109) { //从物品栏到快捷栏
                    if (!this.mergeItemStack(itemStack1, 109, 118, false)) return ItemStack.EMPTY;
                } else if (index >= 109 && index < 118 ) {
                    if (!this.mergeItemStack(itemStack1, 82, 109, false)) return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemStack1, 82, 118, false)) return ItemStack.EMPTY; //从合成台取出来

            if (itemStack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();

            if (itemStack1.getCount() == itemstack.getCount()) return ItemStack.EMPTY;
            slot.onTake(playerIn, itemStack1);
        }

        return itemstack;
    }

    @Override
    public void fillStackedContents(RecipeItemHelper itemHelperIn) {
        if (this.inputInventory instanceof IRecipeHelperPopulator) {
            ((IRecipeHelperPopulator)this.inputInventory).fillStackedContents(itemHelperIn);
        }
//        if (this.outputInventory instanceof IRecipeHelperPopulator) {
//            ((IRecipeHelperPopulator)this.outputInventory).fillStackedContents(itemHelperIn);
//        }
    }

    @Override
    public void clear() {
        this.inputInventory.clear();
        this.outputInventory.clear();
    }

    @Override
    public boolean matches(IRecipe<? super CraftingInventory> recipeIn) {
        return recipeIn.matches(this.inputInventory, this.player.world);
    }

    @Override
    public int getOutputSlot() {
        return 81;
    }

    @Override
    public int getWidth() {
        return 9;
    }

    @Override
    public int getHeight() {
        return 9;
    }

    @Override
    public int getSize() {
        return 82;
    }

    @Override
    public RecipeBookCategory func_241850_m() {
        return null;
    }

    //获取配方输出
    @Nullable
    public ItemStack getRecipeOut(ExtremeCraftInventory inventory) {
        Set<IRecipe<?>> recipes = findRecipesByType(EndlessRecipeType.EXTREME_CRAFT, this.player.world);
        for (IRecipe<?> iRecipe : recipes) {
            ExtremeCraftRecipe recipe = (ExtremeCraftRecipe) iRecipe;
            if (recipe.matches(inventory, this.player.world)) {
                return recipe.getRecipeOutput();
            }
        }

        return ItemStack.EMPTY;
    }

    public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> typeIn, World world) {
        return world != null ? world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }

    @SuppressWarnings("resource")
    @OnlyIn(Dist.CLIENT)
    public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> typeIn) {
        ClientWorld world = Minecraft.getInstance().world;
        return world != null ? world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.emptySet();
    }
}
