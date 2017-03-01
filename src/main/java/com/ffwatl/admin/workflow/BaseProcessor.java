package com.ffwatl.admin.workflow;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.core.OrderComparator;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Base class for all Workflow Processors.  Responsible of keeping track of an ordered collection
 * of {@link Activity Activities}
 *
 * @author ffw_ATL.
 */
public abstract class BaseProcessor implements Processor {

    private static final Logger LOGGER = LogManager.getLogger();

    protected List<Activity<ProcessContext<?>>> activities = new ArrayList<>();
    protected List<ModuleActivity> moduleActivities = new ArrayList<>();
    private String beanName;
    protected ErrorHandler defaultErrorHandler;
    private boolean autoRollbackOnError = true;

    /**
     * Called after the properties have been set, Ensures the list of activities
     *  is not empty and each activity is supported by this Workflow Processor
     */
    @PostConstruct
    private void init(){
        if (CollectionUtils.isEmpty(activities) || beanName == null) {
            throw new BeanCreationException(beanName + ". Property: activities. No activities were wired for this workflow");
        }

        //sort the activities based on their configured order
        OrderComparator.sort(activities);

        HashSet<String> moduleNames = new HashSet<>();
        for (Iterator<Activity<ProcessContext<?>>> iter = activities.iterator(); iter.hasNext();) {
            Activity<? extends ProcessContext<?>> activity = iter.next();
            if ( !supports(activity)) {
                throw new BeanInitializationException("The workflow processor [" + beanName + "] does " +
                        "not support the activity of type"+activity.getClass().getName());
            }

            if (activity instanceof ModuleActivity) {
                moduleActivities.add((ModuleActivity) activity);
                moduleNames.add(((ModuleActivity) activity).getModuleName());
            }
        }

        if (CollectionUtils.isNotEmpty(moduleActivities)) {
            //log the fact that we've got some modifications to the workflow
            StringBuffer message = new StringBuffer();
            message.append("The following modules have made changes to the ")
                    .append(getBeanName())
                    .append(" workflow: ");
            message.append(Arrays.toString(moduleNames.toArray()));
            message.append("\n");
            message.append("The final ordering of activities for the ")
                    .append(getBeanName())
                    .append(" workflow is: \n");

            ArrayList<String> activityNames = new ArrayList<>();
            CollectionUtils.collect(activities, new Transformer() {

                @Override
                public Object transform(Object input) {
                    return ((Activity) input).getBeanName();
                }
            }, activityNames);

            message.append(Arrays.toString(activityNames.toArray()));
            LOGGER.debug("Configured bean "+beanName+" with following activities: "+ message.toString());
        }
    }

    public String getBeanName(){
        return beanName;
    }

    /**
     * Whether or not the ActivityStateManager should automatically attempt to rollback any RollbackHandlers registered.
     * If false, rolling back will require an explicit call to ActivityStateManagerImpl.getStateManager().rollbackAllState().
     * The default value is true.
     *
     * @return Whether or not the ActivityStateManager should automatically attempt to rollback
     */
    public boolean getAutoRollbackOnError() {
        return autoRollbackOnError;
    }

    /**
     * Returns a filtered set of {@link #getActivities()} that have implemented the {@link ModuleActivity} interface. This
     * set of module activities is only set once during {@link #init()}, so if you invoke
     * {@link #setActivities(List)} after the bean has been initialized you will need to manually reset the list of module
     * activities as well (which could be achieved by manually invoking {@link #init()}).
     */
    public List<ModuleActivity> getModuleActivities() {
        return moduleActivities;
    }

    public ErrorHandler getDefaultErrorHandler() {
        return defaultErrorHandler;
    }

    public List<Activity<ProcessContext<?>>> getActivities() {
        return activities;
    }


    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    /**
     * Set whether or not the ActivityStateManager should automatically attempt to rollback any RollbackHandlers registered.
     * If false, rolling back will require an explicit call to ActivityStateManagerImpl.getStateManager().rollbackAllState().
     * The default value is true.
     *
     * @param autoRollbackOnError Whether or not the ActivityStateManager should automatically attempt to rollback
     */
    public void setAutoRollbackOnError(boolean autoRollbackOnError) {
        this.autoRollbackOnError = autoRollbackOnError;
    }

    @Override
    public void setDefaultErrorHandler(ErrorHandler defaultErrorHandler) {
        this.defaultErrorHandler = defaultErrorHandler;
    }

    /**
     * Sets the collection of Activities to be executed by the Workflow Process
     *
     * @param activities ordered collection (List) of activities to be executed by the processor
     */
    @Override
    public void setActivities(List<Activity<ProcessContext<?>>> activities) {
        this.activities = activities;
    }

}