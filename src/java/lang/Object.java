/*
 * Copyright (c) 1994, 2012, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/**
 * Class {@code Object} is the root of the class hierarchy.
 * Every class has {@code Object} as a superclass. All objects,
 * including arrays, implement the methods of this class.
 *
 * @author  unascribed
 * @see     java.lang.Class
 * @since   JDK1.0
 */
public class Object {

    private static native void registerNatives();
    static {
        registerNatives();
    }

    /**
     * Returns the runtime class of this {@code Object}. The returned
     * {@code Class} object is the object that is locked by {@code
     * static synchronized} methods of the represented class.
     *
     * <p><b>The actual result type is {@code Class<? extends |X|>}
     * where {@code |X|} is the erasure of the static type of the
     * expression on which {@code getClass} is called.</b> For
     * example, no cast is required in this code fragment:</p>
     *
     * <p>
     * {@code Number n = 0;                             }<br>
     * {@code Class<? extends Number> c = n.getClass(); }
     * </p>
     *
     * @return The {@code Class} object that represents the runtime
     *         class of this object.
     * @jls 15.8.2 Class Literals
     */
    public final native Class<?> getClass();

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link java.util.HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     *     an execution of a Java application, the {@code hashCode} method
     *     must consistently return the same integer, provided no information
     *     used in {@code equals} comparisons on the object is modified.
     *     This integer need not remain consistent from one execution of an
     *     application to another execution of the same application.
     *     无论何时在同一个Java应用中调用多次该方法都必须返回一致的integer，在对象修改时
     *     不提供信息用于比较。这个integer返回值在相同的镜像应用调用不需要保持一致。
     * <li>If two objects are equal according to the {@code equals(Object)}
     *     method, then calling the {@code hashCode} method on each of
     *     the two objects must produce the same integer result.
     *     如果两个对象通过 equals(Object) 方法返回是一致的，那么这两个对象调用 hashCode
     *     方法 必须产生一致的integer结果。
     * <li>It is <em>not</em> required that if two objects are unequal
     *     according to the {@link java.lang.Object#equals(java.lang.Object)}
     *     method, then calling the {@code hashCode} method on each of the
     *     two objects must produce distinct integer results.  However, the
     *     programmer should be aware that producing distinct integer results
     *     for unequal objects may improve the performance of hash tables.
     *     如果两个对象通过 equals() 方法被判断为不相等，那么这两个对象调用hashCode必须
     *     产生不同的integer结果。然而，开发者需要意识到为不同的对象产生不同的integer结果
     *     可能会提升 hash tables 的性能。
     * </ul>
     * <p>
     * As much as is reasonably practical, the hashCode method defined by
     * class {@code Object} does return distinct integers for distinct
     * objects. (This is typically implemented by converting the internal
     * address of the object into an integer, but this implementation
     * technique is not required by the
     * Java&trade; programming language.)
     * 因为很合理实用，所以 hashCode 方法定义在 Object中为不同的对象返回不同的integer
     * 结果。（一个典型的实现是将对象内部地址转换为integer，但是这个实现技巧在Java中不是必须的）
     *
     * @return  a hash code value for this object.
     * @see     java.lang.Object#equals(java.lang.Object)
     * @see     java.lang.System#identityHashCode
     */
    public native int hashCode();

    /**
     * Indicates whether some other object is "equal to" this one.
     * 指明其他对象和当前对象是否一致。
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * 该方法实现一个等价关系在一个非空的参考对象上：
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     *     {@code x}, {@code x.equals(x)} should return
     *     {@code true}.
     *     具有 自反性 ：对任意一个非空参考对象 x.equals(x) 应该返回 true
     * <li>It is <i>symmetric</i>: for any non-null reference values
     *     {@code x} and {@code y}, {@code x.equals(y)}
     *     should return {@code true} if and only if
     *     {@code y.equals(x)} returns {@code true}.
     *     具有 对称性：对任意非空对象 x 和 y ， 如果 x.equals(y)返回true 那么
     *     y.equals(x) 也返回true
     * <li>It is <i>transitive</i>: for any non-null reference values
     *     {@code x}, {@code y}, and {@code z}, if
     *     {@code x.equals(y)} returns {@code true} and
     *     {@code y.equals(z)} returns {@code true}, then
     *     {@code x.equals(z)} should return {@code true}.
     *     具有 传递性： 对任意非空对象 x,y,z 如果 x.equals(y) 返回 true 且
     *     y.equals(z) 返回true 那么 x.equals(z) 也应该返回 true
     * <li>It is <i>consistent</i>: for any non-null reference values
     *     {@code x} and {@code y}, multiple invocations of
     *     {@code x.equals(y)} consistently return {@code true}
     *     or consistently return {@code false}, provided no
     *     information used in {@code equals} comparisons on the
     *     objects is modified.
     *     具有 一致性： 对任意非空的引用值 x,y 多次调用 x.equals(y) 应一致
     *     返回 true 或 false ，在对象修改后的比较中不提供信息。
     * <li>For any non-null reference value {@code x},
     *     {@code x.equals(null)} should return {@code false}.
     *     对任意 非空引用对象 x ， x.equals(null) 应该返回 false.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     * 该方法为类实现最具有识别对象可能等值关系的能力。方法为任意非空引用对象 x,y 返回true
     * 当且仅当 x,y 引用相同的对象 （x == y 有值 true）
     *
     * @param   obj   the reference object with which to compare.
     * @return  {@code true} if this object is the same as the obj
     *          argument; {@code false} otherwise.
     * @see     #hashCode()
     * @see     java.util.HashMap
     */
    public boolean equals(Object obj) {
        return (this == obj);
    }

    /**
     * Creates and returns a copy of this object.  The precise meaning
     * of "copy" may depend on the class of the object. The general
     * intent is that, for any object {@code x}, the expression:
     * 创建并返回当前对象的复制对象。 严格意义上的 “复制” 可能依赖于类组成对象。 通常
     * 的意义是 为了任何一个对象
     * <blockquote>
     * <pre>
     * x.clone() != x</pre></blockquote>
     * will be true, and that the expression:
     * <blockquote>
     * <pre>
     * x.clone().getClass() == x.getClass()</pre></blockquote>
     * will be {@code true}, but these are not absolute requirements.
     * While it is typically the case that:
     * <blockquote>
     * <pre>
     * x.clone().equals(x)</pre></blockquote>
     * will be {@code true}, this is not an absolute requirement.
     * <p>
     * By convention, the returned object should be obtained by calling
     * {@code super.clone}.  If a class and all of its superclasses (except
     * {@code Object}) obey this convention, it will be the case that
     * {@code x.clone().getClass() == x.getClass()}.
     * 按照惯例，返回的对象应该通过调用 super.clone 获取。 如果一个类和他的所有父类（除了Object）
     * 遵循这个惯例，将会产生一下场景 x.clone().getClass() == x.getClass()
     * <p>
     * By convention, the object returned by this method should be independent
     * of this object (which is being cloned).  To achieve this independence,
     * it may be necessary to modify one or more fields of the object returned
     * by {@code super.clone} before returning it.  Typically, this means
     * copying any mutable objects that comprise the internal "deep structure"
     * of the object being cloned and replacing the references to these
     * objects with references to the copies.  If a class contains only
     * primitive fields or references to immutable objects, then it is usually
     * the case that no fields in the object returned by {@code super.clone}
     * need to be modified.
     * 按照惯例，通过这个方法返回的对象应当是独立与当前对象的。 为了在返回前取得这样的独立 可能必须修改
     * 一个或多个从 super.clone 来的字段。典型的是这意味这复制任意一个对象变量包含内部 “深度构建”
     * 为了对象被克隆且为复制对象替换这些对象的引用。如果一个类只存在私有字段 或 应用不可变对象， 一个
     * 常见的场景是 super.clone 这个对象没有返回字段需要被修改。
     * <p>
     * The method {@code clone} for class {@code Object} performs a
     * specific cloning operation. First, if the class of this object does
     * not implement the interface {@code Cloneable}, then a
     * {@code CloneNotSupportedException} is thrown. Note that all arrays
     * are considered to implement the interface {@code Cloneable} and that
     * the return type of the {@code clone} method of an array type {@code T[]}
     * is {@code T[]} where T is any reference or primitive type.
     * Otherwise, this method creates a new instance of the class of this
     * object and initializes all its fields with exactly the contents of
     * the corresponding fields of this object, as if by assignment; the
     * contents of the fields are not themselves cloned. Thus, this method
     * performs a "shallow copy" of this object, not a "deep copy" operation.
     * 该方法为类（Object）执行一个特殊的克隆操作。首先，如果该类的对象没有实现 Cloneable 抽象，
     * 会抛出一个 CloneNotSupportedException 异常。注意所有的数组，他们都被经过深思熟虑的实现了
     * Cloneable 抽象 并且为 数组类型的 clone 方法 返回类型，在 T 是引用或私有类型时。
     * 否则，该方法为该类该对象创建一个新的实例 并 初始化他的所有字段 和正确的容器 为了这个对象一致的字段，
     * 好似分配；这些字段的容器并不是他们自身的克隆。因此，该方法为对象执行 “浅复制”，而不是 “深复制” 操作
     * <p>
     * The class {@code Object} does not itself implement the interface
     * {@code Cloneable}, so calling the {@code clone} method on an object
     * whose class is {@code Object} will result in throwing an
     * exception at run time.
     * 该类（Object）自身没有实现 Cloneable 抽象， 所以调用 clone 方法在一个Object对象
     * 上将会抛出一个运行时异常。
     *
     * @return     a clone of this instance.
     * @throws  CloneNotSupportedException  if the object's class does not
     *               support the {@code Cloneable} interface. Subclasses
     *               that override the {@code clone} method can also
     *               throw this exception to indicate that an instance cannot
     *               be cloned.
     * @see java.lang.Cloneable
     */
    protected native Object clone() throws CloneNotSupportedException;

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * 返回一个字符串来描述这个对象。通常，该方法返回一个字符串以文本的形式描述这个对象。
     * 结果应当简洁且表达的内容易于人们理解。
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return  a string representation of the object.
     */
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

    /**
     * Wakes up a single thread that is waiting on this object's
     * monitor. If any threads are waiting on this object, one of them
     * is chosen to be awakened. The choice is arbitrary and occurs at
     * the discretion of the implementation. A thread waits on an object's
     * monitor by calling one of the {@code wait} methods.
     * 唤醒单独一个正在等待对象监控器（对象锁）的线程。如果如果有多个线程在等待当前对象，
     * 则他们中的一个会被选中唤醒。这个选择是随意的。一个线程等待一个对象上的监控器通过
     * 调用 wait 方法。
     * <p>
     * The awakened thread will not be able to proceed until the current
     * thread relinquishes the lock on this object. The awakened thread will
     * compete in the usual manner with any other threads that might be
     * actively competing to synchronize on this object; for example, the
     * awakened thread enjoys no reliable privilege or disadvantage in being
     * the next thread to lock this object.
     * 一个被唤醒的线程将不会继续执行，直到当前线程放弃这个对象锁。被唤醒的线程将会和其他线程
     * 以通常的方式进行竞争，可能会活跃的竞争在这个对象上同步的机会；举一个例子，被唤醒的线程
     * 没有享受任何优势或劣势在竞争对象锁上。
     * <p>
     * This method should only be called by a thread that is the owner
     * of this object's monitor. A thread becomes the owner of the
     * object's monitor in one of three ways:
     * 这个方法只应该被一个拥有当前对象锁的线程调用。一个线程获取到对象锁有一下三种方式：
     * <ul>
     * <li>By executing a synchronized instance method of that object.
     *     通过执行该对象的一个同步的实例方法
     * <li>By executing the body of a {@code synchronized} statement
     *     that synchronizes on the object.
     *     通过执行 对象中 标注 synchronizes 的代码块
     * <li>For objects of type {@code Class,} by executing a
     *     synchronized static method of that class.
     *     通过执行标注了 synchronizes 的静态方法 （类对象锁）
     * </ul>
     * <p>
     * Only one thread at a time can own an object's monitor.
     * 同时只能有一个线程获取一个对象锁
     *
     * @throws  IllegalMonitorStateException  if the current thread is not
     *               the owner of this object's monitor.
     * @see        java.lang.Object#notifyAll()
     * @see        java.lang.Object#wait()
     */
    public final native void notify();

    /**
     * Wakes up all threads that are waiting on this object's monitor. A
     * thread waits on an object's monitor by calling one of the
     * {@code wait} methods.
     * 唤醒所有正在等待当前对象监控器（对象锁）的线程。一个线程等待一个对象锁可以调用
     * wait 方法中的一个。
     * <p>
     * The awakened threads will not be able to proceed until the current
     * thread relinquishes the lock on this object. The awakened threads
     * will compete in the usual manner with any other threads that might
     * be actively competing to synchronize on this object; for example,
     * the awakened threads enjoy no reliable privilege or disadvantage in
     * being the next thread to lock this object.
     * <p>
     * This method should only be called by a thread that is the owner
     * of this object's monitor. See the {@code notify} method for a
     * description of the ways in which a thread can become the owner of
     * a monitor.
     *
     * @throws  IllegalMonitorStateException  if the current thread is not
     *               the owner of this object's monitor.
     * @see        java.lang.Object#notify()
     * @see        java.lang.Object#wait()
     */
    public final native void notifyAll();

    /**
     * Causes the current thread to wait until either another thread invokes the
     * {@link java.lang.Object#notify()} method or the
     * {@link java.lang.Object#notifyAll()} method for this object, or a
     * specified amount of time has elapsed.
     * 致使当前线程等待 直到其他线程调用该对象的 notify() 或 notifyAll() 方法，
     * 或者超时
     * <p>
     * The current thread must own this object's monitor.
     * 当前线程必须拥有当前对象的监控器（对象锁）
     * <p>
     * This method causes the current thread (call it <var>T</var>) to
     * place itself in the wait set for this object and then to relinquish
     * any and all synchronization claims on this object. Thread <var>T</var>
     * becomes disabled for thread scheduling purposes and lies dormant
     * until one of four things happens:
     * 该方法会导致当前线程将其自身放置到当前对象的 wait set 中，然后释放当前对象上所有
     * 的同步要求。该线程变为不可用于线程调度目的 并且 进入睡眠状态直到一下四种情况发生：
     * <ul>
     * <li>Some other thread invokes the {@code notify} method for this
     * object and thread <var>T</var> happens to be arbitrarily chosen as
     * the thread to be awakened.
     * 其他线程调用当前对象的 notify 方法 并且正好被随机的选中唤醒
     * <li>Some other thread invokes the {@code notifyAll} method for this
     * object.
     * 其他线程调用当前对象上的 notifyAll 方法
     * <li>Some other thread {@linkplain Thread#interrupt() interrupts}
     * thread <var>T</var>.
     * 其他线程调用 interrupts() 方法打断当前线程。
     * <li>The specified amount of real time has elapsed, more or less.  If
     * {@code timeout} is zero, however, then real time is not taken into
     * consideration and the thread simply waits until notified.
     * 超过被指定的时间，多或少。 如果 timeout 是 0 ，然而 真实时间不是需要考虑的，
     * 线程简单的等待直到被唤醒。
     * </ul>
     * The thread <var>T</var> is then removed from the wait set for this
     * object and re-enabled for thread scheduling. It then competes in the
     * usual manner with other threads for the right to synchronize on the
     * object; once it has gained control of the object, all its
     * synchronization claims on the object are restored to the status quo
     * ante - that is, to the situation as of the time that the {@code wait}
     * method was invoked. Thread <var>T</var> then returns from the
     * invocation of the {@code wait} method. Thus, on return from the
     * {@code wait} method, the synchronization state of the object and of
     * thread {@code T} is exactly as it was when the {@code wait} method
     * was invoked.
     * 当前线程被移出当前对象的 wait set 并且重新可以被线程调度。然后以通常的方法和其他线程
     * 竞争对象同步的许可；当获取对象控制的时候，所有当前对象的同步许可状态被存储为 quo ante
     *  - 这是，作为标记 wait 方法被调用。线程从 wait 方法返回。因此， 当从 wait 方法返回
     *  时 当前对象的同步状态 和 线程 是 exactly 作为 wait 方法已调用。
     * <p>
     * A thread can also wake up without being notified, interrupted, or
     * timing out, a so-called <i>spurious wakeup</i>.  While this will rarely
     * occur in practice, applications must guard against it by testing for
     * the condition that should have caused the thread to be awakened, and
     * continuing to wait if the condition is not satisfied.  In other words,
     * waits should always occur in loops, like this one:
     * 一个线程也可能会在不是 notified,interrupted,timing out 的情况下被唤醒，这也称为
     * 假唤醒。 在之间场景中很少出现， 应用必须警惕这一点，通过测试条件是否满足来防止线程被意外唤醒，
     * 如果条件不满足则继续 wait 。换句话说 等待总是应该在循环中，就像下面这样：
     * <pre>
     *     synchronized (obj) {
     *         while (&lt;condition does not hold&gt;)
     *             obj.wait(timeout);
     *         ... // Perform action appropriate to condition
     *     }
     * </pre>
     * (For more information on this topic, see Section 3.2.3 in Doug Lea's
     * "Concurrent Programming in Java (Second Edition)" (Addison-Wesley,
     * 2000), or Item 50 in Joshua Bloch's "Effective Java Programming
     * Language Guide" (Addison-Wesley, 2001).
     *
     * <p>If the current thread is {@linkplain java.lang.Thread#interrupt()
     * interrupted} by any thread before or while it is waiting, then an
     * {@code InterruptedException} is thrown.  This exception is not
     * thrown until the lock status of this object has been restored as
     * described above.
     * 如果当前线程在 wait 之前或之中被 其他线程调用 interrupt() 方法打断，将会抛出
     * InterruptedException 异常。 直到当对象锁状态被恢复到上述状态。
     * <p>
     * Note that the {@code wait} method, as it places the current thread
     * into the wait set for this object, unlocks only this object; any
     * other objects on which the current thread may be synchronized remain
     * locked while the thread waits.
     * 注意 wait 方法，放置当前线程到当前线程的 wait set ，只解锁当前对象；任何其他的对象
     * 对于当前线程来说任然可能是上锁的状态在此期间线程需要等待。
     * <p>
     * This method should only be called by a thread that is the owner
     * of this object's monitor. See the {@code notify} method for a
     * description of the ways in which a thread can become the owner of
     * a monitor.
     * 该方法只应该被拥有当前对象监视器的线程调用。 查看 notify 方法获取更多描述线程
     * 如何成为监视器的拥有者。
     *
     * @param      timeout   the maximum time to wait in milliseconds.
     * @throws  IllegalArgumentException      if the value of timeout is
     *               negative.
     * @throws  IllegalMonitorStateException  if the current thread is not
     *               the owner of the object's monitor.
     * @throws  InterruptedException if any thread interrupted the
     *             current thread before or while the current thread
     *             was waiting for a notification.  The <i>interrupted
     *             status</i> of the current thread is cleared when
     *             this exception is thrown.
     * @see        java.lang.Object#notify()
     * @see        java.lang.Object#notifyAll()
     */
    public final native void wait(long timeout) throws InterruptedException;

    /**
     * Causes the current thread to wait until another thread invokes the
     * {@link java.lang.Object#notify()} method or the
     * {@link java.lang.Object#notifyAll()} method for this object, or
     * some other thread interrupts the current thread, or a certain
     * amount of real time has elapsed.
     * <p>
     * This method is similar to the {@code wait} method of one
     * argument, but it allows finer control over the amount of time to
     * wait for a notification before giving up. The amount of real time,
     * measured in nanoseconds, is given by:
     * 该方法很像是 一个参数的wait方法 但是该方法允许更好的控制等待唤醒的超时时间。
     * 真实时间的测量标准是纳秒
     * <blockquote>
     * <pre>
     * 1000000*timeout+nanos</pre></blockquote>
     * <p>
     * In all other respects, this method does the same thing as the
     * method {@link #wait(long)} of one argument. In particular,
     * {@code wait(0, 0)} means the same thing as {@code wait(0)}.
     * 在所有其他方面，该方法和 wait(long) 方法做的事情是一样的。
     * wait(0, 0) 等同于 wait(0)
     * <p>
     * The current thread must own this object's monitor. The thread
     * releases ownership of this monitor and waits until either of the
     * following two conditions has occurred:
     * 当前线程必须拥有当前对象的监控器。 线程释放当前监视器的所有权并等待直到下面两个
     * 条件中的一个发生：
     * <ul>
     * <li>Another thread notifies threads waiting on this object's monitor
     *     to wake up either through a call to the {@code notify} method
     *     or the {@code notifyAll} method.
     *     其他线程唤醒当前等待当前对象锁的线程，调用 notify 方法 或 notifyAll方法
     * <li>The timeout period, specified by {@code timeout}
     *     milliseconds plus {@code nanos} nanoseconds arguments, has
     *     elapsed.
     *     超过指定的超时时间 （毫秒 + 纳秒）
     * </ul>
     * <p>
     * The thread then waits until it can re-obtain ownership of the
     * monitor and resumes execution.
     * 线程等待直到可以重新获取 监视器的所有权 并 恢复执行
     * <p>
     * As in the one argument version, interrupts and spurious wakeups are
     * possible, and this method should always be used in a loop:
     * 一个争论是，interrupt 和 意外唤醒 可能，方法应该总是使用如下的循环判断
     * <pre>
     *     synchronized (obj) {
     *         while (&lt;condition does not hold&gt;)
     *             obj.wait(timeout, nanos);
     *         ... // Perform action appropriate to condition
     *     }
     * </pre>
     * This method should only be called by a thread that is the owner
     * of this object's monitor. See the {@code notify} method for a
     * description of the ways in which a thread can become the owner of
     * a monitor.
     * 该方法只应该被拥有对象监视器的线程唤醒。 查看 notify 方法了解更多线程获取监视器
     * 所有权的场景。
     *
     * @param      timeout   the maximum time to wait in milliseconds.
     * @param      nanos      additional time, in nanoseconds range
     *                       0-999999.
     * @throws  IllegalArgumentException      if the value of timeout is
     *                      negative or the value of nanos is
     *                      not in the range 0-999999.
     * @throws  IllegalMonitorStateException  if the current thread is not
     *               the owner of this object's monitor.
     * @throws  InterruptedException if any thread interrupted the
     *             current thread before or while the current thread
     *             was waiting for a notification.  The <i>interrupted
     *             status</i> of the current thread is cleared when
     *             this exception is thrown.
     */
    public final void wait(long timeout, int nanos) throws InterruptedException {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (nanos < 0 || nanos > 999999) {
            throw new IllegalArgumentException(
                                "nanosecond timeout value out of range");
        }

        if (nanos > 0) {
            timeout++;
        }

        wait(timeout);
    }

    /**
     * Causes the current thread to wait until another thread invokes the
     * {@link java.lang.Object#notify()} method or the
     * {@link java.lang.Object#notifyAll()} method for this object.
     * In other words, this method behaves exactly as if it simply
     * performs the call {@code wait(0)}.
     * 致使当前线程进入等待状态直到其他线程调用当前对象的 notify() 或 notifyAll() 方法。
     * 换句话说，这个方法执行就是简单的调用 wait(0)
     * <p>
     * The current thread must own this object's monitor. The thread
     * releases ownership of this monitor and waits until another thread
     * notifies threads waiting on this object's monitor to wake up
     * either through a call to the {@code notify} method or the
     * {@code notifyAll} method. The thread then waits until it can
     * re-obtain ownership of the monitor and resumes execution.
     * 当前线程必须拥有当前对象的监视器。线程释放这个监视器的所有券 并且 等待 直到
     * 其他线程唤醒这些等待这个监视器的线程，通过调用 notify() 或 notifyAll()
     * 方法中的一个实现。线程等待直到他可以重新拥有这个监视器的所有权并且恢复执行。
     * <p>
     * As in the one argument version, interrupts and spurious wakeups are
     * possible, and this method should always be used in a loop:
     * <pre>
     *     synchronized (obj) {
     *         while (&lt;condition does not hold&gt;)
     *             obj.wait();
     *         ... // Perform action appropriate to condition
     *     }
     * </pre>
     * This method should only be called by a thread that is the owner
     * of this object's monitor. See the {@code notify} method for a
     * description of the ways in which a thread can become the owner of
     * a monitor.
     *
     * @throws  IllegalMonitorStateException  if the current thread is not
     *               the owner of the object's monitor.
     * @throws  InterruptedException if any thread interrupted the
     *             current thread before or while the current thread
     *             was waiting for a notification.  The <i>interrupted
     *             status</i> of the current thread is cleared when
     *             this exception is thrown.
     * @see        java.lang.Object#notify()
     * @see        java.lang.Object#notifyAll()
     */
    public final void wait() throws InterruptedException {
        wait(0);
    }

    /**
     * Called by the garbage collector on an object when garbage collection
     * determines that there are no more references to the object.
     * A subclass overrides the {@code finalize} method to dispose of
     * system resources or to perform other cleanup.
     * 该方法在当前对象没有被引用时，被垃圾收集器调用。子类可以覆写这个方法来处理系统资源
     * 或执行其他的清理。
     * <p>
     * The general contract of {@code finalize} is that it is invoked
     * if and when the Java&trade; virtual
     * machine has determined that there is no longer any
     * means by which this object can be accessed by any thread that has
     * not yet died, except as a result of an action taken by the
     * finalization of some other object or class which is ready to be
     * finalized. The {@code finalize} method may take any action, including
     * making this object available again to other threads; the usual purpose
     * of {@code finalize}, however, is to perform cleanup actions before
     * the object is irrevocably discarded. For example, the finalize method
     * for an object that represents an input/output connection might perform
     * explicit I/O transactions to break the connection before the object is
     * permanently discarded.
     * 该方法的一般约定是当JVM认为当前可以被任何线程访问的对象不再有任何意义时当前对象还没有死亡
     * 除了是由于其他对象或类的终结，他已经准备好被终结。 finalize 方法可能带来任一行为，包括
     * 使当前对象再次可用于其他线程；finalize 常见的目的，无论怎么样，它在当前对象被不可恢复的
     * 废弃前用作清理。举个例子，一个表示输入/输出连接的对象可能执行明确的I/O
     * 处理，对象永久销毁之前 finalize 方法会关闭连接。
     *
     * <p>
     * The {@code finalize} method of class {@code Object} performs no
     * special action; it simply returns normally. Subclasses of
     * {@code Object} may override this definition.
     * Object类的finalize方法不会执行特殊的行为；它只是简单的返回。子类可以覆写定义它。
     * <p>
     * The Java programming language does not guarantee which thread will
     * invoke the {@code finalize} method for any given object. It is
     * guaranteed, however, that the thread that invokes finalize will not
     * be holding any user-visible synchronization locks when finalize is
     * invoked. If an uncaught exception is thrown by the finalize method,
     * the exception is ignored and finalization of that object terminates.
     * JAVA 不保证哪一个线程将会调用给定对象的 finalize 方法。可以保证的是，无论如何，
     * 当finalize被线程调用的时候不会持有任何用户可见的同步锁。如果一个未捕获的异常在
     * finalize 方法中抛出，这个异常将会被忽视 并且 将会使这个对象被终止。
     *
     * <p>
     * After the {@code finalize} method has been invoked for an object, no
     * further action is taken until the Java virtual machine has again
     * determined that there is no longer any means by which this object can
     * be accessed by any thread that has not yet died, including possible
     * actions by other objects or classes which are ready to be finalized,
     * at which point the object may be discarded.
     * 当对象的 finalize 方法被调用之后，没有带来更多的行为直到JVM再次认为这个可以被任何线程
     * 访问但还没有死亡的对象没有任何意义，包括可能的行为是其他对象或类已经准备被终止，
     * 来表明当前对象可能被终止。
     *
     * <p>
     * The {@code finalize} method is never invoked more than once by a Java
     * virtual machine for any given object.
     * 对于任何对象JVM从来都不会调用 finalize 方法超过一次
     * <p>
     * Any exception thrown by the {@code finalize} method causes
     * the finalization of this object to be halted, but is otherwise
     * ignored.
     * 任何finalize 方法抛出的异常都会导致 对象停止，但是异常会被忽视。
     *
     * @throws Throwable the {@code Exception} raised by this method
     * @see java.lang.ref.WeakReference
     * @see java.lang.ref.PhantomReference
     * @jls 12.6 Finalization of Class Instances
     */
    protected void finalize() throws Throwable { }
}
