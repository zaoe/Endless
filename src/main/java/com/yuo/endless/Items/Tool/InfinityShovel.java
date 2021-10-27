package com.yuo.endless.Items.Tool;

import com.yuo.endless.tab.ModGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;

public class InfinityShovel extends ShovelItem {
    public InfinityShovel() {
        super(MyItemTier.INFINITY, 0, -3.0f, new Properties().group(ModGroup.myGroup).isImmuneToFire());
    }
}
