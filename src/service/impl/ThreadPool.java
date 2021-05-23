package service.impl;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadPool {

    private final Logger logger = Logger.getLogger(ThreadPool.class.getName());

    // The size of the pool
    private final int corePoolSize;

    // Queue for RequestRunnable
    private final LinkedBlockingQueue<Runnable> queue;

    // The list of WorkerThread
    private final WorkerThread[] workerThreads;

    /**
     * Constructor
     *
     * @param corePoolSize corePoolSize
     */
    public ThreadPool(int corePoolSize) {

        this.corePoolSize = corePoolSize;

        // Initialize the queue
        queue = new LinkedBlockingQueue<>();

        // Add the worker threads and start
        workerThreads = new WorkerThread[corePoolSize];

        for (int i = 0; i < corePoolSize; i++) {
            workerThreads[i] = new WorkerThread(queue);
            workerThreads[i].start();
        }

        logger.log(Level.INFO, "The thread pool is created with " + workerThreads.length + " worker threads.");
    }

    /**
     * Execute a RequestRunnable instance to the threadpool
     *
     * @param requestRunnable RequestRunnable
     */
    public void execute(Runnable requestRunnable) {
        synchronized (queue) {
            queue.add(requestRunnable);
            queue.notify();
        }
    }


    /**
     * Get the number of requests waiting in the queue
     *
     * @return the number of waiting requests
     */
    public int getQueuedRequests() {
        return queue.size();
    }
}