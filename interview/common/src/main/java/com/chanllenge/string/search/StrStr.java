package com.chanllenge.string.search;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andysong on 14-11-17.
 * Classic HaysStack search
 */
public class StrStr {


    public static int find(String haystack, String needle) {


        if (haystack == null && haystack.isEmpty()) return -1;
        if (needle == null && needle.isEmpty()) return -1;

        if (haystack.length() < needle.length())
            return -1;

        int srcInx = 0;
        int serInx = 0;


        while (srcInx < haystack.length()) {

            int matchInx = srcInx;
            while (srcInx < haystack.length() && serInx < needle.length() &&
                    (haystack.charAt(srcInx) == needle.charAt(serInx))) {
                srcInx++;
                serInx++;
            }

            if (serInx == needle.length()) return matchInx;

            srcInx = matchInx + 1;
            serInx = 0;

        }

        return -1;
    }

    public static int find2(String haystack, String needle) {

        if (haystack == null && haystack.isEmpty()) return -1;
        if (needle == null && needle.isEmpty()) return -1;

        if (haystack.length() < needle.length())
            return -1;

        int srcInx = 0;
        int serInx = 0;

        if (needle.length() == 1) {
            for (;srcInx < haystack.length(); srcInx++) {
                if (haystack.charAt(srcInx) == needle.charAt(serInx)) return srcInx;
            }
        }

        while (srcInx < haystack.length()) {

             if (needle.length() == 2) {
                 int one = 0, two=1;

                 if (haystack.charAt(srcInx + one) == needle.charAt(serInx + one) &&
                         haystack.charAt(srcInx + two) == needle.charAt(serInx + two)) {
                     return srcInx;
                 } else if (haystack.charAt(srcInx + one) == needle.charAt(serInx + two)) {
                     srcInx++;
                 } else {
                     srcInx+=2;
                 }
             }

            if (needle.length() >= 3) {
                int one = 0, two=1, three=2;

                if (haystack.charAt(srcInx + one) == needle.charAt(serInx + one) &&
                        haystack.charAt(srcInx + two) == needle.charAt(serInx + two) &&
                        haystack.charAt(srcInx + three) == needle.charAt(serInx + three)) {

                    int next = three;
                    for (;next < needle.length();next++) {
                        if (haystack.charAt(srcInx + next) != needle.charAt(serInx + next)) break;
                    }

                    if (next == needle.length()) return srcInx;

                } else if (haystack.charAt(srcInx + one) == needle.charAt(serInx + two)) {
                    srcInx++;
                } else if (haystack.charAt(srcInx + one) == needle.charAt(serInx + three)) {
                    srcInx+=2;
                } else {
                    srcInx+=3;
                }
            }
        }

        return -1;
    }


    public static List<Integer> findAll(String pattern, String source) {
        char[] x = pattern.toCharArray(), y = source.toCharArray();
        int i, j, s, d, last, m = x.length, n = y.length;
        List<Integer> result = new ArrayList<Integer>();

        int[] b = new int[65536];

		/* Pre processing */
        for (i = 0; i < b.length; i++)
            b[i] = 0;
        s = 1;
        for (i = m - 1; i >= 0; i--) {
            b[x[i]] |= s;
            s <<= 1;
        }

		/* Searching phase */
        j = 0;
        while (j <= n - m) {
            i = m - 1;
            last = m;
            d = ~0;
            while (i >= 0 && d != 0) {
                d &= b[y[j + i]];
                i--;
                if (d != 0) {
                    if (i >= 0)
                        last = i + 1;
                    else {
                        result.add(j);
                    }
                }
                d <<= 1;
            }
            j += last;
        }


        return result;
    }


    public static void main(String[] args) {

        long total = 0;
        long max=0, min = 0;
        for (int i=0; i<100; ++i) {
            long time = System.nanoTime();
            find("I am your honey boyfriend", "honey");

            long elapse = (System.nanoTime() - time);
            total += elapse;

            if (max == 0) max=elapse;
            if (max < elapse) max=elapse;

            if (min==0) min= elapse;
            if (min > elapse) min=elapse;
        }

        System.out.println("[Find stats ");
        System.out.println("Total:" + total);
        System.out.println("AVG:" + total / 100);
        System.out.println("MAX:" + max);
        System.out.println("MIN:" + min + "]");

        System.gc();
        System.gc();
        System.gc();

        total = 0;
        max=0; min = 0;
        for (int i=0; i<100; ++i) {
            long time = System.nanoTime();
            find2("I am your honey boyfriend", "honey");

            long elapse = (System.nanoTime() - time);
            total += elapse;

            if (max == 0) max=elapse;
            if (max < elapse) max=elapse;

            if (min==0) min= elapse;
            if (min > elapse) min=elapse;
        }

        System.out.println("[Find2 stats ");
        System.out.println("Total:" + total);
        System.out.println("AVG:" + total / 100);
        System.out.println("MAX:" + max);
        System.out.println("MIN:" + min + "]");



    }

}
