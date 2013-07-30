package com.adenon.sp.kernel.utils.cache;

import java.lang.reflect.Array;
import java.util.Arrays;

public class RoundArray<T extends Number> {

    protected T[] array;
    protected int size;

    @SuppressWarnings("unchecked")
    public RoundArray(Class<T> clazz,
                      int size) {
        this.size = size;
        this.array = (T[]) Array.newInstance(clazz, size);
    }

    public void put(T element) {
        for (int i = this.array.length - 1; i > 0; i--) {
            this.array[i] = this.array[i - 1];
        }
        this.array[0] = element;
    }

    public void reset(T value) {
        Arrays.fill(this.array, value);
    }

    public T[] getAll() {
        return this.getWindow(this.array.length);
    }

    public T[] getWindow(int length) {
        int newLength = length > this.array.length ? this.array.length : length;
        return Arrays.copyOf(this.array, newLength);
    }

    public String visualize(int height) {
        StringBuilder graph = new StringBuilder();
        T[] arr = this.array;
        //
        long max = this.max(arr);
        max = max < 10 ? 10 : max;
        float step = (1.0f * max) / height;
        int space = String.valueOf(max).length();
        //
        for (int i = height; i > -1; i--) {
            String line = String.format("%" + space + "d | ", (int) (i * step));
            for (Number val : arr) {
                int lineVal = Math.round(i * step);
                val = (val == null) ? 0 : val;
                line += val.longValue() >= lineVal ? " # " : "   ";
            }
            graph.append(line + "\n");
        }
        for (int i = 0; i < arr.length; i++) {
            graph.append("---");
        }
        graph.append("- : " + arr.length);
        return graph.toString();
    }

    private long max(Number[] arr) {
        Number max = 0;
        for (Number val : arr) {
            if (val == null) {
                continue;
            }
            max = val.longValue() > max.longValue() ? val : max;
        }
        return max.longValue();
    }

    //
    public static void main(String[] args) {
        RoundArray<Long> array = new RoundArray<Long>(Long.class, 10);
        for (long i = 0; i < 20; i++) {
            array.put(i);
            System.out.println(array.visualize(20));
        }
        for (long i = 20; i > 0; i--) {
            array.put(i);
            System.out.println(array.visualize(20));
        }
        for (long i = 20; i > 0; i--) {
            array.put(0L);
            System.out.println(array.visualize(20));
        }
    }

}
