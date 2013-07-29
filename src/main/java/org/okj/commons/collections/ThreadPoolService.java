/**
 * 
 */
package org.okj.commons.collections;

/**
 * @author toy
 * 
 */
public interface ThreadPoolService {

    /**
     * 
     */
    public abstract void init();

    /**
     * @param task
     */
    public abstract void appendTask(ResourceUpdateTask task);

    public abstract void stop();

}
