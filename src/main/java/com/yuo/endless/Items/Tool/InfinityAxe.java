package com.yuo.endless.Items.Tool;

import com.yuo.endless.tab.ModGroup;
import net.minecraft.item.AxeItem;
import net.minecraft.item.PickaxeItem;

public class InfinityAxe extends AxeItem {
    public InfinityAxe() {
        super(MyItemTier.INFINITY, 0, -3.1f, new Properties().group(ModGroup.myGroup).isImmuneToFire());
    }
}
