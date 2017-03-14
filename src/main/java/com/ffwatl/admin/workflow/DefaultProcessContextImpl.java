package com.ffwatl.admin.workflow;

/**
 * @author ffw_ATL.
 */
public class DefaultProcessContextImpl<T> implements ProcessContext<T> {

    private T seedData;
    private boolean stopEntireProcess = false;

    @Override
    public boolean stopProcess() {
        this.stopEntireProcess = true;
        return true;
    }

    @Override
    public boolean isStopped() {
        return stopEntireProcess;
    }

    @Override
    public void setSeedData(T seedObject) {
        seedData = seedObject;
    }

    @Override
    public T getSeedData() {
        return seedData;
    }
}