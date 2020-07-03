package ru.javaops.basejava;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10000;
    private static int counter = 0;
    private static final Object LOCK = new Object();

    private static int accountA = 100;
    private static int accountB = 200;
    private static final Object LOCK_A = new Object();
    private static final Object LOCK_B = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ": " + getState());
                throw new IllegalStateException(); //only current thread is stopped, not JVM
            }
        };
        thread0.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ": " + Thread.currentThread().getState());
            }
        }).start();

        Thread.sleep(100);
        System.out.println(thread0.getName() + ": " + thread0.getState());

        MainConcurrency mainConcurrency = new MainConcurrency();

        List<Thread> threadList = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    staticIncrement1();
                    //staticIncrement2();
                    //staticIncrement3();
                    //mainConcurrency.notStaticIncrement1();
                    //mainConcurrency.notStaticIncrement2();
                    //mainConcurrency.notStaticIncrement3();
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

        System.out.println(counter);
        System.out.println();

        System.out.println("Transferring");
        for (int i = 0; i < 1; i++) {
            Thread t1 = new Thread(() -> {
                mainConcurrency.transferAtoB(200);
            });
            t1.start();
            System.out.println(t1.getName() + " started");

            Thread t2 = new Thread(() -> {
                mainConcurrency.transferBtoA(300);
            });
            t2.start();
            System.out.println(t2.getName() + " started");
        }

    }

    private static synchronized void staticIncrement1() {
        counter++;
    }

    private static void staticIncrement2() {
        double sin = Math.sin(30.0); //not need to synchronize
        synchronized (MainConcurrency.class) {
            counter++;
        }
    }

    private static void staticIncrement3() {
        double sin = Math.sin(30.0); //not need to synchronize
        synchronized (LOCK) {
            counter++;
        }
    }

    private synchronized void notStaticIncrement1() {
        counter++;
    }

    private void notStaticIncrement2() {
        double sin = Math.sin(30.0); //not need to synchronize
        synchronized (this) {
            counter++;
        }
    }

    private void notStaticIncrement3() {
        double sin = Math.sin(30.0); //not need to synchronize
        synchronized (LOCK) {
            counter++;
        }
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
