package com.leaderli.liutil.collection;

import java.util.Arrays;

public class LiLimitArray<T> {

    public final int size;
    private final Object[] data;

    public LiLimitArray(int size) {
        this.size = size;
        data = new Object[size];
    }


    public void add(T t) {
        if (t == null || contains(t)) {
            return;
        }
        System.arraycopy(data, 0, data, 1, size - 1);
        data[0] = t;
    }

    public boolean remove(T t) {
        if (t == null) {
            return false;
        }
        for (int i = 0; i < size; i++) {

            if (t.equals(data[i])) {
                fastRemove(i);
                return true;
            }
        }
        return false;
    }

    private void fastRemove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(data, index + 1, data, index,
                    numMoved);
        data[size - 1] = null;
    }

    public boolean contains(T t) {
        if (t == null) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (t.equals(data[i])) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return Arrays.toString(data);
    }
}
