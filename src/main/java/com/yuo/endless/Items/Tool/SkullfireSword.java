package com.yuo.endless.Items.Tool;

import com.yuo.endless.tab.ModGroup;
import net.minecraft.item.SwordItem;

public class SkullfireSword extends SwordItem {
    public SkullfireSword() {
        super(MyItemTier.SKULLFIRE, 0, -2.4f, new Properties().group(ModGroup.myGroup).isImmuneToFire());
    }
}
