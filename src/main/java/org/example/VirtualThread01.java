package org.example;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadFactory;

/**
 * 虚拟线程创建
 */
public class VirtualThread01 {
    /**
     * 使用方式一 构造一个虚拟线程后调用unstarted方法先不启动，然后手动调用start方法启动
     */
    @Test
    public void test1() {
        Thread thread1 = Thread.ofVirtual().name("虚拟线程1").unstarted(new Task());
        thread1.start();
    }

    /**
     * 使用方式二 构造一个虚拟线程并直接启动
     */
    @Test
    public void test2() {
        Thread.ofVirtual().name("虚拟线程2").start(new Task());
    }

    /**
     * 使用方式三 创建一个线程工厂，再利用线程工程创建一个线程后启动
     */
    @Test
    public void test3() {
        ThreadFactory threadFactory = Thread.ofVirtual().name("虚拟线程3").factory();
        Thread thread = threadFactory.newThread(new Task());
        thread.start();
    }

    /**
     * 使用方式四 快速启动一个虚拟线程
     */
    @Test
    public void test4() {
        Thread.startVirtualThread(new Task());
    }

    /**
     * 使用方式五 结构化并发，java21内容，可以创建虚拟线程
     */
    @Test
    public void test5() {
        StructuredTaskScope.ShutdownOnFailure scope = new StructuredTaskScope.ShutdownOnFailure();
        scope.fork(() -> {
            System.out.println(Thread.currentThread());
            return "success";
        });
    }

    /**
     * 使用方式六 通过Executors创建
     */
    @Test
    public void test6() {
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        executorService.submit(new Task());
        executorService.submit(new Task());
        executorService.submit(new Task());
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread());
        }
    }
}
