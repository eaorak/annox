package com.adenon.sp.kernel.utils.cache;

import java.util.Enumeration;
import java.util.Hashtable;

public abstract class ObjectPool<T> {

    private long expirationTime;

    private Hashtable<T, Long> locked, unlocked;

    public ObjectPool() {
        this.expirationTime = 30000; // 30 seconds
        this.locked = new Hashtable<T, Long>();
        this.unlocked = new Hashtable<T, Long>();
    }

    protected abstract T create();

    public abstract boolean validate(T o);

    public abstract void expire(T o);

    public synchronized T checkOut() {
        long now = System.currentTimeMillis();
        T t;
        if (this.unlocked.size() > 0) {
            Enumeration<T> e = this.unlocked.keys();
            while (e.hasMoreElements()) {
                t = e.nextElement();
                if ((now - this.unlocked.get(t)) > this.expirationTime) {
                    // object has expired
                    this.unlocked.remove(t);
                    this.expire(t);
                    t = null;
                } else {
                    if (this.validate(t)) {
                        this.unlocked.remove(t);
                        this.locked.put(t, now);
                        return (t);
                    } else {
                        // object failed validation
                        this.unlocked.remove(t);
                        this.expire(t);
                        t = null;
                    }
                }
            }
        }
        // no objects available, create a new one
        t = this.create();
        this.locked.put(t, now);
        return (t);
    }

    public synchronized void checkIn(T t) {
        this.locked.remove(t);
        this.unlocked.put(t, System.currentTimeMillis());
    }
}
