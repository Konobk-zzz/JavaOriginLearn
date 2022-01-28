/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Formatter;
import java.util.Locale;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

/**
 * A <code>PrintStream</code> adds functionality to another output stream,
 * namely the ability to print representations of various data values
 * conveniently.  Two other features are provided as well.  Unlike other output
 * streams, a <code>PrintStream</code> never throws an
 * <code>IOException</code>; instead, exceptional situations merely set an
 * internal flag that can be tested via the <code>checkError</code> method.
 * Optionally, a <code>PrintStream</code> can be created so as to flush
 * automatically; this means that the <code>flush</code> method is
 * automatically invoked after a byte array is written, one of the
 * <code>println</code> methods is invoked, or a newline character or byte
 * (<code>'\n'</code>) is written.
 * A PrintStream为另一个输出流添加了功能，即能够方便地打印各种数据值的表示。 还提供了另外两个功能。
 * 与其他输出流不同， PrintStream从不抛出IOException ; 相反，异常情况只是设置一个可以通过checkError方法测试的内部标志。
 * 可以选择一个PrintStream ，以便自动刷新; 这意味着flush字节数组写入方法后自动调用，
 * 所述一个println方法被调用时，或者一个新行字符或字节（ '\n' ）被写入。
 *
 * <p> All characters printed by a <code>PrintStream</code> are converted into
 * bytes using the platform's default character encoding.  The <code>{@link
 * PrintWriter}</code> class should be used in situations that require writing
 * characters rather than bytes.
 * 由印刷的所有字符PrintStream被转换成使用平台的默认字符编码字节。
 * 在需要编写字符而不是字节的情况下，应使用PrintWriter类。
 *
 * @author     Frank Yellin
 * @author     Mark Reinhold
 * @since      JDK1.0
 */

public class PrintStream extends FilterOutputStream
    implements Appendable, Closeable
{

    private final boolean autoFlush;
    private boolean trouble = false;
    private Formatter formatter;

    /**
     * Track both the text- and character-output streams, so that their buffers
     * can be flushed without flushing the entire stream.
     */
    private BufferedWriter textOut;
    private OutputStreamWriter charOut;

    /**
     * requireNonNull is explicitly declared here so as not to create an extra
     * dependency on java.util.Objects.requireNonNull. PrintStream is loaded
     * early during system initialization.
     */
    private static <T> T requireNonNull(T obj, String message) {
        if (obj == null)
            throw new NullPointerException(message);
        return obj;
    }

    /**
     * Returns a charset object for the given charset name.
     * @throws NullPointerException          is csn is null
     * @throws UnsupportedEncodingException  if the charset is not supported
     */
    private static Charset toCharset(String csn)
        throws UnsupportedEncodingException
    {
        requireNonNull(csn, "charsetName");
        try {
            return Charset.forName(csn);
        } catch (IllegalCharsetNameException|UnsupportedCharsetException unused) {
            // UnsupportedEncodingException should be thrown
            throw new UnsupportedEncodingException(csn);
        }
    }

    /* Private constructors */
    private PrintStream(boolean autoFlush, OutputStream out) {
        super(out);
        this.autoFlush = autoFlush;
        this.charOut = new OutputStreamWriter(this);
        this.textOut = new BufferedWriter(charOut);
    }

    private PrintStream(boolean autoFlush, OutputStream out, Charset charset) {
        super(out);
        this.autoFlush = autoFlush;
        this.charOut = new OutputStreamWriter(this, charset);
        this.textOut = new BufferedWriter(charOut);
    }

    /* Variant of the private constructor so that the given charset name
     * can be verified before evaluating the OutputStream argument. Used
     * by constructors creating a FileOutputStream that also take a
     * charset name.
     */
    private PrintStream(boolean autoFlush, Charset charset, OutputStream out)
        throws UnsupportedEncodingException
    {
        this(autoFlush, out, charset);
    }

    /**
     * Creates a new print stream.  This stream will not flush automatically.
     * 创建一个新的打印流。 此流不会自动刷新。
     *
     * @param  out        The output stream to which values and objects will be
     *                    printed
     *
     * @see java.io.PrintWriter#PrintWriter(java.io.OutputStream)
     */
    public PrintStream(OutputStream out) {
        this(out, false);
    }

    /**
     * Creates a new print stream.
     * 创建一个新的打印流。
     *
     * @param  out        The output stream to which values and objects will be
     *                    printed
     * @param  autoFlush  A boolean; if true, the output buffer will be flushed
     *                    whenever a byte array is written, one of the
     *                    <code>println</code> methods is invoked, or a newline
     *                    character or byte (<code>'\n'</code>) is written
     *
     * @see java.io.PrintWriter#PrintWriter(java.io.OutputStream, boolean)
     */
    public PrintStream(OutputStream out, boolean autoFlush) {
        this(autoFlush, requireNonNull(out, "Null output stream"));
    }

    /**
     * Creates a new print stream.
     * 创建一个新的打印流。
     *
     * @param  out        The output stream to which values and objects will be
     *                    printed
     * @param  autoFlush  A boolean; if true, the output buffer will be flushed
     *                    whenever a byte array is written, one of the
     *                    <code>println</code> methods is invoked, or a newline
     *                    character or byte (<code>'\n'</code>) is written
     * @param  encoding   The name of a supported
     *                    <a href="../lang/package-summary.html#charenc">
     *                    character encoding</a>
     *
     * @throws  UnsupportedEncodingException
     *          If the named encoding is not supported
     *
     * @since  1.4
     */
    public PrintStream(OutputStream out, boolean autoFlush, String encoding)
        throws UnsupportedEncodingException
    {
        this(autoFlush,
             requireNonNull(out, "Null output stream"),
             toCharset(encoding));
    }

    /**
     * Creates a new print stream, without automatic line flushing, with the
     * specified file name.  This convenience constructor creates
     * the necessary intermediate {@link java.io.OutputStreamWriter
     * OutputStreamWriter}, which will encode characters using the
     * {@linkplain java.nio.charset.Charset#defaultCharset() default charset}
     * for this instance of the Java virtual machine.
     * 使用指定的文件名创建新的打印流，无需自动换行。 这个方便的构造函数创建必要的中间体OutputStreamWriter ，
     * 它将使用Java虚拟机的这个实例使用default charset对字符进行编码。
     *
     * @param  fileName
     *         The name of the file to use as the destination of this print
     *         stream.  If the file exists, then it will be truncated to
     *         zero size; otherwise, a new file will be created.  The output
     *         will be written to the file and is buffered.
     *
     * @throws  FileNotFoundException
     *          If the given file object does not denote an existing, writable
     *          regular file and a new regular file of that name cannot be
     *          created, or if some other error occurs while opening or
     *          creating the file
     *
     * @throws  SecurityException
     *          If a security manager is present and {@link
     *          SecurityManager#checkWrite checkWrite(fileName)} denies write
     *          access to the file
     *
     * @since  1.5
     */
    public PrintStream(String fileName) throws FileNotFoundException {
        this(false, new FileOutputStream(fileName));
    }

    /**
     * Creates a new print stream, without automatic line flushing, with the
     * specified file name and charset.  This convenience constructor creates
     * the necessary intermediate {@link java.io.OutputStreamWriter
     * OutputStreamWriter}, which will encode characters using the provided
     * charset.
     * 创建一个新的打印流，不需要自动换行，具有指定的文件名和字符集。
     * 这个方便的构造函数创建必要的中间体OutputStreamWriter ，它将使用提供的字符集对字符进行编码。
     *
     * @param  fileName
     *         The name of the file to use as the destination of this print
     *         stream.  If the file exists, then it will be truncated to
     *         zero size; otherwise, a new file will be created.  The output
     *         will be written to the file and is buffered.
     *
     * @param  csn
     *         The name of a supported {@linkplain java.nio.charset.Charset
     *         charset}
     *
     * @throws  FileNotFoundException
     *          If the given file object does not denote an existing, writable
     *          regular file and a new regular file of that name cannot be
     *          created, or if some other error occurs while opening or
     *          creating the file
     *
     * @throws  SecurityException
     *          If a security manager is present and {@link
     *          SecurityManager#checkWrite checkWrite(fileName)} denies write
     *          access to the file
     *
     * @throws  UnsupportedEncodingException
     *          If the named charset is not supported
     *
     * @since  1.5
     */
    public PrintStream(String fileName, String csn)
        throws FileNotFoundException, UnsupportedEncodingException
    {
        // ensure charset is checked before the file is opened
        this(false, toCharset(csn), new FileOutputStream(fileName));
    }

    /**
     * Creates a new print stream, without automatic line flushing, with the
     * specified file.  This convenience constructor creates the necessary
     * intermediate {@link java.io.OutputStreamWriter OutputStreamWriter},
     * which will encode characters using the {@linkplain
     * java.nio.charset.Charset#defaultCharset() default charset} for this
     * instance of the Java virtual machine.
     * 使用指定的文件创建一个新的打印流，而不需要自动换行。 这个方便的构造函数创建必要的中间体OutputStreamWriter ，
     * 它将使用Java虚拟机的这个实例使用default charset对字符进行编码。
     *
     * @param  file
     *         The file to use as the destination of this print stream.  If the
     *         file exists, then it will be truncated to zero size; otherwise,
     *         a new file will be created.  The output will be written to the
     *         file and is buffered.
     *
     * @throws  FileNotFoundException
     *          If the given file object does not denote an existing, writable
     *          regular file and a new regular file of that name cannot be
     *          created, or if some other error occurs while opening or
     *          creating the file
     *
     * @throws  SecurityException
     *          If a security manager is present and {@link
     *          SecurityManager#checkWrite checkWrite(file.getPath())}
     *          denies write access to the file
     *
     * @since  1.5
     */
    public PrintStream(File file) throws FileNotFoundException {
        this(false, new FileOutputStream(file));
    }

    /**
     * Creates a new print stream, without automatic line flushing, with the
     * specified file and charset.  This convenience constructor creates
     * the necessary intermediate {@link java.io.OutputStreamWriter
     * OutputStreamWriter}, which will encode characters using the provided
     * charset.
     * 使用指定的文件和字符集创建新的打印流，而不需要自动换行。
     * 这个方便的构造函数创建必要的中间体OutputStreamWriter ，它将使用提供的字符集对字符进行编码。
     *
     * @param  file
     *         The file to use as the destination of this print stream.  If the
     *         file exists, then it will be truncated to zero size; otherwise,
     *         a new file will be created.  The output will be written to the
     *         file and is buffered.
     *
     * @param  csn
     *         The name of a supported {@linkplain java.nio.charset.Charset
     *         charset}
     *
     * @throws  FileNotFoundException
     *          If the given file object does not denote an existing, writable
     *          regular file and a new regular file of that name cannot be
     *          created, or if some other error occurs while opening or
     *          creating the file
     *
     * @throws  SecurityException
     *          If a security manager is present and {@link
     *          SecurityManager#checkWrite checkWrite(file.getPath())}
     *          denies write access to the file
     *
     * @throws  UnsupportedEncodingException
     *          If the named charset is not supported
     *
     * @since  1.5
     */
    public PrintStream(File file, String csn)
        throws FileNotFoundException, UnsupportedEncodingException
    {
        // ensure charset is checked before the file is opened
        this(false, toCharset(csn), new FileOutputStream(file));
    }

    /** Check to make sure that the stream has not been closed */
    private void ensureOpen() throws IOException {
        if (out == null)
            throw new IOException("Stream closed");
    }

    /**
     * Flushes the stream.  This is done by writing any buffered output bytes to
     * the underlying output stream and then flushing that stream.
     * 刷新流。 这是通过将任何缓冲的输出字节写入底层输出流，然后刷新该流来完成的。
     *
     * @see        java.io.OutputStream#flush()
     */
    public void flush() {
        synchronized (this) {
            try {
                ensureOpen();
                out.flush();
            }
            catch (IOException x) {
                trouble = true;
            }
        }
    }

    private boolean closing = false; /* To avoid recursive closing */

    /**
     * Closes the stream.  This is done by flushing the stream and then closing
     * the underlying output stream.
     * 关闭流。 这是通过刷新流，然后关闭底层输出流来完成的。
     *
     * @see        java.io.OutputStream#close()
     */
    public void close() {
        synchronized (this) {
            if (! closing) {
                closing = true;
                try {
                    textOut.close();
                    out.close();
                }
                catch (IOException x) {
                    trouble = true;
                }
                textOut = null;
                charOut = null;
                out = null;
            }
        }
    }

    /**
     * Flushes the stream and checks its error state. The internal error state
     * is set to <code>true</code> when the underlying output stream throws an
     * <code>IOException</code> other than <code>InterruptedIOException</code>,
     * and when the <code>setError</code> method is invoked.  If an operation
     * on the underlying output stream throws an
     * <code>InterruptedIOException</code>, then the <code>PrintStream</code>
     * converts the exception back into an interrupt by doing:
     * 刷新流并检查其错误状态。 内部错误状态设置为true当底层输出流引发IOException以外的InterruptedIOException ，
     * 当调用setError方法时。 如果底层输出流上的一个InterruptedIOException引发了一个InterruptedIOException ，
     * 那么PrintStream会通过执行以下PrintStream将PrintStream转换为中断：
     * <pre>
     *     Thread.currentThread().interrupt();
     * </pre>
     * or the equivalent.
     *
     * @return <code>true</code> if and only if this stream has encountered an
     *         <code>IOException</code> other than
     *         <code>InterruptedIOException</code>, or the
     *         <code>setError</code> method has been invoked
     */
    public boolean checkError() {
        if (out != null)
            flush();
        if (out instanceof java.io.PrintStream) {
            PrintStream ps = (PrintStream) out;
            return ps.checkError();
        }
        return trouble;
    }

    /**
     * Sets the error state of the stream to <code>true</code>.
     * 将流的错误状态设置为true 。
     *
     * <p> This method will cause subsequent invocations of {@link
     * #checkError()} to return <tt>true</tt> until {@link
     * #clearError()} is invoked.
     * 此方法将导致后续调用checkError()返回true直到clearError()被调用。
     *
     * @since JDK1.1
     */
    protected void setError() {
        trouble = true;
    }

    /**
     * Clears the internal error state of this stream.
     *
     * <p> This method will cause subsequent invocations of {@link
     * #checkError()} to return <tt>false</tt> until another write
     * operation fails and invokes {@link #setError()}.
     *
     * @since 1.6
     */
    protected void clearError() {
        trouble = false;
    }

    /*
     * Exception-catching, synchronized output operations,
     * which also implement the write() methods of OutputStream
     */

    /**
     * Writes the specified byte to this stream.  If the byte is a newline and
     * automatic flushing is enabled then the <code>flush</code> method will be
     * invoked.
     * 将指定的字节写入此流。 如果字节是换行符，并且启用自动刷新，则将调用flush方法。
     *
     * <p> Note that the byte is written as given; to write a character that
     * will be translated according to the platform's default character
     * encoding, use the <code>print(char)</code> or <code>println(char)</code>
     * methods.
     * 请注意，该字节写为给定; 根据平台的默认字符编码编写一个将被翻译的字符，使用print(char)或println(char)方法。
     *
     * @param  b  The byte to be written
     * @see #print(char)
     * @see #println(char)
     */
    public void write(int b) {
        try {
            synchronized (this) {
                ensureOpen();
                out.write(b);
                if ((b == '\n') && autoFlush)
                    out.flush();
            }
        }
        catch (InterruptedIOException x) {
            Thread.currentThread().interrupt();
        }
        catch (IOException x) {
            trouble = true;
        }
    }

    /**
     * Writes <code>len</code> bytes from the specified byte array starting at
     * offset <code>off</code> to this stream.  If automatic flushing is
     * enabled then the <code>flush</code> method will be invoked.
     * 从指定的字节数组写入len字节，从偏移量off开始到此流。 如果启用自动冲洗，则将调用flush方法。
     *
     * <p> Note that the bytes will be written as given; to write characters
     * that will be translated according to the platform's default character
     * encoding, use the <code>print(char)</code> or <code>println(char)</code>
     * methods.
     * 请注意，字节将按照给定的方式写入; 要根据平台的默认字符编码来编写将被翻译的字符，
     * 请使用print(char)或println(char)方法。
     *
     * @param  buf   A byte array
     * @param  off   Offset from which to start taking bytes
     * @param  len   Number of bytes to write
     */
    public void write(byte buf[], int off, int len) {
        try {
            synchronized (this) {
                ensureOpen();
                out.write(buf, off, len);
                if (autoFlush)
                    out.flush();
            }
        }
        catch (InterruptedIOException x) {
            Thread.currentThread().interrupt();
        }
        catch (IOException x) {
            trouble = true;
        }
    }

    /*
     * The following private methods on the text- and character-output streams
     * always flush the stream buffers, so that writes to the underlying byte
     * stream occur as promptly as with the original PrintStream.
     */

    private void write(char buf[]) {
        try {
            synchronized (this) {
                ensureOpen();
                textOut.write(buf);
                textOut.flushBuffer();
                charOut.flushBuffer();
                if (autoFlush) {
                    for (int i = 0; i < buf.length; i++)
                        if (buf[i] == '\n')
                            out.flush();
                }
            }
        }
        catch (InterruptedIOException x) {
            Thread.currentThread().interrupt();
        }
        catch (IOException x) {
            trouble = true;
        }
    }

    private void write(String s) {
        try {
            synchronized (this) {
                ensureOpen();
                textOut.write(s);
                textOut.flushBuffer();
                charOut.flushBuffer();
                if (autoFlush && (s.indexOf('\n') >= 0))
                    out.flush();
            }
        }
        catch (InterruptedIOException x) {
            Thread.currentThread().interrupt();
        }
        catch (IOException x) {
            trouble = true;
        }
    }

    private void newLine() {
        try {
            synchronized (this) {
                ensureOpen();
                textOut.newLine();
                textOut.flushBuffer();
                charOut.flushBuffer();
                if (autoFlush)
                    out.flush();
            }
        }
        catch (InterruptedIOException x) {
            Thread.currentThread().interrupt();
        }
        catch (IOException x) {
            trouble = true;
        }
    }

    /* Methods that do not terminate lines */

    /**
     * Prints a boolean value.  The string produced by <code>{@link
     * java.lang.String#valueOf(boolean)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     * 打印布尔值。 由String.valueOf(boolean)生成的字符串根据平台的默认字符编码被翻译成字节，
     * 这些字节的写法方式完全符合write(int)要求。
     *
     * @param      b   The <code>boolean</code> to be printed
     */
    public void print(boolean b) {
        write(b ? "true" : "false");
    }

    /**
     * Prints a character.  The character is translated into one or more bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     * 打印一个字符 根据平台的默认字符编码，该字符被转换成一个或多个字节，
     * 这些字节的write(int)完全按照write(int)方式编写。
     *
     * @param      c   The <code>char</code> to be printed
     */
    public void print(char c) {
        write(String.valueOf(c));
    }

    /**
     * Prints an integer.  The string produced by <code>{@link
     * java.lang.String#valueOf(int)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     * 打印一个整数。 由String.valueOf(int)生成的字符串根据平台的默认字符编码被翻译成字节，
     * 这些字节是按照write(int)方法的方式编写的。
     *
     * @param      i   The <code>int</code> to be printed
     * @see        java.lang.Integer#toString(int)
     */
    public void print(int i) {
        write(String.valueOf(i));
    }

    /**
     * Prints a long integer.  The string produced by <code>{@link
     * java.lang.String#valueOf(long)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     * 打印一个长整数。 由String.valueOf(long)生成的字符串根据平台的默认字符编码转换为字节，
     * 这些字节按照write(int)的方式写入。
     *
     * @param      l   The <code>long</code> to be printed
     * @see        java.lang.Long#toString(long)
     */
    public void print(long l) {
        write(String.valueOf(l));
    }

    /**
     * Prints a floating-point number.  The string produced by <code>{@link
     * java.lang.String#valueOf(float)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     * 打印浮点数。 由String.valueOf(float)生成的字符串根据平台的默认字符编码被翻译成字节，
     * 这些字节是按照write(int)方法的方式编写的。
     *
     * @param      f   The <code>float</code> to be printed
     * @see        java.lang.Float#toString(float)
     */
    public void print(float f) {
        write(String.valueOf(f));
    }

    /**
     * Prints a double-precision floating-point number.  The string produced by
     * <code>{@link java.lang.String#valueOf(double)}</code> is translated into
     * bytes according to the platform's default character encoding, and these
     * bytes are written in exactly the manner of the <code>{@link
     * #write(int)}</code> method.
     * 打印双精度浮点数。 由String.valueOf(double)生成的字符串根据平台的默认字符编码被翻译成字节，
     * 这些字节按照write(int)的方式写入。
     *
     * @param      d   The <code>double</code> to be printed
     * @see        java.lang.Double#toString(double)
     */
    public void print(double d) {
        write(String.valueOf(d));
    }

    /**
     * Prints an array of characters.  The characters are converted into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     * 打印字符数组。 根据平台的默认字符编码，这些字符被转换为字节，这些字节是以完全符合write(int)方法的方式编写的。
     *
     * @param      s   The array of chars to be printed
     *
     * @throws  NullPointerException  If <code>s</code> is <code>null</code>
     */
    public void print(char s[]) {
        write(s);
    }

    /**
     * Prints a string.  If the argument is <code>null</code> then the string
     * <code>"null"</code> is printed.  Otherwise, the string's characters are
     * converted into bytes according to the platform's default character
     * encoding, and these bytes are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     * 打印字符串。 如果参数是null那么打印字符串"null" 。
     * 否则，字符串的字符将根据平台的默认字符编码转换为字节，并且这些字节以write(int)的方式写入。
     *
     * @param      s   The <code>String</code> to be printed
     */
    public void print(String s) {
        if (s == null) {
            s = "null";
        }
        write(s);
    }

    /**
     * Prints an object.  The string produced by the <code>{@link
     * java.lang.String#valueOf(Object)}</code> method is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     * 打印一个对象。 由String.valueOf(Object)方法生成的字符串根据平台的默认字符编码进行转换为字节，
     * 这些字节的write(int)完全按照write(int)的方式编写。
     *
     * @param      obj   The <code>Object</code> to be printed
     * @see        java.lang.Object#toString()
     */
    public void print(Object obj) {
        write(String.valueOf(obj));
    }


    /* Methods that do terminate lines */

    /**
     * Terminates the current line by writing the line separator string.  The
     * line separator string is defined by the system property
     * <code>line.separator</code>, and is not necessarily a single newline
     * character (<code>'\n'</code>).
     * 通过写入行分隔符字符串来终止当前行。 行分隔符字符串由系统属性line.separator定义，并不一定是单个换行符（ '\n' ）。
     */
    public void println() {
        newLine();
    }

    /**
     * Prints a boolean and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(boolean)}</code> and then
     * <code>{@link #println()}</code>.
     * 打印一个布尔值，然后终止该行。 该方法的行为就像调用print(boolean)然后println() 。
     *
     * @param x  The <code>boolean</code> to be printed
     */
    public void println(boolean x) {
        synchronized (this) {
            print(x);
            newLine();
        }
    }

    /**
     * Prints a character and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(char)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x  The <code>char</code> to be printed.
     */
    public void println(char x) {
        synchronized (this) {
            print(x);
            newLine();
        }
    }

    /**
     * Prints an integer and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(int)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x  The <code>int</code> to be printed.
     */
    public void println(int x) {
        synchronized (this) {
            print(x);
            newLine();
        }
    }

    /**
     * Prints a long and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(long)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x  a The <code>long</code> to be printed.
     */
    public void println(long x) {
        synchronized (this) {
            print(x);
            newLine();
        }
    }

    /**
     * Prints a float and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(float)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x  The <code>float</code> to be printed.
     */
    public void println(float x) {
        synchronized (this) {
            print(x);
            newLine();
        }
    }

    /**
     * Prints a double and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(double)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x  The <code>double</code> to be printed.
     */
    public void println(double x) {
        synchronized (this) {
            print(x);
            newLine();
        }
    }

    /**
     * Prints an array of characters and then terminate the line.  This method
     * behaves as though it invokes <code>{@link #print(char[])}</code> and
     * then <code>{@link #println()}</code>.
     *
     * @param x  an array of chars to print.
     */
    public void println(char x[]) {
        synchronized (this) {
            print(x);
            newLine();
        }
    }

    /**
     * Prints a String and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(String)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x  The <code>String</code> to be printed.
     */
    public void println(String x) {
        synchronized (this) {
            print(x);
            newLine();
        }
    }

    /**
     * Prints an Object and then terminate the line.  This method calls
     * at first String.valueOf(x) to get the printed object's string value,
     * then behaves as
     * though it invokes <code>{@link #print(String)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x  The <code>Object</code> to be printed.
     */
    public void println(Object x) {
        String s = String.valueOf(x);
        synchronized (this) {
            print(s);
            newLine();
        }
    }


    /**
     * A convenience method to write a formatted string to this output stream
     * using the specified format string and arguments.
     * 使用指定的格式字符串和参数将格式化的字符串写入此输出流的便利方法。
     *
     * <p> An invocation of this method of the form <tt>out.printf(format,
     * args)</tt> behaves in exactly the same way as the invocation
     * 调用此方法的形式out.printf(format, args)的行为方式与调用完全相同
     *
     * <pre>
     *     out.format(format, args) </pre>
     *
     * @param  format
     *         A format string as described in <a
     *         href="../util/Formatter.html#syntax">Format string syntax</a>
     *
     * @param  args
     *         Arguments referenced by the format specifiers in the format
     *         string.  If there are more arguments than format specifiers, the
     *         extra arguments are ignored.  The number of arguments is
     *         variable and may be zero.  The maximum number of arguments is
     *         limited by the maximum dimension of a Java array as defined by
     *         <cite>The Java&trade; Virtual Machine Specification</cite>.
     *         The behaviour on a
     *         <tt>null</tt> argument depends on the <a
     *         href="../util/Formatter.html#syntax">conversion</a>.
     *
     * @throws  java.util.IllegalFormatException
     *          If a format string contains an illegal syntax, a format
     *          specifier that is incompatible with the given arguments,
     *          insufficient arguments given the format string, or other
     *          illegal conditions.  For specification of all possible
     *          formatting errors, see the <a
     *          href="../util/Formatter.html#detail">Details</a> section of the
     *          formatter class specification.
     *
     * @throws  NullPointerException
     *          If the <tt>format</tt> is <tt>null</tt>
     *
     * @return  This output stream
     *
     * @since  1.5
     */
    public PrintStream printf(String format, Object ... args) {
        return format(format, args);
    }

    /**
     * A convenience method to write a formatted string to this output stream
     * using the specified format string and arguments.
     * 使用指定的格式字符串和参数将格式化的字符串写入此输出流的便利方法。
     *
     * <p> An invocation of this method of the form <tt>out.printf(l, format,
     * args)</tt> behaves in exactly the same way as the invocation
     * 调用此方法的形式out.printf(l, format, args)的行为方式与调用完全相同
     *
     * <pre>
     *     out.format(l, format, args) </pre>
     *
     * @param  l
     *         The {@linkplain java.util.Locale locale} to apply during
     *         formatting.  If <tt>l</tt> is <tt>null</tt> then no localization
     *         is applied.
     *
     * @param  format
     *         A format string as described in <a
     *         href="../util/Formatter.html#syntax">Format string syntax</a>
     *
     * @param  args
     *         Arguments referenced by the format specifiers in the format
     *         string.  If there are more arguments than format specifiers, the
     *         extra arguments are ignored.  The number of arguments is
     *         variable and may be zero.  The maximum number of arguments is
     *         limited by the maximum dimension of a Java array as defined by
     *         <cite>The Java&trade; Virtual Machine Specification</cite>.
     *         The behaviour on a
     *         <tt>null</tt> argument depends on the <a
     *         href="../util/Formatter.html#syntax">conversion</a>.
     *
     * @throws  java.util.IllegalFormatException
     *          If a format string contains an illegal syntax, a format
     *          specifier that is incompatible with the given arguments,
     *          insufficient arguments given the format string, or other
     *          illegal conditions.  For specification of all possible
     *          formatting errors, see the <a
     *          href="../util/Formatter.html#detail">Details</a> section of the
     *          formatter class specification.
     *
     * @throws  NullPointerException
     *          If the <tt>format</tt> is <tt>null</tt>
     *
     * @return  This output stream
     *
     * @since  1.5
     */
    public PrintStream printf(Locale l, String format, Object ... args) {
        return format(l, format, args);
    }

    /**
     * Writes a formatted string to this output stream using the specified
     * format string and arguments.
     * 使用指定的格式字符串和参数将格式化的字符串写入此输出流。
     *
     * <p> The locale always used is the one returned by {@link
     * java.util.Locale#getDefault() Locale.getDefault()}, regardless of any
     * previous invocations of other formatting methods on this object.
     * 始终使用的区域设置是由Locale.getDefault()返回的区域设置，无论以前在此对象上调用其他格式化方法。
     *
     * @param  format
     *         A format string as described in <a
     *         href="../util/Formatter.html#syntax">Format string syntax</a>
     *
     * @param  args
     *         Arguments referenced by the format specifiers in the format
     *         string.  If there are more arguments than format specifiers, the
     *         extra arguments are ignored.  The number of arguments is
     *         variable and may be zero.  The maximum number of arguments is
     *         limited by the maximum dimension of a Java array as defined by
     *         <cite>The Java&trade; Virtual Machine Specification</cite>.
     *         The behaviour on a
     *         <tt>null</tt> argument depends on the <a
     *         href="../util/Formatter.html#syntax">conversion</a>.
     *
     * @throws  java.util.IllegalFormatException
     *          If a format string contains an illegal syntax, a format
     *          specifier that is incompatible with the given arguments,
     *          insufficient arguments given the format string, or other
     *          illegal conditions.  For specification of all possible
     *          formatting errors, see the <a
     *          href="../util/Formatter.html#detail">Details</a> section of the
     *          formatter class specification.
     *
     * @throws  NullPointerException
     *          If the <tt>format</tt> is <tt>null</tt>
     *
     * @return  This output stream
     *
     * @since  1.5
     */
    public PrintStream format(String format, Object ... args) {
        try {
            synchronized (this) {
                ensureOpen();
                if ((formatter == null)
                    || (formatter.locale() != Locale.getDefault()))
                    formatter = new Formatter((Appendable) this);
                formatter.format(Locale.getDefault(), format, args);
            }
        } catch (InterruptedIOException x) {
            Thread.currentThread().interrupt();
        } catch (IOException x) {
            trouble = true;
        }
        return this;
    }

    /**
     * Writes a formatted string to this output stream using the specified
     * format string and arguments.
     * 使用指定的格式字符串和参数将格式化的字符串写入此输出流。
     *
     * @param  l
     *         The {@linkplain java.util.Locale locale} to apply during
     *         formatting.  If <tt>l</tt> is <tt>null</tt> then no localization
     *         is applied.
     *
     * @param  format
     *         A format string as described in <a
     *         href="../util/Formatter.html#syntax">Format string syntax</a>
     *
     * @param  args
     *         Arguments referenced by the format specifiers in the format
     *         string.  If there are more arguments than format specifiers, the
     *         extra arguments are ignored.  The number of arguments is
     *         variable and may be zero.  The maximum number of arguments is
     *         limited by the maximum dimension of a Java array as defined by
     *         <cite>The Java&trade; Virtual Machine Specification</cite>.
     *         The behaviour on a
     *         <tt>null</tt> argument depends on the <a
     *         href="../util/Formatter.html#syntax">conversion</a>.
     *
     * @throws  java.util.IllegalFormatException
     *          If a format string contains an illegal syntax, a format
     *          specifier that is incompatible with the given arguments,
     *          insufficient arguments given the format string, or other
     *          illegal conditions.  For specification of all possible
     *          formatting errors, see the <a
     *          href="../util/Formatter.html#detail">Details</a> section of the
     *          formatter class specification.
     *
     * @throws  NullPointerException
     *          If the <tt>format</tt> is <tt>null</tt>
     *
     * @return  This output stream
     *
     * @since  1.5
     */
    public PrintStream format(Locale l, String format, Object ... args) {
        try {
            synchronized (this) {
                ensureOpen();
                if ((formatter == null)
                    || (formatter.locale() != l))
                    formatter = new Formatter(this, l);
                formatter.format(l, format, args);
            }
        } catch (InterruptedIOException x) {
            Thread.currentThread().interrupt();
        } catch (IOException x) {
            trouble = true;
        }
        return this;
    }

    /**
     * Appends the specified character sequence to this output stream.
     * 将指定的字符序列附加到此输出流。
     *
     * <p> An invocation of this method of the form <tt>out.append(csq)</tt>
     * behaves in exactly the same way as the invocation
     * 调用此方法的形式out.append(csq)的行为方式与调用完全相同
     *
     * <pre>
     *     out.print(csq.toString()) </pre>
     *
     * <p> Depending on the specification of <tt>toString</tt> for the
     * character sequence <tt>csq</tt>, the entire sequence may not be
     * appended.  For instance, invoking then <tt>toString</tt> method of a
     * character buffer will return a subsequence whose content depends upon
     * the buffer's position and limit.
     * 取决于toString字符序列csq本说明书中，整个序列可以不追加。
     * 例如，然后调用一个字符缓冲区的toString方法将返回一个序列，其内容取决于缓冲区的位置和限制。
     *
     * @param  csq
     *         The character sequence to append.  If <tt>csq</tt> is
     *         <tt>null</tt>, then the four characters <tt>"null"</tt> are
     *         appended to this output stream.
     *
     * @return  This output stream
     *
     * @since  1.5
     */
    public PrintStream append(CharSequence csq) {
        if (csq == null)
            print("null");
        else
            print(csq.toString());
        return this;
    }

    /**
     * Appends a subsequence of the specified character sequence to this output
     * stream.
     * 将指定字符序列的子序列附加到此输出流。
     *
     * <p> An invocation of this method of the form <tt>out.append(csq, start,
     * end)</tt> when <tt>csq</tt> is not <tt>null</tt>, behaves in
     * exactly the same way as the invocation
     * 形式的这种方法的调用时out.append(csq, start, end) csq不是null，行为以完全相同的方式调用
     *
     * <pre>
     *     out.print(csq.subSequence(start, end).toString()) </pre>
     *
     * @param  csq
     *         The character sequence from which a subsequence will be
     *         appended.  If <tt>csq</tt> is <tt>null</tt>, then characters
     *         will be appended as if <tt>csq</tt> contained the four
     *         characters <tt>"null"</tt>.
     *
     * @param  start
     *         The index of the first character in the subsequence
     *
     * @param  end
     *         The index of the character following the last character in the
     *         subsequence
     *
     * @return  This output stream
     *
     * @throws  IndexOutOfBoundsException
     *          If <tt>start</tt> or <tt>end</tt> are negative, <tt>start</tt>
     *          is greater than <tt>end</tt>, or <tt>end</tt> is greater than
     *          <tt>csq.length()</tt>
     *
     * @since  1.5
     */
    public PrintStream append(CharSequence csq, int start, int end) {
        CharSequence cs = (csq == null ? "null" : csq);
        write(cs.subSequence(start, end).toString());
        return this;
    }

    /**
     * Appends the specified character to this output stream.
     * 将指定的字符附加到此输出流。
     *
     * <p> An invocation of this method of the form <tt>out.append(c)</tt>
     * behaves in exactly the same way as the invocation
     * 调用此方法的形式out.append(c)的行为方式与调用完全相同
     *
     * <pre>
     *     out.print(c) </pre>
     *
     * @param  c
     *         The 16-bit character to append
     *
     * @return  This output stream
     *
     * @since  1.5
     */
    public PrintStream append(char c) {
        print(c);
        return this;
    }

}
