package com.recluit.workshop.communication.adapter;


import org.junit.Test;

import java.io.IOException;
import java.net.UnknownHostException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit test for simple CommunicationAdapterLauncher.
 */
public class SocketConnectionTest {

    @Test(expected = UnknownHostException.class)
    public void noConnection() throws IOException {
        SocketConnection con = new SocketConnection("10.211.755.10", 3550, 10000);
        con.connect();
    }

    @Test
    public void noCredits() {
        try {
            SocketConnection con = new SocketConnection("10.211.55.10", 3550, 10000);
            con.connect();
            StringBuilder b = new StringBuilder();

            b.append("23456ABCD");
            con.sendData(b.toString());

            con.receiveData();
            con.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
