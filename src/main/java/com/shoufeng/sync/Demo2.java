package com.shoufeng.sync;

/**
 * 顺序打印ABC
 *
 * @author shoufeng
 */
public class Demo2 {
    volatile int flag = 1;

    public static void main(String[] args) {
        Demo2 demo2 = new Demo2();
        new Thread(() -> {
            while (true) {
                try {
                    demo2.printA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    demo2.printB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    demo2.printC();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public synchronized void printA() throws InterruptedException {
        while (flag != 1) {
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + "打印" + "A");
        flag = 2;
        this.notifyAll();
    }

    public synchronized void printB() throws InterruptedException {
        while (flag != 2) {
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + "打印" + "B");
        flag = 3;
        this.notifyAll();
    }

    public synchronized void printC() throws InterruptedException {
        while (flag != 3) {
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + "打印" + "C");
        flag = 1;
        this.notifyAll();
    }
}
