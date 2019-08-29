package com.shoufeng.sync;

/**
 * synchronized使用样例
 *
 * @author shoufeng
 */
public class Demo {

    //锁在类上
    public synchronized static void print2() throws InterruptedException {
        Thread.sleep(1000L);
        System.out.println(Thread.currentThread().getName() + " print2 开始");
    }

    public static void main(String[] args) {
        final Demo demo1 = new Demo();
        final Demo demo2 = new Demo();
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        demo1.print1();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        demo1.print1();
//                        demo2.print1();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //锁在实例上
    public synchronized void print1() throws InterruptedException {
        Thread.sleep(1000L);
        System.out.println(Thread.currentThread().getName() + " print1 开始");
    }

    //锁在实例上
    public void print3() throws InterruptedException {
        synchronized (this) {
            Thread.sleep(1000L);
            System.out.println(Thread.currentThread().getName() + " print3 开始");
        }
    }

    //锁在类上
    public void print4() throws InterruptedException {
        synchronized (Demo.class) {
            Thread.sleep(1000L);
            System.out.println(Thread.currentThread().getName() + " print4 开始");
        }
    }
}
