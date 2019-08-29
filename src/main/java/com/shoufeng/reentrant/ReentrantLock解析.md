# reentrantLock解析

## 功能

一个可重入的互斥锁 [`Lock`](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/util/concurrent/locks/Lock.html)，它具有与使用 `synchronized` 方法和语句所访问的隐式监视器锁相同的一些基本行为和语义，但功能更强大。

`ReentrantLock` 将由最近成功获得锁，并且还没有释放该锁的线程所*拥有*。当锁没有被另一个线程所拥有时，调用 `lock` 的线程将成功获取该锁并返回。如果当前线程已经拥有该锁，此方法将立即返回。可以使用 [`isHeldByCurrentThread()`](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/util/concurrent/locks/ReentrantLock.html#isHeldByCurrentThread()) 和 [`getHoldCount()`](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/util/concurrent/locks/ReentrantLock.html#getHoldCount()) 方法来检查此情况是否发生。

此类的构造方法接受一个可选的*公平* 参数。当设置为 `true` 时，在多个线程的争用下，这些锁倾向于将访问权授予等待时间最长的线程。否则此锁将无法保证任何特定访问顺序。与采用默认设置（使用不公平锁）相比，使用公平锁的程序在许多线程访问时表现为很低的总体吞吐量（即速度很慢，常常极其慢），但是在获得锁和保证锁分配的均衡性时差异较小。不过要注意的是，公平锁不能保证线程调度的公平性。因此，使用公平锁的众多线程中的一员可能获得多倍的成功机会，这种情况发生在其他活动线程没有被处理并且目前并未持有锁时。还要注意的是，未定时的 [`tryLock`](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/util/concurrent/locks/ReentrantLock.html#tryLock()) 方法并没有使用公平设置。因为即使其他线程正在等待，只要该锁是可用的，此方法就可以获得成功。