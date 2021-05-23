package service.impl;

import java.util.concurrent.LinkedBlockingQueue;

public class WorkerThread extends Thread {

    private final LinkedBlockingQueue<Runnable> queue;

    public WorkerThread(LinkedBlockingQueue<Runnable> queue) {

        this.queue = queue;
    }

    @Override
    public void run() {
        Runnable request;

        while (true) {

            // Poll the Runnable from the queue
            synchronized (queue) {

                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                request = queue.poll();
            }

            // Run the Runnable
            if (request != null) {
                request.run();
            }
        }
    }
}