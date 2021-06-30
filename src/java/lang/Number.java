/*
 * Copyright (c) 1994, 2011, Oracle and/or its affiliates. All rights reserved.
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

/**
 * The abstract class {@code Number} is the superclass of platform
 * classes representing numeric values that are convertible to the
 * primitive types {@code byte}, {@code double}, {@code float}, {@code
 * int}, {@code long}, and {@code short}.
 * 抽象类Number是表示数字值可转换为基本数据类型平台类的超类byte ， double ，
 * float ， int ， long和short
 *
 * The specific semantics of the conversion from the numeric value of
 * a particular {@code Number} implementation to a given primitive
 * type is defined by the {@code Number} implementation in question.
 * 从一个特定的数字值转换的特定语义Number实现给定的原语类型由定义Number所讨论的实现。
 *
 * For platform classes, the conversion is often analogous to a
 * narrowing primitive conversion or a widening primitive conversion
 * as defining in <cite>The Java&trade; Language Specification</cite>
 * for converting between primitive types.  Therefore, conversions may
 * lose information about the overall magnitude of a numeric value, may
 * lose precision, and may even return a result of a different sign
 * than the input.
 * 对于平台类，转换往往是类似于一个基本收缩转换或加宽原语转换为
 * The Java™ Language Specification限定用于原始类型之间的转换。
 * 因此，转换可能会丢失有关数值的总体大小的信息，可能会失去精度，甚至可能会返回与输入不同的符号结果。
 *
 * See the documentation of a given {@code Number} implementation for
 * conversion details.
 * 有关转换的详细信息，请参阅给定的Number实现的文档。
 *
 * @author      Lee Boynton
 * @author      Arthur van Hoff
 * @jls 5.1.2 Widening Primitive Conversions
 * @jls 5.1.3 Narrowing Primitive Conversions
 * @since   JDK1.0
 */
public abstract class Number implements java.io.Serializable {
    /**
     * Returns the value of the specified number as an {@code int},
     * which may involve rounding or truncation.
     * 返回指定数字的值为 int ，可能涉及四舍五入或截断。
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code int}.
     */
    public abstract int intValue();

    /**
     * Returns the value of the specified number as a {@code long},
     * which may involve rounding or truncation.
     * 返回指定数字的值为 long ，可能涉及四舍五入或截断。
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code long}.
     */
    public abstract long longValue();

    /**
     * Returns the value of the specified number as a {@code float},
     * which may involve rounding.
     * 返回指定号码作为值 float ，这可能涉及舍入。
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code float}.
     */
    public abstract float floatValue();

    /**
     * Returns the value of the specified number as a {@code double},
     * which may involve rounding.
     * 返回指定数字的值为 double ，可能涉及四舍五入。
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code double}.
     */
    public abstract double doubleValue();

    /**
     * Returns the value of the specified number as a {@code byte},
     * which may involve rounding or truncation.
     * 返回指定数字的值为byte ，可能涉及四舍五入或截断。
     *
     * <p>This implementation returns the result of {@link #intValue} cast
     * to a {@code byte}.
     * 此实现返回intValue()转换为byte的结果。
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code byte}.
     * @since   JDK1.1
     */
    public byte byteValue() {
        return (byte)intValue();
    }

    /**
     * Returns the value of the specified number as a {@code short},
     * which may involve rounding or truncation.
     * 返回指定数字的值作为short ，这可能涉及四舍五入或截断。
     *
     * <p>This implementation returns the result of {@link #intValue} cast
     * to a {@code short}.
     * 此实现返回intValue()转换为short的结果。
     *
     * @return  the numeric value represented by this object after conversion
     *          to type {@code short}.
     * @since   JDK1.1
     */
    public short shortValue() {
        return (short)intValue();
    }

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -8742448824652078965L;
}
