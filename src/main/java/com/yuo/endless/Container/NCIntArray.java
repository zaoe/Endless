package com.yuo.endless.Container;

import net.minecraft.util.IIntArray;

public class NCIntArray implements IIntArray {
    private int timer; //计时器
    @Override
    public int get(int index) {
        switch(index) {
            case 0:
                return timer;
            default:
                return 0;
        }
    }

    @Override
    public void set(int index, int value) {
        switch(index) {
            case 0:
                timer = value;
                break;
        }
    }

    @Override
    public int size() {
        return 1;
    }
}
