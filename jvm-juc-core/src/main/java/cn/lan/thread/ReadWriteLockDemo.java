package cn.lan.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock读写锁的代码示例
 * 独占锁 == 写锁 : 该锁在某一时刻只能被一个线程持有
 * 共享锁 == 读锁 : 该锁可以被多个线程共同持有
 *
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache cache = new MyCache();
        //写
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                cache.put(tempInt + "", tempInt + "");
            }, "写线程_"+i).start();
        }
        //读
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                cache.get(tempInt + "");
            }, "读线程_"+i).start();
        }
    }
}

/**
 * 模拟缓存资源类:实现读写锁
 */
class MyCache {
    //缓存更新快，需要用volatile修饰,保证线程之间可见性
    private volatile Map<String, Object> map = new HashMap<>();
    //读写锁
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    /**
     * 写操作:原子 + 独占,整个过程是一个完整的统一体,中间不许被分割,打断
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "正在写入value： " + value);
            //模拟网络传输
            TimeUnit.MILLISECONDS.sleep(300);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t" + "写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rwLock.writeLock().unlock();
        }
    }

    /**
     * 读操作:是有读锁
     * @param key
     */
    public void get(String key) {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "正在读取key： " + key);
            //模拟网络传输
            TimeUnit.MILLISECONDS.sleep(300);
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t" + "读取完成value： " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rwLock.readLock().unlock();
        }
    }
}
