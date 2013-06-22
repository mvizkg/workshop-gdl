package com.recluit.lab.restservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientSockect 
{
	PrintWriter writer;
	BufferedReader reader;
	Socket sock;
	Thread t;
	ArrayList<String> loans;
	
	public ArrayList<String> getLoans()
	{
		return loans;
	}

	//public ArrayList<String> ClientSocket( String rfc, String opt )
	public ArrayList<String> ClientSocket( String rfc )
	{
		//establishNetwork( rfc, opt );
		establishNetwork( rfc );
		return getLoans();
	}
	
	//private void establishNetwork( String rfc, String opt )
	private void establishNetwork( String rfc )
	{
		try
		{
			sock = new Socket( "127.0.0.1", 3550 );
			reader = new BufferedReader( new InputStreamReader( sock.getInputStream() ) );
			System.out.println("* Connection established with the server *");
			
			Thread t = new Thread( new IncomingMessageReader() );
			t.start();
			writer = new PrintWriter( sock.getOutputStream() );
			//writer.print( opt + rfc );
			writer.print( rfc );
			writer.flush();
			t.join();
			writer.close();
		} 
		catch(Exception ex){
			ex.printStackTrace();
		}

	}
	
	class IncomingMessageReader implements Runnable
	{
		public void run()
		{
			String line = null;
			loans = new ArrayList<>();
			
			try {
				while( ( line = reader.readLine() )!= null )
				{
					System.out.println( "Incoming message:" + line.trim() );
					loans.add( line.trim() );
				}
			} catch (IOException e) {
				System.out.println( e.getMessage() );
			}
		}
	}
}