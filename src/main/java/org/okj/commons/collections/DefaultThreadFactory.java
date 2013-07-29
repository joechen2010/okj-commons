/**
 * 
 */
package org.okj.commons.collections;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author toy
 * 
 */
public class DefaultThreadFactory implements java.util.concurrent.ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private String namePrefix;

    DefaultThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
    }

    DefaultThreadFactory(String threadNamePrefix) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = threadNamePrefix;
    }

    /**
     * @param namePrefix
     *            the namePrefix to set
     * @return
     */
    public DefaultThreadFactory setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
        return this;
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }

    public static DefaultThreadFactory create() {
        return new DefaultThreadFactory();
    }

    public static DefaultThreadFactory create(String threadNamePrefix) {
        return new DefaultThreadFactory(threadNamePrefix);
    }
}
