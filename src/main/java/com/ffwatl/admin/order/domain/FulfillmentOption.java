package com.ffwatl.admin.order.domain;


import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.order.service.FulfillmentType;

import java.io.Serializable;

public interface FulfillmentOption extends Serializable {
    long getId();
    /**
     * Gets the name displayed to the user when they selected the FulfillmentOption for
     * their order. This might be "2-day" or "Super-saver shipping";
     *
     * @return the display name for this option.
     */
    I18n getName();

    /**
     * Gets the long description for this option which can be shown to the user
     * to provide more information about the option they are selecting. An example
     * might be that this is shipped the next business day or that it requires additional
     * processing time;
     *
     * @return the description to display to the user.
     */
    I18n getLongDescription();

    /**
     * Gets the type of fulfillment that this option supports;
     * @return the type of this option.
     */
    FulfillmentType getFulfillmentType();


    FulfillmentOption setId(long id);

    /**
     * Set the display name for this option that will be shown to the user to select from
     * such as "2-day" or "Express" or "Super-saver shipping"
     *
     * @param name - the display name for this option
     */
    FulfillmentOption setName(I18n name);

    /**
     * Sets the long description for this option to show to the user when they select an option
     * for fulfilling their order
     *
     * @param longDescription the description to show to the user.
     */
    FulfillmentOption setLongDescription(I18n longDescription);

    /**
     * Sets the type of fulfillment that this option supports
     * @param fulfillmentType type of fulfillment
     */
    FulfillmentOption setFulfillmentType(FulfillmentType fulfillmentType);
}