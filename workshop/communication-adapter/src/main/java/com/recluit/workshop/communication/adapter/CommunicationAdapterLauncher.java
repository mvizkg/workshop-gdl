package com.recluit.workshop.communication.adapter;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class CommunicationAdapterLauncher
{
    public static void main( String[] args )
    {
        try {
            SocketConnection con = new SocketConnection("10.211.55.10", 3550, 10000);
            con.connect();
            con.addListener(new MessageListener());
            StringBuilder b = new StringBuilder();

            b.append("RELM");
            b.append("23456ABCDE");
            b.append("otro");
            con.sendData(b.toString());

            con.receiveData();
            con.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
