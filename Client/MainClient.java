package Client;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class MainClient{
  public static void main(String[] args){
    new MainClient().go();
  }
  Socket sc;
  BufferedReader io;
  PrintWriter pw;
  ObjectInputStream OIS;
  int Id;
  JFrame framech;
  User Pro ;
  JFrame frgr;
  JTextField groupN,groupPass;
  JButton Create;
  String grName="test",grPass="test";
  boolean flag=true;
  JButton CreateButton,JoinButton,Disconnect;
  public void go(){
    network();
    System.out.println("Start");
    Pro= new User(pw,io);
    framech = new JFrame("Welcome!");
    CreateButton = new JButton("Create a Group");
    JoinButton =new JButton("Join Existing Group");
    Disconnect = new JButton("Disconnect");
    CreateButton.addActionListener(new CreateListener());
    JoinButton.addActionListener(new JoinListener());
    Disconnect.addActionListener(new DCListener());
    JPanel pan=new JPanel();
    pan.setLayout(new BoxLayout(pan,BoxLayout.Y_AXIS));
    pan.add(CreateButton);
    pan.add(Box.createVerticalGlue());
    pan.add(Box.createHorizontalGlue());
    pan.add(JoinButton);
    pan.add(Box.createVerticalGlue());
    pan.add(Box.createHorizontalGlue());
    pan.add(Disconnect);
    pan.add(Box.createVerticalGlue());
    pan.add(Box.createHorizontalGlue());
    JPanel panIn=new JPanel();
    panIn.setSize(300,500);
    framech.getContentPane().add(BorderLayout.CENTER,pan);
    framech.getContentPane().add(BorderLayout.WEST,panIn);
    framech.setVisible(true);
    framech.setSize(500,200);
  }
  private void network(){
		try{
			sc = new Socket("127.0.0.1",5000);
			io = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			pw = new PrintWriter(sc.getOutputStream());
      OIS = new ObjectInputStream(sc.getInputStream());
		} catch(IOException ex){ ex.printStackTrace(); }
	}
  public class CreateListener implements ActionListener{
    public void actionPerformed(ActionEvent ev){
      framech.setVisible(false);
      setsName();
      while(flag){}
      System.out.println("Creating a group with name "+grName+" and pass "+grPass);
      pw.println("create/"+Pro.getID()+"/"+Pro.getIPAddr()+"/"+grName+"/"+grPass+"/end");
      pw.flush();
      GroupCr groupcreate=new GroupCr();
      groupcreate.go();
    }
  }
  private void setsName(){
    frgr=new JFrame("Group name and password?");
    groupN=new JTextField(20);
    groupPass=new JTextField(20);
    Create=new JButton("Create Group");
    Create.addActionListener(new SubmitListener());
    JPanel pan1=new JPanel();
    pan1.setLayout(new BoxLayout(pan1,BoxLayout.Y_AXIS));
    pan1.add(groupN);
    pan1.add(groupPass);
    pan1.add(Create);
    frgr.getContentPane().add(BorderLayout.CENTER,pan1);
    System.out.println("IN frame");
    frgr.setSize(400,300);
    frgr.setVisible(true);
    frgr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  public class SubmitListener implements ActionListener{
    public void actionPerformed(ActionEvent ev){
      grName=groupN.getText();
      grPass=groupN.getText();
      if(grPass.equals(""))
        grPass="None";
      frgr.dispose();
      flag=false;
    }
  }
  public class JoinListener implements ActionListener{
    public void actionPerformed(ActionEvent ev){
      Scanner scan=new Scanner(System.in);
      ArrayList<String> Data=new ArrayList<String>();
      pw.println("join/"+Pro.getID());
      pw.flush();
      try{Data=(ArrayList<String>)OIS.readObject();}
      catch(Exception ex){ex.printStackTrace();}
      Iterator it=Data.iterator();
      while (it.hasNext()){
          String Group []=((String)it.next()).split("/");
          System.out.println(Group[0]+" "+Group[1]+" "+Group[2]);
      }
      int grch=scan.nextInt();
      String Group[]=new String[3];
      it=Data.iterator();
      while (it.hasNext()){
        Group=((String)it.next()).split("/");
        if(new Integer(grch).toString().equals(Group[0]))
            break;
      }
      if(!(Group[2].equals("None"))){
        String passquer=scan.nextLine();
        if(!(passquer.equals(Group[2]))){
          System.out.println("Wrong Password");
        }
      }
      pw.println(Group[0]);
      pw.flush();
      String IPAddr="127.0.0.1";
      try{IPAddr = io.readLine();}
      catch(Exception ex){ex.printStackTrace();}
      Group grIn=new Group(IPAddr,grch,Group[1]);
      grIn.connect(Pro.getname());
    }
  }
  public class DCListener implements ActionListener{
    public void actionPerformed(ActionEvent ev){
      pw.write("dc/"+Pro.getID());
      pw.flush();
      try{sc.close();}
      catch(Exception ex){ex.printStackTrace();}
    }
  }
}
