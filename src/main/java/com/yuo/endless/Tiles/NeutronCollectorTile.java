package com.yuo.endless.Tiles;

import com.yuo.endless.Container.NCIntArray;
import com.yuo.endless.Container.NeutronCollectorContainer;
import com.yuo.endless.Items.ItemRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class NeutronCollectorTile extends LockableTileEntity implements ITickableTileEntity, ISidedInventory {
    private int timer; //计时器
    private final int TIME = 3600; //总时间
    private NonNullList<ItemStack> output = NonNullList.withSize(1, ItemStack.EMPTY); //输出栏
    private final NCIntArray data = new NCIntArray();

    public NeutronCollectorTile() {
        super(TileTypeRegistry.NEUTRON_COLLECTOR_TILE.get());
    }

    @Override
    public void tick() {
        if (!this.output.get(0).isEmpty() && this.output.get(0).getCount() == 64) return; //产物已满，停止计时
        this.timer++;
        this.data.set(0, this.timer);
        if (world == null || world.isRemote) return;
        if (this.timer >= TIME){
            this.timer = 0;
            this.data.set(0, this.timer);
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
       NbtRead(nbt);
    }

    private void NbtRead(CompoundNBT nbt){
        this.output = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.output);
        this.timer = nbt.getInt("Timer");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        NbtWrite(compound);
        return super.write(compound);
    }

    private void NbtWrite(CompoundNBT compound){
        compound.putInt("Timer", this.timer);
        ItemStackHelper.saveAllItems(compound, this.output);
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
        NbtWrite(compound);
        return compound;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        NbtRead(tag);
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("gui.endless.neutron_collector");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new NeutronCollectorContainer(id, player, this, this.data);
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
        return this.output.get(0);
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

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }

    //通过面获取slot
    @Override
    public int[] getSlotsForFace(Direction side) {
        return side == Direction.DOWN ? new int[]{0} : new int[]{1};
    }

    //自动输入
    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return false;
    }

    //自动输出
    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return direction == Direction.DOWN;
    }
}
