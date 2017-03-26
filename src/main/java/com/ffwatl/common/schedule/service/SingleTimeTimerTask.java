package com.ffwatl.common.schedule.service;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ffw_ATL.
 */
public abstract class SingleTimeTimerTask extends TimerTask {

    private final Long key;
    private LocalDateTime startDateTime;
    private Timer timer;

    public SingleTimeTimerTask(Long key){
        super();
        this.key = key;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleTimeTimerTask timerTask = (SingleTimeTimerTask) o;

        if (!getKey().equals(timerTask.getKey())) return false;
        if (getStartDateTime() != null ? !getStartDateTime().equals(timerTask.getStartDateTime()) : timerTask.getStartDateTime() != null)
            return false;
        return !(getTimer() != null ? !getTimer().equals(timerTask.getTimer()) : timerTask.getTimer() != null);

    }

    @Override
    public int hashCode() {
        int result = getKey().hashCode();
        result = 31 * result + (getStartDateTime() != null ? getStartDateTime().hashCode() : 0);
        result = 31 * result + (getTimer() != null ? getTimer().hashCode() : 0);
        return result;
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