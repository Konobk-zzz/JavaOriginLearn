package com.zzz.java.lang;

/**
 * @date 2021/7/6 10:40
 */
public class DoubleTest {

    private static void testToHexString() {
        System.out.println("Double.toHexString(1.0) : " + Double.toHexString(1.0));
    }

    private static void testDoubleToLongBits() {
        System.out.println("Double.doubleToLongBits(Double.longBitsToDouble(0xfff0000000000000L)): " + Double.doubleToLongBits(Double.longBitsToDouble(0xfff0000000000000L)));
    }

    public static void main(String[] args) {
//        testToHexString();
        testDoubleToLongBits();
    }
}
