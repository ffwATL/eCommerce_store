package com.ffwatl.admin.workflow;

import java.io.Serializable;

/**
 * @author ffw_ATL.
 */
public interface ProcessContext<T> extends Serializable {

    /**
     * Activly informs the workflow process to stop processing
     * no further activities will be executed
     *
     * @return whether or not the stop process call was successful
     */
    boolean stopProcess();

    /**
     * Is the process stopped
     *
     * @return whether or not the process is stopped
     */
    boolean isStopped();

    /**
     * Provide seed information to this ProcessContext, usually
     * provided at time of workflow kickoff by the containing
     * workflow processor.
     *
     * @param seedObject - initial seed data for the workflow
     */
    void setSeedData(T seedObject);

    /**
     * Returns the seed information
     * @return the seed information;
     */
    T getSeedData();
}