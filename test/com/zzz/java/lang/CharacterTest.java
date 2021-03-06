package com.zzz.java.lang;

/**
 * @date 2021/8/19 17:26
 */
public class CharacterTest {

    /**
     * 注意这个方法只能返回数字字符的值，而且是在radix这个基数范围内，若数字字符超过这个基数则返回 -1，若字符不是数字，也返回 -1。
     *
     * 通俗理解：digit()是个边界值判断，不过边界返回字符数字本身数值，超过边界即返回 -1
     */
    private static void testDigit() {
        int digit = Character.digit(66, 16);//正常
        int digit2 = Character.digit(50,2);//越界
        int digit3 = Character.digit(47,2);//非数字ASCII码
        System.out.println("digit = " + digit);
        System.out.println("digit2 = " + digit2);
        System.out.println("digit3 = " + digit3);
    }

    private static void codePoint2Char() {
        System.out.println(Character.valueOf((char) 66));
        System.out.println(Character.toChars(66));
    }

    private static void testCharacterData() {
        Character.toLowerCase(66);
    }

    public static void main(String[] args) {
//        testDigit();
        testCharacterData();
    }
}
