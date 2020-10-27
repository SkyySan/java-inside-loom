package fr.umlv.javainsideloom;

import java.util.concurrent.locks.ReentrantLock;

public class Example1 {
    //private final static Object lock = new Object();
    private final static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        var scope = new ContinuationScope("hello1");
        var continuation = new Continuation(scope, ()->{
            /*
            synchronized (lock) {
                Continuation.yield(scope);
            }
            System.out.println("Hello continuation");
            */
            lock.lock();
            try {
                Continuation.yield(scope);
            } finally {
                lock.unlock();
            }
            System.out.println("Hello continuation");
        });

        continuation.run();
        continuation.run();
    }

}
