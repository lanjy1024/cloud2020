package cn.lan.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * CountDownLatch的代码示例2
 * 模拟并发问题:阻止每个子线程，直到所有其他子线程都已启动,再去执行业务代码;
 * 这种模式对于尝试重现并发错误非常有用，因为它可以用来强制数千个线程尝试并行执行某些逻辑。
 *
 */
public class CountDownLatchDemo2 {
    public static void main(String[] args) throws InterruptedException {
        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch readyThreadCounter = new CountDownLatch(5);
        CountDownLatch callingThreadBlocker = new CountDownLatch(1);
        CountDownLatch completedThreadCounter = new CountDownLatch(5);

        List<Thread> workers = Stream.generate(() -> new Thread(new WaitingWorker(outputScraper, readyThreadCounter, callingThreadBlocker, completedThreadCounter)))
                .limit(5)
                .collect(toList());
        //Lambda表达式遍历List集合
        workers.forEach(Thread::start);
        //readyThreadCounter等待所有子线程有启动
        readyThreadCounter.await();
        outputScraper.add(Thread.currentThread().getName()+"线程:Workers ready");
        //readyThreadCounter确保所有子线程都启动之后,开始执行业务逻辑
        callingThreadBlocker.countDown();
        completedThreadCounter.await();
        outputScraper.add(Thread.currentThread().getName()+"线程:Workers complete");
        //Lambda表达式遍历List集合
        outputScraper.forEach((str) -> {
            System.out.println(str);
        });
    }
}



class WaitingWorker implements Runnable {

    private List<String>   outputScraper;
    private CountDownLatch readyThreadCounter;
    private CountDownLatch callingThreadBlocker;
    private CountDownLatch completedThreadCounter;

    public WaitingWorker(
            List<String> outputScraper,
            CountDownLatch readyThreadCounter,
            CountDownLatch callingThreadBlocker,
            CountDownLatch completedThreadCounter) {

        this.outputScraper = outputScraper;
        this.readyThreadCounter = readyThreadCounter;
        this.callingThreadBlocker = callingThreadBlocker;
        this.completedThreadCounter = completedThreadCounter;

    }

    @Override
    public void run() {
        //readyThreadCounter确保所有子线程都启动
        readyThreadCounter.countDown();
        try {
            callingThreadBlocker.await();
            outputScraper.add(Thread.currentThread().getName()+"线程:Counted down");
            TimeUnit.SECONDS.sleep(2);//do something
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            completedThreadCounter.countDown();
        }

    }

}