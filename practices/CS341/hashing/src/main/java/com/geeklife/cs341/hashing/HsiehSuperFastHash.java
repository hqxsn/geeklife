package com.geeklife.cs341.hashing;

/**
 * Created by andysong on 14-10-24.
 */
public class HsiehSuperFastHash implements Hash32, Hash64 {

    private final int[] LEFT_SHIFT_WIDTHS = { 0, 10, 11, 16, 43, 42, 43, 48 };
    private final int[] RIGHT_SHIFT_WIDTHS = { 0, 1, 17, 11, 49, 33, 49, 43 };

    /**
     * Implementation of Fnv1 Hash, ported from 64-bit version.
     *
     * @param data
     * @param seed
     * @return
     */
    public long computeHsiehLongHash(byte[] data, long seed) {
        if (data == null) {
            return seed;
        }

        final int len = data.length;
        long hVal = seed;

        for (int i = 0; i < len - 8; i += 8) {
            hVal += HashingUtils.gatherIntLE(data, i);
            long tmp = HashingUtils.gatherIntLE(data, i + 4) << 27;
            hVal = (hVal << 32) ^ tmp;
            hVal += hVal >> 43;
        }

        final int rem = len & 7;

        if (rem > 0) {
            final int i = len - rem;
            final int t1 = (rem >= 4) ? HashingUtils.gatherIntLE(data, i)
                    : HashingUtils.gatherPartialIntLE(data, i, Math.min(rem,
                    3));
            final int t2 = (rem > 4) ? HashingUtils.gatherPartialIntLE(data,
                    i + 4, rem - 4) : 0;

            hVal += t1;
            hVal ^= hVal << LEFT_SHIFT_WIDTHS[rem];
            hVal ^= t2;
            hVal += hVal >> RIGHT_SHIFT_WIDTHS[rem];
        }

        hVal ^= hVal << 35;
        hVal += hVal >> 37;
        hVal ^= hVal << 36;
        hVal += hVal >> 49;
        hVal ^= hVal << 57;
        hVal += hVal >> 38;
        hVal ^= hVal << 3;
        hVal += hVal >> 5;
        hVal ^= hVal << 4;
        hVal += hVal >> 17;
        hVal ^= hVal << 25;
        hVal += hVal >> 6;

        return hVal;
    }

    /**
     * Implementation of Fnv1 Hash, ported from 32-bit version.
     *
     * @param data
     * @param seed
     * @return
     */
    public int computeHsiehIntHash(byte[] data, int seed) {
        if (data == null) {
            return seed;
        }

        final int len = data.length;
        int hVal = seed;
        int rem = len & 3;

        for (int i = 0; i < len - 4; i += 4) {
            hVal += HashingUtils.gatherPartialIntLE(data, i, 2);
            int tmp = HashingUtils.gatherPartialIntLE(data, i + 2, 2) << 11;
            hVal = (hVal << 16) ^ tmp;
            hVal += hVal >> 11;
        }

        if (rem > 0) {
            final int i = len - rem;

            final int t1 = HashingUtils.gatherPartialIntLE(data, i, Math
                    .min(rem, 2));
            final int t2 = rem > 2 ? HashingUtils.gatherPartialIntLE(data,
                    i + 2, rem - 2) : 0;

            hVal += t1;
            hVal ^= hVal << LEFT_SHIFT_WIDTHS[rem];
            hVal ^= t2;
            hVal += hVal >> RIGHT_SHIFT_WIDTHS[rem];
        }

        hVal ^= hVal << 3;
        hVal += hVal >> 5;
        hVal ^= hVal << 4;
        hVal += hVal >> 17;
        hVal ^= hVal << 25;
        hVal += hVal >> 6;

        return hVal;
    }

    @Override
    public int hash(byte[] data, long seed) {
        return computeHsiehIntHash(data, HASHING.HASHING_SEED);
    }

    @Override
    public long hash64(byte[] data, long seed) {
        return computeHsiehLongHash(data, HASHING64.HASHING_SEED);
    }
}
