## Semaphore解析

## 功能

一个计数信号量。从概念上讲，信号量维护了一个许可集。如有必要，在许可可用前会阻塞每一个 [`acquire()`](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/util/concurrent/Semaphore.html#acquire())，然后再获取该许可。每个 [`release()`](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/util/concurrent/Semaphore.html#release()) 添加一个许可，从而可能释放一个正在阻塞的获取者。但是，不使用实际的许可对象，`Semaphore` 只对可用许可的号码进行计数，并采取相应的行动。

Semaphore 通常用于限制可以访问某些资源（物理或逻辑的）的线程数目。

## **构造方法**

`Semaphore(int permits)` 
          创建具有给定的许可数和非公平的公平设置的 `Semaphore`。

`Semaphore(int permits, boolean fair)` 
          创建具有给定的许可数和给定的公平设置的 `Semaphore`。

## 方法

`acquire()` 
          从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，否则线程被[中断](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/lang/Thread.html#interrupt())。

`acquire(int permits)` 
          从此信号量获取给定数目的许可，在提供这些许可前一直将线程阻塞，或者线程已被[中断](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/lang/Thread.html#interrupt())。

`acquireUninterruptibly()` 
          从此信号量中获取许可，在有可用的许可前将其阻塞。

`acquireUninterruptibly(int permits)` 
          从此信号量获取给定数目的许可，在提供这些许可前一直将线程阻塞。

`availablePermits()` 
          返回此信号量中当前可用的许可数。

`drainPermits()` 
          获取并返回立即可用的所有许可。

`getQueuedThreads()` 
          返回一个 collection，包含可能等待获取的线程。

`getQueueLength()` 
          返回正在等待获取的线程的估计数目。

`hasQueuedThreads()` 
          查询是否有线程正在等待获取。

`isFair()` 
          如果此信号量的公平设置为 true，则返回 `true`。

`reducePermits(int reduction)` 
          根据指定的缩减量减小可用许可的数目。

`release(int permits)` 
          释放给定数目的许可，将其返回到信号量。

`toString()` 
          返回标识此信号量的字符串，以及信号量的状态。

`tryAcquire()` 
          仅在调用时此信号量存在一个可用许可，才从信号量获取许可。

`tryAcquire(int permits)` 
          仅在调用时此信号量中有给定数目的许可时，才从此信号量中获取这些许可。

`tryAcquire(int permits, long timeout, TimeUnit unit)` 
          如果在给定的等待时间内此信号量有可用的所有许可，并且当前线程未被[中断](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/lang/Thread.html#interrupt())，则从此信号量获取给定数目的许可。

`tryAcquire(long timeout, TimeUnit unit)` 
          如果在给定的等待时间内，此信号量有可用的许可并且当前线程未被[中断](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/lang/Thread.html#interrupt())，则从此信号量获取一个许可。