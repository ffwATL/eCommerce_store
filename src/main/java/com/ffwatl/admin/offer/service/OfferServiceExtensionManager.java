package com.ffwatl.admin.offer.service;


import com.ffwatl.common.extension.ExtensionManager;
import org.springframework.stereotype.Service;

@Service("offer_service_extension_manager")
public class OfferServiceExtensionManager extends ExtensionManager<OfferServiceExtensionHandler> {


    public static final String STOP_PROCESSING = "stopProcessing";

    /**
     * Should take in a className that matches the ExtensionHandler interface being managed.
     */
    public OfferServiceExtensionManager() {
        super(OfferServiceExtensionHandler.class);
    }
}