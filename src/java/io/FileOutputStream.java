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
 * A file output stream is an output stream for writing data to a
 * <code>File</code> or to a <code>FileDescriptor</code>. Whether or not
 * a file is available or may be created depends upon the underlying
 * platform.  Some platforms, in particular, allow a file to be opened
 * for writing by only one <tt>FileOutputStream</tt> (or other
 * file-writing object) at a time.  In such situations the constructors in
 * this class will fail if the file involved is already open.
 * 文件输出流是用于将数据写入到输出流File或一个FileDescriptor 。
 * 文件是否可用或可能被创建取决于底层平台。 特别是某些平台允许一次只能打开一个文件
 * 来写入一个FileOutputStream （或其他文件写入对象）。
 * 在这种情况下，如果所涉及的文件已经打开，则此类中的构造函数将失败。
 *
 * <p><code>FileOutputStream</code> is meant for writing streams of raw bytes
 * such as image data. For writing streams of characters, consider using
 * <code>FileWriter</code>.
 * FileOutputStream用于写入诸如图像数据的原始字节流。 对于写入字符流，请考虑使用FileWriter 。
 *
 * @author  Arthur van Hoff
 * @see     java.io.File
 * @see     java.io.FileDescriptor
 * @see     java.io.FileInputStream
 * @see     java.nio.file.Files#newOutputStream
 * @since   JDK1.0
 */
public
class FileOutputStream extends OutputStream
{
    /**
     * The system dependent file descriptor.
     */
    private final FileDescriptor fd;

    /**
     * True if the file is opened for append.
     */
    private final boolean append;

    /**
     * The associated channel, initialized lazily.
     */
    private FileChannel channel;

    /**
     * The path of the referenced file
     * (null if the stream is created with a file descriptor)
     */
    private final String path;

    private final Object closeLock = new Object();
    private volatile boolean closed = false;

    /**
     * Creates a file output stream to write to the file with the
     * specified name. A new <code>FileDescriptor</code> object is
     * created to represent this file connection.
     * 创建文件输出流以指定的名称写入文件。 创建一个新的FileDescriptor对象来表示此文件连接。
     * <p>
     * First, if there is a security manager, its <code>checkWrite</code>
     * method is called with <code>name</code> as its argument.
     * 首先，如果有一个安全管理器，它的checkWrite方法name作为参数来调用。
     * <p>
     * If the file exists but is a directory rather than a regular file, does
     * not exist but cannot be created, or cannot be opened for any other
     * reason then a <code>FileNotFoundException</code> is thrown.
     * 如果文件存在但是是一个目录而不是常规文件，不存在但不能创建，
     * 或者由于任何其他原因无法打开，那么抛出一个FileNotFoundException 。
     *
     * @param      name   the system-dependent filename
     * @exception  FileNotFoundException  if the file exists but is a directory
     *                   rather than a regular file, does not exist but cannot
     *                   be created, or cannot be opened for any other reason
     * @exception  SecurityException  if a security manager exists and its
     *               <code>checkWrite</code> method denies write access
     *               to the file.
     * @see        java.lang.SecurityManager#checkWrite(java.lang.String)
     */
    public FileOutputStream(String name) throws FileNotFoundException {
        this(name != null ? new File(name) : null, false);
    }

    /**
     * Creates a file output stream to write to the file with the specified
     * name.  If the second argument is <code>true</code>, then
     * bytes will be written to the end of the file rather than the beginning.
     * A new <code>FileDescriptor</code> object is created to represent this
     * file connection.
     * 创建文件输出流以指定的名称写入文件。 如果第二个参数是true ，则字节将写入文件的末尾而不是开头。
     * 创建一个新的FileDescriptor对象来表示此文件连接。
     * <p>
     * First, if there is a security manager, its <code>checkWrite</code>
     * method is called with <code>name</code> as its argument.
     * 首先，如果有一个安全管理员，它的checkWrite方法是以name作为参数来调用的。
     * <p>
     * If the file exists but is a directory rather than a regular file, does
     * not exist but cannot be created, or cannot be opened for any other
     * reason then a <code>FileNotFoundException</code> is thrown.
     * 如果文件存在但是是一个目录而不是常规文件，不存在但不能创建，或者由于任何其他原因无法打开，
     * 那么抛出一个FileNotFoundException 。
     *
     * @param     name        the system-dependent file name
     * @param     append      if <code>true</code>, then bytes will be written
     *                   to the end of the file rather than the beginning
     * @exception  FileNotFoundException  if the file exists but is a directory
     *                   rather than a regular file, does not exist but cannot
     *                   be created, or cannot be opened for any other reason.
     * @exception  SecurityException  if a security manager exists and its
     *               <code>checkWrite</code> method denies write access
     *               to the file.
     * @see        java.lang.SecurityManager#checkWrite(java.lang.String)
     * @since     JDK1.1
     */
    public FileOutputStream(String name, boolean append)
        throws FileNotFoundException
    {
        this(name != null ? new File(name) : null, append);
    }

    /**
     * Creates a file output stream to write to the file represented by
     * the specified <code>File</code> object. A new
     * <code>FileDescriptor</code> object is created to represent this
     * file connection.
     * 创建文件输出流以写入由指定的File对象表示的文件。 创建一个新的FileDescriptor对象来表示此文件连接。
     * <p>
     * First, if there is a security manager, its <code>checkWrite</code>
     * method is called with the path represented by the <code>file</code>
     * argument as its argument.
     * 首先，如果有一个安全管理器，它的checkWrite方法被调用与由file参数表示的路径作为其参数。
     * <p>
     * If the file exists but is a directory rather than a regular file, does
     * not exist but cannot be created, or cannot be opened for any other
     * reason then a <code>FileNotFoundException</code> is thrown.
     * 如果文件存在但是是一个目录而不是常规文件，则不存在但不能创建，或者由于任何其他原因无法打开，因此抛出FileNotFoundException 。
     *
     * @param      file               the file to be opened for writing.
     * @exception  FileNotFoundException  if the file exists but is a directory
     *                   rather than a regular file, does not exist but cannot
     *                   be created, or cannot be opened for any other reason
     * @exception  SecurityException  if a security manager exists and its
     *               <code>checkWrite</code> method denies write access
     *               to the file.
     * @see        java.io.File#getPath()
     * @see        java.lang.SecurityException
     * @see        java.lang.SecurityManager#checkWrite(java.lang.String)
     */
    public FileOutputStream(File file) throws FileNotFoundException {
        this(file, false);
    }

    /**
     * Creates a file output stream to write to the file represented by
     * the specified <code>File</code> object. If the second argument is
     * <code>true</code>, then bytes will be written to the end of the file
     * rather than the beginning. A new <code>FileDescriptor</code> object is
     * created to represent this file connection.
     * 创建文件输出流以写入由指定的File对象表示的文件。 如果第二个参数是true ，
     * 则字节将被写入文件的末尾而不是开头。 创建一个新的FileDescriptor对象来表示此文件连接。
     * <p>
     * First, if there is a security manager, its <code>checkWrite</code>
     * method is called with the path represented by the <code>file</code>
     * argument as its argument.
     * 首先，如果有一个安全管理器，则调用其checkWrite方法，其中以file参数表示的路径作为参数。
     * <p>
     * If the file exists but is a directory rather than a regular file, does
     * not exist but cannot be created, or cannot be opened for any other
     * reason then a <code>FileNotFoundException</code> is thrown.
     * 如果文件存在但是是一个目录而不是常规文件，不存在但不能创建，或者由于任何其他原因无法打开，
     * 那么将抛出一个FileNotFoundException 。
     *
     * @param      file               the file to be opened for writing.
     * @param     append      if <code>true</code>, then bytes will be written
     *                   to the end of the file rather than the beginning
     * @exception  FileNotFoundException  if the file exists but is a directory
     *                   rather than a regular file, does not exist but cannot
     *                   be created, or cannot be opened for any other reason
     * @exception  SecurityException  if a security manager exists and its
     *               <code>checkWrite</code> method denies write access
     *               to the file.
     * @see        java.io.File#getPath()
     * @see        java.lang.SecurityException
     * @see        java.lang.SecurityManager#checkWrite(java.lang.String)
     * @since 1.4
     */
    public FileOutputStream(File file, boolean append)
        throws FileNotFoundException
    {
        String name = (file != null ? file.getPath() : null);
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkWrite(name);
        }
        if (name == null) {
            throw new NullPointerException();
        }
        if (file.isInvalid()) {
            throw new FileNotFoundException("Invalid file path");
        }
        this.fd = new FileDescriptor();
        fd.attach(this);
        this.append = append;
        this.path = name;

        open(name, append);
    }

    /**
     * Creates a file output stream to write to the specified file
     * descriptor, which represents an existing connection to an actual
     * file in the file system.
     * 创建文件输出流以写入指定的文件描述符，表示与文件系统中实际文件的现有连接。
     * <p>
     * First, if there is a security manager, its <code>checkWrite</code>
     * method is called with the file descriptor <code>fdObj</code>
     * argument as its argument.
     * 首先，如果有一个安全管理器，它的checkWrite方法被调用与文件描述符fdObj参数作为其参数。
     * <p>
     * If <code>fdObj</code> is null then a <code>NullPointerException</code>
     * is thrown.
     * 如果fdObj为null，则抛出NullPointerException 。
     * <p>
     * This constructor does not throw an exception if <code>fdObj</code>
     * is {@link java.io.FileDescriptor#valid() invalid}.
     * However, if the methods are invoked on the resulting stream to attempt
     * I/O on the stream, an <code>IOException</code> is thrown.
     * 如果fdObj是invalid，这个构造函数不会抛出异常 。
     * 但是，如果在结果流上调用方法来尝试流上的I / O，则会抛出一个IOException 。
     *
     * @param      fdObj   the file descriptor to be opened for writing
     * @exception  SecurityException  if a security manager exists and its
     *               <code>checkWrite</code> method denies
     *               write access to the file descriptor
     * @see        java.lang.SecurityManager#checkWrite(java.io.FileDescriptor)
     */
    public FileOutputStream(FileDescriptor fdObj) {
        SecurityManager security = System.getSecurityManager();
        if (fdObj == null) {
            throw new NullPointerException();
        }
        if (security != null) {
            security.checkWrite(fdObj);
        }
        this.fd = fdObj;
        this.append = false;
        this.path = null;

        fd.attach(this);
    }

    /**
     * Opens a file, with the specified name, for overwriting or appending.
     * @param name name of file to be opened
     * @param append whether the file is to be opened in append mode
     */
    private native void open0(String name, boolean append)
        throws FileNotFoundException;

    // wrap native call to allow instrumentation
    /**
     * Opens a file, with the specified name, for overwriting or appending.
     * @param name name of file to be opened
     * @param append whether the file is to be opened in append mode
     */
    private void open(String name, boolean append)
        throws FileNotFoundException {
        open0(name, append);
    }

    /**
     * Writes the specified byte to this file output stream.
     * 将指定的字节写入此文件输出流。 实现write方法OutputStream 。
     *
     * @param   b   the byte to be written.
     * @param   append   {@code true} if the write operation first
     *     advances the position to the end of file
     */
    private native void write(int b, boolean append) throws IOException;

    /**
     * Writes the specified byte to this file output stream. Implements
     * the <code>write</code> method of <code>OutputStream</code>.
     * 将 b.length字节从指定的字节数组写入此文件输出流。
     *
     * @param      b   the byte to be written.
     * @exception  IOException  if an I/O error occurs.
     */
    public void write(int b) throws IOException {
        write(b, append);
    }

    /**
     * Writes a sub array as a sequence of bytes.
     * @param b the data to be written
     * @param off the start offset in the data
     * @param len the number of bytes that are written
     * @param append {@code true} to first advance the position to the
     *     end of file
     * @exception IOException If an I/O error has occurred.
     */
    private native void writeBytes(byte b[], int off, int len, boolean append)
        throws IOException;

    /**
     * Writes <code>b.length</code> bytes from the specified byte array
     * to this file output stream.
     *
     * @param      b   the data.
     * @exception  IOException  if an I/O error occurs.
     */
    public void write(byte b[]) throws IOException {
        writeBytes(b, 0, b.length, append);
    }

    /**
     * Writes <code>len</code> bytes from the specified byte array
     * starting at offset <code>off</code> to this file output stream.
     * 从位于偏移量 off的指定字节数组写入 len字节到该文件输出流。
     *
     * @param      b     the data.
     * @param      off   the start offset in the data.
     * @param      len   the number of bytes to write.
     * @exception  IOException  if an I/O error occurs.
     */
    public void write(byte b[], int off, int len) throws IOException {
        writeBytes(b, off, len, append);
    }

    /**
     * Closes this file output stream and releases any system resources
     * associated with this stream. This file output stream may no longer
     * be used for writing bytes.
     * 关闭此文件输出流并释放与此流相关联的任何系统资源。 此文件输出流可能不再用于写入字节。
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
     * Returns the file descriptor associated with this stream.
     * 返回与此流相关联的文件描述符。
     *
     * @return  the <code>FileDescriptor</code> object that represents
     *          the connection to the file in the file system being used
     *          by this <code>FileOutputStream</code> object.
     *
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FileDescriptor
     */
     public final FileDescriptor getFD()  throws IOException {
        if (fd != null) {
            return fd;
        }
        throw new IOException();
     }

    /**
     * Returns the unique {@link java.nio.channels.FileChannel FileChannel}
     * object associated with this file output stream.
     * 返回与此文件输出流相关联的唯一的FileChannel对象。
     *
     * <p> The initial {@link java.nio.channels.FileChannel#position()
     * position} of the returned channel will be equal to the
     * number of bytes written to the file so far unless this stream is in
     * append mode, in which case it will be equal to the size of the file.
     * Writing bytes to this stream will increment the channel's position
     * accordingly.  Changing the channel's position, either explicitly or by
     * writing, will change this stream's file position.
     * 返回通道的初始position将等于写入文件的字节数，除非该流是附加模式，
     * 在这种情况下，它将等于文件的大小。 将字节写入此流将相应地增加通道的位置。
     * 通过显式地或通过写入来更改频道的位置将会改变这个流的文件位置。
     *
     * @return  the file channel associated with this file output stream
     *
     * @since 1.4
     * @spec JSR-51
     */
    public FileChannel getChannel() {
        synchronized (this) {
            if (channel == null) {
                channel = FileChannelImpl.open(fd, path, false, true, append, this);
            }
            return channel;
        }
    }

    /**
     * Cleans up the connection to the file, and ensures that the
     * <code>close</code> method of this file output stream is
     * called when there are no more references to this stream.
     * 清除与文件的连接，并确保当没有对此流的更多引用时，调用此文件输出流的 close方法。
     *
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FileInputStream#close()
     */
    protected void finalize() throws IOException {
        if (fd != null) {
            if (fd == FileDescriptor.out || fd == FileDescriptor.err) {
                flush();
            } else {
                /* if fd is shared, the references in FileDescriptor
                 * will ensure that finalizer is only called when
                 * safe to do so. All references using the fd have
                 * become unreachable. We can call close()
                 */
                close();
            }
        }
    }

    private native void close0() throws IOException;

    private static native void initIDs();

    static {
        initIDs();
    }

}
