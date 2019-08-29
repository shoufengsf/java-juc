package com.shoufeng.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch样例
 *
 * @author shoufeng
 */
public class Demo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "countDown");
            countDownLatch.countDown();
        });
        executorService.submit(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "countDown");
            countDownLatch.countDown();
            try {
                System.out.println(Thread.currentThread().getName() + "开始等待");
                countDownLatch.await();
                System.out.println(Thread.currentThread().getName() + "开始再次启动");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.submit(() -> {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "countDown");
            countDownLatch.countDown();
        });
        System.out.println("主线程countDownLatch.await()");
        countDownLatch.await();
        System.out.println("主线程再次启动");
        executorService.shutdown();
    }
}
