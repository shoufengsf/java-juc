package com.shoufeng.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * fork/join样例
 * 计算 1+2+3+4+5...
 *
 * @author shoufeng
 */
public class Demo extends RecursiveTask<Integer> {

    private int start;
    private int end;

    public Demo(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Demo demo = new Demo(1, 10000);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(demo);
        System.out.println("计算结果：" + forkJoinTask.get());
    }

    @Override
    protected Integer compute() {
        if (start == end) {
            return start;
        }
        if (end - start < 2) {
            return end + start;
        }
        int middle = (end + start) / 2;
        Demo leftDemo = new Demo(start, middle);
        Demo rightDemo = new Demo(middle + 1, end);
        leftDemo.fork();
        rightDemo.fork();
        return leftDemo.join() + rightDemo.join();
    }
}
