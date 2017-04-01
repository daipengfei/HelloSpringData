package com.april.fourth.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by daipengfei
 * on 2017/4/1.
 */
public class LogConcurrent {

    static final Logger logger = LoggerFactory.getLogger(LogConcurrent.class);

    static BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(200);
        for (int i = 0; i < 200; i++) {
            new LocalThread("local-thread-" + i).start();
            latch.countDown();
        }
        latch.await();
        for (int i = 0; i < 10000; i++) {
            queue.offer("Hello World! Num = " + i);
        }
    }

    static class LocalThread extends Thread {
        public LocalThread(String name) {
            super(name);
        }

        public void run() {
            while (true) {
                String logString = queue.poll();
                if (logString == null) {
                    continue;
                }
                logger.info(logString);
            }
        }

    }

}
