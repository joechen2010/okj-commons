package org.okj.commons.collections;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This queue have a capacity limitation, when the size of content is reached the cell, it will abandon the oldest data.
 * 
 * @author ehanrli
 * 
 * @param <E>
 */
public class LRUBlockingQueue<E> extends LinkedBlockingQueue<E> {
    /**
	 * 
	 */
    private static final long serialVersionUID = -5285826071057826779L;

    public LRUBlockingQueue(int capacity) {
        super(capacity);
    }

    /**
     * Override the super , add exceed check.
     */
    public boolean add(E e) {
        if (super.offer(e)) {
            return true;
        }
        while (true) {
            ArrayList<E> abandonedData = new ArrayList<E>();
            drainTo(abandonedData, 5);
            log(abandonedData);
            if (super.offer(e)) {
                return true;
            }
        }
    }

    /**
     * @param abandonedData
     */
    protected void log(ArrayList<E> abandonedData) {

    }

    public void put(E e) throws InterruptedException {
        add(e);
    }

    public boolean offer(E e) {
        return add(e);
    }
}
