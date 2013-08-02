/**
 * 
 */
package org.okj.commons.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.okj.commons.web.jackson.JsonMapper;
import org.okj.commons.web.jackson.JsonMapperFactory;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

/**
 * A special thread pool for avoiding the resource conflication in multi thread situation. It will add lock by the
 * resource id before running the task. Only the thread get the lock with specified resource id can run which can avoid
 * two threads operate the same resource at the same time.
 * 
 * 
 * 
 */
public abstract class SimpleThreadPool<T> implements ThreadPoolService {
    protected Logger log = Logger.getLogger(SimpleThreadPool.class.getName());

    // private static Logger log = Logger.getLogger("dbUpdateLog");

    private JsonMapper jsonMapper = JsonMapperFactory.get();

    private int threadPoolSize = 20;

    private int taskQueueSize = 10 * 10000;

    protected LRUBlockingQueue<T> taskQueue;

    private String threadPoolName;

    private List<WorkerThread> threadPool = Lists.newArrayList();

    private DefaultThreadFactory threadFactory = new DefaultThreadFactory();

    /**
     * @param threadPoolName
     *            the threadPoolName to set
     */
    public void setThreadPoolName(String threadPoolName) {
        this.threadPoolName = threadPoolName;
    }

    /**
     * @return the threadPoolName
     */
    public String getThreadPoolName() {
        return threadPoolName;
    }

    public void setLogAppender(String logAppender) {
        log = Logger.getLogger(logAppender);
    }

    protected List<WorkerThread> getThreadPool() {
        return threadPool;
    }

    /**
     * @param threadPoolSize
     *            the threadPoolSize to set
     */
    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    /**
     * 
     * Notes: it will limit the waiting task in the task queue. When more taskes append the task beyond the size, the
     * old task will be abandoned.
     * 
     * @param taskQueueSize
     *            the taskQueueSize to set
     */
    public void setTaskQueueSize(int taskQueueSize) {
        this.taskQueueSize = taskQueueSize;
    }

    @PostConstruct
    public void init() {
        stop();

        taskQueue = new LRUBlockingQueue<T>(taskQueueSize) {
            /**
             * 
             */
            private static final long serialVersionUID = 2137261878366925010L;

            protected void log(ArrayList<T> abandonedData) {
                log.error("The thread pool has been exausted, abandon these tasks :\n"
                        + jsonMapper.toJson(abandonedData));
            }
        };

        startThreadPool();
    }

    /**
     * 
     */
    private void startThreadPool() {
        threadFactory.setNamePrefix(threadPoolName);
        for (int i = 0; i < threadPoolSize; i++) {
            WorkerThread worker = new WorkerThread();
            worker.start();
            threadPool.add(worker);
        }

    }

    @PreDestroy
    public void stop() {
        if (!CollectionUtils.isEmpty(threadPool)) {
            for (WorkerThread worker : threadPool) {
                worker.stop();
            }
        }
    }

    public void terminate() {
        if (!CollectionUtils.isEmpty(threadPool)) {
            for (WorkerThread worker : threadPool) {
                worker.interrupt();
            }
        }
    }

    /**
     * @return
     */
    public ThreadFactory getThreadFactory() {
        return threadFactory;
    }

    /**
     * @return
     */
    public long getQueueSize() {
        return taskQueue.size();
    }

    public void appendTask(ResourceUpdateTask task) {
        logDebugInfo();
    }

    /**
     * 
     */
    protected void logDebugInfo() {
        log.debug(threadPoolName + " queue size is " + getQueueSize());
        if (taskQueue.size() > 10000) {
            log.warn("The tasks in queue is " + taskQueue.size() + ",it might be not safety,  please check the status");
        }
    }

    public WorkerThread getWorkerThread(Long threadId) {
        if (threadId == null) {
            return null;
        }
        for (WorkerThread worker : threadPool) {
            if (worker.thread.getId() == threadId) {
                return worker;
            }
        }
        return null;
    }

    class WorkerThread implements Runnable {

        private boolean running = true;

        private Thread thread;

        /**
         * 
         */
        public WorkerThread() {
            super();
            this.thread = getThreadFactory().newThread(this);
        }

        public void interrupt() {
            thread.interrupt();
        }

        public void start() {
            thread.start();
        }

        public long getThreadId() {
            return thread.getId();
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            log.info("start the thread in thread pool[" + threadPoolName + "]");
            while (running) {
                T task = null;

                try {
                    task = taskQueue.take();
                } catch (InterruptedException e) {
                    log.error(e);
                }

                if (task != null) {
                    try {
                        runTask(task);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("hello");
                        log.error(e);
                    }
                }
            }
        }

        /*
         * (non-Javadoc)
         * @see com.ericsson.cgc.mic.collect.ResourceThreadPool#stop()
         */
        public void stop() {
            running = false;
        }
    }

    /**
     * @param task
     * 
     */
    abstract protected void runTask(T task);

}
