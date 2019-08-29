package com.shoufeng.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * exchanger样例
 * 结果交换
 *
 * @author shoufeng
 */
public class Demo {

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<String>();
        Demo demo = new Demo();
        new Thread(new Runnable() {
            public void run() {
                try {
                    demo.methodA(exchanger);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                try {
                    demo.methodB(exchanger);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void methodA(Exchanger<String> exchanger) throws InterruptedException, TimeoutException {
        System.out.println("生成methodA结果");
        String result = "result A";
//        String exchange = exchanger.exchange(result);
        //设置等待交换超时
        String exchange = exchanger.exchange(result, 2, TimeUnit.SECONDS);
        System.out.println("methodA 获取的结果: " + exchange);
    }

    public void methodB(Exchanger<String> exchanger) throws InterruptedException {
        System.out.println("生成methodB结果");
        String result = "result B";
        Thread.sleep(3000L);
        String exchange = exchanger.exchange(result);
        System.out.println("methodB 获取的结果: " + exchange);
    }
}
