package com.shoufeng.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore样例
 *
 * @author shoufeng
 */
public class Demo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 1; i < 100; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    Thread.sleep(finalI *1000L);
                    System.out.println(Thread.currentThread().getName() + "请求获取信号量");
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "获取到信号量");
                    int queueLength = semaphore.getQueueLength();
                    System.out.println("等待获取信号量的线程数：" + queueLength);
                    Thread.sleep(finalI *1000L);
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName() + "释放信号量");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}
