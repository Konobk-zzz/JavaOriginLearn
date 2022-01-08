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

package java.io;

/**
 * This abstract class is the superclass of all classes representing
 * an input stream of bytes.
 * 这个抽象类是表示输入字节流的所有类的超类
 *
 * <p> Applications that need to define a subclass of <code>InputStream</code>
 * must always provide a method that returns the next byte of input.
 * 需要定义InputStream子类的应用InputStream必须始终提供一种返回输入的下一个字节的方法。
 *
 * @author  Arthur van Hoff
 * @see     java.io.BufferedInputStream
 * @see     java.io.ByteArrayInputStream
 * @see     java.io.DataInputStream
 * @see     java.io.FilterInputStream
 * @see     java.io.InputStream#read()
 * @see     java.io.OutputStream
 * @see     java.io.PushbackInputStream
 * @since   JDK1.0
 */
public abstract class InputStream implements Closeable {

    // MAX_SKIP_BUFFER_SIZE is used to determine the maximum buffer size to
    // use when skipping.
    private static final int MAX_SKIP_BUFFER_SIZE = 2048;

    /**
     * Reads the next byte of data from the input stream. The value byte is
     * returned as an <code>int</code> in the range <code>0</code> to
     * <code>255</code>. If no byte is available because the end of the stream
     * has been reached, the value <code>-1</code> is returned. This method
     * blocks until input data is available, the end of the stream is detected,
     * or an exception is thrown.
     * 从输入流读取数据的下一个字节。值字节被返回作为int范围0至255。
     * 如果没有字节可用，因为已经到达流的末尾，则返回值-1 。
     * 该方法阻塞直到输入数据可用，检测到流的结尾，或抛出异常。
     *
     * <p> A subclass must provide an implementation of this method.
     *
     * @return     the next byte of data, or <code>-1</code> if the end of the
     *             stream is reached.
     *             数据的下一个字节，如果达到流的末尾， -1
     * @exception  IOException  if an I/O error occurs.
     */
    public abstract int read() throws IOException;

    /**
     * Reads some number of bytes from the input stream and stores them into
     * the buffer array <code>b</code>. The number of bytes actually read is
     * returned as an integer.  This method blocks until input data is
     * available, end of file is detected, or an exception is thrown.
     * 从输入流读取一些字节数，并将它们存储到缓冲区b 。 实际读取的字节数作为整数返回。
     * 该方法阻塞直到输入数据可用，检测到文件结束或抛出异常。
     *
     * <p> If the length of <code>b</code> is zero, then no bytes are read and
     * <code>0</code> is returned; otherwise, there is an attempt to read at
     * least one byte. If no byte is available because the stream is at the
     * end of the file, the value <code>-1</code> is returned; otherwise, at
     * least one byte is read and stored into <code>b</code>.
     * 如果b的长度为零，则不会读取字节并返回0 ; 否则，尝试读取至少一个字节。
     * 如果没有字节可用，因为流在文件末尾，则返回值-1 ; 否则，读取至少一个字节并存储到b 。
     *
     * <p> The first byte read is stored into element <code>b[0]</code>, the
     * next one into <code>b[1]</code>, and so on. The number of bytes read is,
     * at most, equal to the length of <code>b</code>. Let <i>k</i> be the
     * number of bytes actually read; these bytes will be stored in elements
     * <code>b[0]</code> through <code>b[</code><i>k</i><code>-1]</code>,
     * leaving elements <code>b[</code><i>k</i><code>]</code> through
     * <code>b[b.length-1]</code> unaffected.
     * 第一个字节读取存储在元素b[0] ，下一个字节存入b[1]等等。
     * 读取的字节数最多等于b的长度。 令k为实际读取的字节数;
     * 这些字节将存储在元素b[0]至b[ k -1] ，使元素b[ k ]至b[b.length-1]不受影响。
     *
     * <p> The <code>read(b)</code> method for class <code>InputStream</code>
     * has the same effect as: <pre><code> read(b, 0, b.length) </code></pre>
     *
     * @param      b   the buffer into which the data is read.
     * @return     the total number of bytes read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the stream has been reached.
     * @exception  IOException  If the first byte cannot be read for any reason
     * other than the end of the file, if the input stream has been closed, or
     * if some other I/O error occurs.
     * @exception  NullPointerException  if <code>b</code> is <code>null</code>.
     * @see        java.io.InputStream#read(byte[], int, int)
     */
    public int read(byte b[]) throws IOException {
        return read(b, 0, b.length);
    }

    /**
     * Reads up to <code>len</code> bytes of data from the input stream into
     * an array of bytes.  An attempt is made to read as many as
     * <code>len</code> bytes, but a smaller number may be read.
     * The number of bytes actually read is returned as an integer.
     * 从输入流读取len字节的数据到一个字节数组。 尝试读取多达len个字节，但可以读取较小的数字。 实际读取的字节数作为整数返回。
     *
     * <p> This method blocks until input data is available, end of file is
     * detected, or an exception is thrown.
     * 该方法阻塞直到输入数据可用，检测到文件结束或抛出异常。
     *
     * <p> If <code>len</code> is zero, then no bytes are read and
     * <code>0</code> is returned; otherwise, there is an attempt to read at
     * least one byte. If no byte is available because the stream is at end of
     * file, the value <code>-1</code> is returned; otherwise, at least one
     * byte is read and stored into <code>b</code>.
     * 如果len为零，则不会读取字节并返回0 ; 否则，尝试读取至少一个字节。
     * 如果没有字节可用，因为流是文件的-1则返回值-1 ; 否则，读取至少一个字节并存储到b 。
     *
     * <p> The first byte read is stored into element <code>b[off]</code>, the
     * next one into <code>b[off+1]</code>, and so on. The number of bytes read
     * is, at most, equal to <code>len</code>. Let <i>k</i> be the number of
     * bytes actually read; these bytes will be stored in elements
     * <code>b[off]</code> through <code>b[off+</code><i>k</i><code>-1]</code>,
     * leaving elements <code>b[off+</code><i>k</i><code>]</code> through
     * <code>b[off+len-1]</code> unaffected.
     * 第一个字节读取存储在元素b[off] ，下一个字节存入b[off+1] ，等等。 读取的字节数最多等于len 。
     * 令k为实际读取的字节数; 这些字节将存储在元素b[off]至b[off+ k -1] ，使元素b[off+ k ]至b[off+len-1]不受影响。
     *
     * <p> In every case, elements <code>b[0]</code> through
     * <code>b[off]</code> and elements <code>b[off+len]</code> through
     * <code>b[b.length-1]</code> are unaffected.
     *
     * <p> The <code>read(b,</code> <code>off,</code> <code>len)</code> method
     * for class <code>InputStream</code> simply calls the method
     * <code>read()</code> repeatedly. If the first such call results in an
     * <code>IOException</code>, that exception is returned from the call to
     * the <code>read(b,</code> <code>off,</code> <code>len)</code> method.  If
     * any subsequent call to <code>read()</code> results in a
     * <code>IOException</code>, the exception is caught and treated as if it
     * were end of file; the bytes read up to that point are stored into
     * <code>b</code> and the number of bytes read before the exception
     * occurred is returned. The default implementation of this method blocks
     * until the requested amount of input data <code>len</code> has been read,
     * end of file is detected, or an exception is thrown. Subclasses are encouraged
     * to provide a more efficient implementation of this method.
     *
     * @param      b     the buffer into which the data is read.
     * @param      off   the start offset in array <code>b</code>
     *                   at which the data is written.
     * @param      len   the maximum number of bytes to read.
     * @return     the total number of bytes read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the stream has been reached.
     * @exception  IOException If the first byte cannot be read for any reason
     * other than end of file, or if the input stream has been closed, or if
     * some other I/O error occurs.
     * @exception  NullPointerException If <code>b</code> is <code>null</code>.
     * @exception  IndexOutOfBoundsException If <code>off</code> is negative,
     * <code>len</code> is negative, or <code>len</code> is greater than
     * <code>b.length - off</code>
     * @see        java.io.InputStream#read()
     */
    public int read(byte b[], int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        int c = read();
        if (c == -1) {
            return -1;
        }
        b[off] = (byte)c;

        int i = 1;
        try {
            for (; i < len ; i++) {
                c = read();
                if (c == -1) {
                    break;
                }
                b[off + i] = (byte)c;
            }
        } catch (IOException ee) {
        }
        return i;
    }

    /**
     * Skips over and discards <code>n</code> bytes of data from this input
     * stream. The <code>skip</code> method may, for a variety of reasons, end
     * up skipping over some smaller number of bytes, possibly <code>0</code>.
     * This may result from any of a number of conditions; reaching end of file
     * before <code>n</code> bytes have been skipped is only one possibility.
     * The actual number of bytes skipped is returned. If {@code n} is
     * negative, the {@code skip} method for class {@code InputStream} always
     * returns 0, and no bytes are skipped. Subclasses may handle the negative
     * value differently.
     * 跳过并丢弃来自此输入流的n字节数据。 由于各种原因， skip方法可能会跳过一些较小数量的字节，可能是0 。
     * 这可能是由许多条件中的任何一个引起的 n字节之前已经跳过的文件到达结束只有一种可能。
     * 返回实际跳过的字节数。 如果n是否定的，则skip类方法InputStream总是返回0，并且没有字节被跳过。 子类可以不同地处理负值。
     *
     * <p> The <code>skip</code> method of this class creates a
     * byte array and then repeatedly reads into it until <code>n</code> bytes
     * have been read or the end of the stream has been reached. Subclasses are
     * encouraged to provide a more efficient implementation of this method.
     * For instance, the implementation may depend on the ability to seek.
     * skip方法创建一个字节数组，然后重复读入它，直到已读取n个字节或已达到流的结尾。
     * 鼓励子类提供更有效的方法实现。 例如，实施可能取决于寻求的能力。
     *
     * @param      n   the number of bytes to be skipped.
     * @return     the actual number of bytes skipped.
     * @exception  IOException  if the stream does not support seek,
     *                          or if some other I/O error occurs.
     */
    public long skip(long n) throws IOException {

        long remaining = n;
        int nr;

        if (n <= 0) {
            return 0;
        }

        int size = (int)Math.min(MAX_SKIP_BUFFER_SIZE, remaining);
        byte[] skipBuffer = new byte[size];
        while (remaining > 0) {
            nr = read(skipBuffer, 0, (int)Math.min(size, remaining));
            if (nr < 0) {
                break;
            }
            remaining -= nr;
        }

        return n - remaining;
    }

    /**
     * Returns an estimate of the number of bytes that can be read (or
     * skipped over) from this input stream without blocking by the next
     * invocation of a method for this input stream. The next invocation
     * might be the same thread or another thread.  A single read or skip of this
     * many bytes will not block, but may read or skip fewer bytes.
     * 返回从该输入流中可以读取（或跳过）的字节数的估计值，而不会被下一次调用此输入流的方法阻塞。
     * 下一个调用可能是同一个线程或另一个线程。 这个多个字节的单个读取或跳过将不会被阻塞，但可以读取或跳过较少的字节。
     *
     * <p> Note that while some implementations of {@code InputStream} will return
     * the total number of bytes in the stream, many will not.  It is
     * never correct to use the return value of this method to allocate
     * a buffer intended to hold all data in this stream.
     * 注意，尽管一些实现InputStream将在流中返回的字节总数，许多人不会。
     * 使用此方法的返回值分配用于保存此流中的所有数据的缓冲区是绝对不正确的。
     *
     * <p> A subclass' implementation of this method may choose to throw an
     * {@link IOException} if this input stream has been closed by
     * invoking the {@link #close()} method.
     * 子类此方法的实现可以选择抛出IOException如果输入流已通过调用关闭close()方法。
     *
     * <p> The {@code available} method for class {@code InputStream} always
     * returns {@code 0}.
     *
     * <p> This method should be overridden by subclasses.
     *
     * @return     an estimate of the number of bytes that can be read (or skipped
     *             over) from this input stream without blocking or {@code 0} when
     *             it reaches the end of the input stream.
     *             当输入流到达输入流的末尾时，可以从该输入流中读取（或跳过）的字节数，而不阻塞的字节数 0 。
     * @exception  IOException if an I/O error occurs.
     */
    public int available() throws IOException {
        return 0;
    }

    /**
     * Closes this input stream and releases any system resources associated
     * with the stream.
     * 关闭此输入流并释放与流相关联的任何系统资源。
     *
     * <p> The <code>close</code> method of <code>InputStream</code> does
     * nothing.
     * 该close的方法InputStream什么都不做。
     *
     * @exception  IOException  if an I/O error occurs.
     */
    public void close() throws IOException {}

    /**
     * Marks the current position in this input stream. A subsequent call to
     * the <code>reset</code> method repositions this stream at the last marked
     * position so that subsequent reads re-read the same bytes.
     * 标记此输入流中的当前位置。 对reset方法的后续调用会将该流重新定位在最后一个标记的位置，以便后续读取重新读取相同的字节。
     *
     * <p> The <code>readlimit</code> arguments tells this input stream to
     * allow that many bytes to be read before the mark position gets
     * invalidated.
     * readlimit参数告诉这个输入流，以允许在标记位置无效之前读取多少字节。
     *
     * <p> The general contract of <code>mark</code> is that, if the method
     * <code>markSupported</code> returns <code>true</code>, the stream somehow
     * remembers all the bytes read after the call to <code>mark</code> and
     * stands ready to supply those same bytes again if and whenever the method
     * <code>reset</code> is called.  However, the stream is not required to
     * remember any data at all if more than <code>readlimit</code> bytes are
     * read from the stream before <code>reset</code> is called.
     * 通常标记的约定是，如果方法 markSupported 返回 true，流将会以某种方法记录所有
     * 调用过 mark 方法以后读取的字节。并且当调用 reset 方法时再次提供这些字节。
     * 然而，流不是必须记录所有的字节，如果之后从流中读取了超过了 readlimit 的字节，在
     * reset 方法被调用前，这些字节会被抛弃。
     *
     * <p> Marking a closed stream should not have any effect on the stream.
     * 标记封闭的流对流不应有任何影响。
     *
     * <p> The <code>mark</code> method of <code>InputStream</code> does
     * nothing.
     * 该mark的方法InputStream什么都不做。
     *
     * @param   readlimit   the maximum limit of bytes that can be read before
     *                      the mark position becomes invalid.
     *                      标记位置无效之前可以读取的最大字节数
     * @see     java.io.InputStream#reset()
     */
    public synchronized void mark(int readlimit) {}

    /**
     * Repositions this stream to the position at the time the
     * <code>mark</code> method was last called on this input stream.
     * 将此流重新定位到上次在此输入流上调用mark方法时的位置
     *
     * <p> The general contract of <code>reset</code> is:
     * reset 的通用约定是：
     *
     * <ul>
     * <li> If the method <code>markSupported</code> returns
     * <code>true</code>, then:
     * 如果 方法 markSupported 返回true
     *
     *     <ul><li> If the method <code>mark</code> has not been called since
     *     the stream was created, or the number of bytes read from the stream
     *     since <code>mark</code> was last called is larger than the argument
     *     to <code>mark</code> at that last call, then an
     *     <code>IOException</code> might be thrown.
     *     如果自从流被创建 mark方法 未被调用过，或者自从上一次调用 mark方法，从流中
     *     读取的字节数大于上次调用 mark方法的限制参数，那么IOException可能会被抛出
     *
     *     <li> If such an <code>IOException</code> is not thrown, then the
     *     stream is reset to a state such that all the bytes read since the
     *     most recent call to <code>mark</code> (or since the start of the
     *     file, if <code>mark</code> has not been called) will be resupplied
     *     to subsequent callers of the <code>read</code> method, followed by
     *     any bytes that otherwise would have been the next input data as of
     *     the time of the call to <code>reset</code>. </ul>
     *     如果IOException没有被抛出，流的状态会被重置到最近标记的位置，接下去读取的字节是
     *     从这个位置开始的（或者如果 mark方法未被调用则从文件开头开始），之后是是调用reset
     *     后下一个输入数据的任何字节。
     *
     * <li> If the method <code>markSupported</code> returns
     * <code>false</code>, then:
     * 如果 方法 markSupported 返回false
     *
     *     <ul><li> The call to <code>reset</code> may throw an
     *     <code>IOException</code>.
     *     嗲用 reset方法可能会抛出IOException
     *
     *     <li> If an <code>IOException</code> is not thrown, then the stream
     *     is reset to a fixed state that depends on the particular type of the
     *     input stream and how it was created. The bytes that will be supplied
     *     to subsequent callers of the <code>read</code> method depend on the
     *     particular type of the input stream. </ul></ul>
     *     如果IOException没有被抛出，流将会被重置到 fixed(固定)的状态，这取决于输入流的特定类型及其创建方式。
     *     将会提供给随后调用 read方法的字节取决于输入流的类型。
     *
     * <p>The method <code>reset</code> for class <code>InputStream</code>
     * does nothing except throw an <code>IOException</code>.
     * 该方法reset类InputStream什么也不做只是抛出一个IOException 。
     *
     * @exception  IOException  if this stream has not been marked or if the
     *               mark has been invalidated.
     * @see     java.io.InputStream#mark(int)
     * @see     java.io.IOException
     */
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    /**
     * Tests if this input stream supports the <code>mark</code> and
     * <code>reset</code> methods. Whether or not <code>mark</code> and
     * <code>reset</code> are supported is an invariant property of a
     * particular input stream instance. The <code>markSupported</code> method
     * of <code>InputStream</code> returns <code>false</code>.
     * 测试这个输入流是否支持mark和reset方法。 是否mark和reset是特定输入流实例的不变属性。
     * 该markSupported的方法InputStream返回false 。
     *
     * @return  <code>true</code> if this stream instance supports the mark
     *          and reset methods; <code>false</code> otherwise.
     * @see     java.io.InputStream#mark(int)
     * @see     java.io.InputStream#reset()
     */
    public boolean markSupported() {
        return false;
    }

}
