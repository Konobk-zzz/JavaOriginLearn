/*
 * Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved.
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

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of the file descriptor class serve as an opaque handle
 * to the underlying machine-specific structure representing an
 * open file, an open socket, or another source or sink of bytes.
 * The main practical use for a file descriptor is to create a
 * {@link FileInputStream} or {@link FileOutputStream} to contain it.
 * 一个文件描述符的实例作为一个不透明的服务用于屏蔽底层机器特殊结构代表的一个打开的文件，
 * 一个打开的套接字，或者其他的源或底层的字节。主要的用途是作为文件描述符来创建一个
 * FileInputStream 或 FileOutputStream，创建这两个实例需要文件描述符。
 *
 * <p>Applications should not create their own file descriptors.
 * 应用不应该创建他们自己的文件描述符。
 *
 * @author  Pavani Diwanji
 * @since   JDK1.0
 */
public final class FileDescriptor {

    private int fd;

    private long handle;

    private Closeable parent;
    private List<Closeable> otherParents;
    private boolean closed;

    /**
     * Constructs an (invalid) FileDescriptor
     * object.
     */
    public /**/ FileDescriptor() {
        fd = -1;
        handle = -1;
    }

    static {
        initIDs();
    }

    // Set up JavaIOFileDescriptorAccess in SharedSecrets
    static {
        sun.misc.SharedSecrets.setJavaIOFileDescriptorAccess(
            new sun.misc.JavaIOFileDescriptorAccess() {
                public void set(FileDescriptor obj, int fd) {
                    obj.fd = fd;
                }

                public int get(FileDescriptor obj) {
                    return obj.fd;
                }

                public void setHandle(FileDescriptor obj, long handle) {
                    obj.handle = handle;
                }

                public long getHandle(FileDescriptor obj) {
                    return obj.handle;
                }
            }
        );
    }

    /**
     * A handle to the standard input stream. Usually, this file
     * descriptor is not used directly, but rather via the input stream
     * known as {@code System.in}.
     * 处理标准输入流。通常，文件描述符不是直接被使用，而是通过输入流 System.in
     *
     * @see     java.lang.System#in
     */
    public static final FileDescriptor in = standardStream(0);

    /**
     * A handle to the standard output stream. Usually, this file
     * descriptor is not used directly, but rather via the output stream
     * known as {@code System.out}.
     * 处理标准输出流。通常文件描述符不是直接被使用，而是通过输出流 System.out
     * @see     java.lang.System#out
     */
    public static final FileDescriptor out = standardStream(1);

    /**
     * A handle to the standard error stream. Usually, this file
     * descriptor is not used directly, but rather via the output stream
     * known as {@code System.err}.
     * 处理标准错误流。通常，文件描述符不是直接被使用，而是通过输出流 System.err
     *
     * @see     java.lang.System#err
     */
    public static final FileDescriptor err = standardStream(2);

    /**
     * Tests if this file descriptor object is valid.
     * 测试该文件描述符是否是有效的。
     *
     * @return  {@code true} if the file descriptor object represents a
     *          valid, open file, socket, or other active I/O connection;
     *          {@code false} otherwise.
     */
    public boolean valid() {
        return ((handle != -1) || (fd != -1));
    }

    /**
     * Force all system buffers to synchronize with the underlying
     * device.  This method returns after all modified data and
     * attributes of this FileDescriptor have been written to the
     * relevant device(s).  In particular, if this FileDescriptor
     * refers to a physical storage medium, such as a file in a file
     * system, sync will not return until all in-memory modified copies
     * of buffers associated with this FileDesecriptor have been
     * written to the physical medium.
     * 通过底层设备强制所有系统缓存同步。该方法将会在该文件描述符修改的所有数据和属性
     * 都写到相关的设备中后返回。在实践中，如果该文件迷哦奥数夫引用实际的物理存储媒介，
     * 类如文件系统中的文件，同步将不会返回直到所有该文件描述符在内存缓存中的修改都
     * 被写入到实际的物理存储媒介中。
     *
     * sync is meant to be used by code that requires physical
     * storage (such as a file) to be in a known state  For
     * example, a class that provided a simple transaction facility
     * might use sync to ensure that all changes to a file caused
     * by a given transaction were recorded on a storage medium.
     * 同步意味着通过使用代码来请求处于一直状态的物理存储（例如一个文件）。例如，
     * 一个类提供了简单的事务，可能使用同步来确保所有的改动写入到文件，因此
     * 给定事务会被记录到存储媒介中。
     *
     * sync only affects buffers downstream of this FileDescriptor.  If
     * any in-memory buffering is being done by the application (for
     * example, by a BufferedOutputStream object), those buffers must
     * be flushed into the FileDescriptor (for example, by invoking
     * OutputStream.flush) before that data will be affected by sync.
     * 该文件描述符的同步值影响缓存下游。如果该应用的任何一个内存型缓存正在被使用（
     * 例如，通过一个 BufferedOutputStream 对象），这些缓存必须被刷新到文件描述符
     * （例如，通过请求 OutputStream.flush）之前的数据将会被同步影响。
     *
     * @exception SyncFailedException
     *        Thrown when the buffers cannot be flushed,
     *        or because the system cannot guarantee that all the
     *        buffers have been synchronized with physical media.
     *        当缓存不能被刷出，或因为系统不能保证所有缓存都能以同步的方式刷到物理媒介。
     * @since     JDK1.1
     */
    public native void sync() throws SyncFailedException;

    /* This routine initializes JNI field offsets for the class */
    private static native void initIDs();

    private static native long set(int d);

    private static FileDescriptor standardStream(int fd) {
        FileDescriptor desc = new FileDescriptor();
        desc.handle = set(fd);
        return desc;
    }

    /*
     * Package private methods to track referents.
     * If multiple streams point to the same FileDescriptor, we cycle
     * through the list of all referents and call close()
     */

    /**
     * Attach a Closeable to this FD for tracking.
     * parent reference is added to otherParents when
     * needed to make closeAll simpler.
     * 将一个Closeable对象附加到文件描述符的追踪中。
     * 父引用将会被添加到 otherParents 中当需要关闭所有时更简单。
     */
    synchronized void attach(Closeable c) {
        if (parent == null) {
            // first caller gets to do this
            parent = c;
        } else if (otherParents == null) {
            otherParents = new ArrayList<>();
            otherParents.add(parent);
            otherParents.add(c);
        } else {
            otherParents.add(c);
        }
    }

    /**
     * Cycle through all Closeables sharing this FD and call
     * close() on each one.
     * 所有 Closeables 共享这个文件描述符并且遍历调用 close() 方法
     *
     * The caller closeable gets to call close0().
     */
    @SuppressWarnings("try")
    synchronized void closeAll(Closeable releaser) throws IOException {
        if (!closed) {
            closed = true;
            IOException ioe = null;
            try (Closeable c = releaser) {
                if (otherParents != null) {
                    for (Closeable referent : otherParents) {
                        try {
                            referent.close();
                        } catch(IOException x) {
                            if (ioe == null) {
                                ioe = x;
                            } else {
                                ioe.addSuppressed(x);
                            }
                        }
                    }
                }
            } catch(IOException ex) {
                /*
                 * If releaser close() throws IOException
                 * add other exceptions as suppressed.
                 */
                if (ioe != null)
                    ex.addSuppressed(ioe);
                ioe = ex;
            } finally {
                if (ioe != null)
                    throw ioe;
            }
        }
    }
}
