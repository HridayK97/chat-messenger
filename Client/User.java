package Client;
import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class User implements ActionListener{
  private String name;
  private String Ids;
  private int ID;
  private String IPAddr;
  JFrame frame1;
  JTextField field;
  public User(PrintWriter pw,BufferedReader io){
    setname();
    while(name==null){}
    try{
      IPAddr = Inet4Address.getLocalHost().getHostAddress();
      Ids=io.readLine();
      //Ids=Ids.substring(4,Ids.length());
      System.out.println(Ids);
      ID= Integer.parseInt(Ids);
      if (name.equals(""))
        pw.println("namech/"+Ids+"/Guest"+Ids+"/end");
      else
        pw.println("namech/"+Ids+"/"+name+"/end");
      pw.flush();
    }
    catch(Exception ex){ex.printStackTrace();}
  }
  public void setname(){
    frame1=new JFrame("Set a Name?");
    frame1.setSize(400,100);
		field=new JTextField(20);
		field.addActionListener(this);
		frame1.getContentPane().add(BorderLayout.NORTH,field);
    frame1.setVisible(true);
    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  public String getIPAddr(){
    return IPAddr;
  }
  public String getID(){
    return Ids;
  }
  public String getname(){
    return name;
  }
	public void actionPerformed(ActionEvent ev){
		if(field.getText().equals(""))
			this.name="Guest"+Ids;
		else
			this.name=field.getText();
			frame1.dispose();
	}
}
