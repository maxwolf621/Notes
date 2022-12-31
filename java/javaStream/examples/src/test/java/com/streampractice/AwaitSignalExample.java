package com.streampractice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

public class AwaitSignalExample {
    
    private Lock lock = new ReentrantLock();
        
    // lock with the condition 
        private Condition condition = lock.newCondition();
    
    public void before() {
        lock.lock();

        try {
            System.out.println("before");
            // sign-all all the thread that are awaiting
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void after() {
        lock.lock();

        try {
            // All threads are awaiting
            condition.await();
            System.out.println("after");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Test
    public void testExample(){
        ExecutorService executorService = 
            Executors.newCachedThreadPool();
        AwaitSignalExample example = new AwaitSignalExample();
        executorService.execute(() -> example.after());
        executorService.execute(() -> example.before());
    } 
}
