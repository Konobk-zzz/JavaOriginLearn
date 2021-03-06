/*
 * Copyright (c) 1994, 2004, Oracle and/or its affiliates. All rights reserved.
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
 * an output stream of bytes. An output stream accepts output bytes
 * and sends them to some sink.
 * 这个抽象类是表示字节输出流的所有类的超类。 输出流接收输出字节并将其发送到某个接收器。
 * <p>
 * Applications that need to define a subclass of
 * <code>OutputStream</code> must always provide at least a method
 * that writes one byte of output.
 * 需要定义OutputStream子类的应用OutputStream必须至少提供一个写入一个字节输出的方法。
 *
 * @author Arthur van Hoff
 * @see java.io.BufferedOutputStream
 * @see java.io.ByteArrayOutputStream
 * @see java.io.DataOutputStream
 * @see java.io.FilterOutputStream
 * @see java.io.InputStream
 * @see java.io.OutputStream#write(int)
 * @since JDK1.0
 */
public abstract class OutputStream implements Closeable, Flushable {
    /**
     * Writes the specified byte to this output stream. The general
     * contract for <code>write</code> is that one byte is written
     * to the output stream. The byte to be written is the eight
     * low-order bits of the argument <code>b</code>. The 24
     * high-order bits of <code>b</code> are ignored.
     * 将指定的字节写入此输出流。 write的一般合同是将一个字节写入输出流。
     * 要写入的字节是参数b的八个低位。 b的24个高位被忽略。
     * <p>
     * Subclasses of <code>OutputStream</code> must provide an
     * implementation for this method.
     * OutputStream的OutputStream必须为此方法提供一个实现。
     *
     * @param b the <code>byte</code>.
     * @throws IOException if an I/O error occurs. In particular,
     *                     an <code>IOException</code> may be thrown if the
     *                     output stream has been closed.
     */
    public abstract void write(int b) throws IOException;

    /**
     * Writes <code>b.length</code> bytes from the specified byte array
     * to this output stream. The general contract for <code>write(b)</code>
     * is that it should have exactly the same effect as the call
     * <code>write(b, 0, b.length)</code>.
     * 将b.length字节从指定的字节数组写入此输出流。
     * write(b)的一般合约是应该具有与电话write(b, 0, b.length)完全相同的效果。
     *
     * @param b the data.
     * @throws IOException if an I/O error occurs.
     * @see java.io.OutputStream#write(byte[], int, int)
     */
    public void write(byte b[]) throws IOException {
        write(b, 0, b.length);
    }

    /**
     * Writes <code>len</code> bytes from the specified byte array
     * starting at offset <code>off</code> to this output stream.
     * The general contract for <code>write(b, off, len)</code> is that
     * some of the bytes in the array <code>b</code> are written to the
     * output stream in order; element <code>b[off]</code> is the first
     * byte written and <code>b[off+len-1]</code> is the last byte written
     * by this operation.
     * 从指定的字节数组写入len字节，从偏移off开始输出到此输出流。
     * write(b, off, len)的一般合同是数组b中的一些字节按顺序写入输出流;
     * 元素b[off]是写入的第一个字节， b[off+len-1]是此操作写入的最后一个字节。
     * <p>
     * The <code>write</code> method of <code>OutputStream</code> calls
     * the write method of one argument on each of the bytes to be
     * written out. Subclasses are encouraged to override this method and
     * provide a more efficient implementation.
     * 该write的方法OutputStream调用写出在每个字节中的一个参数的写入方法。 鼓励子类覆盖此方法并提供更有效的实现。
     * <p>
     * If <code>b</code> is <code>null</code>, a
     * <code>NullPointerException</code> is thrown.
     * <p>
     * 如果b是null ，则抛出NullPointerException 。
     * If <code>off</code> is negative, or <code>len</code> is negative, or
     * <code>off+len</code> is greater than the length of the array
     * <code>b</code>, then an <tt>IndexOutOfBoundsException</tt> is thrown.
     * 如果off为负数，或len为负数，或off+len大于数组b的长度，则抛出IndexOutOfBoundsException 。
     *
     * @param b   the data.
     * @param off the start offset in the data.
     * @param len the number of bytes to write.
     * @throws IOException if an I/O error occurs. In particular,
     *                     an <code>IOException</code> is thrown if the output
     *                     stream is closed.
     */
    public void write(byte b[], int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if ((off < 0) || (off > b.length) || (len < 0) ||
                ((off + len) > b.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        for (int i = 0; i < len; i++) {
            write(b[off + i]);
        }
    }

    /**
     * Flushes this output stream and forces any buffered output bytes
     * to be written out. The general contract of <code>flush</code> is
     * that calling it is an indication that, if any bytes previously
     * written have been buffered by the implementation of the output
     * stream, such bytes should immediately be written to their
     * intended destination.
     * 刷新此输出流并强制任何缓冲的输出字节被写出。
     * flush的一般合同是，呼叫它表明，如果先前写入的任何字节已经通过输出流的实现进行缓冲，
     * 则这些字节应该立即被写入到它们的预定目的地。
     * <p>
     * If the intended destination of this stream is an abstraction provided by
     * the underlying operating system, for example a file, then flushing the
     * stream guarantees only that bytes previously written to the stream are
     * passed to the operating system for writing; it does not guarantee that
     * they are actually written to a physical device such as a disk drive.
     * 如果此流的预期目标是由底层操作系统（例如文件）提供的抽象，那么刷新流仅保证先前写入流的字节传递到操作系统进行写入;
     * 它并不保证它们实际上被写入物理设备，如磁盘驱动器。
     * <p>
     * The <code>flush</code> method of <code>OutputStream</code> does nothing.
     * 该flush的方法OutputStream什么都不做。
     *
     * @throws IOException if an I/O error occurs.
     */
    public void flush() throws IOException {
    }

    /**
     * Closes this output stream and releases any system resources
     * associated with this stream. The general contract of <code>close</code>
     * is that it closes the output stream. A closed stream cannot perform
     * output operations and cannot be reopened.
     * 关闭此输出流并释放与此流相关联的任何系统资源。 close的一般合同是关闭输出流。
     * 封闭流不能执行输出操作，无法重新打开。
     * <p>
     * The <code>close</code> method of <code>OutputStream</code> does nothing.
     * 该close的方法OutputStream什么都不做。
     *
     * @throws IOException if an I/O error occurs.
     */
    public void close() throws IOException {
    }

}
