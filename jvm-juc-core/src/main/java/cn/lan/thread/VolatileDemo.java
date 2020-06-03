package cn.lan.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @类描述：验证volatile的可见性
 * volatile关键字是java虚拟机提供的轻量级的同步机制；
 * 有以下3个特点：
 * 1、保证可见性；
 * 2、不保证原子性；(操作结果一致性)
 * 3、禁止指令重排；
 * @创建人：lanjy
 * @创建时间：2020/3/26
 */
public class VolatileDemo {
    public static void main(String[] args) throws InterruptedException {
//        volatileVisibilityDemo1();
        volatileVisibilityDemo();
//        atomicDemo();
    }




    /**
     * volatile不保证原子性的验证
     * 开启多个线程，同时对有volatile修饰的变量进行修改，无法保证结果一致性
     * 如何解决原子性
     * 1、加sync
     * 2、使用JUC下的原子类
     * @throws InterruptedException
     */
    private static void atomicDemo() throws InterruptedException {
        System.out.println("原子性测试");
        PhoneRepertory phone=new PhoneRepertory();
        //模拟并发：for循环开启20个线程,每个线程对手机库存减1000
        //正常情况是20个线程执行完之后，手机库存为0,但由于volatile无法保证原子性，最终就可能是0，有可能不是0
        //原子性，即最终一致性

        //確保20个线程都跑完
        CountDownLatch countDownLatch = new CountDownLatch(20);
        for (int i = 1; i <= 20; i++) {
            new Thread(()->{
                for (int j = 0; j <1000 ; j++) {
                    //不保证原子性
                    phone.decrement();
                    //使用AtomicInteger，保证原子性
                    phone.decrementAtomic();
                }
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        //等待20个项目都执行完毕之后，去查手机库存
        //Thread.activeCount() > 2，线程数大于2（程序启动时有两个线程，main线程和gc线程）
        /*while(Thread.activeCount() > 2){
            //Thread.yield();线程让步；业务代码使用这个函数需慎重。
            Thread.yield();
        }*/
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t 最终的库存volatile number是："+phone.number);
        System.out.println(Thread.currentThread().getName()+"\t 最终的库存AtomicInteger atomicInteger是："+phone.atomicInteger);
    }


    /**
     * 验证volatile的可见性
     * volatile可以保证可见性，及时通知其它线程主物理内存的值已被修改
     */
    private static void volatileVisibilityDemo() {
        System.out.println("可见性测试");
        PhoneRepertory myData=new PhoneRepertory();//资源类
        //启动一个线程操作共享数据
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
                myData.decrementAtomic();
                System.out.println(Thread.currentThread().getName()+"\t update atomicInteger value: "+myData.atomicInteger.get());
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        },"线程_AAA").start();

        //主线程读取PhoneRepertory中number的值,如果线程_AA修改了PhoneRepertory中number的值，而主线程的number的值还一直是初始值，就会在这里死循环
        while (myData.atomicInteger.get() == 20000){
            //main线程持有共享数据的拷贝，一直为20000
            System.out.println("myData.volatileNumber==20000:true");
        }
        System.out.println(Thread.currentThread().getName()+"跳出循环=========");
        System.out.println(Thread.currentThread().getName()+"\t mission is over. main get volatileNumber value: "+myData.atomicInteger.get());
    }

    private static void volatileVisibilityDemo1() {
        System.out.println("可见性测试--反例");
        PhoneRepertory myData=new PhoneRepertory();//资源类
        //启动一个线程操作共享数据
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
                myData.setNumberTo60();
                System.out.println(Thread.currentThread().getName()+"\t update number value: "+myData.number);
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        },"线程_AAA").start();

        //主线程读取PhoneRepertory中number的值,如果线程_AA修改了PhoneRepertory中number的值，而主线程的number的值还一直是初始值，就会在这里死循环
        while (myData.number==20000){
            //main线程持有共享数据的拷贝，一直为20000
            //System.out.println("myData.number==20000:true");
        }

        System.out.println(Thread.currentThread().getName()+"\t mission is over. main get number value: "+myData.number);
    }
}

class PhoneRepertory{
    //volatile无法保证原子性,保证可见性；
    volatile int volatileNumber=20000;
    int number=20000;

    //AtomicInteger 是一个 java.util.concurrent(简称JUC) 包提供的一个原子类，
    // 通过这个类可以对 Integer 进行一些原子操作。
    // 主要是依赖于 sun.misc.Unsafe 提供的一些 native 方法保证操作的原子性。
    AtomicInteger atomicInteger=new AtomicInteger(20000);

    public void setVolatileNumberTo60(){
        this.volatileNumber=60;
    }
    public void setNumberTo60(){
        this.number=60;
    }
    //volatile不保证原子性
    public void decrement(){
        volatileNumber--;
    }

    public void decrementAtomic(){
        atomicInteger.getAndDecrement();
    }
}
