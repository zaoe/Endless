package com.yuo.endless.Container;

import com.yuo.endless.Recipe.EndlessRecipeType;
import com.yuo.endless.Tiles.TileUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NeutroniumCompressorContainer extends Container {

    private final IInventory items;
    private final NiumCIntArray data;
    private final World world;

    public NeutroniumCompressorContainer(int id, PlayerInventory playerInventory){
        this(id,playerInventory , new Inventory(3) , new NiumCIntArray());
    }

    public NeutroniumCompressorContainer(int id, PlayerInventory playerInventory, IInventory inventory, NiumCIntArray intArray) {
        super(ContainerTypeRegistry.neutroniumCompressorContainer.get(), id);
        this.items = inventory;
        this.data = intArray;
        this.world = playerInventory.player.world;
        trackIntArray(data);
        //矿物输入槽
        this.addSlot(new NiumCSlot(items, world, 0, 39,35));
        //奇点槽
        this.addSlot(new NCOutputSlot(items, 1, 116,35));
        //添加玩家物品栏
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        //添加玩家快捷栏
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.items.isUsableByPlayer(playerIn);
    }

    //玩家shift行为
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemstack = itemStack1.copy();
            if (index >= 2){
                if (!TileUtils.getRecipeOut(itemStack1, world, EndlessRecipeType.NEUTRONIUM).isEmpty()){
                    if (!this.mergeItemStack(itemStack1, 0, 1, false)) return ItemStack.EMPTY;
                }
                if (index >= 2 && index < 29) { //从物品栏到快捷栏
                    if (!this.mergeItemStack(itemStack1, 30, 38, false)) return ItemStack.EMPTY;
                } else if (index >= 29 && index < 38 ) {
                    if (!this.mergeItemStack(itemStack1, 2, 29, false)) return ItemStack.EMPTY;
                }
            }else if (!this.mergeItemStack(itemStack1, 2, 38, false)) return ItemStack.EMPTY; //取出来

            if (itemStack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();

            if (itemStack1.getCount() == itemstack.getCount()) return ItemStack.EMPTY;
            slot.onTake(playerIn, itemStack1);
        }

        return itemstack;
    }

    //获取物品数量
    public int getNumber(){
        return this.data.get(0);
    }
    //总数
    public int getCount(){ return this.data.get(1);}
    //获取压缩进度 2种显示
    public int getProgress(){ return (int) Math.ceil(this.data.get(0) / (this.data.get(1) * 1.0) * 22);}
    public int getProgress1(){ return (int) Math.ceil(this.data.get(0) / (this.data.get(1) * 1.0) * 16);}
}
