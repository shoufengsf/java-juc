package com.shoufeng.reentrantreadwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁 读锁是共享锁 写锁是排他锁
 *
 * @author shoufeng
 */
public class Demo {
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        Demo demo = new Demo();
        new Thread(() -> {
            try {
                demo.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                demo.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                demo.write();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                demo.write();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void read() throws InterruptedException {
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        readLock.lock();
        System.out.println(Thread.currentThread().getName() + "正在读取。。。");
        Thread.sleep(5000L);
        readLock.unlock();
    }

    public void write() throws InterruptedException {
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        System.out.println(Thread.currentThread().getName() + "正在写");
        Thread.sleep(3000L);
        writeLock.unlock();
    }
}
