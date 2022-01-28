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
 * The {@code DataInput} interface provides
 * for reading bytes from a binary stream and
 * reconstructing from them data in any of
 * the Java primitive types. There is also
 * a
 * facility for reconstructing a {@code String}
 * from data in
 * <a href="#modified-utf-8">modified UTF-8</a>
 * format.
 * DataInput接口提供从二进制流读取字节并从其中重建任何Java原语类型的数据。
 * 还有，为了重建设施String从数据modified UTF-8格式。
 * <p>
 * It is generally true of all the reading
 * routines in this interface that if end of
 * file is reached before the desired number
 * of bytes has been read, an {@code EOFException}
 * (which is a kind of {@code IOException})
 * is thrown. If any byte cannot be read for
 * any reason other than end of file, an {@code IOException}
 * other than {@code EOFException} is
 * thrown. In particular, an {@code IOException}
 * may be thrown if the input stream has been
 * closed.
 * 在这个接口中的所有读取例程通常都是如果在读取所需的字节数之前到达文件结尾
 * ，则抛出一个EOFException （一种是IOException ）。
 * 如果除文件结尾之外的任何其他原因无法读取任何字节，则抛出IOException以外的EOFException 。
 * 特别地，如果输入流已经被关闭，则可以抛出IOException 。
 *
 * <h3><a name="modified-utf-8">Modified UTF-8</a></h3>
 * <p>
 * Implementations of the DataInput and DataOutput interfaces represent
 * Unicode strings in a format that is a slight modification of UTF-8.
 * (For information regarding the standard UTF-8 format, see section
 * <i>3.9 Unicode Encoding Forms</i> of <i>The Unicode Standard, Version
 * 4.0</i>).
 * Note that in the following table, the most significant bit appears in the
 * far left-hand column.
 *
 * <blockquote>
 *   <table border="1" cellspacing="0" cellpadding="8"
 *          summary="Bit values and bytes">
 *     <tr>
 *       <th colspan="9"><span style="font-weight:normal">
 *         All characters in the range {@code '\u005Cu0001'} to
 *         {@code '\u005Cu007F'} are represented by a single byte:</span></th>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <th colspan="8" id="bit_a">Bit Values</th>
 *     </tr>
 *     <tr>
 *       <th id="byte1_a">Byte 1</th>
 *       <td><center>0</center>
 *       <td colspan="7"><center>bits 6-0</center>
 *     </tr>
 *     <tr>
 *       <th colspan="9"><span style="font-weight:normal">
 *         The null character {@code '\u005Cu0000'} and characters
 *         in the range {@code '\u005Cu0080'} to {@code '\u005Cu07FF'} are
 *         represented by a pair of bytes:</span></th>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <th colspan="8" id="bit_b">Bit Values</th>
 *     </tr>
 *     <tr>
 *       <th id="byte1_b">Byte 1</th>
 *       <td><center>1</center>
 *       <td><center>1</center>
 *       <td><center>0</center>
 *       <td colspan="5"><center>bits 10-6</center>
 *     </tr>
 *     <tr>
 *       <th id="byte2_a">Byte 2</th>
 *       <td><center>1</center>
 *       <td><center>0</center>
 *       <td colspan="6"><center>bits 5-0</center>
 *     </tr>
 *     <tr>
 *       <th colspan="9"><span style="font-weight:normal">
 *         {@code char} values in the range {@code '\u005Cu0800'}
 *         to {@code '\u005CuFFFF'} are represented by three bytes:</span></th>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <th colspan="8"id="bit_c">Bit Values</th>
 *     </tr>
 *     <tr>
 *       <th id="byte1_c">Byte 1</th>
 *       <td><center>1</center>
 *       <td><center>1</center>
 *       <td><center>1</center>
 *       <td><center>0</center>
 *       <td colspan="4"><center>bits 15-12</center>
 *     </tr>
 *     <tr>
 *       <th id="byte2_b">Byte 2</th>
 *       <td><center>1</center>
 *       <td><center>0</center>
 *       <td colspan="6"><center>bits 11-6</center>
 *     </tr>
 *     <tr>
 *       <th id="byte3">Byte 3</th>
 *       <td><center>1</center>
 *       <td><center>0</center>
 *       <td colspan="6"><center>bits 5-0</center>
 *     </tr>
 *   </table>
 * </blockquote>
 * <p>
 * The differences between this format and the
 * standard UTF-8 format are the following:
 * <ul>
 * <li>The null byte {@code '\u005Cu0000'} is encoded in 2-byte format
 *     rather than 1-byte, so that the encoded strings never have
 *     embedded nulls.
 * <li>Only the 1-byte, 2-byte, and 3-byte formats are used.
 * <li><a href="../lang/Character.html#unicode">Supplementary characters</a>
 *     are represented in the form of surrogate pairs.
 * </ul>
 * 此格式与标准UTF-8格式之间的差异如下：
 *
 * 空字节'\u0000'以2字节格式而不是1字节编码，因此编码字符串从不具有嵌入的空值。
 * 仅使用1字节，2字节和3字节格式。
 * Supplementary characters以代理对的形式表示。
 * @author  Frank Yellin
 * @see     java.io.DataInputStream
 * @see     java.io.DataOutput
 * @since   JDK1.0
 */
public
interface DataInput {
    /**
     * Reads some bytes from an input
     * stream and stores them into the buffer
     * array {@code b}. The number of bytes
     * read is equal
     * to the length of {@code b}.
     * 从输入流中读取一些字节，并将它们存储到缓冲区数组b 。 读取的字节数等于b的长度
     * <p>
     * This method blocks until one of the
     * following conditions occurs:
     * 该方法阻塞，直到出现以下情况之一：
     * <ul>
     * <li>{@code b.length}
     * bytes of input data are available, in which
     * case a normal return is made.
     * b.length字节的输入数据可用，在这种情况下将进行正常的返回。
     *
     * <li>End of
     * file is detected, in which case an {@code EOFException}
     * is thrown.
     * 检测到文件结束，在这种情况下会抛出一个EOFException 。
     *
     * <li>An I/O error occurs, in
     * which case an {@code IOException} other
     * than {@code EOFException} is thrown.
     * 发生I / O错误，在这种情况下，抛出IOException以外的EOFException 。
     * </ul>
     * <p>
     * If {@code b} is {@code null},
     * a {@code NullPointerException} is thrown.
     * If {@code b.length} is zero, then
     * no bytes are read. Otherwise, the first
     * byte read is stored into element {@code b[0]},
     * the next one into {@code b[1]}, and
     * so on.
     * If an exception is thrown from
     * this method, then it may be that some but
     * not all bytes of {@code b} have been
     * updated with data from the input stream.
     * 如果b是null ，则会抛出一个NullPointerException 。
     * 如果b.length为零，则不读取任何字节。 否则，读取的第一个字节存储到元素b[0] ，
     * 下一个写入b[1] ，依此类推。 如果从该方法抛出异常，
     * 那么可能是b中的一些但不是所有的字节已经被使用来自输入流的数据更新。
     *
     * @param     b   the buffer into which the data is read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    void readFully(byte b[]) throws IOException;

    /**
     *
     * Reads {@code len}
     * bytes from
     * an input stream.
     * <p>
     * This method
     * blocks until one of the following conditions
     * occurs:
     * <ul>
     * <li>{@code len} bytes
     * of input data are available, in which case
     * a normal return is made.
     *
     * <li>End of file
     * is detected, in which case an {@code EOFException}
     * is thrown.
     *
     * <li>An I/O error occurs, in
     * which case an {@code IOException} other
     * than {@code EOFException} is thrown.
     * </ul>
     * <p>
     * If {@code b} is {@code null},
     * a {@code NullPointerException} is thrown.
     * If {@code off} is negative, or {@code len}
     * is negative, or {@code off+len} is
     * greater than the length of the array {@code b},
     * then an {@code IndexOutOfBoundsException}
     * is thrown.
     * If {@code len} is zero,
     * then no bytes are read. Otherwise, the first
     * byte read is stored into element {@code b[off]},
     * the next one into {@code b[off+1]},
     * and so on. The number of bytes read is,
     * at most, equal to {@code len}.
     *
     * @param     b   the buffer into which the data is read.
     * @param off  an int specifying the offset into the data.
     * @param len  an int specifying the number of bytes to read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    void readFully(byte b[], int off, int len) throws IOException;

    /**
     * Makes an attempt to skip over
     * {@code n} bytes
     * of data from the input
     * stream, discarding the skipped bytes. However,
     * it may skip
     * over some smaller number of
     * bytes, possibly zero. This may result from
     * any of a
     * number of conditions; reaching
     * end of file before {@code n} bytes
     * have been skipped is
     * only one possibility.
     * This method never throws an {@code EOFException}.
     * The actual
     * number of bytes skipped is returned.
     * 尝试从输入流中跳过n个字节的数据，丢弃跳过的字节。
     * 但是，它可能跳过一些较小数量的字节，可能会为零。
     * 这可能是由许多条件中的任何一个引起的 n字节之前已经跳过的文件到达结束只是一种可能。
     * 这个方法永远不会抛出一个EOFException 。 返回实际跳过的字节数。
     *
     * @param      n   the number of bytes to be skipped.
     * @return     the number of bytes actually skipped.
     * @exception  IOException   if an I/O error occurs.
     */
    int skipBytes(int n) throws IOException;

    /**
     * Reads one input byte and returns
     * {@code true} if that byte is nonzero,
     * {@code false} if that byte is zero.
     * This method is suitable for reading
     * the byte written by the {@code writeBoolean}
     * method of interface {@code DataOutput}.
     * 读取一个输入字节，并返回true如果该字节不为零， false如果该字节是零。
     * 此方法适用于读取由writeBoolean接口的方法DataOutput写入的字节 。
     *
     * @return     the {@code boolean} value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    boolean readBoolean() throws IOException;

    /**
     * Reads and returns one input byte.
     * The byte is treated as a signed value in
     * the range {@code -128} through {@code 127},
     * inclusive.
     * This method is suitable for
     * reading the byte written by the {@code writeByte}
     * method of interface {@code DataOutput}.
     * 读取并返回一个输入字节。 该字节被视为-128至127范围内的有符号值。 此方法适用于读取由writeByte接口的方法DataOutput写入的字节 。
     *
     * @return     the 8-bit value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    byte readByte() throws IOException;

    /**
     * Reads one input byte, zero-extends
     * it to type {@code int}, and returns
     * the result, which is therefore in the range
     * {@code 0}
     * through {@code 255}.
     * This method is suitable for reading
     * the byte written by the {@code writeByte}
     * method of interface {@code DataOutput}
     * if the argument to {@code writeByte}
     * was intended to be a value in the range
     * {@code 0} through {@code 255}.
     * 读取一个输入字节，将其扩展为类型int ，并返回结果，因此在0到255 。
     * 此方法适用于读取由writeByte接口的方法DataOutput写入的字节,
     * 如果参数到writeByte想要的值是在范围内的值0 ~ 255 。
     *
     * @return     the unsigned 8-bit value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    int readUnsignedByte() throws IOException;

    /**
     * Reads two input bytes and returns
     * a {@code short} value. Let {@code a}
     * be the first byte read and {@code b}
     * be the second byte. The value
     * returned
     * is:
     * <pre>{@code (short)((a << 8) | (b & 0xff))
     * }</pre>
     * This method
     * is suitable for reading the bytes written
     * by the {@code writeShort} method of
     * interface {@code DataOutput}.
     * 读取两个输入字节并返回一个short值。 让a成为第一个字节， b是第二个字节。 返回的值为：
     *    (short)((a << 8) | (b & 0xff))
     * 此方法适用于读取由写入字节writeShort接口的方法DataOutput 。
     *
     * @return     the 16-bit value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    short readShort() throws IOException;

    /**
     * Reads two input bytes and returns
     * an {@code int} value in the range {@code 0}
     * through {@code 65535}. Let {@code a}
     * be the first byte read and
     * {@code b}
     * be the second byte. The value returned is:
     * <pre>{@code (((a & 0xff) << 8) | (b & 0xff))
     * }</pre>
     * This method is suitable for reading the bytes
     * written by the {@code writeShort} method
     * of interface {@code DataOutput}  if
     * the argument to {@code writeShort}
     * was intended to be a value in the range
     * {@code 0} through {@code 65535}.
     * 读取两个输入字节，并返回0到65535的int值。 让a成为第一个字节， b是第二个字节。 返回的值为：
     *    (((a & 0xff) << 8) | (b & 0xff))
     * 此方法适用于读取由写入字节writeShort接口的方法DataOutput如果参数到writeShort意欲是在范围内的值0通过65535 。
     *
     *
     * @return     the unsigned 16-bit value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    int readUnsignedShort() throws IOException;

    /**
     * Reads two input bytes and returns a {@code char} value.
     * Let {@code a}
     * be the first byte read and {@code b}
     * be the second byte. The value
     * returned is:
     * <pre>{@code (char)((a << 8) | (b & 0xff))
     * }</pre>
     * This method
     * is suitable for reading bytes written by
     * the {@code writeChar} method of interface
     * {@code DataOutput}.
     * 读取两个输入字节并返回一个char值。 让a成为第一个字节， b是第二个字节。 返回的值为：
     *    (char)((a << 8) | (b & 0xff))
     * 此方法适用于读取由写入的字节writeChar接口的方法DataOutput 。
     *
     * @return     the {@code char} value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    char readChar() throws IOException;

    /**
     * Reads four input bytes and returns an
     * {@code int} value. Let {@code a-d}
     * be the first through fourth bytes read. The value returned is:
     * <pre>{@code
     * (((a & 0xff) << 24) | ((b & 0xff) << 16) |
     *  ((c & 0xff) <<  8) | (d & 0xff))
     * }</pre>
     * This method is suitable
     * for reading bytes written by the {@code writeInt}
     * method of interface {@code DataOutput}.
     * 读取四个输入字节并返回一个int值。 让a-d是第一到第四个字节读取。 返回的值为：
     *    (((a & 0xff) << 24) | ((b & 0xff) << 16) | ((c & 0xff) << 8) | (d & 0xff))
     * 此方法适用于读取由写入的字节writeInt接口的方法DataOutput 。
     *
     * @return     the {@code int} value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    int readInt() throws IOException;

    /**
     * Reads eight input bytes and returns
     * a {@code long} value. Let {@code a-h}
     * be the first through eighth bytes read.
     * The value returned is:
     * <pre>{@code
     * (((long)(a & 0xff) << 56) |
     *  ((long)(b & 0xff) << 48) |
     *  ((long)(c & 0xff) << 40) |
     *  ((long)(d & 0xff) << 32) |
     *  ((long)(e & 0xff) << 24) |
     *  ((long)(f & 0xff) << 16) |
     *  ((long)(g & 0xff) <<  8) |
     *  ((long)(h & 0xff)))
     * }</pre>
     * <p>
     * This method is suitable
     * for reading bytes written by the {@code writeLong}
     * method of interface {@code DataOutput}.
     * 读取八个输入字节并返回一个long值。 让a-h是第一个到第八个字节读取。 返回的值为：
     *    (((long)(a & 0xff) << 56) |
     *    ((long)(b & 0xff) << 48) |
     *    ((long)(c & 0xff) << 40) |
     *    ((long)(d & 0xff) << 32) |
     *    ((long)(e & 0xff) << 24) |
     *    ((long)(f & 0xff) << 16) |
     *    ((long)(g & 0xff) << 8) |
     *    ((long)(h & 0xff)))
     * 此方法适用于读取由写入的字节writeLong接口的方法DataOutput 。
     *
     * @return     the {@code long} value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    long readLong() throws IOException;

    /**
     * Reads four input bytes and returns
     * a {@code float} value. It does this
     * by first constructing an {@code int}
     * value in exactly the manner
     * of the {@code readInt}
     * method, then converting this {@code int}
     * value to a {@code float} in
     * exactly the manner of the method {@code Float.intBitsToFloat}.
     * This method is suitable for reading
     * bytes written by the {@code writeFloat}
     * method of interface {@code DataOutput}.
     * 读取四个输入字节并返回一个float值。
     * 它由第一构造一个执行此int在完全相同的方式值readInt方法，
     * 那么这个变换int值到一个float在该方法的完全相同的方式Float.intBitsToFloat 。
     * 此方法适用于读取由写入的字节writeFloat接口的方法DataOutput 。
     *
     * @return     the {@code float} value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    float readFloat() throws IOException;

    /**
     * Reads eight input bytes and returns
     * a {@code double} value. It does this
     * by first constructing a {@code long}
     * value in exactly the manner
     * of the {@code readLong}
     * method, then converting this {@code long}
     * value to a {@code double} in exactly
     * the manner of the method {@code Double.longBitsToDouble}.
     * This method is suitable for reading
     * bytes written by the {@code writeDouble}
     * method of interface {@code DataOutput}.
     * 读取八个输入字节并返回一个double值。 它是通过首先按照readLong方法构建一个long值，
     * 然后将该long值转换为double的方法，方法Double.longBitsToDouble 。
     * 此方法适用于读取由写入的字节writeDouble接口的方法DataOutput 。
     *
     * @return     the {@code double} value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    double readDouble() throws IOException;

    /**
     * Reads the next line of text from the input stream.
     * It reads successive bytes, converting
     * each byte separately into a character,
     * until it encounters a line terminator or
     * end of
     * file; the characters read are then
     * returned as a {@code String}. Note
     * that because this
     * method processes bytes,
     * it does not support input of the full Unicode
     * character set.
     * <p>
     * If end of file is encountered
     * before even one byte can be read, then {@code null}
     * is returned. Otherwise, each byte that is
     * read is converted to type {@code char}
     * by zero-extension. If the character {@code '\n'}
     * is encountered, it is discarded and reading
     * ceases. If the character {@code '\r'}
     * is encountered, it is discarded and, if
     * the following byte converts &#32;to the
     * character {@code '\n'}, then that is
     * discarded also; reading then ceases. If
     * end of file is encountered before either
     * of the characters {@code '\n'} and
     * {@code '\r'} is encountered, reading
     * ceases. Once reading has ceased, a {@code String}
     * is returned that contains all the characters
     * read and not discarded, taken in order.
     * Note that every character in this string
     * will have a value less than {@code \u005Cu0100},
     * that is, {@code (char)256}.
     * 从输入流读取下一行文本。 它读取连续字节，将每个字节分别转换为字符，
     * 直到遇到行终止符或文件结尾; 读取的字符然后作为String返回。
     * 请注意，由于此方法处理字节，它不支持输入完整的Unicode字符集。
     *
     * 如果在甚至可以读取一个字节之前遇到文件结尾，则返回null 。
     * 否则，每个所读取字节被转换为类型char由零扩展。
     * 如果遇到'\n' '\n'，则会被丢弃，读取停止。
     * 如果遇到'\r' '\r'，则会被丢弃，如果以下字节转换为字符'\n' ，
     * 则也将被丢弃; 阅读然后停止。 如果在遇到任何一个字符'\n'和'\r'之前遇到文件结尾，则读取停止。
     * 一旦读取停止，将返回一个String ，其中包含读取并且不被丢弃的所有字符。
     * 请注意，此字符串中的每个字符都将具有小于\u0100的值，即(char)256 。
     *
     * @return the next line of text from the input stream,
     *         or {@code null} if the end of file is
     *         encountered before a byte can be read.
     * @exception  IOException  if an I/O error occurs.
     */
    String readLine() throws IOException;

    /**
     * Reads in a string that has been encoded using a
     * <a href="#modified-utf-8">modified UTF-8</a>
     * format.
     * The general contract of {@code readUTF}
     * is that it reads a representation of a Unicode
     * character string encoded in modified
     * UTF-8 format; this string of characters
     * is then returned as a {@code String}.
     * 读取已使用modified UTF-8格式编码的字符串。
     * readUTF的一般合同是它读取以修改的UTF-8格式编码的Unicode字符串的表示; 这个字符串然后作为String返回。
     * <p>
     * First, two bytes are read and used to
     * construct an unsigned 16-bit integer in
     * exactly the manner of the {@code readUnsignedShort}
     * method . This integer value is called the
     * <i>UTF length</i> and specifies the number
     * of additional bytes to be read. These bytes
     * are then converted to characters by considering
     * them in groups. The length of each group
     * is computed from the value of the first
     * byte of the group. The byte following a
     * group, if any, is the first byte of the
     * next group.
     * 首先，读取并使用两个字节，以完全符合readUnsignedShort方法的方式构造无符号16位整数。
     * 该整数值称为UTF长度 ，并指定要读取的其他字节数。
     * 然后将这些字节通过组合考虑来转换为字符。 每组的长度根据组的第一个字节的值计算。
     * 组之后的字节（如果有的话）是下一组的第一个字节。
     * <p>
     * If the first byte of a group
     * matches the bit pattern {@code 0xxxxxxx}
     * (where {@code x} means "may be {@code 0}
     * or {@code 1}"), then the group consists
     * of just that byte. The byte is zero-extended
     * to form a character.
     * 如果一个组的第一个字节与位模式0xxxxxxx （其中x表示“可能是0或1 ”） 1 ，
     * 则该组由该字节组成。 该字节是零扩展形成一个字符。
     * <p>
     * If the first byte
     * of a group matches the bit pattern {@code 110xxxxx},
     * then the group consists of that byte {@code a}
     * and a second byte {@code b}. If there
     * is no byte {@code b} (because byte
     * {@code a} was the last of the bytes
     * to be read), or if byte {@code b} does
     * not match the bit pattern {@code 10xxxxxx},
     * then a {@code UTFDataFormatException}
     * is thrown. Otherwise, the group is converted
     * to the character:
     * <pre>{@code (char)(((a & 0x1F) << 6) | (b & 0x3F))
     * }</pre>
     * 如果一组的第一个字节与位模式110xxxxx匹配，则该组由该字节a和第二个字节b 。
     * 如果没有字节b （因为字节a是要读取的最后一个字节），或者如果字节b与位模式10xxxxxx不匹配，
     * 则抛出UTFDataFormatException 。 否则，该组将转换为字符：
     *    (char)(((a & 0x1F) << 6) | (b & 0x3F))
     *
     * If the first byte of a group
     * matches the bit pattern {@code 1110xxxx},
     * then the group consists of that byte {@code a}
     * and two more bytes {@code b} and {@code c}.
     * If there is no byte {@code c} (because
     * byte {@code a} was one of the last
     * two of the bytes to be read), or either
     * byte {@code b} or byte {@code c}
     * does not match the bit pattern {@code 10xxxxxx},
     * then a {@code UTFDataFormatException}
     * is thrown. Otherwise, the group is converted
     * to the character:
     * <pre>{@code
     * (char)(((a & 0x0F) << 12) | ((b & 0x3F) << 6) | (c & 0x3F))
     * }</pre>
     * 如果一组的第一个字节与位模式1110xxxx匹配，则该组由该字节a和另外两个字节b和c 。
     * 如果没有字节c （因为字节a是要读取的最后两个字节之一），或者字节b或字节c与位模式10xxxxxx不匹配，
     * 则抛出UTFDataFormatException 。 否则，该组将转换为字符：
     *    (char)(((a & 0x0F) << 12) | ((b & 0x3F) << 6) | (c & 0x3F))
     *
     * If the first byte of a group matches the
     * pattern {@code 1111xxxx} or the pattern
     * {@code 10xxxxxx}, then a {@code UTFDataFormatException}
     * is thrown.
     * 如果组的第一个字节与模式1111xxxx或模式10xxxxxx ，则抛出UTFDataFormatException 。
     * <p>
     * If end of file is encountered
     * at any time during this entire process,
     * then an {@code EOFException} is thrown.
     * 如果在整个过程中的任何时间遇到文件结束，则抛出一个EOFException 。
     * <p>
     * After every group has been converted to
     * a character by this process, the characters
     * are gathered, in the same order in which
     * their corresponding groups were read from
     * the input stream, to form a {@code String},
     * which is returned.
     * 在通过该过程将每个组转换为字符之后，以与从输入流读取其对应组的顺序相同的顺序收集String ，形成返回的String 。
     * <p>
     * The {@code writeUTF}
     * method of interface {@code DataOutput}
     * may be used to write data that is suitable
     * for reading by this method.
     * 可以使用接口DataOutput的writeUTF方法来写入适合于通过该方法读取的数据。
     * @return     a Unicode string.
     * @exception  EOFException            if this stream reaches the end
     *               before reading all the bytes.
     * @exception  IOException             if an I/O error occurs.
     * @exception  UTFDataFormatException  if the bytes do not represent a
     *               valid modified UTF-8 encoding of a string.
     */
    String readUTF() throws IOException;
}
