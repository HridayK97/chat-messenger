package Client;
import java.io.*;
import java.net.*;
import java.util.*;
public class GroupCr{
  ArrayList chatroom;
  public void go(){
    chatroom = new ArrayList();
    try{
      ServerSocket ser=new ServerSocket(4500);
      while(true){
        Socket sc = ser.accept();
        PrintWriter writer = new PrintWriter(sc.getOutputStream());
        chatroom.add(writer);
        Thread th = new Thread(new ClientFix(sc));
        th.start();
      }
    } catch(Exception ex){ ex.printStackTrace(); }
  }
  class ClientFix implements Runnable{
    InputStreamReader is;
    BufferedReader br;
    public ClientFix(Socket sc){
      try{
        is = new InputStreamReader(sc.getInputStream());
			  br = new BufferedReader(is);
      } catch(IOException ex){ ex.printStackTrace(); }
    }
    public void run(){
      String read;
      try{
        while((read = br.readLine())!=null){
          System.out.println(read);
          Send(read);
        }
      } catch(IOException ex){ ex.printStackTrace(); }
    }
  }
  public synchronized void Send(String msg){
    Iterator it = chatroom.iterator();
    while(it.hasNext()){
      try{
        PrintWriter write = (PrintWriter) it.next();
        write.println(msg);
				write.flush();
      } catch(Exception ex){ ex.printStackTrace(); }
    }
  }
}
