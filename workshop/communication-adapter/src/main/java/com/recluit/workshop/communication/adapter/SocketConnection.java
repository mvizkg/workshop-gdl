/*
* :tabSize=4:indentSize=4:noTabs=true:
* :folding=explicit:collapseFolds=1:
*
* Copyright (c) 2003
*
*/
package com.recluit.workshop.communication.adapter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.FileWriter;

/**
 * Es la responsable de conectarse con BRITE enviar una solicitud y obtener una
 * respuesta.
 *
 *@author     JcSO
 */
public class SocketConnection {

    // Constants
    private static final int ETX = 0x03;

    //{{{ private variables
    private String host;
    private int port;
    private int timeout;

    private Socket connection;

    private BufferedInputStream in;
    private BufferedOutputStream out;

    //}}}

    /**
     *  Constructor principal de la clase SocketConnection
     */
    //{{{ SocketConnection() constructor
    public SocketConnection() {
        host = "10.211.55.10";
        port = 3550;
        timeout = 30000;
    }  //}}}

    //{{{ connect() method
    public void connect() {
        try {
            connection = new Socket(host, port);
            connection.setSoTimeout(timeout);

            out = new BufferedOutputStream(connection.getOutputStream());
            in = new BufferedInputStream(connection.getInputStream());
        } catch (UnknownHostException e) {}
        catch (java.net.ConnectException e) {}
        catch (IOException e) {}
    }  //}}}

    //{{{ sendData() method

    public void sendData(String data) {
        try {
            out.write(data.getBytes()); //obtenemos un arreglo de cracteres
            out.write((byte) 0x03);
            out.flush();
        } catch (IOException e) {}
    }  //}}}

    //{{{ receiveData() method

    public void receiveData() throws IOException {
        StringBuffer data = new StringBuffer();
        int b = 0;

        while ((b = in.read()) != ETX) {
            data.append((char) b);
        }
        System.out.println("Datos recibidos " + data.toString());

    }  //}}}

    //{{{ close() method
    public void close() throws IOException {
        connection.close();
    } //}}}
}