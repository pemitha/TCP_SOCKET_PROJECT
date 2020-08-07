package lk.bank.threadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {

    //changed variable types to private
    private final BlockingQueue<Runnable> blockingQueue;
    private final ThreadExecutor[] threads;

    public ThreadPool(final int numOfThreads) {
        blockingQueue = new LinkedBlockingQueue<>();
        threads = new ThreadExecutor[numOfThreads];

        for (int i = 0; i < numOfThreads; i++) {
            threads[i] = new ThreadExecutor();
            threads[i].start();
        }
    }

    public void execute(final Runnable task) {
        blockingQueue.add(task);
    }

    //Modified a method to shutdown the server
    public void shutdownImmediately() {
        for (int i = 0; i < threads.length; i++) {
            threads[i].shutdownSignal = true;
            threads[i] = null;
        }
    }

    private class ThreadExecutor extends Thread {
        boolean shutdownSignal = false;
        private Runnable taskToPerform = null;

        public void setShutdownSignal(boolean shutdownSignal) {
            this.shutdownSignal = shutdownSignal;
        }

        @Override
        public void run() {
            while (true && !shutdownSignal) {
                taskToPerform = blockingQueue.poll();
                if (taskToPerform != null) {
                    taskToPerform.run();
                }
                if (shutdownSignal) {
                    break;
                }
            }
        }
    }
}
