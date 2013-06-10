package com.recluit.workshop.communication.adapter;

/**
 * Hello world!
 *
 */
public class CommunicationAdapterLauncher
{
    public static void main( String[] args )
    {
        SocketConnection con = new SocketConnection();
        try {
            con.connect();
            con.sendData("S300200007001777194498888S300200007001777194498888S300200007001777194498888S300200007001777194498888S300200007001777194498888S300200007001777194498888S300200007001777194498888S300200007001777194498888S300200007001777194498888S3002000070017771944988881234\n");
            con.receiveData();
//            con.close();
//            con.connect();
//            con.sendData("S3002001234564777538801\n");
//            con.receiveData();
//            con.close();
//            con.connect();
//            con.sendData("S3002001234564777538801\n");
//            con.receiveData();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
