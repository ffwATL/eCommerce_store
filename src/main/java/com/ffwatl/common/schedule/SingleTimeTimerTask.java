package com.ffwatl.common.schedule;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ffw_ATL.
 */
public abstract class SingleTimeTimerTask extends TimerTask {

    private Long key;
    private LocalDateTime startDateTime;
    private Timer timer;

    public LocalDateTime getStartDateTime(){
        return startDateTime;
    }

    public Long getKey() {
        return key;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public SingleTimeTimerTask setKey(Long key) {
        this.key = key;
        return this;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    @Override
    public String toString() {
        return "SingleTimeTimerTask{" +
                "key=" + key +
                ", startDateTime=" + startDateTime +
                ", timer=" + timer +
                '}';
    }
}