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

/**
 * Serializability of a class is enabled by the class implementing the
 * java.io.Serializable interface. Classes that do not implement this
 * interface will not have any of their state serialized or
 * deserialized.  All subtypes of a serializable class are themselves
 * serializable.  The serialization interface has no methods or fields
 * and serves only to identify the semantics of being serializable. <p>
 * 通过实现 java.io.Serializable 接口来让类成为可序列化的。没有实现这个接口的类
 * 在序列化和反序列化的时候将不会有任何状态。 序列化接口没有方法或字段并且只服务 鉴别
 * 可序列化的语义。
 *
 * To allow subtypes of non-serializable classes to be serialized, the
 * subtype may assume responsibility for saving and restoring the
 * state of the supertype's public, protected, and (if accessible)
 * package fields.  The subtype may assume this responsibility only if
 * the class it extends has an accessible no-arg constructor to
 * initialize the class's state.  It is an error to declare a class
 * Serializable if this is not the case.  The error will be detected at
 * runtime. <p>
 * 允许不可序列化的类的子类变得可序列化， 子类可能需要承担父类的 public，protected，
 * package fields（如果存在）的保存和恢复工作。子类可能需要承担这份责任只在继承的类
 * 有一个可以访问的无参构造器来初始化类的转台。如果该实例不存在将会在序列化类的时候声明
 * 一个错误。这个错误将会被定义为运行时错误。
 *
 * During deserialization, the fields of non-serializable classes will
 * be initialized using the public or protected no-arg constructor of
 * the class.  A no-arg constructor must be accessible to the subclass
 * that is serializable.  The fields of serializable subclasses will
 * be restored from the stream. <p>
 * 在反序列化的时候，不可序列化类的字段初始化将会使用对应的 public 或 protected
 * 的无参构造器 一个无参构造器必须可以被这个 可序列化的子类 访问。 可序列化子类的字段
 * 将会被从流中恢复。
 *
 * When traversing a graph, an object may be encountered that does not
 * support the Serializable interface. In this case the
 * NotSerializableException will be thrown and will identify the class
 * of the non-serializable object. <p>
 * 当遍历一个图时，一个对象可能碰到不支持 可序列化接口。在这种情况下 NotSerializableException
 * 将会被抛出 并且 认为这个类的对象不可序列化。
 *
 * Classes that require special handling during the serialization and
 * deserialization process must implement special methods with these exact
 * signatures:
 * 类需要特别处理在序列化和反序列化过程中必须实现特别的方法如下签名：
 *
 * <PRE>
 * private void writeObject(java.io.ObjectOutputStream out)
 *     throws IOException
 * private void readObject(java.io.ObjectInputStream in)
 *     throws IOException, ClassNotFoundException;
 * private void readObjectNoData()
 *     throws ObjectStreamException;
 * </PRE>
 *
 * <p>The writeObject method is responsible for writing the state of the
 * object for its particular class so that the corresponding
 * readObject method can restore it.  The default mechanism for saving
 * the Object's fields can be invoked by calling
 * out.defaultWriteObject. The method does not need to concern
 * itself with the state belonging to its superclasses or subclasses.
 * State is saved by writing the individual fields to the
 * ObjectOutputStream using the writeObject method or by using the
 * methods for primitive data types supported by DataOutput.
 * writeObject 方法负责将这个特别的对象状态写入，如次一致的 readObject 方法可以恢复他。
 * 默认的原理是保存对象的字段可以调用 out.defaultWriteObject。这个方法不需要担心他
 * 自身的状态属于超类或子类。状态被保存 通过写入独立的域  ObjectOutputStream 使用
 * writeObject 方法或通过使用 DataOutput 为原始数据类型提供支持。
 *
 * <p>The readObject method is responsible for reading from the stream and
 * restoring the classes fields. It may call in.defaultReadObject to invoke
 * the default mechanism for restoring the object's non-static and
 * non-transient fields.  The defaultReadObject method uses information in
 * the stream to assign the fields of the object saved in the stream with the
 * correspondingly named fields in the current object.  This handles the case
 * when the class has evolved to add new fields. The method does not need to
 * concern itself with the state belonging to its superclasses or subclasses.
 * State is saved by writing the individual fields to the
 * ObjectOutputStream using the writeObject method or by using the
 * methods for primitive data types supported by DataOutput.
 * readObject 方法负责从流中读取并恢复类的字段。可以调用 in.defaultReadObject 这个
 * 方法以默认的机制来恢复非静态 和 non-transient字段。defaultReadObject 方法使用
 * 流中的信息给字段赋值这个对象保存在流中有和当前对象有相同的字段名称。 这种处理场景当类
 * 主键添加了新的字段。该方法不需要担心它自身状态属于它的超类或者子类。状态保存在
 * ObjectOutputStream 中的独立字段，ObjectOutputStream 使用 writeObject方法
 * 或 通过 DataOutput的方法来支持原始数据类型。
 *
 * <p>The readObjectNoData method is responsible for initializing the state of
 * the object for its particular class in the event that the serialization
 * stream does not list the given class as a superclass of the object being
 * deserialized.  This may occur in cases where the receiving party uses a
 * different version of the deserialized instance's class than the sending
 * party, and the receiver's version extends classes that are not extended by
 * the sender's version.  This may also occur if the serialization stream has
 * been tampered; hence, readObjectNoData is useful for initializing
 * deserialized objects properly despite a "hostile" or incomplete source
 * stream.
 * readObjectNoData 方法负责初始化这个指定的类的对象的状态，在序列化流中没有列出给定
 * 类的对象的父类在反序列化事件中。 这可能导致一个场景 反序列化实例类使用了收到来自不同版本的
 * 发送的部分，并且 接收者的版本继承类没有继承发送者版本的类。这也可能出现如果序列化流被破坏了；
 * 因此 readObjectNoData 方法在正确的初始化反序列化对象即使存在错误 或 被破坏的流是十分有用的。
 *
 * <p>Serializable classes that need to designate an alternative object to be
 * used when writing an object to the stream should implement this
 * special method with the exact signature:
 * 可序列化的类需要指定一个供代替选择的对象 被用于当写入一个对象到流时应该实现这个特殊的方法
 * 如下签名：
 *
 * <PRE>
 * ANY-ACCESS-MODIFIER Object writeReplace() throws ObjectStreamException;
 * </PRE><p>
 *
 * This writeReplace method is invoked by serialization if the method
 * exists and it would be accessible from a method defined within the
 * class of the object being serialized. Thus, the method can have private,
 * protected and package-private access. Subclass access to this method
 * follows java accessibility rules. <p>
 * writeReplace 方法被序列化调用 如果方法存在且它可以从中定义的方法访问正在序列化的
 * 对象的类。因此，该方法有 private，protected，packaged-private 访问权限。
 * 子类访问这个方法遵循java可访问性规则。
 *
 * Classes that need to designate a replacement when an instance of it
 * is read from the stream should implement this special method with the
 * exact signature.
 * 当从流中读取实例时需要指定替换的类应实现具有确切签名的特殊方法。
 *
 * <PRE>
 * ANY-ACCESS-MODIFIER Object readResolve() throws ObjectStreamException;
 * </PRE><p>
 *
 * This readResolve method follows the same invocation rules and
 * accessibility rules as writeReplace.<p>
 * 这个readResolve方法遵循与writeReplace相同的调用规则和可访问性规则。
 *
 * The serialization runtime associates with each serializable class a version
 * number, called a serialVersionUID, which is used during deserialization to
 * verify that the sender and receiver of a serialized object have loaded
 * classes for that object that are compatible with respect to serialization.
 * If the receiver has loaded a class for the object that has a different
 * serialVersionUID than that of the corresponding sender's class, then
 * deserialization will result in an {@link InvalidClassException}.  A
 * serializable class can declare its own serialVersionUID explicitly by
 * declaring a field named <code>"serialVersionUID"</code> that must be static,
 * final, and of type <code>long</code>:
 * 序列化运行时将每个可序列化的类与称为serialVersionUID的版本号相关联，该序列号在反序列化期间
 * 用于验证序列化对象的发送者和接收者是否已加载与该序列化兼容的对象的类。 如果接收方加载了一个具有
 * 不同于相应发件人类的serialVersionUID的对象的类，则反序列化将导致InvalidClassException 。
 * 一个可序列化的类可以通过声明一个名为"serialVersionUID"的字段来显式地声明它自己的serialVersionUID，
 * 该字段必须是静态的，最终的，类型是long ：
 *
 * <PRE>
 * ANY-ACCESS-MODIFIER static final long serialVersionUID = 42L;
 * </PRE>
 *
 * If a serializable class does not explicitly declare a serialVersionUID, then
 * the serialization runtime will calculate a default serialVersionUID value
 * for that class based on various aspects of the class, as described in the
 * Java(TM) Object Serialization Specification.  However, it is <em>strongly
 * recommended</em> that all serializable classes explicitly declare
 * serialVersionUID values, since the default serialVersionUID computation is
 * highly sensitive to class details that may vary depending on compiler
 * implementations, and can thus result in unexpected
 * <code>InvalidClassException</code>s during deserialization.  Therefore, to
 * guarantee a consistent serialVersionUID value across different java compiler
 * implementations, a serializable class must declare an explicit
 * serialVersionUID value.  It is also strongly advised that explicit
 * serialVersionUID declarations use the <code>private</code> modifier where
 * possible, since such declarations apply only to the immediately declaring
 * class--serialVersionUID fields are not useful as inherited members. Array
 * classes cannot declare an explicit serialVersionUID, so they always have
 * the default computed value, but the requirement for matching
 * serialVersionUID values is waived for array classes.
 * 如果可序列化类没有显式声明serialVersionUID，则序列化运行时将根据Java（TM）对象序列化规范
 * 中所述的类的各个方面计算该类的默认serialVersionUID值。 但是， 强烈建议所有可序列化的类都
 * 明确声明serialVersionUID值，因为默认的serialVersionUID计算对类详细信息非常敏感，这可能
 * 会因编译器实现而异，因此可能会在反InvalidClassException化期间导致InvalidClassException
 * 的InvalidClassException。 因此，为了保证不同Java编译器实现之间的一致的serialVersionUID值，
 * 一个可序列化的类必须声明一个显式的serialVersionUID值。 还强烈建议，显式的serialVersionUID声明
 * 在可能的情况下使用private修饰符，因为这种声明仅适用于立即声明的类 - serialVersionUID字段作为
 * 继承成员无效。 数组类不能声明一个显式的serialVersionUID，所以它们总是具有默认的计算值，
 * 但是对于数组类，放弃了匹配serialVersionUID值的要求。
 *
 * @author  unascribed
 * @see java.io.ObjectOutputStream
 * @see java.io.ObjectInputStream
 * @see java.io.ObjectOutput
 * @see java.io.ObjectInput
 * @see java.io.Externalizable
 * @since   JDK1.1
 */
public interface Serializable {
}
