package com.recluit.workshop.communication.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class MessageListener implements IListener {
    private final static Logger logger = LoggerFactory.getLogger(MessageListener.class);


    @Override
    public void onMessage(String message) {
        logger.debug("Datos recibidos: {}", message);
    }
}
