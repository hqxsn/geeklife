package com.geeklife.cs341.hashing;

/**
 * Created by andysong on 14-10-24.
 */
public class Fnv1aHash implements Hash32, Hash64 {

    public final static int FNV_32_PRIME = 0x01000193;
    public final static int FNV_32_INIT = 0x811c9dc5;

    public final static long FNV_64_PRIME = 0x100000001b3L;
    public final static long FNV_64_INIT = 0xcbf29ce484222325L;

    /**
     * Implementation of Fnv1a Hash, ported from 64-bit version.
     *
     * @param data
     * @param seed
     * @return
     */
    public long computeFnv1aLongHash(byte[] data, long seed) {
        final int len = data.length;
        long hVal = seed;

        for (int i = 0; i < len; i++) {
            hVal ^= data[i];
            hVal *= FNV_64_PRIME;
        }

        return hVal;
    }

    /**
     * Implementation of Fnv1a Hash, ported from 32-bit version.
     *
     * @param data
     * @param seed
     * @return
     */
    public int computeFnv1aIntHash(byte[] data, int seed) {
        final int len = data.length;
        int hVal = seed;

        for (int i = 0; i < len; i++) {
            hVal ^= data[i];
            hVal *= FNV_32_PRIME;
        }

        return hVal;
    }

    @Override
    public int hash(byte[] data, long seed) {
        return computeFnv1aIntHash(data, HASHING.HASHING_SEED);
    }

    @Override
    public long hash64(byte[] data, long seed) {
        return computeFnv1aLongHash(data, HASHING64.HASHING_SEED);
    }
}
