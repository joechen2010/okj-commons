/**
 * 
 */
package org.okj.commons.collections;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.okj.commons.web.jackson.JsonMapper;
import org.okj.commons.web.jackson.JsonMapperFactory;

/**
 * Use this one to do a async update , it can control the concurrent work thread to database.
 * 
 * 
 * 
 */
public class FixedSizeTaskThreadPool {
    private Logger log = Logger.getLogger(FixedSizeTaskThreadPool.class.getName());
    // private static Logger log = Logger.getLogger("dbUpdateLog");

    private JsonMapper jsonMapper = JsonMapperFactory.get();

    private int workerThreads = 10;

    private ExecutorService threadPool;

    private int taskQueueSize = 10 * 10000;

    private LRUBlockingQueue<Runnable> taskQueue;

    private String threadPoolName;

    /**
     * @param threadPoolName
     *            the threadPoolName to set
     */
    public void setThreadPoolName(String threadPoolName) {
        this.threadPoolName = threadPoolName;
    }

    public void setLogAppender(String logAppender) {
        log = Logger.getLogger(logAppender);
    }

    /**
     * @param workerThreads
     *            the workerThreads to set
     */
    public void setWorkerThreads(int workerThreads) {
        this.workerThreads = workerThreads;
    }

    /**
     * @param taskQueueSize
     *            the taskQueueSize to set
     */
    public void setTaskQueueSize(int taskQueueSize) {
        this.taskQueueSize = taskQueueSize;
    }

    @PostConstruct
    public void init() {
        if (threadPool != null) {
            threadPool.shutdown();
        }
        taskQueue = new LRUBlockingQueue<Runnable>(taskQueueSize) {
            /**
             * 
             */
            private static final long serialVersionUID = 2137261878366925010L;

            protected void log(ArrayList<Runnable> abandonedData) {
                log.error("The thread pool has been exausted, abandon the datas:\n" + jsonMapper.toJson(abandonedData));
            }
        };

        // add thread PRIOIRTY LATE
        threadPool = Executors.newFixedThreadPool(workerThreads, taskQueue, threadPoolName);
    }

    @PreDestroy
    public void stop() {
        if (threadPool != null) {
            threadPool.shutdown();
        }
    }

    /**
     * @return
     */
    public long getQueueSize() {
        return taskQueue.size();
    }

    public void appendTask(Runnable asyncTask) {
        log.debug(threadPoolName + " queue size is " + getQueueSize());
        if (taskQueue.size() > 10000) {
            log.warn("The tasks in queue is " + taskQueue.size() + ",it might be not safety,  please check the status");
        }
        threadPool.execute(asyncTask);
    }

}
