package com.geeklife.cs341.hashing;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by andysong on 14-10-21.
 */
public class UNSAFE {

    public static final ByteOrder NATIVE_BYTE_ORDER = ByteOrder.nativeOrder();

    static Unsafe unsafe_ins;

    public static  long BYTE_ARRAY_OFFSET;
    public static  int BYTE_ARRAY_SCALE;
    public static  long INT_ARRAY_OFFSET;
    public static  int INT_ARRAY_SCALE;
    public static  long SHORT_ARRAY_OFFSET;
    public static  int SHORT_ARRAY_SCALE;
    public static  long BUFFER_ADDRESS_OFFSET;
    public static  long BUFFER_ARRAY_OFFSET;
    public static  long BUFFER_ARRAYOFFSET_OFFSET;

    static {
        Field f = null;
        try {
            f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe_ins = (Unsafe)f.get(null);
            BYTE_ARRAY_OFFSET = unsafe_ins.arrayBaseOffset(byte[].class);
            BYTE_ARRAY_SCALE = unsafe_ins.arrayIndexScale(byte[].class);
            INT_ARRAY_OFFSET = unsafe_ins.arrayBaseOffset(int[].class);
            INT_ARRAY_SCALE = unsafe_ins.arrayIndexScale(int[].class);
            SHORT_ARRAY_OFFSET = unsafe_ins.arrayBaseOffset(short[].class);
            SHORT_ARRAY_SCALE = unsafe_ins.arrayIndexScale(short[].class);
            Field addressField = Buffer.class.getDeclaredField("address");
            BUFFER_ADDRESS_OFFSET = unsafe_ins.objectFieldOffset(addressField);
            Field arrayField = ByteBuffer.class.getDeclaredField("hb");
            BUFFER_ARRAY_OFFSET = unsafe_ins.objectFieldOffset(arrayField);
            Field arrayOffsetField = ByteBuffer.class.getDeclaredField("offset");
            BUFFER_ARRAYOFFSET_OFFSET = unsafe_ins.objectFieldOffset(arrayOffsetField);
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
