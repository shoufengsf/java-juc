package com.shoufeng.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier样例
 *
 * @author shoufeng
 */
public class Demo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println("全部到达");
        });
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "开始cyclicBarrier.await");
                try {
                    int parties = cyclicBarrier.getParties();
                    System.out.println(Thread.currentThread().getName() + "cyclicBarrier.getParties: " + parties);
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "再次启动");
                cyclicBarrier.reset();
                System.out.println(Thread.currentThread().getName() + "cyclicBarrier.reset()重置");
            }
        });
        executorService.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "开始cyclicBarrier.await");
                try {
                    int parties = cyclicBarrier.getParties();
                    System.out.println(Thread.currentThread().getName() + "cyclicBarrier.getParties: " + parties);
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "再次启动");
            }
        });
        executorService.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "开始cyclicBarrier.await");
                try {
                    int parties = cyclicBarrier.getParties();
                    System.out.println(Thread.currentThread().getName() + "cyclicBarrier.getParties: " + parties);
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "再次启动");
            }

        });
        executorService.shutdown();
    }
}
