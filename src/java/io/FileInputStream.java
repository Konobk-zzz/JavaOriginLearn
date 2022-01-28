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

import java.nio.channels.FileChannel;
import sun.nio.ch.FileChannelImpl;


/**
 * A <code>FileInputStream</code> obtains input bytes
 * from a file in a file system. What files
 * are  available depends on the host environment.
 * FileInputStream从文件系统中的文件获取输入字节。 什么文件可用取决于主机环境。
 *
 * <p><code>FileInputStream</code> is meant for reading streams of raw bytes
 * such as image data. For reading streams of characters, consider using
 * <code>FileReader</code>.
 * FileInputStream用于读取诸如图像数据的原始字节流。 要阅读字符串，请考虑使用FileReader
 *
 * @author  Arthur van Hoff
 * @see     java.io.File
 * @see     java.io.FileDescriptor
 * @see     java.io.FileOutputStream
 * @see     java.nio.file.Files#newInputStream
 * @since   JDK1.0
 */
public
class FileInputStream extends InputStream
{
    /* File Descriptor - handle to the open file */
    private final FileDescriptor fd;

    /**
     * The path of the referenced file
     * (null if the stream is created with a file descriptor)
     * 引用文件的路径（如果是null，说明流已经被一个文件描述符创建了）
     */
    private final String path;

    private FileChannel channel = null;

    private final Object closeLock = new Object();
    private volatile boolean closed = false;

    /**
     * Creates a <code>FileInputStream</code> by
     * opening a connection to an actual file,
     * the file named by the path name <code>name</code>
     * in the file system.  A new <code>FileDescriptor</code>
     * object is created to represent this file
     * connection.
     * 通过打开与实际文件的连接创建一个FileInputStream文件，该文件由文件系统中的路径名name命名。
     * 创建一个新的FileDescriptor对象来表示此文件连接。
     * <p>
     * First, if there is a security
     * manager, its <code>checkRead</code> method
     * is called with the <code>name</code> argument
     * as its argument.
     * 首先，如果有一个安全管理器，它的checkRead方法被调用与name参数作为其参数。
     * <p>
     * If the named file does not exist, is a directory rather than a regular
     * file, or for some other reason cannot be opened for reading then a
     * <code>FileNotFoundException</code> is thrown.
     * 如果该名称的文件不存在，是一个文件目录，或一些其他原因不能够打开他，则抛出FileNotFoundException
     *
     * @param      name   the system-dependent file name.
     * @exception  FileNotFoundException  if the file does not exist,
     *                   is a directory rather than a regular file,
     *                   or for some other reason cannot be opened for
     *                   reading.
     * @exception  SecurityException      if a security manager exists and its
     *               <code>checkRead</code> method denies read access
     *               to the file.
     * @see        java.lang.SecurityManager#checkRead(java.lang.String)
     */
    public FileInputStream(String name) throws FileNotFoundException {
        this(name != null ? new File(name) : null);
    }

    /**
     * Creates a <code>FileInputStream</code> by
     * opening a connection to an actual file,
     * the file named by the <code>File</code>
     * object <code>file</code> in the file system.
     * A new <code>FileDescriptor</code> object
     * is created to represent this file connection.
     * 创建一个 FileInputStream 通过一个打开的真实文件连接。
     * 文件名取决于 File对象 的入参 file在文件系统中的名称。
     * 一个新的 FileDescriptor 对象将会被创建用来代表这个文件连接。
     * <p>
     * First, if there is a security manager,
     * its <code>checkRead</code> method  is called
     * with the path represented by the <code>file</code>
     * argument as its argument.
     * 首先，如果存在安全管理， checkRead 方法将会使用 file的路径
     * 作为参数检查
     * <p>
     * If the named file does not exist, is a directory rather than a regular
     * file, or for some other reason cannot be opened for reading then a
     * <code>FileNotFoundException</code> is thrown.
     *
     * @param      file   the file to be opened for reading.
     * @exception  FileNotFoundException  if the file does not exist,
     *                   is a directory rather than a regular file,
     *                   or for some other reason cannot be opened for
     *                   reading.
     * @exception  SecurityException      if a security manager exists and its
     *               <code>checkRead</code> method denies read access to the file.
     * @see        java.io.File#getPath()
     * @see        java.lang.SecurityManager#checkRead(java.lang.String)
     */
    public FileInputStream(File file) throws FileNotFoundException {
        String name = (file != null ? file.getPath() : null);
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkRead(name);
        }
        if (name == null) {
            throw new NullPointerException();
        }
        if (file.isInvalid()) {
            throw new FileNotFoundException("Invalid file path");
        }
        fd = new FileDescriptor();
        fd.attach(this);
        path = name;
        open(name);
    }

    /**
     * Creates a <code>FileInputStream</code> by using the file descriptor
     * <code>fdObj</code>, which represents an existing connection to an
     * actual file in the file system.
     * 创建一个 FileInputStream 通过使用文件描述对象 fdObj，他代表了一个文件系统中
     * 真实物理文件已经存在的连接
     * <p>
     * If there is a security manager, its <code>checkRead</code> method is
     * called with the file descriptor <code>fdObj</code> as its argument to
     * see if it's ok to read the file descriptor. If read access is denied
     * to the file descriptor a <code>SecurityException</code> is thrown.
     * <p>
     * If <code>fdObj</code> is null then a <code>NullPointerException</code>
     * is thrown.
     * <p>
     * This constructor does not throw an exception if <code>fdObj</code>
     * is {@link java.io.FileDescriptor#valid() invalid}.
     * However, if the methods are invoked on the resulting stream to attempt
     * I/O on the stream, an <code>IOException</code> is thrown.
     *
     * @param      fdObj   the file descriptor to be opened for reading.
     * @throws     SecurityException      if a security manager exists and its
     *                 <code>checkRead</code> method denies read access to the
     *                 file descriptor.
     * @see        SecurityManager#checkRead(java.io.FileDescriptor)
     */
    public FileInputStream(FileDescriptor fdObj) {
        SecurityManager security = System.getSecurityManager();
        if (fdObj == null) {
            throw new NullPointerException();
        }
        if (security != null) {
            security.checkRead(fdObj);
        }
        fd = fdObj;
        path = null;

        /*
         * FileDescriptor is being shared by streams.
         * Register this stream with FileDescriptor tracker.
         */
        fd.attach(this);
    }

    /**
     * Opens the specified file for reading.
     * 打开指定文件读取
     * @param name the name of the file
     */
    private native void open0(String name) throws FileNotFoundException;

    // wrap native call to allow instrumentation
    /**
     * Opens the specified file for reading.
     * @param name the name of the file
     */
    private void open(String name) throws FileNotFoundException {
        open0(name);
    }

    /**
     * Reads a byte of data from this input stream. This method blocks
     * if no input is yet available.
     * 从该输入流中读取字节数据。如果输入还不可用则该方法阻塞。
     *
     * @return     the next byte of data, or <code>-1</code> if the end of the
     *             file is reached.
     * @exception  IOException  if an I/O error occurs.
     */
    public int read() throws IOException {
        return read0();
    }

    private native int read0() throws IOException;

    /**
     * Reads a subarray as a sequence of bytes.
     * 读取顺序的字节子集
     * @param b the data to be written
     * @param off the start offset in the data
     * @param len the number of bytes that are written
     * @exception IOException If an I/O error has occurred.
     */
    private native int readBytes(byte b[], int off, int len) throws IOException;

    /**
     * Reads up to <code>b.length</code> bytes of data from this input
     * stream into an array of bytes. This method blocks until some input
     * is available.
     * 从输入流读取数组长度的字节到字节数组中。该方法将会阻塞到输入可用。
     *
     * @param      b   the buffer into which the data is read.
     * @return     the total number of bytes read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the file has been reached.
     * @exception  IOException  if an I/O error occurs.
     */
    public int read(byte b[]) throws IOException {
        return readBytes(b, 0, b.length);
    }

    /**
     * Reads up to <code>len</code> bytes of data from this input stream
     * into an array of bytes. If <code>len</code> is not zero, the method
     * blocks until some input is available; otherwise, no
     * bytes are read and <code>0</code> is returned.
     * 读取 len 个字节从输入流中到字节数组中。如果 len 不是0，则方法会阻塞直到输入可用；
     * 否则，不会读取任何字节，并且返回 0
     *
     * @param      b     the buffer into which the data is read.
     * @param      off   the start offset in the destination array <code>b</code>
     * @param      len   the maximum number of bytes read.
     * @return     the total number of bytes read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the file has been reached.
     * @exception  NullPointerException If <code>b</code> is <code>null</code>.
     * @exception  IndexOutOfBoundsException If <code>off</code> is negative,
     * <code>len</code> is negative, or <code>len</code> is greater than
     * <code>b.length - off</code>
     * @exception  IOException  if an I/O error occurs.
     */
    public int read(byte b[], int off, int len) throws IOException {
        return readBytes(b, off, len);
    }

    /**
     * Skips over and discards <code>n</code> bytes of data from the
     * input stream.
     * 在当前输入流中跳过 n 个字节
     *
     * <p>The <code>skip</code> method may, for a variety of
     * reasons, end up skipping over some smaller number of bytes,
     * possibly <code>0</code>. If <code>n</code> is negative, the method
     * will try to skip backwards. In case the backing file does not support
     * backward skip at its current position, an <code>IOException</code> is
     * thrown. The actual number of bytes skipped is returned. If it skips
     * forwards, it returns a positive value. If it skips backwards, it
     * returns a negative value.
     * 由于各种原因， skip方法可能会跳过一些较小数量的字节，可能是0 。
     * 如果n为负，则该方法将尝试向后跳。 如果后台文件不支持其当前位置的向后跳过，
     * 则会抛出IOException 。 返回实际跳过的字节数。 如果它向前跳，它返回一个正值。
     * 如果它向后跳，它返回一个负值。
     *
     * <p>This method may skip more bytes than what are remaining in the
     * backing file. This produces no exception and the number of bytes skipped
     * may include some number of bytes that were beyond the EOF of the
     * backing file. Attempting to read from the stream after skipping past
     * the end will result in -1 indicating the end of the file.
     * 该方法可能会跳过比后备文件中剩余的字节更多的字节。
     * 这不会产生异常，并且跳过的字节数可能包括超出后台文件的EOF的一些字节数。
     * 尝试在跳过结束后从流中读取将导致-1表示文件的结尾。
     *
     * @param      n   the number of bytes to be skipped.
     * @return     the actual number of bytes skipped.
     * @exception  IOException  if n is negative, if the stream does not
     *             support seek, or if an I/O error occurs.
     */
    public native long skip(long n) throws IOException;

    /**
     * Returns an estimate of the number of remaining bytes that can be read (or
     * skipped over) from this input stream without blocking by the next
     * invocation of a method for this input stream. Returns 0 when the file
     * position is beyond EOF. The next invocation might be the same thread
     * or another thread. A single read or skip of this many bytes will not
     * block, but may read or skip fewer bytes.
     * 返回从此输入流中可以读取（或跳过）的剩余字节数的估计值，而不会被下一次调用此输入流的方法阻塞。
     * 当文件位置超出EOF时返回0。 下一个调用可能是同一个线程或另一个线程。
     * 这个多个字节的单个读取或跳过将不会被阻塞，但可以读取或跳过较少的字节。
     *
     * <p> In some cases, a non-blocking read (or skip) may appear to be
     * blocked when it is merely slow, for example when reading large
     * files over slow networks.
     * 在某些情况下，非阻塞读取（或跳过）在缓慢时可能会被阻止，例如在慢速网络中读取大文件时。
     *
     * @return     an estimate of the number of remaining bytes that can be read
     *             (or skipped over) from this input stream without blocking.
     * @exception  IOException  if this file input stream has been closed by calling
     *             {@code close} or an I/O error occurs.
     */
    public native int available() throws IOException;

    /**
     * Closes this file input stream and releases any system resources
     * associated with the stream.
     * 关闭此文件输入流并释放与流相关联的任何系统资源。
     *
     * <p> If this stream has an associated channel then the channel is closed
     * as well.
     * 如果该流具有相关联的信道，则该信道也被关闭。
     *
     * @exception  IOException  if an I/O error occurs.
     *
     * @revised 1.4
     * @spec JSR-51
     */
    public void close() throws IOException {
        synchronized (closeLock) {
            if (closed) {
                return;
            }
            closed = true;
        }
        if (channel != null) {
           channel.close();
        }

        fd.closeAll(new Closeable() {
            public void close() throws IOException {
               close0();
           }
        });
    }

    /**
     * Returns the <code>FileDescriptor</code>
     * object  that represents the connection to
     * the actual file in the file system being
     * used by this <code>FileInputStream</code>.
     * 返回 FileDescriptor对象，表示与该FileInputStream
     * 正在使用的文件系统中的实际文件的 FileInputStream 。
     *
     * @return     the file descriptor object associated with this stream.
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FileDescriptor
     */
    public final FileDescriptor getFD() throws IOException {
        if (fd != null) {
            return fd;
        }
        throw new IOException();
    }

    /**
     * Returns the unique {@link java.nio.channels.FileChannel FileChannel}
     * object associated with this file input stream.
     * 返回与此文件输入流相关联的唯一FileChannel对象。
     *
     * <p> The initial {@link java.nio.channels.FileChannel#position()
     * position} of the returned channel will be equal to the
     * number of bytes read from the file so far.  Reading bytes from this
     * stream will increment the channel's position.  Changing the channel's
     * position, either explicitly or by reading, will change this stream's
     * file position.
     * 返回通道的初始position将等于从文件读取的字节数到目前为止。
     * 从该流读取字节将增加通道的位置。 通过显式地或通过阅读来改变频道的位置将会改变这个流的文件位置。
     *
     * @return  the file channel associated with this file input stream
     *
     * @since 1.4
     * @spec JSR-51
     */
    public FileChannel getChannel() {
        synchronized (this) {
            if (channel == null) {
                channel = FileChannelImpl.open(fd, path, true, false, this);
            }
            return channel;
        }
    }

    private static native void initIDs();

    private native void close0() throws IOException;

    static {
        initIDs();
    }

    /**
     * Ensures that the <code>close</code> method of this file input stream is
     * called when there are no more references to it.
     * 确保当没有更多的引用时，调用此文件输入流的 close方法。
     *
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FileInputStream#close()
     */
    protected void finalize() throws IOException {
        if ((fd != null) &&  (fd != FileDescriptor.in)) {
            /* if fd is shared, the references in FileDescriptor
             * will ensure that finalizer is only called when
             * safe to do so. All references using the fd have
             * become unreachable. We can call close()
             */
            close();
        }
    }
}
