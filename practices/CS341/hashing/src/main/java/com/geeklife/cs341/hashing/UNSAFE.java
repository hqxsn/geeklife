package com.geeklife.cs341.hashing;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by andysong on 14-10-21.
 */
public class UNSAFE {

    static Unsafe unsafe_ins;

    static {
        Field f = null;
        try {
            f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe_ins = (Unsafe)f.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    public static Unsafe getUnsafe() {
        return unsafe_ins;
    }

}
