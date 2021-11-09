package com.yuo.endless.Container;

import com.yuo.endless.Recipe.EndlessRecipeType;
import com.yuo.endless.Tiles.TileUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NiumCSlot extends Slot {
    private World world;
    public NiumCSlot(IInventory inventoryIn, World worldIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
        this.world = worldIn;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return !TileUtils.getRecipeOut(stack, world, EndlessRecipeType.NEUTRONIUM).isEmpty();
    }
}
