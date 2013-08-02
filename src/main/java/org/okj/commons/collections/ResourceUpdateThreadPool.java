/**
 * 
 */
package org.okj.commons.collections;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;

import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;

/**
 * A special thread pool for avoiding the resource conflication in multi thread situation. It will add lock by the
 * resource id before running the task. Only the thread get the lock with specified resource id can run which can avoid
 * two threads operate the same resource at the same time.
 * 
 * 
 * 
 */
public class ResourceUpdateThreadPool extends SimpleThreadPool<ResourceUpdateTask> {
    private ResourceLock resourceLock;

    private ConcurrentMap<Long, ConcurrentLinkedQueue<ResourceUpdateTask>> reassignedTasks = Maps.newConcurrentMap();

    /**
     * @param resourceLock
     *            the resourceLock to set
     */
    public void setResourceLock(ResourceLock resourceLock) {
        this.resourceLock = resourceLock;
    }

    @PostConstruct
    public void init() {
        Assert.notNull(resourceLock, "Cannot find the ResourceLock Object");
        super.init();
        for (WorkerThread thread : getThreadPool()) {
            ConcurrentLinkedQueue<ResourceUpdateTask> reAssignedTaskQueue = Queues.newConcurrentLinkedQueue();
            reassignedTasks.put(thread.getThreadId(), reAssignedTaskQueue);
        }

    }

    public void appendTask(ResourceUpdateTask task) {
        logDebugInfo();
        taskQueue.add(task);
    }

    /*
     * (non-Javadoc)
     * @see com.ericsson.cgc.mic.collect.SimpleThreadPool#runTask(java.lang.Object)
     */
    @Override
    protected void runTask(ResourceUpdateTask task) {
        runCurrentTask(task);
        runReassignedTasks(task);
    }

    private void runCurrentTask(ResourceUpdateTask task) {
        log.debug("Get a task to run | resource id :  " + task.getResourceId() + " task:" + task.getClass().getName());
        if (task.noResourceId() || resourceLock.lock(task.getResourceId())) {
            execute(task);
            return;
        }

        log.info("Cannot get the lock for resource:" + task.getResourceId() + ", assign the task to the Locker thread");
        // try to assign the task to locker thread to run late.
        Long runnerThreadId = getRunnerThreadId(task);
        WorkerThread locker = getWorkerThread(runnerThreadId);
        if (locker != null) {
            reassignTaskToLockerThread(locker, task);
        } else {
            taskQueue.add(task);
        }

    }

    /**
     * @param task
     * @param locker
     */
    private void reassignTaskToLockerThread(WorkerThread locker, ResourceUpdateTask task) {
        reassignedTasks.get(locker.getThreadId()).add(task);
    }

    /**
     * @param task
     * @return
     */
    private Long getRunnerThreadId(ResourceUpdateTask task) {
        return resourceLock.getLockerThread(task.getResourceId());
    }

    private void runReassignedTasks(ResourceUpdateTask task) {
        ConcurrentLinkedQueue<ResourceUpdateTask> queue = reassignedTasks.get(Thread.currentThread().getId());
        ResourceUpdateTask assigned = queue.poll();
        while (assigned != null) {
            if (assigned != null) {
                execute(assigned);
            }
            assigned = queue.poll();
        }
    }

    private void execute(ResourceUpdateTask task) {
        try {
            task.run();
        } catch (Exception e) {
            log.error(e);
        } finally {
            resourceLock.release(task.getResourceId());
        }
    }
}
