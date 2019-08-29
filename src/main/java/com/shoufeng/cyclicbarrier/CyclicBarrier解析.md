## CyclicBarrier解析

## 功能

一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。因为该 barrier 在释放等待线程后可以重用，所以称它为*循环* 的 barrier。

`CyclicBarrier` 支持一个可选的 [`Runnable`](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/lang/Runnable.html) 命令，在一组线程中的最后一个线程到达之后（但在释放所有线程之前），该命令只在每个屏障点运行一次。若在继续所有参与线程之前更新共享状态，此*屏障操作* 很有用。

## 构造方法

`CyclicBarrier(int parties)` 
          创建一个新的 `CyclicBarrier`，它将在给定数量的参与者（线程）处于等待状态时启动，但它不会在启动 barrier 时执行预定义的操作。

`CyclicBarrier(int parties, Runnable barrierAction)` 
          创建一个新的 `CyclicBarrier`，它将在给定数量的参与者（线程）处于等待状态时启动，并在启动 barrier 时执行给定的屏障操作，该操作由最后一个进入 barrier 的线程执行。

## 方法

`await()` 
          在所有[参与者](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/util/concurrent/CyclicBarrier.html#getParties())都已经在此 barrier 上调用 `await` 方法之前，将一直等待。

`await(long timeout, TimeUnit unit)` 
          在所有[参与者](http://tool.oschina.net/uploads/apidocs/jdk-zh/java/util/concurrent/CyclicBarrier.html#getParties())都已经在此屏障上调用 `await` 方法之前将一直等待,或者超出了指定的等待时间。

`getNumberWaiting()` 
          返回当前在屏障处等待的参与者数目。

`getParties()` 
          返回要求启动此 barrier 的参与者数目。

`isBroken()` 
          查询此屏障是否处于损坏状态。

`reset()` 
          将屏障重置为其初始状态。

