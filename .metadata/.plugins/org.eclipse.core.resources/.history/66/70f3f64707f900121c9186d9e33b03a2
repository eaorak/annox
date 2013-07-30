package com.adenon.sp.kernel.utils.cache;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ExpandArray<T> {

    private static int EXPAND_SIZE = 10;
    private T[]        arr;

    @SuppressWarnings("unchecked")
    public ExpandArray(Class<?> type,
                       int size) {
        this.arr = (T[]) Array.newInstance(type, size);
    }

    public void put(int index,
                    T resource) {
        this.expand(index);
        this.arr[index] = resource;
    }

    public T get(int index) {
        return this.exceeded(index) ? null : this.arr[index];
    }

    public T remove(int index) {
        if (this.exceeded(index)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        T resource = this.arr[index];
        this.arr[index] = null;
        return resource;
    }

    private void expand(int index) {
        if (index >= this.arr.length) {
            int newSize = ((index / EXPAND_SIZE) + 1) * EXPAND_SIZE;
            this.arr = Arrays.copyOf(this.arr, newSize);
        }
    }

    private boolean exceeded(int index) {
        if ((index < 0) || (index > this.arr.length)) {
            return true;
        }
        return false;
    }

}