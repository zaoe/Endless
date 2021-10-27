package com.yuo.endless.Items.Tool;

import com.yuo.endless.tab.ModGroup;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BowItem;

public class InfinityBow extends BowItem {
    public InfinityBow() {
        super(new Properties().group(ModGroup.myGroup).isImmuneToFire());
    }
}
