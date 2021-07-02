package com.zzz.java.lang;

/**
 * @author jiaao@yscredit.com
 * @date 2021/6/30 20:16
 */
public class LongTest {

    private static void testToUnsignedString() {
        System.out.println("Long.toUnsignedString(-15L, 4): " + Long.toUnsignedString(-15L, 4));
    }

    public static void main(String[] args) {
//        testToUnsignedString();
        System.out.println(Long.toBinaryString(-2L << 1));
        System.out.println(Long.toBinaryString(-2L >>> -1));
        System.out.println(Long.rotateLeft(-2L, 1));
//        System.out.println("(i2 * 52429) >>> (16+3): " + Long.reverse(4L));
    }
}
