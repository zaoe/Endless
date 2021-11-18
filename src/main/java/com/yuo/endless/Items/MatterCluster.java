package com.yuo.endless.Items;

import com.yuo.endless.tab.ModGroup;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//物质团
public class MatterCluster extends Item  {

    public MatterCluster() {
        super(new Properties().group(ModGroup.myGroup));
    }

    //设置物品map
    public static ItemStack setMap(Map<ItemStack, Integer> map){
        ItemStack stack = new ItemStack(ItemRegistry.matterCluster.get());
        setItemTag(stack, map);
        return  stack;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!stack.hasTag() || !stack.getTag().keySet().contains("matterCluster")){
            return ;
        }
        ListNBT matterCluster = (ListNBT) stack.getTag().get("matterCluster");
        //物品种类数量信息
        tooltip.add(new StringTextComponent(matterCluster.size() + "/64" + new TranslationTextComponent("endless.text.itemInfo.matter_cluster2").getString()));
        tooltip.add(new StringTextComponent(""));

        if (Screen.hasShiftDown()) { //在物品上按下shift键
            for (int i = 0; i < matterCluster.size(); i++) {
                CompoundNBT nbt = (CompoundNBT) matterCluster.get(i);
                ItemStack read = ItemStack.read(nbt);
                int count = nbt.getInt("count");

                tooltip.add(new StringTextComponent(read.getItem().getRarity(read).color + read.getDisplayName().getString() + TextFormatting.GRAY + " x " + count));
            }
        } else {
            tooltip.add(new TranslationTextComponent("endless.text.itemInfo.matter_cluster"));
            tooltip.add(new TranslationTextComponent("endless.text.itemInfo.matter_cluster1"));
        }
    }

    //设置物品tag
    public static void setItemTag(ItemStack stack, Map<ItemStack, Integer> map){
        if (!stack.hasTag()){
            stack.setTag(new CompoundNBT());
        }
        CompoundNBT nbt = new CompoundNBT();
        ListNBT listNBT = new ListNBT();

        for (ItemStack key : map.keySet()) { //遍历所有键
            CompoundNBT nbt1 = new CompoundNBT();
            key.write(nbt1);
            nbt1.putInt("count", map.get(key));
            listNBT.add(nbt1);
        }
        nbt.put("matterCluster", listNBT);
        stack.setTag(nbt);
    }

    //获取物品tag
    public static Map<ItemStack, Integer> getItemTag(ItemStack stack){
        if (!stack.hasTag() || !stack.getTag().keySet().contains("matterCluster")){
            return null;
        }
        ListNBT matterCluster = (ListNBT) stack.getTag().get("matterCluster");
        Map<ItemStack, Integer> data = new HashMap<>();
        for (int i = 0; i < matterCluster.size(); i++){
            CompoundNBT nbt = (CompoundNBT) matterCluster.get(i);
            data.put(ItemStack.read(nbt), nbt.getInt("count"));
        }
        return data;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote){
            //生成物质团类所有物品
            Map<ItemStack, Integer> map = getItemTag(stack);
            if (map != null){
                for (ItemStack key : map.keySet()) {
                    Integer integer = map.get(key);
                    int size = key.getMaxStackSize();
                    int i = integer % size; //不足一组部分
                    int j = integer / size; //几组
                    addEntityItem(worldIn, new ItemStack(key.getItem(), i), playerIn);
                    for (int m = 0; m < j; m++){
                        addEntityItem(worldIn, new ItemStack(key.getItem(), size), playerIn);
                    }
                }
            }

        }
        stack.shrink(1);
        return ActionResult.resultSuccess(stack);
    }

    private static void addEntityItem(World world, ItemStack stack, PlayerEntity player){
        world.addEntity(new ItemEntity(world, player.getPosX(), player.getPosY(), player.getPosZ(), stack));
    }
}
