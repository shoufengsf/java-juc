package com.shoufeng.reentrant;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用reentrantlock顺序打印ABC
 */
public class Demo2 {
    //默认非公平锁
    ReentrantLock reentrantLock = new ReentrantLock();

    private volatile int flag = 1;

    public static void main(String[] args) {
        Demo2 demo2 = new Demo2();
        new Thread(() -> {
            while (true) {
                demo2.printA();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                demo2.printB();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                demo2.printC();
            }
        }).start();
    }

    public void printA() {
        reentrantLock.lock();
        if (flag != 1) {
            reentrantLock.unlock();
            return;
        }
        System.out.println(Thread.currentThread().getName() + "打印" + "A");
        flag = 2;
        reentrantLock.unlock();
    }

    public void printB() {
        reentrantLock.lock();
        if (flag != 2) {
            reentrantLock.unlock();
            return;
        }
        System.out.println(Thread.currentThread().getName() + "打印" + "B");
        flag = 3;
        reentrantLock.unlock();
    }

    public void printC() {
        reentrantLock.lock();
        if (flag != 3) {
            reentrantLock.unlock();
            return;
        }
        System.out.println(Thread.currentThread().getName() + "打印" + "C");
        flag = 1;
        reentrantLock.unlock();
    }
}
