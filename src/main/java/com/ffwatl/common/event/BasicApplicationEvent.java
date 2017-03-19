package com.ffwatl.common.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base abstract ApplicationEvent that provides a marker for application events and provides a default
 * context map.
 *
 * @author ffw_ATL.
 */
public abstract class BasicApplicationEvent extends ApplicationEvent {

    private transient final Map<String, Object> context = new ConcurrentHashMap<>();
    /**

     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public BasicApplicationEvent(Object source) {
        super(source);
    }

    /**
     * Context map that allows generic objects / properties to be passed around on events. This map is synchronized.
     */
    public Map<String, Object> getContext() {
        return context;
    }
}