package com.ffwatl.common.schedule;

/**
 * @author ffw_ATL.
 */
public interface SingleTimeTimerTaskFactory {

    SingleTimeTimerTask getInstance(Long key);

}