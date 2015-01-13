package com.geeklife.cs341.hashing;

/**
 * Created by andysong on 14-10-30.
 */
public class CWowHash implements Hash64, Hash32 {

    public static final long LONG_LO_MASK = 0x00000000FFFFFFFFL;

    public final static int CWOW_32_M = 0x57559429;
    public final static int CWOW_32_N = 0x5052acdb;

    public final static long CWOW_64_M = 0x95b47aa3355ba1a1L;
    public final static long CWOW_64_M_LO = CWOW_64_M & LONG_LO_MASK;
    public final static long CWOW_64_M_HI = CWOW_64_M >>> 32;

    public final static long CWOW_64_N = 0x8a970be7488fda55L;
    public final static long CWOW_64_N_LO = CWOW_64_N & LONG_LO_MASK;
    public final static long CWOW_64_N_HI = CWOW_64_N >>> 32;

    /**
     * Implementation of CrapWow Hash, ported from 64-bit version.
     */
    public long computeCWowLongHash(byte[] data, long seed) {
        final int length = data.length;
        /* cwfold( a, b, lo, hi ): */
        /* p = (u64)(a) * (u128)(b); lo ^=(u64)p; hi ^= (u64)(p >> 64) */
        /* cwmixa( in ): cwfold( in, m, k, h ) */
        /* cwmixb( in ): cwfold( in, n, h, k ) */

        long hVal = seed;
        long k = length + seed + CWOW_64_N;

        int pos = 0;
        int len = length;

        long aL, aH, bL, bH;
        long r1, r2, r3, rML;
        long pL;
        long pH;

        while (len >= 16) {
            /* cwmixb(X) = cwfold( X, N, hVal, k ) */
            aL = HashingUtils.gatherIntLE(data, pos) & LONG_LO_MASK; pos += 4;
            aH = HashingUtils.gatherIntLE(data, pos) & LONG_LO_MASK; pos += 4;
            bL = CWOW_64_N_LO; bH = CWOW_64_N_HI;
            r1 = aL * bL; r2 = aH * bL; r3 = aL * bH;
            rML = (r1 >>> 32) + (r2 & LONG_LO_MASK) + (r3 & LONG_LO_MASK);
            pL = (r1 & LONG_LO_MASK) + ((rML & LONG_LO_MASK) << 32);
            pH = (aH * bH) + (rML >>> 32);
            hVal ^= pL; k ^= pH;

            /* cwmixa(Y) = cwfold( Y, M, k, hVal ) */
            aL = HashingUtils.gatherIntLE(data, pos) & LONG_LO_MASK; pos += 4;
            aH = HashingUtils.gatherIntLE(data, pos) & LONG_LO_MASK; pos += 4;
            bL = CWOW_64_M_LO; bH = CWOW_64_M_HI;
            r1 = aL * bL; r2 = aH * bL; r3 = aL * bH;
            rML = (r1 >>> 32) + (r2 & LONG_LO_MASK) + (r3 & LONG_LO_MASK);
            pL = (r1 & LONG_LO_MASK) + ((rML & LONG_LO_MASK) << 32);
            pH = (aH * bH) + (rML >>> 32);
            k ^= pL; hVal ^= pH;

            len -= 16;
        }

        if (len >= 8) {
            /* cwmixb(X) = cwfold( X, N, hVal, k ) */
            aL = HashingUtils.gatherIntLE(data, pos) & LONG_LO_MASK; pos += 4;
            aH = HashingUtils.gatherIntLE(data, pos) & LONG_LO_MASK; pos += 4;
            bL = CWOW_64_N_LO; bH = CWOW_64_N_HI;
            r1 = aL * bL; r2 = aH * bL; r3 = aL * bH;
            rML = (r1 >>> 32) + (r2 & LONG_LO_MASK) + (r3 & LONG_LO_MASK);
            pL = (r1 & LONG_LO_MASK) + ((rML & LONG_LO_MASK) << 32);
            pH = (aH * bH) + (rML >>> 32);
            hVal ^= pL; k ^= pH;

            len -= 8;
        }

        if (len > 0) {
            aL = HashingUtils.gatherPartialLongLE(data, pos, len);
            aH = aL >> 32;
            aL = aL & LONG_LO_MASK;

            /* cwmixa(Y) = cwfold( Y, M, k, hVal ) */
            bL = CWOW_64_M_LO;
            bH = CWOW_64_M_HI;
            r1 = aL * bL; r2 = aH * bL; r3 = aL * bH;
            rML = (r1 >>> 32) + (r2 & LONG_LO_MASK) + (r3 & LONG_LO_MASK);
            pL = (r1 & LONG_LO_MASK) + ((rML & LONG_LO_MASK) << 32);
            pH = (aH * bH) + (rML >>> 32);
            k ^= pL; hVal ^= pH;
        }

        /* cwmixb(X) = cwfold( X, N, hVal, k ) */
        aL = (hVal ^ (k + CWOW_64_N));
        aH = aL >> 32;
        aL = aL & LONG_LO_MASK;

        bL = CWOW_64_N_LO;
        bH = CWOW_64_N_HI;
        r1 = aL * bL; r2 = aH * bL; r3 = aL * bH;
        rML = (r1 >>> 32) + (r2 & LONG_LO_MASK) + (r3 & LONG_LO_MASK);
        pL = (r1 & LONG_LO_MASK) + ((rML & LONG_LO_MASK) << 32);
        pH = (aH * bH) + (rML >>> 32);
        hVal ^= pL; k ^= pH;

        hVal ^= k;

        return hVal;
    }

    /**
     * Implementation of CrapWow Hash, ported from 32-bit version.
     */
    public int computeCWowIntHash(byte[] data, int seed) {
        final int length = data.length;

        /* cwfold( a, b, lo, hi ): */
        /* p = (u32)(a) * (u64)(b); lo ^=(u32)p; hi ^= (u32)(p >> 32) */
        /* cwmixa( in ): cwfold( in, m, k, h ) */
        /* cwmixb( in ): cwfold( in, n, h, k ) */

        int hVal = seed;
        int k = length + seed + CWOW_32_N;
        long p = 0;

        int pos = 0;
        int len = length;

        while (len >= 8) {
            int i1 = HashingUtils.gatherIntLE(data, pos);
            int i2 = HashingUtils.gatherIntLE(data, pos + 4);

            /* cwmixb(i1) = cwfold( i1, N, hVal, k ) */
            p = i1 * (long) CWOW_32_N;
            k ^= p & LONG_LO_MASK;
            hVal ^= (p >> 32);
            /* cwmixa(i2) = cwfold( i2, M, k, hVal ) */
            p = i2 * (long) CWOW_32_M;
            hVal ^= p & LONG_LO_MASK;
            k ^= (p >> 32);

            pos += 8;
            len -= 8;
        }

        if (len >= 4) {
            int i1 = HashingUtils.gatherIntLE(data, pos);

            /* cwmixb(i1) = cwfold( i1, N, hVal, k ) */
            p = i1 * (long) CWOW_32_N;
            k ^= p & LONG_LO_MASK;
            hVal ^= (p >> 32);

            pos += 4;
            len -= 4;
        }

        if (len > 0) {
            int i1 = HashingUtils.gatherPartialIntLE(data, pos, len);

            /* cwmixb(i1) = cwfold( i1, N, hVal, k ) */
            p = (i1 & ((1 << (len * 8)) - 1)) * (long) CWOW_32_M;
            hVal ^= p & LONG_LO_MASK;
            k ^= (p >> 32);
        }

        p = (hVal ^ (k + CWOW_32_N)) * (long) CWOW_32_N;
        k ^= p & LONG_LO_MASK;
        hVal ^= (p >> 32);
        hVal ^= k;

        return hVal;
    }

    @Override
    public int hash(byte[] data, long seed) {
        return 0;
    }

    @Override
    public long hash64(byte[] data, long seed) {
        return 0;
    }
}
