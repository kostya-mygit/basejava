package ru.javaops.basejava;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10000;

    private static final Object LOCK = new Object();
    private static final Lock REENTRANT_LOCK = new ReentrantLock();

    private static int counter = 0;
    private static AtomicInteger atomicCounter = new AtomicInteger();

    private static ThreadLocal<SimpleDateFormat> DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("dd.MM.yyyy");
        }

        ;
    };

    private static int accountA = 100;
    private static int accountB = 200;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println(Thread.currentThread().getName());

        //simple Thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ": " + Thread.currentThread().getState());
            }
        }).start();

        //IllegalStateException
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ": " + getState());
                throw new IllegalStateException(); //only current thread is stopped, not JVM
            }
        };
        thread0.start();

        Thread.sleep(100);
        System.out.println(thread0.getName() + ": " + thread0.getState());
        System.out.println();

        MainConcurrency mainConcurrency = new MainConcurrency();

        //various methods increment()
        List<Thread> threadList = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    staticSynchronizedMethodIncrement();
                    /*staticSynchronizedBlockIncrement();
                    staticSynchronizedByLockIncrement();
                    mainConcurrency.notStaticSynchronizedMethodIncrement();
                    mainConcurrency.notStaticSynchronizedBlockIncrement();
                    mainConcurrency.notStaticSynchronizedByLockIncrement();
                    incrementWithReentrantLock();
                    incrementWithAtomicCounter();*/
                }
            });
            thread.start();
            threadList.add(thread);
        }
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        printCounter();

        //CountDownLatch
        System.out.println("Using CountDownLatch instead of join()");
        resetCounter();
        CountDownLatch latch1 = new CountDownLatch(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    staticSynchronizedMethodIncrement();
                }
                latch1.countDown();
            });
            thread.start();
        }
        latch1.await();
        printCounter();

        //CyclicBarrier
        System.out.println("Using CyclicBarrier instead of join()");
        resetCounter();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(THREADS_NUMBER + 1);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    staticSynchronizedMethodIncrement();
                }
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        printCounter();

        //ExecutorService
        System.out.println("Using ExecutorService instead of new Thread()");
        resetCounter();
        CountDownLatch latch2 = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService1 = Executors.newCachedThreadPool();
        for (int i = 0; i < THREADS_NUMBER; i++) {
            executorService1.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    staticSynchronizedMethodIncrement();
                }
                latch2.countDown();
            });
        }
        latch2.await();
        executorService1.shutdown();
        printCounter();

        //Callable + Future
        System.out.println("Using Callable instead of Runnable");
        resetCounter();
        CountDownLatch latch3 = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService2 = Executors.newCachedThreadPool();
        Future<Long> future;
        for (int i = 0; i < THREADS_NUMBER; i++) {
            future = executorService2.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    staticSynchronizedMethodIncrement();
                }
                latch3.countDown();
                return latch3.getCount();
            });
            System.out.println(future.get());
        }
        latch3.await();
        executorService2.shutdown();
        printCounter();

        // LocalThread
        System.out.println("LocalThread");
        resetCounter();
        CountDownLatch latch4 = new CountDownLatch(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    staticSynchronizedMethodIncrement();
                    System.out.println(DATE_FORMAT.get().format(new Date()));
                }
                latch4.countDown();
            });
            thread.start();
        }
        latch4.await();
        printCounter();

        //DeadLock
        /*final String lock1 = "lock1";
        final String lock2 = "lock2";

        System.out.println("DeadLock");
        deadLock(lock1, lock2);
        deadLock(lock2, lock1);*/

        //wait() + notifyAll()
        System.out.println("Transferring");
        for (int i = 0; i < 1; i++) {
            Thread t1 = new Thread(() -> {
                mainConcurrency.transferAtoB(200);
            });
            t1.start();
            System.out.println(t1.getName() + " started");
            Thread.sleep(100);

            Thread t2 = new Thread(() -> {
                mainConcurrency.transferBtoA(300);
            });
            t2.start();
            System.out.println(t2.getName() + " started");
        }
    }

    private static void printCounter() {
        if (atomicCounter.get() > 0) {
            System.out.println("Atomic counter: " + atomicCounter.get());
        } else {
            System.out.println("Counter: " + counter);
        }
        System.out.println();
    }

    private static void resetCounter() {
        counter = 0;
    }

    private static synchronized void staticSynchronizedMethodIncrement() {
        counter++;
    }

    private static void staticSynchronizedBlockIncrement() {
        double sin = Math.sin(30.0); //not need to synchronize
        synchronized (MainConcurrency.class) {
            counter++;
        }
    }

    private static void staticSynchronizedByLockIncrement() {
        double sin = Math.sin(30.0); //not need to synchronize
        synchronized (LOCK) {
            counter++;
        }
    }

    private synchronized void notStaticSynchronizedMethodIncrement() {
        counter++;
    }

    private void notStaticSynchronizedBlockIncrement() {
        double sin = Math.sin(30.0); //not need to synchronize
        synchronized (this) {
            counter++;
        }
    }

    private void notStaticSynchronizedByLockIncrement() {
        double sin = Math.sin(30.0); //not need to synchronize
        synchronized (LOCK) {
            counter++;
        }
    }

    private static void incrementWithReentrantLock() {
        REENTRANT_LOCK.lock();
        try {
            counter++;
        } finally {
            REENTRANT_LOCK.unlock();
        }
    }

    private static void incrementWithAtomicCounter() {
        atomicCounter.incrementAndGet();
    }

    private static void deadLock(Object lock1, Object lock2) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            System.out.println("Waiting for " + lock1);
            synchronized (lock1) {
                System.out.println("Holding " + lock1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Waiting for " + lock2);
                synchronized (lock2) {
                    System.out.println("Holding " + lock2);
                }
            }
        }).start();
    }

    private synchronized void transferAtoB(int amount) {
        while (accountA < amount) {
            System.out.println(Thread.currentThread().getName() + " before wait");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " after wait");
        }
        accountA -= amount;
        accountB += amount;
        System.out.println("transferred from A to B : " + amount);
        System.out.println("accountA=" + accountA + ", accountB=" + accountB);
        notifyAll();
    }

    private synchronized void transferBtoA(int amount) {
        while (accountB < amount) {
            System.out.println(Thread.currentThread().getName() + " before wait");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " after wait");
        }
        accountB -= amount;
        accountA += amount;
        System.out.println("transferred from B to A : " + amount);
        System.out.println("accountA=" + accountA + ", accountB=" + accountB);
        notifyAll();
    }
}
