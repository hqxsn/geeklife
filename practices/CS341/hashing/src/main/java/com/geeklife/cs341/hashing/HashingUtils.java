package com.geeklife.cs341.hashing;

import java.math.BigInteger;
import java.nio.ByteOrder;

/**
 * Created by andysong on 14-10-22.
 */
public class HashingUtils {

    public static final long LONG_LO_MASK = 0x00000000FFFFFFFFL;

    /** rotate a long by the specified number of bits */
    public static final long rotateLong(long val, int bits) {
        return (val >> bits) | (val << (64 - bits));
    }

    /** rotate a long by the specified number of bits */
    public static final int rotateInt(int val, int bits) {
        return (val >> bits) | (val << (32 - bits));
    }

    /** take a bunch of random bytes and turn them into a single long */
    public static final long condenseBytesIntoLong(byte[] representation) {
        long seed = 0L;
        int pos = 0;

        for (byte b : representation) {
            long bLong = ((long) b) << (pos * 8);
            seed ^= bLong;
            pos = (pos + 1) % 8;
        }

        return seed;
    }

    /** take a bunch of random bytes and turn them into a single int */
    public static final int condenseBytesIntoInt(byte[] representation) {
        int seed = 0;
        int pos = 0;

        for (byte b : representation) {
            long bLong = ((long) b) << (pos * 8);
            seed ^= bLong;
            pos = (pos + 1) % 4;
        }

        return seed;
    }

    /** gather a long from the specified index into the byte array */
    public static final long gatherLongLE(byte[] data, int index) {
        int i1 = gatherIntLE(data, index);
        long l2 = gatherIntLE(data, index + 4);

        return uintToLong(i1) | (l2 << 32);
    }

    /**
     * gather a partial long from the specified index using the specified number
     * of bytes into the byte array
     */
    public static final long gatherPartialLongLE(byte[] data, int index,
                                                 int available) {
        if (available >= 4) {
            int i = gatherIntLE(data, index);
            long l = uintToLong(i);

            available -= 4;

            if (available == 0) {
                return l;
            }

            int i2 = gatherPartialIntLE(data, index + 4, available);

            l <<= (available << 3);
            l |= (long) i2;

            return l;
        }

        return (long) gatherPartialIntLE(data, index, available);
    }

    /** perform unsigned extension of int to long */
    public static final long uintToLong(int i) {
        long l = (long) i;

        return (l << 32) >>> 32;
    }

    /** gather an int from the specified index into the byte array */
    public static final int gatherIntLE(byte[] data, int index) {
        int i = data[index] & 0xFF;

        i |= (data[++index] & 0xFF) << 8;
        i |= (data[++index] & 0xFF) << 16;
        i |= (data[++index] << 24);

        return i;
    }

    /**
     * gather a partial int from the specified index using the specified number
     * of bytes into the byte array
     */
    public static final int gatherPartialIntLE(byte[] data, int index,
                                               int available) {
        int i = data[index] & 0xFF;

        if (available > 1) {
            i |= (data[++index] & 0xFF) << 8;
            if (available > 2) {
                i |= (data[++index] & 0xFF) << 16;
            }
        }

        return i;
    }

    /**
     * Multiply a 128-bit value by a long. FIXME: need to verify!
     */
    public static final void multiply128_optimized(long a, long b, long[] dest) {
        long aH = a >> 32;
        long aL = a & LONG_LO_MASK;
        long bH = b >> 32;
        long bL = b & LONG_LO_MASK;
        long r1, r2, r3, rML;

        r1 = aL * bL; r2 = aH * bL; r3 = aL * bH;
        rML = (r1 >>> 32) + (r2 & LONG_LO_MASK) + (r3 & LONG_LO_MASK);
        dest[0] = (r1 & LONG_LO_MASK) + ((rML & LONG_LO_MASK) << 32);
        dest[1] = (aH * bH) + (rML >>> 32);
    }

    /**
     * Multiply a 128-bit value by a long.
     */
    public static final void multiply128(long a, long b, long[] dest) {
        BigInteger a1 = BigInteger.valueOf(a);
        BigInteger b1 = BigInteger.valueOf(b);
        BigInteger product = a1.multiply(b1);

        dest[0] = product.longValue();
        dest[1] = product.shiftRight(64).longValue();
    }

    public static void checkRange(byte[] buf, int off) {
        if (off < 0 || off >= buf.length) {
            throw new ArrayIndexOutOfBoundsException(off);
        }
    }

    public static void checkRange(byte[] buf, int off, int len) {
        checkLength(len);
        if (len > 0) {
            checkRange(buf, off);
            checkRange(buf, off + len - 1);
        }
    }

    public static void checkLength(int len) {
        if (len < 0) {
            throw new IllegalArgumentException("lengths must be >= 0");
        }
    }

    public static long readLongLE64(byte[] buf, int i) {
        return (buf[i] & 0xFFL) | ((buf[i+1] & 0xFFL) << 8) | ((buf[i+2] & 0xFFL) << 16) | ((buf[i+3] & 0xFFL) << 24)
                | ((buf[i+4] & 0xFFL) << 32) | ((buf[i+5] & 0xFFL) << 40) | ((buf[i+6] & 0xFFL) << 48) | ((buf[i+7] & 0xFFL) << 56);
    }

    public static long rotateLeft64(long val, int x) {
        return (val << x) | (val >>> (64-x));
    }

    public static int rotateLeft(int val, int x) {
        return Integer.rotateLeft(val, x);
    }

    public static byte readByte(byte[] buf, int i) {
        return buf[i];
    }

    public static int readIntLE64(byte[] buf, int i) {
        return (buf[i] & 0xFF) | ((buf[i+1] & 0xFF) << 8) | ((buf[i+2] & 0xFF) << 16) | ((buf[i+3] & 0xFF) << 24);
    }

    public static int readInt(byte[] src, int srcOff) {
        return UNSAFE.getUnsafe().getInt(src, UNSAFE.BYTE_ARRAY_OFFSET + srcOff);
    }

    public static int readIntLE(byte[] src, int srcOff) {
        int i = readInt(src, srcOff);
        if (UNSAFE.NATIVE_BYTE_ORDER == ByteOrder.BIG_ENDIAN) {
            i = Integer.reverseBytes(i);
        }
        return i;
    }





}
