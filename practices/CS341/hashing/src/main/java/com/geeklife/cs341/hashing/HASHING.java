package com.geeklife.cs341.hashing;

import sun.misc.Hashing;

/**
 * Created by andysong on 14-10-21.
 */
public enum HASHING {




    JDK_STRING_HASH {
        public int hash(String str) {
            int h = Hashing.murmur3_32(HASHING_SEED, str.toCharArray(), 0, str.length());

            // ensure result is not zero to avoid recalcing
            h = (0 != h) ? h : 1;
            return h;
        }

        public int hash(byte[] bytes) {
            int h = Hashing.murmur3_32(HASHING_SEED, bytes, 0, bytes.length);

            // ensure result is not zero to avoid recalcing
            h = (0 != h) ? h : 1;
            return h;
        }

        public int hash(char[] chars) {
            int h = Hashing.murmur3_32(HASHING_SEED, chars, 0, chars.length);

            // ensure result is not zero to avoid recalcing
            h = (0 != h) ? h : 1;
            return h;
        }

    },
    JDK_MD5_HASH {

    }
    ;


    static final int HASHING_SEED;
    static {
        // ??? Stronger random source? (Random/SecureRandom is not available)
        HASHING_SEED = System.identityHashCode(String.class)
                ^ System.identityHashCode(System.class)
                ^ System.identityHashCode(Thread.currentThread())
                ^ (int) (System.currentTimeMillis() >>> 2) // resolution is poor
                ^ (int) (System.nanoTime() >>> 5); // resolution is poor
    }

    public int hash(String str) {
        throw new IllegalAccessError("Cannot access default function");
    }

    public int hash(byte[] bytes) {
        throw new IllegalAccessError("Cannot access default function");
    }

    public int hash(char[] chars) {
        throw new IllegalAccessError("Cannot access default function");
    }


}
