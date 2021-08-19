package com.zzz.java.lang;

/**
 * @author jiaao@yscredit.com
 * @date 2021/8/19 17:26
 */
public class CharacterTest {

    /**
     * 注意这个方法只能返回数字字符的值，而且是在radix这个基数范围内，若数字字符超过这个基数则返回 -1，若字符不是数字，也返回 -1。
     *
     * 通俗理解：digit()是个边界值判断，不过边界返回字符数字本身数值，超过边界即返回 -1
     */
    private static void testDigit() {
        int digit = Character.digit(51, 6);//正常
        int digit2 = Character.digit(50,2);//越界
        int digit3 = Character.digit(47,2);//非数字ASCII码
        System.out.println("digit = " + digit);
        System.out.println("digit2 = " + digit2);
        System.out.println("digit3 = " + digit3);
    }

    public static void main(String[] args) {
        testDigit();
    }
}
