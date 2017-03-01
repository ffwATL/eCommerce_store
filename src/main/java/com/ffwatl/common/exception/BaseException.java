package com.ffwatl.common.exception;

/**
 * Base exception class for FFWATLExceptions that understands root cause messages.
 * @author ffw_ATL.
 */
public class BaseException extends Exception implements RootCauseAccessor {

    private Throwable rootCause;

    public BaseException(){
        super();
    }

    public BaseException(String message){
        super(message);
        this.rootCause = this;
    }

    public BaseException(Throwable cause){
        super(cause);

        if(cause != null){
            rootCause = findRootCause(cause);
        }
    }

    public BaseException(String message, Throwable cause){
        super(message, cause);

        if(cause != null){
            rootCause = findRootCause(cause);
        }else {
            rootCause = this;
        }
    }

    private Throwable findRootCause(Throwable cause) {
        Throwable rootCause = cause;
        while (rootCause != null && rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

    @Override
    public Throwable getRootCause() {
        return rootCause;
    }

    @Override
    public String getRootCauseMessage() {
        if(rootCause != null){
            return rootCause.getMessage();
        }
        return getMessage();
    }
}
