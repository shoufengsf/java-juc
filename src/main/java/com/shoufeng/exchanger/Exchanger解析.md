# Exchanger解析

## 功能

可以在对中对元素进行配对和交换的线程的同步点。每个线程将条目上的某个方法呈现给 [`exchange`](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/util/concurrent/Exchanger.html#exchange(V)) 方法，与伙伴线程进行匹配，并且在返回时接收其伙伴的对象。Exchanger 可能被视为 [`SynchronousQueue`](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/util/concurrent/SynchronousQueue.html) 的双向形式。Exchanger 可能在应用程序（比如遗传算法和管道设计）中很有用。

## 方法

`exchange(V x)` 
          等待另一个线程到达此交换点（除非当前线程被[中断](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/lang/Thread.html#interrupt())），然后将给定的对象传送给该线程，并接收该线程的对象。

`exchange(V x, long timeout, TimeUnit unit)` 
          等待另一个线程到达此交换点（除非当前线程被[中断](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/lang/Thread.html#interrupt())，或者超出了指定的等待时间），然后将给定的对象传送给该线程，同时接收该线程的对象。

