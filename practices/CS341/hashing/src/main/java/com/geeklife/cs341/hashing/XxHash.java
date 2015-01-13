package com.geeklife.cs341.hashing;

import static com.geeklife.cs341.hashing.HashingUtils.*;

/**
 * Created by andysong on 14-10-24.
 */
public class XxHash implements Hash64, Hash32 {

    static final int PRIME1 = -1640531535;
    static final int PRIME2 = -2048144777;
    static final int PRIME3 = -1028477379;
    static final int PRIME4 = 668265263;
    static final int PRIME5 = 374761393;

    static final long PRIME64_1 = -7046029288634856825L; //11400714785074694791
    static final long PRIME64_2 = -4417276706812531889L; //14029467366897019727
    static final long PRIME64_3 = 1609587929392839161L;
    static final long PRIME64_4 = -8796714831421723037L; //9650029242287828579
    static final long PRIME64_5 = 2870177450012600261L;

    public long xxhash64(byte[] buf, int off, int len, long seed) {

        checkRange(buf, off, len);

        final int end = off + len;
        long h64;

        if (len >= 32) {
            final int limit = end - 32;
            long v1 = seed + PRIME64_1 + PRIME64_2;
            long v2 = seed + PRIME64_2;
            long v3 = seed + 0;
            long v4 = seed - PRIME64_1;
            do {
                v1 += readLongLE64(buf, off) * PRIME64_2;
                v1 = rotateLeft64(v1, 31);
                v1 *= PRIME64_1;
                off += 8;

                v2 += readLongLE64(buf, off) * PRIME64_2;
                v2 = rotateLeft64(v2, 31);
                v2 *= PRIME64_1;
                off += 8;

                v3 += readLongLE64(buf, off) * PRIME64_2;
                v3 = rotateLeft64(v3, 31);
                v3 *= PRIME64_1;
                off += 8;

                v4 += readLongLE64(buf, off) * PRIME64_2;
                v4 = rotateLeft64(v4, 31);
                v4 *= PRIME64_1;
                off += 8;
            } while (off <= limit);

            h64 = rotateLeft64(v1, 1) + rotateLeft64(v2, 7) + rotateLeft64(v3, 12) + rotateLeft64(v4, 18);

            v1 *= PRIME64_2; v1 = rotateLeft64(v1, 31); v1 *= PRIME64_1; h64 ^= v1;
            h64 = h64 * PRIME64_1 + PRIME64_4;

            v2 *= PRIME64_2; v2 = rotateLeft64(v2, 31); v2 *= PRIME64_1; h64 ^= v2;
            h64 = h64 * PRIME64_1 + PRIME64_4;

            v3 *= PRIME64_2; v3 = rotateLeft64(v3, 31); v3 *= PRIME64_1; h64 ^= v3;
            h64 = h64 * PRIME64_1 + PRIME64_4;

            v4 *= PRIME64_2; v4 = rotateLeft64(v4, 31); v4 *= PRIME64_1; h64 ^= v4;
            h64 = h64 * PRIME64_1 + PRIME64_4;
        } else {
            h64 = seed + PRIME64_5;
        }

        h64 += len;

        while (off <= end - 8) {
            long k1 = readLongLE64(buf, off);
            k1 *= PRIME64_2; k1 = rotateLeft64(k1, 31); k1 *= PRIME64_1; h64 ^= k1;
            h64 = rotateLeft64(h64, 27) * PRIME64_1 + PRIME64_4;
            off += 8;
        }

        if (off <= end - 4) {
            h64 ^= (readIntLE64(buf, off) & 0xFFFFFFFFL) * PRIME64_1;
            h64 = rotateLeft64(h64, 23) * PRIME64_2 + PRIME64_3;
            off += 4;
        }

        while (off < end) {
            h64 ^= (readByte(buf, off) & 0xFF) * PRIME64_5;
            h64 = rotateLeft64(h64, 11) * PRIME64_1;
            ++off;
        }

        h64 ^= h64 >>> 33;
        h64 *= PRIME64_2;
        h64 ^= h64 >>> 29;
        h64 *= PRIME64_3;
        h64 ^= h64 >>> 32;

        return h64;
    }

    public int xxhash(byte[] buf, int off, int len, int seed) {

        checkRange(buf, off, len);

        final int end = off + len;
        int h32;

        if (len >= 16) {
            final int limit = end - 16;
            int v1 = seed + PRIME1 + PRIME2;
            int v2 = seed + PRIME2;
            int v3 = seed + 0;
            int v4 = seed - PRIME1;
            do {
                v1 += readIntLE(buf, off) * PRIME2;
                v1 = rotateLeft(v1, 13);
                v1 *= PRIME1;
                off += 4;

                v2 += readIntLE(buf, off) * PRIME2;
                v2 = rotateLeft(v2, 13);
                v2 *= PRIME1;
                off += 4;

                v3 += readIntLE(buf, off) * PRIME2;
                v3 = rotateLeft(v3, 13);
                v3 *= PRIME1;
                off += 4;

                v4 += readIntLE(buf, off) * PRIME2;
                v4 = rotateLeft(v4, 13);
                v4 *= PRIME1;
                off += 4;
            } while (off <= limit);

            h32 = rotateLeft(v1, 1) + rotateLeft(v2, 7) + rotateLeft(v3, 12) + rotateLeft(v4, 18);
        } else {
            h32 = seed + PRIME5;
        }

        h32 += len;

        while (off <= end - 4) {
            h32 += readIntLE(buf, off) * PRIME3;
            h32 = rotateLeft(h32, 17) * PRIME4;
            off += 4;
        }

        while (off < end) {
            h32 += (readByte(buf, off) & 0xFF) * PRIME5;
            h32 = rotateLeft(h32, 11) * PRIME1;
            ++off;
        }

        h32 ^= h32 >>> 15;
        h32 *= PRIME2;
        h32 ^= h32 >>> 13;
        h32 *= PRIME3;
        h32 ^= h32 >>> 16;

        return h32;
    }

    @Override
    public int hash(byte[] data, long seed) {
        return xxhash(data, 0, data.length, HASHING.HASHING_SEED);
    }

    @Override
    public long hash64(byte[] data, long seed) {
        return xxhash64(data, 0, data.length, HASHING64.HASHING_SEED);
    }
}
