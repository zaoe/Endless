package com.yuo.endless.Items;

import com.yuo.endless.tab.ModGroup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 通用普通物品
 */
public class OrdinaryItem extends Item{

	public OrdinaryItem() {
		super(new Properties().group(ModGroup.myGroup)); //设置物品所在 创造模式物品栏
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		return ActionResult.resultPass(playerIn.getHeldItem(handIn));
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		Item item = stack.getItem();
		if (item == ItemRegistry.crystalMatrixIngot.get()){
			tooltip.add(new TranslationTextComponent("endless.text.itemInfo.crystal_matrix_ingot"));
		}
		if (item == ItemRegistry.neutronPile.get()){
			tooltip.add(new TranslationTextComponent("endless.text.itemInfo.neutron_pile"));
		}
		if (item == ItemRegistry.neutronNugget.get()){
			tooltip.add(new TranslationTextComponent("endless.text.itemInfo.neutron_nugget"));
		}
		if (item == ItemRegistry.neutroniumIngot.get()){
			tooltip.add(new TranslationTextComponent("endless.text.itemInfo.neutronium_ingot"));
		}
		if (item == ItemRegistry.infinityCatalyst.get()){
			tooltip.add(new TranslationTextComponent("endless.text.itemInfo.infinity_catalyst"));
		}
		if (item == ItemRegistry.infinityIngot.get()){
			tooltip.add(new TranslationTextComponent("endless.text.itemInfo.infinity_ingot"));
		}
		if (item == ItemRegistry.recordFragment.get()){
			tooltip.add(new TranslationTextComponent("endless.text.itemInfo.record_fragment"));
		}
	}
}
