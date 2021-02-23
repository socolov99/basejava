package com.urise.webapp;

public class DeadLockExample {
    private static final String LOCK_1 = "LOCK_1";
    private static final String LOCK_2 = "LOCK_2";

    public static void main(String[] args) throws InterruptedException {

        Thread thread0 = new Thread(() -> threadRun(LOCK_1, LOCK_2));
        Thread thread1 = new Thread(() -> threadRun(LOCK_2, LOCK_1));

        thread0.start();
        thread1.start();

        Thread.sleep(1000);

        if (thread0.isAlive() && thread1.isAlive()) {
            System.out.println("It's deadlock");
        }
    }

    private static void threadRun(String lock1, String lock2) {
        System.out.println(Thread.currentThread().getName() + " is waiting for " + lock1 + " holding");
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName() + " captured  " + lock1);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is waiting for " + lock2 + " holding");
            synchronized (lock2) {
                //Can't come here
                System.out.println(Thread.currentThread().getName() + " captured  " + lock2);
            }
        }
    }


}