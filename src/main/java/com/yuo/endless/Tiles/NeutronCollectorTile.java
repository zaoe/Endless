package com.yuo.endless.Tiles;

import com.yuo.endless.Container.NCIntArray;
import com.yuo.endless.Container.NeutronCollectorContainer;
import com.yuo.endless.Items.ItemRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;

public class NeutronCollectorTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IInventory {
    private int timer; //计时器
    private NonNullList<ItemStack> output =NonNullList.withSize(1, ItemStack.EMPTY); //输出栏
    private final NCIntArray data = new NCIntArray();

    public NeutronCollectorTile() {
        super(TileTypeRegistry.NEUTRON_COLLECTOR_TILE.get());
    }

    @Override
    public void tick() {
        this.timer++;
        if (world.isRemote || world == null) return;
        if (this.timer >= 1200){
            this.timer = 0;
            ItemStack stack = this.output.get(0);
            //产物为空 设置产物 否则数量加1
            if(stack.isEmpty()) this.output.set(0, new ItemStack(ItemRegistry.neutronPile.get()));
            else this.output.get(0).grow(1);
            markDirty();//标记变化
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.output = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.output);
        this.timer = nbt.getInt("Timer");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("Timer", this.timer);
        ItemStackHelper.saveAllItems(compound, this.output);
        return super.write(compound);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        handleUpdateTag(world.getBlockState(pkt.getPos()), pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT compound = super.getUpdateTag();
        compound.putInt("Timer", this.timer);
        ItemStackHelper.saveAllItems(compound, this.output);
        return compound;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.output = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(tag, this.output);
        this.timer = tag.getInt("Timer");
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("111");
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new NeutronCollectorContainer(p_createMenu_1_, p_createMenu_2_, this, this.data);
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.output.isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.output.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.output, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.output, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {

    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void clear() {
        this.output.clear();
    }
}
