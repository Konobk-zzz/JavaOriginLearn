package com.zzz.java.lang;

/**
 * @author jiaao@yscredit.com
 * @date 2021/6/29 10:45
 */
public class IntegerTest {

    private static void testIntegerAddress() {
        Integer integer1 = 127;
        Integer integer2 = new Integer(127);
        Integer integer3 = Integer.parseInt("127");
        Integer integer4 = new Integer(Integer.parseInt("127"));
        System.out.println();
    }

    private static void testCompareUnsigned() {
        int val1 = -1 + Integer.MIN_VALUE;
        int val2 = 1 + Integer.MIN_VALUE;
        System.out.println("val1: " + val1);
        System.out.println("val2: " + val2);
        System.out.println("CompareUnsigned: " + Integer.compareUnsigned(val1, val2));
        System.out.println("CompareUnsigned: " + Integer.compareUnsigned(1, -1));
        System.out.println("compare: " + Integer.compare(1, -1));
    }

    private static void testMovePosition() {
        System.out.println("-20 >> 2: " + (-20 >> 2));
        System.out.println("-20 >>> 2: " + (-20 >>> 2));
        System.out.println("Integer.toBinaryString(-20 >> 2): " + Integer.toBinaryString(-20 >> 2));
        System.out.println("Integer.toBinaryString(-20 >>> 2): " + Integer.toBinaryString(-20 >>> 2));
    }

    private static void testSignum() {
        System.out.println("Integer.signum(1): " + Integer.signum(1));
        System.out.println("Integer.signum(0): " + Integer.signum(0));
        System.out.println("Integer.signum(-1): " + Integer.signum(-1));
    }

    private static void testReverse() {
        System.out.println("Integer.reverse(1): " + Integer.reverse(1));
        System.out.println("Integer.reverseBytes(1): " + Integer.reverseBytes(1));
    }

    private static void testByte() {
        byte b = (byte) (-128 -1);
        System.out.println(b);
    }

    public static void main(String[] args) {
//        testIntegerAddress();
//        testCompareUnsigned();
//        testMovePosition();
//        testSignum();
//        testReverse();
        testByte();
    }
}
