/**
 * 
 */
package org.okj.commons.collections;

/**
 * @author toy
 * 
 */
public class ResourceUpdateTask implements Runnable {

    private String resourceId;

    /**
     * @param resourceId
     */
    public ResourceUpdateTask(String resourceId) {
        super();
        this.resourceId = resourceId;
    }

    public ResourceUpdateTask() {
        super();
    }

    public boolean noResourceId() {
        return resourceId == null;
    }

    /**
     * @return the resourceId
     */
    public String getResourceId() {
        return resourceId;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {

    }

}
