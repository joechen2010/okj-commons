/**
 * 
 */
package org.okj.commons.collections;

/**
 * @author toy
 * 
 */
public interface ResourceLock {
    public boolean lock(String resourceId);

    public Long getLockerThread(String resourceId);

    public void release(String resourceId);

    public abstract void setExpiredTime(long expiredTime);
}
