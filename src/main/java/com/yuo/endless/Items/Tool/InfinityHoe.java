package com.yuo.endless.Items.Tool;

import com.yuo.endless.tab.ModGroup;
import net.minecraft.item.HoeItem;

public class InfinityHoe extends HoeItem {
    public InfinityHoe() {
        super(MyItemTier.INFINITY, 0, -1.0f, new Properties().group(ModGroup.myGroup).isImmuneToFire());
    }
}
