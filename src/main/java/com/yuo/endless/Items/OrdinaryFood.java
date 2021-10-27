package com.yuo.endless.Items;

import com.yuo.endless.tab.ModGroup;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

//食物
public class OrdinaryFood extends Item {
    public OrdinaryFood(Food food){
        super(new Properties().food(food).group(ModGroup.myGroup));
    }
}
