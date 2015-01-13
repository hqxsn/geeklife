package com.geeklife.cs341.hashing;

/**
 * Created by andysong on 14-10-21.
 */
public enum HASHING64 {

    CITY_HASH {

        public long hash64(byte[] bytes) {

            return CityHash.cityHash64WithSeed(bytes, 0, bytes.length, HASHING_SEED);
        }

        public long hash64(String str) {

            byte[] bytes = str.getBytes();

            return CityHash.cityHash64WithSeed(bytes, 0, bytes.length, HASHING_SEED);
        }

    };

    static final int HASHING_SEED;
    static {
        // ??? Stronger random source? (Random/SecureRandom is not available)
        HASHING_SEED = System.identityHashCode(String.class)
                ^ System.identityHashCode(System.class)
                ^ System.identityHashCode(Thread.currentThread())
                ^ (int) (System.currentTimeMillis() >>> 2) // resolution is poor
                ^ (int) (System.nanoTime() >>> 5); // resolution is poor
    }


    public long hash64(byte[] bytes) {
        throw new IllegalAccessError("Cannot access default function");
    }

    public long hash64(String str) {
        throw new IllegalAccessError("Cannot access default function");
    }
}
