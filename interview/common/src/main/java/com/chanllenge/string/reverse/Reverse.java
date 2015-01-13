package com.chanllenge.string.reverse;

/**
 * Created by andysong on 14-11-18.
 */
public class Reverse {

    public static String reverse(String str) {

        if (str == null || str.isEmpty()) return str;

        char[] chars = str.toCharArray();

        reverse(chars, 0, chars.length);


        int start = 0;
        for(int i=0; i<chars.length;++i) {
            if(chars[i] == ' ') {
                reverse(chars, start, i);
                start = i+1;
            }
        }

        if (start < chars.length) {

            reverse(chars, start, chars.length);
        }

        return new String(chars);
    }

    static void reverse(char[] chars, int start, int end) {
        int last = end-1;
        for(int i=0; i< end; ++i) {
            if (start + i  >= last - i) break;

            char tmp = chars[start + i ];
            chars[start + i] = chars[last-i];
            chars[last-i]=tmp;
        }

    }

    public static void main(String[] args) {
        System.out.println(reverse("hello I am Andy"));
    }

}
