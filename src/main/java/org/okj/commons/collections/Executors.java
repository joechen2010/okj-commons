/**
 * 
 */
package org.okj.commons.collections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 */
public class Executors {

    /**
     * Create a thread pool , allow set the name prefix of thread to do better trace.
     * 
     * @param nThreads
     * @param threadNamePrefix
     * @return
     */
    public static ExecutorService newFixedThreadPool(int nThreads, String threadNamePrefix) {
        return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), DefaultThreadFactory.create(threadNamePrefix));
    }

    public static ExecutorService newFixedThreadPool(int nThreads, long keepWaitingTime, String threadNamePrefix) {
        return new ThreadPoolExecutor(nThreads, nThreads, keepWaitingTime, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), DefaultThreadFactory.create(threadNamePrefix));
    }

    /**
     * Create a thread Pool , allow use custom queue to do better control
     * 
     * @param nThreads
     * @param workQueue
     * @return
     */
    public static ExecutorService newFixedThreadPool(int nThreads, BlockingQueue<Runnable> workQueue) {
        return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, workQueue);
    }

    /**
     * Create a thread pool, allow set the name prefix of thread and use customed queue.
     * 
     * @param nThreads
     * @param workQueue
     * @param threadNamePrefix
     * @return
     */
    public static ExecutorService newFixedThreadPool(int nThreads, BlockingQueue<Runnable> workQueue,
            String threadNamePrefix) {
        return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, workQueue,
                DefaultThreadFactory.create(threadNamePrefix));
    }
}
