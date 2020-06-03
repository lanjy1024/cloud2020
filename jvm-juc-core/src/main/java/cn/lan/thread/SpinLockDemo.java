package cn.lan.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁的代码示例
 * 线程_AA	 尝试获取锁 ...
 * 线程_AA	 获取锁===成功...
 * 线程_BB	 尝试获取锁 ...
 * 线程_AA	 释放锁 ...
 * 线程_BB	 获取锁===成功...
 * 线程_BB	 释放锁 ...
 */
public class SpinLockDemo {
    //原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnlock();
    }, "线程_AA").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnlock();
        }, "线程_BB").start();
    }

    /**
     * 自旋锁,while循环尝试获取锁
     */
    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t" + " 尝试获取锁 ...");
        while (!atomicReference.compareAndSet(null, thread)) { }
        System.out.println(Thread.currentThread().getName() + "\t" + " 获取锁===成功...");
    }
    /**
     * 自旋锁,CAS比较--尝试释放锁
     */
    public void myUnlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t" + " 释放锁 ...");
    }
}
