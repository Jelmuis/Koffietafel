/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beroepsproduct5.Model;

/**
 *
 * @author jelle
 */
public class ThreadClass extends Thread {
    private final int secDelay = 60;
    private Thread t;
    private String threadName;

    public ThreadClass(String name) {
        threadName = name;
        System.out.println("Creating thread: " + threadName);
    }

    public void run() {
        System.out.println("Running thread: " + threadName);
        try {
            for (int i = 10000; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", Cycle: " + i);
                Thread.sleep(secDelay * 1000);
                
            }
        } catch (InterruptedException e) {
            System.out.println("Thread: " + threadName + " interrupted");
        }
        System.out.println("Thread: " + threadName + " exiting...");
    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this.threadName);
            t.start();
        }
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }
}
