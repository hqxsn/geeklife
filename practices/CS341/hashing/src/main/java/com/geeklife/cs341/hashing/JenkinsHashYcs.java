package com.geeklife.cs341.hashing;

/**
 * Created by andysong on 14-10-23.
 */
public class JenkinsHashYcs implements Hash32, Hash64 {

    /**
     * A Java implementation of hashword from lookup3.c by Bob Jenkins
     * (<a href="http://burtleburtle.net/bob/c/lookup3.c">original source</a>).
     *
     * @param k   the key to hash
     * @param offset   offset of the start of the key
     * @param length   length of the key
     * @param initval  initial value to fold into the hash
     * @return  the 32 bit hash code
     */
    @SuppressWarnings("fallthrough")
    public static int lookup3(int[] k, int offset, int length, int initval) {
        int a,b,c;
        a = b = c = 0xdeadbeef + (length<<2) + initval;

        int i=offset;
        while (length > 3)
        {
            a += k[i];
            b += k[i+1];
            c += k[i+2];

            // mix(a,b,c)... Java needs "out" parameters!!!
            // Note: recent JVMs (Sun JDK6) turn pairs of shifts (needed to do a rotate)
            // into real x86 rotate instructions.
            {
                a -= c;  a ^= (c<<4)|(c>>>-4);   c += b;
                b -= a;  b ^= (a<<6)|(a>>>-6);   a += c;
                c -= b;  c ^= (b<<8)|(b>>>-8);   b += a;
                a -= c;  a ^= (c<<16)|(c>>>-16); c += b;
                b -= a;  b ^= (a<<19)|(a>>>-19); a += c;
                c -= b;  c ^= (b<<4)|(b>>>-4);   b += a;
            }

            length -= 3;
            i += 3;
        }

        switch(length) {
            case 3 : c+=k[i+2];  // fall through
            case 2 : b+=k[i+1];  // fall through
            case 1 : a+=k[i+0];  // fall through
                // final(a,b,c);
            {
                c ^= b; c -= (b<<14)|(b>>>-14);
                a ^= c; a -= (c<<11)|(c>>>-11);
                b ^= a; b -= (a<<25)|(a>>>-25);
                c ^= b; c -= (b<<16)|(b>>>-16);
                a ^= c; a -= (c<<4)|(c>>>-4);
                b ^= a; b -= (a<<14)|(a>>>-14);
                c ^= b; c -= (b<<24)|(b>>>-24);
            }
            case 0:
                break;
        }
        return c;
    }

    /**
     * <p>The hash value of a character sequence is defined to be the hash of
     * it's unicode code points, according to
     * </p>
     * <p>If you know the number of code points in the {@code CharSequence}, you can
     * generate the same hash as the original lookup3
     * via {@code lookup3ycs(s, start, end, initval+(numCodePoints<<2))}
     */
    public static int lookup3ycs(CharSequence s, int start, int end, int initval) {
        int a,b,c;
        a = b = c = 0xdeadbeef + initval;
        // only difference from lookup3 is that "+ (length<<2)" is missing
        // since we don't know the number of code points to start with,
        // and don't want to have to pre-scan the string to find out.

        int i=start;
        boolean mixed=true;  // have the 3 state variables been adequately mixed?
        for(;;) {
            if (i>= end) break;
            mixed=false;
            char ch;
            ch = s.charAt(i++);
            a += Character.isHighSurrogate(ch) && i< end ? Character.toCodePoint(ch, s.charAt(i++)) : ch;
            if (i>= end) break;
            ch = s.charAt(i++);
            b += Character.isHighSurrogate(ch) && i< end ? Character.toCodePoint(ch, s.charAt(i++)) : ch;
            if (i>= end) break;
            ch = s.charAt(i++);
            c += Character.isHighSurrogate(ch) && i< end ? Character.toCodePoint(ch, s.charAt(i++)) : ch;
            if (i>= end) break;

            // mix(a,b,c)... Java needs "out" parameters!!!
            // Note: recent JVMs (Sun JDK6) turn pairs of shifts (needed to do a rotate)
            // into real x86 rotate instructions.
            {
                a -= c;  a ^= (c<<4)|(c>>>-4);   c += b;
                b -= a;  b ^= (a<<6)|(a>>>-6);   a += c;
                c -= b;  c ^= (b<<8)|(b>>>-8);   b += a;
                a -= c;  a ^= (c<<16)|(c>>>-16); c += b;
                b -= a;  b ^= (a<<19)|(a>>>-19); a += c;
                c -= b;  c ^= (b<<4)|(b>>>-4);   b += a;
            }
            mixed=true;
        }


        if (!mixed) {
            // final(a,b,c)
            c ^= b; c -= (b<<14)|(b>>>-14);
            a ^= c; a -= (c<<11)|(c>>>-11);
            b ^= a; b -= (a<<25)|(a>>>-25);
            c ^= b; c -= (b<<16)|(b>>>-16);
            a ^= c; a -= (c<<4)|(c>>>-4);
            b ^= a; b -= (a<<14)|(a>>>-14);
            c ^= b; c -= (b<<24)|(b>>>-24);
        }

        return c;
    }


    /**<p>This is the 64 bit version of lookup3ycs, corresponding to Bob Jenkin's
     * lookup3 hashlittle2 with initval biased by -(numCodePoints<<2).  It is equivalent
     * to lookup3ycs in that if the high bits of initval==0, then the low bits of the
     * result will be the same as lookup3ycs.
     * </p>
     */
    public static long lookup3ycs64(CharSequence s, int start, int end, long initval) {
        int a,b,c;
        a = b = c = 0xdeadbeef + (int)initval;
        c += (int)(initval>>>32);
        // only difference from lookup3 is that "+ (length<<2)" is missing
        // since we don't know the number of code points to start with,
        // and don't want to have to pre-scan the string to find out.

        int i=start;
        boolean mixed=true;  // have the 3 state variables been adequately mixed?
        for(;;) {
            if (i>= end) break;
            mixed=false;
            char ch;
            ch = s.charAt(i++);
            a += Character.isHighSurrogate(ch) && i< end ? Character.toCodePoint(ch, s.charAt(i++)) : ch;
            if (i>= end) break;
            ch = s.charAt(i++);
            b += Character.isHighSurrogate(ch) && i< end ? Character.toCodePoint(ch, s.charAt(i++)) : ch;
            if (i>= end) break;
            ch = s.charAt(i++);
            c += Character.isHighSurrogate(ch) && i< end ? Character.toCodePoint(ch, s.charAt(i++)) : ch;
            if (i>= end) break;

            // mix(a,b,c)... Java needs "out" parameters!!!
            // Note: recent JVMs (Sun JDK6) turn pairs of shifts (needed to do a rotate)
            // into real x86 rotate instructions.
            {
                a -= c;  a ^= (c<<4)|(c>>>-4);   c += b;
                b -= a;  b ^= (a<<6)|(a>>>-6);   a += c;
                c -= b;  c ^= (b<<8)|(b>>>-8);   b += a;
                a -= c;  a ^= (c<<16)|(c>>>-16); c += b;
                b -= a;  b ^= (a<<19)|(a>>>-19); a += c;
                c -= b;  c ^= (b<<4)|(b>>>-4);   b += a;
            }
            mixed=true;
        }


        if (!mixed) {
            // final(a,b,c)
            c ^= b; c -= (b<<14)|(b>>>-14);
            a ^= c; a -= (c<<11)|(c>>>-11);
            b ^= a; b -= (a<<25)|(a>>>-25);
            c ^= b; c -= (b<<16)|(b>>>-16);
            a ^= c; a -= (c<<4)|(c>>>-4);
            b ^= a; b -= (a<<14)|(a>>>-14);
            c ^= b; c -= (b<<24)|(b>>>-24);
        }

        return c + (((long)b) << 32);
    }

    /*
     * --------------------------------------------------------------------
     * hash() -- hash a variable-length key into a 64-bit value k : the key (the
     * unaligned variable-length array of bytes) level : can be any 8-byte value
     * Returns a 64-bit value. Every bit of the key affects every bit of the
     * return value. No funnels. Every 1-bit and 2-bit delta achieves avalanche.
     * About 41+5len instructions.
     *
     * The best hash table sizes are powers of 2. There is no need to do mod a
     * prime (mod is sooo slow!). If you need less than 64 bits, use a bitmask.
     * For example, if you need only 10 bits, do h = (h & hashmask(10)); In
     * which case, the hash table should have hashsize(10) elements.
     *
     * If you are hashing n strings (ub1 **)k, do it like this: for (i=0, h=0;
     * i<n; ++i) h = hash( k[i], len[i], h);
     *
     * By Bob Jenkins, Jan 4 1997. bob_jenkins@burtleburtle.net. You may use
     * this code any way you wish, private, educational, or commercial, but I
     * would appreciate if you give me credit.
     *
     * See http://burtleburtle.net/bob/hash/evahash.html Use for hash table
     * lookup, or anything where one collision in 2^^64 is acceptable. Do NOT
     * use for cryptographic purposes.
     * --------------------------------------------------------------------
     */
    public long computeJenkinsLongHash(byte[] k, long level) {
        /* Set up the internal state */
        long a = level;
        long b = level;
        /* the golden ratio; an arbitrary value */
        long c = 0x9e3779b97f4a7c13L;
        int len = k.length;

        /*---------------------------------------- handle most of the key */
        int i = 0;
        while (len >= 24) {
            a += HashingUtils.gatherLongLE(k, i);
            b += HashingUtils.gatherLongLE(k, i + 8);
            c += HashingUtils.gatherLongLE(k, i + 16);

            /* mix64(a, b, c); */
            a -= b; a -= c; a ^= (c >> 43);
            b -= c; b -= a; b ^= (a << 9);
            c -= a; c -= b; c ^= (b >> 8);
            a -= b; a -= c; a ^= (c >> 38);
            b -= c; b -= a; b ^= (a << 23);
            c -= a; c -= b; c ^= (b >> 5);
            a -= b; a -= c; a ^= (c >> 35);
            b -= c; b -= a; b ^= (a << 49);
            c -= a; c -= b; c ^= (b >> 11);
            a -= b; a -= c; a ^= (c >> 12);
            b -= c; b -= a; b ^= (a << 18);
            c -= a; c -= b; c ^= (b >> 22);
            /* mix64(a, b, c); */

            i += 24;
            len -= 24;
        }

        /*------------------------------------- handle the last 23 bytes */
        c += k.length;

        if (len > 0) {
            if (len >= 8) {
                a += HashingUtils.gatherLongLE(k, i);
                if (len >= 16) {
                    b += HashingUtils.gatherLongLE(k, i + 8);
                    // this is bit asymmetric; LSB is reserved for length (see
                    // above)
                    if (len > 16) {
                        c += (HashingUtils.gatherPartialLongLE(k, i + 16,
                                len - 16) << 8);
                    }
                } else if (len > 8) {
                    b += HashingUtils.gatherPartialLongLE(k, i + 8, len - 8);
                }
            } else {
                a += HashingUtils.gatherPartialLongLE(k, i, len);
            }
        }

        /* mix64(a, b, c); */
        a -= b; a -= c; a ^= (c >> 43);
        b -= c; b -= a; b ^= (a << 9);
        c -= a; c -= b; c ^= (b >> 8);
        a -= b; a -= c; a ^= (c >> 38);
        b -= c; b -= a; b ^= (a << 23);
        c -= a; c -= b; c ^= (b >> 5);
        a -= b; a -= c; a ^= (c >> 35);
        b -= c; b -= a; b ^= (a << 49);
        c -= a; c -= b; c ^= (b >> 11);
        a -= b; a -= c; a ^= (c >> 12);
        b -= c; b -= a; b ^= (a << 18);
        c -= a; c -= b; c ^= (b >> 22);
        /* mix64(a, b, c); */

        return c;
    }

    /*
   -------------------------------------------------------------------------------
   hashlittle() -- hash a variable-length key into a 32-bit value
     k       : the key (the unaligned variable-length array of bytes)
     length  : the length of the key, counting by bytes
     initval : can be any 4-byte value
   Returns a 32-bit value.  Every bit of the key affects every bit of
   the return value.  Two keys differing by one or two bits will have
   totally different hash values.
   The best hash table sizes are powers of 2.  There is no need to do
   mod a prime (mod is sooo slow!).  If you need less than 32 bits,
   use a bitmask.  For example, if you need only 10 bits, do
     h = (h & hashmask(10));
   In which case, the hash table should have hashsize(10) elements.
   If you are hashing n strings (uint8_t **)k, do it like this:
     for (i=0, h=0; i<n; ++i) h = hashlittle( k[i], len[i], h);
   By Bob Jenkins, 2006.  bob_jenkins@burtleburtle.net.  You may use this
   code any way you wish, private, educational, or commercial.  It's free.
   Use for hash table lookup, or anything where one collision in 2^^32 is
   acceptable.  Do NOT use for cryptographic purposes.
   -------------------------------------------------------------------------------
   */
    public int computeJenkinsIntHash(byte[] k, int level) {
        /* Set up the internal state */
        int a, b, c;
        a = b = c = (0xdeadbeef + (k.length << 2) + level);

        int len = k.length;

        /*---------------------------------------- handle most of the key */
        int i = 0;
        while (len >= 12) {
            a += HashingUtils.gatherIntLE(k, i);
            b += HashingUtils.gatherIntLE(k, i + 4);
            c += HashingUtils.gatherIntLE(k, i + 8);

            /* mix(a, b, c); */
            a -= c;  a ^= HashingUtils.rotateInt(c, 4);  c += b;
            b -= a;  b ^= HashingUtils.rotateInt(a, 6);  a += c;
            c -= b;  c ^= HashingUtils.rotateInt(b, 8);  b += a;
            a -= c;  a ^= HashingUtils.rotateInt(c,16);  c += b;
            b -= a;  b ^= HashingUtils.rotateInt(a,19);  a += c;
            c -= b;  c ^= HashingUtils.rotateInt(b, 4);  b += a;
            /* mix(a, b, c); */

            i += 12;
            len -= 12;
        }

        /*------------------------------------- handle the last 23 bytes */
        c += k.length;

        if (len > 0) {
            if (len >= 4) {
                a += HashingUtils.gatherIntLE(k, i);
                if (len >= 8) {
                    b += HashingUtils.gatherIntLE(k, i + 4);
                    // this is bit asymmetric; LSB is reserved for length (see
                    // above)
                    if (len > 8) {
                        c += (HashingUtils.gatherPartialIntLE(k, i + 8,
                                len - 8) << 8);
                    }
                } else if (len > 4) {
                    b += HashingUtils.gatherPartialIntLE(k, i + 4, len - 4);
                }
            } else {
                a += HashingUtils.gatherPartialIntLE(k, i, len);
            }
        }

        /* final(a, b, c); */
        c ^= b; c -= HashingUtils.rotateInt(b,14);
        a ^= c; a -= HashingUtils.rotateInt(c,11);
        b ^= a; b -= HashingUtils.rotateInt(a,25);
        c ^= b; c -= HashingUtils.rotateInt(b,16);
        a ^= c; a -= HashingUtils.rotateInt(c,4);
        b ^= a; b -= HashingUtils.rotateInt(a,14);
        c ^= b; c -= HashingUtils.rotateInt(b,24);
        /* final(a, b, c); */

        return c;
    }

    @Override
    public int hash(byte[] data, long seed) {
        return computeJenkinsIntHash(data, 0);
    }

    @Override
    public long hash64(byte[] data, long seed) {
        return computeJenkinsLongHash(data, 0l);
    }
}
