package com.adenon.sp.kernel.utils.cache;

import java.lang.reflect.Array;
import java.security.InvalidParameterException;
import java.util.concurrent.atomic.AtomicLong;

public class RoundCache<T> {

    private int        CACHE_SIZE;
    private AtomicLong seq = new AtomicLong(-1);
    private T[]        cache;

    /**
     * Instantiates a new RoundCache with specified size.
     * 
     * @param size Size parameter should consist of '1's as binary because it is also being <br>
     *            used as the modulation parameter to array index. (e.g. 3->11, 15->1111)
     * @throws InvalidParameterException
     */
    @SuppressWarnings("unchecked")
    public RoundCache(Class<?> clazz,
                      int size) {
        if (Integer.toBinaryString(size).contains("0")) {
            throw new InvalidParameterException("Invalid size ! Value should consist of '1's as binary ! (e.g. 3->11, 15->1111)");
        }
        this.CACHE_SIZE = size;
        this.cache = (T[]) Array.newInstance(clazz, size + 1);
    }

    public T get(long id) {
        if (id < 0) {
            return null;
        }
        return this.cache[this.getIndex(id)];
    }

    public long put(T obj) {
        int idx = 0;
        int retry = 0;
        long sid = 0;
        while (this.cache[idx = this.getIndex(sid = this.seq.incrementAndGet())] != null) {
            if (++retry > this.CACHE_SIZE) {
                throw new RuntimeException("No space avaliable in cache ! Limit [" + this.CACHE_SIZE + "] Retry count [" + retry + "]");
            }
        }
        this.cache[idx] = obj;
        return sid;
    }

    public T[] getCache() {
        return this.cache;
    }

    public T remove(long id) {
        int index = this.getIndex(id);
        T t = this.cache[index];
        this.cache[index] = null;
        return t;
    }

    private int getIndex(long id) {
        return (int) (id & this.CACHE_SIZE);
    }

    public static void main(String[] args) {
        RoundCache<Integer> roundCache = new RoundCache<Integer>(Integer.class, 3);
        roundCache.put(1);
        roundCache.put(2);
        roundCache.put(3);
        roundCache.put(4);
        roundCache.put(4);
    }
}