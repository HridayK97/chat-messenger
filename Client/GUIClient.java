package Client;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class GUIClient {
	BufferedReader io;
	BufferedReader usr;
	PrintWriter pw;
	Socket sc;

	JTextField outgoing;
	JTextArea incoming;
	String ICN="test";
	JTextField field;
	JFrame frame;
	public void go(String IPAddr,String GroupN,String name){
    ICN=name;
		JPanel mainPanel = new JPanel();
		incoming = new JTextArea(15,50);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane qScroller = new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		outgoing = new JTextField(20);
		frame=new JFrame(GroupN);
		frame.setSize(600,600);
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		network(IPAddr);
		Thread th = new Thread(new readThread());
		th.start();
		frame.getContentPane().add(BorderLayout.SOUTH, mainPanel);
		frame.getContentPane().add(BorderLayout.CENTER,qScroller);
		frame.setSize(400,300);
    frame.setVisible(true);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			try {
				pw.println(ICN+": "+outgoing.getText());
				pw.flush();
			} catch(Exception ex) { ex.printStackTrace(); }
			outgoing.setText("");
			outgoing.requestFocus();
		}
	}
	class readThread implements Runnable{
		public void run(){
			String text;
			try{
				while((text = io.readLine())!=null)
					incoming.append(text + "\n");
			} catch(IOException ex){ ex.printStackTrace(); }
		}
	}
	private void network(String IPAddr){
		try{
			sc = new Socket(IPAddr,4500);
			io = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			pw = new PrintWriter(sc.getOutputStream());
		} catch(IOException ex){ ex.printStackTrace(); }
	}
}
