package com.geeklife.cs341.hashing;

import static com.geeklife.cs341.hashing.HashingUtils.*;

/**
 * Created by andysong on 14-10-21.
 */
public class MurmurHash implements Hash32, Hash64 {

    private final static long M_LONG = 0xc6a4a7935bd1e995L;
    private final static int R_LONG = 47;
    private final static int M_INT = 0x5bd1e995;
    private final static int R_INT = 24;
    private final static int R1_INT = 13;
    private final static int R2_INT = 15;

    /**
     * Implementation of Murmur Hash, ported from 64-bit version.
     *
     * @param data
     * @param seed
     * @return
     */
    public long computeMurmurLongHash(byte[] data, long seed) {
        final int len = data.length;
        long h = seed ^ len;
        int i = 0;

        for (int end = len - 8; i <= end; i += 8) {
            long k = gatherLongLE(data, i);

            k *= M_LONG;
            k ^= k >> R_LONG;
            k *= M_LONG;

            h ^= k;
            h *= M_LONG;
        }

        if (i < len) {
            h ^= gatherPartialLongLE(data, i, (len - i));
            h *= M_LONG;
        }

        h ^= h >> R_LONG;
        h *= M_LONG;
        h ^= h >> R_LONG;

        return h;
    }

    /**
     * Implementation of Murmur Hash, ported from 32-bit version.
     *
     * @param data
     * @param seed
     * @return
     */
    public int computeMurmurIntHash(byte[] data, int seed) {
        final int len = data.length;
        int h = seed ^ len;
        int i = 0;

        for (int end = len - 4; i <= end; i += 4) {
            int k = gatherIntLE(data, i);

            k *= M_INT;
            k ^= k >> R_INT;
            k *= M_INT;

            h *= M_INT;
            h ^= k;
        }

        if (i < len) {
            h ^= gatherPartialIntLE(data, i, (len - i));
            h *= M_INT;
        }

        h ^= h >> R1_INT;
        h *= M_INT;
        h ^= h >> R2_INT;

        return h;
    }

    @Override
    public int hash(byte[] data, long seed) {
        return computeMurmurIntHash(data, HASHING.HASHING_SEED);
    }

    @Override
    public long hash64(byte[] data, long seed) {
        return computeMurmurLongHash(data, HASHING64.HASHING_SEED);
    }
}
