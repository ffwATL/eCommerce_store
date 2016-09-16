package com.ffwatl.domain;

import com.ffwatl.util.WebUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;


public class Util {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            logger.info("Start conversion.");
            WebUtil.cssMinToNormal(new File("main.css"), new File("out2.css"));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info("Finish conversion.");
    }
}