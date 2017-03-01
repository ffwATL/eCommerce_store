package com.ffwatl.common.exception;

/**
 * Interface indicating that the exception knows how to return the root cause message.
 *
 * @author ffw_ATL.
 */
public interface RootCauseAccessor {

    Throwable getRootCause();

    String getRootCauseMessage();
}