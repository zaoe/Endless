package com.yuo.endless.Items.Tool;

import com.yuo.endless.tab.ModGroup;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ToolType;

import java.util.HashMap;
import java.util.Map;

public class InfinityPickaxe extends PickaxeItem {

    public InfinityPickaxe() {
        super(MyItemTier.INFINITY, 0, -2.8f, new Properties().group(ModGroup.myGroup).isImmuneToFire());
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)){
            Map<Enchantment, Integer> map = new HashMap<Enchantment, Integer>();
            map.put(Enchantments.FORTUNE, 10);
            ItemStack stack = new ItemStack(this);
            EnchantmentHelper.setEnchantments(map, stack);
            items.add(stack);
        }
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        int i = this.getTier().getHarvestLevel();
        if (blockIn.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE) {
            return i >= blockIn.getHarvestLevel();
        }
        if (blockIn.getBlock().equals(Blocks.BEDROCK)) return true;
        Material material = blockIn.getMaterial();
        return material == Material.ROCK || material == Material.IRON || material == Material.ANVIL;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return 99.0f;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }
}
