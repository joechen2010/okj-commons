/**
 * 
 */
package org.okj.commons.collections;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.MapMaker;

/**
 * @author toy
 * 
 */
public class SimpleMemoryResourceLock implements ResourceLock {

    private ConcurrentMap<String, Resource> locks = null;
    private long expiredTime = 2 * 60 * 1000;

    /**
     * 
     */
    @SuppressWarnings("deprecation")
    public SimpleMemoryResourceLock() {
        // avoid the memory leak.
        locks = new MapMaker().concurrencyLevel(4).expiration(1, TimeUnit.HOURS).makeMap();
    }

    /*
     * (non-Javadoc)
     * @see com.ericsson.cgc.mic.collect.ResourceLock#lock(java.lang.String)
     */
    @Override
    public boolean lock(String resourceId) {
        if (resourceId == null) {
            return true;
        }

        Resource locking = new Resource();
        Resource locked = locks.putIfAbsent(resourceId, locking);
        boolean flag = false;

        if (locked == null) {
            flag = true;
        } else if (locked.isExpired()) {
            flag = locks.replace(resourceId, locked, locking);
        }

        return flag;
    }

    /*
     * (non-Javadoc)
     * @see com.ericsson.cgc.mic.collect.ResourceLock#release(java.lang.String)
     */
    @Override
    public void release(String resourceId) {
        if (resourceId != null) {
            locks.remove(resourceId);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ericsson.cgc.mic.collect.ResourceLock#getLockerThread(java.lang.String )
     */
    @Override
    public Long getLockerThread(String resourceId) {
        if (resourceId == null) {
            return null;
        }
        Resource rs = locks.get(resourceId);
        return rs == null ? null : rs.threadId;
    }

    /**
     * @param expiredTime
     *            the expiredTime to set
     */
    @Override
    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    class Resource {
        private Long threadId;

        private long timeStamp;

        /**
         * @param threadId
         */
        public Resource() {
            this.threadId = Thread.currentThread().getId();
            this.timeStamp = System.currentTimeMillis();
        }

        private boolean isExpired() {
            return System.currentTimeMillis() - timeStamp > expiredTime;
        }
    }

}
