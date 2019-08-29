# synchronized解析

synchronized修饰的代码块保证了可见性、原子性、防止了指令重排

## synchronized修饰不同位置的含义

### 1、普通同步方法，锁是当前实例对象

```java
//锁在实例上
public synchronized void print1() throws InterruptedException {
    Thread.sleep(1000L);
    System.out.println(Thread.currentThread().getName() + " print1 开始");
}
```

### 2、静态同步方法，锁是当前类的class对象

```java
//锁在类上
public synchronized static void print2() throws InterruptedException {
    Thread.sleep(1000L);
    System.out.println(Thread.currentThread().getName() + " print2 开始");
}
```

### 3、同步方法块，锁是括号里面的对象

```java
//锁在实例上
public void print3() throws InterruptedException {
    synchronized (this) {
        Thread.sleep(1000L);
        System.out.println(Thread.currentThread().getName() + " print3 开始");
    }
}
```

```java
//锁在类上
public void print4() throws InterruptedException {
    synchronized (Demo.class) {
        Thread.sleep(1000L);
        System.out.println(Thread.currentThread().getName() + " print4 开始");
    }
}
```

## synchronized底层原理

```java
public void print3() throws java.lang.InterruptedException;
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=3, locals=3, args_size=1
         0: aload_0
         1: dup
         2: astore_1
         3: monitorenter
         4: ldc2_w        #2                  // long 1000l
         7: invokestatic  #4                  // Method java/lang/Thread.sleep:(J)V
        10: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
        13: new           #6                  // class java/lang/StringBuilder
        16: dup
        17: invokespecial #7                  // Method java/lang/StringBuilder."<init>":()V
        20: invokestatic  #8                  // Method java/lang/Thread.currentThread:()Ljava/lang/Thread;
        23: invokevirtual #9                  // Method java/lang/Thread.getName:()Ljava/lang/String;
        26: invokevirtual #10                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        29: ldc           #15                 // String  print3 ??
        31: invokevirtual #10                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        34: invokevirtual #12                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        37: invokevirtual #13                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        40: aload_1
        41: monitorexit
        42: goto          50
        45: astore_2
        46: aload_1
        47: monitorexit
        48: aload_2
        49: athrow
        50: return
      Exception table:
         from    to  target type
             4    42    45   any
            45    48    45   any
      LineNumberTable:
        line 22: 0
        line 23: 4
        line 24: 10
        line 25: 40
        line 26: 50
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      51     0  this   Lcom/shoufeng/sync/Demo;
    Exceptions:
      throws java.lang.InterruptedException

  public void print4() throws java.lang.InterruptedException;
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=3, locals=3, args_size=1
         0: ldc           #16                 // class com/shoufeng/sync/Demo
         2: dup
         3: astore_1
         4: monitorenter
         5: ldc2_w        #2                  // long 1000l
         8: invokestatic  #4                  // Method java/lang/Thread.sleep:(J)V
        11: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
        14: new           #6                  // class java/lang/StringBuilder
        17: dup
        18: invokespecial #7                  // Method java/lang/StringBuilder."<init>":()V
        21: invokestatic  #8                  // Method java/lang/Thread.currentThread:()Ljava/lang/Thread;
        24: invokevirtual #9                  // Method java/lang/Thread.getName:()Ljava/lang/String;
        27: invokevirtual #10                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        30: ldc           #17                 // String  print4 ??
        32: invokevirtual #10                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        35: invokevirtual #12                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        38: invokevirtual #13                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        41: aload_1
        42: monitorexit
        43: goto          51
        46: astore_2
        47: aload_1
        48: monitorexit
        49: aload_2
        50: athrow
        51: return
      Exception table:
         from    to  target type
             5    43    46   any
            46    49    46   any
      LineNumberTable:
        line 30: 0
        line 31: 5
        line 32: 11
        line 33: 41
        line 34: 51
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      52     0  this   Lcom/shoufeng/sync/Demo;
    Exceptions:
      throws java.lang.InterruptedException
```

**monitorenter**：每个对象都是一个监视器锁（monitor）。当monitor被占用时就会处于锁定状态，线程执行monitorenter指令时尝试获取monitor的所有权，过程如下：

**如果monitor的进入数为0**，则该线程进入monitor，然后将进入数设置为1，该线程即为monitor的所有者；

**如果线程已经占有该monitor**，只是重新进入，则进入monitor的进入数加1；

**如果其他线程已经占用了monitor**，则该线程进入阻塞状态，直到monitor的进入数为0，再重新尝试获取monitor的所有权；

**monitorexit**：执行monitorexit的线程必须是objectref所对应的monitor的所有者。**指令执行时，monitor的进入数减1，如果减1后进入数为0，那线程退出monitor，不再是这个monitor的所有者**。其他被这个monitor阻塞的线程可以尝试去获取这个 monitor 的所有权。

通过上面两段描述，我们应该能很清楚的看出Synchronized的实现原理，**Synchronized的语义底层是通过一个monitor的对象来完成，其实wait/notify等方法也依赖于monitor对象**，这就是为什么只有在同步的块或者方法中才能调用wait/notify等方法，**否则会抛出java.lang.IllegalMonitorStateException的异常的原因**。

```java
public synchronized void print1() throws java.lang.InterruptedException;
    descriptor: ()V
    flags: ACC_PUBLIC, ACC_SYNCHRONIZED
    Code:
      stack=3, locals=1, args_size=1
         0: ldc2_w        #2                  // long 1000l
         3: invokestatic  #4                  // Method java/lang/Thread.sleep:(J)V
         6: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
         9: new           #6                  // class java/lang/StringBuilder
        12: dup
        13: invokespecial #7                  // Method java/lang/StringBuilder."<init>":()V
        16: invokestatic  #8                  // Method java/lang/Thread.currentThread:()Ljava/lang/Thread;
        19: invokevirtual #9                  // Method java/lang/Thread.getName:()Ljava/lang/String;
        22: invokevirtual #10                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        25: ldc           #11                 // String  print1 ??
        27: invokevirtual #10                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        30: invokevirtual #12                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        33: invokevirtual #13                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        36: return
      LineNumberTable:
        line 10: 0
        line 11: 6
        line 12: 36
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      37     0  this   Lcom/shoufeng/sync/Demo;
    Exceptions:
      throws java.lang.InterruptedException

  public static synchronized void print2() throws java.lang.InterruptedException;
    descriptor: ()V
    flags: ACC_PUBLIC, ACC_STATIC, ACC_SYNCHRONIZED
    Code:
      stack=3, locals=0, args_size=0
         0: ldc2_w        #2                  // long 1000l
         3: invokestatic  #4                  // Method java/lang/Thread.sleep:(J)V
         6: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;
         9: new           #6                  // class java/lang/StringBuilder
        12: dup
        13: invokespecial #7                  // Method java/lang/StringBuilder."<init>":()V
        16: invokestatic  #8                  // Method java/lang/Thread.currentThread:()Ljava/lang/Thread;
        19: invokevirtual #9                  // Method java/lang/Thread.getName:()Ljava/lang/String;
        22: invokevirtual #10                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        25: ldc           #14                 // String  print2 ??
        27: invokevirtual #10                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        30: invokevirtual #12                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        33: invokevirtual #13                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        36: return
      LineNumberTable:
        line 16: 0
        line 17: 6
        line 18: 36
    Exceptions:
      throws java.lang.InterruptedException

```

从编译的结果来看，方法的同步并没有通过指令 **monitorenter** 和 **monitorexit** 来完成（理论上其实也可以通过这两条指令来实现），不过相对于普通方法，其常量池中多了 **ACC_SYNCHRONIZED** 标示符。**JVM就是根据该标示符来实现方法的同步的**：

当方法调用时，**调用指令将会检查方法的 ACC_SYNCHRONIZED 访问标志是否被设置**，如果设置了，**执行线程将先获取monitor**，获取成功之后才能执行方法体，**方法执行完后再释放monitor**。在方法执行期间，其他任何线程都无法再获得同一个monitor对象。

两种同步方式本质上没有区别，只是方法的同步是一种隐式的方式来实现，无需通过字节码来完成。**两个指令的执行是JVM通过调用操作系统的互斥原语mutex来实现，被阻塞的线程会被挂起、等待重新调度**，会导致“用户态和内核态”两个态之间来回切换，对性能有较大影响。

## 同步概念

### Java对象头

在JVM中，对象在内存中的布局分为三块区域：**对象头、实例数据和对齐填充**。如下图所示：

|      |                    |                                        |              |
| ---: | ------------------ | -------------------------------------- | ------------ |
|      |                    |                                        | 哈希码       |
|      |                    |                                        | GC分代年龄   |
|      |                    |                                        | 锁状态标示   |
|      |                    | 存储对象自身的运行时数据（Mark Work）  | 线程持有的锁 |
|      |                    |                                        | 偏向线程ID   |
|      | 对象头             |                                        | 偏向时间戳   |
| 对象 |                    | 类型指针                               |              |
|      |                    | 若为对象数组，还应有记录数组长度的数据 |              |
|      | 实例数据           |                                        |              |
|      | 对齐填充（非必须） |                                        |              |
|      |                    |                                        |              |

**实例数据**：存放类的属性数据信息，包括父类的属性信息；

**对齐填充**：由于虚拟机要求 **对象起始地址必须是8字节的整数倍**。填充数据不是必须存在的，仅仅是为了字节对齐；

**对象头**：**Java对象头一般占有2个机器码**（在32位虚拟机中，1个机器码等于4字节，也就是32bit，在64位虚拟机中，1个机器码是8个字节，也就是64bit），但是 **如果对象是数组类型，则需要3个机器码，因为JVM虚拟机可以通过Java对象的元数据信息确定Java对象的大小**，但是无法从数组的元数据来确认数组的大小，所以用一块来记录数组长度。