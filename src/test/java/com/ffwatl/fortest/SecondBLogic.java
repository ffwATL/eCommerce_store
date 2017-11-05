package com.ffwatl.fortest;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * @author ffw_ATL.
 */
public class SecondBLogic implements BLogic, Handler<SOAPMessageContext>{
    @Override
    public void method_1() {

    }

    @Override
    public void method_2() {

    }

    @Override
    public String getReport() {
        return null;
    }


    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        return false;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    @Override
    public void close(MessageContext context) {

    }
}
