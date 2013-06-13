/*
* :tabSize=4:indentSize=4:noTabs=true:
* :folding=explicit:collapseFolds=1:
*
* Copyright (c) 2003
*
*/
package com.recluit.workshop.communication.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Es la responsable de conectarse con BRITE enviar una solicitud y obtener una
 * respuesta.
 *
 *@author     JcSO
 */
public class SocketConnection {

    private final static Logger logger = LoggerFactory.getLogger(SocketConnection.class);

    // Constants
    private static final byte ETX = 0x03;
    private static final int REQUEST_MESSAGE_SIZE = 127;

    // Variables
    private String host;
    private int port;
    private int timeout;

    private Socket connection;

    private BufferedInputStream in;
    private BufferedOutputStream out;

    private List<IListener> listeners = new ArrayList<IListener>();

    /**
     *  Constructor principal de la clase SocketConnection
     */
    public SocketConnection(String host, int port, int timeout) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
    }

    public void connect() throws IOException {
        try {
            connection = new Socket(host, port);
            connection.setSoTimeout(timeout);

            out = new BufferedOutputStream(connection.getOutputStream());
            in = new BufferedInputStream(connection.getInputStream());
        } catch (UnknownHostException e) {
            logger.error("Ha ocurrido un error:", e);
            throw new UnknownHostException(e.getMessage());
        }
        catch (java.net.ConnectException e) {
            logger.error("Ha ocurrido un error:", e);
            throw new java.net.ConnectException(e.getMessage());
        }
        catch (IOException e) {
            logger.error("Ha ocurrido un error:", e);
            throw new IOException(e.getMessage());
        }
    }

    public void sendData(String data) throws IOException {
        try {
            data = StringUtils.padRight(data, REQUEST_MESSAGE_SIZE);

            out.write(data.getBytes()); //obtenemos un arreglo de cracteres
            out.write(ETX);
            out.flush();
        } catch (IOException e) {
            logger.error("Ha ocurrido un error:", e);
            throw new IOException(e.getMessage());
        }
    }

    public void receiveData() throws IOException {
        StringBuilder data = new StringBuilder();
        int b;

        while ((b = in.read()) != ETX) {
            data.append((char) b);
        }

        String message = data.toString(); // Avoid multiple conversion

        for (IListener listener : listeners) {
            listener.onMessage(message);
        }

    }

    public void close() throws IOException {
        connection.close();
    }

    public void addListener(IListener listener) {
        listeners.add(listener);
    }
}