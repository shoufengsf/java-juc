package com.shoufeng.reentrantreadwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 写锁降级样例
 *
 * @author shoufeng
 */
public class Demo2 {
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        Demo2 demo2 = new Demo2();
        new Thread(() -> {
            try {
                demo2.writeRead();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                demo2.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void read() throws InterruptedException {
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        readLock.lock();
        System.out.println(Thread.currentThread().getName() + "正在读。。。");
        Thread.sleep(5000L);
        readLock.unlock();
    }

    public void writeRead() throws InterruptedException {
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        writeLock.lock();
        System.out.println(Thread.currentThread().getName() + "正在写");
        Thread.sleep(1000L);
        //开启读锁
        readLock.lock();
        System.out.println(Thread.currentThread().getName() + "开启读锁");
        //释放写锁 实现锁降级
        writeLock.unlock();
        System.out.println(Thread.currentThread().getName() + "释放写锁 实现锁降级");
        System.out.println(Thread.currentThread().getName() + "正在读。。。");
        Thread.sleep(5000L);
    }
}
