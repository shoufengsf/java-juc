package com.shoufeng.reentrant;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock样例
 * 使用condition顺序打印ABC
 *
 * @author shoufeng
 */
public class Demo {

    volatile int flag = 1;

    public static void main(String[] args) {
        //默认非公平锁
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition conditionA = reentrantLock.newCondition();
        Condition conditionB = reentrantLock.newCondition();
        Condition conditionC = reentrantLock.newCondition();
        Demo demo = new Demo();
        //打印A
        new Thread(() -> {
            while (true) {
                reentrantLock.lock();
                System.out.println("printA加锁");
                if (demo.flag != 1) {
                    try {
                        conditionA.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                demo.printA();
                demo.flag = 2;
                conditionB.signal();
                reentrantLock.unlock();
                System.out.println("printA解锁");
            }

        }).start();
        //打印B
        new Thread(() -> {
            while (true) {
                reentrantLock.lock();
                System.out.println("printB加锁");
                if (demo.flag != 2) {
                    try {
                        conditionB.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                demo.printB();
                demo.flag = 3;
                conditionC.signal();
                reentrantLock.unlock();
                System.out.println("printB解锁");
            }
        }).start();
        //打印C
        new Thread(() -> {
            while (true) {
                reentrantLock.lock();
                System.out.println("printC加锁");
                if (demo.flag != 3) {
                    try {
                        conditionC.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                demo.printC();
                demo.flag = 1;
                conditionA.signal();
                reentrantLock.unlock();
                System.out.println("printC解锁");
            }
        }).start();
    }

    public void printA() {
        System.out.println(Thread.currentThread().getName() + "打印" + "A");
    }

    public void printB() {
        System.out.println(Thread.currentThread().getName() + "打印" + "B");
    }

    public void printC() {
        System.out.println(Thread.currentThread().getName() + "打印" + "C");
    }
}
