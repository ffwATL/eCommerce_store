package com.ffwatl.common.extension;


import org.apache.commons.beanutils.BeanComparator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExtensionManager<T extends ExtensionHandler> implements InvocationHandler {

    private boolean handlersSorted = false;

    private static final String LOCK_OBJECT = "EM_LOCK";

    private final T extensionHandler;

    private List<T> handlers = new ArrayList<>();

    /**
     * Should take in a className that matches the ExtensionHandler interface being managed.
     * @param _clazz
     */
    @SuppressWarnings("unchecked")
    public ExtensionManager(Class<T> _clazz) {
        extensionHandler = (T) Proxy.newProxyInstance(_clazz.getClassLoader(),
                new Class[]{_clazz},
                this);
    }

    public T getProxy() {
        return extensionHandler;
    }

    /**
     * If you are attempting to register a handler with this manager and are invoking this outside of
     * an {@link ExtensionManager} subclass, consider using {@link #registerHandler(ExtensionHandler)} instead.
     *
     * While the sorting of the handlers prior to their return is thread safe, adding directly to this list is not.
     *
     * @return a list of handlers sorted by their priority
     * @see {@link #registerHandler(ExtensionHandler)}
     */
    public List<T> getHandlers() {
        if (!handlersSorted) {
            synchronized (LOCK_OBJECT) {
                sortHandlers();
            }
        }
        return handlers;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void sortHandlers() {
        if (!handlersSorted) {
            Comparator fieldCompare = new BeanComparator("priority");
            Collections.sort(handlers, fieldCompare);
            handlersSorted = true;
        }
    }

    /**
     * Intended to be invoked from the extension handlers themselves. This will add the given handler to this
     * manager's list of handlers. This also checks to ensure that the handler has not been already registered
     * with this {@link ExtensionManager} by checking the class names of the already-added handlers.
     *
     * This method is thread safe.
     *
     * @param handler the handler to register with this extension manager
     * @return true if the handler was successfully registered, false if this handler was already contained
     * in the list of handlers for this manager
     */
    public boolean registerHandler(T handler) {
        synchronized (LOCK_OBJECT) {
            if(handler == null) return false;

            for (T item : this.handlers) {
                if (item.getClass().equals(handler.getClass())) {
                    return false;
                }
            }
            this.handlers.add(handler);
            handlersSorted = false;

            return true;
        }
    }

    public void setHandlers(List<T> handlers) {
        this.handlers = handlers;
    }

    /**
     * Utility method that is useful for determining whether or not an ExtensionManager implementation
     * should continue after processing a ExtensionHandler call.
     *
     * By default, returns true for CONTINUE
     */
    public boolean shouldContinue(ExtensionResultStatusType result, ExtensionHandler handler,
                                  Method method, Object[] args) {
        if (result != null) {
            if (ExtensionResultStatusType.HANDLED_STOP.equals(result)) {
                return false;
            }else if (ExtensionResultStatusType.HANDLED.equals(result) && ! continueOnHandled()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether or not this extension manager continues on {@link ExtensionResultStatusType}.HANDLED.
     */
    public boolean continueOnHandled() {
        return false;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean notHandled = true;
        for (ExtensionHandler handler : getHandlers()) {
            try {
                if (handler.isEnabled()) {
                    ExtensionResultStatusType result = (ExtensionResultStatusType) method.invoke(handler, args);
                    if (!ExtensionResultStatusType.NOT_HANDLED.equals(result)) {
                        notHandled = false;
                    }
                    if (!shouldContinue(result, handler, method, args)) {
                        break;
                    }
                }
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        }
        return notHandled ? ExtensionResultStatusType.NOT_HANDLED : ExtensionResultStatusType.HANDLED;
    }
}