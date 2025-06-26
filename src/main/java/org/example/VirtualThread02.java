package org.example;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class VirtualThread02 {
    public static void main(String[] args) {
        Set<String> threadNames = ConcurrentHashMap.newKeySet();

        // 使用Executors创建虚拟线程
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        // 创建300万个虚拟线程
        IntStream.range(0, 3_000_000).forEach(i -> {
            executorService.submit(() -> {
                try {
                    Thread.sleep(Duration.ofSeconds(1));
                    String threadInfo = Thread.currentThread().toString();
                    String workName = threadInfo.split("@")[1];
                    threadNames.add(workName);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        });

        // 运行结果为5或者6，电脑性能不同结果不同，但可以看出，运行300W个虚拟线程只需要创建很少的平台线程
        System.out.println(STR."Platform Threads: \{threadNames.size()}");
    }
}
