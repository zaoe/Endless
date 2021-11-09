package com.yuo.endless.Container;

import net.minecraft.util.IIntArray;

public class NiumCIntArray implements IIntArray {
    private int number; //物品数量
    private int numberTotal; //配方物品总数
    @Override
    public int get(int index) {
        switch(index) {
            case 0:
                return number;
            case 1:
                return numberTotal;
            default:
                return 0;
        }
    }

    @Override
    public void set(int index, int value) {
        switch(index) {
            case 0:
                number = value;
                break;
            case 1:
                numberTotal = value;
                break;
        }
    }

    @Override
    public int size() {
        return 2;
    }
}
