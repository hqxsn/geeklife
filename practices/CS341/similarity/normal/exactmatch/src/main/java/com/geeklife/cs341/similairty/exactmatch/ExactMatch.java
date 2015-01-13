package com.geeklife.cs341.similairty.exactmatch;

/**
 * Created by andysong on 14-10-20.
 */
public class ExactMatch {

    public static boolean isMatch(String a, String b) {
        return a.equals(b);
    }

    public static boolean isMatchIgnoreCase(String a, String b) {
        return a.equalsIgnoreCase(b);
    }



}
