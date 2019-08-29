# ThreadLocal解析

## 功能

该类提供了线程局部 (thread-local) 变量。这些变量不同于它们的普通对应物，因为访问某个变量（通过其 `get` 或 `set` 方法）的每个线程都有自己的局部变量，它独立于变量的初始化副本。`ThreadLocal` 实例通常是类中的 private static 字段，它们希望将状态与某一个线程（例如，用户 ID 或事务 ID）相关联。

例如，以下类生成对每个线程唯一的局部标识符。 线程 ID 是在第一次调用 `UniqueThreadIdGenerator.getCurrentThreadId()` 时分配的，在后续调用中不会更改。

## 方法

### initialValue

```java
protected T initialValue()
```

返回此线程局部变量的当前线程的“初始值”。线程第一次使用 [`get()`](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/lang/ThreadLocal.html#get()) 方法访问变量时将调用此方法，但如果线程之前调用了 [`set(T)`](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/lang/ThreadLocal.html#set(T)) 方法，则不会对该线程再调用 `initialValue` 方法。通常，此方法对每个线程最多调用一次，但如果在调用 [`get()`](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/lang/ThreadLocal.html#get()) 后又调用了 [`remove()`](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/lang/ThreadLocal.html#remove())，则可能再次调用此方法。

该实现返回 `null`；如果程序员希望线程局部变量具有 `null` 以外的值，则必须为 `ThreadLocal` 创建子类，并重写此方法。通常将使用匿名内部类完成此操作。

**返回：**

返回此线程局部变量的初始值

### get

```java
public T get()
```

返回此线程局部变量的当前线程副本中的值。如果变量没有用于当前线程的值，则先将其初始化为调用 [`initialValue()`](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/lang/ThreadLocal.html#initialValue()) 方法返回的值。

**返回：**

此线程局部变量的当前线程的值

### set

```java
public void set(T value)
```

将此线程局部变量的当前线程副本中的值设置为指定值。大部分子类不需要重写此方法，它们只依靠 [`initialValue()`](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/lang/ThreadLocal.html#initialValue()) 方法来设置线程局部变量的值。

**参数：**

`value` - 存储在此线程局部变量的当前线程副本中的值。

### remove

```java
public void remove()
```

移除此线程局部变量当前线程的值。如果此线程局部变量随后被当前线程 [读取](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/lang/ThreadLocal.html#get())，且这期间当前线程没有 [设置](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/lang/ThreadLocal.html#set(T))其值，则将调用其 [`initialValue()`](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/lang/ThreadLocal.html#initialValue()) 方法重新初始化其值。这将导致在当前线程多次调用 `initialValue` 方法。