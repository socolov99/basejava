package com.urise.webapp;

public class DeadLockExample {
    private static final Object LOCK_1 = new Object();
    private static final Object LOCK_2 = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            System.out.println("thread1 is waiting for LOCK1 holding");
            synchronized (LOCK_1) {
                System.out.println("thread1 captured  LOCK1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread1 is waiting for LOCK2 holding");
                synchronized (LOCK_2) {
                    //Can't come here
                    System.out.println("thread1 captured  LOCK2");
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            System.out.println("thread2 is waiting for LOCK2 holding");
            synchronized (LOCK_2) {
                System.out.println("thread2 captured  LOCK2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread2 is waiting for LOCK1 holding");
                synchronized (LOCK_1) {
                    //Can't come here
                    System.out.println("thread2 captured  LOCK1");
                }
            }
        });

        thread1.start();
        thread2.start();

        Thread.sleep(1000);

        if (thread1.isAlive() && thread2.isAlive()) {
            System.out.println("It's deadlock");
        }
    }
}