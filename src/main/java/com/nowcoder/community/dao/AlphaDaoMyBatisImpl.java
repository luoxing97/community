package com.nowcoder.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Repository
@Primary
public class AlphaDaoMyBatisImpl implements AlphaDao {
    @Override
    public String select() {
        return "MyBatis";
    }

    public static void main(String[] args) {
        System.out.println("AlphaDaoMyBatisImpl");
        System.out.println("AlphaDaoMyBatisImpl");
        System.out.println("AlphaDaoMyBatisImpl");
        printAB();
        System.out.println("20240610---22");
    }

    public static void printAB(){
        int num = 5;
        System.out.println("sd");
        ThreadFactory threadFactory = new ThreadFactory() {
            int n = 1;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "线程" + n);
            }
        };
        ExecutorService executorService = new ThreadPoolExecutor(2, 2, 1,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>(1), threadFactory);
        ReentrantLock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        //executorService.submit(() -> System.out.println("sdgsdd"));


        executorService.submit(() -> {
            for (int i = 0; i < num; i++) {
                lock.lock();
                countDownLatch.countDown();
                System.out.print("A");
                conditionB.signal();
                try {
                    conditionA.await();
                    conditionB.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
            System.out.println("AAA");
        });
        executorService.execute(() -> {
            for (int i = 0; i < num; i++) {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.lock();
                System.out.print("B");
                conditionA.signal();
                try {
                    conditionB.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
            System.out.println("BBB");
        });

    }
}

