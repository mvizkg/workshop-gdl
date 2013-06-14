package sockets;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class ClientSockectExample {

	JTextField field;
	JTextField fieldID;
	JTextArea textArea;
	JButton button;
	JRadioButton searchRFC = new JRadioButton("Search RFC");
	JRadioButton cancelLoan = new JRadioButton("Cancel Loan");
	JRadioButton addLoan = new JRadioButton("Add Loan");
	PrintWriter writer;
	BufferedReader reader;
	Socket sock;
	ButtonGroup group = new ButtonGroup();
	
	public static void main(String[] args){
		new ClientSockectExample().buildGUI();
	}
	
	private void buildGUI()
	{
		JFrame frame = new JFrame("Client");
		JPanel panel = new JPanel();
		
		field = new JTextField(20);
		fieldID = new JTextField(5);
		
		textArea = new JTextArea(20, 40);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		
		JScrollPane scroller = new JScrollPane(textArea);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		button = new JButton("SEND");
		button.addActionListener( new ButtonListener() );
		panel.add(scroller);
		
		searchRFC.setSelected(true);
		group.add(searchRFC);
		group.add(cancelLoan);
		group.add(addLoan);
		
		JPanel radioPanel = new JPanel( new GridLayout(1, 1) );
		radioPanel.add(searchRFC);
		radioPanel.add(cancelLoan);
		radioPanel.add(addLoan);
		radioPanel.setBorder( BorderFactory.createEmptyBorder(20, 20, 20, 20) );
		
		panel.add(radioPanel, BorderLayout.LINE_START);
		panel.add(field, BorderLayout.LINE_END);
		panel.add(fieldID);
		panel.add(button);
				
		establishNetwork();
		Thread t = new Thread( new IncomingMessageReader() );
		t.start();
		
		frame.getContentPane().add(BorderLayout.CENTER, panel);
		frame.setSize(500, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void establishNetwork()
	{
		try
		{
			sock = new Socket( "127.0.0.1", 3550 );
			reader = new BufferedReader( new InputStreamReader( sock.getInputStream() ) );
			System.out.println("* Connection established with the server *");
			writer = new PrintWriter( sock.getOutputStream() );
		} 
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			try
			{
				if( searchRFC.isSelected() )
				{
					writer.print( "s" + field.getText().trim() );
					writer.flush();
				}
				else if( cancelLoan.isSelected() )
				{
					writer.print( "c" + field.getText().trim() );
					writer.flush();
				}
				else if( addLoan.isSelected() )
				{
					writer.print( "a" + field.getText().trim() );
					writer.flush();
				}
				field.setText("");
				field.requestFocus();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			finally {
				//writer.close();
				//button.setEnabled(false);
			}
		}
	}
	
	class IncomingMessageReader implements Runnable
	{
		public void run() {
			String line = null;
			try
			{
				while( ( line = reader.readLine() )!= null )
				{
					textArea.append( line.trim() + "\n" );
					System.out.println( "Incoming message:" + line.trim() );
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}