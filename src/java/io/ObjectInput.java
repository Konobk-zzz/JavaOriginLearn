/*
 * Copyright (c) 1996, 2010, Oracle and/or its affiliates. All rights reserved.
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
 * ObjectInput extends the DataInput interface to include the reading of
 * objects. DataInput includes methods for the input of primitive types,
 * ObjectInput extends that interface to include objects, arrays, and Strings.
 * ObjectInput扩展了DataInput接口以包含对象的读取。
 * DataInput包括用于输入原始类型的方法，ObjectInput将该接口扩展为包含对象，数组和字符串。
 *
 * @author  unascribed
 * @see java.io.InputStream
 * @see java.io.ObjectOutputStream
 * @see java.io.ObjectInputStream
 * @since   JDK1.1
 */
public interface ObjectInput extends DataInput, AutoCloseable {
    /**
     * Read and return an object. The class that implements this interface
     * defines where the object is "read" from.
     * 读取并返回一个对象。 实现此接口的类定义了对象“读取”的位置。
     *
     * @return the object read from the stream
     * @exception java.lang.ClassNotFoundException If the class of a serialized
     *      object cannot be found.
     * @exception IOException If any of the usual Input/Output
     * related exceptions occur.
     */
    public Object readObject()
        throws ClassNotFoundException, IOException;

    /**
     * Reads a byte of data. This method will block if no input is
     * available.
     * 读取一个字节的数据。 如果没有输入可用，此方法将阻止。
     * @return  the byte read, or -1 if the end of the
     *          stream is reached.
     * @exception IOException If an I/O error has occurred.
     */
    public int read() throws IOException;

    /**
     * Reads into an array of bytes.  This method will
     * block until some input is available.
     * 读入一个字节数组。 该方法将阻塞，直到有些输入可用。
     * @param b the buffer into which the data is read
     * @return  the actual number of bytes read, -1 is
     *          returned when the end of the stream is reached.
     * @exception IOException If an I/O error has occurred.
     */
    public int read(byte b[]) throws IOException;

    /**
     * Reads into an array of bytes.  This method will
     * block until some input is available.
     * 读入一个字节数组。 该方法将阻塞，直到有些输入可用。
     * @param b the buffer into which the data is read
     * @param off the start offset of the data
     * @param len the maximum number of bytes read
     * @return  the actual number of bytes read, -1 is
     *          returned when the end of the stream is reached.
     * @exception IOException If an I/O error has occurred.
     */
    public int read(byte b[], int off, int len) throws IOException;

    /**
     * Skips n bytes of input.
     * 跳过n个字节的输入。
     * @param n the number of bytes to be skipped
     * @return  the actual number of bytes skipped.
     * @exception IOException If an I/O error has occurred.
     */
    public long skip(long n) throws IOException;

    /**
     * Returns the number of bytes that can be read
     * without blocking.
     * 返回可以读取而不阻塞的字节数。
     * @return the number of available bytes.
     * @exception IOException If an I/O error has occurred.
     */
    public int available() throws IOException;

    /**
     * Closes the input stream. Must be called
     * to release any resources associated with
     * the stream.
     * 关闭输入流。 必须调用释放与流相关联的任何资源。
     * @exception IOException If an I/O error has occurred.
     */
    public void close() throws IOException;
}
