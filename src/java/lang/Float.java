/*
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.lang;

import sun.misc.FloatingDecimal;
import sun.misc.FloatConsts;
import sun.misc.DoubleConsts;

/**
 * The {@code Float} class wraps a value of primitive type
 * {@code float} in an object. An object of type
 * {@code Float} contains a single field whose type is
 * {@code float}.
 * Float类在一个对象中包含一个原始类型float的值。 类型为Float的对象
 * 包含一个单一字段，其类型为float 。
 *
 * <p>In addition, this class provides several methods for converting a
 * {@code float} to a {@code String} and a
 * {@code String} to a {@code float}, as well as other
 * constants and methods useful when dealing with a
 * {@code float}.
 * 此外，该类还提供了几种将float转换为String和String转换为float ，以及在处理float时有用的其他常数和方法。
 *
 * @author  Lee Boynton
 * @author  Arthur van Hoff
 * @author  Joseph D. Darcy
 * @since JDK1.0
 */
public final class Float extends Number implements Comparable<Float> {
    /**
     * A constant holding the positive infinity of type
     * {@code float}. It is equal to the value returned by
     * {@code Float.intBitsToFloat(0x7f800000)}.
     * 恒定持有float类型的正无穷大。 它等于Float.intBitsToFloat(0x7f800000)返回的值。
     */
    public static final float POSITIVE_INFINITY = 1.0f / 0.0f;

    /**
     * A constant holding the negative infinity of type
     * {@code float}. It is equal to the value returned by
     * {@code Float.intBitsToFloat(0xff800000)}.
     * 持有float类型的负无穷大的float 。 它等于Float.intBitsToFloat(0xff800000)返回的值。
     */
    public static final float NEGATIVE_INFINITY = -1.0f / 0.0f;

    /**
     * A constant holding a Not-a-Number (NaN) value of type
     * {@code float}.  It is equivalent to the value returned by
     * {@code Float.intBitsToFloat(0x7fc00000)}.
     * 一个常数，持有float类型的非数字（NaN）值。 它相当于返回的值Float.intBitsToFloat(0x7fc00000) 。
     */
    public static final float NaN = 0.0f / 0.0f;

    /**
     * A constant holding the largest positive finite value of type
     * {@code float}, (2-2<sup>-23</sup>)&middot;2<sup>127</sup>.
     * It is equal to the hexadecimal floating-point literal
     * {@code 0x1.fffffeP+127f} and also equal to
     * {@code Float.intBitsToFloat(0x7f7fffff)}.
     * 的常量保持型的最大正的有限值float ，（2-2 -23）A·2 127。 它等于十六进制浮点数文字0x1.fffffeP+127f ，也等于Float.intBitsToFloat(0x7f7fffff) 。
     */
    public static final float MAX_VALUE = 0x1.fffffeP+127f; // 3.4028235e+38f

    /**
     * A constant holding the smallest positive normal value of type
     * {@code float}, 2<sup>-126</sup>.  It is equal to the
     * hexadecimal floating-point literal {@code 0x1.0p-126f} and also
     * equal to {@code Float.intBitsToFloat(0x00800000)}.
     * 常数保持float类型的最小正正常值，2 -126 。 它等于十六进制浮点数文字0x1.0p-126f ，也等于Float.intBitsToFloat(0x00800000) 。
     *
     * @since 1.6
     */
    public static final float MIN_NORMAL = 0x1.0p-126f; // 1.17549435E-38f

    /**
     * A constant holding the smallest positive nonzero value of type
     * {@code float}, 2<sup>-149</sup>. It is equal to the
     * hexadecimal floating-point literal {@code 0x0.000002P-126f}
     * and also equal to {@code Float.intBitsToFloat(0x1)}.
     * 一个常数保持最小的正非零值类型float 2^-149 。 它等于十六进制浮点数文字0x0.000002P-126f ，也等于Float.intBitsToFloat(0x1) 。
     */
    public static final float MIN_VALUE = 0x0.000002P-126f; // 1.4e-45f

    /**
     * Maximum exponent a finite {@code float} variable may have.  It
     * is equal to the value returned by {@code
     * Math.getExponent(Float.MAX_VALUE)}.
     *
     * @since 1.6
     */
    public static final int MAX_EXPONENT = 127;

    /**
     * Minimum exponent a normalized {@code float} variable may have.
     * It is equal to the value returned by {@code
     * Math.getExponent(Float.MIN_NORMAL)}.
     *
     * @since 1.6
     */
    public static final int MIN_EXPONENT = -126;

    /**
     * The number of bits used to represent a {@code float} value.
     *
     * @since 1.5
     */
    public static final int SIZE = 32;

    /**
     * The number of bytes used to represent a {@code float} value.
     *
     * @since 1.8
     */
    public static final int BYTES = SIZE / Byte.SIZE;

    /**
     * The {@code Class} instance representing the primitive type
     * {@code float}.
     *
     * @since JDK1.1
     */
    @SuppressWarnings("unchecked")
    public static final Class<Float> TYPE = (Class<Float>) Class.getPrimitiveClass("float");

    /**
     * Returns a string representation of the {@code float}
     * argument. All characters mentioned below are ASCII characters.
     * 返回float参数的字符串float形式。 下面提到的所有字符都是ASCII字符。
     * <ul>
     * <li>If the argument is NaN, the result is the string
     * "{@code NaN}".
     * 如果参数是NaN，结果是字符串“ NaN ”。
     * <li>Otherwise, the result is a string that represents the sign and
     *     magnitude (absolute value) of the argument. If the sign is
     *     negative, the first character of the result is
     *     '{@code -}' ({@code '\u005Cu002D'}); if the sign is
     *     positive, no sign character appears in the result. As for
     *     the magnitude <i>m</i>:
     *     否则，结果是表示参数的符号和大小（绝对值）的字符串。 如果符号为负，则结果的第一个字符为“ - ”（ '\u002D' ）; 如果符号为正，则结果中不会出现任何符号字符。 至于幅度m ：
     * <ul>
     * <li>If <i>m</i> is infinity, it is represented by the characters
     *     {@code "Infinity"}; thus, positive infinity produces
     *     the result {@code "Infinity"} and negative infinity
     *     produces the result {@code "-Infinity"}.
     *     如果m是无穷大，它由字符"Infinity" ; 因此，正无穷大产生结果"Infinity"和负无穷大产生结果"-Infinity" 。
     * <li>If <i>m</i> is zero, it is represented by the characters
     *     {@code "0.0"}; thus, negative zero produces the result
     *     {@code "-0.0"} and positive zero produces the result
     *     {@code "0.0"}.
     *     如果m为零，则由"0.0" ; 因此，负零产生结果"-0.0"和正零产生结果"0.0" 。
     * <li> If <i>m</i> is greater than or equal to 10<sup>-3</sup> but
     *      less than 10<sup>7</sup>, then it is represented as the
     *      integer part of <i>m</i>, in decimal form with no leading
     *      zeroes, followed by '{@code .}'
     *      ({@code '\u005Cu002E'}), followed by one or more
     *      decimal digits representing the fractional part of
     *      <i>m</i>.
     *      如果m大于或等于10^-3但小于10^7 ，则表示为m的整数部分，以十进制形式表示，不带前导零，后跟“ . ”（ '\u002E' ），后跟一个或多个表示m的小数部分十进制数字。
     * <li> If <i>m</i> is less than 10<sup>-3</sup> or greater than or
     *      equal to 10<sup>7</sup>, then it is represented in
     *      so-called "computerized scientific notation." Let <i>n</i>
     *      be the unique integer such that 10<sup><i>n</i> </sup>&le;
     *      <i>m</i> {@literal <} 10<sup><i>n</i>+1</sup>; then let <i>a</i>
     *      be the mathematically exact quotient of <i>m</i> and
     *      10<sup><i>n</i></sup> so that 1 &le; <i>a</i> {@literal <} 10.
     *      The magnitude is then represented as the integer part of
     *      <i>a</i>, as a single decimal digit, followed by
     *      '{@code .}' ({@code '\u005Cu002E'}), followed by
     *      decimal digits representing the fractional part of
     *      <i>a</i>, followed by the letter '{@code E}'
     *      ({@code '\u005Cu0045'}), followed by a representation
     *      of <i>n</i> as a decimal integer, as produced by the
     *      method {@link java.lang.Integer#toString(int)}.
     *      如果m小于10 -3或大于或等于10 7 ，则表示为所谓的“计算机科学记数法”。
     *      设n是唯一的整数，使得10N的‰¤ 米 <10 N + 1; 然后让一个是m的精确算术商和10 牛顿 ，
     *      使1个â‰¤ 一个 <10.然后将大小被表示为a的整数部分，作为一个单一的十进制数字，后跟“ . ”（ '\u002E' ），
     *      再后面是表示一个小数部分十进制数字，后面跟有字母“ E ”（ '\u0045' ），接着为十进制整数n的表示，作为由该方法制备Integer.toString(int) 。
     *
     * </ul>
     * </ul>
     * How many digits must be printed for the fractional part of
     * <i>m</i> or <i>a</i>? There must be at least one digit
     * to represent the fractional part, and beyond that as many, but
     * only as many, more digits as are needed to uniquely distinguish
     * the argument value from adjacent values of type
     * {@code float}. That is, suppose that <i>x</i> is the
     * exact mathematical value represented by the decimal
     * representation produced by this method for a finite nonzero
     * argument <i>f</i>. Then <i>f</i> must be the {@code float}
     * value nearest to <i>x</i>; or, if two {@code float} values are
     * equally close to <i>x</i>, then <i>f</i> must be one of
     * them and the least significant bit of the significand of
     * <i>f</i> must be {@code 0}.
     * m或a的小数部分必须打印多少位数？ 必须有至少一个数字来表示小数部分，并且除了必须唯一区分参数值和类型float相邻值所需的数量之外，还需要多少数字。
     * 也就是说，假设x是由用于有限非零参数f的此方法生成的十进制表示形式表示的确切数学值。 那么f必须是最接近x的float值;
     * 或者，如果两个float值都同样接近为x，则f必须是它们中的一个和f的有效数的至少显著位必须为0 。
     *
     * <p>To create localized string representations of a floating-point
     * value, use subclasses of {@link java.text.NumberFormat}.
     * 要创建浮点值的本地化字符串表示，请使用NumberFormat的子类 。
     *
     * @param   f   the float to be converted.
     * @return a string representation of the argument.
     */
    public static String toString(float f) {
        return FloatingDecimal.toJavaFormatString(f);
    }

    /**
     * Returns a hexadecimal string representation of the
     * {@code float} argument. All characters mentioned below are
     * ASCII characters.
     * 返回float参数的十六进制字符串float形式。 下面提到的所有字符都是ASCII字符。
     *
     * <ul>
     * <li>If the argument is NaN, the result is the string
     *     "{@code NaN}".
     *     如果参数是NaN，结果是字符串“ NaN ”。
     * <li>Otherwise, the result is a string that represents the sign and
     * magnitude (absolute value) of the argument. If the sign is negative,
     * the first character of the result is '{@code -}'
     * ({@code '\u005Cu002D'}); if the sign is positive, no sign character
     * appears in the result. As for the magnitude <i>m</i>:
     * 否则，结果是表示参数的符号和大小（绝对值）的字符串。 如果符号为负，则结果的第一个字符为“ - ”（ '\u002D' ）; 如果符号为正，则结果中不会出现任何符号字符。 至于幅度m ：
     *
     * <ul>
     * <li>If <i>m</i> is infinity, it is represented by the string
     * {@code "Infinity"}; thus, positive infinity produces the
     * result {@code "Infinity"} and negative infinity produces
     * the result {@code "-Infinity"}.
     * 如果m是无穷大，它由字符串"Infinity" ; 因此，正无穷大产生结果"Infinity"和负无穷大产生结果"-Infinity" 。
     *
     * <li>If <i>m</i> is zero, it is represented by the string
     * {@code "0x0.0p0"}; thus, negative zero produces the result
     * {@code "-0x0.0p0"} and positive zero produces the result
     * {@code "0x0.0p0"}.
     * 如果m为零，则用字符串"0x0.0p0" ; 因此，负零产生结果"-0x0.0p0" ，正零产生结果"0x0.0p0" 。
     *
     * <li>If <i>m</i> is a {@code float} value with a
     * normalized representation, substrings are used to represent the
     * significand and exponent fields.  The significand is
     * represented by the characters {@code "0x1."}
     * followed by a lowercase hexadecimal representation of the rest
     * of the significand as a fraction.  Trailing zeros in the
     * hexadecimal representation are removed unless all the digits
     * are zero, in which case a single zero is used. Next, the
     * exponent is represented by {@code "p"} followed
     * by a decimal string of the unbiased exponent as if produced by
     * a call to {@link Integer#toString(int) Integer.toString} on the
     * exponent value.
     * 如果m是具有float一化表示的float值，则使用子字符串来表示有效位数和指数字段。
     * 有效位数由字符"0x1." "0x1."后面是有意义数据的其余部分的小写十六进制表示形式作为分数。
     * 删除十六进制表示中的尾随零，除非所有数字都为零，在这种情况下使用单个零。
     * 接下来，指数由"p"后面是无偏指数的十进制字符串，就像通过对指数值调用Integer.toString生成的那样 。
     *
     * <li>If <i>m</i> is a {@code float} value with a subnormal
     * representation, the significand is represented by the
     * characters {@code "0x0."} followed by a
     * hexadecimal representation of the rest of the significand as a
     * fraction.  Trailing zeros in the hexadecimal representation are
     * removed. Next, the exponent is represented by
     * {@code "p-126"}.  Note that there must be at
     * least one nonzero digit in a subnormal significand.
     * 如果m是具有float表示的float值，则有效位数由字符"0x0."后面是有效数的其余部分的十六进制表示作为分数。
     * 删除十六进制表示中的尾随零。 接下来，指数由"p-126" 。 请注意，在异常有效位数中必须至少有一个非零数字。
     *
     * </ul>
     *
     * </ul>
     *
     * <table border>
     * <caption>Examples</caption>
     * <tr><th>Floating-point Value</th><th>Hexadecimal String</th>
     * <tr><td>{@code 1.0}</td> <td>{@code 0x1.0p0}</td>
     * <tr><td>{@code -1.0}</td>        <td>{@code -0x1.0p0}</td>
     * <tr><td>{@code 2.0}</td> <td>{@code 0x1.0p1}</td>
     * <tr><td>{@code 3.0}</td> <td>{@code 0x1.8p1}</td>
     * <tr><td>{@code 0.5}</td> <td>{@code 0x1.0p-1}</td>
     * <tr><td>{@code 0.25}</td>        <td>{@code 0x1.0p-2}</td>
     * <tr><td>{@code Float.MAX_VALUE}</td>
     *     <td>{@code 0x1.fffffep127}</td>
     * <tr><td>{@code Minimum Normal Value}</td>
     *     <td>{@code 0x1.0p-126}</td>
     * <tr><td>{@code Maximum Subnormal Value}</td>
     *     <td>{@code 0x0.fffffep-126}</td>
     * <tr><td>{@code Float.MIN_VALUE}</td>
     *     <td>{@code 0x0.000002p-126}</td>
     * </table>
     * @param   f   the {@code float} to be converted.
     * @return a hex string representation of the argument.
     * @since 1.5
     * @author Joseph D. Darcy
     */
    public static String toHexString(float f) {
        if (Math.abs(f) < FloatConsts.MIN_NORMAL
            &&  f != 0.0f ) {// float subnormal
            // Adjust exponent to create subnormal double, then
            // replace subnormal double exponent with subnormal float
            // exponent
            String s = Double.toHexString(Math.scalb((double)f,
                                                     /* -1022+126 */
                                                     DoubleConsts.MIN_EXPONENT-
                                                     FloatConsts.MIN_EXPONENT));
            return s.replaceFirst("p-1022$", "p-126");
        }
        else // double string will be the same as float string
            return Double.toHexString(f);
    }

    /**
     * Returns a {@code Float} object holding the
     * {@code float} value represented by the argument string
     * {@code s}.
     * 返回一个Float对象，保存由参数字符串s的float值。
     *
     * <p>If {@code s} is {@code null}, then a
     * {@code NullPointerException} is thrown.
     * 如果s是null ，那么抛出一个NullPointerException 。
     *
     * <p>Leading and trailing whitespace characters in {@code s}
     * are ignored.  Whitespace is removed as if by the {@link
     * String#trim} method; that is, both ASCII space and control
     * characters are removed. The rest of {@code s} should
     * constitute a <i>FloatValue</i> as described by the lexical
     * syntax rules:
     * s中的前导和尾随空格s将被忽略。 空格被删除，好像通过String.trim()方法; 也就是说，ASCII空间和控制字符都被删除。 s的其余部分应构成FloatValue ，如词法语法规则所述：
     *
     * <blockquote>
     * <dl>
     * <dt><i>FloatValue:</i>
     * <dd><i>Sign<sub>opt</sub></i> {@code NaN}
     * <dd><i>Sign<sub>opt</sub></i> {@code Infinity}
     * <dd><i>Sign<sub>opt</sub> FloatingPointLiteral</i>
     * <dd><i>Sign<sub>opt</sub> HexFloatingPointLiteral</i>
     * <dd><i>SignedInteger</i>
     * </dl>
     *
     * <dl>
     * <dt><i>HexFloatingPointLiteral</i>:
     * <dd> <i>HexSignificand BinaryExponent FloatTypeSuffix<sub>opt</sub></i>
     * </dl>
     *
     * <dl>
     * <dt><i>HexSignificand:</i>
     * <dd><i>HexNumeral</i>
     * <dd><i>HexNumeral</i> {@code .}
     * <dd>{@code 0x} <i>HexDigits<sub>opt</sub>
     *     </i>{@code .}<i> HexDigits</i>
     * <dd>{@code 0X}<i> HexDigits<sub>opt</sub>
     *     </i>{@code .} <i>HexDigits</i>
     * </dl>
     *
     * <dl>
     * <dt><i>BinaryExponent:</i>
     * <dd><i>BinaryExponentIndicator SignedInteger</i>
     * </dl>
     *
     * <dl>
     * <dt><i>BinaryExponentIndicator:</i>
     * <dd>{@code p}
     * <dd>{@code P}
     * </dl>
     *
     * </blockquote>
     *
     * where <i>Sign</i>, <i>FloatingPointLiteral</i>,
     * <i>HexNumeral</i>, <i>HexDigits</i>, <i>SignedInteger</i> and
     * <i>FloatTypeSuffix</i> are as defined in the lexical structure
     * sections of
     * <cite>The Java&trade; Language Specification</cite>,
     * except that underscores are not accepted between digits.
     * If {@code s} does not have the form of
     * a <i>FloatValue</i>, then a {@code NumberFormatException}
     * is thrown. Otherwise, {@code s} is regarded as
     * representing an exact decimal value in the usual
     * "computerized scientific notation" or as an exact
     * hexadecimal value; this exact numerical value is then
     * conceptually converted to an "infinitely precise"
     * binary value that is then rounded to type {@code float}
     * by the usual round-to-nearest rule of IEEE 754 floating-point
     * arithmetic, which includes preserving the sign of a zero
     * value.
     * 其中Sign ， FloatingPointLiteral ， HexNumeral ， HexDigits ， SignedInteger和FloatTypeSuffix在The Java™ Language Specification的词法结构部分中定义 ，
     * 不同之处在于数字之间不接受下划线。 如果s不具有的floatValue的形式，那么NumberFormatException异常。
     * 否则， s被认为是在通常的“计算机科学符号”中表示精确的十进制值，或者作为精确的十六进制值;
     * 这个精确的数值然后在概念上被转换成“无限精确”二进制值，然后通过IEEE 754浮点float的通常的圆到最近的规则进行四舍五入到类型float，
     * 其包括保留零值的符号。
     *
     * Note that the round-to-nearest rule also implies overflow and
     * underflow behaviour; if the exact value of {@code s} is large
     * enough in magnitude (greater than or equal to ({@link
     * #MAX_VALUE} + {@link Math#ulp(float) ulp(MAX_VALUE)}/2),
     * rounding to {@code float} will result in an infinity and if the
     * exact value of {@code s} is small enough in magnitude (less
     * than or equal to {@link #MIN_VALUE}/2), rounding to float will
     * result in a zero.
     *  注意，round-to-nearest规则也意味着溢出和下溢行为; 如果s的确切值足够大（大于或等于（ MAX_VALUE + ulp(MAX_VALUE)/2 ）），
     *  则舍入到float将导致无穷大，如果s的确切值足够小（小于或等于到MIN_VALUE/2 ），四舍五入将导致零。
     *
     * Finally, after rounding a {@code Float} object representing
     * this {@code float} value is returned.
     * 最后，在舍入后，返回一个Float对象，表示此float 。
     *
     * <p>To interpret localized string representations of a
     * floating-point value, use subclasses of {@link
     * java.text.NumberFormat}.
     * 要解释浮点值的本地化字符串表示，请使用NumberFormat的子类 。
     *
     * <p>Note that trailing format specifiers, specifiers that
     * determine the type of a floating-point literal
     * ({@code 1.0f} is a {@code float} value;
     * {@code 1.0d} is a {@code double} value), do
     * <em>not</em> influence the results of this method.  In other
     * words, the numerical value of the input string is converted
     * directly to the target floating-point type.  In general, the
     * two-step sequence of conversions, string to {@code double}
     * followed by {@code double} to {@code float}, is
     * <em>not</em> equivalent to converting a string directly to
     * {@code float}.  For example, if first converted to an
     * intermediate {@code double} and then to
     * {@code float}, the string<br>
     * {@code "1.00000017881393421514957253748434595763683319091796875001d"}<br>
     * results in the {@code float} value
     * {@code 1.0000002f}; if the string is converted directly to
     * {@code float}, <code>1.000000<b>1</b>f</code> results.
     * 注意，尾部格式说明，即确定一个浮点文字的类型说明符（ 1.0f是float值; 1.0d是double值）， 不影响该方法的结果。 换句话说，输入字符串的数值直接转换为目标浮点类型。
     * 一般来说，转换的两步序列，字符串为double后跟double至float ， 并不等同于将字符串直接转换为float 。 例如，如果首先转换为中间double ，然后转换为float ，则字符串
     * "1.00000017881393421514957253748434595763683319091796875001d"
     * 结果在float价值1.0000002f ; 如果字符串直接转换为float ， 1.0000001f结果为float 。
     *
     * <p>To avoid calling this method on an invalid string and having
     * a {@code NumberFormatException} be thrown, the documentation
     * for {@link Double#valueOf Double.valueOf} lists a regular
     * expression which can be used to screen the input.
     *
     * @param   s   the string to be parsed.
     * @return  a {@code Float} object holding the value
     *          represented by the {@code String} argument.
     * @throws  NumberFormatException  if the string does not contain a
     *          parsable number.
     */
    public static Float valueOf(String s) throws NumberFormatException {
        return new Float(parseFloat(s));
    }

    /**
     * Returns a {@code Float} instance representing the specified
     * {@code float} value.
     * If a new {@code Float} instance is not required, this method
     * should generally be used in preference to the constructor
     * {@link #Float(float)}, as this method is likely to yield
     * significantly better space and time performance by caching
     * frequently requested values.
     * 返回一个Float指定的float值的Float实例。 如果不需要新的Float实例，
     * 则该方法通常优先于构造函数Float(float)使用 ，因为该方法可能通过缓存经常请求的值而产生明显更好的空间和时间性能。
     *
     * @param  f a float value.
     * @return a {@code Float} instance representing {@code f}.
     * @since  1.5
     */
    public static Float valueOf(float f) {
        return new Float(f);
    }

    /**
     * Returns a new {@code float} initialized to the value
     * represented by the specified {@code String}, as performed
     * by the {@code valueOf} method of class {@code Float}.
     * 返回一个新 float初始化为指定的代表的值 String ，如通过执行 valueOf类的方法 Float 。
     *
     * @param  s the string to be parsed.
     * @return the {@code float} value represented by the string
     *         argument.
     * @throws NullPointerException  if the string is null
     * @throws NumberFormatException if the string does not contain a
     *               parsable {@code float}.
     * @see    java.lang.Float#valueOf(String)
     * @since 1.2
     */
    public static float parseFloat(String s) throws NumberFormatException {
        return FloatingDecimal.parseFloat(s);
    }

    /**
     * Returns {@code true} if the specified number is a
     * Not-a-Number (NaN) value, {@code false} otherwise.
     * 返回 true如果指定的号码是一个不一个数字（NaN）值， false否则。
     *
     * @param   v   the value to be tested.
     * @return  {@code true} if the argument is NaN;
     *          {@code false} otherwise.
     */
    public static boolean isNaN(float v) {
        return (v != v);
    }

    /**
     * Returns {@code true} if the specified number is infinitely
     * large in magnitude, {@code false} otherwise.
     * 返回 true如果指定的数字大小无限大， false false。
     *
     * @param   v   the value to be tested.
     * @return  {@code true} if the argument is positive infinity or
     *          negative infinity; {@code false} otherwise.
     */
    public static boolean isInfinite(float v) {
        return (v == POSITIVE_INFINITY) || (v == NEGATIVE_INFINITY);
    }


    /**
     * Returns {@code true} if the argument is a finite floating-point
     * value; returns {@code false} otherwise (for NaN and infinity
     * arguments).
     * 如果参数是有限浮点值，则返回true ; 返回false （对于NaN和无穷大参数）。
     *
     * @param f the {@code float} value to be tested
     * @return {@code true} if the argument is a finite
     * floating-point value, {@code false} otherwise.
     * @since 1.8
     */
     public static boolean isFinite(float f) {
        return Math.abs(f) <= FloatConsts.MAX_VALUE;
    }

    /**
     * The value of the Float.
     *
     * @serial
     */
    private final float value;

    /**
     * Constructs a newly allocated {@code Float} object that
     * represents the primitive {@code float} argument.
     * 构造一个新分配的 Float对象，该对象表示基元 float参数。
     *
     * @param   value   the value to be represented by the {@code Float}.
     */
    public Float(float value) {
        this.value = value;
    }

    /**
     * Constructs a newly allocated {@code Float} object that
     * represents the argument converted to type {@code float}.
     * 构造一个新分配 Float对象，它表示转换为类型参数 float 。
     *
     * @param   value   the value to be represented by the {@code Float}.
     */
    public Float(double value) {
        this.value = (float)value;
    }

    /**
     * Constructs a newly allocated {@code Float} object that
     * represents the floating-point value of type {@code float}
     * represented by the string. The string is converted to a
     * {@code float} value as if by the {@code valueOf} method.
     * 构造一个新分配的Float对象，该对象表示由字符串表示的类型float的浮点值。
     * 该字符串被转换为一个float值如果由valueOf方法。
     *
     * @param      s   a string to be converted to a {@code Float}.
     * @throws  NumberFormatException  if the string does not contain a
     *               parsable number.
     * @see        java.lang.Float#valueOf(java.lang.String)
     */
    public Float(String s) throws NumberFormatException {
        value = parseFloat(s);
    }

    /**
     * Returns {@code true} if this {@code Float} value is a
     * Not-a-Number (NaN), {@code false} otherwise.
     * 如果这个 Float值 Float数字（NaN），则返回 true ， false false。
     *
     * @return  {@code true} if the value represented by this object is
     *          NaN; {@code false} otherwise.
     */
    public boolean isNaN() {
        return isNaN(value);
    }

    /**
     * Returns {@code true} if this {@code Float} value is
     * infinitely large in magnitude, {@code false} otherwise.
     * 返回 true如果这个 Float值是无限大的， false false。
     *
     * @return  {@code true} if the value represented by this object is
     *          positive infinity or negative infinity;
     *          {@code false} otherwise.
     */
    public boolean isInfinite() {
        return isInfinite(value);
    }

    /**
     * Returns a string representation of this {@code Float} object.
     * The primitive {@code float} value represented by this object
     * is converted to a {@code String} exactly as if by the method
     * {@code toString} of one argument.
     * 返回此Float对象的字符串表示形式。 原始float该对象表示值被转换为String完全一样，如果通过该方法toString一个参数的。
     *
     * @return  a {@code String} representation of this object.
     * @see java.lang.Float#toString(float)
     */
    public String toString() {
        return Float.toString(value);
    }

    /**
     * Returns the value of this {@code Float} as a {@code byte} after
     * a narrowing primitive conversion.
     *
     * @return  the {@code float} value represented by this object
     *          converted to type {@code byte}
     * @jls 5.1.3 Narrowing Primitive Conversions
     */
    public byte byteValue() {
        return (byte)value;
    }

    /**
     * Returns the value of this {@code Float} as a {@code short}
     * after a narrowing primitive conversion.
     *
     * @return  the {@code float} value represented by this object
     *          converted to type {@code short}
     * @jls 5.1.3 Narrowing Primitive Conversions
     * @since JDK1.1
     */
    public short shortValue() {
        return (short)value;
    }

    /**
     * Returns the value of this {@code Float} as an {@code int} after
     * a narrowing primitive conversion.
     *
     * @return  the {@code float} value represented by this object
     *          converted to type {@code int}
     * @jls 5.1.3 Narrowing Primitive Conversions
     */
    public int intValue() {
        return (int)value;
    }

    /**
     * Returns value of this {@code Float} as a {@code long} after a
     * narrowing primitive conversion.
     *
     * @return  the {@code float} value represented by this object
     *          converted to type {@code long}
     * @jls 5.1.3 Narrowing Primitive Conversions
     */
    public long longValue() {
        return (long)value;
    }

    /**
     * Returns the {@code float} value of this {@code Float} object.
     *
     * @return the {@code float} value represented by this object
     */
    public float floatValue() {
        return value;
    }

    /**
     * Returns the value of this {@code Float} as a {@code double}
     * after a widening primitive conversion.
     *
     * @return the {@code float} value represented by this
     *         object converted to type {@code double}
     * @jls 5.1.2 Widening Primitive Conversions
     */
    public double doubleValue() {
        return (double)value;
    }

    /**
     * Returns a hash code for this {@code Float} object. The
     * result is the integer bit representation, exactly as produced
     * by the method {@link #floatToIntBits(float)}, of the primitive
     * {@code float} value represented by this {@code Float}
     * object.
     * 返回此Float对象的哈希码。 结果是由该Float对象表示的基float值的整数位表示，正如由方法floatToIntBits(float)所产生的Float 。
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Float.hashCode(value);
    }

    /**
     * Returns a hash code for a {@code float} value; compatible with
     * {@code Float.hashCode()}.
     * 返回一个float值的哈希码; 兼容Float.hashCode() 。
     *
     * @param value the value to hash
     * @return a hash code value for a {@code float} value.
     * @since 1.8
     */
    public static int hashCode(float value) {
        return floatToIntBits(value);
    }

    /**

     * Compares this object against the specified object.  The result
     * is {@code true} if and only if the argument is not
     * {@code null} and is a {@code Float} object that
     * represents a {@code float} with the same value as the
     * {@code float} represented by this object. For this
     * purpose, two {@code float} values are considered to be the
     * same if and only if the method {@link #floatToIntBits(float)}
     * returns the identical {@code int} value when applied to
     * each.
     *
     * <p>Note that in most cases, for two instances of class
     * {@code Float}, {@code f1} and {@code f2}, the value
     * of {@code f1.equals(f2)} is {@code true} if and only if
     *
     * <blockquote><pre>
     *   f1.floatValue() == f2.floatValue()
     * </pre></blockquote>
     *
     * <p>also has the value {@code true}. However, there are two exceptions:
     * <ul>
     * <li>If {@code f1} and {@code f2} both represent
     *     {@code Float.NaN}, then the {@code equals} method returns
     *     {@code true}, even though {@code Float.NaN==Float.NaN}
     *     has the value {@code false}.
     * <li>If {@code f1} represents {@code +0.0f} while
     *     {@code f2} represents {@code -0.0f}, or vice
     *     versa, the {@code equal} test has the value
     *     {@code false}, even though {@code 0.0f==-0.0f}
     *     has the value {@code true}.
     * </ul>
     *
     * This definition allows hash tables to operate properly.
     *
     * @param obj the object to be compared
     * @return  {@code true} if the objects are the same;
     *          {@code false} otherwise.
     * @see java.lang.Float#floatToIntBits(float)
     */
    public boolean equals(Object obj) {
        return (obj instanceof Float)
               && (floatToIntBits(((Float)obj).value) == floatToIntBits(value));
    }

    /**
     * Returns a representation of the specified floating-point value
     * according to the IEEE 754 floating-point "single format" bit
     * layout.
     * 根据IEEE 754浮点“单格式”位布局返回指定浮点值的表示。
     *
     * <p>Bit 31 (the bit that is selected by the mask
     * {@code 0x80000000}) represents the sign of the floating-point
     * number.
     * 位31（由掩码0x80000000选择的位）表示浮点数的符号。
     * Bits 30-23 (the bits that are selected by the mask
     * {@code 0x7f800000}) represent the exponent.
     * 位30-23（由掩码0x7f800000选择的位）表示指数。
     * Bits 22-0 (the bits that are selected by the mask
     * {@code 0x007fffff}) represent the significand (sometimes called
     * the mantissa) of the floating-point number.
     * 位22-0（由掩码0x007fffff选择的位）表示浮点数的有效数（有时称为尾数）。
     *
     * <p>If the argument is positive infinity, the result is
     * {@code 0x7f800000}.
     * 如果参数为无穷大，结果为0x7f800000 。
     *
     * <p>If the argument is negative infinity, the result is
     * {@code 0xff800000}.
     * 如果参数为负无穷大，则结果为0xff800000 。
     *
     * <p>If the argument is NaN, the result is {@code 0x7fc00000}.
     * 如果参数是NaN，结果是0x7fc00000 。
     *
     * <p>In all cases, the result is an integer that, when given to the
     * {@link #intBitsToFloat(int)} method, will produce a floating-point
     * value the same as the argument to {@code floatToIntBits}
     * (except all NaN values are collapsed to a single
     * "canonical" NaN value).
     * 在所有情况下，结果是一个整数，当给予intBitsToFloat(int)方法时，将产生与floatToIntBits的参数相同的浮点值（除了所有NaN值都折叠为单个“规范”NaN值）。
     *
     * @param   value   a floating-point number.
     * @return the bits that represent the floating-point number.
     */
    public static int floatToIntBits(float value) {
        int result = floatToRawIntBits(value);
        // Check for NaN based on values of bit fields, maximum
        // exponent and nonzero significand.
        if ( ((result & FloatConsts.EXP_BIT_MASK) ==
              FloatConsts.EXP_BIT_MASK) &&
             (result & FloatConsts.SIGNIF_BIT_MASK) != 0)
            result = 0x7fc00000;
        return result;
    }

    /**
     * Returns a representation of the specified floating-point value
     * according to the IEEE 754 floating-point "single format" bit
     * layout, preserving Not-a-Number (NaN) values.
     * 根据IEEE 754浮点“单格式”位布局返回指定浮点值的表示，保留非数字（NaN）值。
     *
     * <p>Bit 31 (the bit that is selected by the mask
     * {@code 0x80000000}) represents the sign of the floating-point
     * number.
     * 位31（由掩码0x80000000选择的位）表示浮点数的符号。
     * Bits 30-23 (the bits that are selected by the mask
     * {@code 0x7f800000}) represent the exponent.
     * 位30-23（由掩码0x7f800000选择的位）表示指数。
     * Bits 22-0 (the bits that are selected by the mask
     * {@code 0x007fffff}) represent the significand (sometimes called
     * the mantissa) of the floating-point number.
     * 位22-0（由掩码0x007fffff选择的位）表示浮点数的有效数（有时称为尾数）。
     *
     * <p>If the argument is positive infinity, the result is
     * {@code 0x7f800000}.
     * 如果参数为无穷大，则结果为0x7f800000 。
     *
     * <p>If the argument is negative infinity, the result is
     * {@code 0xff800000}.
     * 如果参数为负无穷大，则结果为0xff800000 。
     *
     * <p>If the argument is NaN, the result is the integer representing
     * the actual NaN value.  Unlike the {@code floatToIntBits}
     * method, {@code floatToRawIntBits} does not collapse all the
     * bit patterns encoding a NaN to a single "canonical"
     * NaN value.
     * 如果参数是NaN，则结果是表示实际NaN值的整数。 与floatToIntBits方法不同， floatToRawIntBits不会将编码NaN的所有位模式折叠到单个“规范”NaN值。
     *
     * <p>In all cases, the result is an integer that, when given to the
     * {@link #intBitsToFloat(int)} method, will produce a
     * floating-point value the same as the argument to
     * {@code floatToRawIntBits}.
     * 在所有情况下，结果是一个整数，当给予intBitsToFloat(int)方法时，将产生与floatToRawIntBits的参数相同的浮点值。
     *
     * @param   value   a floating-point number.
     * @return the bits that represent the floating-point number.
     * @since 1.3
     */
    public static native int floatToRawIntBits(float value);

    /**
     * Returns the {@code float} value corresponding to a given
     * bit representation.
     * 返回与给float表示对应的float值。
     * The argument is considered to be a representation of a
     * floating-point value according to the IEEE 754 floating-point
     * "single format" bit layout.
     * 根据IEEE 754浮点“单格式”位布局，该参数被认为是浮点值的表示。
     *
     * <p>If the argument is {@code 0x7f800000}, the result is positive
     * infinity.
     * 如果参数为0x7f800000 ，则结果为正无穷大。
     *
     * <p>If the argument is {@code 0xff800000}, the result is negative
     * infinity.
     * 如果参数为0xff800000 ，结果为负无穷大。
     *
     * <p>If the argument is any value in the range
     * {@code 0x7f800001} through {@code 0x7fffffff} or in
     * the range {@code 0xff800001} through
     * {@code 0xffffffff}, the result is a NaN.  No IEEE 754
     * floating-point operation provided by Java can distinguish
     * between two NaN values of the same type with different bit
     * patterns.  Distinct values of NaN are only distinguishable by
     * use of the {@code Float.floatToRawIntBits} method.
     * 如果参数在上述范围内的任何值0x7f800001通过0x7fffffff ，或在范围0xff800001通过0xffffffff ，
     * 其结果是NaN。 Java提供的IEEE 754浮点运算不能用不同的位模式区分同一类型的两个NaN值。
     * NaN的不同值只能通过使用Float.floatToRawIntBits方法进行Float.floatToRawIntBits 。
     *
     * <p>In all other cases, let <i>s</i>, <i>e</i>, and <i>m</i> be three
     * values that can be computed from the argument:
     * 在所有其他情况下，令s ， e和m是可以从参数计算的三个值：
     *
     * <blockquote><pre>{@code
     * int s = ((bits >> 31) == 0) ? 1 : -1;
     * int e = ((bits >> 23) & 0xff);
     * int m = (e == 0) ?
     *                 (bits & 0x7fffff) << 1 :
     *                 (bits & 0x7fffff) | 0x800000;
     * }</pre></blockquote>
     *
     * Then the floating-point result equals the value of the mathematical
     * expression <i>s</i>&middot;<i>m</i>&middot;2<sup><i>e</i>-150</sup>.
     * 那么浮点结果等于数学表达式s · m ·2 ^ e -150的值 。
     *
     * <p>Note that this method may not be able to return a
     * {@code float} NaN with exactly same bit pattern as the
     * {@code int} argument.  IEEE 754 distinguishes between two
     * kinds of NaNs, quiet NaNs and <i>signaling NaNs</i>.  The
     * differences between the two kinds of NaN are generally not
     * visible in Java.  Arithmetic operations on signaling NaNs turn
     * them into quiet NaNs with a different, but often similar, bit
     * pattern.  However, on some processors merely copying a
     * signaling NaN also performs that conversion.  In particular,
     * copying a signaling NaN to return it to the calling method may
     * perform this conversion.  So {@code intBitsToFloat} may
     * not be able to return a {@code float} with a signaling NaN
     * bit pattern.  Consequently, for some {@code int} values,
     * {@code floatToRawIntBits(intBitsToFloat(start))} may
     * <i>not</i> equal {@code start}.  Moreover, which
     * particular bit patterns represent signaling NaNs is platform
     * dependent; although all NaN bit patterns, quiet or signaling,
     * must be in the NaN range identified above.
     * 请注意，此方法可能无法返回与int参数完全相同的位模式的float NaN。 IEEE 754区分了两种NaN，
     * 安静的NaN和信号NaN 。 两种NaN之间的区别通常在Java中不可见。 信号NaN的算术运算将它们变成具有不同但通常相似的位模式的安静的NaN。
     * 然而，在某些处理器上，仅仅复制信号NaN也执行该转换。 特别地，复制信令NaN以将其返回到调用方法可以执行该转换。
     * 所以intBitsToFloat可能无法返回一个float具有signaling NaN的位模式。 因此，对于一些int值， floatToRawIntBits(intBitsToFloat(start))可能不等于start 。
     * 此外，哪些特定位模式表示信令NaN是平台依赖的; 尽管所有NaN位模式，安静或信令都必须位于上面确定的NaN范围内。
     *
     * @param   bits   an integer.
     * @return  the {@code float} floating-point value with the same bit
     *          pattern.
     */
    public static native float intBitsToFloat(int bits);

    /**
     * Compares two {@code Float} objects numerically.  There are
     * two ways in which comparisons performed by this method differ
     * from those performed by the Java language numerical comparison
     * operators ({@code <, <=, ==, >=, >}) when
     * applied to primitive {@code float} values:
     *
     * <ul><li>
     *          {@code Float.NaN} is considered by this method to
     *          be equal to itself and greater than all other
     *          {@code float} values
     *          (including {@code Float.POSITIVE_INFINITY}).
     * <li>
     *          {@code 0.0f} is considered by this method to be greater
     *          than {@code -0.0f}.
     * </ul>
     *
     * This ensures that the <i>natural ordering</i> of {@code Float}
     * objects imposed by this method is <i>consistent with equals</i>.
     *
     * @param   anotherFloat   the {@code Float} to be compared.
     * @return  the value {@code 0} if {@code anotherFloat} is
     *          numerically equal to this {@code Float}; a value
     *          less than {@code 0} if this {@code Float}
     *          is numerically less than {@code anotherFloat};
     *          and a value greater than {@code 0} if this
     *          {@code Float} is numerically greater than
     *          {@code anotherFloat}.
     *
     * @since   1.2
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(Float anotherFloat) {
        return Float.compare(value, anotherFloat.value);
    }

    /**
     * Compares the two specified {@code float} values. The sign
     * of the integer value returned is the same as that of the
     * integer that would be returned by the call:
     * <pre>
     *    new Float(f1).compareTo(new Float(f2))
     * </pre>
     *
     * @param   f1        the first {@code float} to compare.
     * @param   f2        the second {@code float} to compare.
     * @return  the value {@code 0} if {@code f1} is
     *          numerically equal to {@code f2}; a value less than
     *          {@code 0} if {@code f1} is numerically less than
     *          {@code f2}; and a value greater than {@code 0}
     *          if {@code f1} is numerically greater than
     *          {@code f2}.
     * @since 1.4
     */
    public static int compare(float f1, float f2) {
        if (f1 < f2)
            return -1;           // Neither val is NaN, thisVal is smaller
        if (f1 > f2)
            return 1;            // Neither val is NaN, thisVal is larger

        // Cannot use floatToRawIntBits because of possibility of NaNs.
        int thisBits    = Float.floatToIntBits(f1);
        int anotherBits = Float.floatToIntBits(f2);

        return (thisBits == anotherBits ?  0 : // Values are equal
                (thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
                 1));                          // (0.0, -0.0) or (NaN, !NaN)
    }

    /**
     * Adds two {@code float} values together as per the + operator.
     *
     * @param a the first operand
     * @param b the second operand
     * @return the sum of {@code a} and {@code b}
     * @jls 4.2.4 Floating-Point Operations
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static float sum(float a, float b) {
        return a + b;
    }

    /**
     * Returns the greater of two {@code float} values
     * as if by calling {@link Math#max(float, float) Math.max}.
     *
     * @param a the first operand
     * @param b the second operand
     * @return the greater of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static float max(float a, float b) {
        return Math.max(a, b);
    }

    /**
     * Returns the smaller of two {@code float} values
     * as if by calling {@link Math#min(float, float) Math.min}.
     *
     * @param a the first operand
     * @param b the second operand
     * @return the smaller of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static float min(float a, float b) {
        return Math.min(a, b);
    }

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -2671257302660747028L;
}
