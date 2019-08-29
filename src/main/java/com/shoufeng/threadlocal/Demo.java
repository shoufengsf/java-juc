package com.shoufeng.threadlocal;

/**
 * 线程局部 (thread-local) 变量
 *
 * @author shoufeng
 */
public class Demo {

    ThreadLocal<String> threadLocal = new ThreadLocal<String>() {

        /**
         * 返回此线程局部变量的当前线程的“初始值”。线程第一次使用 get() 方法访问变量时将调用此方法，但如果线程之前调用了 set(T) 方法，则不会对该线程再调用 initialValue 方法。通常，此方法对每个线程最多调用一次，但如果在调用 get() 后又调用了 remove()，则可能再次调用此方法。
         * 该实现返回 null；如果程序员希望线程局部变量具有 null 以外的值，则必须为 ThreadLocal 创建子类，并重写此方法。通常将使用匿名内部类完成此操作。
         *
         * 返回：
         * 返回此线程局部变量的初始值
         * @return 初始值
         */
        @Override
        protected String initialValue() {
            return super.initialValue();
        }
    };

    public static void main(String[] args) {
        Demo demo = new Demo();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "获取初始值" + demo.getThreadLocalValue());
            demo.setThreadLocalA();
            System.out.println(Thread.currentThread().getName() + "获取设置A后的值" + demo.getThreadLocalValue());
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "获取初始值" + demo.getThreadLocalValue());
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "获取未设置时候的值" + demo.getThreadLocalValue());
            demo.setThreadLocalB();
            System.out.println(Thread.currentThread().getName() + "获取设置B后的值" + demo.getThreadLocalValue());
        }).start();
    }

    public void setThreadLocalA() {
        threadLocal.set("A");
    }

    public void setThreadLocalB() {
        threadLocal.set("B");
    }

    public String getThreadLocalValue() {
        return threadLocal.get();
    }
}
