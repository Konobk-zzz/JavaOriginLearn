/*
 * Copyright (c) 1996, 2012, Oracle and/or its affiliates. All rights reserved.
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
 * Abstract class for reading character streams.  The only methods that a
 * subclass must implement are read(char[], int, int) and close().  Most
 * subclasses, however, will override some of the methods defined here in order
 * to provide higher efficiency, additional functionality, or both.
 * 用于读取字符流的抽象类。 子类必须实现的唯一方法是read（char []，int，int）和close（）。
 * 然而，大多数子类将覆盖这里定义的一些方法，以便提供更高的效率，附加的功能或两者。
 *
 *
 * @see BufferedReader
 * @see   LineNumberReader
 * @see CharArrayReader
 * @see InputStreamReader
 * @see   FileReader
 * @see FilterReader
 * @see   PushbackReader
 * @see PipedReader
 * @see StringReader
 * @see Writer
 *
 * @author      Mark Reinhold
 * @since       JDK1.1
 */

public abstract class Reader implements Readable, Closeable {

    /**
     * The object used to synchronize operations on this stream.  For
     * efficiency, a character-stream object may use an object other than
     * itself to protect critical sections.  A subclass should therefore use
     * the object in this field rather than <tt>this</tt> or a synchronized
     * method.
     * 用于同步此流上的操作的对象。 为了效率，字符流对象可以使用自身以外的对象来保护关键部分。
     * 因此，子类应该使用此字段中的对象而不是this或同步方法。
     */
    protected Object lock;

    /**
     * Creates a new character-stream reader whose critical sections will
     * synchronize on the reader itself.
     * 创建一个新的字符流阅读器，其关键部分将在阅读器本身上同步。
     */
    protected Reader() {
        this.lock = this;
    }

    /**
     * Creates a new character-stream reader whose critical sections will
     * synchronize on the given object.
     * 创建一个新的字符流阅读器，其关键部分将在给定对象上同步。
     *
     * @param lock  The Object to synchronize on.
     */
    protected Reader(Object lock) {
        if (lock == null) {
            throw new NullPointerException();
        }
        this.lock = lock;
    }

    /**
     * Attempts to read characters into the specified character buffer.
     * The buffer is used as a repository of characters as-is: the only
     * changes made are the results of a put operation. No flipping or
     * rewinding of the buffer is performed.
     * 尝试将字符读入指定的字符缓冲区。 缓冲区用作字符存储库：唯一的更改是put操作的结果。
     * 不执行缓冲器的翻转或倒带。
     *
     * @param target the buffer to read characters into
     * @return The number of characters added to the buffer, or
     *         -1 if this source of characters is at its end
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if target is null
     * @throws java.nio.ReadOnlyBufferException if target is a read only buffer
     * @since 1.5
     */
    public int read(java.nio.CharBuffer target) throws IOException {
        int len = target.remaining();
        char[] cbuf = new char[len];
        int n = read(cbuf, 0, len);
        if (n > 0)
            target.put(cbuf, 0, n);
        return n;
    }

    /**
     * Reads a single character.  This method will block until a character is
     * available, an I/O error occurs, or the end of the stream is reached.
     * 读一个字符 该方法将阻塞，直到字符可用，发生I / O错误或达到流的结尾。
     *
     * <p> Subclasses that intend to support efficient single-character input
     * should override this method.
     * 旨在支持高效单字符输入的子类应该覆盖此方法。
     *
     * @return     The character read, as an integer in the range 0 to 65535
     *             (<tt>0x00-0xffff</tt>), or -1 if the end of the stream has
     *             been reached
     *             字符读取，作为0到65535（ 0x00-0xffff ）范围内的整数，如果已经达到流的末尾，则为-1。
     *
     * @exception  IOException  If an I/O error occurs
     */
    public int read() throws IOException {
        char cb[] = new char[1];
        if (read(cb, 0, 1) == -1)
            return -1;
        else
            return cb[0];
    }

    /**
     * Reads characters into an array.  This method will block until some input
     * is available, an I/O error occurs, or the end of the stream is reached.
     * 将字符读入数组。 该方法将阻塞，直到某些输入可用，发生I / O错误或达到流的结尾。
     *
     * @param       cbuf  Destination buffer
     *
     * @return      The number of characters read, or -1
     *              if the end of the stream
     *              has been reached
     *
     * @exception   IOException  If an I/O error occurs
     */
    public int read(char cbuf[]) throws IOException {
        return read(cbuf, 0, cbuf.length);
    }

    /**
     * Reads characters into a portion of an array.  This method will block
     * until some input is available, an I/O error occurs, or the end of the
     * stream is reached.
     * 将字符读入数组的一部分。 该方法将阻塞，直到某些输入可用，发生I / O错误或达到流的结尾。
     *
     * @param      cbuf  Destination buffer
     * @param      off   Offset at which to start storing characters
     * @param      len   Maximum number of characters to read
     *
     * @return     The number of characters read, or -1 if the end of the
     *             stream has been reached
     *
     * @exception  IOException  If an I/O error occurs
     */
    abstract public int read(char cbuf[], int off, int len) throws IOException;

    /** Maximum skip-buffer size */
    private static final int maxSkipBufferSize = 8192;

    /** Skip buffer, null until allocated */
    private char skipBuffer[] = null;

    /**
     * Skips characters.  This method will block until some characters are
     * available, an I/O error occurs, or the end of the stream is reached.
     * 跳过字符 该方法将阻塞，直到某些字符可用，发生I / O错误或达到流的结尾。
     *
     * @param  n  The number of characters to skip
     *
     * @return    The number of characters actually skipped
     *
     * @exception  IllegalArgumentException  If <code>n</code> is negative.
     * @exception  IOException  If an I/O error occurs
     */
    public long skip(long n) throws IOException {
        if (n < 0L)
            throw new IllegalArgumentException("skip value is negative");
        int nn = (int) Math.min(n, maxSkipBufferSize);
        synchronized (lock) {
            if ((skipBuffer == null) || (skipBuffer.length < nn))
                skipBuffer = new char[nn];
            long r = n;
            while (r > 0) {
                int nc = read(skipBuffer, 0, (int)Math.min(r, nn));
                if (nc == -1)
                    break;
                r -= nc;
            }
            return n - r;
        }
    }

    /**
     * Tells whether this stream is ready to be read.
     * 告诉这个流是否准备好被读取。
     *
     * @return True if the next read() is guaranteed not to block for input,
     * false otherwise.  Note that returning false does not guarantee that the
     * next read will block.
     * 如果下一个read（）保证不阻止输入，则为True，否则为false。
     * 请注意，返回false并不能代表下一次读取将被阻止。
     *
     * @exception  IOException  If an I/O error occurs
     */
    public boolean ready() throws IOException {
        return false;
    }

    /**
     * Tells whether this stream supports the mark() operation. The default
     * implementation always returns false. Subclasses should override this
     * method.
     * 告诉这个流是否支持mark（）操作。 默认实现始终返回false。 子类应该覆盖此方法。
     *
     * @return true if and only if this stream supports the mark operation.
     */
    public boolean markSupported() {
        return false;
    }

    /**
     * Marks the present position in the stream.  Subsequent calls to reset()
     * will attempt to reposition the stream to this point.  Not all
     * character-input streams support the mark() operation.
     * 标记流中的当前位置。 对reset（）的后续调用将尝试将流重新定位到此位置。 并非所有字符输入流都支持mark（）操作。
     *
     * @param  readAheadLimit  Limit on the number of characters that may be
     *                         read while still preserving the mark.  After
     *                         reading this many characters, attempting to
     *                         reset the stream may fail.
     *                         限制仍然保留标记时可能读取的字符数。 读了这么多字符后，尝试重置流可能会失败。
     *
     * @exception  IOException  If the stream does not support mark(),
     *                          or if some other I/O error occurs
     */
    public void mark(int readAheadLimit) throws IOException {
        throw new IOException("mark() not supported");
    }

    /**
     * Resets the stream.  If the stream has been marked, then attempt to
     * reposition it at the mark.  If the stream has not been marked, then
     * attempt to reset it in some way appropriate to the particular stream,
     * for example by repositioning it to its starting point.  Not all
     * character-input streams support the reset() operation, and some support
     * reset() without supporting mark().
     *
     * @exception  IOException  If the stream has not been marked,
     *                          or if the mark has been invalidated,
     *                          or if the stream does not support reset(),
     *                          or if some other I/O error occurs
     */
    public void reset() throws IOException {
        throw new IOException("reset() not supported");
    }

    /**
     * Closes the stream and releases any system resources associated with
     * it.  Once the stream has been closed, further read(), ready(),
     * mark(), reset(), or skip() invocations will throw an IOException.
     * Closing a previously closed stream has no effect.
     *
     * @exception  IOException  If an I/O error occurs
     */
     abstract public void close() throws IOException;

}
