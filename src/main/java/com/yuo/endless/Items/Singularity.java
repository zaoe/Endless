package com.yuo.endless.Items;

import com.yuo.endless.tab.ModGroup;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Singularity extends Item{

    private Block ORE;

    public Singularity(Block ore) {
        super(new Properties().group(ModGroup.myGroup));
        this.ORE = ore;
    }

    public Block getORE() {
        return ORE;
    }

    //获取颜色代码
    public static int getColor(ItemStack stack){
        if (stack.getItem() instanceof Singularity){
            Singularity item = (Singularity) stack.getItem();
            if (item.getORE() == Blocks.NETHERITE_BLOCK){
                return 0x4d494d;
            }
            if (item.getORE().getMaterialColor() != null){
                int colorValue = item.getORE().getMaterialColor().colorValue;
                return colorValue;
            }
        }
        return 0;
    }

}
