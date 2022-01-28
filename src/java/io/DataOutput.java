/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

/**
 * The <code>DataOutput</code> interface provides
 * for converting data from any of the Java
 * primitive types to a series of bytes and
 * writing these bytes to a binary stream.
 * There is  also a facility for converting
 * a <code>String</code> into
 * <a href="DataInput.html#modified-utf-8">modified UTF-8</a>
 * format and writing the resulting series
 * of bytes.
 * DataOutput接口提供将数据从任何Java基本类型转换为一系列字节，并将这些字节写入二进制流。
 * 还有一种将String转换为modified UTF-8格式并编写结果字节系列的功能。
 * <p>
 * For all the methods in this interface that
 * write bytes, it is generally true that if
 * a byte cannot be written for any reason,
 * an <code>IOException</code> is thrown.
 * 对于写入字节的这个接口中的所有方法，一般来说，如果一个字节不能由于任何原因写入，则抛出一个IOException 。
 *
 * @author Frank Yellin
 * @see java.io.DataInput
 * @see java.io.DataOutputStream
 * @since JDK1.0
 */
public
interface DataOutput {
    /**
     * Writes to the output stream the eight
     * low-order bits of the argument <code>b</code>.
     * The 24 high-order  bits of <code>b</code>
     * are ignored.
     * 向输出流写入参数b的八个低位。 b的24位b被忽略。
     *
     * @param b the byte to be written.
     * @throws IOException if an I/O error occurs.
     */
    void write(int b) throws IOException;

    /**
     * Writes to the output stream all the bytes in array <code>b</code>.
     * If <code>b</code> is <code>null</code>,
     * a <code>NullPointerException</code> is thrown.
     * If <code>b.length</code> is zero, then
     * no bytes are written. Otherwise, the byte
     * <code>b[0]</code> is written first, then
     * <code>b[1]</code>, and so on; the last byte
     * written is <code>b[b.length-1]</code>.
     * 将输出流写入数组b中的所有字节。 如果b是null ，
     * 则抛出一个NullPointerException 。
     * 如果b.length为零，则不会写入任何字节。
     * 否则，首先写入字节b[0] ，然后写入b[1] ，等等;
     * 写入的最后一个字节是b[b.length-1] 。
     *
     * @param b the data.
     * @throws IOException if an I/O error occurs.
     */
    void write(byte b[]) throws IOException;

    /**
     * Writes <code>len</code> bytes from array
     * <code>b</code>, in order,  to
     * the output stream.  If <code>b</code>
     * is <code>null</code>, a <code>NullPointerException</code>
     * is thrown.  If <code>off</code> is negative,
     * or <code>len</code> is negative, or <code>off+len</code>
     * is greater than the length of the array
     * <code>b</code>, then an <code>IndexOutOfBoundsException</code>
     * is thrown.  If <code>len</code> is zero,
     * then no bytes are written. Otherwise, the
     * byte <code>b[off]</code> is written first,
     * then <code>b[off+1]</code>, and so on; the
     * last byte written is <code>b[off+len-1]</code>.
     * 将len字节从阵列b写入，输出流。 如果b是null ，则抛出一个NullPointerException 。
     * 如果off为负数，或len为负数，或off+len大于数组b的长度，则抛出IndexOutOfBoundsException 。
     * 如果len为零，则不会写入任何字节。 否则，先写字节b[off] ，然后b[off+1] ，等等;
     * 写入的最后一个字节是b[off+len-1] 。
     *
     * @param b   the data.
     * @param off the start offset in the data.
     * @param len the number of bytes to write.
     * @throws IOException if an I/O error occurs.
     */
    void write(byte b[], int off, int len) throws IOException;

    /**
     * Writes a <code>boolean</code> value to this output stream.
     * If the argument <code>v</code>
     * is <code>true</code>, the value <code>(byte)1</code>
     * is written; if <code>v</code> is <code>false</code>,
     * the  value <code>(byte)0</code> is written.
     * The byte written by this method may
     * be read by the <code>readBoolean</code>
     * method of interface <code>DataInput</code>,
     * which will then return a <code>boolean</code>
     * equal to <code>v</code>.
     * 将boolean值写入此输出流。 如果参数v为true ，则写入值(byte)1 ;
     * 如果v是false ，则写入值(byte)0 。
     * 由该方法写入的字节可以由readBoolean的readBoolean DataInput ，然后返回boolean等于v 。
     *
     * @param v the boolean to be written.
     * @throws IOException if an I/O error occurs.
     */
    void writeBoolean(boolean v) throws IOException;

    /**
     * Writes to the output stream the eight low-
     * order bits of the argument <code>v</code>.
     * The 24 high-order bits of <code>v</code>
     * are ignored. (This means  that <code>writeByte</code>
     * does exactly the same thing as <code>write</code>
     * for an integer argument.) The byte written
     * by this method may be read by the <code>readByte</code>
     * method of interface <code>DataInput</code>,
     * which will then return a <code>byte</code>
     * equal to <code>(byte)v</code>.
     * 向输出流写入参数v的八个低位位。 v的24位v被忽略。
     * （这意味着writeByte不完全一样的东西作为write为整数参数。）
     * 此方法写入的字节可以由读取readByte接口的方法DataInput ，然后将返回一个byte等于(byte)v 。
     *
     * @param v the byte value to be written.
     * @throws IOException if an I/O error occurs.
     */
    void writeByte(int v) throws IOException;

    /**
     * Writes two bytes to the output
     * stream to represent the value of the argument.
     * The byte values to be written, in the  order
     * shown, are:
     * <pre>{@code
     * (byte)(0xff & (v >> 8))
     * (byte)(0xff & v)
     * }</pre> <p>
     * The bytes written by this method may be
     * read by the <code>readShort</code> method
     * of interface <code>DataInput</code> , which
     * will then return a <code>short</code> equal
     * to <code>(short)v</code>.
     * 将两个字节写入输出流以表示参数的值。 要写入的字节值按照显示的顺序是：
     * (byte)(0xff & (v >> 8)) (byte)(0xff & v)
     * 由该方法写入的字节可以由readShort的readShort DataInput ，然后返回short等于(short)v 。
     *
     * @param v the <code>short</code> value to be written.
     * @throws IOException if an I/O error occurs.
     */
    void writeShort(int v) throws IOException;

    /**
     * Writes a <code>char</code> value, which
     * is comprised of two bytes, to the
     * output stream.
     * The byte values to be written, in the  order
     * shown, are:
     * <pre>{@code
     * (byte)(0xff & (v >> 8))
     * (byte)(0xff & v)
     * }</pre><p>
     * The bytes written by this method may be
     * read by the <code>readChar</code> method
     * of interface <code>DataInput</code> , which
     * will then return a <code>char</code> equal
     * to <code>(char)v</code>.
     * 将由两个字节组成的char值写入输出流。 要写入的字节值按照显示的顺序是：
     * (byte)(0xff & (v >> 8)) (byte)(0xff & v)
     * 用这种方法写入的字节可以由读取readChar接口的方法DataInput ，然后将返回一个char等于(char)v 。
     *
     * @param v the <code>char</code> value to be written.
     * @throws IOException if an I/O error occurs.
     */
    void writeChar(int v) throws IOException;

    /**
     * Writes an <code>int</code> value, which is
     * comprised of four bytes, to the output stream.
     * The byte values to be written, in the  order
     * shown, are:
     * <pre>{@code
     * (byte)(0xff & (v >> 24))
     * (byte)(0xff & (v >> 16))
     * (byte)(0xff & (v >>  8))
     * (byte)(0xff & v)
     * }</pre><p>
     * The bytes written by this method may be read
     * by the <code>readInt</code> method of interface
     * <code>DataInput</code> , which will then
     * return an <code>int</code> equal to <code>v</code>.
     * 将int值写入输出流，该值由四个字节组成。 要写入的字节值按照显示的顺序是：
     * (byte)(0xff & (v >> 24)) (byte)(0xff & (v >> 16)) (byte)(0xff & (v >> 8)) (byte)(0xff & v)
     * 用这种方法写入的字节可以由读取readInt接口的方法DataInput ，则这将返回一个int等于v 。
     *
     * @param v the <code>int</code> value to be written.
     * @throws IOException if an I/O error occurs.
     */
    void writeInt(int v) throws IOException;

    /**
     * Writes a <code>long</code> value, which is
     * comprised of eight bytes, to the output stream.
     * The byte values to be written, in the  order
     * shown, are:
     * <pre>{@code
     * (byte)(0xff & (v >> 56))
     * (byte)(0xff & (v >> 48))
     * (byte)(0xff & (v >> 40))
     * (byte)(0xff & (v >> 32))
     * (byte)(0xff & (v >> 24))
     * (byte)(0xff & (v >> 16))
     * (byte)(0xff & (v >>  8))
     * (byte)(0xff & v)
     * }</pre><p>
     * The bytes written by this method may be
     * read by the <code>readLong</code> method
     * of interface <code>DataInput</code> , which
     * will then return a <code>long</code> equal
     * to <code>v</code>.
     * 将long值（由八个字节组成）写入输出流。 要写入的字节值按照显示的顺序是：
     * (byte)(0xff & (v >> 56)) (byte)(0xff & (v >> 48)) (byte)(0xff & (v >> 40)) (byte)(0xff & (v >> 32)) (byte)(0xff & (v >> 24)) (byte)(0xff & (v >> 16)) (byte)(0xff & (v >> 8)) (byte)(0xff & v)
     * 用这种方法写入的字节可以由读取readLong接口的方法DataInput ，然后将返回一个long等于v 。
     *
     * @param v the <code>long</code> value to be written.
     * @throws IOException if an I/O error occurs.
     */
    void writeLong(long v) throws IOException;

    /**
     * Writes a <code>float</code> value,
     * which is comprised of four bytes, to the output stream.
     * It does this as if it first converts this
     * <code>float</code> value to an <code>int</code>
     * in exactly the manner of the <code>Float.floatToIntBits</code>
     * method  and then writes the <code>int</code>
     * value in exactly the manner of the  <code>writeInt</code>
     * method.  The bytes written by this method
     * may be read by the <code>readFloat</code>
     * method of interface <code>DataInput</code>,
     * which will then return a <code>float</code>
     * equal to <code>v</code>.
     * 将float值写入输出流，该值由四个字节组成。 它这样做是因为，
     * 如果它首先将这个float值到int在完全相同的方式Float.floatToIntBits方法，
     * 然后再写入int在完全相同的方式值writeInt方法。
     * 用这种方法写入的字节可以由读取readFloat接口的方法DataInput ，然后将返回一个float等于v 。
     *
     * @param v the <code>float</code> value to be written.
     * @throws IOException if an I/O error occurs.
     */
    void writeFloat(float v) throws IOException;

    /**
     * Writes a <code>double</code> value,
     * which is comprised of eight bytes, to the output stream.
     * It does this as if it first converts this
     * <code>double</code> value to a <code>long</code>
     * in exactly the manner of the <code>Double.doubleToLongBits</code>
     * method  and then writes the <code>long</code>
     * value in exactly the manner of the  <code>writeLong</code>
     * method. The bytes written by this method
     * may be read by the <code>readDouble</code>
     * method of interface <code>DataInput</code>,
     * which will then return a <code>double</code>
     * equal to <code>v</code>.
     * 将double值（由8个字节组成）写入输出流。 它这样做是因为，
     * 如果它首先将这个double值到long在完全相同的方式Double.doubleToLongBits方法，
     * 然后再写入long在完全相同的方式值writeLong方法。
     * 用这种方法写入的字节可以由读取readDouble接口的方法DataInput ，然后将返回一个double等于v 。
     *
     * @param v the <code>double</code> value to be written.
     * @throws IOException if an I/O error occurs.
     */
    void writeDouble(double v) throws IOException;

    /**
     * Writes a string to the output stream.
     * For every character in the string
     * <code>s</code>,  taken in order, one byte
     * is written to the output stream.  If
     * <code>s</code> is <code>null</code>, a <code>NullPointerException</code>
     * is thrown.<p>  If <code>s.length</code>
     * is zero, then no bytes are written. Otherwise,
     * the character <code>s[0]</code> is written
     * first, then <code>s[1]</code>, and so on;
     * the last character written is <code>s[s.length-1]</code>.
     * For each character, one byte is written,
     * the low-order byte, in exactly the manner
     * of the <code>writeByte</code> method . The
     * high-order eight bits of each character
     * in the string are ignored.
     * 将一个字符串写入输出流。 对于字符串中的每个字符s ，为了拍摄，一个字节写入到输出流。
     * 如果s是null ，则抛出一个NullPointerException 。
     * 如果s.length为零，则不会写入任何字节。 否则，首先写入字符s[0] ，然后写入s[1] ，等等;
     * 写最后一个字符是s[s.length-1] 。 对于每个字符，写入一个字节，低字节，按照writeByte方法的方式。
     * 字符串中每个字符的高8位被忽略。
     *
     * @param s the string of bytes to be written.
     * @throws IOException if an I/O error occurs.
     */
    void writeBytes(String s) throws IOException;

    /**
     * Writes every character in the string <code>s</code>,
     * to the output stream, in order,
     * two bytes per character. If <code>s</code>
     * is <code>null</code>, a <code>NullPointerException</code>
     * is thrown.  If <code>s.length</code>
     * is zero, then no characters are written.
     * Otherwise, the character <code>s[0]</code>
     * is written first, then <code>s[1]</code>,
     * and so on; the last character written is
     * <code>s[s.length-1]</code>. For each character,
     * two bytes are actually written, high-order
     * byte first, in exactly the manner of the
     * <code>writeChar</code> method.
     * 写入每一个字符在字符串中s ，到输出流中，为了，每个字符使用两个字节。
     * 如果s是null ，则抛出一个NullPointerException 。
     * 如果s.length为零，则不会写任何字符。 否则，首先写入字符s[0] ，然后写入s[1] ，等等;
     * 写最后一个字符是s[s.length-1] 。 对于每个字符，两个字节实际上是以writeChar方式的方式写入高位字节。
     *
     * @param s the string value to be written.
     * @throws IOException if an I/O error occurs.
     */
    void writeChars(String s) throws IOException;

    /**
     * Writes two bytes of length information
     * to the output stream, followed
     * by the
     * <a href="DataInput.html#modified-utf-8">modified UTF-8</a>
     * representation
     * of  every character in the string <code>s</code>.
     * If <code>s</code> is <code>null</code>,
     * a <code>NullPointerException</code> is thrown.
     * Each character in the string <code>s</code>
     * is converted to a group of one, two, or
     * three bytes, depending on the value of the
     * character.<p>
     * If a character <code>c</code>
     * is in the range <code>&#92;u0001</code> through
     * <code>&#92;u007f</code>, it is represented
     * by one byte:
     * <pre>(byte)c </pre>  <p>
     * If a character <code>c</code> is <code>&#92;u0000</code>
     * or is in the range <code>&#92;u0080</code>
     * through <code>&#92;u07ff</code>, then it is
     * represented by two bytes, to be written
     * in the order shown: <pre>{@code
     * (byte)(0xc0 | (0x1f & (c >> 6)))
     * (byte)(0x80 | (0x3f & c))
     * }</pre> <p> If a character
     * <code>c</code> is in the range <code>&#92;u0800</code>
     * through <code>uffff</code>, then it is
     * represented by three bytes, to be written
     * in the order shown: <pre>{@code
     * (byte)(0xe0 | (0x0f & (c >> 12)))
     * (byte)(0x80 | (0x3f & (c >>  6)))
     * (byte)(0x80 | (0x3f & c))
     * }</pre>  <p> First,
     * the total number of bytes needed to represent
     * all the characters of <code>s</code> is
     * calculated. If this number is larger than
     * <code>65535</code>, then a <code>UTFDataFormatException</code>
     * is thrown. Otherwise, this length is written
     * to the output stream in exactly the manner
     * of the <code>writeShort</code> method;
     * after this, the one-, two-, or three-byte
     * representation of each character in the
     * string <code>s</code> is written.<p>  The
     * bytes written by this method may be read
     * by the <code>readUTF</code> method of interface
     * <code>DataInput</code> , which will then
     * return a <code>String</code> equal to <code>s</code>.
     * 将两个字节的长度信息写入输出流，其后是字符串 s中每个字符的s 。
     * 如果s是null ，则抛出一个NullPointerException 。
     * 字符串s中的每个字符根据字符的值s为一个，两个或三个字节的组。
     * 如果一个字符c在\u0001到\u007f ，它由一个字节表示：
     * <p>
     * (byte)c
     * 如果一个字符c是\u0000或者在\u0080到\u07ff的范围内，那么它由两个字节表示，按照显示的顺序写：
     * <p>
     * (byte)(0xc0 | (0x1f & (c >> 6))) (byte)(0x80 | (0x3f & c))
     * 如果字符c在\u0800到uffff ，那么它将由三个字节表示，按照显示的顺序写入：
     * <p>
     * (byte)(0xe0 | (0x0f & (c >> 12))) (byte)(0x80 | (0x3f & (c >> 6))) (byte)(0x80 | (0x3f & c))
     * 首先，以表示的所有字符所需的字节的总数s被计算。 如果这个数字大于65535 ，则抛出一个UTFDataFormatException 。
     * 否则，这个长度以writeShort方式的方式写入输出流; 之后，写入字符串s中的每个字符的一个，两个或三个字节的表示。
     * <p>
     * 用这种方法写入的字节可以由读取readUTF接口的方法DataInput ，然后将返回一个String等于s 。
     *
     * @param s the string value to be written.
     * @throws IOException if an I/O error occurs.
     */
    void writeUTF(String s) throws IOException;
}
