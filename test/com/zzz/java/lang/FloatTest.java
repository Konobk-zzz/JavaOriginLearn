package com.zzz.java.lang;

import java.util.Objects;

/**
 * @author jiaao@yscredit.com
 * @date 2021/7/5 9:28
 */
public class FloatTest {

    public static void testInstanceIsNew() {
        Float float1 = new Float(1F);
        Float float2 = Float.valueOf(1F);
        Float float3 = new Float(129F);
        Float float4 = Float.valueOf(129F);
        Float float5 = new Float(129F);
        System.out.println("float1 Instance.hashCode(): " + float1.hashCode());
        System.out.println("float2 Instance.hashCode(): " + float2.hashCode());
        System.out.println("float3 Instance.hashCode(): " + float3.hashCode());
        System.out.println("float4 Instance.hashCode(): " + float4.hashCode());
        System.out.println("float5 Instance.hashCode(): " + float5.hashCode());
    }

    public static void testFloatToIntBits() {
        System.out.println("Float.floatToIntBits(0.0f / 0.0f) :" + Float.floatToIntBits(0.0f / 0.0f));
        System.out.println("Float.floatToIntBits(1.0f / 0.0f) :" + Float.floatToIntBits(1.0f / 0.0f));
        System.out.println("Float.floatToIntBits(-1.0f / 0.0f) :" + Float.floatToIntBits(-1.0f / 0.0f));
        System.out.println("Int 2 NegativeInfinite Float: " + Float.intBitsToFloat(-8388608));
    }

    private static void testM(int bits) {
        System.out.println("(bits & 0x7fffff) | 0x800000 : " + ((bits & 0x7fffff) | 0x800000));
    }

    private static double intBitsToFloatCopy(int bits) {
        int s = ((bits >> 31) == 0) ? 1 : -1;
        int e = ((bits >> 23) & 0xff);
        int m = (e == 0) ?
                         (bits & 0x7fffff) << 1 :
                         (bits & 0x7fffff) | 0x800000;
        return s * m * Math.pow(2.0, e - 150.0);
    }

    public static void main(String[] args) {
//        testInstanceIsNew();
//        testFloatToIntBits();
//        testM(1);
        System.out.println("intBitsToFloatCopy(1065353216) : " + intBitsToFloatCopy(1065353216));
        System.out.println("Float.intBitsToFloat(1065353216) : " + Float.intBitsToFloat(1065353216));
        System.out.println(Float.floatToIntBits(1.0F));
    }
}
