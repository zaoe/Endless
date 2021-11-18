package com.yuo.endless.Items;

import com.yuo.endless.tab.ModGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Singularity extends Item{

    private final int colorIndex; //底色
    private final int color; //主色

    public Singularity(int colorIndex, int color) {
        super(new Properties().group(ModGroup.myGroup));
        this.colorIndex = colorIndex;
        this.color = color;
    }

    //获取颜色代码
    public static int getColor(ItemStack stack, int colorIndex){
        int rgb = 0;
        int rgbIndex = 0;
        if (stack.getItem() instanceof Singularity){
            Singularity item = (Singularity) stack.getItem();
            if (item.color != 0) rgb = item.color;
            if (item.colorIndex != 0) rgbIndex = item.colorIndex;
        }
        return colorIndex == 1 ? rgb : rgbIndex;
    }

}
